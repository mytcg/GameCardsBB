package net.mytcg.topcar.ui;

import net.mytcg.topcar.ui.custom.ColorLabelField;
import net.mytcg.topcar.ui.custom.FixedButtonField;
import net.mytcg.topcar.ui.custom.ListItemField;
import net.mytcg.topcar.util.Const;
import net.mytcg.topcar.util.SettingsBean;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class ShopCategoriesScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	
	ListItemField tmp = new ListItemField("Empty", -1, false, 0);
	
	boolean update = true;
	boolean freebie = false;
	
	public void process(String val) {
		SettingsBean _instance = SettingsBean.getSettings();
		System.out.println(val);
		if (update) {
			SettingsBean.saveSettings(_instance);
		}
		
		if ((!(isDisplaying()))||(update)) {
			int fromIndex;
	    	if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
	    		setText(val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex)));
	    	} else if (((fromIndex = val.indexOf(Const.xml_productcategories)) != -1)) {
	    		int albumid = -1;
	    		boolean empty = true;
	    		String albumname = "";
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
	    	_instance = null;
	    	setDisplaying(true);
		}		
	}
	public ShopCategoriesScreen(boolean freebie) {
		super(null);
		this.freebie = freebie;
		bgManager.setStatusHeight(exit.getContentHeight());
		
		exit.setChangeListener(this);
		
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		addButton(exit);
		
		if(freebie){
			add(new ColorLabelField("To say thank you for joining we have given you 300 credits and you get to choose a free booster to start out with"));
			doConnect(Const.freebiecategories);
		}else{
			add(new ColorLabelField("Choose a category."));
			doConnect(Const.productcategories);
		}
	}
	public ShopCategoriesScreen() {
		this(false);
	}
	
	public boolean onClose() {
		screen = null;
		UiApplication.getUiApplication().popScreen(this);
		return true;
	}
	
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			if(freebie){
				close();
				Const.app.previousScreen();
			}else{
				screen = null;
				UiApplication.getUiApplication().popScreen(this);
			}
		} else if(f instanceof ListItemField){
			int category = ((ListItemField)(f)).getId();
			screen = new ShopProductsScreen(category, freebie);
			UiApplication.getUiApplication().pushScreen(screen);
		}
	}
}