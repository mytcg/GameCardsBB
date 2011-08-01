package net.mytcg.topcar.ui;

import net.mytcg.topcar.ui.custom.FixedButtonField;
import net.mytcg.topcar.ui.custom.ImageField;
import net.mytcg.topcar.util.Card;
import net.mytcg.topcar.util.Const;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;


public class RemoveCardFromDeckScreen extends AppScreen implements FieldChangeListener
{
	private FixedButtonField exit = new FixedButtonField(Const.back);
	private FixedButtonField flips = new FixedButtonField(Const.flip);
	private FixedButtonField remove = new FixedButtonField("Remove Card");
	
	private boolean flip = false;
	private Card card = null;
	private int deckid = -1;
	private ImageField image;
	
	public RemoveCardFromDeckScreen(Card card, int deckid) {
		super(true);
		this.card = card;
		this.deckid = deckid;
		
		bgManager.setStatusHeight(exit.getContentHeight());
		exit.setChangeListener(this);
		flips.setChangeListener(this);
		remove.setChangeListener(this);
		
		image = new ImageField(card.getFronturl());
		
		add(image);
		
		addButton(remove);
		addButton(flips);
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
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		} else if (f == remove) {
			doConnect(Const.removefromdeck+Const.card_id+card.getId()+Const.deck_id+deckid);
		} else if ((f == flips)) {
			flip = !flip;
			if (flip) {
				image.setUrl(card.getBackurl());
			} else {
				image.setUrl(card.getFronturl());
			}
		}
	}
}