package net.mytcg.dex.ui;

import net.mytcg.dex.ui.custom.FixedButtonField;
import net.mytcg.dex.ui.custom.NonEditableField;
import net.mytcg.dex.util.Card;
import net.mytcg.dex.util.Const;
import net.mytcg.dex.util.SettingsBean;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class DeleteScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField yes = new FixedButtonField(Const.yes);
	FixedButtonField no = new FixedButtonField(Const.no);
	NonEditableField note = new NonEditableField("");
	Card card = null;
	
	public void process(String val) {
		synchronized(UiApplication.getEventLock()) {
			SettingsBean _instance = SettingsBean.getSettings();
			_instance.deleted = true;
			SettingsBean.saveSettings(_instance);
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		}
	}
	
	public DeleteScreen(Card card, AppScreen screen)
	{
		super(screen);
		
		this.card = card;
		
		note.setText(Const.remove);
		add(note);
		
		bgManager.setStatusHeight(Const.getButtonHeight());
		
		yes.setChangeListener(this);
		no.setChangeListener(this);
		
		addButton(yes);
		addButton(new FixedButtonField(""));
		addButton(no);
		
	}
	
	public void fieldChanged(Field f, int i) {
		if (f == yes) {
			doConnect(Const.deletecard+card.getId());
		} else if (f == no) {
			UiApplication.getUiApplication().popScreen(this);
		}
	}
}