package net.mytcg.kickoff.ui;

import net.mytcg.kickoff.ui.custom.ColorLabelField;
import net.mytcg.kickoff.ui.custom.FixedButtonField;
import net.mytcg.kickoff.ui.custom.SexyEditField;
import net.mytcg.kickoff.util.Const;
import net.mytcg.kickoff.util.SettingsBean;
import net.rim.device.api.io.Base64OutputStream;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class ChangePasswordScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField change = new FixedButtonField("Change");
	FixedButtonField exit = new FixedButtonField(Const.back);
	
	SexyEditField oldpassword = new SexyEditField("");
	SexyEditField newpassword = new SexyEditField("");
	SexyEditField confirmpassword = new SexyEditField("");
	ColorLabelField lbltop = null;
	
	public void process(String val) {
		System.out.println(val);
		int fromIndex;
    	if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
    		synchronized(UiApplication.getEventLock()) {
    			lbltop.setText(val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex)));
    			if ((fromIndex = val.indexOf(Const.xml_setencrypt)) != -1) {
	    			if(val.substring(fromIndex+Const.xml_setencrypt_length, val.indexOf(Const.xml_setencrypt_end, fromIndex)).equals("true")){
	    				SettingsBean _instance = SettingsBean.getSettings();
	    				String password64="";
	    				try {
	    					password64 = new String(Base64OutputStream.encode((newpassword.getText().trim()).getBytes(), 0, (newpassword.getText().trim()).length(), false, false), "UTF-8");
	    					_instance.setPassword(password64);
	    					SettingsBean.saveSettings(_instance);
	    				} catch (Exception e) {
	    					
	    				}
	    			}
    			}
    		}
    	} 
    	invalidate();
	}
	
	public boolean onClose() {
		System.exit(0);
		return true;
	}
	
	public ChangePasswordScreen()
	{
		super(null);
		lbltop = new ColorLabelField("");
		add(lbltop);
		add(new ColorLabelField("Old Password"));
		add(oldpassword);
		add(new ColorLabelField("New Password"));
		add(newpassword);
		add(new ColorLabelField("Confirm Password"));
		add(confirmpassword);
		
		bgManager.setStatusHeight(Const.getButtonHeight());
		
		exit.setChangeListener(this);
		change.setChangeListener(this);
		
		addButton(change);
		addButton(new FixedButtonField(""));
		addButton(exit);
	}
	
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		}else if (f == change) {
			if ((oldpassword.getText() == null)||(oldpassword.getText().length() <= 0)||(newpassword.getText() == null)||(newpassword.getText().length() <= 0)||(confirmpassword.getText() == null)||(confirmpassword.getText().length() <= 0)) {
				lbltop.setText("Password fields cannot be blank.");
			} else if(!newpassword.getText().equals(confirmpassword.getText())){
				lbltop.setText("New password does not match Confirm password.");
			} else {
				doConnect("changepassword="+newpassword.getText()+"&oldpass="+oldpassword.getText());
			}
		}
	}
}