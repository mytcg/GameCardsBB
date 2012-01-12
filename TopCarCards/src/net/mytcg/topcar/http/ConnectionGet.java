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
import net.mytcg.topcar.ui.custom.CompareField;
import net.mytcg.topcar.ui.custom.HorizontalGamePlayManager;
import net.mytcg.topcar.ui.custom.HorizontalStatManager;
import net.mytcg.topcar.ui.custom.ImageField;
import net.mytcg.topcar.ui.custom.ImageLoader;
import net.mytcg.topcar.ui.custom.ThumbnailField;
import net.mytcg.topcar.ui.custom.VerticalGamePlayManager;
import net.mytcg.topcar.ui.custom.VerticalStatManager;
import net.mytcg.topcar.util.Const;
import net.mytcg.topcar.util.SettingsBean;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.GaugeField;

public final class ConnectionGet extends Connection implements Runnable {
	private DataInputStream _input;
	private ByteArrayOutputStream _output;
	private AppScreen screen = null;
	private ConnectionHandler field = null;
	private ThumbnailField thumb = null;
	private ImageField image = null;
	private ImageLoader imageload = null;
	private CompareField com = null;
	private VerticalStatManager vStat = null;
	private HorizontalStatManager hStat = null;
	private VerticalGamePlayManager vGame = null;
	private HorizontalGamePlayManager hGame = null;
	private int type = -1;
	private GameCardsHome home;
	private boolean autoclean = true;
	private String filename;
	private GaugeField progress = null;
	
	public ConnectionGet(String url, GameCardsHome home) {
		this(url);
		this.home=home;
	}
	public ConnectionGet(String url, VerticalGamePlayManager vGame, GaugeField prog) {
		this(url);
		this.vGame = vGame;
		this.progress = prog;
	}
	public ConnectionGet(String url, HorizontalGamePlayManager hGame, GaugeField prog) {
		this(url);
		this.hGame = hGame;
		this.progress = prog;
	}
	public ConnectionGet(String url, ConnectionHandler field, ImageField image, String filename) {
		this(url);
		this.image = image;
		this.field = field;
		this.filename = filename;
	}
	public ConnectionGet(String url, ConnectionHandler field, ImageField image, String filename, GaugeField prog) {
		this(url);
		this.image = image;
		this.field = field;
		this.filename = filename;
		this.progress = prog;
	}
	public ConnectionGet(String url, ConnectionHandler field, ImageLoader image, String filename) {
		this(url);
		this.imageload = image;
		this.field = field;
		this.filename = filename;
	}
	public ConnectionGet(String url, ConnectionHandler field, CompareField com, String filename) {
		this(url);
		this.com = com;
		this.field = field;
		this.filename = filename;
	}
	public ConnectionGet(String url, ConnectionHandler field, VerticalStatManager image, String filename, GaugeField prog) {
		this(url);
		this.vStat = image;
		this.field = field;
		this.filename = filename;
		this.progress = prog;
	}
	public ConnectionGet(String url, ConnectionHandler field, HorizontalStatManager image, String filename, GaugeField prog) {
		this(url);
		this.hStat = image;
		this.field = field;
		this.filename = filename;
		this.progress = prog;
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
		factory = new HttpConnectionFactory(_url);
    	int lengt;
    	byte[] data;
    	
    	while (_isBusy) {
    		try {
	        	_s = factory.getNextConnection();
	        	
	        	try {
	        		
	        		System.out.println("starting with url " + _url);
		        	_s.setRequestMethod(HttpConnection.GET);
		        	SettingsBean _instance = SettingsBean.getSettings();
		        	_s.setRequestProperty("AUTH_USER", _instance.getUsername());
		        	_s.setRequestProperty("AUTH_PW", _instance.getPassword());
		        	_input = _s.openDataInputStream();
		        	lengt = (int)_s.getLength();
		        	
		        	
		        	
		        	if (screen != null) {
		        		screen.setText("Loading...");
		        	}
		        	
		        	int total = 0;
		        	if(progress != null){
		        		progress.setValue(0);
		        		try{
		        			progress.getManager().invalidate();
		        		}catch(Exception e){}
		        		total = lengt;
		        	}
		        	_output = new ByteArrayOutputStream();
	                int ch;
	                int jump = 9;
	                while((ch = _input.read()) != -1){
	                	total--;
	                   	if(progress != null){
	                   		int prog = (int)(((double)((double)lengt - (double)total)/(double)lengt)*100);
	                   		if(prog > jump){
	                   			jump = jump + 10;
		                   		synchronized(UiApplication.getEventLock()) {
			                		try{
			                			if ( prog > 0 ) {
			                				progress.setValue(prog);
			                				progress.getManager().invalidate();
			                            }
			                		}catch(Exception e){System.out.println(e.toString());};
		                  		}
	                   		}
	    		      	}
	                    _output.write(ch);
	                }
	                progress = null;
	                data = _output.toByteArray();
	                _text = new String(data);
	                try {
	                  	_output.close();
	                } catch (Exception e) {
	                    	
	                }
		        	//}
		        	
		        	if (data.length <= 2048) {
		        		System.out.println("["+new String(data) + "]");
		        	}
		        	
		        	
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
		        	if ((field != null)&&(com != null)) {
		        		field.process(data, com, filename, _url);
		        	}
		        	if ((field != null)&&(vStat != null)) {
		        		field.process(data, vStat, filename, _url);
		        	}
		        	if ((field != null)&&(hStat != null)) {
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
	        	cleanup();
	        	close(true);
	        } catch (NoMoreTransportsException e) {
	        	if (field != null) {
        			field.removeUrl(_url);
        		}
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
