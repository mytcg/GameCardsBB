package net.mytcg.phakama.ui.custom;

import net.mytcg.phakama.util.Const;
import net.mytcg.phakama.util.Product;
import net.rim.device.api.math.Fixed32;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;

public final class ProductField extends Field {
	private Product product;
	private Bitmap image;
	
	public ProductField(Product product, Bitmap thumb) {
		this.product = product;
		this.image = thumb;
		construct();
	}
	
	protected void drawFocus(Graphics g, boolean x) {
		
	}
	
	public void construct() {
		int font = Const.FONT;
		
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
		return Const.getHeight()-Const.getButtonCentre().getHeight();
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
		
		g.drawText(product.getDesc(), image.getWidth()+10, 4);
		
		g.drawText("Credits: "+product.getPrice(), image.getWidth()+10, Const.FONT+6);
		
		g.drawText("Cards: "+product.getNumCards(), image.getWidth()+10, (Const.FONT*2)+8);
		
		g.drawText("Type: "+product.getType(), image.getWidth()+10, (Const.FONT*3)+12);

		_font = _font.derive(Font.PLAIN,Const.FONT);
		g.setFont(_font);
	}

	protected boolean navigationClick(int status, int time) {
        fieldChangeNotify(1);
        return true;
    }
}