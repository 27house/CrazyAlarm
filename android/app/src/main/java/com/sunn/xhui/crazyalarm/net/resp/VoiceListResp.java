package com.sunn.xhui.crazyalarm.net.resp;

import com.sunn.xhui.crazyalarm.data.Voice;

import java.util.List;

public class VoiceListResp extends BaseResp {

    private List<Voice> list;

	public List<Voice> getList() {
		return list;
	}

	public void setList(List<Voice> list) {
		this.list = list;
	}
}
