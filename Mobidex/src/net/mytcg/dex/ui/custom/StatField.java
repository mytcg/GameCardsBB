package net.mytcg.dex.ui.custom;

import net.mytcg.dex.util.Const;
import net.mytcg.dex.util.Stat;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;

public final class StatField extends Field {
	private boolean focus;
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

		//landscape();

		Font _font = getFont();
		_font = _font.derive(Font.BOLD,font);
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
    	return true;
    }
	public void paint(Graphics g) {
		if(stat.getFrontOrBack()==flip){
			if(focus){
				if(!(Const.getPortrait())){
					//g.fillRect(((getPreferredWidth()-(image.getWidth()))/2)+stat.getTop()*image.getWidth()/350, (((getPreferredHeight())-((image.getHeight())))/2)+(250 - stat.getLeft() - stat.getWidth())*image.getHeight()/250, stat.getHeight()*image.getHeight()/250, stat.getWidth()*image.getWidth()/350);
					g.drawRect(0, 0, stat.getHeight()*image.getHeight()/250, stat.getWidth()*image.getWidth()/350);
					g.fillRect(0, 0, 300, 300);
					System.out.println("FUUUUU1");
				}else{
					//g.fillRect(((getPreferredWidth()-(image.getWidth()))/2)+stat.getLeft()*image.getWidth()/250, (((getPreferredHeight())-((image.getHeight())))/2)+stat.getTop()*image.getHeight()/350, stat.getWidth()*image.getWidth()/250, stat.getHeight()*image.getHeight()/350);
					g.drawRect(0, 0, stat.getWidth()*image.getWidth()/250, stat.getHeight()*image.getHeight()/350);
				}
			}
		}
	}
	protected boolean navigationClick(int status, int time) {
        fieldChangeNotify(1);
        return true;
    }
	
	public void landscape() {
		if (!(Const.getPortrait())) {
			try {
				//image = Const.rotate(image, 270);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	public void process(byte[] data) {
		landscape();
		invalidate();
	}
}