package com.mppkvvcl.jteone.dtos.templates.pdfbill;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ReadInformation {

    private String meterSrNo;
    private String date;
    private String type;
    private String source;

    private boolean isTOD;

    private BigDecimal solarPlantCapacity;
    private BigDecimal residualUnit;
    private boolean isChild;

    private List<ReadDetail> readDetailList;

    private BigDecimal residualUnitAdjusted;
    private BigDecimal netMeteredUnit;
    private BigDecimal netBilledUnit;

    public String getMeterSrNo() {
        return meterSrNo;
    }

    public void setMeterSrNo(String meterSrNo) {
        this.meterSrNo = meterSrNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isTOD() {
        return isTOD;
    }

    public void setTOD(boolean TOD) {
        isTOD = TOD;
    }

    public BigDecimal getSolarPlantCapacity() {
        return solarPlantCapacity;
    }

    public void setSolarPlantCapacity(BigDecimal solarPlantCapacity) {
        this.solarPlantCapacity = solarPlantCapacity;
    }

    public BigDecimal getResidualUnit() {
        return residualUnit;
    }

    public void setResidualUnit(BigDecimal residualUnit) {
        this.residualUnit = residualUnit;
    }

    public boolean isChild() {
        return isChild;
    }

    public void setChild(boolean child) {
        isChild = child;
    }

    public BigDecimal getResidualUnitAdjusted() {
        return residualUnitAdjusted;
    }

    public void setResidualUnitAdjusted(BigDecimal residualUnitAdjusted) {
        this.residualUnitAdjusted = residualUnitAdjusted;
    }

    public BigDecimal getNetMeteredUnit() {
        return netMeteredUnit;
    }

    public void setNetMeteredUnit(BigDecimal netMeteredUnit) {
        this.netMeteredUnit = netMeteredUnit;
    }

    public BigDecimal getNetBilledUnit() {
        return netBilledUnit;
    }

    public void setNetBilledUnit(BigDecimal netBilledUnit) {
        this.netBilledUnit = netBilledUnit;
    }

    public List<ReadDetail> getReadDetailList() {
        return readDetailList;
    }

    public void setReadDetailList(List<ReadDetail> readDetailList) {
        this.readDetailList = readDetailList;
    }

    public void addRead(int index, String type, BigDecimal currentRead, BigDecimal previousRead, BigDecimal mf, BigDecimal meteredUnit, BigDecimal assessment, BigDecimal billedUnit) {
        if (readDetailList == null) readDetailList = new ArrayList<>();

        ReadDetail readDetail = new ReadDetail();
        readDetail.setIndex(index);
        readDetail.setType(type);
        readDetail.setCurrentRead(currentRead);
        readDetail.setPreviousRead(previousRead);
        readDetail.setMf(mf);
        readDetail.setMeteredUnit(meteredUnit);
        readDetail.setAssessment(assessment);
        readDetail.setBilledUnit(billedUnit);
        readDetailList.add(readDetail);
    }

    public ReadInformation() {
    }
}
