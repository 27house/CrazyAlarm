package com.sunn.xhui.crazyalarm.data;

import java.io.Serializable;

/**
 * 响铃后小游戏
 *
 * @author XHui.sun
 * created at 2018/5/21 0021  11:02
 */
public class AlarmGame implements Serializable {
	public static final int TYPE_NORMAL = 0;
	public static final int TYPE_ONLY_VOICE = 1;
	public static final int TYPE_ONLY_VIBRATE = 2;
	private int id;
	private String name;
	private int type;
	/**
	 * rules : 1分钟内获得80分即可完成任务
	 */

	private String rules;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}
}
