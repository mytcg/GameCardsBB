package net.mytcg.kickoff.ui.custom;

import net.mytcg.kickoff.util.Const;
import net.rim.device.api.math.Fixed32;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.XYRect;
import net.rim.device.api.ui.component.LabelField;

public final class MenuThumbnailField extends LabelField {	
	private boolean focus;
	private boolean focusable = true;
	private int counter = 0;
	
	private Bitmap button_thumbnail;
	private Bitmap main_image;
	private Bitmap button_select;
	MenuField menu;
	
	public MenuThumbnailField(Bitmap menuOption, Bitmap menuOptionSelected, Bitmap mainImage, MenuField menu) {
		button_thumbnail = menuOption;
		main_image = mainImage;
		button_select = menuOptionSelected;
		this.menu = menu;
	}
	
	//Bitmap img = Const.getBackground();
	protected void drawFocus(Graphics graphics, boolean on) {
		//if(counter!=0){
			//XYRect rect = new XYRect();
			//rect = getFieldExtent(this);
		//	Bitmap bmp = new Bitmap(this.getWidth(), this.getHeight());
	    //	Display.screenshot(bmp, rect.x, rect.y, this.getWidth(), this.getHeight());
	    //	graphics.drawBitmap(0, 0, this.getWidth(), this.getHeight(), bmp, 0, 0);
		//	this.paint(graphics);
		//}
		//counter++;
    }
	
	public static final XYRect getFieldExtent(Field fld) {
        int cy = fld.getContentTop();
        int cx = fld.getContentLeft();
        Manager m = fld.getManager();
        while (m != null) {
            cy += m.getContentTop() - m.getVerticalScroll();
            cx += m.getContentLeft() - m.getHorizontalScroll();
            if (m instanceof Screen)
                break;
            m = m.getManager();
        }
        return new XYRect(cx, cy, fld.getContentWidth(), fld.getContentHeight());
    }
	
	public boolean isFocus (){
		return focus;
	}

	public Bitmap getThumbnail(){
		return button_thumbnail;
	}
	public void setThumbnail(Bitmap thumb){
		this.button_thumbnail = thumb;
	}
	public int getPreferredWidth() {
		return 74 + (Const.getWidth() % 74)/(Const.getWidth()/74) ;
	}
	public int getPreferredHeight() {
		return Const.getAlbumThumb().getHeight()+2;
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
		g.setColor(2500134);
		g.drawFilledPath(xPts, yPts, null, null);
		//g.drawTexturedPath(xPts,yPts,null,null,0,this.getTop(),Fixed32.ONE,0,0,Fixed32.ONE,grey);
		if (focus) {
			g.drawBitmap(6, 0, button_select.getWidth(), button_select.getHeight(), button_select, 0, 0);
		}else{
			g.drawBitmap(6, 0, button_thumbnail.getWidth(), button_thumbnail.getHeight(), button_thumbnail, 0, 0);
		}
	}
	protected void onFocus(int direction) {
		focus = true;
		menu.setImage(main_image);
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