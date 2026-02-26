package com.mppkvvcl.jteone.dtos.templates.pdfbill;

import java.math.BigDecimal;

public class TODDetail {

    private int index;
    private String type; //TOD1, TOD2, TOD3, TOD4
    private String demandHour; //Peak, Off Peak

    private String timing;
    private BigDecimal unit;
    private BigDecimal amount;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDemandHour() {
        return demandHour;
    }

    public void setDemandHour(String demandHour) {
        this.demandHour = demandHour;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public BigDecimal getUnit() {
        return unit;
    }

    public void setUnit(BigDecimal unit) {
        this.unit = unit;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TODDetail() {
    }

    public TODDetail(int index, String type, String demandHour, String timing, BigDecimal unit, BigDecimal amount) {
        this.index = index;
        this.type = type;
        this.demandHour = demandHour;
        this.timing = timing;
        this.unit = unit;
        this.amount = amount;
    }
}
