package net.mytcg.topcar.ui;

import net.mytcg.topcar.ui.custom.ColorLabelField;
import net.mytcg.topcar.ui.custom.FixedButtonField;
import net.mytcg.topcar.ui.custom.SexyEditField;
import net.mytcg.topcar.util.Const;
import net.mytcg.topcar.util.SettingsBean;
import net.rim.device.api.io.Base64OutputStream;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class NewDeckScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	FixedButtonField create = new FixedButtonField(Const.create);
	SexyEditField deckname = new SexyEditField("");
	int categoryid = -1;
	
	public NewDeckScreen(int categoryid)
	{
		super(null);
		add(new ColorLabelField(" Deck Name:"));
		this.categoryid = categoryid;
		
		add(deckname);
		
		bgManager.setStatusHeight(exit.getContentHeight());
		
		exit.setChangeListener(this);
		create.setChangeListener(this);
		
		addButton(create);
		addButton(new FixedButtonField(""));
		addButton(exit);
	}
	
	public void process(String val) {
		System.out.println("WAWA "+val);
		int fromIndex;
		int deckid = -1;
	    if ((fromIndex = val.indexOf(Const.xml_deck_id)) != -1) {
	    	try{
	    		deckid = Integer.parseInt(val.substring(fromIndex+Const.xml_deck_id_length, val.indexOf(Const.xml_deck_id_end, fromIndex)));
	    	}catch(Exception e){deckid=-1;};
	    }
	    System.out.println("deckid "+deckid);
	    SettingsBean _instance = SettingsBean.getSettings();
		_instance.deckid = deckid;
		SettingsBean.saveSettings(_instance);
		synchronized(UiApplication.getEventLock()) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		}
	}
	
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		} else if (f == create) {
			String lbl="";
			try {
				lbl = new String(Base64OutputStream.encode(deckname.getText().getBytes(), 0, deckname.getText().length(), false, false), "UTF-8");
			} catch (Exception e) {
				lbl="";
			}
			SettingsBean _instance = SettingsBean.getSettings();
			_instance.created = true;
			SettingsBean.saveSettings(_instance);
			
			doConnect(Const.createdeck+Const.description+lbl+Const.category_id+categoryid);
		}
	}
}