//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

public class UploadInfo {
    boolean used;
    String name;
    int protocal;
    String server;
    int port;
    String mn;
    String password;
    int heartbeat_rate;
    int realtime_rate;
    int sysstate_rate;
    int devstate_rate;
    int syslog_rate;
    int devlog_rate;
    int overtime;
    int recount;

    public UploadInfo() {
    }

    public boolean isUsed() {
        return this.used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProtocal() {
        return this.protocal;
    }

    public void setProtocal(int protocal) {
        this.protocal = protocal;
    }

    public String getServer() {
        return this.server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getMn() {
        return this.mn;
    }

    public void setMn(String mn) {
        this.mn = mn;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getHeartbeat_rate() {
        return this.heartbeat_rate;
    }

    public void setHeartbeat_rate(int heartbeat_rate) {
        this.heartbeat_rate = heartbeat_rate;
    }

    public int getRealtime_rate() {
        return this.realtime_rate;
    }

    public void setRealtime_rate(int realtime_rate) {
        this.realtime_rate = realtime_rate;
    }

    public int getSysstate_rate() {
        return this.sysstate_rate;
    }

    public void setSysstate_rate(int sysstate_rate) {
        this.sysstate_rate = sysstate_rate;
    }

    public int getDevstate_rate() {
        return this.devstate_rate;
    }

    public void setDevstate_rate(int devstate_rate) {
        this.devstate_rate = devstate_rate;
    }

    public int getSyslog_rate() {
        return this.syslog_rate;
    }

    public void setSyslog_rate(int syslog_rate) {
        this.syslog_rate = syslog_rate;
    }

    public int getDevlog_rate() {
        return this.devlog_rate;
    }

    public void setDevlog_rate(int devlog_rate) {
        this.devlog_rate = devlog_rate;
    }

    public int getOvertime() {
        return this.overtime;
    }

    public void setOvertime(int overtime) {
        this.overtime = overtime;
    }

    public int getRecount() {
        return this.recount;
    }

    public void setRecount(int recount) {
        this.recount = recount;
    }
}
