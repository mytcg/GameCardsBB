package net.mytcg.dex.ui;

import net.mytcg.dex.ui.custom.FixedButtonField;
import net.mytcg.dex.ui.custom.ListItemField;
import net.mytcg.dex.util.Const;
import net.mytcg.dex.util.Stat;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class NumberScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	
	ListItemField tmp = new ListItemField("Empty", -1, false, 0);
	Stat stt = null;
	private String number = "";
	
	public void process(String val) {
		
	}
	
	public NumberScreen(String[] stats, AppScreen screen) {
		super(screen);
		bgManager.setStatusHeight(exit.getContentHeight());
		exit.setChangeListener(this);
		
		addButton(exit);
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		
		stats = format(stats);
		
		for (int i = 0; i < stats.length; i++) {
			tmp = new ListItemField(stats[i], i, false, 0);
			tmp.setChangeListener(this);
			add(tmp);
		}
		if (stats.length == 0) {
			add(tmp);
		}
		
	}
	private String[] format(String[] sznumbers) {
		   String tmpnum;
		   for (int i = 0; i < sznumbers.length; i++) {
			   tmpnum = "";
			   for (int x = 0; x < sznumbers[i].length(); x++) {
				   if (!((sznumbers[i].charAt(x) == '*')||(sznumbers[i].charAt(x) == '#'))) {
					   tmpnum += sznumbers[i].charAt(x);
				   }
			   }
			   sznumbers[i] = tmpnum;
		   }
		   return sznumbers;
	   }
	public String getResponse() {
		return number;
	}
	protected void onExposed() {
		if (!isVisible()) {
			
		}
		super.onExposed();
	}
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			UiApplication.getUiApplication().popScreen(this);
		} else {
			number = ((ListItemField)f).getLabel();
			UiApplication.getUiApplication().popScreen(this);
		}
	}
}