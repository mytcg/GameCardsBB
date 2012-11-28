package net.mytcg.dev.http;

import net.mytcg.dev.ui.custom.AwardField;
import net.mytcg.dev.ui.custom.CompareField;
import net.mytcg.dev.ui.custom.HorizontalStatManager;
import net.mytcg.dev.ui.custom.ImageField;
import net.mytcg.dev.ui.custom.ImageLoader;
import net.mytcg.dev.ui.custom.ThumbnailField;
import net.mytcg.dev.ui.custom.VerticalStatManager;
import net.rim.device.api.ui.component.GaugeField;

class ThumbConnection {
	private String url = "";
	private int type = -1;
	private ThumbnailField thumb = null;
	private AwardField awardthumb = null;
	private ImageField img = null;
	private ImageLoader imgload = null;
	private CompareField com = null;
	private VerticalStatManager vert = null;
	private HorizontalStatManager hori = null;
	private String filename = "";
	private GaugeField progress = null;
	
	public ThumbConnection(String url, int type, ThumbnailField thumb) {
		this.url = url;
		this.type = type;
		this.thumb = thumb;
	}
	public ThumbConnection(String url, int type, AwardField awardthumb) {
		this.url = url;
		this.type = type;
		this.awardthumb = awardthumb;
	}
	public ThumbConnection(String url, String filename, HorizontalStatManager img) {
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
	public AwardField getAwardThumb() {
		return awardthumb;
	}
	public ImageField getImg() {
		return img;
	}
	public ImageLoader getImgLoad() {
		return imgload;
	}
	public CompareField getCom() {
		return com;
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
	public void setGaugeField(GaugeField prog) {
		progress = prog;
	}
	public GaugeField getGaugeField() {
		return progress;
	}
}