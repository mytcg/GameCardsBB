package net.mytcg.dex.ui;

import net.mytcg.dex.ui.custom.ColorLabelField;
import net.mytcg.dex.ui.custom.FixedButtonField;
import net.mytcg.dex.ui.custom.SexyEditField;
import net.mytcg.dex.util.Const;
import net.mytcg.dex.util.SettingsBean;
import net.rim.device.api.io.Base64OutputStream;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.EditField;

public class RegistrationScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField register = new FixedButtonField(Const.register);
	FixedButtonField exit = new FixedButtonField(Const.exit);
	
	SexyEditField fullname = new SexyEditField("");
	SexyEditField username = new SexyEditField("", EditField.FILTER_URL, 36);
	SexyEditField cell = new SexyEditField("", EditField.FILTER_NUMERIC, 36);
	SexyEditField email = new SexyEditField("", EditField.FILTER_EMAIL, 36);
	SexyEditField password = new SexyEditField("");
	
	public boolean onClose() {
		System.exit(0);
		return true;
	}
	public void process(String val) {
		int fromIndex;
    	if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
    		super.process(val);
    	} else if (((fromIndex = val.indexOf(Const.xml_usercategories)) != -1)) {
    		SettingsBean _instance = SettingsBean.getSettings();
    		_instance.setAuthenticated(true);
    		_instance.setUsername(username.getText());
    		
    		String password64="";
			try {
				password64 = new String(Base64OutputStream.encode(password.getText().getBytes(), 0, password.getText().length(), false, false), "UTF-8");
			} catch (Exception e) {
				
			}
			_instance.setPassword(password64);
			
    		SettingsBean.saveSettings(_instance);
    		
    		synchronized(UiApplication.getEventLock()) {
				close();
				Const.GOTOSCREEN = Const.ALBUMSCREEN;
				Const.FROMSCREEN = Const.LOGINSCREEN;
	    		Const.app.nextScreen();
			}
		}
	}
	
	public RegistrationScreen()
	{
		super(null);
		
		SettingsBean _instance = SettingsBean.getSettings();
		_instance.setAuthenticated(false);
		SettingsBean.saveSettings(_instance);
		
		add(new ColorLabelField(Const.name));
		add(fullname);
		add(new ColorLabelField(Const.surname));
		add(username);
		add(new ColorLabelField(Const.cell));
		add(cell);
		add(new ColorLabelField(Const.age));
		add(email);
		add(new ColorLabelField(Const.gender));
		add(password);
		
		//bgManager.setStatusHeight(Const.getButtonHeight());
		
		exit.setChangeListener(this);
		register.setChangeListener(this);
		
		
		addButton(register);
		addButton(new FixedButtonField(""));
		addButton(exit);
	}
	
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			System.exit(0);
		} else if (f == register) {
			SettingsBean _instance = SettingsBean.getSettings();
			_instance.setUsername(cell.getText());
			SettingsBean.saveSettings(_instance);
			_instance = null;
			
			if ((cell.getText() == null)||(cell.getText().length() <= 0)) {
				setText("Cell Number cannot be blank.");
			} else if ((username.getText() == null)||username.getText().length() <= 0) {
				setText("Username cannot be blank.");
			} else if ((password.getText() == null)||password.getText().length() <= 0) {
				setText("Password cannot be blank.");
			} else if ((email.getText() == null)||email.getText().length() <= 0) {
				setText("Email cannot be blank.");
			} else {
				String full64="";
				try {
					full64 = new String(Base64OutputStream.encode(fullname.getText().getBytes(), 0, fullname.getText().length(), false, false), "UTF-8");
				} catch (Exception e) {
					
				}
				String url = Const.registeruser+Const.username+full64+Const.userfullname+username.getText()+Const.usercell+cell.getText()+Const.useremail+email.getText()+Const.userpassword+password.getText();
				url = Const.removeSpaces(url);
				doConnect(url, false);
			}
		}
	}
}