package net.mytcg.topcar.ui;

import net.mytcg.topcar.ui.custom.CardField;
import net.mytcg.topcar.ui.custom.ColorLabelField;
import net.mytcg.topcar.ui.custom.FixedButtonField;
import net.mytcg.topcar.ui.custom.SexyEditField;
import net.mytcg.topcar.util.Card;
import net.mytcg.topcar.util.Const;
import net.mytcg.topcar.util.SettingsBean;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.EditField;

public class AuctionCreateScreen extends AppScreen implements FieldChangeListener
{
	boolean created = false;
	FixedButtonField exit = new FixedButtonField(Const.back);
	FixedButtonField auction = new FixedButtonField(Const.auction);
	SexyEditField openingBid = new SexyEditField("", EditField.FILTER_NUMERIC, 36);
	SexyEditField buyNowPrice = new SexyEditField("", EditField.FILTER_NUMERIC, 36);
	SexyEditField auctionDuration = new SexyEditField("", EditField.FILTER_NUMERIC, 36);
	ColorLabelField lblOpeningBid = new ColorLabelField(" Opening bid");
	ColorLabelField lblBuyNowPrice = new ColorLabelField(" Buy now price");
	ColorLabelField lblAuctionDuration = new ColorLabelField(" Auction duration(days)");
	CardField cardfield = null;

	Card card = null;
	
	public AuctionCreateScreen(Card card, Bitmap cardthumb)
	{
		super(null);
		this.card = card;
		SettingsBean _instance = SettingsBean.getSettings();
		_instance.created = false;
		SettingsBean.saveSettings(_instance);
		Font _font = getFont();
		_font = _font.derive(Font.BOLD, Const.FONT);
		setFont(_font);

		bgManager.setStatusHeight(exit.getContentHeight());
		
		exit.setChangeListener(this);
		auction.setChangeListener(this);
		cardfield = new CardField(card, cardthumb);
		add(cardfield);
		add(lblOpeningBid);
		add(openingBid);
		add(lblBuyNowPrice);
		add(buyNowPrice);
		add(lblAuctionDuration);
		add(auctionDuration);

		addButton(auction);
		addButton(new FixedButtonField(""));
		addButton(exit);
		
		invalidate();
		setDisplaying(true);
	}

	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		} else if (f == auction && created == false) {
			if(valid()){
				created = true;
				SettingsBean _instance = SettingsBean.getSettings();
				_instance.created = true;
				SettingsBean.saveSettings(_instance);
				synchronized(UiApplication.getEventLock()) {
					try{
						this.removeButton(auction);
						addButton(new FixedButtonField(""));
					}catch(Exception e){
						
					}
				}
				doConnect(Const.createauction+"&cardid="+card.getId()+"&bid="+openingBid.getText()+"&buynow="+buyNowPrice.getText()+"&days="+auctionDuration.getText());
			}
		} 
	}
	
	public void process(String val) {
		int fromIndex = -1;
		System.out.println(val);
		if ((fromIndex = val.indexOf(Const.xml_success)) != -1) {
			if(val.substring(fromIndex+Const.xml_success_length, val.indexOf(Const.xml_success_end, fromIndex)).equals("1")){
				synchronized(UiApplication.getEventLock()) {
					try{
						this.delete(cardfield);
						this.delete(openingBid);
						this.delete(buyNowPrice);
						this.delete(auctionDuration);
						lblBuyNowPrice.setText("");
						lblAuctionDuration.setText("");
						lblOpeningBid.setText(" Auction created!");
					}catch(Exception e){
						
					}
				}
			}else{
				synchronized(UiApplication.getEventLock()) {
					try{
						this.delete(cardfield);
						this.delete(openingBid);
						this.delete(buyNowPrice);
						this.delete(auctionDuration);
						lblBuyNowPrice.setText("");
						lblAuctionDuration.setText("");
						lblOpeningBid.setText(" Error creating auction.");
					}catch(Exception e){
						
					}
				}
			}
		}
	}
	
	public boolean valid(){
		if (openingBid.getText().length() == 0) {
			setText("Please enter an opening bid.");
			return false;
		}
		try{
			Integer.parseInt(openingBid.getText());
		}catch(NumberFormatException e){
			setText("Please enter a valid opening bid.");
			return false;
		}
		if (buyNowPrice.getText().length() == 0) {
			setText("Please enter a buy now price.");
			return false;
		}
		try{
			Integer.parseInt(buyNowPrice.getText());
		}catch(NumberFormatException e){
			setText("Please enter a valid buy now price.");
			return false;
		}
		if (auctionDuration.getText().length() == 0) {
			setText("Please enter the length of the auction(in days).");
			return false;
		}
		try{
			Integer.parseInt(auctionDuration.getText());
		}catch(NumberFormatException e){
			setText("Please enter a valid length of the auction(in days).");
			return false;
		}
		return true;
	}
}