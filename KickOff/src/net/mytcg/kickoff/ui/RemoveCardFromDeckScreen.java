package net.mytcg.kickoff.ui;

import net.mytcg.kickoff.ui.custom.FixedButtonField;
import net.mytcg.kickoff.ui.custom.ImageField;
import net.mytcg.kickoff.util.Card;
import net.mytcg.kickoff.util.Const;
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
	
	public RemoveCardFromDeckScreen(AppScreen screen, Card card, int deckid) {
		super(screen, true);
		this.card = card;
		this.deckid = deckid;
		
		exit.setChangeListener(this);
		flips.setChangeListener(this);
		remove.setChangeListener(this);
		
		if(!(Const.getPortrait())){
			hStatManager.setStatusHeight(exit.getContentHeight());
			hStatManager.setUrl(card.getFronturl());
		}else{
			vStatManager.setStatusHeight(exit.getContentHeight());
			vStatManager.setUrl(card.getFronturl());
		}
		
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
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		} else if (f == remove) {
			doConnect(Const.removefromdeck+Const.card_id+card.getId()+Const.deck_id+deckid);
		} else if ((f == flips)) {
			flip = !flip;
			if (flip) {
				if(!(Const.getPortrait())){
					hStatManager.removeProgressBar();
					hStatManager.setUrl(card.getBackurl());
					hStatManager.invalidate();
				}else{
					vStatManager.removeProgressBar();
					vStatManager.setUrl(card.getBackurl());
					vStatManager.invalidate();
				}
			} else {
				if(!(Const.getPortrait())){
					hStatManager.removeProgressBar();
					hStatManager.setUrl(card.getFronturl());
					hStatManager.invalidate();
				}else{
					vStatManager.removeProgressBar();
					vStatManager.setUrl(card.getFronturl());
					vStatManager.invalidate();
				}
			}
		}
	}
}