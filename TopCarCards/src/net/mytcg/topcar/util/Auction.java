package net.mytcg.topcar.util;

import net.rim.device.api.util.Persistable;

public class Auction implements Persistable {
	private int id = -1;
	private int cardid = -1;
	private int usercardid = -1;
	private String description = "";
	private String thumburl = "";
	private String fronturl = "";
	private String backurl = "";
	private String openingbid = "";
	private String buynowprice = "";
	private String username = "";
	private String price = "";
	private String enddate = "";
	private String lastbiduser = "";
	
	public Auction(int id, int cardid, int usercardid, String description, String thumburl, String openingbid, String buynowprice, String price, String username, String enddate, String lastbiduser) {
		setId(id);
		setCardId(cardid);
		setUserCardId(usercardid);
		setDesc(description);
		setThumburl(thumburl);
		setOpeningBid(openingbid);
		setBuyNowPrice(buynowprice);
		setPrice(price);
		setUsername(username);
		setEndDate(enddate);
		setLastBidUser(lastbiduser);
	}
	
	public boolean equals(Auction compare) {
		if (this.id == compare.id) {
			if (this.id == compare.cardid) {
				if (this.id == compare.usercardid) {
					if (this.description.equals(compare.description)) {
						if (this.thumburl.equals(compare.thumburl)) {
							if (this.fronturl.equals(compare.fronturl)) {
								if (this.backurl.equals(compare.backurl)){
									if (this.openingbid.equals(compare.openingbid)) {
										if (this.buynowprice.equals(compare.buynowprice)) {
											if (this.username.equals(compare.username)) {
												if (this.price.equals(compare.price)) {
													if (this.enddate.equals(compare.enddate)) {
														if (this.lastbiduser.equals(compare.lastbiduser)) {
															return true;
														}
													}
												}
											}
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
	
	public void setId(int id) {
		this.id = id;
	}
	public void setCardId(int id) {
		this.cardid = id;
	}
	public void setUserCardId(int id) {
		this.usercardid = id;
	}
	public void setDesc(String description) {
		this.description = description;
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
	public void setOpeningBid(String openingbid) {
		this.openingbid = openingbid;
	}
	public void setBuyNowPrice(String buynowprice) {
		this.buynowprice = buynowprice;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public void setEndDate(String enddate) {
		this.enddate = enddate;
	}
	public void setLastBidUser(String lastbiduser) {
		this.lastbiduser = lastbiduser;
	}
	
	
	public int getId() {
		return id;
	}
	public int getCardId() {
		return cardid;
	}
	public int getUserCardId() {
		return usercardid;
	}
	public String getDesc() {
		return description;
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
	public String getOpeningBid() {
		return openingbid;
	}
	public String getBuyNowPrice() {
		return buynowprice;
	}
	public String getUsername() {
		return username;
	}
	public String getPrice() {
		return price;
	}
	public String getEndDate() {
		return enddate;
	}
	public String getLastBidUser() {
		return lastbiduser;
	}
}
