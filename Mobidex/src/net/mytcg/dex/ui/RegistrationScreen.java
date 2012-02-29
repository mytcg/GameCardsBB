package net.mytcg.dex.ui;

import net.mytcg.dex.ui.custom.ColorLabelField;
import net.mytcg.dex.ui.custom.FixedButtonField;
import net.mytcg.dex.ui.custom.SexyEditField;
import net.mytcg.dex.util.Const;
import net.mytcg.dex.util.Country;
import net.mytcg.dex.util.SettingsBean;
import net.rim.blackberry.api.browser.Browser;
import net.rim.blackberry.api.browser.BrowserSession;
import net.rim.device.api.io.Base64OutputStream;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.CheckboxField;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.HorizontalFieldManager;

public class RegistrationScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField register = new FixedButtonField(Const.register);
	FixedButtonField exit = new FixedButtonField(Const.exit);
	
	SexyEditField fullname = new SexyEditField("");
	SexyEditField username = new SexyEditField("", EditField.FILTER_URL, 36);
	SexyEditField cell = new SexyEditField("", EditField.FILTER_PHONE, 36);
	SexyEditField email = new SexyEditField("", EditField.FILTER_EMAIL, 36);
	SexyEditField password = new SexyEditField("");
	ColorLabelField terms = new ColorLabelField(" Terms and Conditions  ", LabelField.FIELD_VCENTER);
	CheckboxField termsBox = new CheckboxField(){protected void drawFocus(Graphics g, boolean x) {} };
	Country countries [] =
	{
	 new Country("+7840", "Abkhazia") ,
	 new Country ("+7940", "Abkhazia"),
	 new Country ("+99544", "Abkhazia" ),
	 new Country("+93", "Afghanistan" ),
	 new Country( "+358", "Aland Islands" ),
	 new Country("+355", "Albania" ),
	 new Country("+213", "Algeria" ),
	 new Country( "+1684", "American Samoa" ),
	 new Country( "+376", "Andorra" ),
	 new Country( "+244", "Angola" ),
	 new Country( "+1264", "Anguilla" ),
	 new Country( "+1268", "Antigua and Barbuda" ),
	 new Country( "+54", "Argentina" ),
	 new Country( "+374", "Armenia" ),
	 new Country( "+297", "Aruba" ),
	 new Country( "+247", "Ascension" ),
	 new Country( "+61", "Australia" ),
	 new Country( "+672", "Australian External Territories" ),
	 new Country( "+43", "Austria" ),
	 new Country( "+994", "Azerbaijan" ),
	 new Country( "+1242", "Bahamas" ),
	 new Country( "+973", "Bahrain" ),
	 new Country( "+880", "Bangladesh" ),
	 new Country( "+1246", "Barbados" ),
	 new Country( "+1268", "Barbuda" ),
	 new Country( "+375", "Belarus" ),
	 new Country( "+32", "Belgium" ),
	 new Country( "+501", "Belize" ),
	 new Country( "+229", "Benin" ),
	 new Country( "+1441", "Bermuda" ),
	 new Country( "+975", "Bhutan" ),
	 new Country( "+591", "Bolivia" ),
	 new Country( "+5997", "Bonaire" ),
	 new Country( "+387", "Bosnia and Herzegovina" ),
	 new Country( "+267", "Botswana" ),
	 new Country( "+55", "Brazil" ),
	 new Country( "+246", "British Indian Ocean Territory" ),
	 new Country( "+1284", "British Virgin Islands" ),
	 new Country( "+673", "Brunei Darussalam" ),
	 new Country( "+359", "Bulgaria" ),
	 new Country( "+226", "Burkina Faso" ),
	 new Country( "+257", "Burundi" ),
	 new Country( "+855", "Cambodia" ),
	 new Country( "+257", "Cameroon" ),
	 new Country( "+1", "Canada" ),
	 new Country( "+238", "Cape Verde" ),
	 new Country( "+5993", "Caribbean Netherlands" ),
	 new Country( "+5994", "Caribbean Netherlands" ),
	 new Country( "+5997", "Caribbean Netherlands" ),
	 new Country( "+1345", "Cayman Islands" ),
	 new Country( "+236", "Central African Republic" ),
	 new Country( "+235", "Chad" ),
	 new Country( "+64", "Chatham Island (New Zealand)" ),
	 new Country( "+56", "Chile" ),
	 new Country( "+86", "China" ),
	 new Country( "+61", "Christmas Island" ),
	 new Country( "+61", "Cocos (Keeling) Islands" ),
	 new Country( "+57", "Colombia" ),
	 new Country( "+269", "Comoros" ),
	 new Country( "+242", "Congo (Brazzaville)" ),
	 new Country( "+243", "Congo, The Democratic Republic of the (Zaire)" ),
	 new Country( "+682", "Cook Islands" ),
	 new Country( "+506", "Costa Rica" ),
	 new Country( "+225", "Côte d'Ivoire" ),
	 new Country( "+385", "Croatia" ),
	 new Country( "+53", "Cuba" ),
	 new Country( "+5399", "Cuba (Guantanamo Bay)" ),
	 new Country( "+5999", "Curaçao" ),
	 new Country( "+357", "Cyprus" ),
	 new Country( "+420", "Czech Republic" ),
	 new Country( "+45", "Denmark" ),
	 new Country( "+246", "Diego Garcia" ),
	 new Country( "+253", "Djibouti" ),
	 new Country( "+1767", "Dominica" ),
	 new Country( "+1809", "Dominican Republic" ),
	 new Country( "+1829", "Dominican Republic" ),
	 new Country( "+1849", "Dominican Republic" ),
	 new Country( "+670", "East Timor" ),
	 new Country( "+56", "Easter Island" ),
	 new Country( "+593", "Ecuador" ),
	 new Country( "+20", "Egypt" ),
	 new Country( "+503", "El Salvador" ),
	 new Country( "+8812", "Ellipso (Mobile Satellite service)" ),
	 new Country( "+8813", "Ellipso (Mobile Satellite service)" ),
	 new Country( "+88213", "EMSAT (Mobile Satellite service)" ),
	 new Country( "+240", "Equatorial Guinea" ),
	 new Country( "+291", "Eritrea" ),
	 new Country( "+372", "Estonia" ),
	 new Country( "+251", "Ethiopia" ),
	 new Country( "+500", "Falkland Islands (Malvinas)" ),
	 new Country( "+298", "Faroe Islands" ),
	 new Country( "+679", "Fiji" ),
	 new Country( "+358", "Finland" ),
	 new Country( "+33", "France" ),
	 new Country( "+596", "French Antilles" ),
	 new Country( "+594", "French Guiana" ),
	 new Country( "+689", "French Polynesia" ),
	 new Country( "+241", "Gabon" ),
	 new Country( "+220", "Gambia" ),
	 new Country( "+995", "Georgia" ),
	 new Country( "+49", "Germany" ),
	 new Country( "+233", "Ghana" ),
	 new Country( "+350", "Gibraltar" ),
	 new Country( "+881", "Global Mobile Satellite System (GMSS)" ),
	 new Country( "+8818", "Globalstar (Mobile Satellite Service)" ),
	 new Country( "+8819", "Globalstar (Mobile Satellite Service)" ),
	 new Country( "+30", "Greece" ),
	 new Country( "+299", "Greenland" ),
	 new Country( "+1473", "Grenada" ),
	 new Country( "+590", "Guadeloupe" ),
	 new Country( "+1671", "Guam" ),
	 new Country( "+502", "Guatemala" ),
	 new Country( "+44", "Guernsey" ),
	 new Country( "+224", "Guinea" ),
	 new Country( "+245", "Guinea-Bissau" ),
	 new Country( "+592", "Guyana" ),
	 new Country( "+509", "Haiti" ),
	 new Country( "+39066", "Holy See (Vatican City State)" ),
	 new Country( "+379", "Holy See (Vatican City State)" ),
	 new Country( "+504", "Honduras" ),
	 new Country( "+852", "Hong Kong" ),
	 new Country( "+36", "Hungary" ),
	 new Country( "+354", "Iceland" ),
	 new Country( "+8810", "ICO Global (Mobile Satellite Service)" ),
	 new Country( "+8811", "ICO Global (Mobile Satellite Service)" ),
	 new Country( "+91", "India" ),
	 new Country( "+62", "Indonesia" ),
	 new Country( "+870", "Inmarsat SNAC" ),
	 new Country( "+800", "International Freephone Service" ),
	 new Country( "+808", "International Shared Cost Service (ISCS)" ),
	 new Country( "+98", "Iran" ),
	 new Country( "+964", "Iraq" ),
	 new Country( "+353", "Ireland" ),
	 new Country( "+8816", "Iridium (Mobile Satellite service)" ),
	 new Country( "+8817", "Iridium (Mobile Satellite service)" ),
	 new Country( "+44", "Isle of Man" ),
	 new Country( "+972", "Israel" ),
	 new Country( "+39", "Italy" ),
	 new Country( "+1876", "Jamaica" ),
	 new Country( "+81", "Japan" ),
	 new Country( "+44", "Jersey" ),
	 new Country( "962", "Jordan" ),
	 new Country( "+76", "Kazakhstan" ),
	 new Country( "+77", "Kazakhstan" ),
	 new Country( "+686", "Kiribati" ),
	 new Country( "+850", "North Korea" ),
	 new Country( "+82", "South Korea" ),
	 new Country( "+965", "Kuwait" ),
	 new Country( "+996", "Kyrgyzstan" ),
	 new Country( "+856", "Laos" ),
	 new Country( "+371", "Latvia" ),
	 new Country( "+961", "Lebanon" ),
	 new Country( "+266", "Lesotho" ),
	 new Country( "+231", "Liberia" ),
	 new Country( "+218", "Libya" ),
	 new Country( "+423", "Liechtenstein" ),
	 new Country( "+370", "Lithuania" ),
	 new Country( "+352", "Luxembourg" ),
	 new Country( "+853", "Macau" ),
	 new Country( "+389", "Macedonia" ),
	 new Country( "+261", "Madagascar" ),
	 new Country( "+256", "Malawi" ),
	 new Country( "+60", "Malaysia" ),
	 new Country( "+960", "Maldives" ),
	 new Country( "+223", "Mali" ),
	 new Country( "+356", "Malta" ),
	 new Country( "+692", "Marshall Islands" ),
	 new Country( "+596", "Martinique" ),
	 new Country( "+222", "Mauritania" ),
	 new Country( "+230", "Mauritius" ),
	 new Country( "+262", "Mayotte" ),
	 new Country( "+52", "Mexico" ),
	 new Country( "+691", "Micronesia, Federated States of" ),
	 new Country( "+1808", "Midway Island" ),
	 new Country( "+373", "Moldova" ),
	 new Country( "+377", "Monaco" ),
	 new Country( "+976", "Mongolia" ),
	 new Country( "+382", "Montenegro" ),
	 new Country( "+1664", "Montserrat" ),
	 new Country( "+212", "Morocco" ),
	 new Country( "+258", "Mozambique" ),
	 new Country( "+95", "Myanmar" ),
	 new Country( "+264", "Namibia" ),
	 new Country( "+674", "Nauru" ),
	 new Country( "+977", "Nepal" ),
	 new Country( "+31", "Netherlands" ),
	 new Country( "+1869", "Nevis" ),
	 new Country( "+687", "New Caledonia" ),
	 new Country( "+64", "New Zealand" ),
	 new Country( "+505", "Nicaragua" ),
	 new Country( "+227", "Niger" ),
	 new Country( "+234", "Nigeria" ),
	 new Country( "+683", "Niue" ),
	 new Country( "+672", "Norfolk Island" ),
	 new Country( "+1670", "Northern Mariana Islands"),
	 new Country( "+47", "Norway" ),
	 new Country( "+968", "Oman" ),
	 new Country( "+92", "Pakistan" ),
	 new Country( "+680", "Palau" ),
	 new Country( "+970", "Palestinian territories" ),
	 new Country( "+507", "Panama" ),
	 new Country( "+675", "Papua New Guinea" ),
	 new Country( "+595", "Paraguay" ),
	 new Country( "+51", "Peru" ),
	 new Country( "+63", "Philippines" ),
	 new Country( "+48", "Poland" ),
	 new Country( "+351", "Portugal" ),
	 new Country( "+1787", "Puerto Rico" ),
	 new Country( "+1939", "Puerto Rico" ),
	 new Country( "+974", "Qatar" ),
	 new Country( "+262", "Réunion" ),
	 new Country( "+40", "Romania" ),
	 new Country( "+7", "Russia" ),
	 new Country( "+250", "Rwanda" ),
	 new Country( "+5994", "Saba" ),
	 new Country( "+590", "Saint Barthélemy" ),
	 new Country( "+290", "Saint Helena and Tristan da Cunha" ),
	 new Country( "+1869", "Saint Kitts and Nevis" ),
	 new Country( "+1758", "Saint Lucia" ),
	 new Country( "+590", "Saint Martin (French)" ),
	 new Country( "+508", "Saint Pierre and Miquelon" ),
	 new Country( "+1784", "Saint Vincent and the Grenadines" ),
	 new Country( "+685", "Samoa" ),
	 new Country( "+378", "San Marino" ),
	 new Country( "+239", "São Tomé and Príncipe" ),
	 new Country( "+966", "Saudi Arabia" ),
	 new Country( "+221", "Senegal" ),
	 new Country( "+381", "Serbia" ),
	 new Country( "+248", "Seychelles" ),
	 new Country( "+232", "Sierra Leone" ),
	 new Country( "+65", "Singapore" ),
	 new Country( "+5993", "Sint Eustatius" ),
	 new Country( "+1721", "Sint Maarten (Dutch)" ),
	 new Country( "+421", "Slovakia" ),
	 new Country( "+386", "Slovenia" ),
	 new Country( "+677", "Solomon Islands" ),
	 new Country( "+252", "Somalia" ),
	 new Country( "+27", "South Africa" ),
	 new Country( "+500", "South Georgia and the South Sandwich Islands" ),
	 new Country( "+211", "South Sudan" ),
	 new Country( "+34", "Spain" ),
	 new Country( "+94", "Sri Lanka" ),
	 new Country( "+249", "Sudan" ),
	 new Country( "+597", "Suriname" ),
	 new Country( "+47", "Svalbard and Jan Mayen" ),
	 new Country( "+268", "Swaziland" ),
	 new Country( "+46", "Sweden" ),
	 new Country( "+41", "Switzerland" ),
	 new Country( "+963", "Syria" ),
	 new Country( "+886", "Taiwan" ),
	 new Country( "+992", "Tajikistan" ),
	 new Country( "+255", "Tanzania" ),
	 new Country( "+66", "Thailand" ),
	 new Country( "+88216", "Thuraya (Mobile Satellite service)" ),
	 new Country( "+228", "Togo" ),
	 new Country( "+690", "Tokelau" ),
	 new Country( "+676", "Tonga" ),
	 new Country( "+1868", "Trinidad and Tobago" ),
	 new Country( "+216", "Tunisia" ),
	 new Country( "+90", "Turkey" ),
	 new Country( "+993", "Turkmenistan" ),
	 new Country( "+1649", "Turks and Caicos Islands" ),
	 new Country( "+688", "Tuvalu" ),
	 new Country( "+256", "Uganda" ),
	 new Country( "+380", "Ukraine" ),
	 new Country( "+971", "United Arab Emirates" ),
	 new Country( "+44", "United Kingdom" ),
	 new Country( "+1", "United States" ),
	 new Country( "+878", "Universal Personal Telecommunications " ),
	 new Country( "+598", "Uruguay" ),
	 new Country( "+998", "Uzbekistan" ),
	 new Country( "+678", "Vanuatu" ),
	 new Country( "+58", "Venezuela" ),
	 new Country( "+39066", "Vatican City State" ),
	 new Country( "+379", "Vatican City State" ),
	 new Country( "+84", "Vietnam" ),
	 new Country( "+1340", "Virgin Islands, US" ),
	 new Country( "+1808", "Wake Island" ),
	 new Country( "+681", "Wallis and Futuna" ),
	 new Country( "+967", "Yemen" ),
	 new Country( "+260", "Zambia" ),
	 new Country( "+255", "Zanzibar" ),
	 new Country( "+263", "Zimbabwe" )
	};
	
	public boolean onClose() {
		System.exit(0);
		return true;
	}
	public void process(String val) {
		int fromIndex;
    	if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
    		super.process(val);
    	} else if (((fromIndex = val.indexOf(Const.xml_usercategories)) != -1)) {
    		SettingsBean _instance = SettingsBean.getSettings();
    		_instance.setAuthenticated(true);
    		_instance.setUsername(username.getText());
    		
    		String password64="";
			try {
				password64 = new String(Base64OutputStream.encode(password.getText().getBytes(), 0, password.getText().length(), false, false), "UTF-8");
			} catch (Exception e) {
				
			}
			_instance.setPassword(password64);
			
    		SettingsBean.saveSettings(_instance);
    		
    		synchronized(UiApplication.getEventLock()) {
				close();
				Const.GOTOSCREEN = Const.ALBUMSCREEN;
				Const.FROMSCREEN = Const.LOGINSCREEN;
	    		Const.app.nextScreen();
			}
		}
	}
	
	public RegistrationScreen()
	{
		super(null);
		
		SettingsBean _instance = SettingsBean.getSettings();
		_instance.setAuthenticated(false);
		SettingsBean.saveSettings(_instance);

		add(new ColorLabelField(Const.name));
		add(fullname);
		add(new ColorLabelField(Const.surname));
		add(username);
		add(new ColorLabelField(Const.cell));
		add(cell);
		add(new ColorLabelField(Const.age));
		add(email);
		add(new ColorLabelField(Const.gender));
		add(password);
		HorizontalFieldManager termsManager = new HorizontalFieldManager(Field.FIELD_HCENTER){public int getPreferredWidth() {return Const.getWidth();}};
		terms.setFocusable(true);
		add(termsManager);
		termsManager.add(terms);
		termsManager.add(termsBox);
		//bgManager.setStatusHeight(Const.getButtonHeight());
		
		terms.setChangeListener(this);
		termsBox.setChangeListener(this);
		exit.setChangeListener(this);
		register.setChangeListener(this);
		
		addButton(register);
		addButton(new FixedButtonField(""));
		addButton(exit);
	}
	
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			System.exit(0);
		} else if (f == terms) {
			BrowserSession browserSession = Browser.getDefaultSession();
			browserSession.displayPage("http://www.mobidex.biz/terms/terms.html");
			browserSession.showBrowser();
		} else if (f == register) {
			SettingsBean _instance = SettingsBean.getSettings();
			_instance.setUsername(cell.getText());
			SettingsBean.saveSettings(_instance);
			_instance = null;
			
			if ((cell.getText() == null)||(cell.getText().length() <= 0)) {
				setText("Cell Number cannot be blank.");
			} else if ((username.getText() == null)||username.getText().length() <= 0) {
				setText("Username cannot be blank.");
			} else if ((password.getText() == null)||password.getText().length() <= 0) {
				setText("Password cannot be blank.");
			} else if ((email.getText() == null)||email.getText().length() <= 0) {
				setText("Email cannot be blank.");
			} else if (cell.getText().length() < 10){
				setText("Your cell needs to be at least 10 numbers long.");
			} else if (!termsBox.getChecked()){
				setText("You need to accept our terms and conditions.");
			} else {
				int countryIndex = -1;
				for(int k = 0; k < countries.length; k++){
					System.out.println("countries[k].countryCode "+countries[k].countryCode+"   "+cell.getText().substring(0, countries[k].countryCode.length()));
					if (cell.getText().length() == countries[k].countryCode.length() + 9) {
						if(countries[k].countryCode.equals(cell.getText().substring(0, countries[k].countryCode.length()))){
							countryIndex = k;
							break;
						}
					}
				}
				if (countryIndex == -1) {
					setText("Please enter your cell number in the international format.");
					return;
				}
				String full64="";
				try {
					full64 = new String(Base64OutputStream.encode(fullname.getText().getBytes(), 0, fullname.getText().length(), false, false), "UTF-8");
				} catch (Exception e) {};
				String number64="";
				try {
					number64 = new String(Base64OutputStream.encode(cell.getText().getBytes(), 0, cell.getText().length(), false, false), "UTF-8");
				} catch (Exception e) {};
				String country64="";
				try {
					country64 = new String(Base64OutputStream.encode(countries[countryIndex].countryName.getBytes(), 0, countries[countryIndex].countryName.length(), false, false), "UTF-8");
				} catch (Exception e) {};
				String url = Const.registeruser+Const.username+full64+Const.userfullname+username.getText()+Const.usercell+number64+Const.useremail+email.getText()+Const.userpassword+password.getText()+Const.country+country64;
				url = Const.removeSpaces(url);
				doConnect(url, false);
			}
		}
	}
}