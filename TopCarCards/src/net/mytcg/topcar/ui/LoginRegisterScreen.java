package net.mytcg.topcar.ui;

import net.mytcg.topcar.ui.custom.FixedButtonField;
import net.mytcg.topcar.ui.custom.ListItemField;
import net.mytcg.topcar.util.Const;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;

public class LoginRegisterScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	
	ListItemField login = new ListItemField(Const.login, -1, false, 0);
	ListItemField register = new ListItemField(Const.register, -1, false, 0);
	
	public LoginRegisterScreen() {
		super(null);
		bgManager.setStatusHeight(exit.getContentHeight());
		
		if (screen == null) {
			exit.setLabel(Const.close);
		}
		
		login.setChangeListener(this);
		register.setChangeListener(this);
		exit.setChangeListener(this);
		
		add(login);
		add(register);
		
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		addButton(exit);
	}
	public boolean onClose() {
		System.exit(0);
		return true;
	}
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			System.exit(0);
		} else if (f == login) {
			close();
			Const.GOTOSCREEN = Const.LOGINSCREEN;
			Const.FROMSCREEN = Const.LOGINSCREEN;
			Const.app.nextScreen();
		} else if (f == register) {
			close();
			Const.GOTOSCREEN = Const.REGISTERSCREEN;
			Const.FROMSCREEN = Const.LOGINSCREEN;
			Const.app.nextScreen();
		}
	}
}