package net.mytcg.dev.http;

import java.util.Vector;

import net.mytcg.dev.ui.custom.ImageField;
import net.mytcg.dev.ui.custom.ThumbnailField;
import net.mytcg.dev.util.Const;


public class ConnectionHandler extends Thread {
	
	private Vector connections = new Vector(); 
	private boolean busy = false;
	private ThumbConnection current = null;
	
	public ConnectionHandler() {
		Const.THREADS = 0;
		connections = new Vector();
		busy = false;
		current = null;
	}
	public synchronized void process(byte[] data, int type, ThumbnailField thumb) {
		thumb.process(data, type);
		Const.THREADS--;
		if ((Const.THREADS == 0)&&(connections.size()==0)) {
			close();
		}
	}
	public synchronized void process(byte[] data, ImageField img, String filename) {
		img.process(data, filename);
		Const.THREADS--;
		if ((Const.THREADS == 0)&&(connections.size()==0)) {
			close();
		}
	}
	public void setBusy() {
		busy = false;
	}
	public boolean isBusy() {
		return busy;
	}
	private void close() {
		connections.removeAllElements();
		connections = null;
		busy = false;
		current = null;
	}
	public synchronized void addConnect(String url, int type, ThumbnailField thumb) {
		ThumbConnection tmp = new ThumbConnection(url, type, thumb);
		if (!connections.contains(tmp)) {
			connections.addElement(tmp);
			if (!busy) {
				busy = true;
				start();
			}
			
			checkRun();
		}
	}
	public synchronized void addConnect(String url, String filename, ImageField img) {
		ThumbConnection tmp = new ThumbConnection(url, filename, img);
		if (!connections.contains(tmp)) {
			connections.addElement(tmp);
			if (!busy) {
				busy = true;
				start();
			}
			
			checkRun();
		}
	}
	public synchronized void checkRun() {
		if (Thread.activeCount() <= 14) {
			if (connections.size() >= 1) {
				current = ((ThumbConnection)connections.elementAt(0));
				
				connections.removeElement(current);
				Const.THREADS++;
				doConnect(current);
				current = null;
			}
		}
	}
	public void run() {
		while (isBusy()) {
			checkRun();
			try {
				Thread.sleep(500);
			} catch (Exception e) {}
		}
	}
	public void doConnect(ThumbConnection thumb) {
		if (thumb.getImg() == null) {
			ConnectionGet cG = new ConnectionGet(thumb.getUrl(), this, thumb.getType(), thumb.getThumb());
			cG.start();
		} else if (thumb.getThumb() == null) {
			ConnectionGet cG = new ConnectionGet(thumb.getUrl(), this, thumb.getImg(), thumb.getFilename());
			cG.start();
			
		}
	}
}
