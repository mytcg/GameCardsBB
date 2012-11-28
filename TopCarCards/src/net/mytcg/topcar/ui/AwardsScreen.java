package net.mytcg.topcar.ui;

import java.util.Vector;

import net.mytcg.topcar.ui.custom.AwardField;
import net.mytcg.topcar.ui.custom.ColorLabelField;
import net.mytcg.topcar.ui.custom.FixedButtonField;
import net.mytcg.topcar.ui.custom.ListItemField;
import net.mytcg.topcar.ui.custom.PageNumberField;
import net.mytcg.topcar.util.Award;
import net.mytcg.topcar.util.Const;
import net.mytcg.topcar.util.SubAward;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
//import net.rim.device.api.ui.TouchEvent;
import net.rim.device.api.ui.UiApplication;

public class AwardsScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	PageNumberField pageNumber = new PageNumberField("Page 1/1");
	
	AwardField tmp = null;
	String awards = null;
	Vector pages = new Vector();
	Vector tempList = new Vector();
	int currentPage = 0;

	public AwardsScreen()
	{
		super(null);
		add(new ColorLabelField(""));
		bgManager.setStatusHeight(exit.getContentHeight());
		bgManager.setArrowMode(true);
		
		
		exit.setChangeListener(this); 
		
		addButton(new FixedButtonField(""));
		addButton(pageNumber);
		addButton(exit);
		
		doConnect(Const.getachis);
	}
	
	public void process(String val) {
		System.out.println(val);
		int listSize = (Const.getUsableHeight()) / Const.getButtonHeight();
		int listCounter = 0;
		pages = new Vector();
		int fromIndex;
	    if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
	    	setText(val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex)));
	    }else if (((fromIndex = val.indexOf(Const.xml_achis)) != -1)) {
		   	String awardname = "";
		   	String description = "";
		   	String incompleteimage = "";
		   	int endIndex = -1;
		   	String awards = "";
		   	while ((fromIndex = val.indexOf(Const.xml_achi)) != -1){
		   		if(listCounter >= listSize){
    				pages.addElement(tempList);
    				tempList = new Vector();
    				listCounter=0;
    			}
		   		endIndex = val.indexOf(Const.xml_achi_end);
		   		awards = val.substring(fromIndex, endIndex+Const.xml_achi_end_length);
		   		fromIndex = awards.indexOf(Const.xml_achi);
		   		
		   		if ((fromIndex = awards.indexOf(Const.xml_name)) != -1) {
		   			awardname = awards.substring(fromIndex+Const.xml_name_length, awards.indexOf(Const.xml_name_end, fromIndex));
		   		}
		   		if ((fromIndex = awards.indexOf(Const.xml_description)) != -1) {
		   			description = awards.substring(fromIndex+Const.xml_description_length, awards.indexOf(Const.xml_description_end, fromIndex));
		   		}
		   		if ((fromIndex = awards.indexOf(Const.xml_incompleteimage)) != -1) {
		   			incompleteimage = awards.substring(fromIndex+Const.xml_incompleteimage_length, awards.indexOf(Const.xml_incompleteimage_end, fromIndex));
		   		}
		   		
		   		if(!awardname.equals("")){
		   			Award award = new Award();
		   			award.setDescription(description);
		   			award.setName(awardname);
		   			award.setIncompleteImage(incompleteimage);
		   			
		   			while((fromIndex = awards.indexOf(Const.xml_subachi)) != -1){
		   				int progress = 0;
			   			int target = 0;
			   			String datecompleted = "";
			   			String completeimage="";
			   			SubAward subaward = new SubAward();
			  
		   				if ((fromIndex = awards.indexOf(Const.xml_progress)) != -1) {
				   			try {
					   			progress = Integer.parseInt(awards.substring(fromIndex+Const.xml_progress_length, awards.indexOf(Const.xml_progress_end, fromIndex)));
					   		} catch (Exception e) {
					   			progress = 0;
					   		}
				   		}
		   				if ((fromIndex = awards.indexOf(Const.xml_target)) != -1) {
				   			try {
					   			target = Integer.parseInt(awards.substring(fromIndex+Const.xml_target_length, awards.indexOf(Const.xml_target_end, fromIndex)));
					   		} catch (Exception e) {
					   			target = 0;
					   		}
				   		}
		   				if ((fromIndex = awards.indexOf(Const.xml_datecompleted)) != -1) {
		   					datecompleted = awards.substring(fromIndex+Const.xml_datecompleted_length, awards.indexOf(Const.xml_datecompleted_end, fromIndex));
				   		}
		   				if ((fromIndex = awards.indexOf(Const.xml_completeimage)) != -1) {
		   					completeimage = awards.substring(fromIndex+Const.xml_completeimage_length, awards.indexOf(Const.xml_completeimage_end, fromIndex));
				   		}
		   				awards = awards.substring(awards.indexOf(Const.xml_subachi_end)+Const.xml_subachi_end_length);
		   				subaward.setProgress(progress);
		   				subaward.setTarget(target);
		   				subaward.setCompleteImage(completeimage);
		   				subaward.setDateCompleted(datecompleted);
		   				award.addSubAward(subaward);
		   			}
		   			synchronized(UiApplication.getEventLock()) {
		   				tmp = new AwardField(award,-1);
		       			tmp.setChangeListener(this);
		       			tempList.addElement(tmp);
	        			listCounter++;
		       		}
		   		}
		   		val = val.substring(val.indexOf(Const.xml_achi_end)+Const.xml_achi_end_length);
		   	}
		   	pages.addElement(tempList);
    		synchronized(UiApplication.getEventLock()) {
    			if(pages.size()<=1){
    				bgManager.setArrowMode(false);
    			}
    			pageNumber.setLabel("Page 1/"+pages.size());
    			AwardField[] temp = new AwardField[((Vector)pages.elementAt(0)).size()];
    			((Vector)pages.elementAt(0)).copyInto(temp);
    			bgManager.deleteAll();
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
					ListItemField[] temp = new ListItemField[((Vector)pages.elementAt(currentPage)).size()];
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
		}else if(f instanceof AwardField){
			Award award = ((AwardField)(f)).getAward();
			if(award != null){
				screen = new AwardDetailScreen(award);
				UiApplication.getUiApplication().pushScreen(screen);
			}
		}
	}
}