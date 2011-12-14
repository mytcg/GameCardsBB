package net.mytcg.topcar.ui;

import java.util.Vector;

import net.mytcg.topcar.ui.custom.ColorLabelField;
import net.mytcg.topcar.ui.custom.FixedButtonField;
import net.mytcg.topcar.ui.custom.ListItemField;
import net.mytcg.topcar.ui.custom.PageNumberField;
import net.mytcg.topcar.util.Const;
import net.mytcg.topcar.util.SettingsBean;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.LabelField;

public class RankingsCategoriesScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	PageNumberField pageNumber = new PageNumberField("Page 1/1");
	
	ListItemField tmp = new ListItemField("Empty", -1, false, 0);
	
	boolean update = true;
	boolean friends = false;
	Vector pages = new Vector();
	Vector tempList = new Vector();
	int currentPage = 0;
	
	public void process(String val) {
		int listSize = (Const.getUsableHeight()) / Const.getButtonHeight();
		int listCounter = 0;
		pages = new Vector();
		SettingsBean _instance = SettingsBean.getSettings();
		if (update) {
			SettingsBean.saveSettings(_instance);
		}
		
		if ((!(isDisplaying()))||(update)) {
			int fromIndex;
	    	if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
	    		setText(val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex)));
	    	} else if (((fromIndex = val.indexOf(Const.xml_productcategories)) != -1)) {
	    		int albumid = -1;
	    		boolean empty = true;
	    		String albumname = "";
	    		int endIndex = -1;
	    		String album = "";
	    		while ((fromIndex = val.indexOf(Const.xml_albumid)) != -1){
	    			if(listCounter >= listSize){
	    				pages.addElement(tempList);
	    				tempList = new Vector();
	    				listCounter=0;
	    			}
	    			endIndex = val.indexOf(Const.xml_albumname_end);
	    			album = val.substring(fromIndex, endIndex+Const.xml_albumname_end_length);
	    			fromIndex = album.indexOf(Const.xml_albumid);
	    			
	    			try {
	    				albumid = Integer.parseInt(album.substring(fromIndex+Const.xml_albumid_length, album.indexOf(Const.xml_albumid_end, fromIndex)));
	    			} catch (Exception e) {
	    				albumid = -1;
	    			}
	    			if ((fromIndex = album.indexOf(Const.xml_albumname)) != -1) {
	    				albumname = album.substring(fromIndex+Const.xml_albumname_length, album.indexOf(Const.xml_albumname_end, fromIndex));
	    			}
	    			val = val.substring(val.indexOf(Const.xml_albumname_end)+Const.xml_albumname_end_length);
	    			
	    			synchronized(UiApplication.getEventLock()) {
	    				tmp = new ListItemField(albumname, albumid, true, 0);
	        			tmp.setChangeListener(this);
	        			tempList.addElement(tmp);
	        			listCounter++;
	        		}
	    			empty = false;
	    		}
	    		if (empty) {
	    			synchronized(UiApplication.getEventLock()) {
	    				tempList.addElement(new ListItemField("Empty", -1, false, 0));
	        		}
	    		}
	    		pages.addElement(tempList);
		        synchronized(UiApplication.getEventLock()) {
		        	System.out.println("SIZE "+((Vector)pages.elementAt(0)).size());
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
	    	_instance = null;
	    	//setDisplaying(true);
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
	
	public RankingsCategoriesScreen(boolean friends) {
		super(null);
		this.friends = friends;
		add(new ColorLabelField(""));
		tempList.addElement(new ColorLabelField("Choose a category."));
		bgManager.setStatusHeight(exit.getContentHeight());
		bgManager.setArrowMode(true);
		
		exit.setChangeListener(this);
		
		addButton(new FixedButtonField(""));
		addButton(pageNumber);
		addButton(exit);
		
		doConnect(Const.leaders);
	}
	
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		} else if(f instanceof ListItemField){
			int categoryid = ((ListItemField)(f)).getId();
			String category = ((ListItemField)(f)).getLabel();
			screen = new RankingsScreen(category,categoryid,friends);
			UiApplication.getUiApplication().pushScreen(screen);
		}
	}
}