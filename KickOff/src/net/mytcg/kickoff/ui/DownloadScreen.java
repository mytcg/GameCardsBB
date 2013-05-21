package net.mytcg.kickoff.ui;

import net.mytcg.kickoff.ui.custom.ColorLabelField;
import net.mytcg.kickoff.ui.custom.FixedButtonField;
import net.mytcg.kickoff.ui.custom.NonEditableField;
import net.mytcg.kickoff.util.Const;
import net.rim.blackberry.api.browser.Browser;
import net.rim.blackberry.api.browser.BrowserSession;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;

public class DownloadScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField download = new FixedButtonField(Const.down);
	FixedButtonField later = new FixedButtonField(Const.later);
	NonEditableField note = new NonEditableField("");
	String url = "";
	
	public void process(String val) {
		
	}
	
	public DownloadScreen(String message, AppScreen screen)
	{
		super(screen);
		this.url = message;
		Font _font = getFont();
		_font = _font.derive(Const.TYPE, Const.FONT);
		setFont(_font); 
		
		add(new ColorLabelField(Const.newupdate));
		note.setText(Const.updatemsg);
		add(note);
		
		bgManager.setStatusHeight(Const.getButtonHeight());
		
		download.setChangeListener(this);
		later.setChangeListener(this);
		
		addButton(download);
		addButton(new FixedButtonField(""));
		addButton(later);
	}
	public void fieldChanged(Field f, int i) {
		if (f == later) {
			close();
		} else if (f == download) {
			BrowserSession browserSession = Browser.getDefaultSession();
			browserSession.displayPage(url);
			browserSession.showBrowser();
		}
	}
}