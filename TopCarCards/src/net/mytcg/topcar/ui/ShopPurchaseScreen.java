package net.mytcg.topcar.ui;

import net.mytcg.topcar.ui.custom.ColorLabelField;
import net.mytcg.topcar.ui.custom.FixedButtonField;
import net.mytcg.topcar.ui.custom.ProductField;
import net.mytcg.topcar.util.Const;
import net.mytcg.topcar.util.Product;
import net.mytcg.topcar.util.SettingsBean;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.UiApplication;

public class ShopPurchaseScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	FixedButtonField confirm = new FixedButtonField(Const.purchase);
	ColorLabelField lblConfirm = null;
	
	boolean update = true;
	boolean freebie = false;
	Product product = null;
	
	public void process(String val) {
		val = "<cardsincategory>"+val+"</cardsincategory>";
		screen = new AlbumListScreen(val,(freebie?3:0));
		synchronized(UiApplication.getEventLock()) {
			UiApplication.getUiApplication().pushScreen(screen);
		}
	}
	public ShopPurchaseScreen(Product product, Bitmap productthumbnail, boolean freebie) {
		super(null);
		this.product = product;
		this.freebie = freebie;
		bgManager.setStatusHeight(exit.getContentHeight());
		Font _font = getFont();
		_font = _font.derive(Const.TYPE,Const.FONT+2);
		setFont(_font);
		if(freebie){
			lblConfirm = new ColorLabelField("Are you sure you want this free booster pack?");
		}else{
			lblConfirm = new ColorLabelField("Current credits: " + SettingsBean.getSettings().getCredits());
		}
		exit.setChangeListener(this);
		confirm.setChangeListener(this);
		
		add(lblConfirm);
		add(new ProductField(product,productthumbnail));
		
		addButton(confirm);
		addButton(new FixedButtonField(""));
		addButton(exit);
		
		invalidate();
		setDisplaying(true);
	}
	
	protected void onExposed() {
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
		} else if(f == confirm){
			doConnect(Const.buyproduct+product.getId()+Const.height+Const.getCardHeight()+Const.bbheight+Const.getAppHeight()+Const.width+Const.getCardWidth()+Const.freebie+(freebie?"1":"0"));
		}
	}
}