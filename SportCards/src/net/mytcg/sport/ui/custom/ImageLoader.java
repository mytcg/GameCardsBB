package net.mytcg.sport.ui.custom;

import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

import net.mytcg.sport.http.ConnectionGet;
import net.mytcg.sport.util.Const;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;

import net.rim.device.api.ui.Graphics;

public final class ImageLoader extends Field {
	
	private String url;
	private String file;
	
	public ImageLoader(String url) {
		this.url = url;
		if ((url != null)&&(url.length() > 0)){
			file = url.substring(url.indexOf(Const.cards)+Const.cards_length, url.indexOf(Const.png));
		}
		construct();
	}
	
	protected void drawFocus(Graphics g, boolean x) {
		
	}
	public void setUrl(String url) {
		this.url = url;
		if ((url != null)&&(url.length() > 0)){
			file = url.substring(url.indexOf(Const.cards)+Const.cards_length, url.indexOf(Const.png));
		}
		construct();
	}
	
	public synchronized void saveData(byte[] data, String filename) {
		FileConnection _file = null;
		OutputStream output = null;
		try {
			_file = (FileConnection)Connector.open(Const.getStorage()+Const.PREFIX+filename);
			_file.create();
			output = _file.openOutputStream();
			output.write(data);
			output.close();
			_file.close();
		} catch (Exception e) {}
	}
	public void getData() {
		FileConnection _file = null;
		try {
			_file = (FileConnection)Connector.open(Const.getStorage()+Const.PREFIX+file);
			if (!_file.exists()) {
				_file.close();
				doConnect(url);
			} else {
				_file.close();
			}
		} catch (Exception e) {}
	}
	
	public void construct() {
		if (file != null) {
			getData();
		}
	}
	
	public void onUndisplay() {
		
	}
	public void onUnfocus() {
		//invalidate();
		
	}
	public void onVisibilityChange(boolean visible) {
		
	}
	public void focusRemove() {
		
	}
	
	public int getPreferredWidth() {
		return Const.getWidth();
	}
	public int getPreferredHeight() {
		return Const.getHeight()-Const.getButtonCentre().getHeight();
	}
	protected void layout(int width, int height) {
		setExtent(getPreferredWidth(),getPreferredHeight());
    }
	public boolean isFocusable() {
    	return true;
    }
	public void paint(Graphics g) {
		
	}
	protected boolean navigationClick(int status, int time) {
        fieldChangeNotify(1);
        return true;
    }
	
	ConnectionGet cG;
	int timeout = 0;
	
	public void doConnect(String url) {
		if ((url != null)&&(url.length() > 0)) {
			(Const.getConnection()).addConnect(url, file, this);
		}
	}
	
	public void process(byte[] data, String filename) {
		saveData(data, filename);
	}
}