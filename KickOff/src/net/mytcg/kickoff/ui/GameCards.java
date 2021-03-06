package net.mytcg.kickoff.ui;

import net.mytcg.kickoff.http.ConnectionGet;
import net.mytcg.kickoff.util.Const;
import net.mytcg.kickoff.util.SettingsBean;
import net.rim.device.api.ui.UiApplication;

public final class GameCards extends UiApplication {
	
	private static AppScreen screen;
	
	public GameCards() {
		Const.getSettings();
		if (SettingsBean.getSettings().getAuthenticated()) {
			//update();
			screen = new MenuScreen();
		} else {
			screen = new LoginRegisterScreen();
		}
		pushScreen(screen);
		Const.app = this;
	}
	public void process(String val) {
		synchronized(UiApplication.getEventLock()) {
			pushScreen(new DownloadScreen(val, screen));
		}
	}
	public void nextScreen() {
		try {
			popScreen(screen);
		} catch (Exception e) {}
		switch (Const.GOTOSCREEN) {
			case Const.MENUSCREEN:
				//update();
				screen = new MenuScreen();
				break;
			case Const.LOGINSCREEN:
				screen = new LoginScreen();
				break;
			case Const.REGISTERSCREEN:
				screen = new RegistrationScreen();
				break;
			case Const.SHOP:
				screen = new ShopCategoriesScreen(true);
				break;
		}
		pushScreen(screen);
	}
	public void update() {
		//String url = SettingsBean.getSettings().getUrl()+Const.update+Const.VERSION+Const.getMSISDN()+Const.getIMSI()+Const.getIMEI()+Const.getOS()+Const.getMake()+Const.getModel()+Const.getOSVer()+Const.getTouch()+Const.width+Const.getWidth()+Const.height+Const.getHeight();
		//url = Const.removeSpaces(url);
		//ConnectionGet cG = new ConnectionGet(url, this);
		//cG.start();
	}
	public void previousScreen() {
		try {
			popScreen(screen);
		} catch (Exception e) {}
		switch (Const.FROMSCREEN) {
			case Const.LOGINSCREEN:
				screen = new LoginScreen();
				break;
			case Const.MENUSCREEN:
				screen = new MenuScreen();
				break;
		}
		pushScreen(screen);
	}

	public static void main(String[] args) {
		try {
			GameCards app = new GameCards();
			app.enterEventDispatcher();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}