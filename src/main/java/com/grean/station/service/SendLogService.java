//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service;

import com.grean.station.dao.CfgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendLogService {
    private String log_server = "http://192.168.188.208:5010";
    @Autowired
    CfgMapper cfgMapper;

    public SendLogService() {
    }

    public void postdLog(String logInfo) {
    }

    public String postdCheckCode(String codeInfo) {
        return "";
    }
}
