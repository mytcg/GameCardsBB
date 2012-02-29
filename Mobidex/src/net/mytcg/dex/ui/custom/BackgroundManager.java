package net.mytcg.dex.ui.custom;

import net.mytcg.dex.util.Const;
import net.rim.device.api.math.Fixed32;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class BackgroundManager extends VerticalFieldManager
{
	Bitmap img = Const.getBackground();
	
	int nStatusHeight = 0;
	int nTitleHeight = 0;
	boolean useall = true;
	boolean title = false;
	
	public BackgroundManager(boolean useall)
	{
		super(VerticalFieldManager.USE_ALL_WIDTH | NO_VERTICAL_SCROLL | NO_VERTICAL_SCROLLBAR);
		this.useall = useall;
	}
	protected boolean navigationMovement(int dx, int dy, int status, int time)
    {
	        this.invalidate(); 
	        return super.navigationMovement(dx, dy, status, time);
	}
	public int getPreferredHeight() {
		int maxheight = Const.getHeight()-(Const.getButtonCentre().getHeight()+nTitleHeight);
		if ((maxheight >= super.getPreferredHeight())&&(super.getPreferredHeight() != 0)) {
			return maxheight;
		} else {
			return super.getPreferredHeight();
		}
	}
	public int getPreferredWidth() {
		return Const.getWidth();
	}
	
	public BackgroundManager()
	{
		this(true);
	}
	
	public void setTitle(boolean title){
		this.title = title;
	}
	
	public void paint(Graphics g)
	{
		int xPts[] = {0,0,getPreferredWidth(),getPreferredWidth()};
		int yPts[] = {0,getPreferredHeight(),getPreferredHeight(),0};
		g.drawTexturedPath(xPts,yPts,null,null,0,0,Fixed32.ONE,0,0,Fixed32.ONE,img);
		if(title){
			int xPts1[] = {0,0,Const.getWidth(),Const.getWidth()};
			int yPts1[] = {0,Const.getLogoRight().getHeight(),Const.getLogoRight().getHeight(),0};
			g.drawTexturedPath(xPts1,yPts1,null,null,0,0,Fixed32.ONE,0,0,Fixed32.ONE,Const.getLogoRight());
		}
		super.paint(g);
	}
	public void setTitleHeight(int height) {
		nTitleHeight = height;
		setExtent();
	}
	public void setStatusHeight(int height)
	{
		nStatusHeight = height;
		setExtent();
	}
	protected void sublayout(int width, int height)
	{
		super.sublayout(width, height);
		if (useall)
			setExtent();
	}
	
	private void setExtent()
	{
		setExtent(getPreferredWidth(), getPreferredHeight());
		setVirtualExtent(getPreferredWidth(), super.getPreferredHeight());
	}
}