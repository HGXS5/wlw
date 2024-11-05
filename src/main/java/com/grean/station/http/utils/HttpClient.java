//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.http.utils;

import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;

public class HttpClient {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static OkHttpClient CLIENT;

    public HttpClient() {
    }

    public static OkHttpClient instance() {
        return CLIENT;
    }

    static {
        CLIENT = (new Builder()).connectTimeout(30L, TimeUnit.SECONDS).readTimeout(6L, TimeUnit.SECONDS).build();
    }
}
