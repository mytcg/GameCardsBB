package net.mytcg.fhm.ui;

import net.mytcg.fhm.ui.custom.FixedButtonField;
import net.mytcg.fhm.util.Const;
import net.mytcg.fhm.util.SettingsBean;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BitmapField;


public class LogOutScreen extends AppScreen implements FieldChangeListener
{
	private FixedButtonField exit = new FixedButtonField(Const.back);
	private FixedButtonField logout = new FixedButtonField(Const.logOut);
	
	private Bitmap log_out;
	
	public LogOutScreen() {
		super(null);
		log_out = Const.getLogOut();
		bgManager.setStatusHeight(exit.getContentHeight());
		exit.setChangeListener(this);
		logout.setChangeListener(this);
		bgManager.add(new BitmapField(log_out, BitmapField.FIELD_HCENTER | BitmapField.FIELD_VCENTER));
		addButton(logout);
		addButton(new FixedButtonField(""));
		addButton(exit);
	}
	
	public void process(String val) {
		synchronized(UiApplication.getEventLock()) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		}
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
		} else if (f == logout) {
			close();
			Const.GOTOSCREEN = Const.LOGINSCREEN;
			Const.FROMSCREEN = Const.LOGINSCREEN;
			Const.app.previousScreen();
			System.exit(0);
		}
	}
}