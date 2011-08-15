package net.mytcg.sport.ui;

import net.mytcg.sport.ui.custom.ColorLabelField;
import net.mytcg.sport.ui.custom.CompareField;
import net.mytcg.sport.ui.custom.FixedButtonField;
import net.mytcg.sport.util.Card;
import net.mytcg.sport.util.Const;
import net.mytcg.sport.util.SettingsBean;
import net.rim.device.api.math.Fixed32;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.UiApplication;


public class CompareScreen extends AppScreen implements FieldChangeListener
{
	private FixedButtonField exit = new FixedButtonField(Const.back);
	private FixedButtonField flips = new FixedButtonField(Const.flip);
	
	private CompareField image1 = null;
	private CompareField image2 = null;
	private boolean flip = false;
	private Card card1 = null;
	private Card card2 = null;
	
	Bitmap img = Const.getBackground();
	public void paint(Graphics g)
	{
		int xPts[] = {0,0,getPreferredWidth(),getPreferredWidth()};
		int yPts[] = {0,getPreferredHeight(),getPreferredHeight(),0};
		g.drawTexturedPath(xPts,yPts,null,null,0,0,Fixed32.ONE,0,0,Fixed32.ONE,img);
		super.paint(g);
	}
	
	public CompareScreen(Card card1, Card card2) {
		super(true);
		this.card1 = card1;
		this.card2 = card2;
		
		//bgManager.setStatusHeight(exit.getContentHeight());
		exit.setChangeListener(this);
		flips.setChangeListener(this);
		
		add(new ColorLabelField(""));
		
		image1 = new CompareField(card1.getFrontFlipurl());
		image1.setChangeListener(this);
		
		image2 = new CompareField(card2.getFrontFlipurl());
		image2.setChangeListener(this);
		
		if(!(Const.getPortrait())){
			hbgManager.add(image1);
			hbgManager.add(image2);
		}else{
			bgManager.add(image1);
			bgManager.add(image2);
		}
		
		
		addButton(flips);
		addButton(new FixedButtonField(""));
		addButton(exit);
	}
	
	public void process(String val) {
		synchronized(UiApplication.getEventLock()) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		}
	}
	
	protected void onExposed() {
		if(SettingsBean.getSettings().created){
			UiApplication.getUiApplication().popScreen(this);
		}
	}
	
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		} else if ((f == flips)) {
			flip = !flip;
			if (flip) {
				image1.setUrl(card1.getBackFlipurl());
				image2.setUrl(card2.getBackFlipurl());
				this.invalidate();
			} else {
				image1.setUrl(card1.getFrontFlipurl());
				image2.setUrl(card2.getFrontFlipurl());
				this.invalidate();
			}
		}
	}
}