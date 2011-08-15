package net.mytcg.sport.ui;

import net.mytcg.sport.ui.custom.FixedButtonField;
import net.mytcg.sport.ui.custom.NonEditableField;
import net.mytcg.sport.util.Card;
import net.mytcg.sport.util.Const;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class DeleteScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField yes = new FixedButtonField(Const.yes);
	FixedButtonField no = new FixedButtonField(Const.no);
	NonEditableField note = new NonEditableField("");
	Card card = null;
	
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
			doConnect(Const.savecard+card.getId());
		} else if (f == no) {
			UiApplication.getUiApplication().popScreen(this);
		}
	}
}