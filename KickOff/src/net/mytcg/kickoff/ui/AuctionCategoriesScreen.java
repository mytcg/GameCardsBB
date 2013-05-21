package net.mytcg.kickoff.ui;

import net.mytcg.kickoff.ui.custom.ColorLabelField;
import net.mytcg.kickoff.ui.custom.FixedButtonField;
import net.mytcg.kickoff.ui.custom.ListItemField;
import net.mytcg.kickoff.util.Const;
import net.mytcg.kickoff.util.SettingsBean;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class AuctionCategoriesScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	
	ListItemField tmp = new ListItemField("Empty", -1, false, 0);
	
	int id = -1;
	boolean update = true;
	
	public void process(String val) {
		//SettingsBean _instance = SettingsBean.getSettings();
		//if (id >= 0) {
    	//	update = _instance.setUsercategories(val, id);
		//} else {
		//	update = _instance.setUsercategories(val);
		//}
		
		//if (update) {
		//	SettingsBean.saveSettings(_instance);
		//}
		
		if ((!(isDisplaying()))||(update)) {
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
	    	//_instance = null;
	    	setDisplaying(true);
		}		
	}
	public AuctionCategoriesScreen() {
		super(null);
		add(new ColorLabelField(""));
		bgManager.setStatusHeight(exit.getContentHeight());
		
		exit.setChangeListener(this);
		
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		addButton(exit);
		//process(SettingsBean.getSettings().getUsercategories());
		
		doConnect(Const.auctioncategories);
	}
	
	public AuctionCategoriesScreen(int categoryId) {
		super(null);
		add(new ColorLabelField(""));
		bgManager.setStatusHeight(exit.getContentHeight());
		
		exit.setChangeListener(this);
		
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		addButton(exit);
		process(SettingsBean.getSettings().getUsercategories());
		
		doConnect(Const.auctioncategories+"&categoryId="+categoryId);
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
			
			int category = ((ListItemField)(f)).getId();
			screen = new AuctionListScreen(category, 0);
			UiApplication.getUiApplication().pushScreen(screen);
		}
	}
}