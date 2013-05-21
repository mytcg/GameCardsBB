package net.mytcg.kickoff.ui.custom;

import net.mytcg.kickoff.util.Const;
import net.rim.device.api.math.Fixed32;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class MainManager extends VerticalFieldManager
{
	//Bitmap img = Const.getGrey();
	
	public MainManager()
	{
		super(VerticalFieldManager.USE_ALL_WIDTH |NO_VERTICAL_SCROLL | NO_VERTICAL_SCROLLBAR);
	}
	protected boolean navigationMovement(int dx, int dy, int status, int time)
    {
	        this.invalidate(); 
	        return super.navigationMovement(dx, dy, status, time);
	}
	public int getPreferredHeight() {
		int maxheight = Const.getHeight()-Const.getButtonCentre().getHeight();
		if ((maxheight >= super.getPreferredHeight())&&(super.getPreferredHeight() != 0)) {
			return maxheight;
		} else {
			return super.getPreferredHeight();
		}
	}
	public int getPreferredWidth() {
		return Const.getWidth();
	}
	
	public void paint(Graphics g)
	{
		int xPts[] = {0,0,getPreferredWidth(),getPreferredWidth()};
		int yPts[] = {0,getPreferredHeight(),getPreferredHeight(),0};
		//g.setColor(3947580);
		g.setColor(2500134);
		g.drawFilledPath(xPts, yPts, null, null);
		//g.drawTexturedPath(xPts,yPts,null,null,0,0,Fixed32.ONE,0,0,Fixed32.ONE,img);
		super.paint(g);
	}
	
	public void addAll(Field []fields){
		for(int i = 0; i < fields.length; i++){
			this.add(fields[i]);
		}
	}
	protected void sublayout(int width, int height)
	{
		super.sublayout(width, height);
		setExtent();
	}
	
	private void setExtent()
	{
		setExtent(getPreferredWidth(), getPreferredHeight());
		setVirtualExtent(getPreferredWidth(), super.getPreferredHeight());
	}
}