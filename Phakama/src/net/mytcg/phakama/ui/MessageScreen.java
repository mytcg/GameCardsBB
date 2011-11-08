package net.mytcg.phakama.ui;

import net.mytcg.phakama.ui.custom.FixedButtonField;
import net.mytcg.phakama.ui.custom.NonEditableField;
import net.mytcg.phakama.util.Const;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;

public class MessageScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField conti = new FixedButtonField(Const.conti);
	NonEditableField note = new NonEditableField("");
	
	public void process(String val) {
		
	}
	
	public MessageScreen(String message, AppScreen screen)
	{
		super(screen);
		note.setText(message);
		add(note);
		
		bgManager.setStatusHeight(Const.getButtonHeight());
		
		conti.setChangeListener(this);
		
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		addButton(conti);
	}
	public void fieldChanged(Field f, int i) {
		if (f == conti) {
			pop();
		}
	}
}