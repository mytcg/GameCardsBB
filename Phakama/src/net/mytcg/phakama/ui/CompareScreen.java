package net.mytcg.phakama.ui;

import net.mytcg.phakama.ui.custom.ColorLabelField;
import net.mytcg.phakama.ui.custom.FixedButtonField;
import net.mytcg.phakama.ui.custom.HorizontalGamePlayManager;
import net.mytcg.phakama.ui.custom.VerticalGamePlayManager;
import net.mytcg.phakama.util.Card;
import net.mytcg.phakama.util.Const;
import net.mytcg.phakama.util.SettingsBean;
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
	
	private VerticalGamePlayManager vimage1 = null;
	private VerticalGamePlayManager vimage2 = null;
	private HorizontalGamePlayManager himage1 = null;
	private HorizontalGamePlayManager himage2 = null;
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
		
		if(!(Const.getPortrait())){
			vimage1 = new VerticalGamePlayManager();
			vimage2 = new VerticalGamePlayManager();
			vimage1.setUrl(card1.getFrontFlipurl());
			vimage2.setUrl(card2.getFrontFlipurl());
			hbgManager.add(vimage1);
			hbgManager.add(vimage2);
		}else{
			himage1 = new HorizontalGamePlayManager();
			himage2 = new HorizontalGamePlayManager();
			himage1.setUrl(card1.getFrontFlipurl());
			himage2.setUrl(card2.getFrontFlipurl());
			bgManager.add(himage1);
			bgManager.add(himage2);
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
				if(!(Const.getPortrait())){
					vimage1.setUrl(card1.getBackFlipurl());
					vimage2.setUrl(card2.getBackFlipurl());
				}else{
					himage1.setUrl(card1.getBackFlipurl());
					himage2.setUrl(card2.getBackFlipurl());
				}
				this.invalidate();
			} else {
				if(!(Const.getPortrait())){
					vimage1.setUrl(card1.getFrontFlipurl());
					vimage2.setUrl(card2.getFrontFlipurl());
				}else{
					himage1.setUrl(card1.getFrontFlipurl());
					himage2.setUrl(card2.getFrontFlipurl());
				}
				this.invalidate();
			}
		}
	}
}