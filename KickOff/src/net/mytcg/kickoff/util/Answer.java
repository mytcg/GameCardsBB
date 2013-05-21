package net.mytcg.kickoff.util;

import net.mytcg.kickoff.ui.custom.CustomCheckboxField;
import net.mytcg.kickoff.ui.custom.SexyEditField;

public class Answer {
	String answerid = "";
	String detailid = "";
	String desc = "";
	String answer = "";
	int answered = -1;
	String creditvalue = "";
	SexyEditField editbox=null;
	CustomCheckboxField checkbox= null;
	
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

	public CustomCheckboxField getCheckBoxPointer() {
		return checkbox;
	}

	public void setCheckBoxPointer(CustomCheckboxField checkbo) {
		checkbox = checkbo;
	}

}


