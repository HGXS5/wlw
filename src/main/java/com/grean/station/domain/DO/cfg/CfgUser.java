//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.DO.cfg;

import java.sql.Timestamp;

public class CfgUser {
  private Integer id;
  private String user;
  private String nick;
  private String password;
  private String group_name;
  private Integer group_id;
  private String description;
  private Timestamp add_time;
  private Timestamp update_time;
  private Timestamp login_time;
  private String access;
  private Timestamp access_time;

  public CfgUser() {
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUser() {
    return this.user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getNick() {
    return this.nick;
  }

  public void setNick(String nick) {
    this.nick = nick;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getGroup_name() {
    return this.group_name;
  }

  public void setGroup_name(String group_name) {
    this.group_name = group_name;
  }

  public Integer getGroup_id() {
    return this.group_id;
  }

  public void setGroup_id(Integer group_id) {
    this.group_id = group_id;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Timestamp getAdd_time() {
    return this.add_time;
  }

  public void setAdd_time(Timestamp add_time) {
    this.add_time = add_time;
  }

  public Timestamp getUpdate_time() {
    return this.update_time;
  }

  public void setUpdate_time(Timestamp update_time) {
    this.update_time = update_time;
  }

  public Timestamp getLogin_time() {
    return this.login_time;
  }

  public void setLogin_time(Timestamp login_time) {
    this.login_time = login_time;
  }

  public String getAccess() {
    return this.access;
  }

  public void setAccess(String access) {
    this.access = access;
  }

  public Timestamp getAccess_time() {
    return this.access_time;
  }

  public void setAccess_time(Timestamp access_time) {
    this.access_time = access_time;
  }
}
