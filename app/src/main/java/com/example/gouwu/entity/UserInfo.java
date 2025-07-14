package com.example.gouwu.entity;

public class UserInfo {
    private  int user_id;
    private String username;
    private String password;
    private String nickname;
    private String pic;
    //全局变量，保存登录的用户名和密码
    public static UserInfo sUserInfo;

    public static UserInfo getsUserInfo() {
        return sUserInfo;
    }

    public static void setsUserInfo(UserInfo sUserInfo) {
        UserInfo.sUserInfo = sUserInfo;
    }

    public UserInfo(int user_id, String username, String password, String nickname,String pic) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.pic = pic;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
