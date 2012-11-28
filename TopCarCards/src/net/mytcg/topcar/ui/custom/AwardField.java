package net.mytcg.topcar.ui.custom;

import java.io.InputStream;
import java.util.Vector;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import net.mytcg.topcar.util.Award;
import net.mytcg.topcar.util.Const;
import net.mytcg.topcar.util.SubAward;
import net.rim.device.api.math.Fixed32;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;


public final class AwardField extends Field {
	
	private String thumbfile;
	private String frontfile;
	private String backfile;
	
	private boolean focus;
	private boolean focusable = true;
	private int subIndex = -1;
	private String label1;
	private String label2;
	private String label3;
	
	private Bitmap button_thumbnail;
	
	private Bitmap button_centre;
	
	private Bitmap button_sel_centre;
	
	private Award award = null;
	
	public String getDescription() {
		return award.getDescription();
	}
	public Award getAward() {
		return award;
	}
	
	public String getThumbUrl() {
		Vector subawards = award.getSubAwards();
		//System.out.println(award.getName()+" subawards.size() "+subawards.size());
		if(subIndex==-1){
			for(int i = subawards.size()-1;i>=0;i--){
				SubAward sub = (SubAward)subawards.elementAt(i);
				//System.out.println(sub.getCompleteImage());
				if(sub.getProgress()>=sub.getTarget()){
					return sub.getCompleteImage();
				}
			}
		}else{
			return award.getSubAward(subIndex).getCompleteImage();
		}
		return award.getIncompleteImage();
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
	
	public AwardField(Award award, int subIndex) {
		this.award = award;
		this.label1 = "";
		if(subIndex ==-1){
			this.label2 = award.getName();
		}else{
			label2 = award.getSubAward(subIndex).getDateCompleted();
		}
		this.label3 = "";
		this.subIndex = subIndex;
		
		if ((getThumbUrl() != null)&&(getThumbUrl().length() > 0)){
			thumbfile = getThumbUrl().substring(getThumbUrl().indexOf("/phone/")+Const.cards_length, getThumbUrl().indexOf(Const.png));
		}
		System.out.println(thumbfile);
		construct();
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
			} else if (_file.fileSize() == 0) {
				_file.delete();
				_file.close();
				_file = null;
				doConnect(url, type);
			} else {
				if (type==0) {
					input = _file.openInputStream();
					byte[] data = new byte[(int) _file.fileSize()];
					input.read(data);
					input.close();
					_file.close();
					button_thumbnail = Const.getSizeImage(EncodedImage.createEncodedImage(data, 0, data.length));
					invalidate();
				}
			}
		} catch (Exception e) {
			try {
				_file.close();
			} catch (Exception ex) {}
			doConnect(url, type);
		}
	}
	
	public void construct() {
		int font = Const.FONT;
		
		button_centre = Const.getThumbCentre();
		button_sel_centre = Const.getThumbRightEdge();
		
		
		button_thumbnail = Const.getThumbLoading();
		if (thumbfile != null) {
			getData(0);
		}
		Font _font = getFont();
		_font = _font.derive(Const.TYPE,font);
		setFont(_font);
	}
	public void setLabel(String label) {
		this.label1 = label;
		invalidate();
	}
	
	public void setSecondLabel(String label) {
		this.label2 = label;
		invalidate();
	}
	
	public void setThirdLabel(String label) {
		this.label3 = label;
		invalidate();
	}
	
	
	public Bitmap getThumbnail(){
		return button_thumbnail;
	}
	public void setThumbnail(Bitmap thumb){
		this.button_thumbnail = thumb;
	}
	public int getPreferredWidth() {
		Manager tmp = getManager();
		if (tmp != null) {
			return tmp.getPreferredWidth();
		} else {
			return Const.getWidth();
		}
	}
	public int getPreferredHeight() {
		return button_sel_centre.getHeight();
	}
	protected void layout(int width, int height) {
		setExtent(getPreferredWidth(),getPreferredHeight());
    }
	public boolean isFocusable() {
    	return focusable;
    }
	public void setFocusable(boolean focusable){
		this.focusable = focusable;
	}
	//Bitmap grey = Const.getGrey();
	public void paint(Graphics g) {
		int xPts[] = {0,0,getPreferredWidth(),getPreferredWidth()};
		int yPts[] = {0,getPreferredHeight(),getPreferredHeight(),0};
		g.setColor(2302755);
		g.drawFilledPath(xPts, yPts, null, null);
		//g.drawTexturedPath(xPts,yPts,null,null,0,this.getTop(),Fixed32.ONE,0,0,Fixed32.ONE,grey);
		
		g.setColor(Const.FONTCOLOR);
		
		if (focus) {
			g.setColor(Const.SELECTEDCOLOR);
			g.drawTexturedPath(xPts,yPts,null,null,0,0,Fixed32.ONE,0,0,Fixed32.ONE,button_centre);
			g.drawBitmap(getPreferredWidth() - (button_sel_centre.getWidth() + 5), 0, button_sel_centre.getWidth(), getPreferredHeight(), button_sel_centre, 0, 0);
		} else {
			//int xPts1[] = {2,2,getPreferredWidth()-2,getPreferredWidth()-2};
			//int yPts1[] = {0,getPreferredHeight(),getPreferredHeight(),0};
				
			g.drawTexturedPath(xPts,yPts,null,null,0,0,Fixed32.ONE,0,0,Fixed32.ONE,button_centre);
		}
		
		
		
		g.drawBitmap(5, 2, button_thumbnail.getWidth(), getPreferredHeight(), button_thumbnail, 0, 0);
		
		Font _font = getFont();
		_font = _font.derive(Const.TYPE,Const.FONT+4);
		g.setFont(_font);
		if(award != null){
			g.drawText(label1, button_thumbnail.getWidth()+10, 4);
			
		}
		if(!label2.equals("")){
			g.drawText(label2, button_thumbnail.getWidth()+10, Const.FONT+6);
		}
		if(!label3.equals("")){
			g.drawText(label3, button_thumbnail.getWidth()+10, (Const.FONT*2)+8);
		}
		
		_font = _font.derive(Font.PLAIN,Const.FONT);
		g.setFont(_font);
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
			try{
				button_thumbnail = Const.getSizeImage(EncodedImage.createEncodedImage(data, 0, data.length));
			}catch(IllegalArgumentException e){
				
			}
			invalidate();
		}
	}
}