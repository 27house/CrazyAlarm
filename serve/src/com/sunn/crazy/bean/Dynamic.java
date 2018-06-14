package com.sunn.crazy.bean;

import java.io.Serializable;
import java.util.List;

public class Dynamic {

    private int id;
    private int userId;
    private String content;
    private List<String> pics;
    private String create_time;

    private int commentCount;
    private int likeCount;
    private String like_u_id;

    public String getLike_u_id() {
        return like_u_id;
    }

    public void setLike_u_id(String like_u_id) {
        this.like_u_id = like_u_id;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
