package net.mytcg.kickoff.ui;

import net.mytcg.kickoff.ui.custom.ColorLabelField;
import net.mytcg.kickoff.ui.custom.FixedButtonField;
import net.mytcg.kickoff.ui.custom.ListItemField;
import net.mytcg.kickoff.util.Const;
import net.mytcg.kickoff.util.SettingsBean;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class DeckCategoryScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	ListItemField tmp = new ListItemField("Empty", -1, false, 0);
	int count = 0;

	public DeckCategoryScreen()
	{
		super(null);
		bgManager.setStatusHeight(exit.getContentHeight());
		
		add(new ColorLabelField(""));
		
		exit.setChangeListener(this); 
	
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		addButton(exit);
		
		doConnect(Const.playablecategories);
	}
	
	public void process(String val) {
		if (!(isDisplaying())) {
			int fromIndex;
			count = 0;
	    	if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
	    		setText(val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex)));
	    	} else if (((fromIndex = val.indexOf(Const.xml_categories)) != -1)) {
	    		int categoryid = -1;
	    		String categoryname = "";
	    		int endIndex = -1;
	    		String category = "";
	    		while ((fromIndex = val.indexOf(Const.xml_categoryid)) != -1){
	    			
	    			endIndex = val.indexOf(Const.xml_category_end);
	    			category = val.substring(fromIndex, endIndex+Const.xml_category_end_length);
	    			fromIndex = category.indexOf(Const.xml_categoryid);
	    			
	    			try {
	    				categoryid = Integer.parseInt(category.substring(fromIndex+Const.xml_categoryid_length, category.indexOf(Const.xml_categoryid_end, fromIndex)));
	    			} catch (Exception e) {
	    				categoryid = -1;
	    			}
	    			if ((fromIndex = category.indexOf(Const.xml_categoryname)) != -1) {
	    				categoryname = category.substring(fromIndex+Const.xml_categoryname_length, category.indexOf(Const.xml_categoryname_end, fromIndex));
	    			}
	    			val = val.substring(val.indexOf(Const.xml_category_end)+Const.xml_category_end_length);
	    			if(categoryid != -1){
		    			synchronized(UiApplication.getEventLock()) {
		    				tmp = new ListItemField(categoryname, categoryid, true, 0);
		    				tmp.setLabel(categoryname);
		        			tmp.setChangeListener(this);
		        			add(tmp);
		        			count++;
		        		}
	    			}
	    		}
	    		if (count == 1) {
	    			synchronized(UiApplication.getEventLock()) {
	    				fieldChanged(tmp, 0);
	    			}
	    		}
	    	} else {
	    		synchronized(UiApplication.getEventLock()) {
    				tmp = new ListItemField("None", -1, true, 0);
        			add(tmp);
        		}
	    	}
	    	invalidate();
	    	setDisplaying(true);
		}		
	}
	
	public void onExposed(){
		SettingsBean _instance = SettingsBean.getSettings();
		if(_instance.created){
			_instance.created = false;
			SettingsBean.saveSettings(_instance);
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		}
		if(count == 1){
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		}
	}
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		}else if(f instanceof ListItemField){
			int category = ((ListItemField)(f)).getId();
			if(category != -1){
				screen = new NewDeckScreen(category);
				UiApplication.getUiApplication().pushScreen(screen);
			}
		}
	}
}