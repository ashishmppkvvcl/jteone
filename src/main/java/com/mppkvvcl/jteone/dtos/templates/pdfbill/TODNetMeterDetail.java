package com.mppkvvcl.jteone.dtos.templates.pdfbill;

import java.math.BigDecimal;

public class TODNetMeterDetail {

    private int index;
    private String type; //TOD1, TOD2, TOD3, TOD4
    private String demandHour; //Peak, Off Peak
    private String netMeterType; //IMPORT, EXPORT

    private BigDecimal currentRead;
    private BigDecimal previousRead;
    private BigDecimal meterConsumption;
    private BigDecimal assessment;
    private BigDecimal finalConsumption;
    private BigDecimal netConsumption;
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

    public String getNetMeterType() {
        return netMeterType;
    }

    public void setNetMeterType(String netMeterType) {
        this.netMeterType = netMeterType;
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

    public BigDecimal getMeterConsumption() {
        return meterConsumption;
    }

    public void setMeterConsumption(BigDecimal meterConsumption) {
        this.meterConsumption = meterConsumption;
    }

    public BigDecimal getAssessment() {
        return assessment;
    }

    public void setAssessment(BigDecimal assessment) {
        this.assessment = assessment;
    }

    public BigDecimal getFinalConsumption() {
        return finalConsumption;
    }

    public void setFinalConsumption(BigDecimal finalConsumption) {
        this.finalConsumption = finalConsumption;
    }

    public BigDecimal getNetConsumption() {
        return netConsumption;
    }

    public void setNetConsumption(BigDecimal netConsumption) {
        this.netConsumption = netConsumption;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TODNetMeterDetail() {
    }

    public TODNetMeterDetail(int index, String type, String demandHour, String netMeterType, BigDecimal currentRead, BigDecimal previousRead, BigDecimal meterConsumption, BigDecimal assessment, BigDecimal finalConsumption, BigDecimal netConsumption, BigDecimal amount) {
        this.index = index;
        this.type = type;
        this.demandHour = demandHour;
        this.netMeterType = netMeterType;
        this.currentRead = currentRead;
        this.previousRead = previousRead;
        this.meterConsumption = meterConsumption;
        this.assessment = assessment;
        this.finalConsumption = finalConsumption;
        this.netConsumption = netConsumption;
        this.amount = amount;
    }
}
