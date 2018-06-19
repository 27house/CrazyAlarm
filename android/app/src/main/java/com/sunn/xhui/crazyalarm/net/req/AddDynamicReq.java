package com.sunn.xhui.crazyalarm.net.req;

import java.util.List;

public class AddDynamicReq {

	private String content;
	private List<String> picPaths;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public List<String> getPicPaths() {
		return picPaths;
	}

	public void setPicPaths(List<String> picPaths) {
		this.picPaths = picPaths;
	}
}
