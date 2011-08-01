package net.mytcg.topcar.ui;

import net.mytcg.topcar.ui.custom.CompareField;
import net.mytcg.topcar.ui.custom.FixedButtonField;
import net.mytcg.topcar.util.Card;
import net.mytcg.topcar.util.Const;
import net.mytcg.topcar.util.SettingsBean;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;


public class CompareScreen extends AppScreen implements FieldChangeListener
{
	private FixedButtonField exit = new FixedButtonField(Const.back);
	private FixedButtonField flips = new FixedButtonField(Const.flip);
	private FixedButtonField option = new FixedButtonField(Const.options);
	
	private CompareField image1 = null;
	private CompareField image2 = null;
	private boolean flip = false;
	private Card card1 = null;
	private Card card2 = null;
	
	public CompareScreen(Card card1, Card card2) {
		super(true);
		this.card1 = card1;
		this.card2 = card2;
		
		//bgManager.setStatusHeight(exit.getContentHeight());
		exit.setChangeListener(this);
		flips.setChangeListener(this);
		
		image1 = new CompareField(card1.getFronturl());
		image1.setChangeListener(this);
		
		image2 = new CompareField(card2.getFronturl());
		image2.setChangeListener(this);
		
		if(!(Const.getPortrait())){
			hbgManager.add(image1);
			hbgManager.add(image2);
		}else{
			bgManager.add(image1);
			bgManager.add(image2);
		}
		
		
		addButton(flips);
		addButton(new FixedButtonField(""));
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
	}
	
	public void fieldChanged(Field f, int i) {
		System.out.println("FIELD: "+f.toString()+ " i "+i);
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		} else if ((f == flips)) {
			flip = !flip;
			if (flip) {
				image1.setUrl(card1.getBackurl());
				image2.setUrl(card2.getBackurl());
				this.invalidate();
			} else {
				image1.setUrl(card1.getFronturl());
				image2.setUrl(card2.getFronturl());
				this.invalidate();
			}
		}
	}
}