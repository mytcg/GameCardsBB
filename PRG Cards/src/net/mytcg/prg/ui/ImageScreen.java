package net.mytcg.prg.ui;

import net.mytcg.prg.custom.FixedButtonField;
import net.mytcg.prg.custom.ImageField;
import net.mytcg.prg.util.Card;
import net.mytcg.prg.util.Const;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class ImageScreen extends AppScreen implements FieldChangeListener
{
	private FixedButtonField exit = new FixedButtonField(Const.back);
	private FixedButtonField flips = new FixedButtonField(Const.flip);
	
	private ImageField image = null;
	private boolean flip = false;
	private Card card = null;
	private boolean confirm = false;
	
	public ImageScreen(Card card, boolean confirm, AppScreen screen) {
		this(card, screen);
		this.confirm = confirm;
		if (confirm) {
			exit.setLabel(Const.accept);
		}
	}
	
	public ImageScreen(Card card, AppScreen screen) {
		super(screen, true);
		this.card = card;
		
		bgManager.setStatusHeight(exit.getContentHeight());
		exit.setChangeListener(this);
		flips.setChangeListener(this);
		
		image = new ImageField(card.getFronturl());
		image.setChangeListener(this);
		
		add(image);
		
		addButton(exit);
		addButton(flips);
		addButton(new FixedButtonField(""));
	}
	
	public void process(String val) {
		synchronized(UiApplication.getEventLock()) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		}
	}
	
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			if (confirm) {
				doConnect(Const.savecard+card.getId());
			} else {
				screen = null;
				UiApplication.getUiApplication().popScreen(this);
			}
		/*} else if (f == option) {
			if (confirm) {
				doConnect(Const.rejectcard+card.getId());
			} else {
				screen = new OptionScreen(card, this);
				UiApplication.getUiApplication().pushScreen(screen);
			}*/
		} else if ((f == image)||(f == flips)) {
			flip = !flip;
			if (flip) {
				image.setUrl(card.getBackurl());
			} else {
				image.setUrl(card.getFronturl());
			}
		}
	}
}