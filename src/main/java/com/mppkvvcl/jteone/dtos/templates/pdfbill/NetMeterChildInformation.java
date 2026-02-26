package com.mppkvvcl.jteone.dtos.templates.pdfbill;

import java.math.BigDecimal;

public class NetMeterChildInformation {

    private int index;

    private String consumerNo;
    private BigDecimal ratio;
    private BigDecimal currentRead;
    private BigDecimal previousRead;
    private BigDecimal mf;
    private BigDecimal meterConsumption;
    private BigDecimal assessment;
    private BigDecimal finalConsumption;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getConsumerNo() {
        return consumerNo;
    }

    public void setConsumerNo(String consumerNo) {
        this.consumerNo = consumerNo;
    }

    public BigDecimal getRatio() {
        return ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
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

    @Override
    public String toString() {
        return "NetMeterChildInformation{" +
                "index=" + index +
                ", consumerNo='" + consumerNo + '\'' +
                ", ratio=" + ratio +
                ", currentRead=" + currentRead +
                ", previousRead=" + previousRead +
                ", mf=" + mf +
                ", meterConsumption=" + meterConsumption +
                ", assessment=" + assessment +
                ", finalConsumption=" + finalConsumption +
                '}';
    }
}
