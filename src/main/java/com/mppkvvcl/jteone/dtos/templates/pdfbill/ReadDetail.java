package com.mppkvvcl.jteone.dtos.templates.pdfbill;

import java.math.BigDecimal;

public class ReadDetail {

    private int index;
    private String type;

    private BigDecimal currentRead;
    private BigDecimal previousRead;
    private BigDecimal mf;
    private BigDecimal meteredUnit;
    private BigDecimal assessment;
    private BigDecimal billedUnit;

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

    public BigDecimal getCurrentRead() {
        return currentRead;
    }

    public void setCurrentRead(BigDecimal currentRead) {
        this.currentRead = currentRead;
    }

    public BigDecimal getPreviousRead() {
        return previousRead;
    }

    public void setPreviousRead(BigDecimal previousRead) {
        this.previousRead = previousRead;
    }

    public BigDecimal getMf() {
        return mf;
    }

    public void setMf(BigDecimal mf) {
        this.mf = mf;
    }

    public BigDecimal getMeteredUnit() {
        return meteredUnit;
    }

    public void setMeteredUnit(BigDecimal meteredUnit) {
        this.meteredUnit = meteredUnit;
    }

    public BigDecimal getAssessment() {
        return assessment;
    }

    public void setAssessment(BigDecimal assessment) {
        this.assessment = assessment;
    }

    public BigDecimal getBilledUnit() {
        return billedUnit;
    }

    public void setBilledUnit(BigDecimal billedUnit) {
        this.billedUnit = billedUnit;
    }

    public ReadDetail() {
    }

    public ReadDetail(int index, String type, BigDecimal currentRead, BigDecimal previousRead, BigDecimal mf, BigDecimal meteredUnit, BigDecimal assessment, BigDecimal billedUnit) {
        this.index = index;
        this.type = type;
        this.currentRead = currentRead;
        this.previousRead = previousRead;
        this.mf = mf;
        this.meteredUnit = meteredUnit;
        this.assessment = assessment;
        this.billedUnit = billedUnit;
    }
}
