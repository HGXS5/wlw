//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev;

import com.fasterxml.jackson.core.type.TypeReference;
import com.grean.station.http.model.BodyData;
import com.grean.station.http.model.PlatformData;
import com.grean.station.http.model.WaterData;
import com.grean.station.http.utils.HttpClient;
import com.grean.station.http.utils.JsonUtil;
import com.grean.station.service.MonitorService;
import com.grean.station.utils.TimeHelper;
import com.grean.station.utils.Utility;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Iterator;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Request.Builder;
import org.joda.time.DateTime;

public class DevPlatform extends DevService {
    private boolean onCmd = false;
    private boolean cmdResult = false;
    private String recvString = "";
    private DevPlatform.QueryThread queryThread;
    PlatformData platformData = new PlatformData();
    private static final TypeReference<BodyData> typeReference = new TypeReference<BodyData>() {
    };
    private String platformUrl = "http://water.greandata.com:9005/openapi/v1/water_data_hour";
    private String platformStcd = "5100000002";
    private String platformToken = "a4b8226c-dd4d-449a-b310-5410d9cdbfaa";

    public DevPlatform() {
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
        DateTime dateTime = new DateTime();
        Timestamp beginTime = new Timestamp(dateTime.minusHours(4).getMillis());
        Timestamp endTime = new Timestamp(dateTime.plusHours(4).getMillis());
        this.platformData.setBegin(TimeHelper.formatDataTime_yyyyMMdd(beginTime));
        this.platformData.setEnd(TimeHelper.formatDataTime_yyyyMMdd(endTime));
        this.platformData.setStcd(this.platformStcd);
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(mediaType, JsonUtil.toJson(this.platformData));
        OkHttpClient client = HttpClient.instance();
        Request request = (new Builder()).url(this.platformUrl).addHeader("Content-Type", "application/json").addHeader("token", this.platformToken).post(requestBody).build();

        try {
            Response response = client.newCall(request).execute();
            Throwable var9 = null;

            try {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        String tempString = response.body().string();
                        BodyData bodyData = (BodyData)JsonUtil.toObject(tempString, typeReference);
                        System.out.println(tempString);
                        Utility.logInfo("接收平台数据 " + tempString);
                        if (bodyData != null && bodyData.getData() != null) {
                            Iterator var12 = bodyData.getData().iterator();

                            while(var12.hasNext()) {
                                WaterData waterData = (WaterData)var12.next();
                                Timestamp timestamp = TimeHelper.parseDevTime(waterData.getDatatime());

                                for(int i = 0; i < waterData.getList().size() && i < this.channelVals.length; ++i) {
                                    if (waterData.getList().get(i) != null) {
                                        if (this.channelTime[i] == null) {
                                            this.channelVals[i] = (Double)waterData.getList().get(i);
                                            this.channelTime[i] = timestamp;
                                        } else if (timestamp.getTime() > this.channelTime[i].getTime()) {
                                            this.channelVals[i] = (Double)waterData.getList().get(i);
                                            this.channelTime[i] = timestamp;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    this.setStatus(true);
                } else {
                    this.setStatus(false);
                    Utility.logInfo("请求返回错误，请检查[{}]" + response);
                }
            } catch (Throwable var25) {
                var9 = var25;
                throw var25;
            } finally {
                if (response != null) {
                    if (var9 != null) {
                        try {
                            response.close();
                        } catch (Throwable var24) {
                            var9.addSuppressed(var24);
                        }
                    } else {
                        response.close();
                    }
                }

            }
        } catch (IOException var27) {
            this.setStatus(false);
            Utility.logInfo("IO错误" + var27);
        } catch (Exception var28) {
            this.setStatus(false);
            Utility.logInfo("其他错误" + var28);
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
        this.queryThread = new DevPlatform.QueryThread();
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
                DevPlatform.this.doDevCmd(cmdType, cmdParam, cmdQN);
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
                    DevPlatform.this.doQuery();
                    Thread.sleep(60000L);
                } catch (Exception var3) {
                    break;
                }
            }

        }
    }
}
