package net.mytcg.dex.ui;

import java.util.Date;

import net.mytcg.dex.ui.custom.FixedButtonField;
import net.mytcg.dex.ui.custom.ListItemField;
import net.mytcg.dex.util.Const;
import net.mytcg.dex.util.SettingsBean;
import net.mytcg.dex.ui.DetailScreen;
import net.rim.device.api.io.http.HttpDateParser;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class AlbumScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	
	ListItemField tmp = new ListItemField("Empty", -1, false, 0);
	ListItemField notifications = new ListItemField("Empty", -1, false, 0);
	
	int id = -999;
	boolean update = true;
	boolean collapse = false;
	boolean initialcheck = true;
	
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
	    			tmp = new ListItemField(Const.search, Const.SEARCH, false, 0);
	    			tmp.setChangeListener(this);
	    			add(tmp);
	    			notifications = new ListItemField((_instance.notifications?"*":"")+Const.notification, Const.NOTIFICATIONS, false, 0);
	    			notifications.setChangeListener(this);
	    			add(notifications);
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
	    		synchronized(UiApplication.getEventLock()) {
		    		//tmp = new ListItemField(Const.redeem, Const.REDEEM, false, 0);
	    			//tmp.setChangeListener(this);
	    			//add(tmp);
					tmp = new ListItemField(Const.logout, Const.LOGOUT, false, 0);
					tmp.setChangeListener(this);
					add(tmp);
					invalidate();
				}
	    	} else if ((fromIndex = val.indexOf(Const.xml_notedate)) != -1) {
				String notedate = val.substring(fromIndex+Const.xml_notedate_length, val.indexOf(Const.xml_notedate_end, fromIndex));
				Date date = new Date(HttpDateParser.parse(notedate));
				_instance = SettingsBean.getSettings();
				System.out.println("date.getTime() " + date.getTime());
				System.out.println("_instance.getNoteLoaded() " + _instance.getNoteLoaded());
				if(date.getTime()/1000>_instance.getNoteLoaded()){
					_instance.notifications = true;
					synchronized(UiApplication.getEventLock()) {
						notifications.setLabel((_instance.notifications?"*":"")+Const.notification);
					}
					if(initialcheck){
						initialcheck = false;
						synchronized(UiApplication.getEventLock()) {
							screen = new DetailScreen(this, Const.NOTIFICATIONSCREEN);
							UiApplication.getUiApplication().pushScreen(screen);
						}
					}
				}
			}
	    	_instance = null;
	    	setDisplaying(true);
		}		
	}
	public AlbumScreen() {
		super(null);
		bgManager.setStatusHeight(exit.getContentHeight());
		
		if (screen == null) {
			exit.setLabel(Const.close);
		}
		
		exit.setChangeListener(this);
		
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		addButton(exit);
		process(SettingsBean.getSettings().getUsercategories());
		
		doConnect(Const.usercategories+Const.second+SettingsBean.getSettings().getLoaded());
		SettingsBean _instance = SettingsBean.getSettings();
		if(_instance.notifications == false){
			doConnect("notedate=1");
		}
	}
	public AlbumScreen(int id) {
		super(null);
		
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
		if (id >= 0) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		} else {
			System.exit(0);
		}
		return true;
	}
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			if (id >= 0) {
				screen = null;
				UiApplication.getUiApplication().popScreen(this);
			} else {
				System.exit(0);
			}
		} else {
			int id = ((ListItemField)(f)).getId();
			boolean hascards = ((ListItemField)(f)).hasCards();
			if (id == Const.LOGOUT) {
				close();
				Const.GOTOSCREEN = Const.LOGINSCREEN;
				Const.FROMSCREEN = Const.LOGINSCREEN;
				Const.app.previousScreen();
			} else if (id == Const.SEARCH){
				screen = new SearchScreen(this);
				//UiApplication.getUiApplication().popScreen(this);
				UiApplication.getUiApplication().pushScreen(screen);
			} else if (id == Const.REDEEM){
				screen = new RedeemScreen(this);
				UiApplication.getUiApplication().pushScreen(screen);
			} else if(f == notifications){
				screen = new DetailScreen(this, Const.NOTIFICATIONSCREEN);
				UiApplication.getUiApplication().pushScreen(screen);
			} else {
				if (!hascards) {
					screen = new AlbumScreen(id);
					UiApplication.getUiApplication().pushScreen(screen);
				} else {
					screen = new AlbumListScreen(id);
					UiApplication.getUiApplication().pushScreen(screen);
				}
			}
		}
	}
}