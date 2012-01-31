package net.mytcg.topcar.ui.custom;

import net.mytcg.topcar.util.Const;
import net.rim.device.api.math.Fixed32;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.TouchEvent;
import net.rim.device.api.ui.XYRect;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class BackgroundManager extends VerticalFieldManager
{
	//Bitmap img = Const.getBackground();
	//Bitmap grey = Const.getGrey();
	
	int color = 2302755;
	int nStatusHeight = 0;
	int nTitleHeight = 0;
	boolean useall = true;
	boolean arrowMode = false;
	boolean title = false;
	private Bitmap leftarrow;
	private Bitmap rightarrow;
	private Bitmap header = Const.getHead();
	XYRect titleRect = new XYRect(0, 0,Const.getWidth() , Const.getLogoHeight()+(Const.FONT));
	//XYRect mainRect = new XYRect(0, 0,Const.getWidth() , img.getHeight());
	
	public BackgroundManager(boolean useall)
	{
		super(VerticalFieldManager.USE_ALL_WIDTH |NO_VERTICAL_SCROLL | NO_VERTICAL_SCROLLBAR);
		arrowMode = false;
		leftarrow = Const.getLeftArrow();
		rightarrow = Const.getRightArrow();
		this.useall = useall;
	}
	public void setArrowMode(boolean mode){
		arrowMode = mode;
	}
	public void setTitle(boolean title){
		this.title = title;
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
		if (arrowMode) {
			return Const.getWidth()-60;
		} else {
			return Const.getWidth();
		}
	}
	public void setColor(int c){
		color = c;
	}
	public BackgroundManager()
	{
		this(true);
	}
	
	public void paint(Graphics g)
	{
		int xPts[] = {0,0,Const.getWidth(),Const.getWidth()};
		int yPts[] = {0,getPreferredHeight(),getPreferredHeight(),0};
		g.setColor(color);
		g.drawFilledPath(xPts, yPts, null, null);
		//g.drawTexturedPath(xPts,yPts,null,null,0,this.getTop(),Fixed32.ONE,0,0,Fixed32.ONE,grey);
		//g.drawBitmap(mainRect, img, 0, Const.getLogoHeight()+(Const.FONT));
		//if(Const.getHeight() > (img.getHeight()-Const.getLogoHeight()+(Const.FONT))){
		//	g.drawBitmap(0, img.getHeight()-(Const.getLogoHeight()+(Const.FONT)),Const.getWidth() ,Const.getHeight() - (img.getHeight()-img.getHeight()-(Const.getLogoHeight()+(Const.FONT))), img, 0, 0);
		//}
		if(title){
			//g.drawBitmap(titleRect, img, 0, 0);
			int xPts1[] = {0,0,Const.getWidth(),Const.getWidth()};
			int yPts1[] = {0,header.getHeight(),header.getHeight(),0};
			g.drawTexturedPath(xPts1,yPts1,null,null,0,0,Fixed32.ONE,0,0,Fixed32.ONE,header);
		}
		if(arrowMode){
			g.drawBitmap(0, (getPreferredHeight()-leftarrow.getHeight()-10)/2, leftarrow.getWidth(), leftarrow.getHeight(), leftarrow, 0, 0);
			g.drawBitmap((Const.getWidth()-rightarrow.getWidth()), (getPreferredHeight()-rightarrow.getHeight()-10)/2, rightarrow.getWidth(), rightarrow.getHeight(), rightarrow, 0, 0);
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
	public void addAll(Field []fields){
		for(int i = 0; i < fields.length; i++){
			this.add(fields[i]);
		}
	}
	public boolean checkLeftArrow(int x, int y){
		if(arrowMode){
			if((x>0 && x < leftarrow.getWidth())&&(y>(getPreferredHeight()-leftarrow.getHeight()-10)/2 && y <((getPreferredHeight()-leftarrow.getHeight()-10)/2)+leftarrow.getHeight())){
				return true;
			}
		}
		return false;
	}
	public boolean checkRightArrow(int x, int y){
		if(arrowMode){
			if((x > Const.getWidth()-rightarrow.getWidth() && x < Const.getWidth())&&(y>(getPreferredHeight()-rightarrow.getHeight()-10)/2 && y <((getPreferredHeight()-rightarrow.getHeight()-10)/2)+rightarrow.getHeight())){
				return true;
			}
		}
		return false;//super.touchEvent(event);
	}
	
	protected void sublayout(int width, int height)
	{
		super.sublayout(width, height);
		Field field;
        int numberOfFields = getFieldCount();
        int h = 0;
        for (int i = 0;i < numberOfFields;i++) {
            field = getField(i); //get the field
            if(field instanceof ThumbnailField || field instanceof ListItemField || field instanceof ListLabelField || field instanceof SexyEditField || field instanceof SeparatorField || field instanceof FriendField || field instanceof ProfileFieldManager || field instanceof ColorLabelField){	
            	if (arrowMode){
            		setPositionChild(field, 30, h);  //set the position for the field
            	} else {
            		setPositionChild(field, 0, h);  //set the position for the field
            	}
            	if(arrowMode){
            		layoutChild( field, Const.getWidth()-60, field.getHeight() ); //lay out the field
            	} else {
            		layoutChild( field, Const.getWidth(), field.getHeight() ); //lay out the field
            	}
            	h = h + field.getHeight();
            }else{
            	h = h + field.getHeight();
            }
        }
		if (useall)
			setExtent();
	}
	
	private void setExtent()
	{
		setExtent(Const.getWidth(), getPreferredHeight());
		setVirtualExtent(Const.getWidth(), super.getPreferredHeight());
	}
}