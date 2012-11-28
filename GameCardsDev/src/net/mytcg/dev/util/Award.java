package net.mytcg.dev.util;

import java.util.Vector;

import net.rim.device.api.util.Persistable;
import net.mytcg.dev.util.SubAward;

public class Award implements Persistable{
	private String name = "";
	private String description = "";
	private String incompleteImage = "";
	private Vector subawards = new Vector();
	public Award(){
		
	}
	
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	public void setDescription(String desc){
		this.description = desc;
	}
	public String getDescription(){
		return description;
	}
	public void setIncompleteImage(String name){
		this.incompleteImage = name;
	}
	public String getIncompleteImage(){
		return incompleteImage;
	}
	public void addSubAward(SubAward sub){
		subawards.addElement(sub);
	}
	public SubAward getSubAward(int c){
		return (SubAward)subawards.elementAt(c);
	}
	public Vector getSubAwards(){
		return subawards;
	}
}
