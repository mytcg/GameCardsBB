package net.mytcg.topcar.http;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.io.file.FileConnection;

import net.mytcg.topcar.ui.AppScreen;
import net.mytcg.topcar.ui.GameCardsHome;
import net.mytcg.topcar.ui.custom.HorizontalGamePlayManager;
import net.mytcg.topcar.ui.custom.HorizontalStatManager;
import net.mytcg.topcar.ui.custom.ImageField;
import net.mytcg.topcar.ui.custom.ImageLoader;
import net.mytcg.topcar.ui.custom.ThumbnailField;
import net.mytcg.topcar.ui.custom.VerticalGamePlayManager;
import net.mytcg.topcar.ui.custom.VerticalStatManager;
import net.mytcg.topcar.util.Const;
import net.mytcg.topcar.util.SettingsBean;

public final class ConnectionGet extends Connection implements Runnable {
	private DataInputStream _input;
	private ByteArrayOutputStream _output;
	private AppScreen screen = null;
	private ConnectionHandler field = null;
	private ThumbnailField thumb = null;
	private ImageField image = null;
	private ImageLoader imageload = null;
	private VerticalStatManager vStat = null;
	private HorizontalStatManager hStat = null;
	private VerticalGamePlayManager vGame = null;
	private HorizontalGamePlayManager hGame = null;
	private int type = -1;
	private GameCardsHome home;
	private boolean autoclean = true;
	private String filename;
	
	public ConnectionGet(String url, GameCardsHome home) {
		this(url);
		this.home=home;
	}
	public ConnectionGet(String url, VerticalGamePlayManager vGame) {
		this(url);
		this.vGame = vGame;
	}
	public ConnectionGet(String url, HorizontalGamePlayManager hGame) {
		this(url);
		this.hGame = hGame;
	}
	public ConnectionGet(String url, ConnectionHandler field, ImageField image, String filename) {
		this(url);
		this.image = image;
		this.field = field;
		this.filename = filename;
	}
	public ConnectionGet(String url, ConnectionHandler field, ImageLoader image, String filename) {
		this(url);
		this.imageload = image;
		this.field = field;
		this.filename = filename;
	}
	public ConnectionGet(String url, ConnectionHandler field, VerticalStatManager image, String filename) {
		this(url);
		this.vStat = image;
		this.field = field;
		this.filename = filename;
	}
	public ConnectionGet(String url, ConnectionHandler field, HorizontalStatManager image, String filename) {
		this(url);
		System.out.println("url " + url + " filename " + filename);
		this.hStat = image;
		this.field = field;
		this.filename = filename;
	}
	public ConnectionGet(String url, ConnectionHandler field, int type, ThumbnailField thumb) {
		this(url);
		this.type = type;
		this.field = field;
		this.thumb = thumb;
		
	}
	public ConnectionGet(String url, AppScreen screen) {
		this(SettingsBean.getSettings().getUrl()+url);
		this.screen = screen;
	}
	public ConnectionGet(String url, AppScreen screen, boolean autoclean) {
		this(SettingsBean.getSettings().getUrl()+url);
		this.screen = screen;
		this.autoclean = autoclean;
	}
	public ConnectionGet(String url) {
		super(url);
		if (screen != null) {
			screen.setText("Attempting Connection");
		}
	}
	public synchronized void saveData(byte[] data, int type) {
		FileConnection _file = null;
		OutputStream output = null;
		String filename = "";
		switch (type) {
		case 0:
			filename = thumb.getThumbFile();
			break;
		case 1:
			filename = thumb.getFrontFile();
			break;
		case 2:
			filename = thumb.getBackFile();
			break;
		}
		
		try {
			_file = (FileConnection)Connector.open(Const.getStorage()+Const.PREFIX+filename);
			_file.create();
			output = _file.openOutputStream();
			output.write(data);
			output.close();
			_file.close();
		} catch (Exception e) {
			
		}
	}
	public void connect() {
		System.out.println("starting connect for " + _url);
		factory = new HttpConnectionFactory(_url);
    	int lengt;
    	byte[] data;
    	
    	while (_isBusy) {
    		try {
	        	_s = factory.getNextConnection();
	        	
	        	try {
	        		
	        		System.out.println("start download for " + _url);
	        		
		        	_s.setRequestMethod(HttpConnection.GET);
		        	SettingsBean _instance = SettingsBean.getSettings();
		        	_s.setRequestProperty("AUTH_USER", _instance.getUsername());
		        	_s.setRequestProperty("AUTH_PW", _instance.getPassword());
		        	_input = _s.openDataInputStream();
		        	lengt = (int)_s.getLength();
		        	
		        	
		        	
		        	if (screen != null) {
		        		screen.setText("Loading...");
		        	}
		        	
		        	/*if (lengt != -1) {
		        		System.out.println("initializing data");
						data = new byte[lengt];
						System.out.println("created byte array");
						_input.readFully(data);
						System.out.println("reading in fully");
						_text = new String(data);
						System.out.println("readFully completed");
		        	} else {*/
	                    _output = new ByteArrayOutputStream();
	                    int ch;
	                    System.out.println("start reading");
	                    while((ch = _input.read()) != -1)
	                        _output.write(ch);
	                    System.out.println("finished reading");
	                    data = _output.toByteArray();
	                    try {
	                    	_output.close();
	                    } catch (Exception e) {
	                    	System.out.println("error e " + e.toString());
	                    	
	                    }
		        	//}
		        	
		        	if (data.length <= 2048) {
		        		System.out.println("LALALA "+new String(data));
		        	}
		        	
		        	System.out.println("starting process requests for " + _url);
		        	
		        	if (screen != null) {
		        		screen.process(new String(data));
		        	}
					if (vGame != null) {
						vGame.process(data);
		        	}
		        	if (hGame != null) {
						hGame.process(data);
		        	}
		        	if ((field != null)&&(thumb != null)) {
		        		saveData(data, type);
		        		field.process(data, type, thumb, _url);
		        	}
		        	if ((field != null)&&(image != null)) {
		        		field.process(data, image, filename, _url);
		        	}
		        	if ((field != null)&&(imageload != null)) {
		        		field.process(data, imageload, filename, _url);
		        	}
		        	if ((field != null)&&(vStat != null)) {
		        		field.process(data, vStat, filename, _url);
		        	}
		        	System.out.println("check for hStat");
		        	if ((field != null)&&(hStat != null)) {
		        		System.out.println("process " );
		        		field.process(data, hStat, filename, _url);
		        	}
		        	if ((home != null)&&(data.length > 0)) {
		        		String val = new String(data);
		        		int fromIndex = -1;
		    			if ((fromIndex = val.indexOf(Const.xml_result)) != -1) {
		    				home.process((val.substring(fromIndex+Const.xml_result_length, val.indexOf(Const.xml_result_end, fromIndex))));
		    	    	}
		        	}
					_isBusy = false;
					close();
	        	} catch (EOFException ex) {
	        		if (field != null) {
	        			field.removeUrl(_url);
	        		}
	        	}
	        } catch (IOException e) {
	        	if (field != null) {
        			field.removeUrl(_url);
        		}
	        	System.out.println("2");
	        	cleanup();
	        	close(true);
	        } catch (NoMoreTransportsException e) {
	        	if (field != null) {
        			field.removeUrl(_url);
        		}
	        	System.out.println("3");
	        	cleanup();
	        	close(false);
	        }
        }
	}
    public void run() {
    	connect();
    }
    public final void start() {
    	final Thread tmp = new Thread(this);
    	tmp.start();
    }
    private final void cleanup() {
    	_text = null;
    	try {
    		_input.close();
    	} catch (Exception e) {}
    	_input = null;
    }
    public final void close() {
    	if ((screen != null)&&(autoclean)) {
    		screen.setText("");
    	}
    	cleanup();
    	close(false);
    }
}
