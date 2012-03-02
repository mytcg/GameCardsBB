package net.mytcg.dex.ui;

import net.mytcg.dex.ui.custom.FixedButtonField;
import net.mytcg.dex.ui.custom.ImageField;
import net.mytcg.dex.ui.custom.StatField;
import net.mytcg.dex.util.Card;
import net.mytcg.dex.util.Const;
import net.rim.blackberry.api.browser.Browser;
import net.rim.blackberry.api.browser.BrowserSession;
import net.rim.blackberry.api.invoke.Invoke;
import net.rim.blackberry.api.invoke.MessageArguments;
import net.rim.blackberry.api.invoke.PhoneArguments;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

import java.util.Vector;
import net.mytcg.dex.util.Stat;

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
	
	public ImageScreen(Card card, boolean confirm, AppScreen screen) {
		this(card, screen);
		this.confirm = confirm;
		if (confirm) {
			exit.setLabel(Const.accept);
			option.setLabel(Const.reject);
		}
		
	}
	
	public ImageScreen(Card card, AppScreen screen) {
		super(screen, true, card);
		this.card = card;
		System.out.println("card.getCardOrientation() "+card.getCardOrientation());
		//bgManager.setStatusHeight(exit.getContentHeight());
		exit.setChangeListener(this);
		flips.setChangeListener(this);
		option.setChangeListener(this);
		
		//image = new ImageField(card.getFronturl());
		//image.setChangeListener(this);
		
		if(!(Const.getPortrait())){
			if(card.getCardOrientation()==2){
				vStatManager.setStatusHeight(exit.getContentHeight());
				vStatManager.setUrl(card.getFronturl());
				System.out.println("setting vStatManager url");
			}else if(card.getCardOrientation()==1){
				hStatManager.setStatusHeight(exit.getContentHeight());
				hStatManager.setUrl(card.getFronturl());
				System.out.println("setting hStatManager url");
			}
		}else{
			if(card.getCardOrientation()==2){
				hStatManager.setStatusHeight(exit.getContentHeight());
				hStatManager.setUrl(card.getFronturl());
				System.out.println("setting hStatManager url");
			}else if(card.getCardOrientation()==1){
				vStatManager.setStatusHeight(exit.getContentHeight());
				vStatManager.setUrl(card.getFronturl());
				System.out.println("setting vStatManager url");
			}
		}
		cardStats = card.getStats();
		if (cardStats != null) {
			stats = new StatField [cardStats.size()];
			for(int i = 0; i < cardStats.size(); i++){
				if(!(Const.getPortrait())){
					if(card.getCardOrientation()==2){
						stats[i] = new StatField ((Stat)cardStats.elementAt(i), vStatManager.image);
					}else if(card.getCardOrientation()==1){
						stats[i] = new StatField ((Stat)cardStats.elementAt(i), hStatManager.image);
					}
				}else{
					if(card.getCardOrientation()==2){
						stats[i] = new StatField ((Stat)cardStats.elementAt(i), hStatManager.image);
					}else if(card.getCardOrientation()==1){
						stats[i] = new StatField ((Stat)cardStats.elementAt(i), vStatManager.image);
					}
				}
				stats[i].setChangeListener(this);
			}
			if((Const.getPortrait())){
				if(card.getCardOrientation()==2){
					for(int i = cardStats.size()-1; i >=0; i--){
						if(stats[i].stat.getWidth()!=0&&stats[i].stat.getFrontOrBack()==1){
							addStat(stats[i]);
						}
					}
				}else{
					for(int i = 0; i < cardStats.size(); i++){
						if(stats[i].stat.getWidth()!=0&&stats[i].stat.getFrontOrBack()==1){
							addStat(stats[i]);
						}
					}
				}
			}else{
				for(int i = 0; i < cardStats.size(); i++){
					if(stats[i].stat.getWidth()!=0&&stats[i].stat.getFrontOrBack()==1){
						addStat(stats[i]);
					}
				}
			}
		} else {
			cardStats = new Vector();
		}
		//addStat(new NullField());
		if (!(card.getFronturl().equals("createacard"))) {
			addButton(option);
			addButton(flips);
		}
		addButton(exit);
		
	}
	
	public void process(String val) {
		synchronized(UiApplication.getEventLock()) {
			screen = null;
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
				screen = new OptionScreen(card, this);
				UiApplication.getUiApplication().pushScreen(screen);
			}
		} else if ((f == flips)) {
			removeStats();
			flip = !flip;
			for(int j = 0; j < stats.length; j++){
				if(flip){
					stats[j].flip = 2;
				}else{
					stats[j].flip = 1;
				}
			}
			if((Const.getPortrait())){
				if(card.getCardOrientation()==2){
					for(int k = cardStats.size()-1; k >=0; k--){
						if(stats[k].flip == stats[k].stat.getFrontOrBack()){
							addStat(stats[k]);
						}
					}
				}else{
					for(int k = 0; k < cardStats.size(); k++){
						if(stats[k].flip == stats[k].stat.getFrontOrBack()){
							addStat(stats[k]);
						}
					}
				}
			}else{
				for(int k = 0; k < cardStats.size(); k++){
					if(stats[k].flip == stats[k].stat.getFrontOrBack()){
						addStat(stats[k]);
					}
				}
			}
			if (flip) {
				if(!(Const.getPortrait())){
					if(card.getCardOrientation()==2){
						vStatManager.setUrl(card.getBackurl());
						vStatManager.invalidate();
					}else if(card.getCardOrientation()==1){
						hStatManager.setUrl(card.getBackurl());
						hStatManager.invalidate();
					}
				}else{
					if(card.getCardOrientation()==2){
						hStatManager.setUrl(card.getBackurl());
						hStatManager.invalidate();
					}else if(card.getCardOrientation()==1){
						vStatManager.setUrl(card.getBackurl());
						vStatManager.invalidate();
					}
				}
			} else {
				if(!(Const.getPortrait())){
					if(card.getCardOrientation()==2){
						vStatManager.setUrl(card.getFronturl());
						vStatManager.invalidate();
					}else if(card.getCardOrientation()==1){
						hStatManager.setUrl(card.getFronturl());
						hStatManager.invalidate();
					}
				}else{
					if(card.getCardOrientation()==2){
						hStatManager.setUrl(card.getFronturl());
						hStatManager.invalidate();
					}else if(card.getCardOrientation()==1){
						vStatManager.setUrl(card.getFronturl());
						vStatManager.invalidate();
					}
				}
			}
		}else{
			for(int j = 0; j < cardStats.size(); j++){
				Stat temp = (Stat)cardStats.elementAt(j);
				if((temp.getFrontOrBack()==1&&!flip)||(temp.getFrontOrBack()==2&&flip)){
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