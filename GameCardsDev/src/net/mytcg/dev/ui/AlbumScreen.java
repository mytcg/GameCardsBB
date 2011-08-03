package net.mytcg.dev.ui;

import net.mytcg.dev.ui.custom.FixedButtonField;
import net.mytcg.dev.ui.custom.ListItemField;
import net.mytcg.dev.util.Const;
import net.mytcg.dev.util.SettingsBean;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class AlbumScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	
	ListItemField tmp = new ListItemField("Empty", -1, false, 0);
	
	int id = -1;
	int type = 0;
	boolean update = true;
	int deckid = -1;
	
	public void process(String val) {
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
	    			clear();
	    		}
	    		int albumid = -1;
	    		int updated = 0;
	    		String albumname = "";
	    		boolean hasCards = false;
	    		int endIndex = -1;
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
	        			add(tmp);
	        		}
	    		}
	    	}
	    	invalidate();
	    	_instance = null;
	    	setDisplaying(true);
		}		
	}
	public AlbumScreen(int type) {
		super(null);
		this.type = type;
		bgManager.setStatusHeight(exit.getContentHeight());
		
		exit.setChangeListener(this);
		
		addButton(exit);
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		process(SettingsBean.getSettings().getUsercategories());
		
		doConnect(Const.usercategories+Const.second+SettingsBean.getSettings().getLoaded());
	}
	public AlbumScreen(int id, int type) {
		super(null);
		this.type = type;
		bgManager.setStatusHeight(exit.getContentHeight());
		
		exit.setChangeListener(this);
		
		addButton(exit);
		
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		
		this.id = id;
		process(SettingsBean.getSettings().getUsercategories(id));
		doConnect(Const.subcategories+id+Const.second+SettingsBean.getSettings().getLoaded());
	}
	public AlbumScreen(int id, int type, int deckid) {
		super(null);
		this.type = type;
		this.deckid = deckid;
		bgManager.setStatusHeight(exit.getContentHeight());
		
		exit.setChangeListener(this);
		
		addButton(exit);
		
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		
		this.id = id;
		process(SettingsBean.getSettings().getUsercategories(id));
		doConnect(Const.subcategories+id+Const.second+SettingsBean.getSettings().getLoaded());
	}
	protected void onExposed() {
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
		} else {
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