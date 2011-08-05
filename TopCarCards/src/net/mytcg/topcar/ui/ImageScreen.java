package net.mytcg.topcar.ui;

import net.rim.blackberry.api.browser.Browser;
import net.rim.blackberry.api.browser.BrowserSession;
import net.rim.blackberry.api.invoke.Invoke;
import net.rim.blackberry.api.invoke.MessageArguments;
import net.rim.blackberry.api.invoke.PhoneArguments;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

import java.util.Vector;

import net.mytcg.topcar.ui.custom.FixedButtonField;
import net.mytcg.topcar.ui.custom.ImageField;
import net.mytcg.topcar.ui.custom.StatField;
import net.mytcg.topcar.util.Card;
import net.mytcg.topcar.util.Const;
import net.mytcg.topcar.util.SettingsBean;
import net.mytcg.topcar.util.Stat;

public class ImageScreen extends AppScreen implements FieldChangeListener
{
	private FixedButtonField exit = new FixedButtonField(Const.back);
	private FixedButtonField flips = new FixedButtonField(Const.flip);
	private FixedButtonField option = new FixedButtonField(Const.options);
	
	private ImageField image = null;
	private StatField [] stats;
	private Vector cardStats = new Vector();
	private boolean flip = false;
	private Card card = null;
	private boolean confirm = false;
	private Bitmap cardthumb = null;
	
	public ImageScreen(Card card, boolean confirm, AppScreen screen, Bitmap cardthumb) {
		this(card, screen, cardthumb);
		this.confirm = confirm;
		if (confirm) {
			exit.setLabel(Const.accept);
			option.setLabel(Const.reject);
		}
		
	}
	
	public ImageScreen(Card card, AppScreen screen, Bitmap cardthumb) {
		super(screen, true);
		this.card = card;
		this.cardthumb = cardthumb;
		
		//bgManager.setStatusHeight(exit.getContentHeight());
		exit.setChangeListener(this);
		flips.setChangeListener(this);
		option.setChangeListener(this);
		
		//image = new ImageField(card.getFronturl());
		//image.setChangeListener(this);
		
		if(!(Const.getPortrait())){
			hStatManager.setStatusHeight(exit.getContentHeight());
			hStatManager.setUrl(card.getFronturl());
			System.out.println("setting hStatManager url");
		}else{
			vStatManager.setStatusHeight(exit.getContentHeight());
			vStatManager.setUrl(card.getFronturl());
			System.out.println("setting vStatManager url");
		}
		
		cardStats = card.getStats();
		if (cardStats != null) {
			stats = new StatField [cardStats.size()];
			for(int i = 0; i < cardStats.size(); i++){
				if(!(Const.getPortrait())){
					stats[i] = new StatField ((Stat)cardStats.elementAt(i), hStatManager.image);
				}else{
					stats[i] = new StatField ((Stat)cardStats.elementAt(i), vStatManager.image);
				}
				
				stats[i].setChangeListener(this);
				//addStat(stats[i]);
			}
		}
		//addStat(new NullField());
		
		if (cardthumb != null) {
			addButton(option);
		} else {
			addButton(new FixedButtonField(""));
		}
		addButton(flips);
		addButton(exit);
	}
	
	public void process(String val) {
		synchronized(UiApplication.getEventLock()) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		}
	}
	
	protected void onExposed() {
		if(SettingsBean.getSettings().created){
			UiApplication.getUiApplication().popScreen(this);
		}
		if(SettingsBean.getSettings().shared){
			SettingsBean _instance = SettingsBean.getSettings();
			_instance.shared = false;
			SettingsBean.saveSettings(_instance);
			UiApplication.getUiApplication().popScreen(this);
		}
	}
	
	public void fieldChanged(Field f, int i) {
		System.out.println("FIELD: "+f.toString()+ " i "+i);
		if (f == exit) {
			if (confirm) {
				doConnect(Const.savecard+card.getId());
			} else {
				screen = null;
				UiApplication.getUiApplication().popScreen(this);
			}
		} else if (f == option) {
			if (confirm) {
				doConnect(Const.rejectcard+card.getId());
			} else {
				screen = new OptionScreen(card, this, cardthumb);
				UiApplication.getUiApplication().pushScreen(screen);
			}
		} else if ((f == flips)) {
			flip = !flip;
			if (stats != null) {
				for(int j = 0; j < stats.length; j++){
					if(flip){
						stats[j].flip = 1;
					}else{
						stats[j].flip = 0;
					}
				}
			}
			if (flip) {
				if(!(Const.getPortrait())){
					hStatManager.setUrl(card.getBackurl());
					hStatManager.invalidate();
				}else{
					vStatManager.setUrl(card.getBackurl());
					vStatManager.invalidate();
				}
			} else {
				if(!(Const.getPortrait())){
					hStatManager.setUrl(card.getFronturl());
					hStatManager.invalidate();
				}else{
					vStatManager.setUrl(card.getFronturl());
					vStatManager.invalidate();
				}
			}
		}else{
			if (cardStats != null) {
				for(int j = 0; j < cardStats.size(); j++){
					Stat temp = (Stat)cardStats.elementAt(j);
					if((temp.getFrontOrBack()==0&&!flip)||(temp.getFrontOrBack()==1&&flip)){
						if(f == stats[j]){
							
							if (temp.getDesc().equals(Const.web)) {
								BrowserSession browserSession = Browser.getDefaultSession();
								browserSession.displayPage(temp.getValue());
								browserSession.showBrowser();
							} else if (temp.getDesc().equals(Const.phone)) {
								PhoneArguments phoneArguments = new PhoneArguments(
						                PhoneArguments.ARG_CALL, temp.getValue());
						        Invoke.invokeApplication(Invoke.APP_TYPE_PHONE, phoneArguments);
							} else if (temp.getDesc().startsWith(Const.eml)) {
								String subject = "";
								Invoke.invokeApplication( Invoke.APP_TYPE_MESSAGES, new MessageArguments(MessageArguments.ARG_NEW,temp.getValue(),subject,Const.sig));
							}
						}
					}
				}
			}
		}
	}
}