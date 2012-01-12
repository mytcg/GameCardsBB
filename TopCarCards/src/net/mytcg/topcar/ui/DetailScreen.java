package net.mytcg.topcar.ui;

import java.util.Vector;

import net.mytcg.topcar.ui.custom.ColorLabelField;
import net.mytcg.topcar.ui.custom.FixedButtonField;
import net.mytcg.topcar.ui.custom.FriendField;
import net.mytcg.topcar.ui.custom.ListItemField;
import net.mytcg.topcar.ui.custom.ListLabelField;
import net.mytcg.topcar.ui.custom.PageNumberField;
import net.mytcg.topcar.ui.custom.ProfileFieldManager;
import net.mytcg.topcar.ui.custom.SexyEditField;
import net.mytcg.topcar.util.Answer;
import net.mytcg.topcar.util.Const;
import net.mytcg.topcar.util.SettingsBean;
import net.rim.blackberry.api.browser.Browser;
import net.rim.blackberry.api.browser.BrowserSession;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.TouchEvent;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.CheckboxField;
import net.rim.device.api.ui.component.SeparatorField;

public class DetailScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	FixedButtonField save = new FixedButtonField(Const.save);
	FixedButtonField buy = new FixedButtonField(Const.buy);
	PageNumberField pageNumber = new PageNumberField("Page 1/1");
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
	Vector pages = new Vector();
	Vector tempList = new Vector();
	int currentPage = 0;
	
	public DetailScreen(AppScreen screen, int screenType)
	{
		super(screen);
		add(new ColorLabelField(""));
		bgManager.setStatusHeight(exit.getContentHeight());
		bgManager.setArrowMode(true);
		if(screenType == Const.PROFILESCREEN){
			lbltop = new ColorLabelField("Earn credits by filling in profile details.");
			tempList.addElement(lbltop);
			save.setChangeListener(this);
			addButton(save);
			doConnect(Const.profiledetails);
		} else if(screenType == Const.BALANCESCREEN){
			lbltop = new ColorLabelField("Go to www.mytcg.net to find out how to get more credits.");
			lbltop.setColor(Color.RED);
			tempList.addElement(lbltop);
			tempList.addElement(lblBalance);
			tempList.addElement(balance);
			tempList.addElement(lblTransactions);
			lblBalance.setFocusable(false);
			lblTransactions.setFocusable(false);
			balance.setText(SettingsBean.getSettings().getCredits());
			balance.setFocusable(false);
			buy.setChangeListener(this);
			addButton(buy);
			doConnect(Const.creditlog);
		} else if(screenType == Const.NOTIFICATIONSCREEN){
			tempList.addElement(lblNotifications);
			lblNotifications.setFocusable(false);
			addButton(new FixedButtonField(""));
			doConnect(Const.notifications);
		} else if(screenType == Const.FRIENDSSCREEN){
			tempList.addElement(lblFriends);
			lblFriends.setFocusable(false);
			addButton(new FixedButtonField(""));
			doConnect(Const.friends);
		}
		exit.setChangeListener(this);
		
		
		addButton(pageNumber);
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
	    		int listSize = (Const.getUsableHeight()) / (Const.getButtonHeight()+14);
	    		int listCounter = 0;
	    		pages = new Vector();
	    		int answerid = -1;
	    		String desc = "";
	    		String answer = "";
	    		String answered = "";
	    		String creditvalue = "";
	    		int endIndex = -1;
	    		String details = "";
	    		while ((fromIndex = val.indexOf(Const.xml_answerid)) != -1){
	    			if(listCounter >= listSize){
	    				pages.addElement(tempList);
	    				tempList = new Vector();
	    				listCounter=0;
	    			}
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
	    				lbltmp = new ColorLabelField(desc);
	    				cbxtmp = new CheckboxField("",false,CheckboxField.NON_FOCUSABLE);
	    				cbxtmp.setChecked(answered.equals("1"));
		    			cbxtmp.setEditable(false);
		    			//cbxtmp.setEnabled(false);
	    				tmp = new SexyEditField((Const.getWidth()-25-60),Const.getButtonHeight());
	    				tmp.setText(answer);
	    				//tmp = new SexyEditField(answer);
	        			tmp.setChangeListener(this);
	    				}catch(Exception e){
	    				}
	        			ProfileFieldManager hmanager = new ProfileFieldManager();
		    			synchronized(UiApplication.getEventLock()) {
		    				tempList.addElement(lbltmp);
		    				tempList.addElement(hmanager);
			        		listCounter++;
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
	    		pages.addElement(tempList);
	        	synchronized(UiApplication.getEventLock()) {
	        		if(pages.size()<=1){
	    				bgManager.setArrowMode(false);
	    			}
	        		pageNumber.setLabel("Page 1/"+pages.size());
	        		Field[] temp = new Field[((Vector)pages.elementAt(0)).size()];
	        		((Vector)pages.elementAt(0)).copyInto(temp);
	        		bgManager.deleteAll();
	    	    	bgManager.addAll(temp);
	    	    }
	    	} else if (((fromIndex = val.indexOf(Const.xml_transactions)) != -1)) {
	    		int listSize = (Const.getUsableHeight()) / (Const.getButtonSelCentre().getHeight()+7);
	    		int listCounter = 3;
	    		pages = new Vector();
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
	    			if(listCounter >= listSize){
	    				pages.addElement(tempList);
	    				tempList = new Vector();
	    				listCounter=0;
	    			}
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
			    				tempList.addElement(lbltrans);
			    				tempList.addElement(new SeparatorField(){public int getPreferredWidth() {
			    					return (int)(Const.getWidth()-28);
			    				}});
			    				listCounter++;
			    			}
	    				}catch(Exception e){};
	    			} else{
	    				synchronized(UiApplication.getEventLock()) {
	    					tempList.addElement(lbltrans);
		    			}
	    				break;
	    			}
	    		}
	    		pages.addElement(tempList);
	        	synchronized(UiApplication.getEventLock()) {
	        		if(pages.size()<=1){
	    				bgManager.setArrowMode(false);
	    			} else {
	    				bgManager.setArrowMode(true);
	    			}
	        		pageNumber.setLabel("Page 1/"+pages.size());
	        		Field[] temp = new Field[((Vector)pages.elementAt(0)).size()];
	        		((Vector)pages.elementAt(0)).copyInto(temp);
	        		bgManager.deleteAll();
	    	    	bgManager.addAll(temp);
	    	    }
	    	} else if (((fromIndex = val.indexOf(Const.xml_notifications)) != -1)) {
	    		int listSize = (Const.getUsableHeight()) / (Const.getButtonSelCentre().getHeight()+7);
	    		int listCounter = 1;
	    		pages = new Vector();
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
	    			if(listCounter >= listSize){
	    				pages.addElement(tempList);
	    				tempList = new Vector();
	    				listCounter=0;
	    			}
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
			    				tempList.addElement(lblnote);
			    				tempList.addElement(new SeparatorField(){public int getPreferredWidth() {
			    					return (int)(Const.getWidth());
			    				}});
			    				listCounter++;
			    			}
	    				}catch(Exception e){};
	    			} 
	    		}
	    		if(noteid == -1){
    				synchronized(UiApplication.getEventLock()) {
    					tempList.addElement(lblnote);
	    			}
    			}
	    		pages.addElement(tempList);
	        	synchronized(UiApplication.getEventLock()) {
	        		if(pages.size()<=1){
	    				bgManager.setArrowMode(false);
	    			}
	        		pageNumber.setLabel("Page 1/"+pages.size());
	        		Field[] temp = new Field[((Vector)pages.elementAt(0)).size()];
	        		((Vector)pages.elementAt(0)).copyInto(temp);
	        		bgManager.deleteAll();
	    	    	bgManager.addAll(temp);
	    	    }
	    	} else if (((fromIndex = val.indexOf(Const.xml_friends)) != -1)) {
	    		int listSize = (Const.getUsableHeight()) / (Const.getThumbCentre().getHeight());
	    		int listCounter = 1;
	    		pages = new Vector();
	    		String desc = "";
	    		String usr = "";
	    		String value = "";
	    		
	    		int endIndex = -1;
	    		String friend = "";
	    		while ((fromIndex = val.indexOf(Const.xml_usr)) != -1){
	    			if(listCounter >= listSize){
	    				pages.addElement(tempList);
	    				tempList = new Vector();
	    				listCounter=0;
	    			}
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
			    				tempList.addElement(lblfriend);
			    				//add(new SeparatorField());
			    				listCounter++;
			    			}
	    				}catch(Exception e){};
	    			}
	    		} if(usr.equals("")){
    				synchronized(UiApplication.getEventLock()) {
    					tempList.addElement(new ColorLabelField("No friends found."));
	    			}
    			}
    			pages.addElement(tempList);
		        synchronized(UiApplication.getEventLock()) {
		        	if(pages.size()<=1){
	    				bgManager.setArrowMode(false);
	    			}
		        	pageNumber.setLabel("Page 1/"+pages.size());
		        	Field[] temp = new Field[((Vector)pages.elementAt(0)).size()];
		        	((Vector)pages.elementAt(0)).copyInto(temp);
		        	bgManager.deleteAll();
		    	   	bgManager.addAll(temp);
		    	}
	    	}
	    	invalidate();
	    	//setDisplaying(true);
		}		
	}
	protected boolean touchEvent(TouchEvent event) {
		int x = event.getX(1);
		int y = event.getY(1) - titleManager.getHeight();
		if(event.getEvent() == TouchEvent.CLICK){
			if(bgManager.checkLeftArrow(x, y)){
				navigationMovement(-1, 0, 536870912, 5000);
				return true;
			}else if(bgManager.checkRightArrow(x, y)){
				navigationMovement(1, 0, -1610612736, 5000);   
				return true;
			}
		}
		if(this.getFieldAtLocation(x, y)==-1){
			return true;
		}else if(this.getFieldAtLocation(x, y)==0){
			if(bgManager.getFieldAtLocation(x, y)!=-1){
				return super.touchEvent(event);
			}
			return true;
		}
		else{
			return super.touchEvent(event);
		}
	}
	public boolean navigationMovement(int dx, int dy, int status, int time) {
		if(bgManager.isFocus() && dy == 0 && dx == -1){
			if(pages.size() >1){
				if((currentPage-1)<0){
					currentPage = pages.size()-1;
				}else{
					currentPage--;
				}
				synchronized(UiApplication.getEventLock()) {
					pageNumber.setLabel("Page "+(currentPage+1)+"/"+pages.size());
					Field[] temp = new Field[((Vector)pages.elementAt(currentPage)).size()];
	    			((Vector)pages.elementAt(currentPage)).copyInto(temp);
	    			bgManager.deleteAll();
		    		bgManager.addAll(temp);
		    	}
			}
			return true;
		}else if(bgManager.isFocus() && dy == 0 && dx == 1){
			if(pages.size() >1){
				if((currentPage+1)>=pages.size()){
					currentPage = 0;
				}else{
					currentPage++;
				}
				synchronized(UiApplication.getEventLock()) {
					pageNumber.setLabel("Page "+(currentPage+1)+"/"+pages.size());
					Field[] temp = new Field[((Vector)pages.elementAt(currentPage)).size()];
	    			((Vector)pages.elementAt(currentPage)).copyInto(temp);
	    			bgManager.deleteAll();
		    		bgManager.addAll(temp);
		    	}
			}
			return true;
		}else{
			return super.navigationMovement(dx, dy, status, time);
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
		} else if(f == buy){
			BrowserSession browserSession = Browser.getDefaultSession();
			browserSession.displayPage("http://buy.mytcg.net");
			browserSession.showBrowser();
		}
	}
}