package net.mytcg.topcar.ui.custom;

import net.mytcg.topcar.util.Const;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.container.HorizontalFieldManager;

public class ProfileFieldManager extends HorizontalFieldManager
{
	public ProfileFieldManager()
	{
		super(Field.FIELD_RIGHT);
	}
	public int getPreferredWidth() {
		return Const.getWidth()-60;
	}
}