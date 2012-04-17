package net.mytcg.dev.ui.custom;

import net.mytcg.dev.util.Const;
import net.rim.device.api.math.Fixed32;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.Screen;
//import net.rim.device.api.ui.TouchEvent;
import net.rim.device.api.ui.XYRect;

public final class ListItemField extends Field {
	
	private boolean focus = false;
	private String label;
	private int id;
	private boolean hasCards;
	private boolean focusable = true;
	
	private Bitmap button_centre;
	private Bitmap button_left_edge;
	private Bitmap button_right_edge;
	
	private Bitmap button_sel_centre;
	private Bitmap button_sel_left_edge;
	private Bitmap button_sel_right_edge;
	
	public int getId() {
		return id;
	}
	public boolean hasCards() {
		return hasCards;
	}
	public ListItemField(String label, int id, boolean hasCards, int updated) {
		this(label, Const.FONT, id, hasCards, updated);
	}
	public ListItemField(String label, int fontsize, int id, boolean hasCards, int updated) {
		construct(fontsize, label, id, hasCards, updated);
	}
	protected void drawFocus(Graphics g, boolean x) {

	}
	
	public void construct(int font, String label, int id, boolean hasCards, int updated) {
		
		button_centre = Const.getListboxCentre();
		button_left_edge = Const.getListboxLeftEdge();
		button_right_edge = Const.getListboxRightEdge();
		
		button_sel_centre = Const.getListboxSelCentre();
		button_sel_left_edge = Const.getListboxSelLeftEdge();
		button_sel_right_edge = Const.getListboxSelRightEdge();
		
		Font _font = getFont();
		_font = _font.derive(Const.TYPE,font+2);
		setFont(_font);
		this.label = label;
		this.id = id;
		this.hasCards = hasCards;
		if (updated==1) {
			this.label = "*"+label;
		}
	}
	public void setLabel(String label) {
		this.label = label;
		invalidate();
	}
	public String getLabel() {
		return label;
	}
	
	public void setFocusable(boolean f){
		focusable = f;
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
		return Const.getButtonHeight();
	}
	protected void layout(int width, int height) {
		setExtent(getPreferredWidth(),getPreferredHeight());
    }
	public boolean isFocusable() {
    	return focusable;
    }
	//Bitmap grey = Const.getGrey();
	public void paint(Graphics g) {
		int xPts[] = {0,0,getPreferredWidth(),getPreferredWidth()};
		int yPts[] = {0,getPreferredHeight(),getPreferredHeight(),0};
		g.setColor(3947580);
		g.drawFilledPath(xPts, yPts, null, null);
		//g.drawTexturedPath(xPts,yPts,null,null,0,this.getTop(),Fixed32.ONE,0,0,Fixed32.ONE,grey);
		g.setColor(Const.FONTCOLOR);
		
		//int xPts[] = {0,0,getPreferredWidth(),getPreferredWidth()};
		//int yPts[] = {0,getPreferredHeight(),getPreferredHeight(),0};
		//g.clear();
		
		g.setColor(Const.FONTCOLOR);
		
		if (focus) {
			g.drawTexturedPath(xPts,yPts,null,null,0,0,Fixed32.ONE,0,0,Fixed32.ONE,button_sel_centre);
			
			g.drawBitmap(0, 0, getPreferredWidth(), getPreferredHeight(), button_sel_left_edge, 0, 0);
			g.drawBitmap(getPreferredWidth()-(button_sel_right_edge.getWidth()), 0, getPreferredWidth(), getPreferredHeight(), button_sel_right_edge, 0, 0);

			g.setColor(Const.SELECTEDCOLOR);
		} else {
			g.drawTexturedPath(xPts,yPts,null,null,0,0,Fixed32.ONE,0,0,Fixed32.ONE,button_centre);
			
			//g.drawBitmap(5, 0, getPreferredWidth(), getPreferredHeight(), button_left_edge, 0, 0);
			//g.drawBitmap(getPreferredWidth()-(button_right_edge.getWidth()+5), 0, getPreferredWidth(), getPreferredHeight(), button_right_edge, 0, 0);
		}
		//g.drawText(label, 5, (int)((getPreferredHeight()-Const.FONT)/2));
		g.drawText(label, 0, (getPreferredHeight()-Const.FONT)/2, DrawStyle.HCENTER, getWidth());
		//g.drawText(label, (getPreferredWidth()/2-label.length()*8/2-4), (int)((getPreferredHeight()-Const.FONT)/2-5));
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
		focus = true;
        invalidate();
        return true;
    }
}