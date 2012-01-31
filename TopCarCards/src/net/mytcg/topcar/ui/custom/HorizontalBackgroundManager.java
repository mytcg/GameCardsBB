package net.mytcg.topcar.ui.custom;

import net.mytcg.topcar.util.Const;
import net.rim.device.api.math.Fixed32;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.container.HorizontalFieldManager;

public class HorizontalBackgroundManager extends HorizontalFieldManager
{
	//Bitmap img = Const.getGrey();
	
	int nStatusHeight = 0;
	int nTitleHeight = 0;
	boolean useall = true;
	
	public HorizontalBackgroundManager(boolean useall)
	{
		super(HorizontalBackgroundManager.USE_ALL_HEIGHT);
		this.useall = useall;
	}
	protected boolean navigationMovement(int dx, int dy, int status, int time)
    {
	        this.invalidate(); 
	        return super.navigationMovement(dx, dy, status, time);
	}
	public int getPreferredHeight() {
		return Const.getHeight()-(Const.getButtonCentre().getHeight()+nTitleHeight)/*-40*/;
	}
	public int getPreferredWidth() {
		return Const.getWidth();
	}
	
	public HorizontalBackgroundManager()
	{
		this(true);
	}
	
	public void paint(Graphics g)
	{
		int xPts[] = {0,0,getPreferredWidth(),getPreferredWidth()};
		int yPts[] = {0,getPreferredHeight(),getPreferredHeight(),0};
		g.setColor(2302755);
		g.drawFilledPath(xPts, yPts, null, null);
		//g.drawTexturedPath(xPts,yPts,null,null,0,0,Fixed32.ONE,0,0,Fixed32.ONE,img);
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