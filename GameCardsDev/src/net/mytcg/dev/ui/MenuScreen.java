package net.mytcg.dev.ui;

import net.mytcg.dev.ui.custom.FixedButtonField;
import net.mytcg.dev.ui.custom.ListItemField;
import net.mytcg.dev.util.Const;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class MenuScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	
	ListItemField albums = new ListItemField("Empty", -1, false, 0);
	ListItemField play = new ListItemField("Empty", -1, false, 0);
	ListItemField decks = new ListItemField("Empty", -1, false, 0);
	ListItemField shop = new ListItemField("Empty", -1, false, 0);
	ListItemField auctions = new ListItemField("Empty", -1, false, 0);
	ListItemField redeem = new ListItemField("Empty", -1, false, 0);
	ListItemField balance = new ListItemField("Empty", -1, false, 0);
	ListItemField profile = new ListItemField("Empty", -1, false, 0);
	ListItemField logout = new ListItemField("Empty", -1, false, 0);
	public MenuScreen()
	{
		super(null);
		bgManager.setStatusHeight(exit.getContentHeight());
		if (screen == null) {
			exit.setLabel(Const.exit);
		}
		albums = new ListItemField(Const.albums, Const.ALBUMS, false, 0);
		play = new ListItemField(Const.play, Const.PLAY, false, 0);
		decks = new ListItemField(Const.decks, Const.DECKS, false, 0);
		shop = new ListItemField(Const.shop, Const.SHOP, false, 0);
		auctions = new ListItemField(Const.auctions, Const.AUCTIONS, false, 0);
		redeem = new ListItemField(Const.redeem, Const.REDEEM, false, 0);
		balance = new ListItemField(Const.balance, Const.BALANCE, false, 0);
		profile = new ListItemField(Const.profile, Const.PROFILE, false, 0);
		logout = new ListItemField(Const.logOut, Const.LOGOUT, false, 0);
		
		
		
		albums.setChangeListener(this);
		play.setChangeListener(this);
		decks.setChangeListener(this);
		shop.setChangeListener(this);
		auctions.setChangeListener(this);
		redeem.setChangeListener(this);
		balance.setChangeListener(this);
		profile.setChangeListener(this);
		logout.setChangeListener(this);
		exit.setChangeListener(this);
		
		add(albums);
		add(play);
		add(decks);
		add(shop);
		add(auctions);
		add(redeem);
		add(balance);
		add(profile);
		add(logout);
		addButton(exit);
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
	}
	
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			System.exit(0);
		} else if(f == albums){
			screen = new AlbumScreen(0);
			UiApplication.getUiApplication().pushScreen(screen);
		} else if(f == play){
			screen = new NewGameScreen();
			UiApplication.getUiApplication().pushScreen(screen);
		} else if(f == decks){
			screen = new DecksScreen();
			UiApplication.getUiApplication().pushScreen(screen);
		} else if(f == shop){
			screen = new ShopCategoriesScreen();
			UiApplication.getUiApplication().pushScreen(screen);
		} else if(f == auctions){
			screen = new AuctionMenuScreen();
			UiApplication.getUiApplication().pushScreen(screen);
		} else if(f == redeem){
			screen = new RedeemScreen(this);
			UiApplication.getUiApplication().pushScreen(screen);
		} else if(f == balance){
			screen = new DetailScreen(this, Const.BALANCESCREEN);
			UiApplication.getUiApplication().pushScreen(screen);
		} else if(f == profile){
			screen = new DetailScreen(this, Const.PROFILESCREEN);
			UiApplication.getUiApplication().pushScreen(screen);
		} else if(f == logout){
			screen = new LogOutScreen();
			UiApplication.getUiApplication().pushScreen(screen);
		}
	}
}