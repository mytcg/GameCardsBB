package net.mytcg.topcar.ui;

import net.mytcg.topcar.ui.custom.ColorLabelField;
import net.mytcg.topcar.ui.custom.FixedButtonField;
import net.mytcg.topcar.ui.custom.ListItemField;
import net.mytcg.topcar.util.Const;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class ChooseDeckScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	ColorLabelField header = new ColorLabelField("");
	
	ListItemField tmp = new ListItemField("Empty", -1, false, 0);
	int categoryId = -1;
	int count = 0;

	public ChooseDeckScreen(int categoryId)
	{
		super(null);
		this.categoryId = categoryId;
		bgManager.setStatusHeight(exit.getContentHeight());
		
		exit.setChangeListener(this);
		
		add(header);
		
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		addButton(exit);
		
		doConnect("getcategorydecks=1&category_id="+categoryId);
	}
	
	public void process(String val) {
		int deckid = -1;
		int fromIndex;
	    if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
	    	setText(val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex)));
	    } else if (((fromIndex = val.indexOf(Const.xml_decks)) != -1)) {
	    	String deckname = "";
	    	int endIndex = -1;
	    	String deck = "";
	    	while ((fromIndex = val.indexOf(Const.xml_deck_id)) != -1){
	    		
	    		endIndex = val.indexOf(Const.xml_deck_end);
	    		deck = val.substring(fromIndex, endIndex+Const.xml_deck_end_length);
	    		fromIndex = deck.indexOf(Const.xml_deck_id);
	    		
	    		try {
	    			deckid = Integer.parseInt(deck.substring(fromIndex+Const.xml_deck_id_length, deck.indexOf(Const.xml_deck_id_end, fromIndex)));
	    		} catch (Exception e) {
	    			deckid = -1;
	    		}
	    		if ((fromIndex = deck.indexOf(Const.xml_descr)) != -1) {
	    			deckname = deck.substring(fromIndex+Const.xml_descr_length, deck.indexOf(Const.xml_descr_end, fromIndex));
	    		}
	    		val = val.substring(val.indexOf(Const.xml_deck_end)+Const.xml_deck_end_length);
	    		if(deckid != -1){
		   			synchronized(UiApplication.getEventLock()) {
		   				tmp = new ListItemField(deckname, deckid, true, 0);
		       			tmp.setChangeListener(this);
		       			add(tmp);
		       			count++;
		       		}
	    		}
	    	}
	    }
    	if(count==0){
    		synchronized(UiApplication.getEventLock()) {
    			screen = new PlayMenuScreen(categoryId, -1);
    			UiApplication.getUiApplication().pushScreen(screen);
    		}
	   	}else if(count==1){
	   		synchronized(UiApplication.getEventLock()) {
	   			screen = new PlayMenuScreen(categoryId, deckid);
	   			UiApplication.getUiApplication().pushScreen(screen);
	   		}
	   	}
    	try{
    		header.setText("Please choose a deck to play with");
    	}catch(Exception e){}
	    invalidate();
	}
	
	public void onExposed(){
		if(count==0||count==1){
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		}
	}
	
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		} else if(f instanceof ListItemField){
			int deckId = ((ListItemField)(f)).getId();
			if(deckId > -1){
				screen = new PlayMenuScreen(categoryId, deckId);
				UiApplication.getUiApplication().pushScreen(screen);
			}
		}
	}
}