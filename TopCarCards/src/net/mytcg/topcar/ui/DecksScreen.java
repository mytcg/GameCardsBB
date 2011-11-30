package net.mytcg.topcar.ui;

import java.util.Vector;

import net.mytcg.topcar.ui.custom.FixedButtonField;
import net.mytcg.topcar.ui.custom.ListItemField;
import net.mytcg.topcar.ui.custom.PageNumberField;
import net.mytcg.topcar.util.Const;
import net.mytcg.topcar.util.SettingsBean;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class DecksScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	PageNumberField pageNumber = new PageNumberField("Page 1/1");
	
	ListItemField newdeck = new ListItemField("Empty", -1, false, 0);
	ListItemField tmp = new ListItemField("Empty", -1, false, 0);
	String decks = null;
	Vector pages = new Vector();
	Vector tempList = new Vector();
	int currentPage = 0;

	public DecksScreen()
	{
		super(null);
		bgManager.setStatusHeight(exit.getContentHeight());
		bgManager.setArrowMode(true);
		
		newdeck = new ListItemField(Const.newdeck, 0, false, 0);
		
		exit.setChangeListener(this); 
		newdeck.setChangeListener(this);
		
		tempList.addElement(newdeck);
		
		addButton(new FixedButtonField(""));
		addButton(pageNumber);
		addButton(exit);
		
		doConnect(Const.getalldecks);
	}
	
	public void process(String val) {
		int listSize = (Const.getUsableHeight()) / Const.getButtonHeight();
		int listCounter = 1;
		pages = new Vector();
		int fromIndex;
	    if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
	    	setText(val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex)));
	    }else if (((fromIndex = val.indexOf(Const.xml_decks)) != -1)) {
		   	int deckid = -1;
		   	String deckname = "";
		   	int endIndex = -1;
		   	String decks = "";
		   	while ((fromIndex = val.indexOf(Const.xml_deck_id)) != -1){
		   		if(listCounter >= listSize){
    				pages.addElement(tempList);
    				tempList = new Vector();
    				listCounter=0;
    			}
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
		       			tempList.addElement(tmp);
	        			listCounter++;
		       		}
		   		}
		   	}
		   	pages.addElement(tempList);
    		synchronized(UiApplication.getEventLock()) {
    			System.out.println("SIZE "+((Vector)pages.elementAt(0)).size());
    			pageNumber.setLabel("Page 1/"+pages.size());
    			ListItemField[] temp = new ListItemField[((Vector)pages.elementAt(0)).size()];
    			((Vector)pages.elementAt(0)).copyInto(temp);
    			bgManager.deleteAll();
	    		bgManager.addAll(temp);
	    	}
	    }
	    invalidate();		
	}
	
	protected boolean navigationMovement(int dx, int dy, int status, int time) {
		if(dy == 0 && dx == -1){
			if(pages.size() >1){
				if((currentPage-1)<0){
					currentPage = pages.size()-1;
				}else{
					currentPage--;
				}
				synchronized(UiApplication.getEventLock()) {
					pageNumber.setLabel("Page "+(currentPage+1)+"/"+pages.size());
					ListItemField[] temp = new ListItemField[((Vector)pages.elementAt(currentPage)).size()];
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
					ListItemField[] temp = new ListItemField[((Vector)pages.elementAt(currentPage)).size()];
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
		if(_instance.deckid !=-1){
			int deckid = _instance.deckid;
			_instance.deckid = -1;
			SettingsBean.saveSettings(_instance);
			screen = new ViewDeckScreen(deckid);
			UiApplication.getUiApplication().pushScreen(screen);
		}else{
			synchronized(UiApplication.getEventLock()) {
				bgManager.deleteAll();
				tempList = new Vector();
				tempList.addElement(newdeck);
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