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

public class LoginScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField login = new FixedButtonField(Const.login);
	FixedButtonField exit = new FixedButtonField(Const.exit);
	
	SexyEditField username = new SexyEditField("");//, EditField.FILTER_NUMERIC, 36);
	SexyEditField password = new SexyEditField("");
	
	public void process(String val) {
		System.out.println("val "+val);
		int fromIndex;
		String freebie = "-1";
    	if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
    		super.process(val);
    	} else if ((fromIndex = val.indexOf(Const.xml_userdetails)) != -1) {
    		if (Const.processUserDetails(val)) {
    			if ((fromIndex = val.indexOf(Const.xml_freebie)) != -1) {
        			freebie = (val.substring(fromIndex+Const.xml_freebie_length, val.indexOf(Const.xml_freebie_end, fromIndex)));
        		}
				synchronized(UiApplication.getEventLock()) {
					close();
					if(freebie.equals("0")){
						Const.GOTOSCREEN = Const.SHOP;
					}else{
						Const.GOTOSCREEN = Const.MENUSCREEN;
					}
					Const.FROMSCREEN = Const.LOGINSCREEN;
		    		Const.app.nextScreen();
				}
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
		_instance.lastloaded();
		SettingsBean.saveSettings(_instance);
		
		add(new ColorLabelField(Const.user));
		add(username);
		username.setText("keldarn");
		add(new ColorLabelField(Const.password));
		add(password);
		password.setText("aaaaaa");
		
		bgManager.setStatusHeight(Const.getButtonHeight());
		
		exit.setChangeListener(this);
		login.setChangeListener(this);
		
		addButton(login);
		addButton(new FixedButtonField(""));
		addButton(exit);
	}
	
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			System.exit(0);
		} else if (f == login) {
			if ((username.getText() == null)||(username.getText().length() <= 0)) {
				setText("Username cannot be blank.");
			} else {
				String password64="";
				try {
					password64 = new String(Base64OutputStream.encode((password.getText().trim()).getBytes(), 0, (password.getText().trim()).length(), false, false), "UTF-8");
				} catch (Exception e) {
					
				}
				
				SettingsBean _instance = SettingsBean.getSettings();
				_instance.setUsername((username.getText()).trim());
				_instance.setPassword(password64);
				SettingsBean.saveSettings(_instance);
				_instance = null;
				doConnect(Const.userdetails+Const.jpg+Const.height+Const.getAppHeight()+Const.width+Const.getCardWidth(), false);
			}
		}
	}
}