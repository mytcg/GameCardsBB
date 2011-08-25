package net.mytcg.dev.ui;

import net.mytcg.dev.ui.custom.ColorLabelField;
import net.mytcg.dev.ui.custom.FixedButtonField;
import net.mytcg.dev.ui.custom.SexyEditField;
import net.mytcg.dev.util.Const;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class RedeemScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	FixedButtonField redeem = new FixedButtonField(Const.redeem);
	SexyEditField number = new SexyEditField("");
	ColorLabelField lbl = new ColorLabelField(Const.code);
	
	public RedeemScreen(AppScreen screen)
	{
		super(screen);
		
		add(lbl);
		add(number);
		
		bgManager.setStatusHeight(exit.getContentHeight());
		
		exit.setChangeListener(this);
		redeem.setChangeListener(this);
		
		addButton(redeem);
		addButton(new FixedButtonField(""));
		addButton(exit);
	}
/*	public void process(String val) {
		synchronized(UiApplication.getEventLock()) {
			screen = new AlbumListScreen(val);
			UiApplication.getUiApplication().pushScreen(screen);
		}
	}*/
	
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		} else if (f == redeem) {
			doConnect(Const.rdm+number.getText());
		}
	}
}