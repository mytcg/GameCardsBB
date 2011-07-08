package net.mytcg.dev.ui;

import net.mytcg.dev.ui.custom.ColorLabelField;
import net.mytcg.dev.ui.custom.FixedButtonField;
import net.mytcg.dev.util.Auction;
import net.mytcg.dev.util.Const;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class BuyNowScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	FixedButtonField confirm = new FixedButtonField(Const.confirm);
	ColorLabelField lblbuynow = null;
	Auction auction = null;
	BidOrBuyScreen screen = null;
	boolean purchased = false;
	
	public BuyNowScreen(BidOrBuyScreen screen, Auction auction)
	{
		super(null);
		this.auction = auction;
		this.screen = screen;
		bgManager.setStatusHeight(exit.getContentHeight());
		lblbuynow = new ColorLabelField("Are you sure you want to buy out the auction of "+auction.getDesc()+" for "+auction.getBuyNowPrice()+" credits?");
		
		add(lblbuynow);

		exit.setChangeListener(this);
		confirm.setChangeListener(this);
		
		addButton(exit);
		addButton(new FixedButtonField(""));
		addButton(confirm);
	}
	
	public void process(String val) {
		int fromIndex;
		if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
			if(val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex)).equals("1")){
				synchronized(UiApplication.getEventLock()) {
					try{
						lblbuynow.setText(" Purchase success!");
					}catch(Exception e){
						
					}
				}
			}else{
				synchronized(UiApplication.getEventLock()) {
					try{
						lblbuynow.setText(" Purchase failed.");
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
					this.removeButton(confirm);
					addButton(new FixedButtonField(""));
				}catch(Exception e){
					
				}
			}
			doConnect(Const.buyauctionnow+"&username="+auction.getUsername()+"&buynowprice="+auction.getBuyNowPrice()+"&auctioncardid="+auction.getId()+"&usercardid="+auction.getUserCardId());
		}
	}
}