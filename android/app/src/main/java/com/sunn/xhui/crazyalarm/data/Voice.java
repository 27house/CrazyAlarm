package com.sunn.xhui.crazyalarm.data;

import android.media.Ringtone;
import android.net.Uri;

import java.io.Serializable;

/**
 * 音频类
 *
 * @author XHui.sun
 * created at 2018/5/17 0017  10:36
 */
public class Voice implements Serializable {
	public static final int TYPE_RING_TONE = 0;
	public static final int TYPE_DOWNLOAD = 1;
	public static final int TYPE_ONLINE = 2;
	private int id;
	private String title;
	private String path;
	private int type;
	private int cursorPos;
	private String ringtone;

	public String getRingtone() {
		return ringtone;
	}

	public void setRingtone(String ringtone) {
		this.ringtone = ringtone;
	}

	public int getCursorPos() {
		return cursorPos;
	}

	public void setCursorPos(int cursorPos) {
		this.cursorPos = cursorPos;
	}

	private boolean isUsed;

	public Voice(int type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean used) {
		isUsed = used;
	}

	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
