package net.mytcg.phakama.http;

import java.util.Vector;

import net.mytcg.phakama.ui.custom.CompareField;
import net.mytcg.phakama.ui.custom.HorizontalStatManager;
import net.mytcg.phakama.ui.custom.ImageField;
import net.mytcg.phakama.ui.custom.ImageLoader;
import net.mytcg.phakama.ui.custom.ThumbnailField;
import net.mytcg.phakama.ui.custom.VerticalStatManager;
import net.mytcg.phakama.util.Const;
import net.rim.device.api.ui.component.GaugeField;


public class ConnectionHandler extends Thread {
	
	private Vector connections = new Vector(); 
	private boolean busy = false;
	private ThumbConnection current = null;
	private Vector urls = new Vector();
	
	public ConnectionHandler() {
		Const.THREADS = 0;
		connections = new Vector();
		busy = false;
		current = null;
		urls = new Vector();
	}
	public synchronized void process(byte[] data, int type, ThumbnailField thumb, String url) {
		removeUrl(url);
		thumb.process(data, type);
		Const.THREADS--;
		if ((Const.THREADS == 0)&&(connections.size()==0)) {
			close();
		}
	}
	public synchronized void process(byte[] data, ImageField img, String filename, String url) {
		removeUrl(url);
		img.process(data, filename);
		Const.THREADS--;
		if ((Const.THREADS == 0)&&(connections.size()==0)) {
			close();
		}
	}
	public void removeUrl(String url) {
		urls.removeElement(url);
	}
	public synchronized void process(byte[] data, ImageLoader img, String filename, String url) {
		removeUrl(url);
		img.process(data, filename);
		Const.THREADS--;
		if ((Const.THREADS == 0)&&(connections.size()==0)) {
			close();
		}
	}
	public synchronized void process(byte[] data, CompareField com, String filename, String url) {
		removeUrl(url);
		com.process(data, filename);
		Const.THREADS--;
		if ((Const.THREADS == 0)&&(connections.size()==0)) {
			close();
		}
	}
	public synchronized void process(byte[] data, VerticalStatManager img, String filename, String url) {
		removeUrl(url);
		img.process(data, filename);
		Const.THREADS--;
		if ((Const.THREADS == 0)&&(connections.size()==0)) {
			close();
		}
	}
	public synchronized void process(byte[] data, HorizontalStatManager img, String filename, String url) {
		removeUrl(url);
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
		//if (urls.contains(url)) {
			
		//} else {
			urls.addElement(url);
			ThumbConnection tmp = new ThumbConnection(url, type, thumb);
			if (!connections.contains(tmp)) {
				connections.addElement(tmp);
				if (!busy) {
					busy = true;
					start();
				}
				checkRun();
			}
		//}
	}
	public synchronized void addConnect(String url, String filename, ImageLoader img) {
		//if (urls.contains(url)) {
			
		//} else {
			urls.addElement(url);
			ThumbConnection tmp = new ThumbConnection(url, filename, img);
			if (!connections.contains(tmp)) {
				connections.addElement(tmp);
				if (!busy) {
					busy = true;
					start();
				}
				
				checkRun();
			}
		//}
	}
	public synchronized void addConnect(String url, String filename, HorizontalStatManager img, GaugeField progress) {
		//if (urls.contains(url)) {
			
		//} else {
			urls.addElement(url);
			ThumbConnection tmp = new ThumbConnection(url, filename, img);
			tmp.setGaugeField(progress);
			if (!connections.contains(tmp)) {
				connections.addElement(tmp);
				if (!busy) {
					busy = true;
					start();
				}
				
				checkRun();
			}
		//}
	}
	public synchronized void addConnect(String url, String filename, VerticalStatManager img, GaugeField progress) {
		//if (urls.contains(url)) {
			
		//} else {
			urls.addElement(url);
			ThumbConnection tmp = new ThumbConnection(url, filename, img);
			tmp.setGaugeField(progress);
			if (!connections.contains(tmp)) {
				connections.addElement(tmp);
				if (!busy) {
					busy = true;
					start();
				}
				
				checkRun();
			}
		//}
	}
	public synchronized void addConnect(String url, String filename, ImageField img) {
		//if (urls.contains(url)) {
			
		//} else {
			urls.addElement(url);
			ThumbConnection tmp = new ThumbConnection(url, filename, img);
			if (!connections.contains(tmp)) {
				connections.addElement(tmp);
				if (!busy) {
					busy = true;
					start();
				}
				
				checkRun();
			}
		//}
	}
	public synchronized void addConnect(String url, String filename, CompareField img) {
		//if (urls.contains(url)) {
			
		//} else {
			urls.addElement(url);
			ThumbConnection tmp = new ThumbConnection(url, filename, img);
			if (!connections.contains(tmp)) {
				connections.addElement(tmp);
				if (!busy) {
					busy = true;
					start();
				}
				
				checkRun();
			}
		//}
	}
	public synchronized void checkRun() {
		if (Thread.activeCount() <= 14) {
			if (connections != null) {
				if (connections.size() >= 1) {
					current = ((ThumbConnection)connections.elementAt(0));
					
					connections.removeElement(current);
					Const.THREADS++;
					doConnect(current);
					current = null;
				}
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
		if (thumb.getImg() != null) {
			ConnectionGet cG = new ConnectionGet(thumb.getUrl(), this, thumb.getImg(), thumb.getFilename());
			cG.start();
		} else if (thumb.getThumb() != null) {
			ConnectionGet cG = new ConnectionGet(thumb.getUrl(), this, thumb.getType(), thumb.getThumb());
			cG.start();
		} else if (thumb.getVert() != null) {
			ConnectionGet cG = new ConnectionGet(thumb.getUrl(), this, thumb.getVert(), thumb.getFilename(), thumb.getGaugeField());
			cG.start();
		} else if (thumb.getHort() != null) {
			ConnectionGet cG = new ConnectionGet(thumb.getUrl(), this, thumb.getHort(), thumb.getFilename(), thumb.getGaugeField());
			cG.start();
		} else if (thumb.getImgLoad() != null) {
			ConnectionGet cG = new ConnectionGet(thumb.getUrl(), this, thumb.getImgLoad(), thumb.getFilename());
			cG.start();
		} else if (thumb.getCom() != null) {
			ConnectionGet cG = new ConnectionGet(thumb.getUrl(), this, thumb.getCom(), thumb.getFilename());
			cG.start();
		}
	}
}
