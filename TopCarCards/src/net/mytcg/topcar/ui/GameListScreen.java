package net.mytcg.topcar.ui;

import net.mytcg.topcar.ui.custom.ColorLabelField;
import net.mytcg.topcar.ui.custom.FixedButtonField;
import net.mytcg.topcar.ui.custom.ListItemField;
import net.mytcg.topcar.util.Const;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class GameListScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	
	ListItemField tmp = new ListItemField("Empty", -1, false, 0);

	public GameListScreen(String val)
	{
		super(null);
		bgManager.setStatusHeight(exit.getContentHeight());
		
		exit.setChangeListener(this); 
		
		add(new ColorLabelField(" Please choose a game to continue."));
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		addButton(exit);
		
		process(val);
	}
	
	public void process(String val) {
		System.out.println("wawawa "+val);
		int fromIndex;
	    if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
	    	setText(val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex)));
	    } else if (((fromIndex = val.indexOf(Const.xml_games)) != -1)) {
	    	int gameid = -1;
	    	String gamename = "";
	    	int endIndex = -1;
	    	String game = "";
	    	while ((fromIndex = val.indexOf(Const.xml_gameid)) != -1){
	    		
	    		endIndex = val.indexOf(Const.xml_game_end);
	    		game = val.substring(fromIndex, endIndex+Const.xml_game_end_length);
	    		fromIndex = game.indexOf(Const.xml_gameid);
	    		
	    		try {
	    			gameid = Integer.parseInt(game.substring(fromIndex+Const.xml_gameid_length, game.indexOf(Const.xml_gameid_end, fromIndex)));
	    		} catch (Exception e) {
	    			gameid = -1;
	    		}
	    		if ((fromIndex = game.indexOf(Const.xml_gamename)) != -1) {
	    			gamename = game.substring(fromIndex+Const.xml_gamename_length, game.indexOf(Const.xml_gamename_end, fromIndex));
	    		}
	    		val = val.substring(val.indexOf(Const.xml_game_end)+Const.xml_game_end_length);
	    		if(gameid != -1){
		   			synchronized(UiApplication.getEventLock()) {
		   				tmp = new ListItemField(gamename, gameid, true, 0);
		   				tmp.setLabel(gamename);
		       			tmp.setChangeListener(this);
		       			add(tmp);
		       		}
	    		}
	    	}
	    	if(gameid == -1){
	    		synchronized(UiApplication.getEventLock()) {
	    			add(tmp);
	    		}
	    	}
	    }
	    invalidate();		
	}
	
	protected void onExposed(){
		if (!isVisible()) {
			synchronized(UiApplication.getEventLock()) {
				bgManager.deleteAll();
			}
			doConnect(Const.getusergames);
		}
	}
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		} else if(f instanceof ListItemField){
			int category = ((ListItemField)(f)).getId();
			if(category > -1){
				screen = new GamePlayScreen(false, category, 1, false);
				UiApplication.getUiApplication().pushScreen(screen);
			}
		}
	}
}