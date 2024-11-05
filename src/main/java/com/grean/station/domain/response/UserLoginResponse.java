//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.response;

public class UserLoginResponse {
    private String name;
    private String nick;
    private String avata;
    private String token;
    private Integer groupIndex;

    public UserLoginResponse() {
    }

    public String getAvata() {
        return this.avata;
    }

    public void setAvata(String avata) {
        this.avata = avata;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getGroupIndex() {
        return this.groupIndex;
    }

    public void setGroupIndex(Integer groupIndex) {
        this.groupIndex = groupIndex;
    }
}
