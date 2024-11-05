//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.grean.station.http.model.DataHandler;
import com.grean.station.http.model.ThData;
import com.grean.station.http.utils.HttpClient;
import com.grean.station.http.utils.JsonUtil;
import com.grean.station.utils.Utility;
import java.io.IOException;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.Request.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PullService {
    private static final Logger log = LoggerFactory.getLogger(PullService.class);
    private static final TypeReference<List<ThData>> typeReference = new TypeReference<List<ThData>>() {
    };
    private final TokenService tokenService;
    private final DataHandler dataHandler;

    public PullService(TokenService tokenService, DataHandler dataHandler) {
        this.tokenService = tokenService;
        this.dataHandler = dataHandler;
    }

    public void start() {
        while(true) {
            OkHttpClient client = HttpClient.instance();
            Request request = (new Builder()).url("https://dev.thuwater.com/api/data/realtime").get().addHeader("X-TH-TOKEN", this.tokenService.genToken()).addHeader("Host", "dev.thuwater.com").build();
            long sleepTime = 0L;

            try {
                Response response = client.newCall(request).execute();
                Throwable var6 = null;

                try {
                    if (response.isSuccessful()) {
                        ResponseBody body = response.body();
                        if (body == null) {
                            sleepTime = 300000L;
                        } else {
                            List<ThData> thDataList = (List)JsonUtil.toObject(body.string(), typeReference);
                            if (thDataList.size() == 0) {
                                sleepTime = 300000L;
                            } else if (thDataList.size() < 100) {
                                this.dataHandler.handleDatas(thDataList);
                                sleepTime = 60000L;
                            } else {
                                this.dataHandler.handleDatas(thDataList);
                            }
                        }
                    } else {
                        Utility.logInfo("请求返回错误，请检查[{}]" + response);
                        this.sleep(60000L);
                        if (response.code() == 401) {
                            this.tokenService.genToken(true);
                        }
                    }
                } catch (Throwable var29) {
                    var6 = var29;
                    throw var29;
                } finally {
                    if (response != null) {
                        if (var6 != null) {
                            try {
                                response.close();
                            } catch (Throwable var28) {
                                var6.addSuppressed(var28);
                            }
                        } else {
                            response.close();
                        }
                    }

                }
            } catch (IOException var31) {
                Utility.logInfo("IO错误" + var31);
                this.sleep(60000L);
            } catch (Exception var32) {
                Utility.logInfo("其他错误" + var32);
                this.sleep(60000L);
            } finally {
                this.sleep(sleepTime);
            }
        }
    }

    private void sleep(long time) {
        try {
            Utility.logInfo("sleep for " + time);
            Thread.sleep(time);
        } catch (InterruptedException var4) {
            Utility.logInfo("线程退出" + var4);
        }

    }
}
