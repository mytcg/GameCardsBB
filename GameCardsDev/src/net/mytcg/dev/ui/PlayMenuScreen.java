package net.mytcg.dev.ui;

import net.mytcg.dev.ui.custom.FixedButtonField;
import net.mytcg.dev.ui.custom.ListItemField;
import net.mytcg.dev.util.Card;
import net.mytcg.dev.util.Const;
import net.mytcg.dev.util.SettingsBean;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class PlayMenuScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	
	public void process(String val) {
		
	}
	
	private ListItemField playpceasy = new ListItemField("Play versus PC (Easy)", 1, false, 0);
	private ListItemField playpchard = new ListItemField("Play versus PC (Hard)", 2, false, 0);
	private ListItemField playonline = new ListItemField("Play Online", 3, false, 0);
	private ListItemField playfriend = new ListItemField("Play Against Friend", 4, false, 0);
	
	public PlayMenuScreen(int deckid) {
		super(null);
		
		bgManager.setStatusHeight(exit.getContentHeight());
		exit.setChangeListener(this);
		
		addButton(exit);
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		
		playpceasy.setChangeListener(this);
		playpchard.setChangeListener(this);
		playonline.setChangeListener(this);
		playfriend.setChangeListener(this);
		
		add(playpceasy);
		add(playpchard);
		add(playonline);
		add(playfriend);
	}
	protected void onExposed() {
		if(SettingsBean.getSettings().created){
			UiApplication.getUiApplication().popScreen(this);
		}
	}
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		} else if (f == playpceasy) {
			
		} else if (f == playpchard) {
		
		} else if (f == playonline) {
			
		} else if (f == playfriend) {
			
		} 
	}
}