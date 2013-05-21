package net.mytcg.topcar.ui;

import net.mytcg.topcar.ui.custom.ColorLabelField;
import net.mytcg.topcar.ui.custom.FixedButtonField;
import net.mytcg.topcar.ui.custom.SexyEditField;
import net.mytcg.topcar.util.Auction;
import net.mytcg.topcar.util.Const;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class PlaceBidScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	FixedButtonField bid = new FixedButtonField(Const.bid);
	SexyEditField textbid = new SexyEditField("");
	ColorLabelField lblBid = null;
	ColorLabelField lblBuyNowPrice = null;
	ColorLabelField lblEndDate = null;
	ColorLabelField lblYourBid = null;
	AuctionInfoScreen screen;
	Auction auction;
	boolean purchased = false;
	
	public PlaceBidScreen(AuctionInfoScreen screen, Auction auction)
	{
		super(null);
		this.auction = auction;
		this.screen =  screen;
		bgManager.setStatusHeight(exit.getContentHeight());
		if(auction.getPrice().equals("")){
			lblBid = new ColorLabelField(Const.openingbid+auction.getOpeningBid());
		}else{
			lblBid = new ColorLabelField(Const.currentbid+auction.getPrice());
		}
		lblBuyNowPrice = new ColorLabelField(Const.buynowprice+auction.getBuyNowPrice());
		lblEndDate = new ColorLabelField(Const.enddate+auction.getEndDate());
		lblYourBid = new ColorLabelField(Const.yourbid);
		
		add(lblBid);
		add(lblBuyNowPrice);
		add(lblEndDate);
		add(lblYourBid);
		add(textbid);

		exit.setChangeListener(this);
		bid.setChangeListener(this);
		
		addButton(bid);
		addButton(new FixedButtonField(""));
		addButton(exit);
	}
	
	public void process(String val) {
		int fromIndex;
		if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
			if(val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex)).equals("1")){
				synchronized(UiApplication.getEventLock()) {
					try{
						this.delete(textbid);
						lblBuyNowPrice.setText("");
						lblEndDate.setText("");
						lblYourBid.setText("");
						lblBid.setText(" Bid success!");
					}catch(Exception e){
						
					}
				}
			}else{
				synchronized(UiApplication.getEventLock()) {
					try{
						this.delete(textbid);
						lblBuyNowPrice.setText("");
						lblEndDate.setText("");
						lblYourBid.setText("");
						lblBid.setText(" Bid failed.");
					}catch(Exception e){
						
					}
				}
			}
    	}
	}
	
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			UiApplication.getUiApplication().popScreen(this);
		} else if (f == bid && purchased == false){
			if(valid()){
				purchased = true;
				screen.purchased = true;
				synchronized(UiApplication.getEventLock()) {
					try{
						this.removeButton(bid);
						addButton(new FixedButtonField(""));
					}catch(Exception e){
						
					}
				}
				doConnect(Const.auctionbid+"&username="+auction.getUsername()+"&bid="+textbid.getText()+"&auctioncardid="+auction.getId());
			}
		}
	}
	
	public boolean valid(){
		String bid = textbid.getText();
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
}