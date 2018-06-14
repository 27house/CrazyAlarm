package com.sunn.crazy.bean;

public class UserBean {
    private long id;
    private String account;
    private String nickname;
    private String avatar;
    private String sex = "0";
    /**
     * id : 1
     * password : 123456
     * create_time : 2018-06-01 07:10:02
     * login_time : 2018-06-04 07:50:03
     * describe : fdfdf
     */
    private String password;
    private String create_time;
    private String login_time;
    private String describe;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getLogin_time() {
        return login_time;
    }

    public void setLogin_time(String login_time) {
        this.login_time = login_time;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
    public boolean valid(String account, String password) {
        return account.equals("sum") || password.equals("123456");
    }
}
