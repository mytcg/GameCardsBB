package net.mytcg.dex.ui;

import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;
import javax.microedition.io.DatagramConnection;
import javax.microedition.pim.PIM;
import javax.microedition.pim.PIMItem;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;

import net.mytcg.dex.ui.custom.ColorLabelField;
import net.mytcg.dex.ui.custom.FixedButtonField;
import net.mytcg.dex.ui.custom.SexyEditField;
import net.mytcg.dex.util.Card;
import net.mytcg.dex.util.Const;
import net.rim.blackberry.api.pdap.BlackBerryContact;
import net.rim.blackberry.api.pdap.BlackBerryContactList;
import net.rim.device.api.io.Base64OutputStream;
import net.rim.device.api.system.RadioInfo;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.EditField;

public class ShareScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField exit = new FixedButtonField(Const.back);
	FixedButtonField contacts = new FixedButtonField(Const.contacts);
	FixedButtonField send = new FixedButtonField(Const.send);
	SexyEditField number = new SexyEditField("", EditField.FILTER_NUMERIC, 36);
	ColorLabelField lbl = new ColorLabelField(Const.cell);
	SexyEditField note = new SexyEditField(Const.getWidth(), (Const.FONT*8));
	
	private int sendSize = 160;
	private int numsms = 0;
	private String[] smstext = null;
	//private String message = " would like to share his Mobidex Business Card with you. Go to http://dex.mytcg.net/m/ to download his Card.";
	private String smsnumber = "";
	private Card card = null;
	
	public ShareScreen(Card card, AppScreen screen)
	{
		super(screen);
	
		this.card = card;
		add(lbl);
		add(number);
		
		lbl = new ColorLabelField(Const.notes);
		add(lbl);
		note.setText("");
		add(note);
		
		Font _font = getFont();
		_font = _font.derive(Font.BOLD, Const.FONT);
		setFont(_font);
		
		bgManager.setStatusHeight(exit.getContentHeight());
		
		exit.setChangeListener(this);
		contacts.setChangeListener(this);
		send.setChangeListener(this);
		
		addButton(exit);
		addButton(contacts);
		addButton(send);
	}
	
	public String selectNumber(String[] numbers) {
		   String number = "";
		   NumberScreen nPS = new NumberScreen(numbers, this);
		   UiApplication.getUiApplication().pushModalScreen(nPS);
		   number = nPS.getResponse();
		   return number;
	   }
	
	public String[] getNumber(PIMItem pim) {
		   int length = pim.countValues(BlackBerryContact.TEL);
		   int realLength = length;
		   for (int i = 0;i < length; ++i) {
			   if (((pim.getString(BlackBerryContact.TEL, i)) == null)||((pim.getString(BlackBerryContact.TEL, i)).length() <= 1)) {
				   realLength--;
				   
			   }
		   }
		   String[] number = new String[realLength];
		   for (int i = 0;i < length; ++i) {
			   if (((pim.getString(BlackBerryContact.TEL, i)) != null)||((pim.getString(BlackBerryContact.TEL, i)).length() > 1)) {
				   number[i] = pim.getString(BlackBerryContact.TEL, i);
			   }
		   }
		   
		   return number;
	   }
	
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			screen = null;
			UiApplication.getUiApplication().popScreen(this);
		} else if (f == contacts) {
			try {
				BlackBerryContactList bb = (BlackBerryContactList)PIM.getInstance().openPIMList(PIM.CONTACT_LIST, PIM.READ_ONLY);
				PIMItem pim = bb.choose();
				String[] numb = getNumber(pim);
			   if (numb.length < 1) {
				   setText("No Number for Contact.");
			   } else if (numb.length == 1) {
				   String num = numb[0];
				   if (num.length() <= 0) {
					   setText("No Number for Contact.");
				   } else {
					   number.setText(num);
				   }
			   } else if (numb.length > 1) {
				   String num = selectNumber(numb);
				   if (num.length() <= 0) {
					   setText("No Number for Contact.");
				   } else {
					   number.setText(num);
				   }
			   }
		   } catch (Exception e) {
			   setText("No Number for Contact.");
		   }
		} else if (f == send) {
			if ((number.getText() == null)||(number.getText().length() <= 0)) {
				setText("Cell Number cannot be blank.");
			} else {
				String note64="";
				String sznote = " ["+note.getText() + "] ";
				try {
					note64 = new String(Base64OutputStream.encode(sznote.getBytes(), 0, sznote.length(), false, false), "UTF-8");
				} catch (Exception e) {
					note64="";
				}
				doConnect(Const.trade+card.getId()+Const.trademethod+number.getText()+Const.sendnote+note64, false);
			}
		}
	}
	
	public void checkSplit(String text) {
		if (text.length() > sendSize) {
			numsms = (int)Math.ceil(((double)text.length() / (double)sendSize));
			smstext = new String[numsms];
			
			for (int i = 0; i < numsms; i++) {
				smstext[i] = text.substring(0, Math.min(text.length(), sendSize));
				if (text.length() > sendSize) {
					text = text.substring(sendSize);
				}
			}
		} else {
			numsms = 1;
			smstext = new String[numsms];
			smstext[0] = text;
		}
	}
	
	public void process(String val) {
		int fromIndex = -1;
		System.out.println(val);
		if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
			String message = val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex));
			if (message.startsWith("User not found.")) {
				checkSplit(message.substring(16));
				smsnumber = number.getText();
				sendSMS();
			} else {
				synchronized(UiApplication.getEventLock()) {
					setText(message);
				}
			}
		}
		
	}
	
	private void sendSMS() {
		setText("Sending SMS.");
		try {
			if (this.smstext[0].length() <= 4) {
				setText("Could not send invite. No data generated.");
			} else {
				if (isCDMA()) {
				 	DatagramConnection connection = null;
				 	Datagram datagram = null;
				    for (int i = 0; i < numsms; i++) {
						connection = (DatagramConnection)Connector.open("sms://"+Const.removeSpaces(this.smsnumber));
		            	byte[] bytes = this.smstext[i].getBytes();
		            	datagram = connection.newDatagram( bytes, bytes.length);
		            	connection.send(datagram);
		            	try {
		            		connection.close();
		            	} catch (Exception e) {}
	            	}
				} else {
				    MessageConnection localMessageConnection = null;
				    TextMessage localTextMessage = null;
		            for (int i = 0; i < numsms; i++) {
						localMessageConnection = (MessageConnection)Connector.open("sms://" + Const.removeSpaces(this.smsnumber));
						localTextMessage = (TextMessage)(localMessageConnection.newMessage(MessageConnection.TEXT_MESSAGE));
						localTextMessage.setPayloadText(this.smstext[i]);
						localMessageConnection.send(localTextMessage);
						localMessageConnection.close();
					}
				}
			}
			setText("Application Shared.");
		} catch (Exception e) {
			setText("Could not send SMS.");
		}
	}
	
	 public static boolean isCDMA() {
        return RadioInfo.getNetworkType() == RadioInfo.NETWORK_CDMA;
    }
}