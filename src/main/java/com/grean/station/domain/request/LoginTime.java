//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

import java.sql.Timestamp;

public class LoginTime {
  String user;
  Timestamp login_time;

  public LoginTime() {
  }

  public String getUser() {
    return this.user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public Timestamp getLogin_time() {
    return this.login_time;
  }

  public void setLogin_time(Timestamp login_time) {
    this.login_time = login_time;
  }
}
