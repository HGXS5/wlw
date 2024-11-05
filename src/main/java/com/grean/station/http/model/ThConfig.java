//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.http.model;

public class ThConfig {
    private String username;
    private String password;
    private String placeid;
    private String equipid;

    public ThConfig(String username, String password, String placeid, String equipid) {
        this.username = username;
        this.password = password;
        this.placeid = placeid;
        this.equipid = equipid;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPlaceid() {
        return this.placeid;
    }

    public void setPlaceid(String placeid) {
        this.placeid = placeid;
    }

    public String getEquipid() {
        return this.equipid;
    }

    public void setEquipid(String equipid) {
        this.equipid = equipid;
    }
}
