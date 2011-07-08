package net.mytcg.dev.ui;

import net.mytcg.dev.ui.custom.FixedButtonField;
import net.mytcg.dev.ui.custom.ListItemField;
import net.mytcg.dev.ui.custom.ThumbnailField;
import net.mytcg.dev.util.Auction;
import net.mytcg.dev.util.Const;
import net.mytcg.dev.util.SettingsBean;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class AuctionListScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	
	ThumbnailField tmp = null;
	
	boolean update = true;
	boolean purchased = false;
	int id = -1;
	int type = 1;
	
	public void process(String val) {
		SettingsBean _instance = SettingsBean.getSettings();
		System.out.println(val);
		if (update) {
			SettingsBean.saveSettings(_instance);
		}
		
		if ((!(isDisplaying()))||(update)) {
			int fromIndex;
	    	if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
	    		setText(val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex)));
	    	} else if (((fromIndex = val.indexOf(Const.xml_auctionsincategory)) != -1)) {
	    		int auctionid = -1;
	    		int usercardid = -1;
	    		int cardid = -1;
	    		boolean empty = true;
	    		String description = "";
	    		String openingbid = "";
	    		String buynowprice = "";
	    		String price = "";
	    		String username = "";
	    		String endDate = "";
	    		String lastBidUser = "";
	    		String auctionthumb = "";
	    		int endIndex = -1;
	    		String auction = "";
	    		while ((fromIndex = val.indexOf(Const.xml_auctioncardid)) != -1){
	    			endIndex = val.indexOf(Const.xml_auction_end);
	    			auction = val.substring(fromIndex, endIndex+Const.xml_auction_end_length);
	    			fromIndex = auction.indexOf(Const.xml_auctioncardid);
	    			
	    			try {
	    				auctionid = Integer.parseInt(auction.substring(fromIndex+Const.xml_auctioncardid_length, auction.indexOf(Const.xml_auctioncardid_end, fromIndex)));
	    			} catch (Exception e) {
	    				auctionid = -1;
	    			}
	    			try {
	    				usercardid = Integer.parseInt(auction.substring(fromIndex+Const.xml_usercardid_length, auction.indexOf(Const.xml_usercardid_end, fromIndex)));
	    			} catch (Exception e) {
	    				usercardid = -1;
	    			}
	    			try {
	    				cardid = Integer.parseInt(auction.substring(fromIndex+Const.xml_cardid_length, auction.indexOf(Const.xml_cardid_end, fromIndex)));
	    			} catch (Exception e) {
	    				cardid = -1;
	    			}
	    			if ((fromIndex = auction.indexOf(Const.xml_description)) != -1) {
	    				description = auction.substring(fromIndex+Const.xml_description_length, auction.indexOf(Const.xml_description_end, fromIndex));
	    			}
	    			if ((fromIndex = auction.indexOf(Const.xml_openingbid)) != -1) {
	    				openingbid = auction.substring(fromIndex+Const.xml_openingbid_length, auction.indexOf(Const.xml_openingbid_end, fromIndex));
	    			}
	    			if ((fromIndex = auction.indexOf(Const.xml_buynowprice)) != -1) {
	    				buynowprice = auction.substring(fromIndex+Const.xml_buynowprice_length, auction.indexOf(Const.xml_buynowprice_end, fromIndex));
	    			}
	    			if ((fromIndex = auction.indexOf(Const.xml_price)) != -1) {
	    				price = auction.substring(fromIndex+Const.xml_price_length, auction.indexOf(Const.xml_price_end, fromIndex));
	    			}
	    			if ((fromIndex = auction.indexOf(Const.xml_username)) != -1) {
	    				username = auction.substring(fromIndex+Const.xml_username_length, auction.indexOf(Const.xml_username_end, fromIndex));
	    			}
	    			if ((fromIndex = auction.indexOf(Const.xml_endDate)) != -1) {
	    				endDate = auction.substring(fromIndex+Const.xml_endDate_length, auction.indexOf(Const.xml_endDate_end, fromIndex));
	    			}
	    			if ((fromIndex = auction.indexOf(Const.xml_lastBidUser)) != -1) {
	    				lastBidUser = auction.substring(fromIndex+Const.xml_lastBidUser_length, auction.indexOf(Const.xml_lastBidUser_end, fromIndex));
	    			}
	    			if ((fromIndex = auction.indexOf(Const.xml_thumburl)) != -1) {
	    				auctionthumb = auction.substring(fromIndex+Const.xml_thumburl_length, auction.indexOf(Const.xml_thumburl_end, fromIndex));
	    			}
	    			val = val.substring(val.indexOf(Const.xml_auction_end)+Const.xml_auction_end_length);
	    			
	    			synchronized(UiApplication.getEventLock()) {
	    				tmp = new ThumbnailField(new Auction(auctionid, cardid, usercardid, description, auctionthumb, openingbid, buynowprice, price, username, endDate, lastBidUser));
	    				if(!price.equals("")){
	    					tmp.setSecondLabel("Current bid: "+price);
	    				}else{
	    					tmp.setSecondLabel("Opening bid: "+openingbid);
	    				}
	    				tmp.setThirdLabel("Buy now price: "+buynowprice);
	        			tmp.setChangeListener(this);
	        			add(tmp);
	        		}
	    			empty = false;
	    		}
	    		if (empty) {
	    			synchronized(UiApplication.getEventLock()) {
	        			add(new ListItemField("Empty", -1, false, 0));
	        		}
	    		}
	    	}
	    	invalidate();
	    	_instance = null;
	    	setDisplaying(true);
		}		
	}
	public AuctionListScreen(int id, int type) {
		super(null);
		this.id = id;
		this.type = type;
		bgManager.setStatusHeight(exit.getContentHeight());
		
		exit.setChangeListener(this);
		
		addButton(exit);
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		System.out.println("AAA "+Const.categoryauction+"&category_id="+id+Const.height+Const.getCardHeight());
		if(type == 0){
			doConnect(Const.categoryauction+"&category_id="+id+Const.height+Const.getCardHeight());
		}else{
			doConnect(Const.userauction+"&username="+SettingsBean.getSettings().getUsername()+Const.height+Const.getCardHeight());
		}
	}
	
	protected void onExposed() {
		if(purchased){
			UiApplication.getUiApplication().popScreen(this);
		}
	}
	public boolean onClose() {
		screen = null;
		UiApplication.getUiApplication().popScreen(this);
		return true;
	}
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		} else if(f instanceof ThumbnailField){
			ThumbnailField auction = ((ThumbnailField)(f));
			if(type==0){
				screen = new BidOrBuyScreen(this, auction.getAuction());
				UiApplication.getUiApplication().pushScreen(screen);
			}else{
				screen = new AuctionInfoScreen(auction.getAuction(), auction.getThumbnail());
				UiApplication.getUiApplication().pushScreen(screen);
			}
		}
	}
}