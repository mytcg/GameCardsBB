package net.mytcg.fhm.ui.custom;

import net.mytcg.fhm.util.Const;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;

public final class PageField extends Field {
	private boolean active = false;
	public boolean empty;
	
	private Bitmap active_dot;
	private Bitmap inactive_dot;
	
	public PageField() {
		active_dot = Const.getActiveDot();
		inactive_dot = Const.getInactiveDot();
	}
	public void setActive(boolean active){
		this.active = active;
	}
	public void onUndisplay() {
		
	}
	public void onVisibilityChange(boolean visible) {
		
	}
	public void focusRemove() {
		
	}
	protected void drawFocus(Graphics g, boolean x) {
		
	}
	
	public int getPreferredWidth() {
		return 6;
	}
	public int getPreferredHeight() {
		return 6;
	}
	protected void layout(int width, int height) {
		setExtent(getPreferredWidth(),getPreferredHeight());
    }
	public boolean isFocusable() {
		return false;
    }
	public void paint(Graphics g) {
		if(active){
			g.drawBitmap(0, 0, 6, 6, active_dot, 0, 0);
		}else{
			g.drawBitmap(0, 0, 6, 6, inactive_dot, 0, 0);
		}
	}
	protected void onFocus(int direction) {
		invalidate();
	}
	protected void onUnfocus() {
		invalidate();
	}
	protected boolean navigationClick(int status, int time) {
        fieldChangeNotify(1);
        return true;
    }
}