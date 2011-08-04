package net.mytcg.topcar.ui;

import net.mytcg.topcar.ui.custom.AuctionField;
import net.mytcg.topcar.ui.custom.ColorLabelField;
import net.mytcg.topcar.ui.custom.FixedButtonField;
import net.mytcg.topcar.ui.custom.SexyEditField;
import net.mytcg.topcar.ui.custom.ThumbnailField;
import net.mytcg.topcar.util.Auction;
import net.mytcg.topcar.util.Card;
import net.mytcg.topcar.util.Const;
import net.mytcg.topcar.util.SettingsBean;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.EditField;

public class AuctionCreateScreen extends AppScreen implements FieldChangeListener
{
	boolean created = false;
	boolean confirm = false;
	FixedButtonField exit = new FixedButtonField(Const.back);
	FixedButtonField empty = new FixedButtonField("");
	FixedButtonField auction = new FixedButtonField(Const.auction);
	SexyEditField openingBid = new SexyEditField("", EditField.FILTER_NUMERIC, 36);
	SexyEditField buyNowPrice = new SexyEditField("", EditField.FILTER_NUMERIC, 36);
	SexyEditField auctionDuration = new SexyEditField("", EditField.FILTER_NUMERIC, 36);
	ColorLabelField lblOpeningBid = new ColorLabelField(" Opening bid");
	ColorLabelField lblBuyNowPrice = new ColorLabelField(" Buy now price");
	ColorLabelField lblAuctionDuration = new ColorLabelField(" Auction duration(days)");
	ThumbnailField cardfield = null;

	Card card = null;
	
	public AuctionCreateScreen(Card card, Bitmap cardthumb)
	{
		super(null);
		this.card = card;
		SettingsBean _instance = SettingsBean.getSettings();
		_instance.created = false;
		SettingsBean.saveSettings(_instance);
		Font _font = getFont();
		_font = _font.derive(Const.TYPE, Const.FONT);
		setFont(_font);

		bgManager.setStatusHeight(exit.getContentHeight());
		
		exit.setChangeListener(this);
		auction.setChangeListener(this);
		
		//cardfield = new CardField(card, cardthumb);
		cardfield = new ThumbnailField(card);
		//add(cardfield);
		add(cardfield);
		add(lblOpeningBid);
		int val = 0;
		try {
			val = Integer.parseInt(card.getValue());
		} catch (Exception e) {
			val = 0;
		}
		openingBid.setText(""+val);
		buyNowPrice.setText(""+ (2*val));
		auctionDuration.setText("5");
		
		add(openingBid);
		add(lblBuyNowPrice);
		add(buyNowPrice);
		add(lblAuctionDuration);
		add(auctionDuration);

		addButton(auction);
		addButton(empty);
		addButton(exit);
		
		invalidate();
		setDisplaying(true);
	}

	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		} else if (f == auction && created == false) {
			if (!confirm) {
				if(valid()){
					created = false;
					SettingsBean _instance = SettingsBean.getSettings();
					_instance.created = false;
					SettingsBean.saveSettings(_instance);
					confirm = true;
					
					//when i auction
					/*
					 * created = true, instance.created = true
					 */
					synchronized(UiApplication.getEventLock()) {
						try{
							
						
							this.delete(cardfield);
							this.delete(openingBid);
							this.delete(buyNowPrice);
							this.delete(auctionDuration);
							
							lblBuyNowPrice.setText("");
							lblAuctionDuration.setText("");
							int val = 0;
							int buynow = 0;
							try {
								buynow = Integer.parseInt(buyNowPrice.getText());
							} catch (Exception e) {
								buynow = 0;
							}
							int bid = 0;
							try {
								bid = Integer.parseInt(openingBid.getText());
							} catch (Exception e) {
								bid = 0;
							}
							val = bid;
							if (buynow > bid) {
								val = buynow;
							}
							val = (int)((double)val/10);
							if (val < 5) {
								val = 5;
							}
							
							lblOpeningBid.setColor(Color.RED);
							lblOpeningBid.setText("Are you sure you want to auction "+card.getDesc()+"? It will cost you " + val + " credits.");
							//lblOpeningBid.setColor(Const.FONTCOLOR);
							
							add(new AuctionField(new Auction(-1, card.getId(), 1, 
									card.getDesc(), card.getThumburl(), openingBid.getText(), buyNowPrice.getText(), "", "You"
									, auctionDuration.getText() + " days left", ""), cardfield.getThumbnail()));
							
							//this.removeButton(auction);
							//addButton(new FixedButtonField(""));
						}catch(Exception e){
							
						}
					}
					//doConnect(Const.createauction+"&cardid="+card.getId()+"&bid="+openingBid.getText()+"&buynow="+buyNowPrice.getText()+"&days="+auctionDuration.getText());
				}
			} else {
				if(valid()){
					created = true;
					SettingsBean _instance = SettingsBean.getSettings();
					_instance.created = true;
					SettingsBean.saveSettings(_instance);
					
					synchronized(UiApplication.getEventLock()) {
						try{
							lblOpeningBid.setColor(Color.RED);
							lblOpeningBid.setText("Attempting to create auction...");
							
							auction.setChangeListener(null);
							auction.empty = true;
							auction.setLabel("");
							//removeButton(auction);
							
						}catch(Exception e){
							
						}
					}
					doConnect(Const.createauction+"&cardid="+card.getId()+"&bid="+openingBid.getText()+"&buynow="+buyNowPrice.getText()+"&days="+auctionDuration.getText());
				}
			}
		} 
	}
	
	public void process(String val) {
		int fromIndex = -1;
		if ((fromIndex = val.indexOf(Const.xml_success)) != -1) {
			if(val.substring(fromIndex+Const.xml_success_length, val.indexOf(Const.xml_success_end, fromIndex)).equals("1")){
				synchronized(UiApplication.getEventLock()) {
					try{
						lblBuyNowPrice.setText("");
						lblAuctionDuration.setText("");
						lblOpeningBid.setText("Auction successfully created!");
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}else{
				synchronized(UiApplication.getEventLock()) {
					try{
						lblBuyNowPrice.setText("");
						lblAuctionDuration.setText("");
						lblOpeningBid.setText("Error creating auction.");
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