package net.mytcg.dev.ui;

import net.mytcg.dev.ui.custom.FixedButtonField;
import net.mytcg.dev.ui.custom.ListItemField;
import net.mytcg.dev.util.Const;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class NewGameScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	
	ListItemField newgame = new ListItemField("Empty", -1, false, 0);
	ListItemField tmp = new ListItemField("Empty", -1, false, 0);
	String games = null;

	public NewGameScreen()
	{
		super(null);
		bgManager.setStatusHeight(exit.getContentHeight());
		
		newgame = new ListItemField(Const.newgame, 0, false, 0);
		
		exit.setChangeListener(this); 
		newgame.setChangeListener(this);
		
		add(newgame);
		addButton(exit);
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		
		doConnect(Const.getusergames);
	}
	
	public void process(String val) {
		System.out.println("wawawa "+val);
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