//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev;

import com.fasterxml.jackson.core.type.TypeReference;
import com.grean.station.http.model.LinghuBody;
import com.grean.station.http.model.LinghuData;
import com.grean.station.http.utils.HttpClient;
import com.grean.station.http.utils.JsonUtil;
import com.grean.station.service.MonitorService;
import com.grean.station.utils.Utility;
import java.io.IOException;
import java.util.Iterator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Request.Builder;

public class DevLinghu extends DevService {
    private boolean onCmd = false;
    private boolean cmdResult = false;
    private String recvString = "";
    private DevLinghu.QueryThread queryThread;
    private static final TypeReference<LinghuBody> typeReference = new TypeReference<LinghuBody>() {
    };
    private String platformUrl = "http://47.114.185.67:8080/AS_IOT/api/link/device/";
    private String platformStcd = "210818001";
    private String platformToken = "ada90795-1845-4913-85ff-d34ff26aabe4";

    public DevLinghu() {
    }

    public String getPlatformUrl() {
        return this.platformUrl;
    }

    public void setPlatformUrl(String platformUrl) {
        this.platformUrl = platformUrl;
    }

    public String getPlatformStcd() {
        return this.platformStcd;
    }

    public void setPlatformStcd(String platformStcd) {
        this.platformStcd = platformStcd;
    }

    public String getPlatformToken() {
        return this.platformToken;
    }

    public void setPlatformToken(String platformToken) {
        this.platformToken = platformToken;
    }

    public void doQuery() {
        OkHttpClient client = HttpClient.instance();
        Request request = (new Builder()).url(this.platformUrl + this.platformStcd + "/monitoring").addHeader("Content-Type", "application/json").addHeader("key", this.platformToken).get().build();

        try {
            Response response = client.newCall(request).execute();
            Throwable var4 = null;

            try {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        String tempString = response.body().string();
                        LinghuBody linghuBody = (LinghuBody)JsonUtil.toObject(tempString, typeReference);
                        System.out.println(tempString);
                        Utility.logInfo("菱湖蚁联平台数据 " + tempString);
                        if (linghuBody != null && linghuBody.getMonitorings() != null) {
                            int index = 0;
                            Iterator var8 = linghuBody.getMonitorings().iterator();

                            while(var8.hasNext()) {
                                LinghuData linghuData = (LinghuData)var8.next();
                                this.channelTime[index] = linghuData.getDataTime();
                                this.channelVals[index] = linghuData.getDataValue();
                                ++index;
                                if (index == this.channelVals.length) {
                                    break;
                                }
                            }
                        }
                    }

                    this.setStatus(true);
                } else {
                    this.setStatus(false);
                    Utility.logInfo("请求返回错误，请检查[{}]" + response);
                }
            } catch (Throwable var19) {
                var4 = var19;
                throw var19;
            } finally {
                if (response != null) {
                    if (var4 != null) {
                        try {
                            response.close();
                        } catch (Throwable var18) {
                            var4.addSuppressed(var18);
                        }
                    } else {
                        response.close();
                    }
                }

            }
        } catch (IOException var21) {
            this.setStatus(false);
            Utility.logInfo("IO错误" + var21);
        } catch (Exception var22) {
            this.setStatus(false);
            Utility.logInfo("其他错误" + var22);
        }

    }

    public void setStatus(boolean connected) {
        if (connected) {
            this.bConnect = true;
            this.disCount = 0;
        } else {
            ++this.disCount;
            if (this.disCount > 10) {
                this.bConnect = false;
            }
        }

    }

    public void startQuery() {
        this.queryThread = new DevLinghu.QueryThread();
        this.queryThread.start();
    }

    public void stopQuery() {
        if (this.queryThread != null) {
            this.queryThread.interrupt();
        }

    }

    public void saveResult() {
    }

    public boolean doDevCmd(int cmdType, String cmdParam, String cmdQN) {
        return false;
    }

    public void doDevCmdThread(final int cmdType, final String cmdParam, final String cmdQN) {
        this.fixedThreadPool.execute(new Runnable() {
            public void run() {
                DevLinghu.this.doDevCmd(cmdType, cmdParam, cmdQN);
            }
        });
    }

    public void doMeaCmd() {
        this.doDevCmdThread(1, (String)null, (String)null);
    }

    public void doStdCmd(String strQN, String strParam) {
        this.doDevCmdThread(2, strParam, strQN);
    }

    public void doZeroCmd(String strQN, String strParam) {
        this.doDevCmdThread(3, strParam, strQN);
    }

    public void doSpanCmd(String strQN, String strParam) {
        this.doDevCmdThread(4, strParam, strQN);
    }

    public void doBlnkCmd(String strQN, String strParam) {
        this.doDevCmdThread(5, strParam, strQN);
    }

    public void doParCmd(String strQN, String strParam) {
        this.doDevCmdThread(6, strParam, strQN);
    }

    public void doRcvrCmd(String strQN, String strParam) {
        this.doDevCmdThread(7, strParam, strQN);
    }

    public void doBlnkCal(String strQN, String strParam) {
    }

    public void doStdCal(String strQN, String strParam) {
    }

    public void doInitCmd() {
    }

    public void doStopCmd() {
        this.initDevState(0);
    }

    public void doRsetCmd() {
    }

    public void doSetTime(String strQN, String strParam) {
    }

    private class QueryThread extends Thread {
        private QueryThread() {
        }

        public void run() {
            super.run();

            try {
                Thread.sleep(3000L);
            } catch (Exception var2) {
            }

            while(!this.isInterrupted() && !MonitorService.restart) {
                try {
                    DevLinghu.this.doQuery();
                    Thread.sleep(60000L);
                } catch (Exception var3) {
                    break;
                }
            }

        }
    }
}
