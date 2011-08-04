package net.mytcg.topcar.ui;

import net.mytcg.topcar.ui.custom.ColorLabelField;
import net.mytcg.topcar.ui.custom.FixedButtonField;
import net.mytcg.topcar.ui.custom.ListItemField;
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
	
	ThumbnailField tmp = new ThumbnailField(new Product(-1, "", 0, "", "", "", "", 0, null));
	
	boolean update = true;
	boolean freebie = false;
	int id = -1;
	
	public void process(String val) {
		SettingsBean _instance = SettingsBean.getSettings();
		System.out.println(val);
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
	    		while ((fromIndex = val.indexOf(Const.xml_productid)) != -1){
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
	        			add(tmp);
	        		}
	    			empty = false;
	    		}
	    		if (empty) {
	    			synchronized(UiApplication.getEventLock()) {
	        			add(new ListItemField("Empty", -1, false, 0));
	        		}
	    		}
	    	}
	    	invalidate();
	    	_instance = null;
	    	setDisplaying(true);
		}		
	}
	public ShopProductsScreen(int id, boolean freebie) {
		super(null);
		this.id = id;
		this.freebie = freebie;
		bgManager.setStatusHeight(exit.getContentHeight());
		
		exit.setChangeListener(this);
		
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		addButton(exit);
		
		add(new ColorLabelField("Current credits:" + SettingsBean.getSettings().getCredits()));
		
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