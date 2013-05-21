package net.mytcg.kickoff.util;

import java.util.Vector;

public class Album {
	private int id = -1;
	private String name = "";
	private boolean hascards = false;
	private Vector subalbums = new Vector();
	
	public Album(int id, String name, boolean hascards) {
		setId(id);
		setName(name);
		hasCards(hascards);
	}
	public void setAlbums(Vector al) {
		subalbums = al;
	}
	public void addAlbum(Album sub) {
		subalbums.addElement(sub);
	}
	public Vector getAlbum() {
		return subalbums;
	}
	public int subCount() {
		return subalbums.size();
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void hasCards(boolean hascards) {
		this.hascards = hascards;
	}
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public boolean hasCards() {
		return hascards;
	}
}
