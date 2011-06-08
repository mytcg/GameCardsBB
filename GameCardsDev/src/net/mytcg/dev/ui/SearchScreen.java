package net.mytcg.dev.ui;

import net.mytcg.dev.ui.custom.ColorLabelField;
import net.mytcg.dev.ui.custom.FixedButtonField;
import net.mytcg.dev.ui.custom.SexyEditField;
import net.mytcg.dev.util.Const;
import net.mytcg.dev.util.SettingsBean;
import net.rim.device.api.io.Base64OutputStream;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class SearchScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField home = new FixedButtonField(Const.home);
	FixedButtonField search = new FixedButtonField(Const.search);
	SexyEditField number = new SexyEditField("");
	ColorLabelField lbl = new ColorLabelField(Const.term);
	
	public SearchScreen(AppScreen screen)
	{
		super(screen);
		
		add(lbl);
		add(number);
		
		bgManager.setStatusHeight(home.getContentHeight());
		
		home.setChangeListener(this);
		search.setChangeListener(this);
		
		addButton(home);
		addButton(new FixedButtonField(""));
		addButton(search);
	}
	public void process(String val) {
		synchronized(UiApplication.getEventLock()) {
			
			//screen = null;
			//UiApplication.getUiApplication().popScreen(this);
			
			screen = new AlbumListScreen(val);
			UiApplication.getUiApplication().pushScreen(screen);
		}
	}
	
	public void fieldChanged(Field f, int i) {
		if (f == home) {
			
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
			
			//screen = new AlbumScreen();
			//UiApplication.getUiApplication().pushScreen(screen);
		} else if (f == search) {
			String search64="";
			try {
				search64 = new String(Base64OutputStream.encode(number.getText().getBytes(), 0, number.getText().length(), false, false), "UTF-8");
			} catch (Exception e) {
				
			}
			doConnect(Const.seek+search64+Const.height+Const.getCardHeight()+Const.second+SettingsBean.getSettings().getLoaded());
		}
	}
}