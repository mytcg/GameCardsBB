package net.mytcg.topcar.http;

import java.util.Vector;

import net.mytcg.topcar.ui.custom.CompareField;
import net.mytcg.topcar.ui.custom.HorizontalStatManager;
import net.mytcg.topcar.ui.custom.ImageField;
import net.mytcg.topcar.ui.custom.ImageLoader;
import net.mytcg.topcar.ui.custom.ThumbnailField;
import net.mytcg.topcar.ui.custom.VerticalStatManager;

class ThumbConnection {
	private String url = "";
	private int type = -1;
	private ThumbnailField thumb = null;
	private ImageField img = null;
	private ImageLoader imgload = null;
	private CompareField com = null;
	private VerticalStatManager vert = null;
	private HorizontalStatManager hori = null;
	private String filename = "";
	
	public ThumbConnection(String url, int type, ThumbnailField thumb) {
		this.url = url;
		this.type = type;
		this.thumb = thumb;
	}
	public ThumbConnection(String url, String filename, HorizontalStatManager img) {
		System.out.println("horizontalStatManager added");
		this.url = url;
		this.filename = filename;
		this.hori = img;
	}
	public ThumbConnection(String url, String filename, VerticalStatManager img) {
		this.url = url;
		this.filename = filename;
		this.vert = img;
	}
	public ThumbConnection(String url, String filename, ImageField img) {
		this.url = url;
		this.filename = filename;
		this.img = img;
	}
	public ThumbConnection(String url, String filename, ImageLoader img) {
		this.url = url;
		this.filename = filename;
		this.imgload = img;
	}
	public ThumbConnection(String url, String filename, CompareField com) {
		this.url = url;
		this.filename = filename;
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
	public ImageLoader getImgLoad() {
		return imgload;
	}
	public String getFilename() {
		return filename;
	}
	public VerticalStatManager getVert() {
		return vert;
	}
	public HorizontalStatManager getHort() {
		return hori;
	}
}