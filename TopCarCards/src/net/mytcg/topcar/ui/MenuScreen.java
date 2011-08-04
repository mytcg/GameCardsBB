package net.mytcg.topcar.ui;

import net.mytcg.topcar.ui.custom.FixedButtonField;
import net.mytcg.topcar.ui.custom.ListItemField;
import net.mytcg.topcar.util.Const;
import net.mytcg.topcar.util.SettingsBean;
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
	ListItemField notifications = new ListItemField("Empty", -1, false, 0);
	ListItemField rankings = new ListItemField("Empty", -1, false, 0);
	ListItemField friendranks = new ListItemField("Empty", -1, false, 0);
	ListItemField friends = new ListItemField("Empty", -1, false, 0);
	ListItemField invitefriend = new ListItemField("Empty", -1, false, 0);
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
		notifications = new ListItemField(Const.notification, Const.NOTIFICATIONS, false, 0);
		rankings = new ListItemField(Const.rankings, Const.RANKINGS, false, 0);
		friendranks = new ListItemField(Const.friendranks, Const.RANKINGS, false, 0);
		friends = new ListItemField(Const.friend, Const.FRIENDS, false, 0);
		invitefriend  = new ListItemField(Const.invitefriend, Const.FRIENDS, false, 0);
		logout = new ListItemField(Const.logOut, Const.LOGOUT, false, 0);
		
		
		
		albums.setChangeListener(this);
		play.setChangeListener(this);
		decks.setChangeListener(this);
		shop.setChangeListener(this);
		auctions.setChangeListener(this);
		redeem.setChangeListener(this);
		balance.setChangeListener(this);
		profile.setChangeListener(this);
		notifications.setChangeListener(this);
		rankings.setChangeListener(this);
		friendranks.setChangeListener(this);
		friends.setChangeListener(this);
		invitefriend.setChangeListener(this);
		logout.setChangeListener(this);
		exit.setChangeListener(this);
		
		add(albums);
		add(play);
		//add(decks);
		add(shop);
		add(auctions);
		add(balance);
		add(profile);
		add(notifications);
		add(rankings);
		add(friendranks);
		add(friends);
		add(invitefriend);
		add(redeem);
		add(logout);
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		addButton(exit);
	}
	
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			SettingsBean _instance = SettingsBean.getSettings();
			_instance.lastloaded();
			SettingsBean.saveSettings(_instance);
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
		} else if(f == notifications){
			screen = new DetailScreen(this, Const.NOTIFICATIONSCREEN);
			UiApplication.getUiApplication().pushScreen(screen);
		} else if(f == rankings){
			screen = new RankingsCategoriesScreen(false);
			UiApplication.getUiApplication().pushScreen(screen);
		} else if(f == friendranks){
			screen = new RankingsCategoriesScreen(true);
			UiApplication.getUiApplication().pushScreen(screen);
		} else if(f == friends){
			screen = new DetailScreen(this, Const.FRIENDSSCREEN);
			UiApplication.getUiApplication().pushScreen(screen);
		} else if(f == invitefriend){
			screen = new InviteFriendScreen();
			UiApplication.getUiApplication().pushScreen(screen);
		} else if(f == logout){
			close();
			Const.GOTOSCREEN = Const.LOGINSCREEN;
			Const.FROMSCREEN = Const.LOGINSCREEN;
			Const.app.previousScreen();
			System.exit(0);
			/*screen = new LogOutScreen();
			UiApplication.getUiApplication().pushScreen(screen);*/
		}
	}
}