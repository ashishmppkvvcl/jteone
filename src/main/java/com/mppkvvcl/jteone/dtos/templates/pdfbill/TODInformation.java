package com.mppkvvcl.jteone.dtos.templates.pdfbill;

import java.math.BigDecimal;
import java.util.List;

public class TODInformation {

    private List<TODDetail> todDetailList;
    private BigDecimal totalUnit;
    private BigDecimal totalAmount;
    private String todPieChartBase64Image;

    private List<TODNetMeterDetail> todNetMeterDetailList;

    public List<TODDetail> getTodDetailList() {
        return todDetailList;
    }

    public void setTodDetailList(List<TODDetail> todDetailList) {
        this.todDetailList = todDetailList;
    }

    public BigDecimal getTotalUnit() {
        return totalUnit;
    }

    public void setTotalUnit(BigDecimal totalUnit) {
        this.totalUnit = totalUnit;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTodPieChartBase64Image() {
        return todPieChartBase64Image;
    }

    public void setTodPieChartBase64Image(String todPieChartBase64Image) {
        this.todPieChartBase64Image = todPieChartBase64Image;
    }

    public List<TODNetMeterDetail> getTodNetMeterDetailList() {
        return todNetMeterDetailList;
    }

    public void setTodNetMeterDetailList(List<TODNetMeterDetail> todNetMeterDetailList) {
        this.todNetMeterDetailList = todNetMeterDetailList;
    }
}
