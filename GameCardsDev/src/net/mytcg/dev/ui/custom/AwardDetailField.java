package net.mytcg.dev.ui.custom;

import net.mytcg.dev.util.Const;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;

public final class AwardDetailField extends Field {	
	String name = "";
	String description = "";
	String progress = "";
	boolean badges = false;
	public AwardDetailField(String name, String description, String progress, boolean badges) {
		this.name = name;
		this.description = description;
		this.progress = progress;
		this.badges = badges;
		construct();
	}
	
	protected void drawFocus(Graphics graphics, boolean on) {
    }
	
	public void construct() {
		invalidate();
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
		return 110;
	}
	protected void layout(int width, int height) {
		setExtent(getPreferredWidth(),getPreferredHeight());
    }
	public boolean isFocusable() {
    	return false;
    }
	public void paint(Graphics g) {
		
		g.setColor(Const.FONTCOLOR);
		
		Font _font = getFont();
		_font = _font.derive(Const.TYPE,Const.FONT+4);
		g.setFont(_font);
		
		g.drawText(name, (Const.getWidth()-name.length()*9)/2, 0);
		
		g.drawText(description, (Const.getWidth()-description.length()*8)/2, 25);
		
		g.drawText(progress, (Const.getWidth()-progress.length()*8)/2, 45);
		if(badges){
			g.drawText("Badges Earned", (Const.getWidth()-13*9)/2, 70);
		}
	}

	protected boolean navigationClick(int status, int time) {
        fieldChangeNotify(1);
        return true;
    }
}
