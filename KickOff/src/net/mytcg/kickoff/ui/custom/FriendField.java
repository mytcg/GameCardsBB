package net.mytcg.kickoff.ui.custom;

import net.mytcg.kickoff.util.Const;
import net.rim.device.api.math.Fixed32;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;

public final class FriendField extends Field {
	String label1 = "";
	String label2 = "";
	String label3 = "";
	boolean focus = false;
	private Bitmap button_centre;
	public FriendField(String label1, String label2,String label3) {
		this.label1 = label1;
		this.label2 = label2;
		this.label3 = label3;
		construct();
	}
	
	public void construct() {
		button_centre = Const.getThumbCentre();
		int font = Const.FONT;
		
		Font _font = getFont();
		_font = _font.derive(Const.TYPE,font);
		setFont(_font);
	}
	
	public boolean isFocusable() {
    	return true;
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
		return Const.getWidth()-60;
	}
	public int getPreferredHeight() {
		return button_centre.getHeight();
	}
	protected void layout(int width, int height) {
		setExtent(getPreferredWidth(),getPreferredHeight());
    }

	public void paint(Graphics g) {
		int _xPts[] = {0,0,getPreferredWidth(),getPreferredWidth()};
		int _yPts[] = {0,getPreferredHeight(),getPreferredHeight(),0};
		//g.setColor(3947580);
		g.setColor(16777215);
		g.drawFilledPath(_xPts, _yPts, null, null);
		g.drawTexturedPath(_xPts,_yPts,null,null,0,0,Fixed32.ONE,0,0,Fixed32.ONE,button_centre);
		
		g.setColor(Const.FONTCOLOR);
		
		if (focus) {
			g.setColor(Const.SELECTEDCOLOR);
		}
		
		Font _font = getFont();
		_font = _font.derive(Const.TYPE,Const.FONT+2);
		g.setFont(_font);
		
		g.drawText(label1, 5, 0);
		
		g.drawText(label2, 5, Const.FONT+4);
		
		g.drawText(label3, 5, (Const.FONT*2)+8);

		_font = _font.derive(Font.PLAIN,Const.FONT);
		g.setFont(_font);
	}

	protected boolean navigationClick(int status, int time) {
        fieldChangeNotify(1);
        return true;
    }
}