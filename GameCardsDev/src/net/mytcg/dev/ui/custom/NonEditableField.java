package net.mytcg.dev.ui.custom;

import net.mytcg.dev.util.Const;
import net.rim.device.api.math.Fixed32;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.container.VerticalFieldManager;

public final class NonEditableField extends Manager {
	
	private int managerWidth;
	private int managerHeight;
	private int mW = 8;
	private int mH = 13;
	
	private VerticalFieldManager vfm = new VerticalFieldManager(HORIZONTAL_SCROLL | NO_VERTICAL_SCROLL | NO_VERTICAL_SCROLLBAR | USE_ALL_WIDTH | USE_ALL_HEIGHT);
	private EditField editField;
	
	private Bitmap text_middle_top;
	private Bitmap text_middle;
	private Bitmap text_middle_bottom;
	private Bitmap text_left_top;
	private Bitmap text_left_middle;
	private Bitmap text_left_bottom;
	private Bitmap text_right_top;
	private Bitmap text_right_middle;
	private Bitmap text_right_bottom;
	
	public NonEditableField(String label) {
		super(NO_VERTICAL_SCROLL | NO_HORIZONTAL_SCROLL);
		construct(label);
	}
	
	protected void drawFocus(Graphics g, boolean x) {
		
	}
	
	public void construct(String label) {
		
		text_middle_top = Const.getTextMiddleTop();
		text_middle = Const.getTextMiddle();
		text_middle_bottom = Const.getTextMiddleBottom();
		text_left_top = Const.getTextLeftTop();
		text_left_middle = Const.getTextLeftMiddle();
		text_left_bottom = Const.getTextLeftBottom();
		text_right_top = Const.getTextRightTop();
		text_right_middle = Const.getTextRightMiddle();
		text_right_bottom = Const.getTextRightBottom();
		
		Font theFont = getFont();
		editField = new EditField("", "", 140, FOCUSABLE|EditField.NO_NEWLINE) {
			protected void drawFocus(Graphics g, boolean x) {}
			
			public int getPreferredWidth() {
				return Const.getWidth()-(Const.PADDING+2*mW);
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
		editField.setText(label);
		editField.setFont(theFont);
		managerWidth = Const.getWidth();
		managerHeight = getPreferredHeight();
		add(vfm);
		vfm.add(editField);
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

	public int getPreferredWidth() {
		return managerWidth;
	}

	public boolean isFocusable() {
		return true;
	}
	
	protected boolean navigationClick(int status, int time) {
		fieldChangeNotify(1);
		return true;
	}
	
	public int getPreferredHeight() {
		int height = (Font.getDefault().getHeight() * 8);
		if (height < Const.getUsableHeight()) {
			return height;
		}
		return Const.getUsableHeight();
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

	public void paint(Graphics g) {
		int _xPts[] = {0,0,getPreferredWidth(),getPreferredWidth()};
		int _yPts[] = {0,Const.getHeight(),Const.getHeight(),0};
		g.setColor(3947580);
		g.drawFilledPath(_xPts, _yPts, null, null);
		//g.drawTexturedPath(_xPts,_yPts,null,null,0,0,Fixed32.ONE,0,0,Fixed32.ONE,Const.getGrey());
		
		int xPts[] = {5,5,getPreferredWidth()-5,getPreferredWidth()-5};
		int yPts[] = {5,getPreferredHeight()-5, getPreferredHeight()-5, 5};
		g.drawTexturedPath(xPts,yPts,null,null,0,0,Fixed32.ONE,0,0,Fixed32.ONE,text_middle);

		//fills left middle
		int xPts3[] = {5,5,5+text_left_middle.getWidth(),5+text_left_middle.getWidth()};
		int yPts3[] = {5,getPreferredHeight()-5,getPreferredHeight()-5,5};
		g.drawTexturedPath(xPts3,yPts3,null,null,5+text_left_middle.getWidth(),0,Fixed32.ONE,0,0,Fixed32.ONE,text_left_middle);
		
		//fills right middle
		int xPts4[] = {getPreferredWidth()-(5+text_right_middle.getWidth()),getPreferredWidth()-(5+text_right_middle.getWidth()),getPreferredWidth()-5,getPreferredWidth()-5};
		int yPts4[] = {5,getPreferredHeight()-5,getPreferredHeight()-5,5};
		g.drawTexturedPath(xPts4,yPts4,null,null,getPreferredWidth()-(5+text_right_middle.getWidth()),0,Fixed32.ONE,0,0,Fixed32.ONE,text_right_middle);
		
		//fills top middle
		int xPts1[] = {5,5,getPreferredWidth()-5,getPreferredWidth()-5};
		int yPts1[] = {5,5+text_middle_top.getHeight(), 5+text_middle_top.getHeight(), 5};
		g.drawTexturedPath(xPts1,yPts1,null,null,0,5+text_middle_top.getHeight(),Fixed32.ONE,0,0,Fixed32.ONE,text_middle_top);
		
		//fills bottom middle
		int xPts2[] = {5,5,getPreferredWidth()-5,getPreferredWidth()-5};
		int yPts2[] = {getPreferredHeight()-(5+text_middle_bottom.getHeight()),getPreferredHeight()-5, getPreferredHeight()-5, getPreferredHeight()-(5+text_middle_bottom.getHeight())};
		g.drawTexturedPath(xPts2,yPts2,null,null,0,getPreferredHeight()-(5+text_middle_bottom.getHeight()),Fixed32.ONE,0,0,Fixed32.ONE,text_middle_bottom);

		g.drawBitmap(5, 5, getPreferredWidth(), getPreferredHeight(), text_left_top, 0, 0);
		g.drawBitmap(5, getPreferredHeight()-(5+text_left_bottom.getHeight()), getPreferredWidth(), getPreferredHeight(), text_left_bottom, 0, 0);
		g.drawBitmap(getPreferredWidth()-(5+text_right_top.getWidth()), 5, getPreferredWidth(), getPreferredHeight(), text_right_top, 0, 0);
		g.drawBitmap(getPreferredWidth()-(5+text_right_bottom.getWidth()), getPreferredHeight()-(5+text_right_bottom.getHeight()), getPreferredWidth(), getPreferredHeight(), text_right_bottom, 0, 0);
		
		super.paint(g);
	}
}