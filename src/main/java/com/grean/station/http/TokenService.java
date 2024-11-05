//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.http;

import com.grean.station.http.utils.DateTimeUtil;
import com.grean.station.http.utils.HttpClient;
import com.grean.station.http.utils.JsonUtil;
import com.grean.station.utils.Utility;
import java.time.LocalDateTime;
import java.util.Map;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.FormBody.Builder;

public class TokenService {
    private volatile String token;
    private volatile LocalDateTime expireTime = LocalDateTime.now().minusYears(1L);
    private RequestBody tokenFreshbody;
    private Request tokenFreshRequest;
    private final Object lock = new Object();

    public TokenService(String username, String password) {
        this.tokenFreshbody = (new Builder()).add("username", username).add("password", password).build();
        this.tokenFreshRequest = (new okhttp3.Request.Builder()).url("https://dev.thuwater.com/api/permission/token").post(this.tokenFreshbody).build();
        this.init();
    }

    private void init() {
        if (!this.freshToken(true, 3, true)) {
            String msg = "初始化获取token失败";
            Utility.logInfo(msg);
            throw new IllegalStateException(msg);
        }
    }

    private boolean freshToken(boolean retry, int maxRetry, boolean forceFresh) {
        if (!forceFresh && this.expireTime.isAfter(LocalDateTime.now())) {
            return true;
        } else {
            synchronized(this.lock) {
                if (this.expireTime.isAfter(LocalDateTime.now())) {
                    return true;
                } else {
                    OkHttpClient client = HttpClient.instance();
                    int retryCount = 0;

                    do {
                        ++retryCount;
                        Call call = client.newCall(this.tokenFreshRequest);

                        try {
                            Response response = call.execute();
                            Throwable var9 = null;

                            try {
                                if (response.isSuccessful() && response.body() != null) {
                                    Map<String, String> tokenMap = JsonUtil.toMapFromBytes(response.body().bytes());
                                    this.token = (String)tokenMap.get("token");
                                    this.expireTime = DateTimeUtil.format((String)tokenMap.get("validDate"));
                                    boolean var11 = true;
                                    return var11;
                                }

                                Utility.logInfo(response.toString());
                            } catch (Throwable var26) {
                                var9 = var26;
                                throw var26;
                            } finally {
                                if (response != null) {
                                    if (var9 != null) {
                                        try {
                                            response.close();
                                        } catch (Throwable var25) {
                                            var9.addSuppressed(var25);
                                        }
                                    } else {
                                        response.close();
                                    }
                                }

                            }
                        } catch (Exception var28) {
                            Utility.logInfo("重试第" + retryCount + "次 " + var28);

                            try {
                                Thread.sleep(30000L);
                            } catch (InterruptedException var24) {
                            }
                        }
                    } while(retry && retryCount <= maxRetry + 1);

                    return false;
                }
            }
        }
    }

    public String genToken() {
        if (this.expireTime.isBefore(LocalDateTime.now())) {
            this.freshToken(true, 3, true);
        }

        return this.token;
    }

    public String genToken(boolean forceFresh) {
        if (forceFresh) {
            this.freshToken(true, 3, forceFresh);
        }

        return this.genToken();
    }
}
