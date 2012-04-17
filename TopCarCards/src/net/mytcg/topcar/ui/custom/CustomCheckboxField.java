package net.mytcg.topcar.ui.custom;

import net.mytcg.topcar.util.Const;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;

public class CustomCheckboxField extends Field{
	boolean focus = false;
	boolean focusable = false;
	boolean checked = false;
	private Bitmap checkedSkin;
	private Bitmap uncheckedSkin;
	public CustomCheckboxField(boolean checked, boolean focusable, long style) {
		super(style);
		this.checked = checked;
		this.focusable = focusable;
		checkedSkin = Const.getBoxSelected();
		uncheckedSkin = Const.getBoxUnselected();
	}
	public void setChecked(boolean checked){
		this.checked = checked;
	}
	public boolean isFocusable() {
	   	return focusable;
	}
	public void onUnfocus() {
		focus = false;
		invalidate();
	}
	public void onFocus(int direction) {
		focus = true;
		invalidate();
	}
	public void onVisibilityChange(boolean visible) {
		
	}
	public void focusRemove() {
		
	}
	protected void drawFocus(Graphics g, boolean x) {
		
	}
	public int getPreferredWidth() {
		return checkedSkin.getWidth();
	}
	public int getPreferredHeight() {
		return checkedSkin.getHeight();
	}
	protected void layout(int width, int height) {
		setExtent(getPreferredWidth(),getPreferredHeight());
	   }
		public void paint(Graphics g) {
		int _xPts[] = {0,0,getPreferredWidth(),getPreferredWidth()};
		int _yPts[] = {0,getPreferredHeight(),getPreferredHeight(),0};
		g.setColor(3947580);
		g.drawFilledPath(_xPts, _yPts, null, null);
		
		if (!checked) {
			g.drawBitmap(0, 0, uncheckedSkin.getWidth(), uncheckedSkin.getHeight(), uncheckedSkin, 0, 0);
		}else{
			g.drawBitmap(0, 0, checkedSkin.getWidth(), checkedSkin.getHeight(), checkedSkin, 0, 0);
		}
	}
	protected boolean navigationClick(int status, int time) {
        fieldChangeNotify(1);
        return true;
	}
}
