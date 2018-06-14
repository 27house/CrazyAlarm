package com.sunn.xhui.crazyalarm.net.resp;

import com.sunn.xhui.crazyalarm.data.Voice;

import java.util.List;

public class VoiceListResp extends BaseResp {

    private List<Voice> datas;

	public List<Voice> getDatas() {
		return datas;
	}

	public void setDatas(List<Voice> datas) {
		this.datas = datas;
	}
}
