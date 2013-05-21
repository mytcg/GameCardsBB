package net.mytcg.kickoff.ui;

import java.util.Vector;

import net.mytcg.kickoff.ui.custom.AwardDetailField;
import net.mytcg.kickoff.ui.custom.AwardField;
import net.mytcg.kickoff.ui.custom.ColorLabelField;
import net.mytcg.kickoff.ui.custom.FixedButtonField;
import net.mytcg.kickoff.util.Award;
import net.mytcg.kickoff.util.SubAward;
import net.mytcg.kickoff.util.Const;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.UiApplication;

public class AwardDetailScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	
	AwardField tmp = null;

	public AwardDetailScreen(Award award)
	{
		super(null);
		add(new ColorLabelField(""));
		bgManager.setStatusHeight(exit.getContentHeight());
		
		exit.setChangeListener(this);
		String progress = "Current Progress: Complete!";
		boolean badges = false;
		for(int i = 0; i < award.getSubAwards().size();i++){
			if(award.getSubAward(i).getDateCompleted().equals("")){
				progress = "Current Progress: "+award.getSubAward(i).getProgress()+"/"+award.getSubAward(i).getTarget();
			}else{
				badges = true;
			}
		}
		add(new AwardDetailField(award.getName(),award.getDescription(),progress,badges));
		for(int i = 0; i < award.getSubAwards().size();i++){
			if(award.getSubAward(i).getProgress()>=award.getSubAward(i).getTarget()){
				add(new AwardField(award,i));
			}
		}
		
		addButton(new FixedButtonField(""));
		addButton(new FixedButtonField(""));
		addButton(exit);
	}
	
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		}
	}
}