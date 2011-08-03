package net.mytcg.dev.ui;

import net.mytcg.dev.ui.custom.FixedButtonField;
import net.mytcg.dev.ui.custom.ListItemField;
import net.mytcg.dev.util.Card;
import net.mytcg.dev.util.Const;
import net.mytcg.dev.util.SettingsBean;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class OptionScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	
	ListItemField tmp = new ListItemField("", -1, false, 0);
	
	Card card = null;
	Bitmap cardthumb;
	
	public void process(String val) {
		
	}
	
	private ListItemField notes = new ListItemField("Add Notes", 1, false, 0);
	private ListItemField share = new ListItemField("Share Card", 2, false, 0);
	private ListItemField auction = new ListItemField("Auction Card", 3, false, 0);
	private ListItemField compare = new ListItemField("Compare With", 4, false, 0);
	private ListItemField details = new ListItemField("Card Details", 5, false, 0);
	//private ListItemField deck = new ListItemField("Add to Deck", 6, false, 0);
	//private ListItemField contact = new ListItemField("Contact", 5, false, 0);
	private ListItemField delete = new ListItemField("Delete Card", 7, false, 0);
	
	public OptionScreen(Card card, AppScreen screen, Bitmap cardthumb) {
		super(screen);
		
		this.card = card;
		this.cardthumb = cardthumb;
		
		bgManager.setStatusHeight(exit.getContentHeight());
		exit.setChangeListener(this);
		
		addButton(exit);
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		
		notes.setChangeListener(this);
		share.setChangeListener(this);
		auction.setChangeListener(this);
		compare.setChangeListener(this);
		details.setChangeListener(this);
		//deck.setChangeListener(this);
		//contact.setChangeListener(this);
		delete.setChangeListener(this);
		
		add(notes);
		add(share);
		add(auction);
		add(compare);
		add(details);
		//add(deck);
		//add(contact);
		add(delete);
		
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
		} else if (f == notes) {
			screen = new NoteScreen(card, this);
			UiApplication.getUiApplication().pushScreen(screen);
		} else if (f == share) {
			screen = new ShareScreen(card, this);
			UiApplication.getUiApplication().pushScreen(screen);
		} else if (f == auction) {
			screen = new AuctionCreateScreen(card, cardthumb);
			UiApplication.getUiApplication().pushScreen(screen);
		} else if (f == compare) {
			screen = new AlbumListScreen(card.getCategoryId(), 2, card);
			UiApplication.getUiApplication().pushScreen(screen);
		} else if (f == details) {
			screen = new CardDetailScreen(card);
			UiApplication.getUiApplication().pushScreen(screen);
		}/* else if (f == deck) {
			screen = new AddCardToDeckScreen(card, cardthumb);
			UiApplication.getUiApplication().pushScreen(screen);
		} /*else if (f == contact) {
			screen = new ContactScreen(card.getStats(), this);
			UiApplication.getUiApplication().pushScreen(screen);
		}*/ else if (f == delete) {
			screen = new DeleteScreen(card, this);
			UiApplication.getUiApplication().pushScreen(screen);
		}
	}
}