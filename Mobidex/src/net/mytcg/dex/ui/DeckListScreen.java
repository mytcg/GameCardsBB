package net.mytcg.dex.ui;

import net.mytcg.dex.ui.custom.ColorLabelField;
import net.mytcg.dex.ui.custom.FixedButtonField;
import net.mytcg.dex.ui.custom.ListItemField;
import net.mytcg.dex.util.Const;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class DeckListScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	FixedButtonField con = new FixedButtonField(Const.con);
	ColorLabelField header = new ColorLabelField("Loading albums");
	
	ListItemField tmp = new ListItemField("Empty", -1, false, 0);
	int cardId = -1;

	public DeckListScreen(int cardId)
	{
		super(null);
		this.cardId = cardId;
		bgManager.setStatusHeight(exit.getContentHeight());
		header.setText("Please select an album");
		
		con.setChangeListener(this);
		exit.setChangeListener(this);
		
		add(header);
		
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		addButton(exit);
		
		doConnect("getalldecks=1");
	}
	
	public void process(String val) {
		int deckid = -1;
		int fromIndex;
	    if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
	    	synchronized(UiApplication.getEventLock()) {
		    	try{
			    	header.setText(val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex)));
		    	}catch(Exception e){};
		    	try{
					hManager1.deleteAll();
				}catch(Exception e){};
	    		addButton(con);
	    	}
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
		       		}
	    		}
	    	}
	    	try{
	    		header.setText("Please select an album");
	    	}catch(Exception e){};
	    }
	    invalidate();
	}
	
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		} else if (f == con) {
			Const.added = 1;
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		} else if(f instanceof ListItemField){
			int deckId = ((ListItemField)(f)).getId();
			if(deckId > -1&&cardId>-1){
				synchronized(UiApplication.getEventLock()) {
				try{
					bgManager.deleteAll();
				}catch(Exception e){};
				try{
					add(header);
					header.setText("Adding...");
				}catch(Exception e){};
				}
				invalidate();
				doConnect("addtodeck="+cardId+"&deckid="+deckId);
			}
		}
	}
}