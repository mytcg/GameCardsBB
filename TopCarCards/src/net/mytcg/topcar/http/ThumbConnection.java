package net.mytcg.topcar.http;

import net.mytcg.topcar.ui.custom.CompareField;
import net.mytcg.topcar.ui.custom.ImageField;
import net.mytcg.topcar.ui.custom.ThumbnailField;

class ThumbConnection {
	private String url = "";
	private int type = -1;
	private ThumbnailField thumb = null;
	private ImageField img = null;
	private CompareField com = null;
	private String filename = "";
	
	public ThumbConnection(String url, int type, ThumbnailField thumb) {
		this.url = url;
		this.type = type;
		this.thumb = thumb;
		this.img = null;
		this.com = null;
	}
	public ThumbConnection(String url, String filename, ImageField img) {
		this.url = url;
		this.filename = filename;
		this.thumb = null;
		this.img = img;
		this.com = null;
	}
	public ThumbConnection(String url, String filename, CompareField com) {
		this.url = url;
		this.filename = filename;
		this.thumb = null;
		this.img = null;
		this.com = com;
	}
	public int getType() {
		return type;
	}
	public String getUrl() {
		return url;
	}
	public ThumbnailField getThumb() {
		return thumb;
	}
	public ImageField getImg() {
		return img;
	}
	public String getFilename() {
		return filename;
	}
}