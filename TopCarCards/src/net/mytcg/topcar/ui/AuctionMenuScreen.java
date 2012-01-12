package net.mytcg.topcar.ui;

import java.util.Vector;

import net.mytcg.topcar.ui.custom.ColorLabelField;
import net.mytcg.topcar.ui.custom.FixedButtonField;
import net.mytcg.topcar.ui.custom.ListItemField;
import net.mytcg.topcar.ui.custom.PageNumberField;
import net.mytcg.topcar.util.Const;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.TouchEvent;
import net.rim.device.api.ui.UiApplication;

public class AuctionMenuScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	PageNumberField pageNumber = new PageNumberField("Page 1/1");
	
	ListItemField createauction = new ListItemField("Empty", -1, false, 0);
	ListItemField tmp = new ListItemField("Empty", -1, false, 0);
	
	Vector pages = new Vector();
	Vector tempList = new Vector();
	int currentPage = 0;

	public AuctionMenuScreen()
	{
		super(null);
		add(new ColorLabelField(""));
		bgManager.setStatusHeight(exit.getContentHeight());
		bgManager.setArrowMode(true);
		
		createauction = new ListItemField(Const.create_auction, 0, false, 0);
		
		exit.setChangeListener(this); 
		createauction.setChangeListener(this);

		tempList.addElement(createauction);
		addButton(new FixedButtonField(""));
		addButton(pageNumber);
		addButton(exit);
		
		doConnect(Const.auctioncategories);
	}
	
	public void process(String val) {
		int listSize = (Const.getUsableHeight()) / Const.getButtonHeight();
		int listCounter = 1;
		pages = new Vector();
		if (!(isDisplaying())) {
			int fromIndex;
	    	if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
	    		setText(val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex)));
	    	} else if (((fromIndex = val.indexOf(Const.xml_cardcategories)) != -1)) {
	    		int albumid = -1;
	    		String albumname = "";
	    		int endIndex = -1;
	    		String album = "";
	    		while ((fromIndex = val.indexOf(Const.xml_albumid)) != -1){
	    			if(listCounter >= listSize){
	    				pages.addElement(tempList);
	    				tempList = new Vector();
	    				listCounter=0;
	    			}
	    			endIndex = val.indexOf(Const.xml_album_end);
	    			album = val.substring(fromIndex, endIndex+Const.xml_album_end_length);
	    			fromIndex = album.indexOf(Const.xml_albumid);
	    			
	    			try {
	    				albumid = Integer.parseInt(album.substring(fromIndex+Const.xml_albumid_length, album.indexOf(Const.xml_albumid_end, fromIndex)));
	    			} catch (Exception e) {
	    				albumid = -1;
	    			}
	    			if ((fromIndex = album.indexOf(Const.xml_albumname)) != -1) {
	    				albumname = album.substring(fromIndex+Const.xml_albumname_length, album.indexOf(Const.xml_albumname_end, fromIndex));
	    			}
	    			val = val.substring(val.indexOf(Const.xml_album_end)+Const.xml_album_end_length);
	    			if(albumid != -1){
		    			synchronized(UiApplication.getEventLock()) {
		    				tmp = new ListItemField(albumname, albumid, true, 0);
		        			tmp.setChangeListener(this);
		        			tempList.addElement(tmp);
		        			listCounter++;
		        		}
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
	}
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
					Field[] temp = new Field[((Vector)pages.elementAt(currentPage)).size()];
	    			((Vector)pages.elementAt(currentPage)).copyInto(temp);
	    			bgManager.deleteAll();
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
		} else if(f == createauction){
			screen = new AlbumScreen(1);
			UiApplication.getUiApplication().pushScreen(screen);
		}else if(f instanceof ListItemField){
			int category = ((ListItemField)(f)).getId();
			if(category > -1){
				screen = new AuctionListScreen(category,0);
				UiApplication.getUiApplication().pushScreen(screen);
			} else if (category == -2) {
				screen = new AuctionListScreen(category,1);
				UiApplication.getUiApplication().pushScreen(screen);
			} else if(category  == -4){
				screen = new AuctionListScreen(category,0);
				UiApplication.getUiApplication().pushScreen(screen);
			} else if(category  == -3){
				screen = new AuctionListScreen(category,0);
				UiApplication.getUiApplication().pushScreen(screen);
			}
		}
	}
}