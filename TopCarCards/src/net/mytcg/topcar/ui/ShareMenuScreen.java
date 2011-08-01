package net.mytcg.topcar.ui;

import net.mytcg.topcar.ui.custom.FixedButtonField;
import net.mytcg.topcar.ui.custom.ListItemField;
import net.mytcg.topcar.util.Card;
import net.mytcg.topcar.util.Const;
import net.mytcg.topcar.util.SettingsBean;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class ShareMenuScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	
	ListItemField sendauction = new ListItemField("Empty", -1, false, 0);
	ListItemField sendfriend = new ListItemField("Empty", -1, false, 0);
	Card card = null;
	Bitmap cardthumb = null;

	public ShareMenuScreen(Card card, Bitmap cardthumb)
	{
		super(null);
		this.card = card;
		this.cardthumb = cardthumb;
		bgManager.setStatusHeight(exit.getContentHeight());
		
		sendauction = new ListItemField("Send card to auction", 0, false, 0);
		sendfriend = new ListItemField("Send card to friend", 0, false, 0);
		
		exit.setChangeListener(this); 
		sendauction.setChangeListener(this);
		sendfriend.setChangeListener(this);
		
		add(sendauction);
		add(sendfriend);
		
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		addButton(exit);
	}
	protected void onExposed() {
		if(SettingsBean.getSettings().created){
			UiApplication.getUiApplication().popScreen(this);
		}
	}
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		} else if(f == sendauction){
			screen = new AuctionCreateScreen(card, cardthumb);
			UiApplication.getUiApplication().pushScreen(screen);
		} else if(f == sendfriend){
			screen = new ShareScreen(card, this);
			UiApplication.getUiApplication().pushScreen(screen);
		} 
	}
}