package net.mytcg.phakama.ui;

import net.mytcg.phakama.ui.custom.FixedButtonField;
import net.mytcg.phakama.ui.custom.ListItemField;
import net.mytcg.phakama.util.Const;
import net.mytcg.phakama.util.SettingsBean;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class DecksScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	
	ListItemField newdeck = new ListItemField("Empty", -1, false, 0);
	ListItemField tmp = new ListItemField("Empty", -1, false, 0);
	String decks = null;

	public DecksScreen()
	{
		super(null);
		bgManager.setStatusHeight(exit.getContentHeight());
		
		newdeck = new ListItemField(Const.newdeck, 0, false, 0);
		
		exit.setChangeListener(this); 
		newdeck.setChangeListener(this);
		
		add(newdeck);
		
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		addButton(exit);
		
		doConnect(Const.getalldecks);
	}
	
	public void process(String val) {
		int fromIndex;
	    if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
	    	setText(val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex)));
	    }else if (((fromIndex = val.indexOf(Const.xml_decks)) != -1)) {
		   	int deckid = -1;
		   	String deckname = "";
		   	int endIndex = -1;
		   	String decks = "";
		   	while ((fromIndex = val.indexOf(Const.xml_deck_id)) != -1){
		   		
		   		endIndex = val.indexOf(Const.xml_deck_end);
		   		decks = val.substring(fromIndex, endIndex+Const.xml_deck_end_length);
		   		fromIndex = decks.indexOf(Const.xml_deck_id);
		   		
		   		try {
		   			deckid = Integer.parseInt(decks.substring(fromIndex+Const.xml_deck_id_length, decks.indexOf(Const.xml_deck_id_end, fromIndex)));
		   		} catch (Exception e) {
		   			deckid = -1;
		   		}
		   		if ((fromIndex = decks.indexOf(Const.xml_descr)) != -1) {
		   			deckname = decks.substring(fromIndex+Const.xml_descr_length, decks.indexOf(Const.xml_descr_end, fromIndex));
		   		}
		   		val = val.substring(val.indexOf(Const.xml_deck_end)+Const.xml_deck_end_length);
		   		if(deckid != -1){
		   			synchronized(UiApplication.getEventLock()) {
		   				tmp = new ListItemField(deckname, deckid, true, 0);
		   				tmp.setLabel(deckname);
		       			tmp.setChangeListener(this);
		       			add(tmp);
		       		}
		   		}
		   	}
	    }
	    invalidate();		
	}
	
	public void onExposed(){
		SettingsBean _instance = SettingsBean.getSettings();
		if(_instance.deckid !=-1){
			int deckid = _instance.deckid;
			_instance.deckid = -1;
			SettingsBean.saveSettings(_instance);
			screen = new ViewDeckScreen(deckid);
			UiApplication.getUiApplication().pushScreen(screen);
		}else{
			synchronized(UiApplication.getEventLock()) {
				bgManager.deleteAll();
				add(newdeck);
			}
			doConnect(Const.getalldecks);
		}
	}
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		} else if(f == newdeck){
			screen = new DeckCategoryScreen();
			UiApplication.getUiApplication().pushScreen(screen);
		} else if(f instanceof ListItemField){
			int deckid = ((ListItemField)(f)).getId();
			if(deckid != -1){
				screen = new ViewDeckScreen(deckid);
				UiApplication.getUiApplication().pushScreen(screen);
			}
		}
	}
}