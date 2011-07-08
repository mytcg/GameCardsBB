package net.mytcg.dev.ui;

import net.mytcg.dev.ui.custom.FixedButtonField;
import net.mytcg.dev.ui.custom.ListItemField;
import net.mytcg.dev.util.Auction;
import net.mytcg.dev.util.Const;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class BidOrBuyScreen extends AppScreen implements FieldChangeListener
{
	Auction auction = null;
	FixedButtonField exit = new FixedButtonField(Const.back);
	
	ListItemField placebid = new ListItemField("Empty", -1, false, 0);
	ListItemField buynow = new ListItemField("Empty", -1, false, 0);
	boolean purchased = false;
	AuctionListScreen auctionListScreen = null;
	
	public BidOrBuyScreen(AuctionListScreen auctionListScreen,Auction auction)
	{
		super(null);
		this.auctionListScreen = auctionListScreen;
		this.auction = auction;
		bgManager.setStatusHeight(exit.getContentHeight());
		
		placebid = new ListItemField(Const.placebid, Const.AUCTIONS, false, 0);
		buynow = new ListItemField(Const.buynow, Const.AUCTIONS, false, 0);

		placebid.setChangeListener(this);
		buynow.setChangeListener(this);
		exit.setChangeListener(this);
		
		add(placebid);
		add(buynow);
		
		addButton(exit);
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
	}
	public boolean onClose() {
		screen = null;
		UiApplication.getUiApplication().popScreen(this);
		return true;
	}
	protected void onExposed() {
		if(purchased){
			auctionListScreen.purchased = true;
			UiApplication.getUiApplication().popScreen(this);
		}
	}
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		} else if(f == placebid){
			screen = new PlaceBidScreen(this, auction);
			UiApplication.getUiApplication().pushScreen(screen);
		} else if(f == buynow){
			screen = new BuyNowScreen(this, auction);
			UiApplication.getUiApplication().pushScreen(screen);
		} 
	}
}