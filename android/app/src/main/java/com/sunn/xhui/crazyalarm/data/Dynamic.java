package com.sunn.xhui.crazyalarm.data;

import java.io.Serializable;
import java.util.List;

public class Dynamic implements Serializable {
	/**
	 * create_time : 2018-06-13 10:21:32
	 * likeCount : 0
	 * id : 1
	 * pics : []
	 * userId : 11
	 * content : 好困好困军火库军火库
	 * commentCount : 0
	 */

	private int id;
	private UserInfo user;
	private String content;
	private List<String> pics;
	private int commentCount;
	private int likeCount;
	private String create_time;
	/**
	 * isLike : 1
	 * user : {"password":"","create_time":"","login_time":"","sex":"2","nickname":"小丫丫","motto":"花飞花落，缘起缘灭。","avatar":"http://192.168.0.136:8080/crazy//upload/f5e8be1e-6ee9-4765-ab04-bba5a6ea4475.jpg","id":1,"account":"sum@qq.com"}
	 */

	private int isLike;

	public int getIsLike() {
		return isLike;
	}

	public void setIsLike(int isLike) {
		this.isLike = isLike;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public List<String> getPics() {
		return pics;
	}

	public void setPics(List<String> pics) {
		this.pics = pics;
	}


}
