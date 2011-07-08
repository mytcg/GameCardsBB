package net.mytcg.dev.ui;

import net.mytcg.dev.ui.custom.AuctionField;
import net.mytcg.dev.ui.custom.ColorLabelField;
import net.mytcg.dev.ui.custom.FixedButtonField;
import net.mytcg.dev.util.Auction;
import net.mytcg.dev.util.Const;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;

public class AuctionInfoScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	
	boolean update = true;
	Auction auction = null;
	
	public AuctionInfoScreen(Auction auction, Bitmap auctionthumbnail) {
		super(null);
		this.auction = auction;
		bgManager.setStatusHeight(exit.getContentHeight());
		
		exit.setChangeListener(this);
		
		add(new AuctionField(auction,auctionthumbnail));
		addButton(exit);
		addButton(new FixedButtonField(""));
		invalidate();
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