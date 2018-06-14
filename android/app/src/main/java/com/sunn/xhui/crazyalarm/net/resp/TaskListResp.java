package com.sunn.xhui.crazyalarm.net.resp;

import com.sunn.xhui.crazyalarm.data.AlarmGame;

import java.util.List;

public class TaskListResp extends BaseResp {

	private List<AlarmGame> datas;

	public List<AlarmGame> getDatas() {
		return datas;
	}

	public void setDatas(List<AlarmGame> datas) {
		this.datas = datas;
	}
}
