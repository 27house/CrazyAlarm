package com.sunn.xhui.crazyalarm.net.req;

/**
 * @author XHui.sun
 * created at 2018/6/19 0019  15:45
 */

public class SetDynamicReq {

	public static final int TYPE_DELETE = -1;

	public static final int TYPE_ADD_LIKE = 11;
	public static final int TYPE_ADD_COMMENT = 12;

	public static final int TYPE_REMOVE_LIKE = 21;
	public static final int TYPE_REMOVE_COMMENT = 22;

	private int type;

	public SetDynamicReq(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	private int dId;
	private int cId;

	private String content;

	public int getdId() {
		return dId;
	}

	public void setdId(int dId) {
		this.dId = dId;
	}

	public int getcId() {
		return cId;
	}

	public void setcId(int cId) {
		this.cId = cId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
