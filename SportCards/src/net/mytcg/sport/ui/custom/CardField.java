package net.mytcg.sport.ui.custom;

import net.mytcg.sport.util.Card;
import net.mytcg.sport.util.Const;
import net.rim.device.api.math.Fixed32;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;

public final class CardField extends Field {
	private Card card;
	private Bitmap image, button_sel_centre;
	
	public CardField(Card card, Bitmap image) {
		this.card = card;
		this.image = image;
		construct();
	}
	
	protected void drawFocus(Graphics g, boolean x) {
		
	}
	
	public void construct() {
		int font = Const.FONT;
		button_sel_centre = Const.getThumbRightEdge();
		
		Font _font = getFont();
		_font = _font.derive(Const.TYPE,font);
		setFont(_font);
	}
	
	public void onUndisplay() {
		
	}
	public void onUnfocus() {
		//invalidate();
		
	}
	public void onVisibilityChange(boolean visible) {
		
	}
	public void focusRemove() {
		
	}
	
	public int getPreferredWidth() {
		return Const.getWidth();
	}
	public int getPreferredHeight() {
		return button_sel_centre.getHeight();
	}
	protected void layout(int width, int height) {
		setExtent(getPreferredWidth(),getPreferredHeight());
    }
	public boolean isFocusable() {
    	return false;
    }
	public void paint(Graphics g) {
		int _xPts[] = {0,0,getPreferredWidth(),getPreferredWidth()};
		int _yPts[] = {0,Const.getHeight(),Const.getHeight(),0};
		g.drawTexturedPath(_xPts,_yPts,null,null,0,0,Fixed32.ONE,0,0,Fixed32.ONE,Const.getBackground());
		
		g.setColor(Const.FONTCOLOR);
		
		g.drawBitmap(5, 5, image.getWidth(), getPreferredHeight(), image, 0, 0);
		
		Font _font = getFont();
		_font = _font.derive(Const.TYPE,Const.FONT+2);
		g.setFont(_font);
		
		g.drawText(card.getDesc(), image.getWidth()+10, 4);

		_font = _font.derive(Font.PLAIN,Const.FONT);
		g.setFont(_font);
	}

	protected boolean navigationClick(int status, int time) {
        fieldChangeNotify(1);
        return true;
    }
}