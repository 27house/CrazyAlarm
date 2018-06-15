package com.sunn.xhui.crazyalarm.data;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * 图片
 *
 * @author XHui.sun
 * created at 2018/4/11 0011  14:50
 */
public class SelectImg implements Serializable {

	public static final int STATUS_DEFAULT = 0;
	public static final int STATUS_UPLOAD = 1;
	public static final int STATUS_SUCCESS = 2;
	public static final int STATUS_ERROR = 3;

	public static final int TYPE_ADD = 1;

	private int type;
	private String localPath;
	private Bitmap thumb;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public Bitmap getThumb() {
		return thumb;
	}

	public void setThumb(Bitmap thumb) {
		this.thumb = thumb;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	private int status;

	private String[] bytes;

	public String[] getBytes() {
		return bytes;
	}

	public void setBytes(String[] bytes) {
		this.bytes = bytes;
	}

	private String fileid;

	public String getFileid() {
		return fileid;
	}

	public void setFileid(String fileid) {
		this.fileid = fileid;
	}
}
