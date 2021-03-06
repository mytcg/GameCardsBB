package net.mytcg.dev.ui;

import net.mytcg.dev.ui.custom.ColorLabelField;
import net.mytcg.dev.ui.custom.FixedButtonField;
import net.mytcg.dev.ui.custom.ProductField;
import net.mytcg.dev.util.Const;
import net.mytcg.dev.util.Product;
import net.mytcg.dev.util.SettingsBean;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.UiApplication;

public class ShopPurchaseScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	FixedButtonField confirm = new FixedButtonField(Const.purchase);
	FixedButtonField cards = new FixedButtonField(Const.boostercards);
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
			lblConfirm = new ColorLabelField("Credits: " + SettingsBean.getSettings().getCredits()+" Premium: " + SettingsBean.getSettings().getPremium());
		}
		exit.setChangeListener(this);
		confirm.setChangeListener(this);
		cards.setChangeListener(this);
		
		add(lblConfirm);
		add(new ProductField(product,productthumbnail));
		
		addButton(confirm);
		addButton(cards);
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
			String purchase = "3";
			if(product.getPriceType()==2){
				purchase = "2";
			}
			doConnect(Const.buyproduct+product.getId()+Const.height+Const.getCardHeight()+Const.jpg+Const.bbheight+Const.getAppHeight()+Const.width+Const.getCardWidth()+Const.freebie+(freebie?"1":"0")+Const.purch+purchase);
		} else if(f == cards){
			screen = new ViewBoosterScreen(product.getId());
			UiApplication.getUiApplication().pushScreen(screen);
		}
	}
}