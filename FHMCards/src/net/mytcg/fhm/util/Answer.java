package net.mytcg.fhm.util;

import net.mytcg.fhm.ui.custom.SexyEditField;
import net.rim.device.api.ui.component.CheckboxField;

public class Answer {
	String answerid = "";
	String detailid = "";
	String desc = "";
	String answer = "";
	int answered = -1;
	String creditvalue = "";
	SexyEditField editbox=null;
	CheckboxField checkbox= null;
	
	public Answer(){
		
	}
	
	public String getAnswerId() {
		return answerid;
	}
	public void setAnswerId(String ansid) {
		answerid = ansid;
	}

	public String getDesc() {
		return desc;
	}
	public void setDesc(String des) {
		desc = des;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answe) {
		answer = answe;
	}

	public int getAnswered() {
		return answered;
	}

	public void setAnswered(int answere) {
		answered = answere;
	}

	public String getCreditValue() {
		return creditvalue;
	}

	public void setCreditValue(String creditvalu) {
		creditvalue = creditvalu;
	}

	public SexyEditField getEditBoxPointer() {
		return editbox;
	}

	public void setEditBoxPointer(SexyEditField editbo) {
		editbox = editbo;
	}

	public CheckboxField getCheckBoxPointer() {
		return checkbox;
	}

	public void setCheckBoxPointer(CheckboxField checkbo) {
		checkbox = checkbo;
	}

}


