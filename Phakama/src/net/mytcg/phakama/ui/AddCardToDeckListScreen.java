package net.mytcg.phakama.ui;

import java.util.Vector;

import net.mytcg.phakama.ui.custom.ColorLabelField;
import net.mytcg.phakama.ui.custom.FixedButtonField;
import net.mytcg.phakama.ui.custom.ListItemField;
import net.mytcg.phakama.ui.custom.ThumbnailField;
import net.mytcg.phakama.util.Card;
import net.mytcg.phakama.util.Const;
import net.mytcg.phakama.util.SettingsBean;
import net.mytcg.phakama.util.Stat;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class AddCardToDeckListScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	
	ThumbnailField tmp = new ThumbnailField(new Card(-1, "", 0, "", "", "", "", 0, null, -1, "", ""));
	ColorLabelField label = new ColorLabelField(" Choose a card to add to your deck.");
	String cards = null;
	int deckid = -1;
	int categoryid = -1;
	boolean update = true;

	public AddCardToDeckListScreen(int deckid, int categoryid)
	{
		super(null);
		this.deckid = deckid;
		this.categoryid = categoryid;
		bgManager.setStatusHeight(exit.getContentHeight());
		
		exit.setChangeListener(this); 
		
		add(label);
		
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		addButton(exit);
		doConnect(Const.cardsincategorynotdeck+categoryid+Const.deck_id+deckid+Const.height+Const.getCardHeight()+Const.width+Const.getCardWidth()+Const.jpg+Const.bbheight+Const.getAppHeight());
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
    		synchronized(UiApplication.getEventLock()) {
    			label.setText(val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex)));
    		}
    		try{
    			Thread.sleep(1000);
    		}catch(Exception e){};
    		synchronized(UiApplication.getEventLock()) {
	    		screen = null;
	    		UiApplication.getUiApplication().popScreen(this);
    		}
    	}else if (((fromIndex = val.indexOf(Const.xml_cardsincategory)) != -1)) {
    		int cardid = -1;
    		String description = "";
    		String quality = "";
    		int quantity = -1;
    		int rating = -1;
    		String thumburl = "";
    		String fronturl = "";
    		String backurl = "";
    		String note = "";
    		int updated = 0;
    		Vector stats = null;
    		String value = "";
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
    			quality = "";
    			quantity = -1;
    			thumburl = "";
    			fronturl = "";
    			rating = -1;
    			backurl = "";
    			value = "";
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
    			if ((fromIndex = card.indexOf(Const.xml_quality)) != -1) {
    				quality = card.substring(fromIndex+Const.xml_quality_length, card.indexOf(Const.xml_quality_end, fromIndex));
    			}
    			if ((fromIndex = card.indexOf(Const.xml_quantity)) != -1) {
    				try {
    					quantity = Integer.parseInt(card.substring(fromIndex+Const.xml_quantity_length, card.indexOf(Const.xml_quantity_end, fromIndex)));
    				} catch (Exception e) {
    					quantity = 0;
    				}
    			}
    			if ((fromIndex = card.indexOf(Const.xml_value)) != -1) {
    				value = card.substring(fromIndex+Const.xml_value_length, card.indexOf(Const.xml_value_end, fromIndex));
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
	
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		} else if(f instanceof ThumbnailField){
			ThumbnailField set = ((ThumbnailField)(f));
			Card card = set.getCard();
			synchronized(UiApplication.getEventLock()) {
    			label.setText("Adding card to deck...");
    		}
			doConnect(Const.addtodeck+Const.deck_id+deckid+Const.card_id+card.getId());
		}
	}
}