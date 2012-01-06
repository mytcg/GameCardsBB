package net.mytcg.topcar.ui;

import java.util.Vector;

import net.mytcg.topcar.ui.custom.ColorLabelField;
import net.mytcg.topcar.ui.custom.FixedButtonField;
import net.mytcg.topcar.ui.custom.ListItemField;
import net.mytcg.topcar.ui.custom.PageNumberField;
import net.mytcg.topcar.ui.custom.ThumbnailField;
import net.mytcg.topcar.util.Card;
import net.mytcg.topcar.util.Const;
import net.mytcg.topcar.util.SettingsBean;
import net.mytcg.topcar.util.Stat;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.TouchEvent;
import net.rim.device.api.ui.UiApplication;

public class ViewDeckScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	PageNumberField pageNumber = new PageNumberField("Page 1/1");
	
	ListItemField addcard = new ListItemField("Empty", -1, false, 0);
	ListItemField deletedeck = new ListItemField("Empty", -1, false, 0);
	ThumbnailField tmp = new ThumbnailField(new Card(-1, "", 0, "", "", "", "", 0, null,-1, "", ""));
	String cards = null;
	int deckid = -1;
	int categoryid = -1;
	boolean update = true;
	Vector pages = new Vector();
	Vector tempList = new Vector();
	int currentPage = 0;

	public ViewDeckScreen(int deckid)
	{
		super(null);
		add(new ColorLabelField(""));
		this.deckid = deckid;
		bgManager.setStatusHeight(exit.getContentHeight());
		bgManager.setArrowMode(true);
		
		addcard = new ListItemField(Const.addcard, 0, false, 0);
		deletedeck = new ListItemField(Const.delete_deck, 0, false, 0);
		
		exit.setChangeListener(this); 
		addcard.setChangeListener(this);
		deletedeck.setChangeListener(this);
		
		tempList.addElement(addcard);
		tempList.addElement(deletedeck);
		
		addButton(new FixedButtonField(""));
		addButton(pageNumber);
		addButton(exit);
		
		doConnect(Const.getcardsindeck+Const.deck_id+deckid+Const.height+Const.getCardHeight()+Const.jpg+Const.bbheight+Const.getAppHeight()+Const.width+Const.getCardWidth());
	}
	
	public void process(String val) {
		int listSize = (Const.getUsableHeight()) / 74;
		int listCounter = 2;
		pages = new Vector();
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
    	}else if (((fromIndex = val.indexOf(Const.xml_deck)) != -1)) {
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
    		if ((fromIndex = val.indexOf(Const.xml_category_id)) != -1) {
    			try {
    				categoryid = Integer.parseInt(val.substring(fromIndex+Const.xml_category_id_length, val.indexOf(Const.xml_category_id_end, fromIndex)));
    			} catch (Exception e) {
    				categoryid = -1;
    			}
    		}

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
    				tmp = new ThumbnailField(_instance.getImages(cardid));
    				tmp.setSecondLabel("Quantity: "+ quantity);
    				if(!quality.equals("")){
    					tmp.setSecondLabel("Quality: "+ quality);
    				}
    				tmp.setChangeListener(this);
    				tempList.addElement(tmp);
        			listCounter++;
    			}
    		}
    		if (empty) {
    			synchronized(UiApplication.getEventLock()) {
    				tempList.addElement(new ListItemField("Empty", -1, false, 0));
    			}
    		}
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
    		SettingsBean.saveSettings(_instance);
    		_instance = null;
    	}
    	invalidate();
	}
	protected boolean touchEvent(TouchEvent event) {
		int x = event.getX(1);
		int y = event.getY(1) - titleManager.getHeight();
		if(event.getEvent() == TouchEvent.CLICK){
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
	}
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
	
	public void onExposed(){
		SettingsBean _instance = SettingsBean.getSettings();
		if(_instance.deleted){
			_instance.deleted = false;
			SettingsBean.saveSettings(_instance);
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		}
		synchronized(UiApplication.getEventLock()) {
			bgManager.deleteAll();
			tempList = new Vector();
			tempList.addElement(addcard);
			tempList.addElement(deletedeck);
		}
		doConnect(Const.getcardsindeck+Const.deck_id+deckid+Const.height+Const.getCardHeight()+Const.jpg+Const.bbheight+Const.getAppHeight()+Const.width+Const.getCardWidth());
	}
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		} else if(f == addcard){
			screen = new AlbumScreen(categoryid, 3,deckid);
			UiApplication.getUiApplication().pushScreen(screen);
		}else if(f == deletedeck){
			screen = new DeckDeleteScreen(deckid);
			UiApplication.getUiApplication().pushScreen(screen);
		} else if(f instanceof ThumbnailField){
			ThumbnailField set = ((ThumbnailField)(f));
			Card card = set.getCard();
			screen = new RemoveCardFromDeckScreen(card,deckid);
			UiApplication.getUiApplication().pushScreen(screen);
		}
	}
}