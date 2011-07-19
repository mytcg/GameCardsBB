package net.mytcg.dev.ui;

import java.util.Vector;

import net.mytcg.dev.ui.custom.ColorLabelField;
import net.mytcg.dev.ui.custom.FixedButtonField;
import net.mytcg.dev.ui.custom.ListItemField;
import net.mytcg.dev.ui.custom.ListLabelField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.mytcg.dev.ui.custom.SexyEditField;
import net.mytcg.dev.util.Answer;
import net.mytcg.dev.util.Const;
import net.mytcg.dev.util.SettingsBean;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.CheckboxField;

public class DetailScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	FixedButtonField save = new FixedButtonField(Const.save);
	SexyEditField username = new SexyEditField("");
	SexyEditField email = new SexyEditField("");
	SexyEditField balance = new SexyEditField("");
	SexyEditField tmp = new SexyEditField("");
	ColorLabelField lbltop = null;
	ColorLabelField lblUsername = new ColorLabelField(Const.usern);
	ColorLabelField lblEmail = new ColorLabelField(Const.email);
	ListItemField lblBalance = new ListItemField(Const.credits, -1, false, 0);
	ListLabelField lbltrans = new ListLabelField("No transactions yet.");
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
			add(lblUsername);
			add(username);
			username.setText(SettingsBean.getSettings().getUsername());
			username.setFocusable(false);
			add(lblEmail);
			add(email);
			email.setText(SettingsBean.getSettings().getEmail());
			email.setFocusable(false);
			save.setChangeListener(this);
			addButton(save);
			doConnect(Const.profiledetails);
		} else if(screenType == Const.BALANCESCREEN){
			lbltop = new ColorLabelField("Go to www.mytcg.net to find out how to get more credits.");
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
		}
		exit.setChangeListener(this);
		
		
		addButton(new FixedButtonField(""));
		addButton(exit);
	}
	
	public void process(String val) {
		System.out.println("weo "+val);
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
	    				tmp = new SexyEditField((Const.getWidth()-25),username.getHeight());
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
		    		try{
		    			synchronized(UiApplication.getEventLock()) {
		    				balance.setText(val.substring(fromIndex, endIndex+Const.xml_credits_end_length));
		    			}
		    		}catch(Exception e){};
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
		    				lbltrans = new ListLabelField(date+": "+desc);
			    			synchronized(UiApplication.getEventLock()) {
			    				add(lbltrans);
			    			}
	    				}catch(Exception e){};
	    			} else{
	    				synchronized(UiApplication.getEventLock()) {
		    				add(lbltrans);
		    			}
	    				break;
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