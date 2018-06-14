package com.sunn.xhui.crazyalarm.data;

import java.io.Serializable;

/**
 * 闹钟循环数据
 *
 * @author XHui.sun
 * created at 2018/5/16 0016  15:02
 */
public class AlarmRepeat implements Serializable {

	public static final int TYPE_ONCE = 0;
	public static final int TYPE_EVERY_DAY = 1;
	public static final int TYPE_WORK_DAY = 2;
	public static final int TYPE_WEEK = 3;
	public static final int TYPE_CUSTOM = 4;

	private String showStr;
	private int type;
	private String weeks;

	public String getWeeks() {
		return weeks;
	}

	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}

	public String getShowStr() {
		return showStr;
	}

	public void setShowStr(String showStr) {
		this.showStr = showStr;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
		switch (type) {
			case TYPE_ONCE:
				setShowStr("只响一次");
				break;
			case TYPE_EVERY_DAY:
				setShowStr("每天");
				break;
			case TYPE_WORK_DAY:
				setShowStr("周一到周五");
				break;
			case TYPE_WEEK:
				setShowStr("周末");
				break;
			case TYPE_CUSTOM:
				setShowStr("");
				break;
			default:
				break;
		}
	}

}
