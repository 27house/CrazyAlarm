package com.sunn.crazy.bean;

public class TaskBean {
    private int id;
    private String name;
    private int type;
    private String rules;

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String h_url;
    private int score;

    public String getH_url() {
        return h_url;
    }

    public void setH_url(String h_url) {
        this.h_url = h_url;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
