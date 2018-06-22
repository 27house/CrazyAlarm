package com.sunn.xhui.crazyalarm.data;

import java.io.Serializable;
import java.util.List;

public class Comment implements Serializable {


	/**
	 * followId : 0
	 * create_time : 2018-06-21 04:50:17
	 * dynamicId : 0
	 * commentId : 0
	 * id : 4
	 * user : {"password":"","create_time":"","login_time":"","sex":"2","nickname":"小丫丫","motto":"花飞花落，缘起缘灭。","avatar":"http://192.168.0.136:8080/crazy//upload/f5e8be1e-6ee9-4765-ab04-bba5a6ea4475.jpg","id":1,"account":"sum@qq.com"}
	 * content : bcfghjjj
	 */

	private int followId;
	private String create_time;
	private int dynamicId;
	private int commentId;
	private int id;
	private UserInfo user;
	private String content;
	/**
	 * subList : []
	 * user : {"password":"","create_time":"","login_time":"","sex":"2","nickname":"小丫丫","motto":"花飞花落，缘起缘灭。","avatar":"http://192.168.0.136:8080/crazy//upload/f5e8be1e-6ee9-4765-ab04-bba5a6ea4475.jpg","id":1,"account":"sum@qq.com"}
	 */

	private List<Comment> subList;

	public List<Comment> getSubList() {
		return subList;
	}

	public void setSubList(List<Comment> subList) {
		this.subList = subList;
	}

	public int getFollowId() {
		return followId;
	}

	public void setFollowId(int followId) {
		this.followId = followId;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public int getDynamicId() {
		return dynamicId;
	}

	public void setDynamicId(int dynamicId) {
		this.dynamicId = dynamicId;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
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


	private UserInfo followUser;

	public UserInfo getFollowUser() {
		return followUser;
	}

	public void setFollowUser(UserInfo followUser) {
		this.followUser = followUser;
	}

	private int subCount;

	public int getSubCount() {
		return subCount;
	}

	public void setSubCount(int subCount) {
		this.subCount = subCount;
	}
}
