package net.mytcg.dex.ui;

import net.mytcg.dex.ui.custom.ColorLabelField;
import net.mytcg.dex.ui.custom.FixedButtonField;
import net.mytcg.dex.ui.custom.SexyEditField;
import net.mytcg.dex.util.Const;
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
import net.rim.device.api.ui.component.ObjectChoiceField;
import net.rim.device.api.ui.container.HorizontalFieldManager;

public class RegistrationScreen extends AppScreen implements FieldChangeListener
{
	FixedButtonField register = new FixedButtonField(Const.register);
	FixedButtonField exit = new FixedButtonField(Const.exit);
	
	SexyEditField fullname = new SexyEditField("");
	SexyEditField username = new SexyEditField("", EditField.FILTER_URL, 36);
	SexyEditField cell = new SexyEditField(Const.getWidth()-12,Const.getButtonHeight(), EditField.FILTER_NUMERIC, 36);
	SexyEditField email = new SexyEditField("", EditField.FILTER_EMAIL, 36);
	SexyEditField password = new SexyEditField("");
	ColorLabelField terms = new ColorLabelField(" Terms and Conditions  ", LabelField.FIELD_VCENTER);
	CheckboxField termsBox = new CheckboxField(){protected void drawFocus(Graphics g, boolean x) {} };
	String numberCodes [] =
	{"+7840","+7940","+99544","+93", "+358","+355", "+213", "+1684", "+376", "+244", "+1264","+1268", "+54", "+374", "+297","+247", "+61", "+672", 
	"+43","+994","+1242","+973","+880","+1246", "+1268","+375","+32","+501","+229","+1441", "+975", "+591", "+5997","+387","+267","+55","+246", 
	"+1284","+673","+359", "+226","+257","+855","+257","+1", "+238","+5993","+5994","+5997", "+1345","+236","+235","+64","+56","+86","+61", "+61",
	"+57", "+269","+242", "+243","+682", "+506","+225", "+385", "+53", "+5399", "+5999", "+357", "+420", "+45", "+246","+253", "+1767", "+1809", 
	"+1829", "+1849","+670", "+56", "+593", "+20", "+503", "+8812", "+8813", "+88213", "+240","+291","+372","+251","+500","+298", "+679", "+358", 
	"+33", "+596", "+594", "+689", "+241", "+220", "+995", "+49", "+233", "+350","+881", "+8818", "+8819", "+30", "+299", "+1473", "+590", "+1671", 
	"+502", "+44", "+224", "+245", "+592", "+509","+39066", "+379", "+504", "+852", "+36", "+354", "+8810","+8811", "+91","+62","+870","+800", "+808", 
	"+98", "+964", "+353", "+8816", "+8817", "+44", "+972", "+39", "+1876", "+81", "+44","962", "+76", "+77", "+686", "+850", "+82", "+965", "+996", 
	"+856", "+371", "+961", "+266", "+231", "+218", "+423", "+370", "+352", "+853", "+389", "+261", "+256", "+60", "+960", "+223", "+356", "+692", 
	"+596", "+222", "+230", "+262", "+52", "+691", "+1808", "+373", "+377", "+976","+382", "+1664","+212", "+258", "+95", "+264", "+674","+977","+31", 
	"+1869", "+687","+64", "+505", "+227", "+234", "+683", "+672", "+1670", "+47", "+968", "+92", "+680","+970", "+507", "+675", "+595", "+51", "+63", 
	"+48", "+351", "+1787","+1939", "+974", "+262", "+40", "+7", "+250", "+5994", "+590", "+290", "+1869", "+1758", "+590", "+508", "+1784", "+685", 
	"+378", "+239", "+966","+221", "+381", "+248", "+232", "+65", "+5993", "+1721", "+421", "+386", "+677", "+252", "+27", "+500", "+211", "+34", 
	"+94", "+249", "+597", "+47", "+268", "+46", "+41", "+963", "+886", "+992", "+255", "+66", "+88216", "+228", "+690","+676", "+1868", "+216", "+90",
	"+993","+1649","+688","+256","+380","+971","+44","+1", "+878","+598","+998", "+678","+58", "+39066", "+379", "+84","+1340","+1808","+681", "+967", 
	"+260", "+255", "+263"};
	String countryNames[] =
	{"+7840  Abkhazia","+7940  Abkhazia","+99544  Abkhazia","+93  Afghanistan","+358  Aland Islands","+355  Albania" ,"+213  Algeria",
	"+1684  American Samoa","+376  Andorra","+244  Angola","+1264  Anguilla","+1268  Antigua and Barbuda","+54  Argentina","+374  Armenia",
	"+297  Aruba","+247  Ascension","+61  Australia","+672  Australian External Territories","+43  Austria","+994  Azerbaijan","+1242  Bahamas",
	"+973  Bahrain","+880  Bangladesh","+1246  Barbados","+1268  Barbuda","+375  Belarus","+32  Belgium","+501  Belize","+229  Benin",
	"+1441  Bermuda","+975  Bhutan","+591  Bolivia","+5997  Bonaire","+387  Bosnia and Herzegovina","+267  Botswana","+55  Brazil",
	"+246  British Indian Ocean Territory","+1284  British Virgin Islands","+673  Brunei Darussalam","+359  Bulgaria","+226  Burkina Faso",
	"+257  Burundi","+855  Cambodia","+257  Cameroon","+1  Canada","+238  Cape Verde","+5993  Caribbean Netherlands","+5994  Caribbean Netherlands",
	"+5997  Caribbean Netherlands","+1345  Cayman Islands","+236  Central African Republic","+235  Chad","+64  Chatham Island (New Zealand)",
	"+56  Chile","+86  China","+61  Christmas Island","+61  Cocos (Keeling) Islands","+57  Colombia","+269  Comoros","+242  Congo (Brazzaville)",
	"+243  Congo, The Democratic Republic","+682  Cook Islands","+506  Costa Rica","+225  Côte d'Ivoire","+385  Croatia","+53  Cuba",
	"+5399  Cuba (Guantanamo Bay)","+5999  Curaçao","+357  Cyprus","+420  Czech Republic","+45  Denmark","+246  Diego Garcia","+253  Djibouti",
	"+1767  Dominica","+1809  Dominican Republic","+1829  Dominican Republic","+1849  Dominican Republic","+670  East Timor","+56  Easter Island",
	"+593  Ecuador","+20  Egypt","+503  El Salvador","+8812  Ellipso (Mobile Satellite service)","+8813  Ellipso (Mobile Satellite service)",
	"+88213  EMSAT (Mobile Satellite service)","+240  Equatorial Guinea","+291  Eritrea","+372  Estonia","+251  Ethiopia","+500  Falkland Islands (Malvinas)",
	"+298  Faroe Islands","+679  Fiji","+358  Finland","+33  France","+596  French Antilles","+594  French Guiana","+689  French Polynesia",
	"+241  Gabon","+220  Gambia","+995  Georgia","+49  Germany","+233  Ghana","+350  Gibraltar","+881  Global Mobile Satellite System (GMSS)",
	"+8818  Globalstar (Mobile Satellite Service)","+8819  Globalstar (Mobile Satellite Service)","+30  Greece","+299  Greenland","+1473  Grenada",
	"+590  Guadeloupe","+1671  Guam","+502  Guatemala","+44  Guernsey","+224  Guinea","+245  Guinea-Bissau","+592  Guyana","+509  Haiti",
	"+39066  Holy See (Vatican City State)","+379  Holy See (Vatican City State)","+504  Honduras","+852  Hong Kong","+36  Hungary","+354  Iceland",
	"+8810  ICO Global (Mobile Satellite Service)","+8811  ICO Global (Mobile Satellite Service)","+91  India","+62  Indonesia","+870  Inmarsat SNAC",
	"+800  International Freephone Service","+808  International Shared Cost Service (ISCS)","+98  Iran","+964  Iraq","+353  Ireland",
	"+8816  Iridium (Mobile Satellite service)","+8817  Iridium (Mobile Satellite service)","+44  Isle of Man","+972  Israel","+39  Italy",
	"+1876  Jamaica","+81  Japan","+44  Jersey","+962  Jordan","+76  Kazakhstan","+77  Kazakhstan","+686  Kiribati","+850  North Korea","+82  South Korea",
	"+965  Kuwait","+996  Kyrgyzstan","+856  Laos","+371  Latvia","+961  Lebanon","+266  Lesotho","+231  Liberia","+218  Libya","+423  Liechtenstein",
	"+370  Lithuania","+352  Luxembourg","+853  Macau","+389  Macedonia","+261  Madagascar","+256  Malawi","+60  Malaysia","+960  Maldives",
	"+223  Mali","+356  Malta","+692  Marshall Islands","+596  Martinique","+222  Mauritania","+230  Mauritius","+262  Mayotte","+52  Mexico",
	"+691  Micronesia, Federated States of","+1808  Midway Island","+373  Moldova","+377  Monaco","+976  Mongolia","+382  Montenegro","+1664  Montserrat",
	"+212  Morocco","+258  Mozambique","+95  Myanmar","+264  Namibia","+674  Nauru","+977  Nepal","+31  Netherlands","+1869  Nevis","+687  New Caledonia",
	"+64  New Zealand","+505  Nicaragua","+227  Niger","+234  Nigeria","+683  Niue","+672  Norfolk Island","+1670  Northern Mariana Islands",
	"+47  Norway","+968  Oman","+92  Pakistan","+680  Palau","+970  Palestinian territories","+507  Panama","+675  Papua New Guinea","+595  Paraguay","+51  Peru",
	"+63  Philippines","+48  Poland","+351  Portugal","+1787  Puerto Rico","+1939  Puerto Rico","+974  Qatar","+262  Réunion","+40  Romania",
	"+7  Russia","+250  Rwanda","+5994  Saba","+590  Saint Barthélemy","+290  Saint Helena and Tristan da Cunha","+1869  Saint Kitts and Nevis",
	"+1758  Saint Lucia","+590  Saint Martin (French)","+508  Saint Pierre and Miquelon","+1784  Saint Vincent and the Grenadines","+685  Samoa",
	"+378  San Marino","+239  Sao Tomé and Príncipe","+966  Saudi Arabia","+221  Senegal","+381  Serbia","+248  Seychelles","+232  Sierra Leone",
	"+65  Singapore","+5993  Sint Eustatius","+1721  Sint Maarten (Dutch)","+421  Slovakia","+386  Slovenia","+677  Solomon Islands","+252  Somalia",
	"+27  South Africa","+500  South Georgia and the South Sandwich Islands","+211  South Sudan","+34  Spain","+94  Sri Lanka","+249  Sudan",
	"+597  Suriname","+47  Svalbard and Jan Mayen","+268  Swaziland","+46  Sweden","+41  Switzerland","+963  Syria","+886  Taiwan","+992  Tajikistan",
	"+255  Tanzania","+66  Thailand","+88216  Thuraya (Mobile Satellite service)","+228  Togo","+690  Tokelau","+676  Tonga","+1868  Trinidad and Tobago",
	"+216  Tunisia","+90  Turkey","+993  Turkmenistan","+1649  Turks and Caicos Islands","+688  Tuvalu","+256  Uganda","+380  Ukraine",
	"+971  United Arab Emirates","+44  United Kingdom","+1  United States","+878  Universal Personal Telecommunications","+598  Uruguay",
	"+998  Uzbekistan","+678  Vanuatu","+58  Venezuela","+39066  Vatican City State","+379  Vatican City State","+84  Vietnam","+1340  Virgin Islands, US",
	"+1808  Wake Island","+681  Wallis and Futuna","+967  Yemen","+260  Zambia","+255  Zanzibar","+263  Zimbabwe"};
	String countryNamesClean [] =
	{"Abkhazia","Abkhazia","Abkhazia","Afghanistan","Aland Islands","Albania" ,"Algeria", "American Samoa","Andorra","Angola","Anguilla",
	"Antigua and Barbuda","Argentina", "Armenia", "Aruba","Ascension","Australia","Australian External Territories","Austria","Azerbaijan","Bahamas", "Bahrain", 
	"Bangladesh","Barbados","Barbuda","Belarus","Belgium", "Belize","Benin","Bermuda","Bhutan", "Bolivia", "Bonaire", "Bosnia and Herzegovina", 
	"Botswana","Brazil", "British Indian Ocean Territory","British Virgin Islands", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi","Cambodia",
	"Cameroon","Canada", "Cape Verde","Caribbean Netherlands","Caribbean Netherlands","Caribbean Netherlands","Cayman Islands","Central African Republic", 
	"Chad","Chatham Island New Zealand","Chile","China","Christmas Island","Cocos Keeling Islands","Colombia", "Comoros", "Congo Brazzaville", 
	"Congo, The Democratic Republic", "Cook Islands","Costa Rica", "Côte d'Ivoire", "Croatia", "Cuba", "Cuba Guantanamo Bay", "Curaçao", 
	"Cyprus", "Czech Republic", "Denmark", "Diego Garcia", "Djibouti", "Dominica", "Dominican Republic", "Dominican Republic", "Dominican Republic", 
	"East Timor","Easter Island", "Ecuador", "Egypt", "El Salvador", "Ellipso Mobile Satellite service", "Ellipso Mobile Satellite service", 
	"EMSAT Mobile Satellite service", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands Malvinas", "Faroe Islands", "Fiji", 
	"Finland", "France", "French Antilles", "French Guiana", "French Polynesia", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", 
	"Global Mobile Satellite System GMSS", "Globalstar Mobile Satellite Service", "Globalstar Mobile Satellite Service", "Greece", "Greenland", 
	"Grenada", "Guadeloupe", "Guam", "Guatemala", "Guernsey", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Holy See Vatican City State", 
	"Holy See Vatican City State", "Honduras", "Hong Kong", "Hungary", "Iceland", "ICO Global Mobile Satellite Service", "ICO Global Mobile Satellite Service", 
	"India", "Indonesia", "Inmarsat SNAC", "International Freephone Service", "International Shared Cost Service ISCS", "Iran", "Iraq", "Ireland", 
	"Iridium Mobile Satellite service", "Iridium Mobile Satellite service", "Isle of Man", "Israel", "Italy", "Jamaica", "Japan","Jersey", "Jordan", 
	"Kazakhstan","Kazakhstan","Kiribati", "North Korea", "South Korea", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", 
	"Libya", "Liechtenstein","Lithuania", "Luxembourg", "Macau", "Macedonia", "Madagascar", "Malawi","Malaysia", "Maldives", "Mali", "Malta", 
	"Marshall Islands", "Martinique", "Mauritania","Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Midway Island","Moldova", 
	"Monaco", "Mongolia", "Montenegro", "Montserrat","Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Nevis", 
	"New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", 
	"Palau", "Palestinian territories", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Puerto Rico", "Puerto Rico", 
	"Qatar", "Réunion", "Romania", "Russia", "Rwanda", "Saba", "Saint Barthélemy", "Saint Helena and Tristan da Cunha", "Saint Kitts and Nevis", 
	"Saint Lucia", "Saint Martin French", "Saint Pierre and Miquelon", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tomé and Príncipe", 
	"Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Sint Eustatius", "Sint Maarten Dutch", "Slovakia", "Slovenia", 
	"Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "South Sudan", "Spain", "Sri Lanka", "Sudan", 
	"Suriname", "Svalbard and Jan Mayen", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", 
	"Thuraya Mobile Satellite service", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", 
	"Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States","Universal Personal Telecommunications ", "Uruguay", 
	"Uzbekistan", "Vanuatu", "Venezuela", "Vatican City State", "Vatican City State", "Vietnam", "Virgin Islands, US", "Wake Island", "Wallis and Futuna", 
	"Yemen","Zambia", "Zanzibar","Zimbabwe"};
	String countryCodes [] =
	{"","","","AF","AX","AL","DZ","AS","AD","AO","AI","AG","AR","AM","AW","","AU","","AT","AZ","BS","BH","BD","BB","","BY","BE","BZ","BJ","BM","BT",
	"BO","","BA","BW","BR","IO","","BN","BG","BF","BI","KH","CM","CA","CV","BQ","BQ","BQ","KY","CF","TD","","CL","CN","CX","CC","CO","KM","CG","CD",
	"CK","CR","CI","HR","CU","CU","CW","CY","CZ","DK","","DJ","DM","DO","DO","DO","","","EC","EG","SV","","","","GQ","ER","EE","ET","FK","FO","FJ",
	"FI","FR","TF","GF","PF","GA","GM","GE","DE","GH","GI","","","","GR","GL","GD","GP","GU","GT","GG","GN","GW","GY","HT","","","HN","HK","HU","IS",
	"","","IN","ID","","","","IR","IQ","IE","","","","IL","IT","JM","JP","JE","JO","KZ","KZ","KI","KP","KR","KW","KG","LA","LV","LB","LS","LR","LY",
	"LI","LT","LU","MO","MK","MG","MW","MY","MV","ML","MT","MH","MQ","MR","MU","YT","MX","FM","","MD","MC","MN","ME","MS","MA","MZ","MM","NA","NR",
	"NP","NL","","NC","NZ","NI","NE","NG","NU","NF","MP","NO","OM","PK","PW","PS","PA","PG","PY","PE","PH","PL","PT","PR","PR","QA","RE","RO","RU",
	"RW","","BL","SH","KN","LC","MF","PM","VC","WS","SM","ST","SA","SN","RS","SC","SL","SG","","SX","SK","SI","SB","SO","ZA","GS","SS","ES","LK","SD",
	"SR","SJ","SZ","SE","CH","SY","TW","TJ","TZ","TH","","TG","TK","TO","TT","TN","TR","TM","TC","TV","UG","UA","AE","GB","US","","UY","UZ","VU","VE",
	"VA","VA","VN","VI","","WF","YE","ZM","","ZW"};
	ObjectChoiceField country = new ObjectChoiceField("",countryNames,0,ObjectChoiceField.FIELD_LEFT);
	
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
		} else if (((fromIndex = val.indexOf(Const.xml_country_code)) != -1)) {
    		String countryCode="";
    		if ((fromIndex = val.indexOf(Const.xml_country_code)) != -1) {
    			countryCode = val.substring(fromIndex+Const.xml_country_code_length, val.indexOf(Const.xml_country_code_end, fromIndex));
			}
    		for(int i = 0; i < countryCodes.length; i++){
    			if(countryCode.equals(countryCodes[i])){
    				synchronized(UiApplication.getEventLock()) {
    					country.setSelectedIndex(i);
    				}
    				break;
    			}
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
		add(new ColorLabelField(" Country"));
		add(country);
		add(new ColorLabelField(Const.cell));
		HorizontalFieldManager numM = new HorizontalFieldManager();
		add(numM);
		numM.add(new ColorLabelField("+", LabelField.FIELD_VCENTER));
		numM.add(cell);
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
		doConnect("checkcountry=1");
	}
	
	public void fieldChanged(Field f, int i) {
		if (f == exit) {
			System.exit(0);
		} else if (f == terms) {
			BrowserSession browserSession = Browser.getDefaultSession();
			browserSession.displayPage("http://www.mobidex.biz/terms");
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
			} else if (!termsBox.getChecked()){
				setText("You need to accept our terms and conditions.");
			} else {
				int countryIndex = -1;
				//System.out.println("numberCodes[k] "+numberCodes[country.getSelectedIndex()]+"   "+cell.getText().substring(0, numberCodes[country.getSelectedIndex()].length()));
				if (cell.getText().length() >= numberCodes[country.getSelectedIndex()].length() + 5) {
					if(numberCodes[country.getSelectedIndex()].equals("+"+cell.getText().substring(0, numberCodes[country.getSelectedIndex()].length()-1))){
						countryIndex = country.getSelectedIndex();
					} else {
						setText("The number you entered does not match the international cellphone number code you have selected.");
						return;
					}
				} else {
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
					country64 = new String(Base64OutputStream.encode(countryNamesClean[countryIndex].getBytes(), 0, countryNamesClean[countryIndex].length(), false, false), "UTF-8");
				} catch (Exception e) {};
				String url = Const.registeruser+Const.username+full64+Const.userfullname+username.getText()+Const.usercell+number64+Const.useremail+email.getText()+Const.userpassword+password.getText()+Const.country+country64;
				url = Const.removeSpaces(url);
				doConnect(url, false);
			}
		}
	}
}