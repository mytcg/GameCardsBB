package net.mytcg.dev.ui.custom;

import net.mytcg.dev.util.Const;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.container.HorizontalFieldManager;

public class PageManager extends HorizontalFieldManager
{
	protected boolean navigationMovement(int dx, int dy, int status, int time)
    {
	        this.invalidate(); 
	        return super.navigationMovement(dx, dy, status, time);
	}
	public int getPreferredHeight() {
		return 10;
	}
	public int getPreferredWidth() {
		return Const.getWidth();
	}
	
	public PageManager()
	{
	}
	
	public void addAll(Field []fields){
		for(int i = 0; i < fields.length; i++){
			this.add(fields[i]);
		}
	}
	protected void sublayout(int width, int height)
	{
		super.sublayout(width, height);
		Field field;
        int numberOfFields = getFieldCount();
        int initialx = (Const.getWidth() - numberOfFields * 14)/2;
        for (int i = 0;i < numberOfFields;i++) {
            field = getField(i); //get the field
            setPositionChild(field, initialx, 1);  //set the position for the field
            layoutChild( field, field.getWidth(), field.getHeight() ); //lay out the field
            initialx = initialx + 14;
        }
		setExtent();
	}
	
	private void setExtent()
	{
		setExtent(getPreferredWidth(), getPreferredHeight());
		setVirtualExtent(getPreferredWidth(), super.getPreferredHeight());
	}
}