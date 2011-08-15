package net.mytcg.sport.ui;

import net.mytcg.sport.ui.custom.ColorLabelField;
import net.mytcg.sport.ui.custom.FixedButtonField;
import net.mytcg.sport.ui.custom.SexyEditField;
import net.mytcg.sport.util.Const;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class InviteFriendScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	FixedButtonField con = new FixedButtonField(Const.con);
	SexyEditField username = new SexyEditField("");
	SexyEditField email = new SexyEditField("");
	SexyEditField phonenumber = new SexyEditField("");
	ColorLabelField lblTop = new ColorLabelField("");
	ColorLabelField lblPhone = new ColorLabelField(" Invite by Phone Number");
	ColorLabelField lblUsername = new ColorLabelField(" Invite by Username");
	ColorLabelField lblEmail = new ColorLabelField(" Invite by Email");
	
	public InviteFriendScreen()
	{
		super(null);
		bgManager.setStatusHeight(exit.getContentHeight());
		
		add(lblTop);
		add(lblUsername);
		add(username);
		add(lblEmail);
		add(email);
		add(lblPhone);
		add(phonenumber);
		
		exit.setChangeListener(this);
		con.setChangeListener(this);
		
		addButton(con);
		addButton(new FixedButtonField(""));
		addButton(exit);
	}
	
	public void process(String val) {
		int fromIndex;
	    if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
	    	synchronized(UiApplication.getEventLock()) {
	    		lblTop.setText(" "+val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex)));
	    	}
	    } 
	}
	
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		} else if (f == con){
			String method = "";
			String friendDetail = "";
			if (username.getText().equals("")
				&& email.getText().equals("")
				&& phonenumber.getText().equals("")) {
				lblTop.setText(" Fill in at least one field.");
			}
			else {
				if (!username.getText().equals(""))
				{
					friendDetail = username.getText();
					method = "username";
				}
				else if (!email.getText().equals(""))
				{
					friendDetail = email.getText();
					method = "email";
				}
				else if (!phonenumber.getText().equals(""))
				{
					friendDetail = phonenumber.getText();
					method = "phone_number";
				}
				lblTop.setText(" Inviting friend...");
				doConnect("friendinvite=1&trademethod="+method+"&detail="+friendDetail);	
			}
		}
	}
}