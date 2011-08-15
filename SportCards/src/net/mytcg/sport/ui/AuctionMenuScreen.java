package net.mytcg.sport.ui;

import net.mytcg.sport.ui.custom.FixedButtonField;
import net.mytcg.sport.ui.custom.ListItemField;
import net.mytcg.sport.util.Const;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class AuctionMenuScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	
	ListItemField createauction = new ListItemField("Empty", -1, false, 0);
	ListItemField tmp = new ListItemField("Empty", -1, false, 0);

	public AuctionMenuScreen()
	{
		super(null);
		bgManager.setStatusHeight(exit.getContentHeight());
		
		createauction = new ListItemField(Const.create_auction, 0, false, 0);
		
		exit.setChangeListener(this); 
		createauction.setChangeListener(this);

		add(createauction);
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		addButton(exit);
		
		doConnect(Const.auctioncategories);
	}
	
	public void process(String val) {
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
		        			add(tmp);
		        		}
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
			}
		}
	}
}