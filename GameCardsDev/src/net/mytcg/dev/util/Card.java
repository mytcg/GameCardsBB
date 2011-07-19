package net.mytcg.dev.util;

import java.util.Vector;

import net.rim.device.api.util.Persistable;

public class Card implements Persistable {
	private int id, categoryid = -1;
	private int gameplayercardid = -1;
	private String description = "";
	private int quantity = -1;
	private String thumburl = "";
	private String fronturl = "";
	private String backurl = "";
	private String frontflipurl = "";
	private String backflipurl = "";
	private String note = "";
	private int updated = 0;
	private Vector stats = new Vector();
	
	public Card(int id, String description, int quantity, String thumburl, String fronturl, String backurl, String note, int updated, Vector stats) {
		setId(id);
		setDesc(description);
		setQuantity(quantity);
		setThumburl(thumburl);
		setFronturl(fronturl);
		setBackurl(backurl);
		setNote(note);
		setUpdated(updated);
		setStats(stats);
	}
	
	public boolean equals(Card compare) {
		if (this.id == compare.id) {
			if (this.description.equals(compare.description)) {
				if (this.quantity == compare.quantity) {
					if (this.thumburl.equals(compare.thumburl)) {
						if (this.fronturl.equals(compare.fronturl)) {
							if (this.backurl.equals(compare.backurl)){
								if (this.note.equals(compare.note)) {
									if (this.updated == compare.updated){
										if ((this.stats.size() == compare.stats.size())&&this.stats.size() > 0) {
											boolean cmp = false;
											for (int i = 0; i < this.stats.size(); i++) {
												if (((Stat)(this.stats.elementAt(i))).equals((Stat)(compare.stats.elementAt(i)))) {
													cmp = true;
												}
											}
											return !cmp;
										}
									}
									
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	public void setStats(Vector stats) {
		this.stats = stats;
	}
	public void addStat(Stat stat) {
		stats.addElement(stat);
	}
	public Vector getStats() {
		return stats;
	}
	public int statsCount() {
		return stats.size();
	}
	
	
	public void setId(int id) {
		this.id = id;
	}
	public void setCategoryId(int categoryid) {
		this.categoryid = categoryid;
	}
	public void setGamePlayerCardId(int id) {
		this.gameplayercardid = id;
	}
	public void setDesc(String description) {
		this.description = description;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public void setThumburl(String url) {
		thumburl = url;
	}
	public void setFronturl(String url) {
		fronturl = url;
	}
	public void setBackurl(String url) {
		backurl = url;
	}
	public void setFrontFlipurl(String url) {
		frontflipurl = url;
	}
	public void setBackFlipurl(String url) {
		backflipurl = url;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public void setUpdated(int updated) {
		this.updated = updated;
	}
	
	public int getId() {
		return id;
	}
	public int getCategoryId() {
		return categoryid;
	}
	public int getGamePlayerCardId() {
		return gameplayercardid;
	}
	public String getDesc() {
		return description;
	}
	public int getQuantity() {
		return quantity;
	}
	public String getThumburl() {
		return thumburl;
	}
	public String getFronturl() {
		return fronturl;
	}
	public String getBackurl() {
		return backurl;
	}
	public String getFrontFlipurl() {
		return frontflipurl;
	}
	public String getBackFlipurl() {
		return backflipurl;
	}
	public String getNote() {
		return note;
	}
	public int getUpdated() {
		return updated;
	}
}
