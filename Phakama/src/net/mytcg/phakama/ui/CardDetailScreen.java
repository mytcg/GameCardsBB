package net.mytcg.phakama.ui;

import net.mytcg.phakama.ui.custom.ColorLabelField;
import net.mytcg.phakama.ui.custom.FixedButtonField;
import net.mytcg.phakama.ui.custom.ListItemField;
import net.mytcg.phakama.util.Card;
import net.mytcg.phakama.util.Const;
import net.mytcg.phakama.util.Stat;

import java.util.Vector;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.LabelField;

public class CardDetailScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	ListItemField tmp = new ListItemField("Empty", -1, false, 0);
	
	public CardDetailScreen(Card card) {
		super(null);
		bgManager.setStatusHeight(exit.getContentHeight());
		
		exit.setChangeListener(this);
		add(new ColorLabelField(card.getDesc()+" details",LabelField.FIELD_HCENTER , 18));
		Vector stats = card.getStats();
		for(int i = 0; i < stats.size();i++){
			Stat stat = (Stat)stats.elementAt(i);
			add(new ListItemField(stat.getDesc()+" : "+stat.getValue(),-1,true,0));
		}
		if(stats.size()== 0){
			add(new ListItemField("None",-1,true,0));
		}
		
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		addButton(exit);
		setDisplaying(true);
	}
	
	protected void onExposed() {
		super.onExposed();
	}
	public boolean onClose() {
		screen = null;
		UiApplication.getUiApplication().popScreen(this);
		return true;
	}
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		}
	}
}