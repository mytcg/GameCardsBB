package net.mytcg.topcar.ui.custom;

import net.mytcg.topcar.util.Auction;
import net.mytcg.topcar.util.Const;
import net.rim.device.api.math.Fixed32;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;

public final class AuctionField extends Field {
	private Auction auction;
	private Bitmap image;
	
	public AuctionField(Auction auction, Bitmap thumb) {
		this.auction = auction;
		this.image = thumb;
		construct();
	}
	
	protected void drawFocus(Graphics g, boolean x) {
		
	}
	
	public void construct() {
		int font = Const.FONT;
		
		Font _font = getFont();
		_font = _font.derive(Const.TYPE,font);
		setFont(_font);
	}
	
	public void onUndisplay() {
		
	}
	public void onUnfocus() {
		//invalidate();
		
	}
	public void onVisibilityChange(boolean visible) {
		
	}
	public void focusRemove() {
		
	}
	
	public int getPreferredWidth() {
		return Const.getWidth();
	}
	public int getPreferredHeight() {
		return Const.getHeight()-Const.getButtonCentre().getHeight();
	}
	protected void layout(int width, int height) {
		setExtent(getPreferredWidth(),getPreferredHeight());
    }
	public boolean isFocusable() {
    	return false;
    }
	public void paint(Graphics g) {
		int _xPts[] = {0,0,getPreferredWidth(),getPreferredWidth()};
		int _yPts[] = {0,Const.getHeight(),Const.getHeight(),0};
		g.drawTexturedPath(_xPts,_yPts,null,null,0,0,Fixed32.ONE,0,0,Fixed32.ONE,Const.getBackground());
		
		g.setColor(Const.FONTCOLOR);
		
		g.drawBitmap(5, 5, image.getWidth(), getPreferredHeight(), image, 0, 0);
		
		Font _font = getFont();
		_font = _font.derive(Const.TYPE,Const.FONT+2);
		g.setFont(_font);
		
		int height = 4;
		
		g.drawText(auction.getDesc(), image.getWidth()+10, height);
		
		height += Const.FONT+4;
		if(!auction.getPrice().equals("")){
			g.drawText("Current bid: "+auction.getPrice(), image.getWidth()+10, height);
		}else{
			g.drawText("Opening bid: "+auction.getOpeningBid(), image.getWidth()+10, height);
		}
		
		height += Const.FONT+4;
		g.drawText("Buy Out: "+auction.getBuyNowPrice(), image.getWidth()+10, height);
		if(!auction.getPrice().equals("")){
			height += Const.FONT+4;
			g.drawText("Bidder: "+auction.getLastBidUser(), image.getWidth()+10, height);
		}
		height += Const.FONT+4;
		g.drawText("Seller: "+auction.getUsername(), image.getWidth()+10, height);
		height += Const.FONT+4;
		g.drawText(""+auction.getEndDate(), image.getWidth()+10, height);

		_font = _font.derive(Font.PLAIN,Const.FONT);
		g.setFont(_font);
	}

	protected boolean navigationClick(int status, int time) {
        fieldChangeNotify(1);
        return true;
    }
}