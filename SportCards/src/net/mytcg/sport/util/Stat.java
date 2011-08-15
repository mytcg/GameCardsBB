package net.mytcg.sport.util;

import net.rim.device.api.util.Persistable;

public class Stat implements Persistable {
	private String description = "";
	private String value = "";
	private String stattype = "";
	private int top, left, width, height, frontOrBack, colorRed, colorGreen, colorBlue, cardstatid, categorystatid;
	private double val;
	
	public Stat(String description, String value, double val, int top, int left, int width, int height, int frontOrBack, int colorRed, int colorGreen, int colorBlue) {
		setDesc(description);
		setValue(value);
		setVal(val);
		setTop(top);
		setLeft(left);
		setWidth(width);
		setHeight(height);
		setFrontOrBack(frontOrBack);
		setColorRed(colorRed);
		setColorGreen(colorGreen);
		setColorBlue(colorBlue);
	}
	
	public boolean equals(Stat compare) {
		if (this.description.equals(compare.description)) {
			if (this.value.equals(compare.value)) {
				if (this.val == compare.val) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void setDesc(String description) {
		this.description = description;
	}
	public void setStatType(String type) {
		this.stattype = type;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void setStatId(int id) {
		this.cardstatid = id;
	}
	public void setCategoryStatId(int id) {
		this.categorystatid = id;
	}
	public void setVal(double val) {
		this.val = val;
	}
	public void setTop(int top) {
		this.top = top;
	}
	public void setLeft(int left) {
		this.left = left;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public void setFrontOrBack(int frontOrBack) {
		this.frontOrBack = frontOrBack;
	}
	public void setColorRed(int colorRed) {
		this.colorRed = colorRed;
	}
	public void setColorGreen(int colorGreen) {
		this.colorGreen = colorGreen;
	}
	public void setColorBlue(int colorBlue) {
		this.colorBlue = colorBlue;
	}
	
	public String getDesc() {
		return description;
	}
	public String getStatType() {
		return stattype;
	}
	public String getValue() {
		return value;
	}
	public int getStatId() {
		return cardstatid;
	}
	public int getCategoryStatId() {
		return categorystatid;
	}
	public double getVal() {
		return val;
	}
	public int getTop() {
		return top;
	}
	public int getLeft() {
		return left;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int getFrontOrBack() {
		return frontOrBack;
	}
	public int getColorRed() {
		return colorRed;
	}
	public int getColorGreen() {
		return colorGreen;
	}
	public int getColorBlue() {
		return colorBlue;
	}
}
