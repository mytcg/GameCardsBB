package net.mytcg.sport.ui;

import net.mytcg.sport.ui.custom.ColorLabelField;
import net.mytcg.sport.ui.custom.FixedButtonField;
import net.mytcg.sport.ui.custom.NonEditableField;
import net.mytcg.sport.util.Const;
import net.mytcg.sport.util.SettingsBean;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class DeckDeleteScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField yes = new FixedButtonField(Const.yes);
	FixedButtonField no = new FixedButtonField(Const.no);
	ColorLabelField note = new ColorLabelField("");
	int deckid = -1;
	
	public DeckDeleteScreen(int deckid)
	{
		super(null);
		
		this.deckid = deckid;
		
		note.setText("Are you sure you want to delete this deck?");
		add(note);
		
		bgManager.setStatusHeight(Const.getButtonHeight());
		
		yes.setChangeListener(this);
		no.setChangeListener(this);
		
		addButton(yes);
		addButton(new FixedButtonField(""));
		addButton(no);
	}
	
	public void process(String val){
		int fromIndex;
    	if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
    		synchronized(UiApplication.getEventLock()) {
    			note.setText(val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex)));
    		}
    		try{
    			Thread.sleep(1000);
    		}catch(Exception e){};
    		synchronized(UiApplication.getEventLock()) {
	    		screen = null;
	    		UiApplication.getUiApplication().popScreen(this);
    		}
    	}
	}
	
	public void fieldChanged(Field f, int i) {
		if (f == yes) {
			SettingsBean _instance = SettingsBean.getSettings();
			_instance.deleted = true;
			SettingsBean.saveSettings(_instance);
			synchronized(UiApplication.getEventLock()) {
    			note.setText("Deleting Deck...");
    		}
			doConnect(Const.deletedeck+Const.deck_id+deckid);
		} else if (f == no) {
			UiApplication.getUiApplication().popScreen(this);
		}
	}
}