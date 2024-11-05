//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.response;

import com.grean.station.domain.request.DrawLegend;
import java.sql.Timestamp;
import java.util.List;

public class ChartVO {
    private List<String> names;
    private List<Timestamp> times;
    private List<EChartStackVO> dataset;
    private List<DrawLegend> drawLegends;

    public ChartVO() {
    }

    public List<Timestamp> getTimes() {
        return this.times;
    }

    public void setTimes(List<Timestamp> times) {
        this.times = times;
    }

    public List<String> getNames() {
        return this.names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<EChartStackVO> getDataset() {
        return this.dataset;
    }

    public void setDataset(List<EChartStackVO> dataset) {
        this.dataset = dataset;
    }

    public List<DrawLegend> getDrawLegends() {
        return this.drawLegends;
    }

    public void setDrawLegends(List<DrawLegend> drawLegends) {
        this.drawLegends = drawLegends;
    }
}
