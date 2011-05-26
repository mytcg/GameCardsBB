package net.mytcg.dex.ui;

import java.util.Vector;

import net.mytcg.dex.ui.custom.FixedButtonField;
import net.mytcg.dex.ui.custom.ListItemField;
import net.mytcg.dex.ui.custom.ThumbnailField;
import net.mytcg.dex.util.Card;
import net.mytcg.dex.util.Const;
import net.mytcg.dex.util.SettingsBean;
import net.mytcg.dex.util.Stat;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class AlbumListScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	
	ThumbnailField tmp = new ThumbnailField(new Card(-1, "", 0, "", "", "", "", 0, null));
	
	int id = -1;
	boolean update = true;
	boolean newcards = false;
	
	public void process(String val) {
		SettingsBean _instance = SettingsBean.getSettings();
    	update = _instance.setCards(val, id);
    	
		if (id == Const.UPDATES) {
			SettingsBean.getSettings().lastloaded();
		}
		
    	if (update) {
    		SettingsBean.saveSettings(_instance);
    	}
    	if (!isDisplaying()||update) {
			int fromIndex;
			int endIndex;
			boolean empty = true;
			String card = "";
	    	if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
	    		setText(val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex)));
	    	} else if (((fromIndex = val.indexOf(Const.xml_cardsincategory)) != -1)) {
	    		synchronized(UiApplication.getEventLock()) {
	    			clear();
	    		}
	    		int cardid = -1;
	    		String description = "";
	    		int quantity = -1;
	    		String thumburl = "";
	    		String fronturl = "";
	    		String backurl = "";
	    		String note = "";
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
	    			
	    			endIndex = val.indexOf(Const.xml_card_end);
	    			card = val.substring(fromIndex, endIndex+Const.xml_card_end_length);
	    			fromIndex = card.indexOf(Const.xml_cardid);
	    			
	    			cardid = -1;
	    			description = "";
	    			quantity = -1;
	    			thumburl = "";
	    			fronturl = "";
	    			backurl = "";
	    			note = "";
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
	    			_instance.setImages(cardid, new Card(cardid, description, quantity, thumburl, fronturl, backurl, note, updated, stats));
	    			  
	    			val = val.substring(val.indexOf(Const.xml_card_end)+Const.xml_card_end_length);
	    			empty = false;
	    			synchronized(UiApplication.getEventLock()) {
	    				if (id == Const.UPDATES) {
	    					tmp = new ThumbnailField(_instance.getImages(cardid), true);
	    				} else {
	    					tmp = new ThumbnailField(_instance.getImages(cardid));
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
	    	setDisplaying(true);
    	}
	}
	public AlbumListScreen(int id) {
		super(null);
		bgManager.setStatusHeight(exit.getContentHeight());
		
		if (id == Const.NEWCARDS) {
			newcards = true;
		}
		
		exit.setChangeListener(this);
		
		addButton(exit);
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		
		this.id = id;
		
		process(SettingsBean.getSettings().getCards(id));
		doConnect(Const.cardsincategory+id+Const.height+Const.getCardHeight()+Const.second+SettingsBean.getSettings().getLoaded());
	}
	
	public AlbumListScreen(String val) {
		super(null);
		bgManager.setStatusHeight(exit.getContentHeight());
		
		exit.setChangeListener(this);
		
		addButton(exit);
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		
		process(val);
	}	
	
	protected void onExposed() {
		screen = null;
		if (!isVisible()) {
			doConnect(Const.cardsincategory+id+Const.height+Const.getCardHeight()+Const.second+SettingsBean.getSettings().getLoaded());
		}
		super.onExposed();
	}

	
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		} else {
			Card card = ((ThumbnailField)(f)).getCard();
			if (card.getQuantity() > 0) {
				screen = new ImageScreen(card, newcards, this);
				UiApplication.getUiApplication().pushScreen(screen);
			}
		}
	}
}