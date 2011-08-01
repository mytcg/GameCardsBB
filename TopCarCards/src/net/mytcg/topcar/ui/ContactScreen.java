package net.mytcg.topcar.ui;

import java.util.Vector;

import net.mytcg.topcar.ui.custom.FixedButtonField;
import net.mytcg.topcar.ui.custom.ListItemField;
import net.mytcg.topcar.util.Const;
import net.mytcg.topcar.util.Stat;
import net.rim.blackberry.api.browser.Browser;
import net.rim.blackberry.api.browser.BrowserSession;
import net.rim.blackberry.api.invoke.Invoke;
import net.rim.blackberry.api.invoke.MessageArguments;
import net.rim.blackberry.api.invoke.PhoneArguments;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class ContactScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	
	ListItemField tmp = new ListItemField("Empty", -1, false, 0);
	Stat stt = null;
	private Vector stats = null;
	
	public void process(String val) {
		
	}
	
	public ContactScreen(Vector stats, AppScreen screen) {
		super(screen);
		bgManager.setStatusHeight(exit.getContentHeight());
		exit.setChangeListener(this);
		
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		addButton(exit);

		this.stats = stats;
		
		for (int i = 0; i < stats.size(); i++) {
			stt = (Stat)stats.elementAt(i);
			tmp = new ListItemField(stt.getDesc()+" : "+stt.getValue(), i, false, 0);
			tmp.setChangeListener(this);
			add(tmp);
		}
		if (stats.size() == 0) {
			add(tmp);
		}
		
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
			tmp = (ListItemField)f;
			stt = (Stat)stats.elementAt(tmp.getId());
			if (stt.getDesc().equals(Const.web)) {
				BrowserSession browserSession = Browser.getDefaultSession();
				browserSession.displayPage(stt.getValue());
				browserSession.showBrowser();
			} else if (stt.getDesc().equals(Const.phone)) {
				PhoneArguments phoneArguments = new PhoneArguments(
		                PhoneArguments.ARG_CALL, stt.getValue());
		        Invoke.invokeApplication(Invoke.APP_TYPE_PHONE, phoneArguments);
			} else if (stt.getDesc().startsWith(Const.eml)) {
				String subject = "";
				Invoke.invokeApplication( Invoke.APP_TYPE_MESSAGES, new MessageArguments(MessageArguments.ARG_NEW,stt.getValue(),subject,Const.sig));
			}
		}
	}
}