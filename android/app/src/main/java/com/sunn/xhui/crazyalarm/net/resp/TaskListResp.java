package com.sunn.xhui.crazyalarm.net.resp;

import com.sunn.xhui.crazyalarm.data.AlarmGame;

import java.util.List;

public class TaskListResp extends BaseResp {

	private List<AlarmGame> list;

	public List<AlarmGame> getList() {
		return list;
	}

	public void setList(List<AlarmGame> list) {
		this.list = list;
	}
}
