//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service;
import com.grean.station.domain.DO.cfg.CfgFactor;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DbService {
    List<CfgFactor> cfgFactorList;

    public DbService() {
    }

    public List<CfgFactor> getCfgFactorList() {
        return this.cfgFactorList;
    }

    public void setCfgFactorList(List<CfgFactor> cfgFactorList) {
        this.cfgFactorList = cfgFactorList;
    }
}
