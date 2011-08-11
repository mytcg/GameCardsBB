package net.mytcg.topcar.ui;

import java.util.Vector;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

import net.mytcg.topcar.ui.custom.FixedButtonField;
import net.mytcg.topcar.ui.custom.ListItemField;
import net.mytcg.topcar.ui.custom.ThumbnailField;
import net.mytcg.topcar.util.Card;
import net.mytcg.topcar.util.Const;
import net.mytcg.topcar.util.SettingsBean;
import net.mytcg.topcar.util.Stat;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class AlbumListScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	
	ThumbnailField tmp = new ThumbnailField(new Card(-1, "", 0, "", "", "", "", 0, null, -1, "", ""));
	
	int id = -1;
	int type = 0;
	boolean update = true;
	boolean newcards = false;
	Card compareCard = null;
	
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
	    		String quality = "";
	    		int quantity = -1;
	    		int rating = -1;
	    		String thumburl = "";
	    		String fronturl = "";
	    		String backurl = "";
	    		String frontflipurl = "";
	    		String backflipurl = "";
	    		String note = "";
	    		int updated = 0;
	    		Vector stats = null;
	    		String statdesc = "";
	    		String value = "";
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
	    			rating = -1;
	    			thumburl = "";
	    			fronturl = "";
	    			backurl = "";
	    			frontflipurl = "";
	    			backflipurl = "";
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
	    			if ((fromIndex = card.indexOf(Const.xml_quantity)) != -1) {
	    				try {
	    					quantity = Integer.parseInt(card.substring(fromIndex+Const.xml_quantity_length, card.indexOf(Const.xml_quantity_end, fromIndex)));
	    				} catch (Exception e) {
	        				quantity = -1;
	        			}
	    			}
	    			if ((fromIndex = card.indexOf(Const.xml_rating)) != -1) {
	    				try {
	    					rating = Integer.parseInt(card.substring(fromIndex+Const.xml_rating_length, card.indexOf(Const.xml_rating_end, fromIndex)));
	    				} catch (Exception e) {
	        				rating = -1;
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
	    			if ((fromIndex = card.indexOf(Const.xml_frontflipurl)) != -1) {
		    			frontflipurl = card.substring(fromIndex+Const.xml_frontflipurl_length, card.indexOf(Const.xml_frontflipurl_end, fromIndex));
		    		}
		    		if ((fromIndex = card.indexOf(Const.xml_backflipurl)) != -1) {
		    			backflipurl = card.substring(fromIndex+Const.xml_backflipurl_length, card.indexOf(Const.xml_backflipurl_end, fromIndex));
		    		}
	    			if ((fromIndex = card.indexOf(Const.xml_note)) != -1) {
	    				note = card.substring(fromIndex+Const.xml_note_length, card.indexOf(Const.xml_note_end, fromIndex));
	    			}
	    			if ((fromIndex = card.indexOf(Const.xml_value)) != -1) {
	    				value = card.substring(fromIndex+Const.xml_value_length, card.indexOf(Const.xml_value_end, fromIndex));
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
	    			cardobject.setCategoryId(id);
	    			cardobject.setFrontFlipurl(frontflipurl);
	    			cardobject.setBackFlipurl(backflipurl);
	    			_instance.setImages(cardid, cardobject);
	    			if(updated == 1){
	    				String thumbfile = "";
	    				String frontfile = "";
	    				String backfile = "";
	    				String backflipfile = "";
	    				String frontflipfile = "";
	    				FileConnection _file = null;
						if ((cardobject.getThumburl() != null)&&(cardobject.getThumburl().length() > 0)){
							thumbfile = cardobject.getThumburl().substring(cardobject.getThumburl().indexOf(Const.cards)+Const.cards_length, cardobject.getThumburl().indexOf(Const.png));
							try{	
								_file = (FileConnection)Connector.open(Const.getStorage()+Const.PREFIX+thumbfile);
								if(_file.exists()){
									_file.delete();
								}
								_file.close();
								_file = null;
							}catch(Exception e){};
						}
						if ((cardobject.getFronturl() != null)&&(cardobject.getFronturl().length() > 0)){
							frontfile = cardobject.getFronturl().substring(cardobject.getFronturl().indexOf(Const.cards)+Const.cards_length, cardobject.getFronturl().indexOf(Const.png));
							try{	
								_file = (FileConnection)Connector.open(Const.getStorage()+Const.PREFIX+frontfile);
								if(_file.exists()){
									_file.delete();
								}
								_file.close();
								_file = null;
							}catch(Exception e){};
						}
						if ((cardobject.getBackurl() != null)&&(cardobject.getBackurl().length() > 0)){
							backfile  = cardobject.getBackurl().substring(cardobject.getBackurl().indexOf(Const.cards)+Const.cards_length, cardobject.getBackurl().indexOf(Const.png));
							try{	
								_file = (FileConnection)Connector.open(Const.getStorage()+Const.PREFIX+backfile);
								if(_file.exists()){
									_file.delete();
								}
								_file.close();
								_file = null;
							}catch(Exception e){};
						}
						if ((cardobject.getBackFlipurl() != null)&&(cardobject.getBackFlipurl().length() > 0)){
							backflipfile  = cardobject.getBackFlipurl().substring(cardobject.getBackFlipurl().indexOf(Const.cards)+Const.cards_length, cardobject.getBackFlipurl().indexOf(Const.png));
							try{	
								_file = (FileConnection)Connector.open(Const.getStorage()+Const.PREFIX+backflipfile);
								if(_file.exists()){
									_file.delete();
								}
								_file.close();
								_file = null;
							}catch(Exception e){};
						}
						if ((cardobject.getFrontFlipurl() != null)&&(cardobject.getFrontFlipurl().length() > 0)){
							frontflipfile  = cardobject.getFrontFlipurl().substring(cardobject.getFrontFlipurl().indexOf(Const.cards)+Const.cards_length, cardobject.getFrontFlipurl().indexOf(Const.png));
							try{	
								_file = (FileConnection)Connector.open(Const.getStorage()+Const.PREFIX+frontflipfile);
								if(_file.exists()){
									_file.delete();
								}
								_file.close();
								_file = null;
							}catch(Exception e){};
						}
					}
	    			val = val.substring(val.indexOf(Const.xml_card_end)+Const.xml_card_end_length);
	    			
	    			empty = false;
	    			synchronized(UiApplication.getEventLock()) {
	    				if (id == Const.UPDATES) {
	    					tmp = new ThumbnailField(_instance.getImages(cardid), true);
	    				} else {
	    					tmp = new ThumbnailField(_instance.getImages(cardid));
	    				}
	    				if(!quality.equals("")){
	    					tmp.setSecondLabel(""+ quality);
	    				}
	    				if(rating != -1){
	    					tmp.setThirdLabel("Rating: "+ rating);
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
	public AlbumListScreen(int id, int type) {
		super(null);
		this.type = type;
		bgManager.setStatusHeight(exit.getContentHeight());
		
		if (id == Const.NEWCARDS) {
			newcards = true;
		}
		
		exit.setChangeListener(this);
		
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		addButton(exit);
		
		this.id = id;
		
		process(SettingsBean.getSettings().getCards(id));
		doConnect(Const.cardsincategory+id+Const.height+Const.getCardHeight()+Const.bbheight+Const.getAppHeight()+Const.width+Const.getCardWidth()+Const.second+SettingsBean.getSettings().getLoaded());
	}
	
	public AlbumListScreen(int id, int type, Card card) {
		super(null);
		this.type = type;
		this.compareCard = card;
		bgManager.setStatusHeight(exit.getContentHeight());
		
		if (id == Const.NEWCARDS) {
			newcards = true;
		}
		
		exit.setChangeListener(this);
		
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		addButton(exit);
		
		this.id = id;
		
		process(SettingsBean.getSettings().getCards(id));
		doConnect(Const.cardsincategory+id+Const.height+Const.getCardHeight()+Const.bbheight+Const.getAppHeight()+Const.width+Const.getCardWidth()+Const.second+SettingsBean.getSettings().getLoaded());
	}
	
	public AlbumListScreen(String val) {
		super(null);
		bgManager.setStatusHeight(exit.getContentHeight());
		
		exit.setChangeListener(this);
		
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		addButton(exit);
		
		process(val);
	}
	
	public AlbumListScreen(String val, int type) {
		super(null);
		this.type = type;
		bgManager.setStatusHeight(exit.getContentHeight());
		
		exit.setChangeListener(this);
		
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		addButton(exit);
		
		process(val);
	}
	
	protected void onExposed() {
		if(type == 1){
			UiApplication.getUiApplication().popScreen(this);
		}
		if(SettingsBean.getSettings().created) {
			SettingsBean _instance = SettingsBean.getSettings();
			_instance.created = false;
			SettingsBean.saveSettings(_instance);
			//UiApplication.getUiApplication().popScreen(this);
		}
		screen = null;
		if (!isVisible()) {
			doConnect(Const.cardsincategory+id+Const.height+Const.getCardHeight()+Const.bbheight+Const.getAppHeight()+Const.width+Const.getCardWidth()+Const.second+SettingsBean.getSettings().getLoaded());
		}
		super.onExposed();
	}

	
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			if(type == 3){
				try{
					close();
					Const.GOTOSCREEN = Const.MENUSCREEN;
					Const.FROMSCREEN = Const.LOGINSCREEN;
					Const.app.nextScreen();
				}catch(Exception e){
				}
			} else {
				screen = null;
				UiApplication.getUiApplication().popScreen(this);
			}
		} else if(f instanceof ThumbnailField){
			ThumbnailField set = ((ThumbnailField)(f));
			Card card = set.getCard();
			if (id == Const.MYCARD) {
				screen = new ShareMenuScreen(card, set.getThumbnail());
				UiApplication.getUiApplication().pushScreen(screen);
			} else if(type == 1){
				screen = new AuctionCreateScreen(card, set.getThumbnail());
				UiApplication.getUiApplication().pushScreen(screen);
			}  else if(type == 2){
				screen = new CompareScreen(card, compareCard);
				UiApplication.getUiApplication().pushScreen(screen);
			} else {
				if (card.getQuantity() > 0) {
					screen = new ImageScreen(card, newcards, this, set.getThumbnail());
					UiApplication.getUiApplication().pushScreen(screen);
				}
			}
		}
	}
}