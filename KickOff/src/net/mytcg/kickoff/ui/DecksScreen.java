package net.mytcg.kickoff.ui;

import java.util.Vector;

import net.mytcg.kickoff.ui.custom.ColorLabelField;
import net.mytcg.kickoff.ui.custom.FixedButtonField;
import net.mytcg.kickoff.ui.custom.ListItemField;
import net.mytcg.kickoff.ui.custom.PageNumberField;
import net.mytcg.kickoff.util.Const;
import net.mytcg.kickoff.util.SettingsBean;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
//import net.rim.device.api.ui.TouchEvent;
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
	boolean comp = false;

	public DecksScreen(boolean comp)
	{
		super(null);
		this.comp = comp;
		add(new ColorLabelField(""));
		bgManager.setStatusHeight(exit.getContentHeight());
		bgManager.setArrowMode(true);
		
		newdeck = new ListItemField(Const.newdeck, 0, false, 0);
		
		exit.setChangeListener(this); 
		newdeck.setChangeListener(this);
		
		if(!comp){
			tempList.addElement(newdeck);
		}
		
		addButton(new FixedButtonField(""));
		addButton(pageNumber);
		addButton(exit);
		if(comp){
			doConnect(Const.getallcompdecks);
		}else{
			doConnect(Const.getallnormaldecks);
		}
	}
	
	public void process(String val) {
		int listSize = (Const.getUsableHeight()) / Const.getButtonHeight();
		int listCounter = 1;
		if(comp){
			listCounter = 0;
		}
		pages = new Vector();
		int fromIndex;
	    if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
	    	setText(val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex)));
	    }else if (((fromIndex = val.indexOf(Const.xml_decks)) != -1)) {
		   	int deckid = -1;
		   	int active = 1;
		   	int type = 1;
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
		   		if ((fromIndex = decks.indexOf(Const.xml_active)) != -1) {
		   			try {
			   			active = Integer.parseInt(decks.substring(fromIndex+Const.xml_active_length, decks.indexOf(Const.xml_active_end, fromIndex)));
			   		} catch (Exception e) {
			   			active = 1;
			   		}
		   		}
		   		if ((fromIndex = decks.indexOf(Const.xml_type)) != -1) {
		   			try {
			   			type = Integer.parseInt(decks.substring(fromIndex+Const.xml_type_length, decks.indexOf(Const.xml_type_end, fromIndex)));
			   		} catch (Exception e) {
			   			type = 1;
			   		}
		   		}
		   		val = val.substring(val.indexOf(Const.xml_deck_end)+Const.xml_deck_end_length);
		   		if(deckid != -1){
		   			synchronized(UiApplication.getEventLock()) {
		   				tmp = new ListItemField(deckname, deckid, true, 0);
		   				tmp.setLabel(deckname);
		   				tmp.setActive(active);
		   				tmp.setType(type);
		       			tmp.setChangeListener(this);
		       			tempList.addElement(tmp);
	        			listCounter++;
		       		}
		   		}
		   	}
		   	pages.addElement(tempList);
    		synchronized(UiApplication.getEventLock()) {
    			if(pages.size()<=1){
    				bgManager.setArrowMode(false);
    			}
    			pageNumber.setLabel("Page 1/"+pages.size());
    			ListItemField[] temp = new ListItemField[((Vector)pages.elementAt(0)).size()];
    			((Vector)pages.elementAt(0)).copyInto(temp);
    			bgManager.deleteAll();
	    		bgManager.addAll(temp);
	    	}
	    }
	    invalidate();		
	}
	/*protected boolean touchEvent(TouchEvent event) {
		int x = event.getX(1);
		int y = event.getY(1) - titleManager.getHeight();
		if(event.getEvent() == TouchEvent.DOWN){
			if(bgManager.checkLeftArrow(x, y)){
				navigationMovement(-1, 0, 536870912, 5000);
				return true;
			}else if(bgManager.checkRightArrow(x, y)){
				navigationMovement(1, 0, -1610612736, 5000);   
				return true;
			}
		}
		if(this.getFieldAtLocation(x, y)==-1){
			return true;
		}else if(this.getFieldAtLocation(x, y)==0){
			if(bgManager.getFieldAtLocation(x, y)!=-1){
				return super.touchEvent(event);
			}
			return true;
		}
		else{
			return super.touchEvent(event);
		}
	}*/
	public boolean navigationMovement(int dx, int dy, int status, int time) {
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
			screen = new ViewDeckScreen(deckid,1,1);
			UiApplication.getUiApplication().pushScreen(screen);
		}else{
			synchronized(UiApplication.getEventLock()) {
				bgManager.deleteAll();
				add(new ColorLabelField(""));
				tempList = new Vector();
				if(!comp){
					tempList.addElement(newdeck);
				}
			}
			if(comp){
				doConnect(Const.getallcompdecks);
			}else{
				doConnect(Const.getallnormaldecks);
			}
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
			int type = ((ListItemField)(f)).getType();
			int active = ((ListItemField)(f)).getActive();
			if(deckid != -1){
				screen = new ViewDeckScreen(deckid,type,active);
				UiApplication.getUiApplication().pushScreen(screen);
			}
		}
	}
}