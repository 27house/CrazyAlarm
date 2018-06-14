package com.sunn.xhui.crazyalarm.event;

/**
 * @author XHui.sun
 * created at 2018/5/22 0022  10:48
 */

public class RefreshAlarmListEvent {
	private boolean startSet;

	public RefreshAlarmListEvent(boolean startSet) {
		this.startSet = startSet;
	}

	public boolean isStartSet() {
		return startSet;
	}
}
