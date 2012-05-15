package net.mytcg.dex.ui;

import net.mytcg.dex.ui.custom.FixedButtonField;
import net.mytcg.dex.ui.custom.ListItemField;
import net.mytcg.dex.util.Card;
import net.mytcg.dex.util.Const;
import net.mytcg.dex.util.SettingsBean;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class OptionScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	
	ListItemField tmp = new ListItemField("", -1, false, 0);
	
	Card card = null;
	
	public void process(String val) {
		
	}
	
	private ListItemField addalbum = new ListItemField("Add to Album", 1, false, 0);
	private ListItemField notes = new ListItemField("Notes", 2, false, 0);
	private ListItemField share = new ListItemField("Share", 3, false, 0);
	private ListItemField contact = new ListItemField("Contact", 4, false, 0);
	private ListItemField delete = new ListItemField("Delete", 5, false, 0);
	
	public OptionScreen(Card card, AppScreen screen) {
		super(screen);
		
		this.card = card;
		
		bgManager.setStatusHeight(exit.getContentHeight());
		exit.setChangeListener(this);
		
		
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		addButton(exit);
		
		addalbum.setChangeListener(this);
		notes.setChangeListener(this);
		share.setChangeListener(this);
		contact.setChangeListener(this);
		delete.setChangeListener(this);
		
		add(addalbum);
		add(notes);
		add(share);
		add(contact);
		add(delete);
		
	}
	protected void onExposed() {
		SettingsBean _instance = SettingsBean.getSettings();
		if (Const.added == 1) {
			Const.added = 0;
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		}
		if(_instance.deleted){
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		}
	}
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		} else if (f == addalbum) {
			screen = new DeckListScreen(card.getId());
			UiApplication.getUiApplication().pushScreen(screen);
		} else if (f == notes) {
			screen = new NoteScreen(card, this);
			UiApplication.getUiApplication().pushScreen(screen);
		} else if (f == share) {
			screen = new ShareScreen(card, this);
			UiApplication.getUiApplication().pushScreen(screen);
		} else if (f == contact) {
			screen = new ContactScreen(card.getStats(), this);
			UiApplication.getUiApplication().pushScreen(screen);
		} else if (f == delete) {
			screen = new DeleteScreen(card, this);
			UiApplication.getUiApplication().pushScreen(screen);
		}
	}
}