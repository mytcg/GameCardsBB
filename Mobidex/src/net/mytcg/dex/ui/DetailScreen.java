package net.mytcg.dex.ui;

import java.util.Vector;

import net.mytcg.dex.ui.custom.ColorLabelField;
import net.mytcg.dex.ui.custom.FixedButtonField;
import net.mytcg.dex.ui.custom.ListItemField;
import net.mytcg.dex.ui.custom.ListLabelField;
import net.mytcg.dex.ui.custom.SexyEditField;
import net.mytcg.dex.util.Const;
import net.mytcg.dex.util.SettingsBean;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.CheckboxField;
import net.rim.device.api.ui.component.SeparatorField;

public class DetailScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	SexyEditField tmp = new SexyEditField("");
	ColorLabelField lbltop = null;
	ListItemField lblNotifications = new ListItemField(Const.notification, Const.NOTIFICATIONS, false, 0);
	ListLabelField lblnote = new ListLabelField("No notifications yet.");
	ListItemField lblTransactions = new ListItemField("Last Transactions", -1, false, 0);
	ColorLabelField lbltmp = new ColorLabelField("");
	CheckboxField cbxtmp = new CheckboxField("",false,CheckboxField.NON_FOCUSABLE);
	Vector answers = new Vector();
	int count =0;
	int credits = 0;
	
	public DetailScreen(AppScreen screen, int screenType)
	{
		super(screen);
		bgManager.setStatusHeight(exit.getContentHeight());
		if(screenType == Const.NOTIFICATIONSCREEN){
			add(lblNotifications);
			lblNotifications.setFocusable(false);
			addButton(new FixedButtonField(""));
			doConnect(Const.notifications);
		}
		exit.setChangeListener(this);
		
		
		addButton(new FixedButtonField(""));
		addButton(exit);
	}
	
	public void process(String val) {
		if (!(isDisplaying())) {
			int fromIndex;
	    	if (((fromIndex = val.indexOf(Const.xml_notifications)) != -1)) {
	    		int noteid = -1;
	    		String desc = "";
	    		String date = "";
	    		SettingsBean _instance = SettingsBean.getSettings();
	    		_instance.noteloaded();
	    		_instance.notifications = false;
	    		SettingsBean.saveSettings(_instance);
	    		int endIndex = -1;
	    		String note = "";
	    		while ((fromIndex = val.indexOf(Const.xml_id)) != -1){
	    			
	    			endIndex = val.indexOf(Const.xml_note_end);
	    			try{
	    				note = val.substring(fromIndex, endIndex+Const.xml_note_end_length);
	    			}catch(Exception e){};
	    			fromIndex = note.indexOf(Const.xml_id);
	    			
	    			try {
	    				noteid = Integer.parseInt(note.substring(fromIndex+Const.xml_id_length, note.indexOf(Const.xml_id_end, fromIndex)));
	    			} catch (Exception e) {
	    				noteid = -1;
	    			}
	    			if ((fromIndex = note.indexOf(Const.xml_descr)) != -1) {
	    				desc = note.substring(fromIndex+Const.xml_descr_length, note.indexOf(Const.xml_descr_end, fromIndex));
	    			}
	    			if ((fromIndex = note.indexOf(Const.xml_date)) != -1) {
	    				date = note.substring(fromIndex+Const.xml_date_length, note.indexOf(Const.xml_date_end, fromIndex));
	    			}
	    			val = val.substring(val.indexOf(Const.xml_note_end)+Const.xml_note_end_length);
	    			if(noteid != -1){
	    				try{
	    					lblnote = new ListLabelField(date+": "+desc);
			    			synchronized(UiApplication.getEventLock()) {
			    				add(lblnote);
			    				add(new SeparatorField());
			    			}
	    				}catch(Exception e){};
	    			} 
	    		}
	    		if(noteid == -1){
    				synchronized(UiApplication.getEventLock()) {
	    				add(lblnote);
	    			}
    			}
	    	}
	    	invalidate();
	    	setDisplaying(true);
		}		
	}
	
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		}
	}
}