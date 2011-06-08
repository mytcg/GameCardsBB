package net.mytcg.dev.ui.custom;

import net.mytcg.dev.util.Const;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.component.LabelField;

public final class ColorLabelField extends LabelField {
	
	private int _currentColor;
	private int _normalColor;
	private int _changeColor;
	private int _backColor;
	private int _width;

	public ColorLabelField(String text) {
		super(text);
		construct(Const.FONT, 0, Font.PLAIN, Const.FONTCOLOR, Const.BACKCOLOR, Const.BACKCOLOR);
	}
	
	public ColorLabelField(String text, long style) {
		super(text, style);
		construct(Const.FONT, 0, Font.PLAIN, Const.FONTCOLOR, Const.BACKCOLOR, Const.BACKCOLOR);
	}
	public ColorLabelField(String text, long style, int font) {
		super(text, style);
		construct(font, 0, Font.PLAIN, Const.FONTCOLOR, Const.BACKCOLOR, Const.BACKCOLOR);
	}
	public ColorLabelField(String text, int width, long style) {
		super(text, style);
		construct(Const.FONT, width, Font.PLAIN, Const.FONTCOLOR, Const.BACKCOLOR, Const.BACKCOLOR);
	}
	public ColorLabelField(String text, int fontsize, long style, int color) {
		super(text, style);
		construct(fontsize, 0, Font.PLAIN, color, Const.BACKCOLOR, Const.BACKCOLOR);
	}
	public ColorLabelField(String text, int fontsize, long style, int color, int width) {
		super(text, style);
		construct(fontsize, width, Font.PLAIN, color, Const.BACKCOLOR, Const.BACKCOLOR);
	}
	public ColorLabelField(String text, int color, int font, int fontsize, long style) {
		super(text, style);
		construct(fontsize, 0, font, color, Const.BACKCOLOR, Const.BACKCOLOR);
	}
	public ColorLabelField(String text, int color, int changeColor, int backColor, int font, int fontsize, long style) {
		super(text, style);
		construct(fontsize, 0, font, color, changeColor, backColor);
	}
	public ColorLabelField(String text, int color, int changeColor, int backColor, int font, int fontsize, int width, long style) {
		super(text,style);
		construct(fontsize, width, font, color, changeColor, backColor);
	}
	
	private void construct(int fontsize, int width, int fontStyle, int color, int changeColor, int backColor) {
		_normalColor = color;
		_changeColor = changeColor;
		_currentColor = backColor;
		_backColor = backColor;
		_width = width;
		Font _font = getFont();
		_font = _font.derive(fontStyle, fontsize);
		setFont(_font);
	}
	
	public int getPreferredWidth() {
		return _width == 0 ? super.getPreferredWidth() : _width;
	}
	public int getPreferredHeight() {
		return super.getPreferredHeight()+5;
	}
	
	public void paint(Graphics g) {
		g.setColor(_currentColor);
		g.fillRect(-1, -1, getPreferredWidth()+2, getPreferredHeight()+2);
		g.setColor(_normalColor);
		super.paint(g);
	}
	
	protected void onFocus(int direction) {
		_currentColor = _changeColor;
		invalidate();
	}
	protected void onUnfocus() {
		_currentColor = _backColor;
		invalidate();
	}
	protected void drawFocus(Graphics graphics, boolean on) {
        //Do nothing
    }
	protected boolean navigationClick(int status, int time) {
        fieldChangeNotify(1);
        return true;
    }
}