//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.exception;

public enum ErrorCode {
    API_ERROR("API_ERROR"),
    FIELD_ERROR("FIELD_ERROR"),
    NOTFOUND_OR_UNAUTHORIZED("NOTFOUND_OR_UNAUTHORIZED"),
    UNAUTHENTICATED("UNAUTHENTICATED"),
    PAGE404("PAGE404");

    private String code;

    public String getCode() {
        return this.code;
    }

    private ErrorCode(String c) {
        this.code = c;
    }
}
