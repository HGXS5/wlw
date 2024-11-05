//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

public class SendCheckInfo {
    boolean scanFlag;
    int id;
    String nickname;

    public SendCheckInfo() {
    }

    public boolean isScanFlag() {
        return this.scanFlag;
    }

    public void setScanFlag(boolean scanFlag) {
        this.scanFlag = scanFlag;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String toString() {
        return "SendCheckInfo{scanFlag=" + this.scanFlag + ", id=" + this.id + ", nickname='" + this.nickname + '\'' + '}';
    }
}
