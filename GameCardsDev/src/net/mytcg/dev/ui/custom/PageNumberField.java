package net.mytcg.dev.ui.custom;

import net.mytcg.dev.util.Const;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;

public final class PageNumberField extends Field {
	private String label;

	public PageNumberField(String label) {
		construct(Const.FONT, label);
	}
	protected void drawFocus(Graphics g, boolean x) {
		
	}
	public void construct(int font, String label) {
		Font _font = getFont();
		_font = _font.derive(Const.TYPE,font);
		setFont(_font);
		this.label = label;
	}
	public void setLabel(String label) {
		this.label = label;
		invalidate();
	}
	public String getLabel() {
		return label;
	}
	
	public int getPreferredWidth() {
		return Const.getWidth()/3;
	}
	public int getPreferredHeight() {
		return Const.getButtonSelCentre().getHeight();
	}
	protected void layout(int width, int height) {
		setExtent(getPreferredWidth(),getPreferredHeight());
    }
	public boolean isFocusable() {
    	return false;
    }
	public void paint(Graphics g) {
		g.setColor(Const.FONTCOLOR);
		
		g.drawText(label, 0, (getPreferredHeight()-20)/2, DrawStyle.HCENTER, getWidth());
	}
}