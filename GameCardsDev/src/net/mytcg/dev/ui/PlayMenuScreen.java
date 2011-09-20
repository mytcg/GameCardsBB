package net.mytcg.dev.ui;

import net.mytcg.dev.ui.custom.ColorLabelField;
import net.mytcg.dev.ui.custom.FixedButtonField;
import net.mytcg.dev.ui.custom.ListItemField;
import net.mytcg.dev.util.Const;
import net.mytcg.dev.util.SettingsBean;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class PlayMenuScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	int categoryId = 10;
	int deckId = -1;
	
	private ListItemField playpc = new ListItemField("Play versus PC", 1, false, 0);
	private ListItemField playonline = new ListItemField("Play Online", 2, false, 0);
	private ListItemField playfriend = new ListItemField("Play Against Friend", 3, false, 0);
	
	public PlayMenuScreen(int categoryId, int deckId) {
		super(null);
		this.categoryId = categoryId;
		this.deckId = deckId;
		bgManager.setStatusHeight(exit.getContentHeight());
		exit.setChangeListener(this);
		
		add(new ColorLabelField(""));
		
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		addButton(exit);
		
		playpc.setChangeListener(this);
		playonline.setChangeListener(this);
		playfriend.setChangeListener(this);
		
		add(playpc);
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
		} else if (f == playpc) {
			screen = new GamePlayScreen(true, categoryId, 1, false, deckId);
			UiApplication.getUiApplication().pushScreen(screen);
		} else if (f == playonline) {
			screen = new GamePlayScreen(true, categoryId, 2, false, deckId);
			UiApplication.getUiApplication().pushScreen(screen);
		} else if (f == playfriend) {
			screen = new GamePlayScreen(true, categoryId, 2, true, deckId);
			UiApplication.getUiApplication().pushScreen(screen);
		} 
	}
}