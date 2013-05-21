package net.mytcg.kickoff.ui;

import net.mytcg.kickoff.ui.custom.FixedButtonField;
import net.mytcg.kickoff.ui.custom.ListItemField;
import net.mytcg.kickoff.util.Const;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class NewGameScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	
	ListItemField newgame = new ListItemField("Empty", -1, false, 0);
	ListItemField tmp = new ListItemField("Empty", -1, false, 0);
	String games = null;
	boolean skip = false;

	public NewGameScreen()
	{
		super(null);
		bgManager.setStatusHeight(exit.getContentHeight());
		
		newgame = new ListItemField(Const.newgame, 0, false, 0);
		
		exit.setChangeListener(this); 
		newgame.setChangeListener(this);
		
		add(newgame);
		
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		addButton(exit);
		
		doConnect(Const.getusergames);
	}
	
	public void process(String val) {
		if (!(isDisplaying())) {
			int fromIndex;
	    	if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
	    		setText(val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex)));
	    	} else if (((fromIndex = val.indexOf(Const.xml_games)) != -1)) {
	    		if ((fromIndex = val.indexOf(Const.xml_gameid)) != -1){
	    			games = val;
	    			synchronized(UiApplication.getEventLock()) {
	    				tmp = new ListItemField("Continue Game", 1, true, 0);
	        			tmp.setChangeListener(this);
	        			add(tmp);
	        		}
	    		} else {
	    			try{
	    			skip = true;
	    			synchronized(UiApplication.getEventLock()) {
		    			screen = new GameCategoryScreen();
		    			UiApplication.getUiApplication().pushScreen(screen);
	    			}
	    			}catch(Exception e){};
	    			return;
	    		}
	    	}
	    	invalidate();
	    	setDisplaying(true);
		}		
	}
	
	public void onExposed(){
		if(skip){
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		}
	}
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		} else if(f == newgame){
			screen = new GameCategoryScreen();
			UiApplication.getUiApplication().pushScreen(screen);
		} else if(f instanceof ListItemField){
			int category = ((ListItemField)(f)).getId();
			if(category == 1){
				screen = new GameListScreen(games);
				UiApplication.getUiApplication().pushScreen(screen);
			}
		}
	}
}