package net.mytcg.dex.ui.custom;

import java.io.InputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

import net.mytcg.dex.util.Card;
import net.mytcg.dex.util.Const;
import net.rim.device.api.math.Fixed32;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;

public final class ThumbnailField extends Field {
	
	private String thumbfile;
	private String frontfile;
	private String backfile;
	
	private boolean focus;
	private String label1;
	private String label2;
	
	private Bitmap button_centre;
	
	private Bitmap button_sel_right_edge;
	
	private Bitmap button_thumbnail;
	
	private Bitmap note;
	
	private Card card;
	
	private boolean delete = false;
	
	public int getId() {
		return card.getId();
	}
	public String getDescription() {
		return card.getDesc();
	}
	public int getQuantity() {
		return card.getQuantity();
	}
	public String getThumbUrl() {
		return card.getThumburl();
	}
	public String getFrontUrl() {
		return card.getFronturl();
	}
	public String getBackUrl() {
		return card.getBackurl();
	}
	public String getThumbFile() {
		return thumbfile;
	}
	public String getFrontFile() {
		return frontfile;
	}
	public String getBackFile() {
		return backfile;
	}
	public Card getCard() {
		return card;
	}
	
	public ThumbnailField(Card card) {
		this(card, false);
	}
	public ThumbnailField(Card card, boolean delete) {
		this.card = card;
		this.delete = delete;
		
		/*if (card.getUpdated() == 1) {
			this.delete = true;
		}*/
		
		label2 = "Quantity: " + card.getQuantity();
		
		if ((getThumbUrl() != null)&&(getThumbUrl().length() > 0)){
			thumbfile = getThumbUrl().substring(getThumbUrl().indexOf(Const.cards)+Const.cards_length, getThumbUrl().indexOf(Const.png));
		}
		if ((getFrontUrl() != null)&&(getFrontUrl().length() > 0)){
			frontfile = getFrontUrl().substring(getFrontUrl().indexOf(Const.cards)+Const.cards_length, getFrontUrl().indexOf(Const.png));
		}
		if ((getBackUrl() != null)&&(getBackUrl().length() > 0)){
			backfile  = getBackUrl().substring(getBackUrl().indexOf(Const.cards)+Const.cards_length, getBackUrl().indexOf(Const.png));
		}
		
		construct(getDescription());
	}
	
	protected void drawFocus(Graphics g, boolean x) {
		
	}
	public void getData(int type) {
		FileConnection _file = null;
		InputStream input = null;
		String filename = "";
		String url = "";
		switch (type) {
		case 0:
			filename = thumbfile;
			url = getThumbUrl();
			break;
		case 1:
			filename = frontfile;
			url = getFrontUrl();
			break;
		case 2:
			filename = backfile;
			url = getBackUrl();
			break;
		}
		try {
			_file = (FileConnection)Connector.open(Const.getStorage());
			if (!_file.exists()) {
				_file.mkdir();
			}
			_file.close();
			_file = (FileConnection)Connector.open(Const.getStorage()+Const.PREFIX+filename);
			if (!_file.exists()) {
				_file.close();
				_file = null;
				doConnect(url, type);
			} else if (!delete) {
				if (type==0) {
					input = _file.openInputStream();
					byte[] data = new byte[(int) _file.fileSize()];
					input.read(data);
					input.close();
					_file.close();
					button_thumbnail = Const.getSizeImage(EncodedImage.createEncodedImage(data, 0, data.length));
					invalidate();
				}
			} else if (delete) {
				_file.delete();
				_file.close();
				_file = null;
				doConnect(url, type);
			}
		} catch (Exception e) {
			
		}
	}
	
	public void construct(String label) {
		int font = Const.FONT;
		
		button_centre = Const.getThumbCentre();
		button_sel_right_edge = Const.getThumbRightEdge();
		note = Const.getNote();
		
		if (getQuantity() == 0) {
			button_thumbnail = Const.getEmptyThumb();
		} else {
			button_thumbnail = Const.getThumbLoading();
		}
		if ((thumbfile != null)&&(getQuantity()>0)) {
			getData(0);
		}
		Font _font = getFont();
		_font = _font.derive(Font.BOLD,font);
		setFont(_font);
		this.label1 = label;
	}
	public void setLabel(String label) {
		this.label1 = label;
		invalidate();
	}
	
	public int getPreferredWidth() {
		return Const.getWidth();
	}
	public int getPreferredHeight() {
		return button_sel_right_edge.getHeight();
	}
	protected void layout(int width, int height) {
		setExtent(getPreferredWidth(),getPreferredHeight());
    }
	public boolean isFocusable() {
    	return true;
    }
	public void paint(Graphics g) {
		int _xPts[] = {0,0,getPreferredWidth(),getPreferredWidth()};
		int _yPts[] = {0,Const.getHeight(),Const.getHeight(),0};
		g.drawTexturedPath(_xPts,_yPts,null,null,0,0,Fixed32.ONE,0,0,Fixed32.ONE,Const.getBackground());
		
		int xPts[] = {0,0,getPreferredWidth(),getPreferredWidth()};
		int yPts[] = {0,Const.getHeight(),Const.getHeight(),0};
		g.drawTexturedPath(xPts,yPts,null,null,0,0,Fixed32.ONE,0,0,Fixed32.ONE,Const.getBackground());
		
		int xPts1[] = {2,2,getPreferredWidth()-2,getPreferredWidth()-2};
		int yPts1[] = {0,getPreferredHeight(),getPreferredHeight(),0};
		
		g.drawTexturedPath(xPts1,yPts1,null,null,0,0,Fixed32.ONE,0,0,Fixed32.ONE,button_centre);
		
		g.drawBitmap(5, 0, button_thumbnail.getWidth(), getPreferredHeight(), button_thumbnail, 0, 0);
		
		g.setColor(Const.FONTCOLOR);
		if (focus) {
			g.setColor(Const.SELECTEDCOLOR);
			g.drawBitmap(Const.getWidth() - (button_sel_right_edge.getWidth() + 5), 0, button_sel_right_edge.getWidth(), getPreferredHeight(), button_sel_right_edge, 0, 0);
		}
		Font _font = getFont();
		_font = _font.derive(Font.BOLD,Const.FONT+2);
		g.setFont(_font);
		
		if ((card.getNote() != null)&&(card.getNote().length() > 0)) {
			g.drawBitmap(5, 0, note.getWidth(), note.getHeight(), note, 0, 0);
		}
		
		if (card.getUpdated() == 1) {
			g.drawText("*" +label1, button_thumbnail.getWidth()+10, 4);
		} else {
			g.drawText(label1, button_thumbnail.getWidth()+10, 4);
		}
		
		_font = _font.derive(Font.PLAIN,Const.FONT);
		g.setFont(_font);
		
		if (label2!=null&&label2.length()>0) {
			g.drawText(label2, button_thumbnail.getWidth()+10, 8+Const.FONT);
		}
	}
	protected void onFocus(int direction) {
		focus = true;
		invalidate();
	}
	protected void onUnfocus() {
		focus = false;
		invalidate();
	}
	protected boolean navigationClick(int status, int time) {
        fieldChangeNotify(1);
        return true;
    }
	
	public void doConnect(String url, int type) {
		
		if ((url != null)&&(url.length() > 0)) {
			(Const.getConnection()).addConnect(url, type, this);
		}
	}
	public void process(byte[] data, int type) {
		if (type == 0) {
			button_thumbnail = Const.getSizeImage(EncodedImage.createEncodedImage(data, 0, data.length));
			invalidate();
		}
		if (type < 2) 
			getData(++type);
	}
}