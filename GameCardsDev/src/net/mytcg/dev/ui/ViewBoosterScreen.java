package net.mytcg.dev.ui;

import java.util.Vector;

import net.mytcg.dev.ui.custom.ColorLabelField;
import net.mytcg.dev.ui.custom.FixedButtonField;
import net.mytcg.dev.ui.custom.ListItemField;
import net.mytcg.dev.ui.custom.PageNumberField;
import net.mytcg.dev.ui.custom.ThumbnailField;
import net.mytcg.dev.util.Card;
import net.mytcg.dev.util.Const;
import net.mytcg.dev.util.SettingsBean;
import net.mytcg.dev.util.Stat;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
//import net.rim.device.api.ui.TouchEvent;
import net.rim.device.api.ui.UiApplication;

public class ViewBoosterScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	PageNumberField pageNumber = new PageNumberField("Page 1/1");
	
	ListItemField addcard = new ListItemField("Empty", -1, false, 0);
	ListItemField deletedeck = new ListItemField("Empty", -1, false, 0);
	ThumbnailField tmp = new ThumbnailField(new Card(-1, "", 0, "", "", "", "", 0, null,-1, "", ""));
	String cards = null;
	int boosterid = -1;
	boolean update = true;
	Vector pages = new Vector();
	int currentPage = 0;

	public ViewBoosterScreen(int boosterid)
	{
		super(null);
		this.boosterid = boosterid;
		bgManager.setStatusHeight(exit.getContentHeight());
		bgManager.setArrowMode(true);
		
		exit.setChangeListener(this); 
		add(new ColorLabelField(""));
		addButton(new FixedButtonField(""));
		addButton(pageNumber);
		addButton(exit);
		
		doConnect(Const.cardsinbooster+boosterid+Const.height+Const.getCardHeight()+Const.bbheight+Const.getAppHeight()+Const.width+Const.getCardWidth()+Const.jpg);
	}
	
	public void process(String val) {
		System.out.println("gg "+val);
		int listSize = (Const.getUsableHeight()) / 74;
		int listCounter = 0;
		pages = new Vector();
		Vector tempList = new Vector();
		SettingsBean _instance = SettingsBean.getSettings();
    	update = _instance.setCards(val, -1);
		
    	if (update) {
    		SettingsBean.saveSettings(_instance);
    	}
    	String card = "";
    	int fromIndex;
    	int endIndex;
    	boolean empty = true;
    	if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
    		setText(val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex)));
    	} else if (((fromIndex = val.indexOf(Const.xml_cardsincategory)) != -1)) {
    		int cardid = -1;
    		String description = "";
    		String quality = "";
    		int quantity = -1;
    		String thumburl = "";
    		String fronturl = "";
    		String backurl = "";
    		String note = "";
    		String value = "";
    		int rating = -1;
    		int updated = 0;
    		Vector stats = null;
    		String statdesc = "";
    		int statival = -1;
    		int stattop = 0;
    		int statleft = 0;
    		int statwidth = 0;
    		int statheight = 0;
    		int statfrontorback = 0;
    		int statcolorred = 0;
    		int statcolorgreen = 0;
    		int statcolorblue = 0;
    		String statval = "";
    		while ((fromIndex = val.indexOf(Const.xml_cardid)) != -1){
    			if(listCounter >= listSize){
    				pages.addElement(tempList);
    				tempList = new Vector();
    				listCounter=0;
    			}
    			endIndex = val.indexOf(Const.xml_card_end);
    			card = val.substring(fromIndex, endIndex+Const.xml_card_end_length);
    			fromIndex = card.indexOf(Const.xml_cardid);

    			cardid = -1;
    			description = "";
    			quality = "";
    			quantity = -1;
    			thumburl = "";
    			fronturl = "";
    			backurl = "";
    			rating = -1;
    			note = "";
    			value = "";
    			updated = 0;
    			stats = new Vector();
    			try {
    				cardid = Integer.parseInt(card.substring(fromIndex+Const.xml_cardid_length, card.indexOf(Const.xml_cardid_end, fromIndex)));
    			} catch (Exception e) {
    				cardid = -1;
    			}
    			if ((fromIndex = card.indexOf(Const.xml_description)) != -1) {
    				description = card.substring(fromIndex+Const.xml_description_length, card.indexOf(Const.xml_description_end, fromIndex));
    			}
    			if ((fromIndex = card.indexOf(Const.xml_quality)) != -1) {
    				quality = card.substring(fromIndex+Const.xml_quality_length, card.indexOf(Const.xml_quality_end, fromIndex));
    			}
    			if ((fromIndex = card.indexOf(Const.xml_value)) != -1) {
    				value = card.substring(fromIndex+Const.xml_value_length, card.indexOf(Const.xml_value_end, fromIndex));
    			}
    			if ((fromIndex = card.indexOf(Const.xml_quantity)) != -1) {
    				try {
    					quantity = Integer.parseInt(card.substring(fromIndex+Const.xml_quantity_length, card.indexOf(Const.xml_quantity_end, fromIndex)));
    				} catch (Exception e) {
    					quantity = 0;
    				}
    			}
    			if ((fromIndex = card.indexOf(Const.xml_thumburl)) != -1) {
    				thumburl = card.substring(fromIndex+Const.xml_thumburl_length, card.indexOf(Const.xml_thumburl_end, fromIndex));
    			}
    			if ((fromIndex = card.indexOf(Const.xml_fronturl)) != -1) {
    				fronturl = card.substring(fromIndex+Const.xml_fronturl_length, card.indexOf(Const.xml_fronturl_end, fromIndex));
    			}
    			if ((fromIndex = card.indexOf(Const.xml_backurl)) != -1) {
    				backurl = card.substring(fromIndex+Const.xml_backurl_length, card.indexOf(Const.xml_backurl_end, fromIndex));
    			}
    			if ((fromIndex = card.indexOf(Const.xml_note)) != -1) {
    				note = card.substring(fromIndex+Const.xml_note_length, card.indexOf(Const.xml_note_end, fromIndex));
    			}
    			if ((fromIndex = card.indexOf(Const.xml_rating)) != -1) {
    				try {
    					rating = Integer.parseInt(card.substring(fromIndex+Const.xml_rating_length, card.indexOf(Const.xml_rating_end, fromIndex)));
    				} catch (Exception e) {
        				rating = -1;
        			}
    			}
    			if ((fromIndex = card.indexOf(Const.xml_updated)) != -1) {
    				try {
    					updated = Integer.parseInt(card.substring(fromIndex+Const.xml_updated_length, card.indexOf(Const.xml_updated_end, fromIndex)));
    				} catch (Exception e) {
    					updated = 0;
    				}
    			}
    			if ((fromIndex = card.indexOf(Const.xml_stats)) != -1){
    				while ((fromIndex = card.indexOf(Const.xml_stat)) != -1){
    					statdesc = "";
    					statival = -1;
    					statval = "";
    					if ((fromIndex = card.indexOf(Const.xml_desc)) != -1) {
    						statdesc = card.substring(fromIndex+Const.xml_desc_length, card.indexOf(Const.xml_end, fromIndex+Const.xml_desc_length));
    					}
    					if ((fromIndex = card.indexOf(Const.xml_ival)) != -1) {
    						try {
    							statival = Integer.parseInt(card.substring(fromIndex+Const.xml_ival_length, card.indexOf(Const.xml_end, fromIndex+Const.xml_ival_length)));
    						} catch (Exception e) {
    							statival = -1;
    						}
    					}
    					if ((fromIndex = card.indexOf(Const.xml_val)) != -1) {
    						statval = card.substring(fromIndex+Const.xml_val_length, card.indexOf(Const.xml_stat_end, fromIndex));
    					}
    					if((fromIndex = card.indexOf(Const.xml_top)) != -1){
    						try{
    							stattop = Integer.parseInt(card.substring(fromIndex+Const.xml_top_length, card.indexOf(Const.xml_end, fromIndex+Const.xml_top_length)));
    						} catch(Exception e){

    						}
    					}
    					if((fromIndex = card.indexOf(Const.xml_left)) != -1){
    						try{
    							statleft = Integer.parseInt(card.substring(fromIndex+Const.xml_left_length, card.indexOf(Const.xml_end, fromIndex+Const.xml_left_length)));
    						} catch(Exception e){

    						}
    					}
    					if((fromIndex = card.indexOf(Const.xml_width)) != -1){
    						try{
    							statwidth = Integer.parseInt(card.substring(fromIndex+Const.xml_width_length, card.indexOf(Const.xml_end, fromIndex+Const.xml_width_length)));
    						} catch(Exception e){

    						}
    					}
    					if((fromIndex = card.indexOf(Const.xml_height)) != -1){
    						try{
    							statheight = Integer.parseInt(card.substring(fromIndex+Const.xml_height_length, card.indexOf(Const.xml_end, fromIndex+Const.xml_height_length)));
    						} catch(Exception e){

    						}
    					}
    					if((fromIndex = card.indexOf(Const.xml_frontorback)) != -1){
    						try{
    							statfrontorback = Integer.parseInt(card.substring(fromIndex+Const.xml_frontorback_length, card.indexOf(Const.xml_end, fromIndex+Const.xml_frontorback_length)));
    						} catch(Exception e){

    						}
    					}
    					if((fromIndex = card.indexOf(Const.xml_red)) != -1){
    						try{
    							statcolorred = Integer.parseInt(card.substring(fromIndex+Const.xml_red_length, card.indexOf(Const.xml_end, fromIndex+Const.xml_red_length)));
    						} catch(Exception e){

    						}
    					}
    					if((fromIndex = card.indexOf(Const.xml_green)) != -1){
    						try{
    							statcolorgreen = Integer.parseInt(card.substring(fromIndex+Const.xml_green_length, card.indexOf(Const.xml_end, fromIndex+Const.xml_green_length)));
    						} catch(Exception e){

    						}
    					}
    					if((fromIndex = card.indexOf(Const.xml_blue)) != -1){
    						try{
    							statcolorblue = Integer.parseInt(card.substring(fromIndex+Const.xml_blue_length, card.indexOf(Const.xml_end, fromIndex+Const.xml_blue_length)));
    						} catch(Exception e){

    						}
    					}
    					stats.addElement(new Stat(statdesc, statval, statival, stattop, statleft, statwidth, statheight, statfrontorback, statcolorred, statcolorgreen, statcolorblue));
    					card = card.substring(card.indexOf(Const.xml_stat_end)+Const.xml_stat_end_length);
    				}
    			}
    			Card cardobject = new Card(cardid, description, quantity, thumburl, fronturl, backurl, note, updated, stats, rating, quality, value);
    			_instance.setImages(cardid, cardobject);

    			val = val.substring(val.indexOf(Const.xml_card_end)+Const.xml_card_end_length);

    			empty = false;
    			synchronized(UiApplication.getEventLock()) {
    				tmp = new ThumbnailField(_instance.getImages(cardid), false, true);
    				if(!quality.equals("")){
    					tmp.setSecondLabel(""+ quality);
    				}
    				if(rating != -1){
    					tmp.setThirdLabel("Rating: "+ rating);
    				}
        			tmp.setChangeListener(this);
        			tempList.addElement(tmp);
        			listCounter++;
    			}
    		}
    		if (empty) {
    			synchronized(UiApplication.getEventLock()) {
    				add(new ListItemField("Empty", -1, false, 0));
    			}
    		}else{
    			pages.addElement(tempList);
    		}
    		synchronized(UiApplication.getEventLock()) {
    			System.out.println("SIZE "+((Vector)pages.elementAt(0)).size());
    			if(pages.size()<=1){
    				bgManager.setArrowMode(false);
    			}
    			pageNumber.setLabel("Page 1/"+pages.size());
    			ThumbnailField[] temp = new ThumbnailField[((Vector)pages.elementAt(0)).size()];
    			((Vector)pages.elementAt(0)).copyInto(temp);
    			bgManager.deleteAll();
    			bgManager.addAll(temp);
	    	}
    		SettingsBean.saveSettings(_instance);
    		_instance = null;
    	}
    	invalidate();
	}
	
	/*protected boolean touchEvent(TouchEvent event) {
		int x = event.getX(1);
		int y = event.getY(1) - titleManager.getHeight();
		if(event.getEvent() == TouchEvent.DOWN){
			if(bgManager.checkLeftArrow(x, y)){
				navigationMovement(-1, 0, 536870912, 5000);
				return true;
			}else if(bgManager.checkRightArrow(x, y)){
				navigationMovement(1, 0, -1610612736, 5000);   
				return true;
			}
		}
		if(this.getFieldAtLocation(x, y)==-1){
			return true;
		}else if(this.getFieldAtLocation(x, y)==0){
			if(bgManager.getFieldAtLocation(x, y)!=-1){
				return super.touchEvent(event);
			}
			return true;
		}
		else{
			return super.touchEvent(event);
		}
	}*/
	public boolean navigationMovement(int dx, int dy, int status, int time) {
		if(dy == 0 && dx == -1){
			if(pages.size() >1){
				if((currentPage-1)<0){
					currentPage = pages.size()-1;
				}else{
					currentPage--;
				}
				synchronized(UiApplication.getEventLock()) {
					pageNumber.setLabel("Page "+(currentPage+1)+"/"+pages.size());
					ThumbnailField[] temp = new ThumbnailField[((Vector)pages.elementAt(currentPage)).size()];
	    			((Vector)pages.elementAt(currentPage)).copyInto(temp);
	    			try{
	    				bgManager.deleteAll();
	    			}catch(Exception e){}
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
					ThumbnailField[] temp = new ThumbnailField[((Vector)pages.elementAt(currentPage)).size()];
	    			((Vector)pages.elementAt(currentPage)).copyInto(temp);
	    			try{
	    				bgManager.deleteAll();
	    			}catch(Exception e){}
		    		bgManager.addAll(temp);
		    	}
			}
			return true;
		}
		return super.navigationMovement(dx, dy, status, time);
	}
	
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		} 
	}
}