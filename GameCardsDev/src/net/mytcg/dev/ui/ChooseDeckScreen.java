package net.mytcg.dev.ui;

import net.mytcg.dev.ui.custom.ColorLabelField;
import net.mytcg.dev.ui.custom.FixedButtonField;
import net.mytcg.dev.ui.custom.ListItemField;
import net.mytcg.dev.util.Const;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.LabelField;

public class ChooseDeckScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	
	ListItemField createNewDeck = new ListItemField("Empty", -1, false, 0);
	ListItemField tmp = new ListItemField("Empty", -1, false, 0);

	public ChooseDeckScreen()
	{
		super(null);
		bgManager.setStatusHeight(exit.getContentHeight());
		
		createNewDeck = new ListItemField(Const.newgame, 0, false, 0);
		
		exit.setChangeListener(this); 
		createNewDeck.setChangeListener(this);
		
		add(new ColorLabelField("Please choose deck to play with",LabelField.FIELD_HCENTER , 18));
		addButton(exit);
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		
		doConnect(Const.getusergames);
	}
	
	public void process(String val) {
		if (!(isDisplaying())) {
			int fromIndex;
	    	if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
	    		setText(val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex)));
	    	} else if (((fromIndex = val.indexOf(Const.xml_games)) != -1)) {
	    		int gameid = -1;
	    		String gamename = "";
	    		int endIndex = -1;
	    		String game = "";
	    		while ((fromIndex = val.indexOf(Const.xml_gameid)) != -1){
	    			
	    			endIndex = val.indexOf(Const.xml_gameid_end);
	    			game = val.substring(fromIndex, endIndex+Const.xml_gameid_end_length);
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
		        			tmp.setChangeListener(this);
		        			add(tmp);
		        		}
	    			}
	    		}
	    	}
	    	add(createNewDeck);
	    	invalidate();
	    	setDisplaying(true);
		}		
	}
	
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		} else if(f == createNewDeck){
			screen = new CreateNewDeckScreen();
			UiApplication.getUiApplication().pushScreen(screen);
		} else if(f instanceof ListItemField){
			int category = ((ListItemField)(f)).getId();
			if(category > -1){
				//screen = new AuctionListScreen(category,0);
				UiApplication.getUiApplication().pushScreen(screen);
			}
		}
	}
}