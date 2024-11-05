//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.response;

import org.joda.time.DateTime;

public class TokenUser {
    String token;
    String name;
    DateTime timeout;

    public TokenUser(String strToken, String strName, DateTime timeOut) {
        this.token = strToken;
        this.name = strName;
        this.timeout = timeOut;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DateTime getTimeout() {
        return this.timeout;
    }

    public void setTimeout(DateTime timeout) {
        this.timeout = timeout;
    }
}
