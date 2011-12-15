package net.mytcg.topcar.ui.custom;

import net.mytcg.topcar.ui.MenuScreen;
import net.mytcg.topcar.util.Const;
import net.rim.device.api.math.Fixed32;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.TouchEvent;
import net.rim.device.api.ui.XYRect;

public final class MenuField extends Field {	
	private boolean focus;
	private boolean focusable = false;
	
	private Bitmap button_image;
	
	public MenuField(Bitmap menuOption) {
		button_image = menuOption;
		invalidate();
	}
	
	protected void drawFocus(Graphics g, boolean x) {
		
	}
	
	protected boolean touchEvent(TouchEvent event) {
		if(event.getEvent() == TouchEvent.CLICK){
			int x = event.getX(1);
			int y = event.getY(1);
			if((x>(getPreferredWidth()-button_image.getWidth())/2 && x < ((getPreferredWidth()-button_image.getWidth())/2+button_image.getWidth()))&&(y>(getPreferredHeight()-button_image.getHeight()+4)/2 && y <((getPreferredHeight()-button_image.getHeight()+4)/2+button_image.getHeight()))){
				if(this.getScreen() instanceof MenuScreen){
					((MenuScreen)this.getScreen()).fieldChanged(this.getScreen().getLeafFieldWithFocus(), 1);
				}
				return true;
			}
		}
		return false;//super.touchEvent(event);
	}
	
	public Bitmap getImage(){
		return button_image;
	}
	public void setImage(Bitmap thumb){
		this.button_image = thumb;
	}
	public int getPreferredWidth() {
		return Const.getWidth();
	}
	public int getPreferredHeight() {
		return Const.getHeight()-(84+Const.getLogo().getHeight());
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
	public void paint(Graphics g) {
		g.drawBitmap((getPreferredWidth()-button_image.getWidth())/2, (getPreferredHeight()-button_image.getHeight()+4)/2, button_image.getWidth(), button_image.getHeight(), button_image, 0, 0);
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
}