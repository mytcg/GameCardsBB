package net.mytcg.kickoff.ui;

import java.util.Vector;

import net.mytcg.kickoff.ui.custom.ColorLabelField;
import net.mytcg.kickoff.ui.custom.FixedButtonField;
import net.mytcg.kickoff.ui.custom.ListItemField;
import net.mytcg.kickoff.ui.custom.PageNumberField;
import net.mytcg.kickoff.util.Const;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
//import net.rim.device.api.ui.TouchEvent;
import net.rim.device.api.ui.UiApplication;

public class GameListScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	PageNumberField pageNumber = new PageNumberField("Page 1/1");
	ColorLabelField header = new ColorLabelField(" Please choose a game to continue.");
	
	ListItemField tmp = new ListItemField("Empty", -1, false, 0);
	
	Vector pages = new Vector();
	Vector tempList = new Vector();
	int currentPage = 0;

	public GameListScreen(String val)
	{
		super(null);
		bgManager.setStatusHeight(exit.getContentHeight());
		bgManager.setArrowMode(true);
		exit.setChangeListener(this); 
		addButton(new FixedButtonField(""));
		addButton(pageNumber);
		addButton(exit);
		
		process(val);
	}
	
	public void process(String val) {
		int listSize = (Const.getUsableHeight()) / Const.getButtonHeight();
		int listCounter = 0;
		tempList = new Vector();
		pages = new Vector();
		int fromIndex;
	    if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
	    	setText(val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex)));
	    } else if (((fromIndex = val.indexOf(Const.xml_games)) != -1)) {
	    	int gameid = -1;
	    	String gamename = "";
	    	int endIndex = -1;
	    	String game = "";
	    	while ((fromIndex = val.indexOf(Const.xml_gameid)) != -1){
	    		if(listCounter >= listSize){
    				pages.addElement(tempList);
    				tempList = new Vector();
    				listCounter=0;
    			}
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
		       			tempList.addElement(tmp);
	        			listCounter++;
		       		}
	    		}
	    	}
	    	if(gameid == -1){
	    		synchronized(UiApplication.getEventLock()) {
	    			add(tmp);
	    		}
	    	}else{
    			pages.addElement(tempList);
    		}
	    	synchronized(UiApplication.getEventLock()) {
    			if(pages.size()<=1){
    				bgManager.setArrowMode(false);
    			}
    			pageNumber.setLabel("Page 1/"+pages.size());
    			ListItemField[] temp = new ListItemField[((Vector)pages.elementAt(0)).size()];
    			((Vector)pages.elementAt(0)).copyInto(temp);
    			bgManager.deleteAll();
    			bgManager.add(header);
    			bgManager.addAll(temp);
	    	}
	    }
	    invalidate();		
	}
	/*protected boolean touchEvent(TouchEvent event) {
		int x = event.getX(1);
		int y = event.getY(1) - titleManager.getHeight();
		if(event.getEvent() == TouchEvent.DOWN){
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
	}*/
	public boolean navigationMovement(int dx, int dy, int status, int time) {
		if(dy == 0 && dx == -1){
			if(pages.size() >1){
				if((currentPage-1)<0){
					currentPage = pages.size()-1;
				}else{
					currentPage--;
				}
				synchronized(UiApplication.getEventLock()) {
					pageNumber.setLabel("Page "+(currentPage+1)+"/"+pages.size());
					ListItemField[] temp = new ListItemField[((Vector)pages.elementAt(currentPage)).size()];
	    			((Vector)pages.elementAt(currentPage)).copyInto(temp);
	    			try{
	    				bgManager.deleteAll();
	    			}catch(Exception e){}
	    			bgManager.add(header);
		    		bgManager.addAll(temp);
		    	}
			}
			return true;
		}else if(dy == 0 && dx == 1){
			if(pages.size() >1){
				if((currentPage+1)>=pages.size()){
					currentPage = 0;
				}else{
					currentPage++;
				}
				synchronized(UiApplication.getEventLock()) {
					pageNumber.setLabel("Page "+(currentPage+1)+"/"+pages.size());
					ListItemField[] temp = new ListItemField[((Vector)pages.elementAt(currentPage)).size()];
	    			((Vector)pages.elementAt(currentPage)).copyInto(temp);
	    			try{
	    				bgManager.deleteAll();
	    			}catch(Exception e){}
	    			bgManager.add(header);
		    		bgManager.addAll(temp);
		    	}
			}
			return true;
		}
		return super.navigationMovement(dx, dy, status, time);
	}
	protected void onExposed(){
		if (!isVisible()) {
			synchronized(UiApplication.getEventLock()) {
				bgManager.deleteAll();
				bgManager.add(header);
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
				screen = new GamePlayScreen(false, category, 1, false, -1, 0, -1);
				UiApplication.getUiApplication().pushScreen(screen);
			}
		}
	}
}