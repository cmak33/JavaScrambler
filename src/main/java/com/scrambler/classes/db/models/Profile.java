package com.scrambler.classes.db.models;

public class Profile {
    private final int id;
    private String nickname;
    private String password;

    public Profile(String nickname,String password,int id){
        this.id = id;
        this.nickname=nickname;
        this.password=password;
    }

    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
