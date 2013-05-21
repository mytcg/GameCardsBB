package net.mytcg.kickoff.http;

import javax.microedition.io.HttpConnection;

public class Connection {
	protected String _url;
	protected HttpConnection _s;
	protected boolean _isBusy;
	protected HttpConnectionFactory factory;
	protected int _returnCode;
	protected String _text;
	
	public Connection(String url) {
		if (url == null) {
			url = "";
		}
		_isBusy = true;
		_url = url;
		_s = null;
		_returnCode = 0;
	}
	public final HttpConnection getCon() {
		return _s;
	}
	public final boolean isBusy() {
		return _isBusy;
	}
	public final int getReturnCode() {
		if ((_s != null)&&(_returnCode <= 0)) {
			try {
				_returnCode = _s.getResponseCode();
			} catch (Exception e) {}
		}
		return _returnCode;
	}
	public final String getText() {
		if (_text == null) {
			_text = "";
		}
		return _text;
	}
	protected final void close(boolean conti) {
		_returnCode = 0;
		_text = null;
		_isBusy = conti;
		_url = null;
		if (!conti) {
			try {
				factory.close();
			} catch (Exception e) {}
			factory = null;
		}
		try {
			_returnCode = _s.getResponseCode();
		} catch (Exception e) {}
		try {
			_s.close();
		} catch (Exception e) {}
		_s = null;
    }
	public void close() {
		close(false);
	}
}