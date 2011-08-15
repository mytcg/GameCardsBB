package net.mytcg.sport.ui;

import java.util.Vector;

import net.mytcg.sport.ui.custom.FixedButtonField;
import net.mytcg.sport.ui.custom.ListItemField;
import net.mytcg.sport.ui.custom.ThumbnailField;
import net.mytcg.sport.util.Card;
import net.mytcg.sport.util.Const;
import net.mytcg.sport.util.SettingsBean;
import net.mytcg.sport.util.Stat;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class ViewDeckScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	
	ListItemField addcard = new ListItemField("Empty", -1, false, 0);
	ListItemField deletedeck = new ListItemField("Empty", -1, false, 0);
	ThumbnailField tmp = new ThumbnailField(new Card(-1, "", 0, "", "", "", "", 0, null,-1, "", ""));
	String cards = null;
	int deckid = -1;
	int categoryid = -1;
	boolean update = true;

	public ViewDeckScreen(int deckid)
	{
		super(null);
		this.deckid = deckid;
		bgManager.setStatusHeight(exit.getContentHeight());
		
		addcard = new ListItemField(Const.addcard, 0, false, 0);
		deletedeck = new ListItemField(Const.delete_deck, 0, false, 0);
		
		exit.setChangeListener(this); 
		addcard.setChangeListener(this);
		deletedeck.setChangeListener(this);
		
		add(addcard);
		add(deletedeck);
		
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		addButton(exit);
		
		doConnect(Const.getcardsindeck+Const.deck_id+deckid+Const.height+Const.getCardHeight()+Const.bbheight+Const.getAppHeight()+Const.width+Const.getCardWidth());
	}
	
	public void process(String val) {
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
    				add(tmp);
    			}
    		}
    		if (empty) {
    			synchronized(UiApplication.getEventLock()) {
    				add(new ListItemField("Empty", -1, false, 0));
    			}
    		}
    		SettingsBean.saveSettings(_instance);
    		_instance = null;
    	}
    	invalidate();
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
			add(addcard);
			add(deletedeck);
		}
		doConnect(Const.getcardsindeck+Const.deck_id+deckid+Const.height+Const.getCardHeight()+Const.bbheight+Const.getAppHeight()+Const.width+Const.getCardWidth());
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