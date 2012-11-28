package net.mytcg.dev.ui.custom;

import net.mytcg.dev.util.Const;
import net.rim.device.api.math.Fixed32;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.container.VerticalFieldManager;

public final class SexyEditField extends Manager {
	
	private int managerWidth;
	private int managerHeight;
	private int mW = 8;
	private int mH = 13;
	
	private VerticalFieldManager vfm = new VerticalFieldManager(NO_HORIZONTAL_SCROLL | NO_VERTICAL_SCROLL | NO_VERTICAL_SCROLLBAR | USE_ALL_WIDTH | USE_ALL_HEIGHT);
	private EditField editField;
	
	private Bitmap editbox_left_top;
	private Bitmap editbox_left_middle;
	private Bitmap editbox_left_bottom;
	private Bitmap editbox_right_top;
	private Bitmap editbox_right_middle;
	private Bitmap editbox_right_bottom;
	private Bitmap editbox_middle_top;
	private Bitmap editbox_middle_middle;
	private Bitmap editbox_middle_bottom;
	
	private Bitmap editbox_sel_left_top;
	private Bitmap editbox_sel_left_middle;
	private Bitmap editbox_sel_left_bottom;
	private Bitmap editbox_sel_right_top;
	private Bitmap editbox_sel_right_middle;
	private Bitmap editbox_sel_right_bottom;
	private Bitmap editbox_sel_middle_top;
	private Bitmap editbox_sel_middle_middle;
	private Bitmap editbox_sel_middle_bottom;
	
	private boolean focus;
	private boolean focusable = true;
	private boolean useManagerWidth = false;
	
	protected void drawFocus(Graphics graphics, boolean on) {
        invalidate();
    }
	
	public SexyEditField(int width, int height, long style, int maxchars) {
		super(style | NO_VERTICAL_SCROLL | NO_HORIZONTAL_SCROLL);
		
		editbox_left_top = Const.getEditboxLeftTop();
		editbox_left_middle = Const.getEditboxLeftMiddle();
		editbox_left_bottom = Const.getEditboxLeftBottom();
		editbox_right_top = Const.getEditboxRightTop();
		editbox_right_middle = Const.getEditboxRightMiddle();
		editbox_right_bottom = Const.getEditboxRightBottom();
		editbox_middle_top = Const.getEditboxMiddleTop();
		editbox_middle_middle = Const.getEditboxMiddleMiddle();
		editbox_middle_bottom = Const.getEditboxMiddleBottom();
		
		editbox_sel_left_top = Const.getEditboxSelLeftTop();
		editbox_sel_left_middle = Const.getEditboxSelLeftMiddle();
		editbox_sel_left_bottom = Const.getEditboxSelLeftBottom();
		editbox_sel_right_top = Const.getEditboxSelRightTop();
		editbox_sel_right_middle = Const.getEditboxSelRightMiddle();
		editbox_sel_right_bottom = Const.getEditboxSelRightBottom();
		editbox_sel_middle_top = Const.getEditboxSelMiddleTop();
		editbox_sel_middle_middle = Const.getEditboxSelMiddleMiddle();
		editbox_sel_middle_bottom = Const.getEditboxSelMiddleBottom();
		
		Font _font = getFont();
		_font = _font.derive(Font.PLAIN,Const.FONT);
		setFont(_font);
		managerWidth = width;//Const.getWidth();
		managerHeight = height;//getPreferredHeight();
		editField = new EditField("", "", maxchars, style|FOCUSABLE|EditField.NO_NEWLINE) {
			protected void drawFocus(Graphics g, boolean x) {}
			
			public int getPreferredWidth() {
				//return Const.getWidth()-(Const.PADDING+2*mW);
				return managerWidth;
			}
			protected void layout(int width, int height) {
				width = getPreferredWidth();
		        //height = super.getPreferredHeight();
		        super.layout(width, height);
		        // uses the super class' layout functionality
		        // after the width and the height are set
		        super.setExtent(width, height);
		        // uses the super class' setExtent functionality
		        // after the width and the height are set
			}
		};
		editField.setFont(_font);
		add(vfm);
		vfm.add(editField);
	}
	public SexyEditField(int width, int height) {
		this(width, height, 0L, 140);
	}
	public SexyEditField() {
		this(0,0, 0L, 140);
	}
	public SexyEditField(String s) {
		this(Const.getWidth(),Const.getButtonHeight(), 0L, 140);
		useManagerWidth = true;
		setText(s);
	}
	public SexyEditField(String s, long l) {
		this(0,0, l, 140);
		setText(s);
	}
	
	public SexyEditField(String s, long l, int maxchars) {
		this(Const.getWidth(),Const.getButtonHeight(), l, maxchars);
		useManagerWidth = true;
		setText(s);
	}
	
	public String getText() {
		return editField.getText();
	}
	public void setText(String s){
		editField.setText(s);
		if (s.length() <= 140) {
			editField.setCursorPosition(s.length());
		}
	}
	public void setFocusable(boolean f){
		focusable = f;
	}
	public int getPreferredWidth() {
		if(!useManagerWidth){
			return managerWidth;
		}else{
			Manager tmp = getManager();
			if (tmp != null) {
				managerWidth =  tmp.getPreferredWidth();
			} else {
				managerWidth =  Const.getWidth();
			}
			useManagerWidth = false;
			return managerWidth;
		}
	}

	public boolean isFocusable() {
		return focusable;
	}
	
	protected void onFocus(int direction) {
		System.out.println("onFocus");
		focus = true;
		invalidate();
		super.onFocus(direction);
	}
	
	protected void onUnfocus() {
		focus = false;
		invalidate();
		super.onUnfocus();
	}
	
	protected boolean navigationClick(int status, int time) {
		fieldChangeNotify(1);
		focus = true;
		invalidate();
		return true;
	}
	
	public int getPreferredHeight() {
		return managerHeight;
	}

	public void setEdit(boolean edit){
		editField.setEditable(edit);
		editField.setDirty(true);
	}
	
	protected void sublayout(int w, int h) {
		if (managerWidth == 0) {
			managerWidth = w;
		}
		if (managerHeight == 0) {
			managerHeight = h;
		}
		int actWidth = Math.min(managerWidth, w);
		int actHeight = Math.min(managerHeight, h);
		layoutChild(vfm, actWidth - mW, actHeight - mH); // Leave room for border
		setPositionChild(vfm, mW, mH);	              // again, careful not to stomp over the border
		setExtent(actWidth, actHeight);
	}
	
	public final void paint(Graphics g) {
		g.setColor(Color.BLACK);
		if (!focus) {
			g.setColor(Color.WHITE);
			//fills body block
			int xPts[] = {5,5,getPreferredWidth()-5,getPreferredWidth()-5};
			int yPts[] = {5,getPreferredHeight()-5, getPreferredHeight()-5, 5};
			g.drawTexturedPath(xPts,yPts,null,null,0,0,Fixed32.ONE,0,0,Fixed32.ONE,editbox_middle_middle);

			//fills left middle
			int xPts3[] = {0,0,editbox_left_middle.getWidth(),editbox_left_middle.getWidth()};
			int yPts3[] = {5,getPreferredHeight()-5,getPreferredHeight()-5,5};
			g.drawTexturedPath(xPts3,yPts3,null,null,editbox_left_middle.getWidth(),0,Fixed32.ONE,0,0,Fixed32.ONE,editbox_left_middle);
			
			//fills right middle
			int xPts4[] = {getPreferredWidth()-(editbox_right_middle.getWidth()),getPreferredWidth()-(editbox_right_middle.getWidth()),getPreferredWidth(),getPreferredWidth()};
			int yPts4[] = {5,getPreferredHeight()-5,getPreferredHeight()-5,5};
			g.drawTexturedPath(xPts4,yPts4,null,null,getPreferredWidth()-(editbox_right_middle.getWidth()),0,Fixed32.ONE,0,0,Fixed32.ONE,editbox_right_middle);
			
			//fills top middle
			int xPts1[] = {5,5,getPreferredWidth()-5,getPreferredWidth()-5};
			int yPts1[] = {0,editbox_middle_top.getHeight(), editbox_middle_top.getHeight(), 0};
			g.drawTexturedPath(xPts1,yPts1,null,null,0,editbox_middle_top.getHeight(),Fixed32.ONE,0,0,Fixed32.ONE,editbox_middle_top);
			
			//fills bottom middle
			int xPts2[] = {5,5,getPreferredWidth()-5,getPreferredWidth()-5};
			int yPts2[] = {getPreferredHeight()-(editbox_middle_bottom.getHeight()),getPreferredHeight(), getPreferredHeight(), getPreferredHeight()-(editbox_middle_bottom.getHeight())};
			g.drawTexturedPath(xPts2,yPts2,null,null,0,getPreferredHeight()-(editbox_middle_bottom.getHeight()),Fixed32.ONE,0,0,Fixed32.ONE,editbox_middle_bottom);

			g.drawBitmap(0, 0, getPreferredWidth(), getPreferredHeight(), editbox_left_top, 0, 0);
			g.drawBitmap(0, getPreferredHeight()-(editbox_left_bottom.getHeight()), getPreferredWidth(), getPreferredHeight(), editbox_left_bottom, 0, 0);
			g.drawBitmap(getPreferredWidth()-(editbox_right_top.getWidth()), 0, getPreferredWidth(), getPreferredHeight(), editbox_right_top, 0, 0);
			g.drawBitmap(getPreferredWidth()-(editbox_right_bottom.getWidth()), getPreferredHeight()-(editbox_right_bottom.getHeight()), getPreferredWidth(), getPreferredHeight(), editbox_right_bottom, 0, 0);
		} else {
			//fills body block
			int xPts[] = {5,5,getPreferredWidth()-5,getPreferredWidth()-5};
			int yPts[] = {5,getPreferredHeight()-5, getPreferredHeight()-5, 5};
			g.drawTexturedPath(xPts,yPts,null,null,0,0,Fixed32.ONE,0,0,Fixed32.ONE,editbox_sel_middle_middle);
			
			//fills left middle
			int xPts3[] = {0,0,editbox_sel_left_middle.getWidth(),editbox_sel_left_middle.getWidth()};
			int yPts3[] = {5,getPreferredHeight()-5,getPreferredHeight()-5,5};
			g.drawTexturedPath(xPts3,yPts3,null,null,editbox_sel_left_middle.getWidth(),0,Fixed32.ONE,0,0,Fixed32.ONE,editbox_sel_left_middle);
			
			//fills right middle
			int xPts4[] = {getPreferredWidth()-(editbox_sel_right_middle.getWidth()),getPreferredWidth()-(editbox_sel_right_middle.getWidth()),getPreferredWidth(),getPreferredWidth()};
			int yPts4[] = {5,getPreferredHeight()-5,getPreferredHeight()-5,5};
			g.drawTexturedPath(xPts4,yPts4,null,null,getPreferredWidth()-(editbox_sel_right_middle.getWidth()),0,Fixed32.ONE,0,0,Fixed32.ONE,editbox_sel_right_middle);
			
			//fills top middle
			int xPts1[] = {5,5,getPreferredWidth()-5,getPreferredWidth()-5};
			int yPts1[] = {0,editbox_sel_middle_top.getHeight(), editbox_sel_middle_top.getHeight(), 0};
			g.drawTexturedPath(xPts1,yPts1,null,null,0,editbox_middle_top.getHeight(),Fixed32.ONE,0,0,Fixed32.ONE,editbox_sel_middle_top);
			
			//fills bottom middle
			int xPts2[] = {5,5,getPreferredWidth()-5,getPreferredWidth()-5};
			int yPts2[] = {getPreferredHeight()-(editbox_sel_middle_bottom.getHeight()),getPreferredHeight(), getPreferredHeight(), getPreferredHeight()-(editbox_sel_middle_bottom.getHeight())};
			g.drawTexturedPath(xPts2,yPts2,null,null,0,getPreferredHeight()-(editbox_sel_middle_bottom.getHeight()),Fixed32.ONE,0,0,Fixed32.ONE,editbox_sel_middle_bottom);
			
			g.drawBitmap(0, 0, getPreferredWidth(), getPreferredHeight(), editbox_sel_left_top, 0, 0);
			g.drawBitmap(0, (getPreferredHeight())-editbox_sel_left_bottom.getHeight(), getPreferredWidth(), getPreferredHeight(), editbox_sel_left_bottom, 0, 0);
			g.drawBitmap((getPreferredWidth())-editbox_sel_right_top.getWidth(), 0, getPreferredWidth(), getPreferredHeight(), editbox_sel_right_top, 0, 0);
			g.drawBitmap((getPreferredWidth())-editbox_sel_right_bottom.getWidth(), (getPreferredHeight())-editbox_sel_right_bottom.getHeight(), getPreferredWidth(), getPreferredHeight(), editbox_sel_right_bottom, 0, 0);
		}
		super.paint(g);
	}
	
}
