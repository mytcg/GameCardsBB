package net.mytcg.topcar.ui;

import net.mytcg.topcar.ui.custom.AuctionField;
import net.mytcg.topcar.ui.custom.ColorLabelField;
import net.mytcg.topcar.ui.custom.FixedButtonField;
import net.mytcg.topcar.ui.custom.SexyEditField;
import net.mytcg.topcar.util.Auction;
import net.mytcg.topcar.util.Const;
import net.mytcg.topcar.util.SettingsBean;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;


public class AuctionInfoScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	FixedButtonField bid = new FixedButtonField(Const.bid);
	FixedButtonField buynow = new FixedButtonField(Const.buynow);
	
	SexyEditField bidbox = new SexyEditField("");
	
	boolean purchased = false;
	
	boolean update = true;
	Auction auction = null;
	Bitmap auctionthumb = null;
	
	public void process(String val) {
		int fromIndex;
		if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
			if(val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex)).equals("1")){
				synchronized(UiApplication.getEventLock()) {
					try{
						setText("You are now the highest bidder!");
					}catch(Exception e){
						
					}
				}
			}else{
				synchronized(UiApplication.getEventLock()) {
					try{
						status.setText(val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex)));
					}catch(Exception e){
						
					}
				}
			}
    	}
	}
	
	public AuctionInfoScreen(Auction auction, Bitmap auctionthumbnail) {
		super(null);
		add(new ColorLabelField(""));
		this.auction = auction;
		auctionthumb = auctionthumbnail;
		bgManager.setStatusHeight(exit.getContentHeight());
		
		exit.setChangeListener(this);
		
		add(new AuctionField(auction,auctionthumbnail));
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		addButton(exit);
		invalidate();
		setDisplaying(true);
	}
	public AuctionInfoScreen(Auction auction, Bitmap auctionthumbnail, boolean biddingtrue) {
		super(null);
		add(new ColorLabelField("Credits: " + SettingsBean.getSettings().getCredits()+" Premium: " + SettingsBean.getSettings().getPremium()));
		this.auction = auction;
		auctionthumb = auctionthumbnail;
		bgManager.setStatusHeight(exit.getContentHeight());
		
		exit.setChangeListener(this);
		bid.setChangeListener(this);
		buynow.setChangeListener(this);
		
		
		
		add(new AuctionField(auction,auctionthumbnail));
		add(new ColorLabelField("Place Bid"));
		add(bidbox);
		int bidval = 0;
		try {
			bidval = Integer.parseInt(auction.getPrice());
			if (bidval == 0) {
				bidval = Integer.parseInt(auction.getOpeningBid());
			}
			bidval++;
		} catch (Exception e) {
			bidval = 0;
		}
		bidbox.setText(""+bidval);
		
		addButton(bid);
		if (auction.getBuyNowPrice().length() > 0) {
			addButton(buynow);
		} else {
			addButton(new FixedButtonField(""));
		}
		addButton(exit);
		invalidate();
		setDisplaying(true);
	}
	
	protected void onExposed() {
		if (!isVisible()) {
			UiApplication.getUiApplication().popScreen(this);
		}
	}
	public boolean onClose() {
		screen = null;
		UiApplication.getUiApplication().popScreen(this);
		return true;
	}
	
	public boolean valid(){
		String bid = bidbox.getText();
		if(bid.equals("")){
			setText("Please enter a bid.");
			return false;
		}
		try{
			Integer.parseInt(bid);
		}catch(NumberFormatException e){
			setText("Please enter a numeric value.");
			return false;
		}
		if(auction.getPrice().equals("")){
			if(Integer.parseInt(bid)<Integer.parseInt(auction.getOpeningBid())){
				setText("Your bid needs to equal or exceed the opning bid..");
				return false;
			}
		}else{
			if(Integer.parseInt(bid)<=Integer.parseInt(auction.getPrice())){
				setText("Your bid needs to be higher than the current bid.");
				return false;
			}
		}
		return true;
	}
	
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		} else if(f == bid){
			if(valid()){
				synchronized(UiApplication.getEventLock()) {
					try{
						bid.empty = true;
						bid.setLabel("");
						buynow.empty = true;
						buynow.setLabel("");
						//addButton(new FixedButtonField(""));
						setText("Attempting to place bid...");
					}catch(Exception e){
						
					}
				}
				doConnect(Const.auctionbid+"&username="+auction.getUsername()+"&bid="+bidbox.getText()+"&auctioncardid="+auction.getId(), false);
			}
		} else if(f == buynow){
			screen = new BuyNowScreen(this, auction);
			UiApplication.getUiApplication().pushScreen(screen);
		} 
	}
}