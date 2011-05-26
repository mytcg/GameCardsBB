package net.mytcg.dex.http;

import java.util.Vector;

import net.mytcg.dex.ui.custom.ThumbnailField;
import net.mytcg.dex.util.Const;


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
		connections.addElement(new ThumbConnection(url, type, thumb));
		if (!busy) {
			busy = true;
			start();
		}
		
		checkRun();
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
		ConnectionGet cG = new ConnectionGet(thumb.getUrl(), this, thumb.getType(), thumb.getThumb());
		cG.start();
	}
}
