package net.mytcg.topcar.ui;

import java.util.Date;
import java.util.Vector;

import net.mytcg.topcar.ui.custom.ColorLabelField;
import net.mytcg.topcar.ui.custom.FixedButtonField;
import net.mytcg.topcar.ui.custom.ListItemField;
import net.mytcg.topcar.ui.custom.PageNumberField;
import net.mytcg.topcar.ui.custom.ThumbnailField;
import net.mytcg.topcar.util.Auction;
import net.mytcg.topcar.util.Const;
import net.mytcg.topcar.util.SettingsBean;
import net.rim.device.api.i18n.SimpleDateFormat;
import net.rim.device.api.io.http.HttpDateParser;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class AuctionListScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	ColorLabelField header = new ColorLabelField("");
	PageNumberField pageNumber = new PageNumberField("Page 1/1");
	
	ThumbnailField tmp = null;
	
	boolean update = true;
	boolean purchased = false;
	int id = -1;
	int type = 1;
	Vector pages = new Vector();
	Vector tempList = new Vector();
	int currentPage = 0;
	
	public void process(String val) {
		int listSize = (Const.getUsableHeight()) / Const.getButtonHeight();
		int listCounter = 1;
		pages = new Vector();
		SettingsBean _instance = SettingsBean.getSettings();
		if (update) {
			SettingsBean.saveSettings(_instance);
		}
		
		if ((!(isDisplaying()))||(update)) {
			int fromIndex;
	    	if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
	    		setText(val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex)));
	    	} else if (((fromIndex = val.indexOf(Const.xml_auctionsincategory)) != -1)) {
	    		
	    		if ((fromIndex = val.indexOf(Const.xml_credits)) != -1) {
    				String credits = val.substring(fromIndex+Const.xml_credits_length, val.indexOf(Const.xml_credits_end, fromIndex));
    				_instance = SettingsBean.getSettings();
    				_instance.setCredits(credits);
    				SettingsBean.saveSettings(_instance);
    				synchronized(UiApplication.getEventLock()) {
    					header.setText("Current credits:" + SettingsBean.getSettings().getCredits());
    	    		}
    				
    			}
	    		
	    		synchronized(UiApplication.getEventLock()) {
	    			clear();
	    			header.setText("Current credits:" + SettingsBean.getSettings().getCredits());
	    			add(header);
	    		}
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
	    		String fronturl = "";
	    		String backurl = "";
	    		int endIndex = -1;
	    		String auction = "";
	    		while ((fromIndex = val.indexOf(Const.xml_auctioncardid)) != -1){
	    			if(listCounter >= listSize){
	    				pages.addElement(tempList);
	    				tempList = new Vector();
	    				listCounter=0;
	    			}
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
	    			if ((fromIndex = auction.indexOf(Const.xml_fronturl)) != -1) {
	    				fronturl = auction.substring(fromIndex+Const.xml_fronturl_length, auction.indexOf(Const.xml_fronturl_end, fromIndex));
	    			}
	    			if ((fromIndex = auction.indexOf(Const.xml_backurl)) != -1) {
	    				backurl = auction.substring(fromIndex+Const.xml_backurl_length, auction.indexOf(Const.xml_backurl_end, fromIndex));
	    			}
	    			val = val.substring(val.indexOf(Const.xml_auction_end)+Const.xml_auction_end_length);
	    			
	    			synchronized(UiApplication.getEventLock()) {
	    				Auction tmpAuc = new Auction(auctionid, cardid, usercardid, description, auctionthumb, openingbid, buynowprice, price, username, endDate, lastBidUser);
	    				tmpAuc.setFronturl(fronturl);
	    				tmpAuc.setBackurl(backurl);
	    				tmp = new ThumbnailField(tmpAuc);
	    				if(!price.equals("")){
	    					if (lastBidUser.equals(SettingsBean.getSettings().getUsername())) {
	    						tmp.setSecondLabel("Current bid: "+price+ " (Yours)");
	    					} else {
	    						tmp.setSecondLabel("Current bid: "+price);
	    					}
	    				}else{
	    					tmp.setSecondLabel("Opening bid: "+openingbid);
	    				}
	    					    
	    				long end = new Date(HttpDateParser.parse(endDate)).getTime();
	    				long current = System.currentTimeMillis();
	    				
	    				long diff = end - current;
	    				
	    				Date test = new Date(diff);
	    				
	    				SimpleDateFormat days = new SimpleDateFormat("d");
	    				SimpleDateFormat hours = new SimpleDateFormat("H");
	    				String day = days.format(test);
	    				String hour = hours.format(test);
	    				if (day.equals("1")) {
	    					day = day + " Day ";
	    				} else {
	    					day = day + " Days ";
	    				}
	    				
	    				if (hour.equals("1")) {
	    					hour = hour + " Hour";
	    				} else {
	    					hour = hour + " Hours";
	    				}
	    				
	    				tmp.setThirdLabel(day + hour);
	        			tmp.setChangeListener(this);
	        			tempList.addElement(tmp);
	        			listCounter++;
	        		}
	    			empty = false;
	    		}
	    		if (empty) {
	    			synchronized(UiApplication.getEventLock()) {
	        			add(new ListItemField("Empty", -1, false, 0));
	        		}
	    		}else{
	    			pages.addElement(tempList);
		        	synchronized(UiApplication.getEventLock()) {
		        		System.out.println("SIZE "+((Vector)pages.elementAt(0)).size());
		        		if(pages.size()<=1){
		    				bgManager.setArrowMode(false);
		    			}
		        		pageNumber.setLabel("Page 1/"+pages.size());
		        		Field[] temp = new Field[((Vector)pages.elementAt(0)).size()];
		        		((Vector)pages.elementAt(0)).copyInto(temp);
		        		bgManager.deleteAll();
		    	    	bgManager.addAll(temp);
		    	    }
	    		}
	    	}
	    	invalidate();
	    	_instance = null;
	    	//setDisplaying(true);
		}		
	}
	
	protected boolean navigationMovement(int dx, int dy, int status, int time) {
		if(dy == 0 && dx == -1){
			if(pages.size() >1){
				if((currentPage-1)<0){
					currentPage = pages.size()-1;
				}else{
					currentPage--;
				}
				synchronized(UiApplication.getEventLock()) {
					pageNumber.setLabel("Page "+(currentPage+1)+"/"+pages.size());
					Field[] temp = new Field[((Vector)pages.elementAt(currentPage)).size()];
	    			((Vector)pages.elementAt(currentPage)).copyInto(temp);
	    			bgManager.deleteAll();
		    		bgManager.addAll(temp);
		    	}
			}
			return true;
		}else if(dy == 0 && dx == 1){
			if(pages.size() >1){
				if((currentPage+1)>=pages.size()){
					currentPage = 0;
				}else{
					currentPage++;
				}
				synchronized(UiApplication.getEventLock()) {
					pageNumber.setLabel("Page "+(currentPage+1)+"/"+pages.size());
					Field[] temp = new Field[((Vector)pages.elementAt(currentPage)).size()];
	    			((Vector)pages.elementAt(currentPage)).copyInto(temp);
	    			bgManager.deleteAll();
		    		bgManager.addAll(temp);
		    	}
			}
			return true;
		}else{
			return super.navigationMovement(dx, dy, status, time);
		}
	}
	
	public AuctionListScreen(int id, int type) {
		super(null);
		add(new ColorLabelField(""));
		this.id = id;
		this.type = type;
		bgManager.setStatusHeight(exit.getContentHeight());
		bgManager.setArrowMode(true);
		
		exit.setChangeListener(this);
		
		header.setText("Current credits:" + SettingsBean.getSettings().getCredits());
		tempList.addElement(header);
		
		addButton(new FixedButtonField(""));
		addButton(pageNumber);
		addButton(exit);
		if(type == 0){
			doConnect(Const.categoryauction+"&category_id="+id+Const.height+Const.getCardHeight()+Const.jpg+Const.bbheight+Const.getAppHeight()+Const.width+Const.getCardWidth());
		}else{
			doConnect(Const.userauction+"&username="+SettingsBean.getSettings().getUsername()+Const.height+Const.getCardHeight()+Const.jpg+Const.bbheight+Const.getAppHeight()+Const.width+Const.getCardWidth());
		}
	}
	
	protected void onExposed() {
		if (!isVisible()) {
			if(type == 0){
				doConnect(Const.categoryauction+"&category_id="+id+Const.height+Const.getCardHeight()+Const.jpg+Const.bbheight+Const.getAppHeight()+Const.width+Const.getCardWidth());
			}
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
				//screen = new BidOrBuyScreen(this, auction.getAuction());
				screen = new AuctionInfoScreen(auction.getAuction(), auction.getThumbnail(), true);
				UiApplication.getUiApplication().pushScreen(screen);
			}else{
				screen = new AuctionInfoScreen(auction.getAuction(), auction.getThumbnail());
				UiApplication.getUiApplication().pushScreen(screen);
			}
		}
	}
}
