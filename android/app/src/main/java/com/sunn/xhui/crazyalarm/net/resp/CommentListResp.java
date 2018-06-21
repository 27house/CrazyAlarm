package com.sunn.xhui.crazyalarm.net.resp;

import com.sunn.xhui.crazyalarm.data.Comment;
import com.sunn.xhui.crazyalarm.data.Dynamic;

import java.util.List;

public class CommentListResp extends BaseResp {


	private List<Comment> list;


	public List<Comment> getList() {
		return list;
	}

	public void setList(List<Comment> list) {
		this.list = list;
	}

}
