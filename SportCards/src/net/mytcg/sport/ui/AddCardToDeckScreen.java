package net.mytcg.sport.ui;

import net.mytcg.sport.ui.custom.ColorLabelField;
import net.mytcg.sport.ui.custom.FixedButtonField;
import net.mytcg.sport.ui.custom.ListItemField;
import net.mytcg.sport.util.Card;
import net.mytcg.sport.util.Const;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.LabelField;

public class AddCardToDeckScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	
	ListItemField tmp = new ListItemField("Empty", -1, false, 0);
	ListItemField createDeck = new ListItemField("Create New Deck",-1,true,0);
	Card card = null;
	Bitmap cardthumb = null;
	
	public void process(String val) {	
		if (!(isDisplaying())) {
			int fromIndex;
	    	if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
	    		setText(val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex)));
	    	} else if (((fromIndex = val.indexOf(Const.xml_cardcategories)) != -1)) {
	    		int albumid = -1;
	    		String albumname = "";
	    		int endIndex = -1;
	    		boolean empty = true;
	    		String album = "";
	    		while ((fromIndex = val.indexOf(Const.xml_albumid)) != -1){
	    			
	    			endIndex = val.indexOf(Const.xml_album_end);
	    			album = val.substring(fromIndex, endIndex+Const.xml_album_end_length);
	    			fromIndex = album.indexOf(Const.xml_albumid);
	    			
	    			try {
	    				albumid = Integer.parseInt(album.substring(fromIndex+Const.xml_albumid_length, album.indexOf(Const.xml_albumid_end, fromIndex)));
	    			} catch (Exception e) {
	    				albumid = -1;
	    			}
	    			if ((fromIndex = album.indexOf(Const.xml_albumname)) != -1) {
	    				albumname = album.substring(fromIndex+Const.xml_albumname_length, album.indexOf(Const.xml_albumname_end, fromIndex));
	    			}
	    			val = val.substring(val.indexOf(Const.xml_album_end)+Const.xml_album_end_length);
	    			
	    			synchronized(UiApplication.getEventLock()) {
	    				tmp = new ListItemField(albumname, albumid, true, 0);
	        			tmp.setChangeListener(this);
	        			add(tmp);
	        		}
	    			empty = false;
	    		}
	    		if (empty) {
	    			synchronized(UiApplication.getEventLock()) {
	        			add(new ListItemField("Empty", -1, false, 0));
	        		}
	    		}
	    	}
	    	invalidate();
	    	setDisplaying(true);
		}		
	}
	public AddCardToDeckScreen(Card card, Bitmap cardthumb) {
		super(null);
		this.card = card;
		this.cardthumb = cardthumb;
		bgManager.setStatusHeight(exit.getContentHeight());
		
		exit.setChangeListener(this);
		add(new ColorLabelField("Add Card to Deck",LabelField.FIELD_HCENTER , 18));
		add(createDeck);
		
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		addButton(exit);
		
		doConnect(Const.getdecks);
	}
	
	protected void onExposed() {
		super.onExposed();
	}
	public boolean onClose() {
		screen = null;
		UiApplication.getUiApplication().popScreen(this);
		return true;
	}
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		} else if(f instanceof ListItemField){
			int id = ((ListItemField)(f)).getId();
			if(id == -1){
				//screen = new CreateNewDeckScreen();
				UiApplication.getUiApplication().pushScreen(screen);
			}else{
				synchronized(UiApplication.getEventLock()) {
					remove(createDeck);
        			clear();
        		}
				doConnect(Const.addtodeck);
			}
		}
	}
}