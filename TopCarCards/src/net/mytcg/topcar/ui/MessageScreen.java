package net.mytcg.topcar.ui;

import net.mytcg.topcar.ui.custom.FixedButtonField;
import net.mytcg.topcar.ui.custom.NonEditableField;
import net.mytcg.topcar.util.Const;
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