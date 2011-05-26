package net.mytcg.dex.util;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

import net.mytcg.dex.http.ConnectionHandler;
import net.mytcg.dex.ui.GameCardsHome;
import net.rim.blackberry.api.phone.Phone;
import net.rim.device.api.io.file.FileIOException;
import net.rim.device.api.math.Fixed32;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.CDMAInfo;
import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.system.Display;
import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.system.GPRSInfo;
import net.rim.device.api.system.IDENInfo;
import net.rim.device.api.system.RadioInfo;
import net.rim.device.api.system.SIMCardInfo;
import net.rim.device.api.ui.Color;

public final class Const {
	
	public static String VERSION = "1.0";
	private static boolean PORTRAIT = true;
	private static final double ratio = 1.40625;
	public static String PREFIX = "dex_";
	public static int THREADS = 0;
	public static ConnectionHandler connect = null;
	
	public static ConnectionHandler getConnection() {
		
		if ((connect == null)||(!connect.isBusy())) {
			connect = new ConnectionHandler();   
		}
		return connect;
	}
	
	public static void setPortrait(boolean portrait) {
		PORTRAIT = portrait;
	}
	public static boolean getPortrait() {
		return PORTRAIT;
	}
	public static String getVersion() {
		return VERSION;
	}
	public static String getMake() {
		return "&make="+DeviceInfo.getManufacturerName();
	}
	public static String getModel() {
		return "&model="+DeviceInfo.getDeviceName(); 
	}
	public static String getOSVer() {
		return "&osver="+DeviceInfo.getSoftwareVersion();
	}
	public static String getTouch() {
		return "&touch="+0;
	}
	
	private static EncodedImage image;
	private static Bitmap b;
	
	private static Bitmap getScaledBitmapImage(String imagename, int ratioX, int ratioY){
		image = EncodedImage.getEncodedImageResource(imagename);
		try {	
			int currentWidthFixed32 = Fixed32.toFP(image.getWidth());
			int currentHeightFixed32 = Fixed32.toFP(image.getHeight());
			double ratio = (double)ratioX / (double) ratioY;
			double w = (double) image.getWidth() * ratio;
			double h = (double)image.getHeight() * ratio;
			int width = (int) w;
			int height = (int) h;
			int requiredWidthFixed32 = Fixed32.toFP(width);
			int requiredHeightFixed32 = Fixed32.toFP(height);
			int scaleXFixed32 = Fixed32.div(currentWidthFixed32, requiredWidthFixed32);
			int scaleYFixed32 = Fixed32.div(currentHeightFixed32, requiredHeightFixed32);
			image = image.scaleImage32(scaleXFixed32, scaleYFixed32);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image.getBitmap();
	}
	private static Bitmap getScaledBitmapImage(EncodedImage image, int ratioX, int ratioY) {
		try {	
			int currentWidthFixed32 = Fixed32.toFP(image.getWidth());
			int currentHeightFixed32 = Fixed32.toFP(image.getHeight());
			double ratio = (double)ratioX / (double) ratioY;
			double w = (double) image.getWidth() * ratio;
			double h = (double)image.getHeight() * ratio;
			int width = (int) w;
			int height = (int) h;
			int requiredWidthFixed32 = Fixed32.toFP(width);
			int requiredHeightFixed32 = Fixed32.toFP(height);
			int scaleXFixed32 = Fixed32.div(currentWidthFixed32, requiredWidthFixed32);
			int scaleYFixed32 = Fixed32.div(currentHeightFixed32, requiredHeightFixed32);
			image = image.scaleImage32(scaleXFixed32, scaleYFixed32);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image.getBitmap();
	}
	private static Bitmap getSizeImage(String bitmap) {
		switch (Const.SCREEN) {
			case Const.LARGE_SCREEN:
				b = Bitmap.getBitmapResource(bitmap);
				break;
			case Const.MEDIUM_SCREEN:
				b = getScaledBitmapImage(bitmap, 3, 4);
				break;
			default:
				b = getScaledBitmapImage(bitmap, 2, 3);
				break;
		}
		return b;
	}
	public static Bitmap getSizeImage(EncodedImage bitmap) {
		switch (Const.SCREEN) {
			case Const.LARGE_SCREEN:
				b = bitmap.getBitmap();
				break;
			case Const.MEDIUM_SCREEN:
				b = getScaledBitmapImage(bitmap, 3, 4);
				break;
			default:
				b = getScaledBitmapImage(bitmap, 2, 3);
				break;
		}
		return b;
	}
	
	
	public static final short SMALL_SCREEN = 0;
	public static final short MEDIUM_SCREEN = 1;
	public static final short LARGE_SCREEN = 2;
	public static short SCREEN = LARGE_SCREEN;
	
	public static void getSettings() {
		SettingsBean _instance = SettingsBean.getSettings();
		int width = Const.getWidth();
		if (width <= 240) {
			_instance.setSize(Const.SMALL_SCREEN);
			Const.SCREEN = Const.SMALL_SCREEN;
			Const.FONT = Const.SMALL_FONT;
		} else if (width >= 360) {
			_instance.setSize(Const.LARGE_SCREEN);
			Const.SCREEN = Const.LARGE_SCREEN;
			Const.FONT = Const.LARGE_FONT;
		} else {
			_instance.setSize(Const.MEDIUM_SCREEN);
			Const.SCREEN = Const.MEDIUM_SCREEN;
			Const.FONT = Const.MEDIUM_FONT;
		}
		if (Const.getWidth() > Const.getHeight()) {
			Const.PORTRAIT = false;
		}
	}
	
	/*
	 * NAVIGATION
	 */
	public static int GOTOSCREEN;
	public static int FROMSCREEN;
	public static final short LOGINSCREEN = 0;
	public static final short ALBUMSCREEN = 1;
	public static final short LISTSCREEN = 2;
	public static final short REGISTERSCREEN = 3;
	
	public static final short USERDET = 0;
	public static final short USERCAT = 1;
	public static final short SUBCAT = 2;
	public static final short CARDS = 3;
	
	public static final short LOGOUT = -1;
	public static final short SEARCH = -9;
	public static final short REDEEM = -10;
	
	public static final short CACHE = -1;
	public static final short MYCARD = -2;
	public static final short NEWCARDS = -3;
	public static final short UPDATES = -4;
	
	public static final short PADDING = 20;
	
	/*
	 * DISPLAY VALUES
	 */
	public static final int SMALL_FONT = 12;
	public static final int MEDIUM_FONT = 14;
	public static final int LARGE_FONT = 16;
	public static int FONT = LARGE_FONT;
	public static final int INCREASE_FONT = 2;
	
	public static final int FONTCOLOR = Color.WHITE;
	public static final int BACKCOLOR = Color.BLACK;
	public static final int BUTTONCOLOR = Color.WHITE;
	public static final int SELECTEDCOLOR = Color.BLUE;
	
	public static final int getWidth() {
		return Display.getWidth();
	}
	public static final int getLogoHeight() {
		return getLogo().getHeight();
	}
	public static final int getHeight() {
		return Display.getHeight();
	}
	public static final int getCardHeight() {
		if (Const.PORTRAIT) {
			return ((Const.getHeight()-getButtonCentre().getHeight())-Const.PADDING);
		} else {
			return (int)((((double)((Const.getHeight()-getButtonCentre().getHeight())-Const.PADDING))*ratio));
		}
	}
	public static final int getUsableHeight() {
		return getHeight()-(getButtonCentre().getHeight()+getLogo().getHeight()+PADDING);
	}
	public static final int getButtonHeight() {
		return getListboxSelLeftEdge().getHeight();
	}
	
	public static Bitmap rotate(Bitmap bitmap, int angle) throws Exception
	{
		if(angle == 0)
		{
			return null; 
		}
		else if(angle != 180 && angle != 90 && angle != 270)
		{
			throw new Exception("Invalid angle");
		}
	 
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
	 
		int[] rowData = new int[width];
		int[] rotatedData = new int[width * height];
	 
		int rotatedIndex = 0;
	 
		for(int i = 0; i < height; i++)
		{
			bitmap.getARGB(rowData, 0, width, 0, i, width, 1);
	 
			for(int j = 0; j < width; j++)
			{
				rotatedIndex = 
					angle == 90 ? (height - i - 1) + j * height : 
					(angle == 270 ? i + height * (width - j - 1) : 
						width * height - (i * width + j) - 1
					);
	 
				rotatedData[rotatedIndex] = rowData[j];
			}
		}

		javax.microedition.lcdui.Image tmpJ2MEImage = null;
		if(angle == 90 || angle == 270)
		{
			tmpJ2MEImage = javax.microedition.lcdui.Image.createRGBImage(rotatedData, height, width, true);
		}
		else
		{
			tmpJ2MEImage = javax.microedition.lcdui.Image.createRGBImage(rotatedData, width, height, true);
		}
		
		rowData = null;
		rotatedData = null;
		
		int rotWidth = tmpJ2MEImage.getWidth();
		int rotHeight = tmpJ2MEImage.getHeight();
		rotatedData = new int[rotWidth * rotHeight];
		tmpJ2MEImage.getRGB(rotatedData, 0, rotWidth, 0, 0, rotWidth, rotHeight);
		tmpJ2MEImage = null;
		
		Bitmap rotatedBitmap = new Bitmap(rotWidth, rotHeight);
		rotatedBitmap.setARGB(rotatedData, 0, rotWidth, 0, 0, rotWidth, rotHeight);
		rotatedData = null;
		return rotatedBitmap;		
	}
	/*
	 * STRING VALUES - Localization values
	 */
	public static String login = "Login";
	public static String register = "Register";
	public static String exit = "Exit";
	public static String back = "Back";
	public static String home = "Home";
	public static String newupdate = " Update Available";
	public static String updatemsg = "New update available. Select \"Update\" to install, or \"Later\" to continue.";
	public static String contacts = "Contacts";
	public static String send = "Share";
	public static String search = "Search";
	public static String term = " Search Term:";
	public static String code = " Redeem Code:";
	public static String conti = "Continue";
	public static String down = "Update";
	public static String later = "Later";
	public static String yes = "Yes";
	public static String no = "No";
	public static String save = "Save";
	public static String flip = "Flip";
	public static String options = "Options";
	public static String logout = "Logout";
	public static String redeem = "Redeem";
	public static String close = "Close";
	public static String accept = "Accept";
	public static String reject = "Reject";
	
	public static String quantity = "Quantity: ";
	
	public static String notes = " Message";
	public static String name = " Full Name";
	public static String surname = " Username";
	public static String cell = " Cell Number";
	public static String age = " Email Address";
	public static String gender = " Password";
	
	public static String remove = "Are you sure you wish to delete this card?";
	
	public static final String getOS() {
		return "&os=Blackberry";
	}
	public static final String getIMEI() {
		String imei = "";
		if (isCDMA()) {
			imei = ""+CDMAInfo.getESN();
		} else if (isIDEN()){
			imei = IDENInfo.imeiToString(IDENInfo.getIMEI());
		} else {
			imei = GPRSInfo.imeiToString(GPRSInfo.getIMEI());
		}
		return "&imei="+imei.trim();
	}
	public static final String getIMSI() {
		String imsi = "";
		if (isCDMA()) {
			imsi = new String(CDMAInfo.getIMSI());
		} else {
			imsi = "";
			try {
				imsi = GPRSInfo.imeiToString(SIMCardInfo.getIMSI());
			} catch (Exception e) {
				imsi = "";
			}
		}
		return "&imsi="+imsi.trim();
	}
	public static final String getMSISDN() {
		return "&msisdn="+Phone.getDevicePhoneNumber(false);
	}
	public final static boolean isCDMA() {
        return RadioInfo.getNetworkType() == RadioInfo.NETWORK_CDMA;
    }
	public final static boolean isIDEN() {
        return RadioInfo.getNetworkType() == RadioInfo.NETWORK_IDEN;
    }
	
	/*
	 * UTILITIES
	 */
	public static String err_null_url = "Null URL passed in";
	public static String http = "http";
	public static String err_url_htt = "URL not http or https";
	
	public static String url = "http://dex.mytcg.net/_phone/?";
	public static String userdetails = "userdetails=1";
	public static String registeruser = "registeruser=1";
	/*
	 * $username = $_REQUEST['username'];
	$password = $_REQUEST['password'];
	$email = $_REQUEST['email'];
	$name = $_REQUEST['name'];
	$cell = $_REQUEST['cell'];
	 * 
	 */
	public static String username = "&name=";
	public static String userfullname = "&username=";
	public static String usercell = "&cell=";
	public static String useremail = "&email=";
	public static String userpassword = "&password=";
	public static String usercategories = "usercategories=1";
	public static String savecard = "savecard=";
	public static String deletecard = "deletecard=";
	public static String rejectcard = "rejectcard=";
	public static String showall = "&showall=0";
	public static String cardsincategory = "cardsincategory=";
	public static String second = "&seconds=";
	public static String savenote = "savenote=";
	public static String trade = "tradecard=";
	public static String seek = "search=";
	public static String rdm = "redeemcode=";
	public static String update = "update=";
	public static String trademethod = "&trademethod=phone_number&detail=";
	public static String sendnote = "&note=";
	public static String cardid = "&cardid=";
	public static String height = "&height=";
	public static String width = "&width=";
	public static String subcategories = "usersubcategories=1&category=";
	public static String cat = "<usercategories><album><album><albumid>-999</albumid><hascards>false</hascards><albumname>Empty</albumname></album></usercategories>";
	public static String card = "<cardsincategory><card><cardid>-999</cardid><description>Empty</description><quantity>0</quantity><backurl></backurl></card></cardsincategory>";
	
	
	public static String web = "Web Address";
	public static String phone = "Mobile No";
	public static String user = " Username";
	public static String fullname = " Full Name";
	public static String password = " Password";
	public static String eml = "Email";
	public static String sig = "\n\n\nSent from my Mobidex";
	
	
	public static String cards = "/cards/";
	public static final int cards_length = cards.length();
	public static String png = ".png";
	
	public static boolean store = false;
	public static int first = 0;
	public static String storage = "file:///SDCard/BlackBerry/MAStore/";
	
	public static String getStorage() {
		while (!store) {
			FileConnection _file = null;
			try {
				_file = (FileConnection)Connector.open(storage);
				if (!_file.exists()) {
					_file.mkdir();
				}
				_file.close();
				store = true;
			} catch (FileIOException e) {
				if (first==0) {
					storage = System.getProperty("fileconn.dir.photos") + "MAStore/";
					++first;
				} else if (first==1) {
					storage = System.getProperty("fileconn.dir.memorycard.photo") + "MAStore/";
					++first;
				} else {
					storage = "file:///store/home/user/pictures/";
					store = false;
				}
			} catch (Exception e) {
				storage = "file:///store/home/user/pictures/";
				store = true;
			}
		}
		return storage;
	}
	
	public static boolean debug = true;
	public static GameCardsHome app;
	
	public static final String tru = "true";
	
	public static String bes_con = ";deviceside=false";
	public static String bis_con = bes_con+";connectionUID=";
	public static String wifi_con = ";deviceside=true;interface=wifi";
	public static String wap_con = ";deviceside=true;ConnectionUID=";
	public static String tcp_con = ";deviceside=true";
	
	public static String ippp = "ippp";
	public static String gpmds = "gpmds";
	public static String wptcp = "wptcp";
	public static String wifi = "wifi";
	public static String wap2 = "wap2";
	public static String waptrans = "waptrans";
	public static String wap = "wap";
	public static String browserconfig = "browserconfig";
	public static String sms1992 = "sms://:1992";
	public static String cmime = "CMIME";
	public static String smsdata = "sms://";
	public static String mmsdata = "mms://";
	public static String internet = "internet";
	
	public static String download_url = "";
	
	public static boolean processUserDetails(String val) {
		int fromIndex;
    	if ((fromIndex = val.indexOf(xml_userdetails)) != -1) {
    		SettingsBean _instance = SettingsBean.getSettings();
    		if ((fromIndex = val.indexOf(xml_email)) != -1) {
    			_instance.setEmail(val.substring(fromIndex+xml_email_length, val.indexOf(xml_email_end, fromIndex)));
    		}
    		if ((fromIndex = val.indexOf(xml_credits)) != -1) {
    			_instance.setCredits(val.substring(fromIndex+xml_credits_length, val.indexOf(xml_credits_end, fromIndex)));
    		}
    		_instance.setAuthenticated(true);
    		SettingsBean.saveSettings(_instance);
    		_instance = null;
    		return true;
    	}
    	return false;
	}
	
	/*
	 * ICONS
	 */
	private static Bitmap missingcardthumb;
	private static String sz_missingcardthumb = "missingcardthumb.png";
	public static Bitmap getEmptyThumb() {
		if (missingcardthumb == null) {
			missingcardthumb = getSizeImage(sz_missingcardthumb);   
		}
		return missingcardthumb;
	}
	
	private static Bitmap note;
	private static String sz_note = "note.png";
	public static Bitmap getNote() {
		if (note == null) {
			note = getSizeImage(sz_note);   
		}
		return note;
	}
	
	private static Bitmap thumbnaildisplaycentre;
	private static String sz_thumbnaildisplaycentre = "thumbnail_display_centre.png";
	public static Bitmap getThumbCentre() {
		if (thumbnaildisplaycentre == null) {
			thumbnaildisplaycentre = getSizeImage(sz_thumbnaildisplaycentre);   
		}
		return thumbnaildisplaycentre;
	}
	
	private static Bitmap thumbnaildisplayrightedge;
	private static String sz_thumbnaildisplayrightedge = "thumbnail_display_right_edge.png";
	public static Bitmap getThumbRightEdge() {
		if (thumbnaildisplayrightedge == null) {
			thumbnaildisplayrightedge = getSizeImage(sz_thumbnaildisplayrightedge);   
		}
		return thumbnaildisplayrightedge;
	}
	
	private static Bitmap loadingthumb;
	private static String sz_loadingthumb = "loadingthumb.png";
	public static Bitmap getThumbLoading() {
		if (loadingthumb == null) {
			loadingthumb = getSizeImage(sz_loadingthumb);   
		}
		return loadingthumb;
	}
	
	private static Bitmap textmiddle;
	private static String sz_textmiddle = "text_middle.png";
	public static Bitmap getTextMiddle() {
		if (textmiddle == null) {
			textmiddle = getSizeImage(sz_textmiddle);   
		}
		return textmiddle;
	}
	
	private static Bitmap textmiddletop;
	private static String sz_textmiddletop = "text_middle_top.png";
	public static Bitmap getTextMiddleTop() {
		if (textmiddletop == null) {
			textmiddletop = getSizeImage(sz_textmiddletop);   
		}
		return textmiddletop;
	}
	
	private static Bitmap textmiddlebottom;
	private static String sz_textmiddlebottom = "text_middle_bottom.png";
	public static Bitmap getTextMiddleBottom() {
		if (textmiddlebottom == null) {
			textmiddlebottom = getSizeImage(sz_textmiddlebottom);   
		}
		return textmiddlebottom;
	}
	
	private static Bitmap textleftmiddle;
	private static String sz_textleftmiddle = "text_left_middle.png";
	public static Bitmap getTextLeftMiddle() {
		if (textleftmiddle == null) {
			textleftmiddle = getSizeImage(sz_textleftmiddle);   
		}
		return textleftmiddle;
	}
	
	private static Bitmap textlefttop;
	private static String sz_textlefttop = "text_left_top.png";
	public static Bitmap getTextLeftTop() {
		if (textlefttop == null) {
			textlefttop = getSizeImage(sz_textlefttop);   
		}
		return textlefttop;
	}
	
	private static Bitmap textleftbottom;
	private static String sz_textleftbottom = "text_left_bottom.png";
	public static Bitmap getTextLeftBottom() {
		if (textleftbottom == null) {
			textleftbottom = getSizeImage(sz_textleftbottom);   
		}
		return textleftbottom;
	}
	
	private static Bitmap textrightmiddle;
	private static String sz_textrightmiddle = "text_right_middle.png";
	public static Bitmap getTextRightMiddle() {
		if (textrightmiddle == null) {
			textrightmiddle = getSizeImage(sz_textrightmiddle);   
		}
		return textrightmiddle;
	}
	
	private static Bitmap textrightttop;
	private static String sz_textrighttop = "text_right_top.png";
	public static Bitmap getTextRightTop() {
		if (textrightttop == null) {
			textrightttop = getSizeImage(sz_textrighttop);   
		}
		return textrightttop;
	}
	
	private static Bitmap textrightbottom;
	private static String sz_textrightbottom = "text_right_bottom.png";
	public static Bitmap getTextRightBottom() {
		if (textrightbottom == null) {
			textrightbottom = getSizeImage(sz_textrightbottom);   
		}
		return textrightbottom;
	}
	
	private static Bitmap listboxcentre;
	private static String sz_listboxcentre = "listbox_centre.png";
	public static Bitmap getListboxCentre() {
		if (listboxcentre == null) {
			listboxcentre = getSizeImage(sz_listboxcentre);   
		}
		return listboxcentre;
	}
	
	private static Bitmap listboxselcentre;
	private static String sz_listboxselcentre = "listbox_sel_centre.png";
	public static Bitmap getListboxSelCentre() {
		if (listboxselcentre == null) {
			listboxselcentre = getSizeImage(sz_listboxselcentre);   
		}
		return listboxselcentre;
	}
	
	private static Bitmap listboxleftedge;
	private static String sz_listboxleftedge = "listbox_left_edge.png";
	public static Bitmap getListboxLeftEdge() {
		if (listboxleftedge == null) {
			listboxleftedge = getSizeImage(sz_listboxleftedge);   
		}
		return listboxleftedge;
	}
	
	private static Bitmap listboxrightedge;
	private static String sz_listboxrightedge = "listbox_right_edge.png";
	public static Bitmap getListboxRightEdge() {
		if (listboxrightedge == null) {
			listboxrightedge = getSizeImage(sz_listboxrightedge);   
		}
		return listboxrightedge;
	}
	
	private static Bitmap listboxselrightedge;
	private static String sz_listboxselrightedge = "listbox_sel_right_edge.png";
	public static Bitmap getListboxSelRightEdge() {
		if (listboxselrightedge == null) {
			listboxselrightedge = getSizeImage(sz_listboxselrightedge);   
		}
		return listboxselrightedge;
	}
	
	private static Bitmap listboxselleftedge;
	private static String sz_listboxselleftedge = "listbox_sel_left_edge.png";
	public static Bitmap getListboxSelLeftEdge() {
		if (listboxselleftedge == null) {
			listboxselleftedge = getSizeImage(sz_listboxselleftedge);   
		}
		return listboxselleftedge;
	}
	
	private static Bitmap buttoncentre;
	private static String sz_buttoncentre = "button_centre.png";
	public static Bitmap getButtonCentre() {
		if (buttoncentre == null) {
			buttoncentre = getSizeImage(sz_buttoncentre);   
		}
		return buttoncentre;
	}
	
	private static Bitmap buttonleftedge;
	private static String sz_buttonleftedge = "button_left_edge.png";
	public static Bitmap getButtonLeftEdge() {
		if (buttonleftedge == null) {
			buttonleftedge = getSizeImage(sz_buttonleftedge);   
		}
		return buttonleftedge;
	}
	
	private static Bitmap buttonrightedge;
	private static String sz_buttonrightedge = "button_right_edge.png";
	public static Bitmap getButtonRightEdge() {
		if (buttonrightedge == null) {
			buttonrightedge = getSizeImage(sz_buttonrightedge);   
		}
		return buttonrightedge;
	}
	
	private static Bitmap buttonselcentre;
	private static String sz_buttonselcentre = "button_sel_centre.png";
	public static Bitmap getButtonSelCentre() {
		if (buttonselcentre == null) {
			buttonselcentre = getSizeImage(sz_buttonselcentre);   
		}
		return buttonselcentre;
	}
	
	private static Bitmap buttonselleftedge;
	private static String sz_buttonselleftedge = "button_sel_left_edge.png";
	public static Bitmap getButtonSelLeftEdge() {
		if (buttonselleftedge == null) {
			buttonselleftedge = getSizeImage(sz_buttonselleftedge);   
		}
		return buttonselleftedge;
	}
	
	private static Bitmap buttonselrightedge;
	private static String sz_buttonselrightedge = "button_sel_right_edge.png";
	public static Bitmap getButtonSelRightEdge() {
		if (buttonselrightedge == null) {
			buttonselrightedge = getSizeImage(sz_buttonselrightedge);   
		}
		return buttonselrightedge;
	}
	
	private static Bitmap background;
	private static String sz_background = "background.png";
	public static Bitmap getBackground() {
		if (background == null) {
			background = getSizeImage(sz_background);   
		}
		return background;
	}
	
	private static Bitmap logo;
	private static String sz_logo = "crosslogo.png";
	public static Bitmap getLogo() {
		if (logo == null) {
			logo = getSizeImage(sz_logo);   
		}
		return logo;
	}
	
	private static Bitmap logoleft;
	private static String sz_logoleft = "crosslogo_left.png";
	public static Bitmap getLogoLeft() {
		if (logoleft == null) {
			logoleft = getSizeImage(sz_logoleft);   
		}
		return logoleft;
	}
	private static Bitmap logoright;
	private static String sz_logoright = "crosslogo_right.png";
	public static Bitmap getLogoRight() {
		if (logoright == null) {
			logoright = getSizeImage(sz_logoright);   
		}
		return logoright;
	}
	
	private static Bitmap editboxlefttop;
	private static String sz_editboxlefttop = "editbox_left_top.png";
	public static Bitmap getEditboxLeftTop() {
		if (editboxlefttop == null) {
			editboxlefttop = getSizeImage(sz_editboxlefttop);   
		}
		return editboxlefttop;
	}
	private static Bitmap editboxleftmiddle;
	private static String sz_editboxleftmiddle = "editbox_left_middle.png";
	public static Bitmap getEditboxLeftMiddle() {
		if (editboxleftmiddle == null) {
			editboxleftmiddle = getSizeImage(sz_editboxleftmiddle);   
		}
		return editboxleftmiddle;
	}
	private static Bitmap editboxleftbottom;
	private static String sz_editboxleftbottom = "editbox_left_bottom.png";
	public static Bitmap getEditboxLeftBottom() {
		if (editboxleftbottom == null) {
			editboxleftbottom = getSizeImage(sz_editboxleftbottom);   
		}
		return editboxleftbottom;
	}
	
	private static Bitmap editboxrighttop;
	private static String sz_editboxrighttop = "editbox_right_top.png";
	public static Bitmap getEditboxRightTop() {
		if (editboxrighttop == null) {
			editboxrighttop = getSizeImage(sz_editboxrighttop);   
		}
		return editboxrighttop;
	}
	private static Bitmap editboxrightmiddle;
	private static String sz_editboxrightmiddle = "editbox_right_middle.png";
	public static Bitmap getEditboxRightMiddle() {
		if (editboxrightmiddle == null) {
			editboxrightmiddle = getSizeImage(sz_editboxrightmiddle);   
		}
		return editboxrightmiddle;
	}
	private static Bitmap editboxrightbottom;
	private static String sz_editboxrightbottom = "editbox_right_bottom.png";
	public static Bitmap getEditboxRightBottom() {
		if (editboxrightbottom == null) {
			editboxrightbottom = getSizeImage(sz_editboxrightbottom);   
		}
		return editboxrightbottom;
	}
	
	private static Bitmap editboxmiddletop;
	private static String sz_editboxmiddletop = "editbox_middle_top.png";
	public static Bitmap getEditboxMiddleTop() {
		if (editboxmiddletop == null) {
			editboxmiddletop = getSizeImage(sz_editboxmiddletop);   
		}
		return editboxmiddletop;
	}
	private static Bitmap editboxmiddlemiddle;
	private static String sz_editboxmiddlemiddle = "editbox_middle_middle.png";
	public static Bitmap getEditboxMiddleMiddle() {
		if (editboxmiddlemiddle == null) {
			editboxmiddlemiddle = getSizeImage(sz_editboxmiddlemiddle);   
		}
		return editboxmiddlemiddle;
	}
	private static Bitmap editboxmiddlebottom;
	private static String sz_editboxmiddlebottom = "editbox_middle_bottom.png";
	public static Bitmap getEditboxMiddleBottom() {
		if (editboxmiddlebottom == null) {
			editboxmiddlebottom = getSizeImage(sz_editboxmiddlebottom);   
		}
		return editboxmiddlebottom;
	}

	private static Bitmap editboxsellefttop;
	private static String sz_editboxsellefttop = "editbox_sel_left_top.png";
	public static Bitmap getEditboxSelLeftTop() {
		if (editboxsellefttop == null) {
			editboxsellefttop = getSizeImage(sz_editboxsellefttop);   
		}
		return editboxsellefttop;
	}
	private static Bitmap editboxselleftmiddle;
	private static String sz_editboxselleftmiddle = "editbox_sel_left_middle.png";
	public static Bitmap getEditboxSelLeftMiddle() {
		if (editboxselleftmiddle == null) {
			editboxselleftmiddle = getSizeImage(sz_editboxselleftmiddle);   
		}
		return editboxselleftmiddle;
	}
	private static Bitmap editboxselleftbottom;
	private static String sz_editboxselleftbottom = "editbox_sel_left_bottom.png";
	public static Bitmap getEditboxSelLeftBottom() {
		if (editboxselleftbottom == null) {
			editboxselleftbottom = getSizeImage(sz_editboxselleftbottom);   
		}
		return editboxselleftbottom;
	}
	
	private static Bitmap editboxselrighttop;
	private static String sz_editboxselrighttop = "editbox_sel_right_top.png";
	public static Bitmap getEditboxSelRightTop() {
		if (editboxselrighttop == null) {
			editboxselrighttop = getSizeImage(sz_editboxselrighttop);   
		}
		return editboxselrighttop;
	}
	private static Bitmap editboxselrightmiddle;
	private static String sz_editboxselrightmiddle = "editbox_sel_right_middle.png";
	public static Bitmap getEditboxSelRightMiddle() {
		if (editboxselrightmiddle == null) {
			editboxselrightmiddle = getSizeImage(sz_editboxselrightmiddle);   
		}
		return editboxselrightmiddle;
	}
	private static Bitmap editboxselrightbottom;
	private static String sz_editboxselrightbottom = "editbox_sel_right_bottom.png";
	public static Bitmap getEditboxSelRightBottom() {
		if (editboxselrightbottom == null) {
			editboxselrightbottom = getSizeImage(sz_editboxselrightbottom);   
		}
		return editboxselrightbottom;
	}
	
	private static Bitmap editboxselmiddletop;
	private static String sz_editboxselmiddletop = "editbox_sel_middle_top.png";
	public static Bitmap getEditboxSelMiddleTop() {
		if (editboxselmiddletop == null) {
			editboxselmiddletop = getSizeImage(sz_editboxselmiddletop);   
		}
		return editboxselmiddletop;
	}
	private static Bitmap editboxselmiddlemiddle;
	private static String sz_editboxselmiddlemiddle = "editbox_sel_middle_middle.png";
	public static Bitmap getEditboxSelMiddleMiddle() {
		if (editboxselmiddlemiddle == null) {
			editboxselmiddlemiddle = getSizeImage(sz_editboxselmiddlemiddle);   
		}
		return editboxselmiddlemiddle;
	}
	private static Bitmap editboxselmiddlebottom;
	private static String sz_editboxselmiddlebottom = "editbox_sel_middle_bottom.png";
	public static Bitmap getEditboxSelMiddleBottom() {
		if (editboxselmiddlebottom == null) {
			editboxselmiddlebottom = getSizeImage(sz_editboxselmiddlebottom);   
		}
		return editboxselmiddlebottom;
	}
	
	/*
	 * XML
	 */
	
	public static final String xml_result = "<result>";
	public static final int xml_result_length = xml_result.length();
	public static final String xml_result_end = "</result>";
	public static final int xml_error_end_length = xml_result_end.length();
	public static final String xml_usercategories = "<usercategories>";
	public static final int xml_usercategories_length = xml_usercategories.length(); 
	public static final String xml_usercategories_end = "</usercategories>";
	public static final int xml_usercategories_end_length = xml_usercategories_end.length();
	public static final String xml_albumid = "<albumid>";
	public static final int xml_albumid_length = xml_albumid.length();
	public static final String xml_albumid_end = "</albumid>";
	public static final int xml_albumid_end_length = xml_albumid_end.length();
	public static final String xml_hascards = "<hascards>";
	public static final int xml_hascards_length = xml_hascards.length();
	public static final String xml_hascards_end = "</hascards>";
	public static final int xml_hascards_end_length = xml_hascards_end.length();
	public static final String xml_albumname = "<albumname>";
	public static final int xml_albumname_length = xml_albumname.length();
	public static final String xml_albumname_end = "</albumname>";
	public static final int xml_albumname_end_length = xml_albumname_end.length();
	public static final String xml_album_end = "</album>";
	public static final int xml_album_end_length = xml_album_end.length();
	public static final String xml_userdetails = "<userdetails>";
	public static final int xml_userdetails_length = xml_userdetails.length();
	public static final String xml_userdetails_end = "</userdetails>";
	public static final int xml_userdetails_end_length = xml_userdetails_end.length();
	public static final String xml_email = "<email>";
	public static final int xml_email_length = xml_email.length();
	public static final String xml_email_end = "</email>";
	public static final int xml_email_end_length = xml_email_end.length();
	public static final String xml_credits = "<credits>";
	public static final int xml_credits_length = xml_credits.length();
	public static final String xml_credits_end = "</credits>";
	public static final int xml_credits_end_length = xml_credits_end.length();
	
	public static final String xml_cardsincategory = "<cardsincategory>";
	public static final int xml_cardsincategory_length = xml_cardsincategory.length();
	public static final String xml_cardsincategory_end = "</cardsincategory>";
	public static final int xml_cardsincategory_end_length = xml_cardsincategory_end.length();
	
	public static final String xml_card = "<card>";
	public static final int xml_card_length = xml_card.length();
	
	public static final String xml_cardid = "<cardid>";
	public static final int xml_cardid_length = xml_cardid.length();
	public static final String xml_cardid_end = "</cardid>";
	public static final int xml_cardid_end_length = xml_cardid_end.length();
	public static final String xml_description = "<description>";
	public static final int xml_description_length = xml_description.length();
	public static final String xml_description_end = "</description>";
	public static final int xml_description_end_length = xml_description_end.length();
	public static final String xml_quantity = "<quantity>";
	public static final int xml_quantity_length = xml_quantity.length();
	public static final String xml_quantity_end = "</quantity>";
	public static final int xml_quantity_end_length = xml_quantity_end.length();
	public static final String xml_thumburl = "<thumburl>";
	public static final int xml_thumburl_length = xml_thumburl.length();
	public static final String xml_thumburl_end = "</thumburl>";
	public static final int xml_thumburl_end_length = xml_thumburl_end.length();
	public static final String xml_fronturl = "<fronturl>";
	public static final int xml_fronturl_length = xml_fronturl.length();
	public static final String xml_fronturl_end = "</fronturl>";
	public static final int xml_fronturl_end_length = xml_fronturl_end.length();
	public static final String xml_backurl = "<backurl>";
	public static final int xml_backurl_length = xml_backurl.length();
	public static final String xml_backurl_end = "</backurl>";
	public static final int xml_backurl_end_length = xml_backurl_end.length();
	public static final String xml_note = "<note>";
	public static final int xml_note_length = xml_note.length();
	public static final String xml_note_end = "</note>";
	public static final int xml_note_end_length = xml_note_end.length();
	public static final String xml_updated = "<updated>";
	public static final int xml_updated_length = xml_updated.length();
	public static final String xml_updated_end = "</updated>";
	public static final int xml_updated_end_length = xml_updated_end.length();
	public static final String xml_stats = "<stats>";
	public static final int xml_stats_length = xml_stats.length();
	public static final String xml_stats_end = "</stats>";
	public static final int xml_stats_end_length = xml_stats_end.length();
	public static final String xml_stat = "<stat "; //(attribute = description, value = value);
	public static final int xml_stat_length = xml_stat.length();
	public static final String xml_stat_end = "</stat>";
	public static final int xml_stat_end_length = xml_stat_end.length();
	public static final String xml_card_end = "</card>";
	public static final int xml_card_end_length = xml_card_end.length();
	
	public static final String xml_val = "\">";
	public static final int xml_val_length = xml_val.length();
	public static final String xml_desc = "desc=\"";
	public static final int xml_desc_length = xml_desc.length();
	public static final String xml_ival = "ival=\"";
	public static final int xml_ival_length = xml_ival.length();
	public static final String xml_end = "\"";
	public static final int xml_end_length = xml_end.length();
	
	public static String removeSpaces(String s) {
	   s = s.replace('(', ' ').replace(')', ' ').replace('#', ' ').replace('*', ' ');
	   s = s.replace('-', ' ');
	   
	   byte[] tmp = s.getBytes();
	   int count = 0;
	   for (int i = 0; i < tmp.length; i++) {
		   if (tmp[i] == ' ') {} else {
			   count++;
		   }
	   }
	   byte[] ret = new byte[count];
	   count=0;
	   for (int i = 0; i < tmp.length; i++) {
		   if (tmp[i] == ' ') {} else {
				   ret[count++] = tmp[i];
			   }
		   }
		   return (new String(ret).trim());
	}
}

