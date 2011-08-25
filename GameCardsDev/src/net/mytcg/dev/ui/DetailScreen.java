package net.mytcg.dev.ui;

import java.util.Vector;

import net.mytcg.dev.ui.custom.ColorLabelField;
import net.mytcg.dev.ui.custom.FixedButtonField;
import net.mytcg.dev.ui.custom.FriendField;
import net.mytcg.dev.ui.custom.ListItemField;
import net.mytcg.dev.ui.custom.ListLabelField;
import net.mytcg.dev.ui.custom.SexyEditField;
import net.mytcg.dev.util.Answer;
import net.mytcg.dev.util.Const;
import net.mytcg.dev.util.SettingsBean;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.CheckboxField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.HorizontalFieldManager;

public class DetailScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	FixedButtonField save = new FixedButtonField(Const.save);
	SexyEditField balance = new SexyEditField("");
	SexyEditField tmp = new SexyEditField("");
	ColorLabelField lbltop = null;
	ListItemField lblBalance = new ListItemField(Const.credits, -1, false, 0);
	ListItemField lblNotifications = new ListItemField(Const.notification, Const.NOTIFICATIONS, false, 0);
	ListItemField lblFriends = new ListItemField(Const.friend, Const.FRIENDS, false, 0);
	ListLabelField lbltrans = new ListLabelField("No transactions yet.");
	ListLabelField lblnote = new ListLabelField("No notifications yet.");
	FriendField lblfriend = new FriendField("","","");
	ListItemField lblTransactions = new ListItemField("Last Transactions", -1, false, 0);
	ColorLabelField lbltmp = new ColorLabelField("");
	CheckboxField cbxtmp = new CheckboxField("",false,CheckboxField.NON_FOCUSABLE);
	Vector answers = new Vector();
	int count =0;
	int credits = 0;
	
	public DetailScreen(AppScreen screen, int screenType)
	{
		super(screen);
		bgManager.setStatusHeight(exit.getContentHeight());
		if(screenType == Const.PROFILESCREEN){
			lbltop = new ColorLabelField("Earn credits by filling in profile details.");
			add(lbltop);
			save.setChangeListener(this);
			addButton(save);
			doConnect(Const.profiledetails);
		} else if(screenType == Const.BALANCESCREEN){
			lbltop = new ColorLabelField("Go to www.mytcg.net to find out how to get more credits.");
			lbltop.setColor(Color.RED);
			add(lbltop);
			add(lblBalance);
			add(balance);
			add(lblTransactions);
			lblBalance.setFocusable(false);
			lblTransactions.setFocusable(false);
			balance.setText(SettingsBean.getSettings().getCredits());
			balance.setFocusable(false);
			addButton(new FixedButtonField(""));
			doConnect(Const.creditlog);
		} else if(screenType == Const.NOTIFICATIONSCREEN){
			add(lblNotifications);
			lblNotifications.setFocusable(false);
			addButton(new FixedButtonField(""));
			doConnect(Const.notifications);
		} else if(screenType == Const.FRIENDSSCREEN){
			add(lblFriends);
			lblFriends.setFocusable(false);
			addButton(new FixedButtonField(""));
			doConnect(Const.friends);
		}
		exit.setChangeListener(this);
		
		
		addButton(new FixedButtonField(""));
		addButton(exit);
	}
	
	public void process(String val) {
		if (!(isDisplaying())) {
			int fromIndex;
	    	if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
	    		//setText(val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex)));
	    		try{
	    			synchronized(UiApplication.getEventLock()) {
			    		if(count >0){
							lbltop.setText(count+" extra field(s) filled in. You got "+credits+" Credits.");
						} else{
							lbltop.setText(" Profile details updated.");
						}
	    			}
	    		}catch(Exception e){};
	    	} else if (((fromIndex = val.indexOf(Const.xml_profiledetails)) != -1)) {
	    		int answerid = -1;
	    		String desc = "";
	    		String answer = "";
	    		String answered = "";
	    		String creditvalue = "";
	    		int endIndex = -1;
	    		String details = "";
	    		while ((fromIndex = val.indexOf(Const.xml_answerid)) != -1){
	    			
	    			endIndex = val.indexOf(Const.xml_detail_end);
	    			details = val.substring(fromIndex, endIndex+Const.xml_detail_end_length);
	    			fromIndex = details.indexOf(Const.xml_answerid);
	    			
	    			try {
	    				answerid = Integer.parseInt(details.substring(fromIndex+Const.xml_answerid_length, details.indexOf(Const.xml_answerid_end, fromIndex)));
	    			} catch (Exception e) {
	    				answerid = -1;
	    			}
	    			if ((fromIndex = details.indexOf(Const.xml_descr)) != -1) {
	    				desc = details.substring(fromIndex+Const.xml_descr_length, details.indexOf(Const.xml_descr_end, fromIndex));
	    			}
	    			if ((fromIndex = details.indexOf(Const.xml_answer)) != -1) {
	    				answer = details.substring(fromIndex+Const.xml_answer_length, details.indexOf(Const.xml_answer_end, fromIndex));
	    			}
	    			if ((fromIndex = details.indexOf(Const.xml_answered)) != -1) {
	    				answered = details.substring(fromIndex+Const.xml_answered_length, details.indexOf(Const.xml_answered_end, fromIndex));
	    			}
	    			if ((fromIndex = details.indexOf(Const.xml_creditvalue)) != -1) {
	    				creditvalue = details.substring(fromIndex+Const.xml_creditvalue_length, details.indexOf(Const.xml_creditvalue_end, fromIndex));
	    			}
	    			val = val.substring(val.indexOf(Const.xml_detail_end)+Const.xml_detail_end_length);
	    			if(answerid != -1){
	    				try{
	    				lbltmp = new ColorLabelField(" "+desc);
	    				cbxtmp = new CheckboxField("",false,CheckboxField.NON_FOCUSABLE);
	    				cbxtmp.setChecked(answered.equals("1"));
		    			cbxtmp.setEditable(false);
		    			//cbxtmp.setEnabled(false);
	    				tmp = new SexyEditField((Const.getWidth()-25),Const.getButtonHeight());
	    				tmp.setText(answer);
	        			tmp.setChangeListener(this);
	    				}catch(Exception e){
	    				}
	        			HorizontalFieldManager hmanager = new HorizontalFieldManager(Field.FIELD_RIGHT);
		    			synchronized(UiApplication.getEventLock()) {
		    				add(lbltmp);
			        		add(hmanager);
			        		hmanager.add(tmp);
			        		hmanager.add(cbxtmp);	
		        		}
		    			try{
			    			Answer ans = new Answer();
			    			ans.setAnswerId(""+answerid);
			    			ans.setAnswer(answer);
			    			ans.setAnswered(Integer.parseInt(answered));
			    			ans.setCreditValue(creditvalue);
			    			ans.setDesc(desc);
			    			ans.setEditBoxPointer(tmp);
			    			ans.setCheckBoxPointer(cbxtmp);
			    			answers.addElement(ans);
		    			}catch(Exception e){};
	    			}
	    		}
	    	} else if (((fromIndex = val.indexOf(Const.xml_transactions)) != -1)) {
	    		int transactionid = -1;
	    		String desc = "";
	    		String date = "";
	    		String value = "";
	    		String credits = "";
	    		int endIndex = -1;
	    		String transaction = "";
	    		
	    		
	    		if ((fromIndex = val.indexOf(Const.xml_credits)) != -1) {
    				credits = val.substring(fromIndex+Const.xml_credits_length, val.indexOf(Const.xml_credits_end, fromIndex));
    				SettingsBean _instance = SettingsBean.getSettings();
    				_instance.setCredits(credits);
    				SettingsBean.saveSettings(_instance);
    				synchronized(UiApplication.getEventLock()) {
    					balance.setText(SettingsBean.getSettings().getCredits());
    	    		}
    				
    			}
	    		while ((fromIndex = val.indexOf(Const.xml_id)) != -1){
	    			
	    			endIndex = val.indexOf(Const.xml_transaction_end);
	    			try{
	    				transaction = val.substring(fromIndex, endIndex+Const.xml_transaction_end_length);
	    			}catch(Exception e){};
	    			fromIndex = transaction.indexOf(Const.xml_id);
	    			
	    			try {
	    				transactionid = Integer.parseInt(transaction.substring(fromIndex+Const.xml_id_length, transaction.indexOf(Const.xml_id_end, fromIndex)));
	    			} catch (Exception e) {
	    				transactionid = -1;
	    			}
	    			if ((fromIndex = transaction.indexOf(Const.xml_descr)) != -1) {
	    				desc = transaction.substring(fromIndex+Const.xml_descr_length, transaction.indexOf(Const.xml_descr_end, fromIndex));
	    			}
	    			if ((fromIndex = transaction.indexOf(Const.xml_date)) != -1) {
	    				date = transaction.substring(fromIndex+Const.xml_date_length, transaction.indexOf(Const.xml_date_end, fromIndex));
	    			}
	    			if ((fromIndex = transaction.indexOf(Const.xml_value)) != -1) {
	    				value = transaction.substring(fromIndex+Const.xml_value_length, transaction.indexOf(Const.xml_value_end, fromIndex));
	    			}
	    			val = val.substring(val.indexOf(Const.xml_transaction_end)+Const.xml_transaction_end_length);
	    			if(transactionid != -1){
	    				try{
	    					//ListItemField tmp = new ListItemField(date+": "+desc, -1, false, 0);
		    				lbltrans = new ListLabelField(date+": "+desc);
			    			synchronized(UiApplication.getEventLock()) {
			    				//add(tmp);
			    				add(lbltrans);
			    				add(new SeparatorField());
			    			}
	    				}catch(Exception e){};
	    			} else{
	    				synchronized(UiApplication.getEventLock()) {
		    				add(lbltrans);
		    			}
	    				break;
	    			}
	    		}
	    	} else if (((fromIndex = val.indexOf(Const.xml_notifications)) != -1)) {
	    		int noteid = -1;
	    		String desc = "";
	    		String date = "";
	    		SettingsBean _instance = SettingsBean.getSettings();
	    		_instance.noteloaded();
	    		_instance.notifications = false;
	    		SettingsBean.saveSettings(_instance);
	    		int endIndex = -1;
	    		String note = "";
	    		while ((fromIndex = val.indexOf(Const.xml_id)) != -1){
	    			
	    			endIndex = val.indexOf(Const.xml_note_end);
	    			try{
	    				note = val.substring(fromIndex, endIndex+Const.xml_note_end_length);
	    			}catch(Exception e){};
	    			fromIndex = note.indexOf(Const.xml_id);
	    			
	    			try {
	    				noteid = Integer.parseInt(note.substring(fromIndex+Const.xml_id_length, note.indexOf(Const.xml_id_end, fromIndex)));
	    			} catch (Exception e) {
	    				noteid = -1;
	    			}
	    			if ((fromIndex = note.indexOf(Const.xml_descr)) != -1) {
	    				desc = note.substring(fromIndex+Const.xml_descr_length, note.indexOf(Const.xml_descr_end, fromIndex));
	    			}
	    			if ((fromIndex = note.indexOf(Const.xml_date)) != -1) {
	    				date = note.substring(fromIndex+Const.xml_date_length, note.indexOf(Const.xml_date_end, fromIndex));
	    			}
	    			val = val.substring(val.indexOf(Const.xml_note_end)+Const.xml_note_end_length);
	    			if(noteid != -1){
	    				try{
	    					lblnote = new ListLabelField(date+": "+desc);
			    			synchronized(UiApplication.getEventLock()) {
			    				add(lblnote);
			    				add(new SeparatorField());
			    			}
	    				}catch(Exception e){};
	    			} 
	    		}
	    		if(noteid == -1){
    				synchronized(UiApplication.getEventLock()) {
	    				add(lblnote);
	    			}
    			}
	    	} else if (((fromIndex = val.indexOf(Const.xml_friends)) != -1)) {
	    		String desc = "";
	    		String usr = "";
	    		String value = "";
	    		
	    		int endIndex = -1;
	    		String friend = "";
	    		while ((fromIndex = val.indexOf(Const.xml_usr)) != -1){
	    			
	    			endIndex = val.indexOf(Const.xml_friend_end);
	    			try{
	    				friend = val.substring(fromIndex, endIndex+Const.xml_friend_end_length);
	    			}catch(Exception e){};
	    			fromIndex = friend.indexOf(Const.xml_usr);
	    			if ((fromIndex = friend.indexOf(Const.xml_usr)) != -1) {
	    				usr = friend.substring(fromIndex+Const.xml_usr_length, friend.indexOf(Const.xml_usr_end, fromIndex));
	    			}
	    			if ((fromIndex = friend.indexOf(Const.xml_valt)) != -1) {
	    				value = friend.substring(fromIndex+Const.xml_valt_length, friend.indexOf(Const.xml_valt_end, fromIndex));
	    			}
	    			if ((fromIndex = friend.indexOf(Const.xml_descr)) != -1) {
	    				desc = friend.substring(fromIndex+Const.xml_descr_length, friend.indexOf(Const.xml_descr_end, fromIndex));
	    			}
	    			
	    			val = val.substring(val.indexOf(Const.xml_friend_end)+Const.xml_friend_end_length);
	    			if(!usr.equals("")){
	    				try{
	    					lblfriend = new FriendField(usr,value,desc);
			    			synchronized(UiApplication.getEventLock()) {
			    				add(lblfriend);
			    				//add(new SeparatorField());
			    			}
	    				}catch(Exception e){};
	    			}
	    		} if(usr.equals("")){
    				synchronized(UiApplication.getEventLock()) {
	    				add(new ColorLabelField("No friends found."));
	    			}
    			}
	    	}
	    	invalidate();
	    	setDisplaying(true);
		}		
	}
	
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		} else if (f == save){
			try{
				count = 0;
				credits = 0;
				boolean saving = false;
				for(int j = 0;j<answers.size();j++){
					Answer tmpAns = (Answer)answers.elementAt(j);
					if(!tmpAns.getAnswer().equals(tmpAns.getEditBoxPointer().getText())){
						saving = true;
						setDisplaying(false);
						doConnect(Const.saveprofiledetail+"&answer_id="+tmpAns.getAnswerId()+"&answer="+tmpAns.getEditBoxPointer().getText());
						if(tmpAns.getAnswered()==0 && tmpAns.getEditBoxPointer().getText().length() > 0){
							count++;
							credits = credits + Integer.parseInt(tmpAns.getCreditValue());
							tmpAns.setAnswered(1);
							tmpAns.getCheckBoxPointer().setChecked(true);
						}
						tmpAns.setAnswer(tmpAns.getEditBoxPointer().getText());
					}
				}
				if (saving) {
					lbltop.setText(" Saving...");
				} else {
					lbltop.setText(" No changes detected.");
				}
			}catch(Exception e){};
		}
	}
}