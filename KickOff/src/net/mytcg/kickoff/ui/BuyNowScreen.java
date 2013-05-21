package net.mytcg.kickoff.ui;

import net.mytcg.kickoff.ui.custom.AuctionField;
import net.mytcg.kickoff.ui.custom.ColorLabelField;
import net.mytcg.kickoff.ui.custom.FixedButtonField;
import net.mytcg.kickoff.util.Auction;
import net.mytcg.kickoff.util.Card;
import net.mytcg.kickoff.util.Const;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class BuyNowScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	FixedButtonField confirm = new FixedButtonField(Const.confirm);
	ColorLabelField lblbuynow = null;
	Auction auction = null;
	AuctionInfoScreen screen = null;
	boolean purchased = false;
	
	protected void onExposed() {
		if (!isVisible()) {
			UiApplication.getUiApplication().popScreen(this);
		}
	}
	
	public BuyNowScreen(AuctionInfoScreen screen, Auction auction)
	{
		super(null);
		this.auction = auction;
		this.screen = screen;
		bgManager.setStatusHeight(exit.getContentHeight());
		lblbuynow = new ColorLabelField("Are you sure you want to buy out the auction of "+auction.getDesc()+" for "+auction.getBuyNowPrice()+" credits?");
		lblbuynow.setColor(Color.RED);
		
		add(lblbuynow);
		
		add(new AuctionField(auction, screen.auctionthumb));

		exit.setChangeListener(this);
		confirm.setChangeListener(this);
		
		addButton(confirm);
		addButton(new FixedButtonField(""));
		addButton(exit);
	}
	
	public void process(String val) {
		int fromIndex;
		if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
			if(val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex)).equals("1")){
				synchronized(UiApplication.getEventLock()) {
					try{
						super.screen = new ImageScreen(new Card(auction.getCardId(), auction.getDesc(), 1, auction.getThumburl(), auction.getFronturl(), auction.getBackurl(), "", 0, null, 0, "", ""), this, null);
						UiApplication.getUiApplication().pushScreen(super.screen);
					}catch(Exception e){
						
					}
				}
			}else{
				synchronized(UiApplication.getEventLock()) {
					try{
						lblbuynow.setText(" Purchase could not be made at this.");
					}catch(Exception e){
						
					}
				}
			}
    	}
	}
	
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			UiApplication.getUiApplication().popScreen(this);
		} else if (f == confirm && purchased == false){
			purchased = true;
			screen.purchased = true;
			synchronized(UiApplication.getEventLock()) {
				try{
					//super.screen = new ImageScreen(new Card(auction.getCardId(), auction.getDesc(), 1, auction.getThumburl(), auction.getFronturl(), auction.getBackurl(), "", 0, null, 0, "", ""), this, null);
					//UiApplication.getUiApplication().pushScreen(super.screen);
					
					confirm.empty = true;
					confirm.setLabel("");
					lblbuynow.setText("");
				}catch(Exception e){
					
				}
			}
			doConnect(Const.buyauctionnow+"&username="+auction.getUsername()+"&buynowprice="+auction.getBuyNowPrice()+"&auctioncardid="+auction.getId()+"&usercardid="+auction.getUserCardId());
		}
	}
}