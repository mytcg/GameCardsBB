package net.mytcg.prg.ui;

import java.util.Vector;

import net.mytcg.prg.custom.FixedButtonField;
import net.mytcg.prg.custom.ListItemField;
import net.mytcg.prg.custom.ThumbnailField;
import net.mytcg.prg.util.Card;
import net.mytcg.prg.util.Const;
import net.mytcg.prg.util.SettingsBean;
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
    	
    	if (update) {
    		SettingsBean.saveSettings(_instance);
    		SettingsBean.getSettings().lastloaded();
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
	    			_instance.setImages(cardid, new Card(cardid, description, quantity, thumburl, fronturl, backurl, note, updated, stats));
	    			 
	    			val = val.substring(val.indexOf(Const.xml_card_end)+Const.xml_card_end_length);
	    			empty = false;
	    			synchronized(UiApplication.getEventLock()) {
	    				tmp = new ThumbnailField(_instance.getImages(cardid));
	        			tmp.setChangeListener(this);
	        			add(tmp);
	        		}
	    		}
	    		if (empty) {
	    			synchronized(UiApplication.getEventLock()) {
	        			add(new ListItemField("Emtpy", -1, false, 0));
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
		if (Const.getMobidex()) {
			doConnect(Const.cardsincategory+id+Const.height+Const.getCardHeight()+Const.showall+Const.second+SettingsBean.getSettings().getLoaded());
		} else {
			doConnect(Const.cardsincategory+id+Const.height+Const.getCardHeight()+Const.second+SettingsBean.getSettings().getLoaded());
		}
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