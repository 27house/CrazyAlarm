package com.sunn.xhui.crazyalarm.net.resp;

import com.sunn.xhui.crazyalarm.data.UserInfo;

public class LoginResp extends BaseResp {


	private UserInfo data;

	public UserInfo getData() {
		return data;
	}

	public void setData(UserInfo data) {
		this.data = data;
	}
}
