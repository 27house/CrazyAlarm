package com.sunn.xhui.crazyalarm.data;

import java.io.Serializable;

/**
 * @author XHui.sun
 * created at 2018/5/16 0016  15:00
 */

public class Alarm implements Serializable {
	private int id;
	private long time;
	private AlarmRepeat alarmRepeat;
	private Voice voice;
	private AlarmGame alarmGame;
	private String desc;
	private boolean open;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public AlarmRepeat getAlarmRepeat() {
		return alarmRepeat;
	}

	public void setAlarmRepeat(AlarmRepeat alarmRepeat) {
		this.alarmRepeat = alarmRepeat;
	}

	public Voice getVoice() {
		return voice;
	}

	public void setVoice(Voice voice) {
		this.voice = voice;
	}

	public AlarmGame getAlarmGame() {
		return alarmGame;
	}

	public void setAlarmGame(AlarmGame alarmGame) {
		this.alarmGame = alarmGame;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
}
