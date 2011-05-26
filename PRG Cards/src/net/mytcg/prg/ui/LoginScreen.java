package net.mytcg.prg.ui;

import net.mytcg.prg.custom.FixedButtonField;
import net.mytcg.prg.custom.SexyEditField;
import net.mytcg.prg.util.Const;
import net.mytcg.prg.util.SettingsBean;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;

public class LoginScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField login = new FixedButtonField(Const.login);
	FixedButtonField exit = new FixedButtonField(Const.exit);
	
	SexyEditField username = new SexyEditField("", EditField.FILTER_NUMERIC, 36);
	
	public void process(String val) {
		int fromIndex;
    	if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
    		super.process(val);
    	} else if (((fromIndex = val.indexOf(Const.xml_usercategories)) != -1)) {
    		SettingsBean _instance = SettingsBean.getSettings();
    		_instance.setAuthenticated(true);
    		SettingsBean.saveSettings(_instance);
    		synchronized(UiApplication.getEventLock()) {
				close();
				Const.GOTOSCREEN = Const.ALBUMSCREEN;
				Const.FROMSCREEN = Const.LOGINSCREEN;
	    		Const.app.nextScreen();
			}
		}
	}
	
	public boolean onClose() {
		System.exit(0);
		return true;
	}
	
	public LoginScreen()
	{
		super(null);
		
		SettingsBean _instance = SettingsBean.getSettings();
		_instance.setAuthenticated(false);
		SettingsBean.saveSettings(_instance);
		
		add(new LabelField(Const.cell));
		add(username);
		
		bgManager.setStatusHeight(Const.getButtonHeight());
		
		exit.setChangeListener(this);
		login.setChangeListener(this);
		
		addButton(exit);
		addButton(new FixedButtonField(""));
		addButton(login);
	}
	
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			System.exit(0);
		} else if (f == login) {
			if ((username.getText() == null)||(username.getText().length() <= 0)) {
				setText("Cell Number cannot be blank.");
			} else {
				SettingsBean _instance = SettingsBean.getSettings();
				_instance.setUsername(username.getText());
				SettingsBean.saveSettings(_instance);
				_instance = null;
				doConnect(Const.usercategories);
			}
		}
	}
}