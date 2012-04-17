package net.mytcg.dev.ui;

import net.mytcg.dev.ui.custom.ColorLabelField;
import net.mytcg.dev.ui.custom.FixedButtonField;
import net.mytcg.dev.ui.custom.ListItemField;
import net.mytcg.dev.util.Const;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class GameLobbyScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	FixedButtonField host = new FixedButtonField(Const.host);
	
	ListItemField tmp = new ListItemField("Empty", -1, false, 0);
	int categoryid=-1;
	int deckid=-1;

	public GameLobbyScreen(int categoryid, int deckid)
	{
		super(null);
		this.categoryid =  categoryid;
		this.deckid =  deckid;
		bgManager.setStatusHeight(exit.getContentHeight());
		
		exit.setChangeListener(this);
		host.setChangeListener(this);
		
		add(new ColorLabelField(" Please choose a game to play."));
		addButton(host);
		addButton(new FixedButtonField(""));
		addButton(exit);
		
		doConnect(Const.getopengames+Const.categoryid+categoryid);	
	}
	
	public void process(String val) {
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
	    		if ((fromIndex = game.indexOf(Const.xml_username)) != -1) {
	    			gamename = game.substring(fromIndex+Const.xml_username_length, game.indexOf(Const.xml_username_end, fromIndex));
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
			add(new ColorLabelField(" Please choose a game to play."));
			doConnect(Const.getopengames+Const.categoryid+categoryid);
		}
	}
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		} else if(f == host){
			screen = new GamePlayScreen(true, categoryid, 2, false, deckid,1,-1);
			UiApplication.getUiApplication().pushScreen(screen);
		} else if(f instanceof ListItemField){
			int gameid = ((ListItemField)(f)).getId();
			if(gameid > -1){
				screen = new GamePlayScreen(true, categoryid, 2, false, deckid,2,gameid);
				UiApplication.getUiApplication().pushScreen(screen);
			}
		}
	}
}