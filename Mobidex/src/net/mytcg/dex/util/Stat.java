package net.mytcg.dex.util;

import net.rim.device.api.util.Persistable;

public class Stat implements Persistable {
	private String description = "";
	private String value = "";
	private int val;
	
	public Stat(String description, String value, int val) {
		setDesc(description);
		setValue(value);
		setVal(val);
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
	public void setValue(String value) {
		this.value = value;
	}
	public void setVal(int val) {
		this.val = val;
	}
	
	public String getDesc() {
		return description;
	}
	public String getValue() {
		return value;
	}
	public int getVal() {
		return val;
	}
}
