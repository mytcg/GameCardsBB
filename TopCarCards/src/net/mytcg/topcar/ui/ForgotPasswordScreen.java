package net.mytcg.topcar.ui;

import net.mytcg.topcar.ui.custom.ColorLabelField;
import net.mytcg.topcar.ui.custom.FixedButtonField;
import net.mytcg.topcar.ui.custom.SexyEditField;
import net.mytcg.topcar.util.Const;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class ForgotPasswordScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField reset = new FixedButtonField("Reset");
	FixedButtonField exit = new FixedButtonField(Const.back);
	
	SexyEditField email = new SexyEditField("");
	ColorLabelField lbltop = null;
	
	public void process(String val) {
		System.out.println(val);
		int fromIndex;
    	if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
    		synchronized(UiApplication.getEventLock()) {
    			lbltop.setText(val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex)));
    		}
    	} 
    	invalidate();
	}
	
	public boolean onClose() {
		System.exit(0);
		return true;
	}
	
	public ForgotPasswordScreen()
	{
		super(null);
		lbltop = new ColorLabelField("");
		add(lbltop);
		add(new ColorLabelField("Email"));
		add(email);
		
		bgManager.setStatusHeight(Const.getButtonHeight());
		
		exit.setChangeListener(this);
		reset.setChangeListener(this);
		
		addButton(reset);
		addButton(new FixedButtonField(""));
		addButton(exit);
	}
	
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		}else if (f == reset) {
			if ((email.getText() == null)||(email.getText().length() <= 0)) {
				lbltop.setText("Email cannot be blank.");
			} else {
				doConnect("resetpassword="+email.getText());
			}
		}
	}
}