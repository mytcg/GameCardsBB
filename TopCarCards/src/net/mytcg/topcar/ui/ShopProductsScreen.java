package net.mytcg.topcar.ui;

import java.util.Vector;

import net.mytcg.topcar.ui.custom.ColorLabelField;
import net.mytcg.topcar.ui.custom.FixedButtonField;
import net.mytcg.topcar.ui.custom.ListItemField;
import net.mytcg.topcar.ui.custom.PageNumberField;
import net.mytcg.topcar.ui.custom.ThumbnailField;
import net.mytcg.topcar.util.Const;
import net.mytcg.topcar.util.Product;
import net.mytcg.topcar.util.SettingsBean;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class ShopProductsScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	PageNumberField pageNumber = new PageNumberField("Page 1/1");
	
	ThumbnailField tmp = new ThumbnailField(new Product(-1, "", 0, "", "", "", "", 0, null));
	ColorLabelField header = new ColorLabelField("");
	
	boolean update = true;
	boolean freebie = false;
	int id = -1;
	Vector pages = new Vector();
	int currentPage = 0;
	
	public void process(String val) {
		int listSize = (Const.getUsableHeight()) / 74;
		int listCounter = 0;
		pages = new Vector();
		Vector tempList = new Vector();
		SettingsBean _instance = SettingsBean.getSettings();
		if (update) {
			SettingsBean.saveSettings(_instance);
		}
		
		if ((!(isDisplaying()))||(update)) {
			int fromIndex;
	    	if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
	    		setText(val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex)));
	    	} else if (((fromIndex = val.indexOf(Const.xml_categoryproducts)) != -1)) {
	    		int productid = -1;
	    		boolean empty = true;
	    		String productname = "";
	    		String producttype = "";
	    		String productprice = "";
	    		String productnumcards = "";
	    		String productthumb = "";
	    		int endIndex = -1;
	    		String product = "";
	    		
	    		if ((fromIndex = val.indexOf(Const.xml_credits)) != -1) {
    				String credits = val.substring(fromIndex+Const.xml_credits_length, val.indexOf(Const.xml_credits_end, fromIndex));
    				_instance = SettingsBean.getSettings();
    				_instance.setCredits(credits);
    				SettingsBean.saveSettings(_instance);
    				synchronized(UiApplication.getEventLock()) {
    					header.setText("Current credits:" + SettingsBean.getSettings().getCredits());
    	    		}
    				
    			}
	    		
	    		while ((fromIndex = val.indexOf(Const.xml_productid)) != -1){
	    			if(listCounter >= listSize){
        				pages.addElement(tempList);
        				tempList = new Vector();
        				listCounter=0;
        			}
	    			endIndex = val.indexOf(Const.xml_product_end);
	    			product = val.substring(fromIndex, endIndex+Const.xml_product_end_length);
	    			fromIndex = product.indexOf(Const.xml_productid);
	    			
	    			try {
	    				productid = Integer.parseInt(product.substring(fromIndex+Const.xml_productid_length, product.indexOf(Const.xml_productid_end, fromIndex)));
	    			} catch (Exception e) {
	    				productid = -1;
	    			}
	    			if ((fromIndex = product.indexOf(Const.xml_productname)) != -1) {
	    				productname = product.substring(fromIndex+Const.xml_productname_length, product.indexOf(Const.xml_productname_end, fromIndex));
	    			}
	    			if ((fromIndex = product.indexOf(Const.xml_producttype)) != -1) {
	    				producttype = product.substring(fromIndex+Const.xml_producttype_length, product.indexOf(Const.xml_producttype_end, fromIndex));
	    			}
	    			if ((fromIndex = product.indexOf(Const.xml_productprice)) != -1) {
	    				productprice = product.substring(fromIndex+Const.xml_productprice_length, product.indexOf(Const.xml_productprice_end, fromIndex));
	    			}
	    			if ((fromIndex = product.indexOf(Const.xml_productnumcards)) != -1) {
	    				productnumcards = product.substring(fromIndex+Const.xml_productnumcards_length, product.indexOf(Const.xml_productnumcards_end, fromIndex));
	    			}
	    			if ((fromIndex = product.indexOf(Const.xml_productthumb)) != -1) {
	    				productthumb = product.substring(fromIndex+Const.xml_productthumb_length, product.indexOf(Const.xml_productthumb_end, fromIndex));
	    			}
	    			val = val.substring(val.indexOf(Const.xml_product_end)+Const.xml_product_end_length);
	    			
	    			synchronized(UiApplication.getEventLock()) {
	    				tmp = new ThumbnailField(new Product(productid, productname, 1, productthumb, "", "", "", 0, null));
	    				tmp.setProductType(producttype);
	    				tmp.setProductNumCards(productnumcards);
	    				if(freebie){
	    					tmp.setProductPrice("Free");
	    					tmp.setSecondLabel("Price: Free");
	    				}else{
	    					tmp.setProductPrice(productprice);
	    					tmp.setSecondLabel("Price: "+productprice);
	    				}
	    				tmp.setThirdLabel("Cards: "+productnumcards);
	        			tmp.setChangeListener(this);
	        			tempList.addElement(tmp);
	        			listCounter++;
	        		}
	    			empty = false;
	    		}
	    		if (empty) {
	    			synchronized(UiApplication.getEventLock()) {
	        			add(new ListItemField("Empty", -1, false, 0));
	        		}
	    		}else{
	    			pages.addElement(tempList);
	    		}
	    		synchronized(UiApplication.getEventLock()) {
	    			System.out.println("SIZE "+((Vector)pages.elementAt(0)).size());
	    			if(pages.size()<=1){
	    				bgManager.setArrowMode(false);
	    			}
	    			pageNumber.setLabel("Page 1/"+pages.size());
	    			ThumbnailField[] temp = new ThumbnailField[((Vector)pages.elementAt(0)).size()];
	    			((Vector)pages.elementAt(0)).copyInto(temp);
	    			bgManager.deleteAll();
	    			bgManager.add(header);
		    		bgManager.addAll(temp);
		    	}
	    	}
	    	invalidate();
	    	_instance = null;
	    	//setDisplaying(true);
		}		
	}
	
	protected boolean navigationMovement(int dx, int dy, int status, int time) {
		if(dy == 0 && dx == -1){
			if(pages.size() >1){
				if((currentPage-1)<0){
					currentPage = pages.size()-1;
				}else{
					currentPage--;
				}
				synchronized(UiApplication.getEventLock()) {
					pageNumber.setLabel("Page "+(currentPage+1)+"/"+pages.size());
					ThumbnailField[] temp = new ThumbnailField[((Vector)pages.elementAt(currentPage)).size()];
	    			((Vector)pages.elementAt(currentPage)).copyInto(temp);
	    			bgManager.deleteAll();
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
					ThumbnailField[] temp = new ThumbnailField[((Vector)pages.elementAt(currentPage)).size()];
	    			((Vector)pages.elementAt(currentPage)).copyInto(temp);
	    			bgManager.deleteAll();
	    			bgManager.add(header);
		    		bgManager.addAll(temp);
		    	}
			}
			return true;
		}else{
			return super.navigationMovement(dx, dy, status, time);
		}
	}
	
	public ShopProductsScreen(int id, boolean freebie) {
		super(null);
		this.id = id;
		this.freebie = freebie;
		bgManager.setStatusHeight(exit.getContentHeight());
		bgManager.setArrowMode(true);
		
		exit.setChangeListener(this);
		
		addButton(new FixedButtonField(""));
		addButton(pageNumber);
		addButton(exit);
		
		if (!freebie) {
			header.setText("Current credits:" + SettingsBean.getSettings().getCredits());
			add(header);
		} else {
			header.setText("Received: 300 credits and a free starter pack.");
			add(header);
		}
		
		if (!freebie) {
			doConnect(Const.categoryproducts+"&categoryId="+id);
		} else {
			doConnect(Const.freebieproducts+"&categoryId="+id);
		}
	}
	
	protected void onExposed() {
		//screen = null;
		//if (!isVisible()) {
		//	doConnect(Const.categoryproducts+"&categoryid="+id);
		//}
		super.onExposed();
	}
	public boolean onClose() {
		screen = null;
		UiApplication.getUiApplication().popScreen(this);
		return true;
	}
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		} else if(f instanceof ThumbnailField){
			ThumbnailField product = ((ThumbnailField)(f));
			screen = new ShopPurchaseScreen(product.getProduct(),product.getThumbnail(), freebie);
			UiApplication.getUiApplication().pushScreen(screen);
		}
	}
}