//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.response;

public class Pagination {
    private Integer start_page;
    private Integer limit;
    private Integer start;
    private Integer totals;
    private Integer total_page;

    public Pagination() {
    }

    public void initPagination() {
        if (this.limit == null || this.limit < 1) {
            this.limit = 20;
        }

        if (this.start_page == null || this.start_page < 1) {
            this.start_page = 1;
        }

        this.start = (this.start_page - 1) * this.limit;
    }

    public String toString() {
        return "Pagination{start=" + this.start + ", limit=" + this.limit + ", totals=" + this.totals + ", start_page=" + this.start_page + ", total_page=" + this.total_page + '}';
    }

    public Integer getStart() {
        return this.start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return this.limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getTotals() {
        return this.totals;
    }

    public void setTotals(Integer totals) {
        this.totals = totals;
    }

    public Integer getStart_page() {
        return this.start_page;
    }

    public void setStart_page(Integer start_page) {
        this.start_page = start_page;
    }

    public Integer getTotal_page() {
        return this.total_page;
    }

    public void setTotal_page(Integer total_page) {
        this.total_page = total_page;
    }
}
