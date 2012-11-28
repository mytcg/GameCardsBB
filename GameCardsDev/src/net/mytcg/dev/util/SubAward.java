package net.mytcg.dev.util;

public class SubAward {
	private int progress = 0;
	private int target = 0;
	private String dateCompleted = "";
	private String completeImage = "";
	
	public SubAward(){
		
	}
	
	public void setProgress(int prog){
		progress = prog;
	}
	public int getProgress(){
		return progress;
	}
	public void setTarget(int tar){
		target = tar;
	}
	public int getTarget(){
		return target;
	}
	public void setDateCompleted(String date){
		dateCompleted = date;
	}
	public String getDateCompleted(){
		return dateCompleted;
	}
	public void setCompleteImage(String image){
		completeImage = image;
	}
	public String getCompleteImage(){
		return completeImage;
	}
}
