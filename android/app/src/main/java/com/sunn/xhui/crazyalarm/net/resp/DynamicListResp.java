package com.sunn.xhui.crazyalarm.net.resp;

import com.sunn.xhui.crazyalarm.data.Dynamic;

import java.util.List;

public class DynamicListResp extends BaseResp {


	/**
	 * result : 0
	 * list : [{"create_time":"2018-06-13 10:21:32","likeCount":0,"id":1,"pics":[],"userId":11,"content":"好困好困军火库军火库","commentCount":0},{"create_time":"2018-06-13 08:21:35","likeCount":0,"id":2,"pics":[],"userId":11,"content":"很高的价格计算的规划","commentCount":0}]
	 * message :
	 */
	private List<Dynamic> list;


	public List<Dynamic> getList() {
		return list;
	}

	public void setList(List<Dynamic> list) {
		this.list = list;
	}

}
