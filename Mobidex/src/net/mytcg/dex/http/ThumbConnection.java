package net.mytcg.dex.http;

import net.mytcg.dex.ui.custom.ThumbnailField;

class ThumbConnection {
	private String url = "";
	private int type = -1;
	private ThumbnailField thumb = null;
	
	public ThumbConnection(String url, int type, ThumbnailField thumb) {
		this.url = url;
		this.type = type;
		this.thumb = thumb;
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
}