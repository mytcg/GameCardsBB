package net.mytcg.topcar.ui;

import net.mytcg.topcar.ui.custom.ColorLabelField;
import net.mytcg.topcar.ui.custom.FixedButtonField;
import net.mytcg.topcar.ui.custom.ListItemField;
import net.mytcg.topcar.ui.custom.SexyEditField;
import net.mytcg.topcar.util.Const;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class RankingsScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	SexyEditField amount = new SexyEditField("");
	ColorLabelField lblType = new ColorLabelField("No users found.");
	ListItemField lblRanking = null;
	
	public RankingsScreen(String category, int categoryId, boolean friends)
	{
		super(null);
		bgManager.setStatusHeight(exit.getContentHeight());
		lblRanking = new ListItemField(category, -1, false, 0);
		add(lblRanking);
		lblRanking.setFocusable(false);
		
		if(!friends){
			doConnect(Const.leaderboard+categoryId);
		}else{
			doConnect(Const.leaderboard+categoryId+Const.friends);
		}
		
		exit.setChangeListener(this);
		
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		addButton(exit);
	}
	
	public void process(String val) {
		System.out.println("weo "+val);
		if (!(isDisplaying())) {
			int fromIndex;
	    	if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
	    		//setText(val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex)));
	    	} if (((fromIndex = val.indexOf(Const.xml_leaderboard)) != -1)) {
	    		int value = -1;
	    		String usr = "";
	    		
	    		int endIndex = -1;
	    		String ranking = "";
	    		while ((fromIndex = val.indexOf(Const.xml_valt)) != -1){
	    			
	    			endIndex = val.indexOf(Const.xml_leader_end);
	    			try{
	    				ranking = val.substring(fromIndex, endIndex+Const.xml_leader_end_length);
	    			}catch(Exception e){};
	    			fromIndex = ranking.indexOf(Const.xml_valt);
	    			
	    			try {
	    				value = Integer.parseInt(ranking.substring(fromIndex+Const.xml_valt_length, ranking.indexOf(Const.xml_valt_end, fromIndex)));
	    			} catch (Exception e) {
	    				value = -1;
	    			}
	    			if ((fromIndex = ranking.indexOf(Const.xml_usr)) != -1) {
	    				usr = ranking.substring(fromIndex+Const.xml_usr_length, ranking.indexOf(Const.xml_usr_end, fromIndex));
	    			}
	    			val = val.substring(val.indexOf(Const.xml_leader_end)+Const.xml_leader_end_length);
	    			if(value != -1){
	    				try{
	    					lblType = new ColorLabelField(usr);
			    			synchronized(UiApplication.getEventLock()) {
			    				add(lblType);
			    				amount = new SexyEditField(""+value);
			    				amount.setEdit(false);
			    				add(amount);
			    			}
	    				}catch(Exception e){};
	    			}
	    		} if(value == -1){
    				synchronized(UiApplication.getEventLock()) {
	    				add(lblType);
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
		} 
	}
}