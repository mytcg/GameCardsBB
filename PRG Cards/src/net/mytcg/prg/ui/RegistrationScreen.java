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

public class RegistrationScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField register = new FixedButtonField(Const.register);
	FixedButtonField exit = new FixedButtonField(Const.exit);
	
	SexyEditField name = new SexyEditField("");
	SexyEditField surname = new SexyEditField("");
	SexyEditField cell = new SexyEditField("", EditField.FILTER_NUMERIC, 36);
	SexyEditField age = new SexyEditField("", EditField.FILTER_NUMERIC, 2);
	SexyEditField gender = new SexyEditField("");
	
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
		
		add(new LabelField(Const.name));
		add(name);
		add(new LabelField(Const.surname));
		add(surname);
		add(new LabelField(Const.cell));
		add(cell);
		add(new LabelField(Const.age));
		add(age);
		add(new LabelField(Const.gender));
		add(gender);
		
		bgManager.setStatusHeight(Const.getButtonHeight());
		
		exit.setChangeListener(this);
		register.setChangeListener(this);
		
		addButton(exit);
		addButton(new FixedButtonField(""));
		addButton(register);
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
			} else {
				String url = Const.registeruser+Const.username+name.getText()+Const.usersurname+surname.getText()+Const.usercell+cell.getText()+Const.userage+age.getText()+Const.usergender+gender.getText();
				url = Const.removeSpaces(url);
				doConnect(url);
			}
		}
	}
}