package net.mytcg.topcar.ui.custom;

import net.mytcg.topcar.util.Const;
import net.mytcg.topcar.util.Stat;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;

public final class StatField extends Field {
	private boolean focus;
	private boolean focusable = true;
	public boolean gamestat = false;
	public Stat stat;
	public int flip = 0;
	private Bitmap image;
	
	public StatField(Stat stat, Bitmap image) {
		this.stat = stat;
		this.image = image;
		construct();
	}
	
	protected void drawFocus(Graphics g, boolean x) {
		
	}
	public void setFlip(int flip) {
		this.flip = flip;
	}
	public void construct() {
		int font = Const.FONT;

		Font _font = getFont();
		_font = _font.derive(Const.TYPE,font);
		setFont(_font);
	}
	
	public void onUndisplay() {
		
	}
	protected void onFocus(int direction) {
		focus = true;
		invalidate();
	}
	protected void onUnfocus() {
		focus = false;
		invalidate();
	}
	public void onVisibilityChange(boolean visible) {
		
	}
	public void focusRemove() {
		
	}
	public void draw(boolean draw){
		focus = draw;
		invalidate();
	}
	public int getPreferredWidth() {
		return Const.getWidth();
	}
	public int getPreferredHeight() {
		if(!(Const.getPortrait())){
			return image.getHeight()-Const.getButtonCentre().getHeight();
		}else{
			return Const.getHeight()-Const.getButtonCentre().getHeight();
		}
	}
	protected void layout(int width, int height) {
		setExtent(getPreferredWidth(),getPreferredHeight());
    }
	public boolean isFocusable() {
    	return focusable;
    }
	
	public void setFocusable(boolean focusable) {
		this.focusable = focusable;
    }
	
	public void paint(Graphics g) {
		if(stat.getFrontOrBack()==flip){
			if(focus){
				g.setColor(65536*stat.getColorRed()+256*stat.getColorGreen()+stat.getColorBlue());
				if(!(Const.getPortrait())){
					//g.fillRect(((getPreferredWidth()-(image.getWidth()))/2)+stat.getTop()*image.getWidth()/350, (((getPreferredHeight())-((image.getHeight())))/2)+(250 - stat.getLeft() - stat.getWidth())*image.getHeight()/250, stat.getHeight()*image.getHeight()/250, stat.getWidth()*image.getWidth()/350);
					if(!gamestat){
						g.drawRect(0, 0, stat.getHeight()*image.getHeight()/250, stat.getWidth()*image.getWidth()/350);
					}else {
						g.drawRect(0, 0, stat.getWidth()*image.getWidth()/250, stat.getHeight()*image.getHeight()/350);
					}
				}else{
					//g.fillRect(((getPreferredWidth()-(image.getWidth()))/2)+stat.getLeft()*image.getWidth()/250, (((getPreferredHeight())-((image.getHeight())))/2)+stat.getTop()*image.getHeight()/350, stat.getWidth()*image.getWidth()/250, stat.getHeight()*image.getHeight()/350);
					if(!gamestat){
						g.drawRect(0, 0, stat.getWidth()*image.getWidth()/250, stat.getHeight()*image.getHeight()/350);
					}else{
						g.drawRect(0, 0, stat.getHeight()*image.getWidth()/350, stat.getWidth()*image.getHeight()/250);
					}
				}
			}
		}
	}
	protected boolean navigationClick(int status, int time) {
        fieldChangeNotify(1);
        return true;
    }
	
	public FieldChangeListener getChangeListener() {
		// TODO Auto-generated method stub
		return super.getChangeListener();
	}
	
	protected void onChange(){
		
	}
}