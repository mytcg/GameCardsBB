package net.mytcg.dev.ui;

import java.util.Vector;

import net.mytcg.dev.ui.custom.ColorLabelField;
import net.mytcg.dev.ui.custom.FixedButtonField;
import net.mytcg.dev.ui.custom.ListItemField;
import net.mytcg.dev.ui.custom.PageNumberField;
import net.mytcg.dev.util.Card;
import net.mytcg.dev.util.Const;
import net.mytcg.dev.util.SettingsBean;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
//import net.rim.device.api.ui.TouchEvent;
import net.rim.device.api.ui.UiApplication;

public class AlbumScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	
	ListItemField tmp = new ListItemField("Empty", -1, false, 0);
	PageNumberField pageNumber = new PageNumberField("Page 1/1");
	
	int id = -1;
	int type = 0;
	boolean update = true;
	int deckid = -1;
	int friendid = -1;
	Card card = null;
	Vector pages = new Vector();
	int currentPage = 0;
	
	public void process(String val) {
		int listSize = (Const.getUsableHeight()) / Const.getButtonHeight();
		int listCounter = 0;
		pages = new Vector();
		Vector tempList = new Vector();
		SettingsBean _instance = SettingsBean.getSettings();
		if (id >= 0) {
    		update = _instance.setUsercategories(val, id);
		} else {
			update = _instance.setUsercategories(val);
		}
		
		if (update) {
			SettingsBean.saveSettings(_instance);
		}
		
		if ((!(isDisplaying()))||(update)) {
			int fromIndex;
	    	if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
	    		setText(val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex)));
	    	} else if (((fromIndex = val.indexOf(Const.xml_usercategories)) != -1)) {
	    		synchronized(UiApplication.getEventLock()) {
	    			add(new ColorLabelField(""));
	    			clear();
	    		}
	    		int albumid = -1;
	    		int updated = 0;
	    		String albumname = "";
	    		boolean hasCards = false;
	    		int endIndex = -1;
	    		String album = "";
	    		while ((fromIndex = val.indexOf(Const.xml_albumid)) != -1){
	    			if(listCounter >= listSize){
        				pages.addElement(tempList);
        				tempList = new Vector();
        				listCounter=0;
        			}
	    			endIndex = val.indexOf(Const.xml_album_end);
	    			album = val.substring(fromIndex, endIndex+Const.xml_album_end_length);
	    			fromIndex = album.indexOf(Const.xml_albumid);
	    			
	    			try {
	    				albumid = Integer.parseInt(album.substring(fromIndex+Const.xml_albumid_length, album.indexOf(Const.xml_albumid_end, fromIndex)));
	    			} catch (Exception e) {
	    				albumid = -1;
	    			}
	    			if ((fromIndex = album.indexOf(Const.xml_hascards)) != -1) {
	    				String boolcmp = album.substring(fromIndex+Const.xml_hascards_length, album.indexOf(Const.xml_hascards_end, fromIndex));
	    				if (boolcmp.compareTo(Const.tru)==0) {
	    					hasCards = true;
	    				} else {
	    					hasCards = false;
	    				}
	    			}
	    			if ((fromIndex = album.indexOf(Const.xml_updated)) != -1) {
	    				try {
		    				updated = Integer.parseInt(album.substring(fromIndex+Const.xml_updated_length, album.indexOf(Const.xml_updated_end, fromIndex)));
		    			} catch (Exception e) {
		    				updated = -1;
		    			}
	    			}
	    			if ((fromIndex = album.indexOf(Const.xml_albumname)) != -1) {
	    				albumname = album.substring(fromIndex+Const.xml_albumname_length, album.indexOf(Const.xml_albumname_end, fromIndex));
	    			}
	    			val = val.substring(val.indexOf(Const.xml_album_end)+Const.xml_album_end_length);
	    			
	    			synchronized(UiApplication.getEventLock()) {
	    				tmp = new ListItemField(albumname, albumid, hasCards, updated);
	        			tmp.setChangeListener(this);
	        			tempList.addElement(tmp);
	        			listCounter++;
	        		}
	    		}
	    		pages.addElement(tempList);
	    		synchronized(UiApplication.getEventLock()) {
	    			if(pages.size()<=1){
	    				bgManager.setArrowMode(false);
	    			} else {
	    				bgManager.setArrowMode(true);
	    			}
	    			
	    			pageNumber.setLabel("Page 1/"+pages.size());
	    			ListItemField[] temp = new ListItemField[((Vector)pages.elementAt(0)).size()];
	    			((Vector)pages.elementAt(0)).copyInto(temp);
	    			bgManager.deleteAll();
		    		bgManager.addAll(temp);
		    	}
	    	}
	    	invalidate();
	    	_instance = null;
	    	//setDisplaying(true);
		}		
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
	
	public AlbumScreen(int type) {
		super(null);
		this.type = type;
		bgManager.setStatusHeight(exit.getContentHeight());
		bgManager.setArrowMode(true);
		add(new ColorLabelField(""));
		
		exit.setChangeListener(this);
		
		addButton(new FixedButtonField(""));
		addButton(pageNumber);
		addButton(exit);
		process(SettingsBean.getSettings().getUsercategories());
		doConnect(Const.usercategories+Const.second+SettingsBean.getSettings().getLoaded());
	}
	public AlbumScreen(int type, Card card) {
		super(null);
		this.type = type;
		this.card = card;
		bgManager.setStatusHeight(exit.getContentHeight());
		bgManager.setArrowMode(true);
		add(new ColorLabelField(""));
		
		exit.setChangeListener(this);
		
		addButton(new FixedButtonField(""));
		addButton(pageNumber);
		addButton(exit);
		process(SettingsBean.getSettings().getUsercategories());
		doConnect(Const.usercategories+Const.second+SettingsBean.getSettings().getLoaded());
	}
	public AlbumScreen(int id, int type) {
		super(null);
		this.type = type;
		bgManager.setStatusHeight(exit.getContentHeight());
		bgManager.setArrowMode(true);
		
		exit.setChangeListener(this);
		
		addButton(new FixedButtonField(""));
		addButton(pageNumber);
		addButton(exit);
		if(type == 4){
			friendid = id;
		}else{
			this.id = id;
			process(SettingsBean.getSettings().getUsercategories(id));
		}
		if(type == 4){
			doConnect(Const.usercategories+Const.friendid+friendid+Const.second+SettingsBean.getSettings().getLoaded());
		} else{
			doConnect(Const.subcategories+id+Const.second+SettingsBean.getSettings().getLoaded());
		}
	}
	public AlbumScreen(int id, int type, Card card) {
		super(null);
		this.type = type;
		this.card = card;
		bgManager.setStatusHeight(exit.getContentHeight());
		bgManager.setArrowMode(true);
		add(new ColorLabelField(""));
		
		exit.setChangeListener(this);
		
		addButton(new FixedButtonField(""));
		addButton(pageNumber);
		addButton(exit);
		
		this.id = id;
		process(SettingsBean.getSettings().getUsercategories(id));
		doConnect(Const.subcategories+id+Const.second+SettingsBean.getSettings().getLoaded());
	}
	public AlbumScreen(int id, int type, int deckid) {
		super(null);
		this.type = type;
		if(type==4){
			this.friendid = deckid;
		} else{
			this.deckid = deckid;
		}
		bgManager.setStatusHeight(exit.getContentHeight());
		bgManager.setArrowMode(true);
		add(new ColorLabelField(""));
		
		exit.setChangeListener(this);
		
		addButton(new FixedButtonField(""));
		addButton(pageNumber);
		addButton(exit);
		
		this.id = id;
		process(SettingsBean.getSettings().getUsercategories(id));
		doConnect(Const.subcategories+id+Const.friendid+friendid+Const.second+SettingsBean.getSettings().getLoaded());
	}
	protected void onExposed() {
		invalidate();
		if(type == 1 || type == 3){
			UiApplication.getUiApplication().popScreen(this);
		}
		if (!isVisible()) {
			if (id >= 0) {
				doConnect(Const.subcategories+id+Const.second+SettingsBean.getSettings().getLoaded());
			} else {
				doConnect(Const.usercategories+Const.second+SettingsBean.getSettings().getLoaded());
			}
		}
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
		} else if(type == 3){
			int id = ((ListItemField)(f)).getId();
			boolean hascards = ((ListItemField)(f)).hasCards();
			if (!hascards) {
				screen = new AlbumScreen(id, type, deckid);
				UiApplication.getUiApplication().pushScreen(screen);
			} else {
				screen = new AddCardToDeckListScreen(deckid,id);
				UiApplication.getUiApplication().pushScreen(screen);
			}
		} else if(type == 2){
			int id = ((ListItemField)(f)).getId();
			boolean hascards = ((ListItemField)(f)).hasCards();
			if (!hascards) {
				screen = new AlbumScreen(id, type, card);
				UiApplication.getUiApplication().pushScreen(screen);
			} else {
				screen = new AlbumListScreen(id, type, card);
				UiApplication.getUiApplication().pushScreen(screen);
			}
		} else if(type == 4){
			int id = ((ListItemField)(f)).getId();
			boolean hascards = ((ListItemField)(f)).hasCards();
			if (!hascards) {
				screen = new AlbumScreen(id, type, friendid);
				UiApplication.getUiApplication().pushScreen(screen);
			} else {
				screen = new AlbumListScreen(id, type, friendid);
				UiApplication.getUiApplication().pushScreen(screen);
			}
		}else {
			int id = ((ListItemField)(f)).getId();
			boolean hascards = ((ListItemField)(f)).hasCards();
			if (!hascards) {
				screen = new AlbumScreen(id, type);
				UiApplication.getUiApplication().pushScreen(screen);
			} else {
				screen = new AlbumListScreen(id, type);
				UiApplication.getUiApplication().pushScreen(screen);
			}
		}
	}
}