package com.mppkvvcl.jteone.dtos.templates.pdfbill;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MeterReplacementDetail {

    private int index;

    private String meterDetail;
    private LocalDate replacementDate;
    private BigDecimal previousRead;
    private BigDecimal currentRead;
    private BigDecimal consumption;
    private BigDecimal proratedCurrentBill;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getMeterDetail() {
        return meterDetail;
    }

    public void setMeterDetail(String meterDetail) {
        this.meterDetail = meterDetail;
    }

    public LocalDate getReplacementDate() {
        return replacementDate;
    }

    public void setReplacementDate(LocalDate replacementDate) {
        this.replacementDate = replacementDate;
    }

    public BigDecimal getPreviousRead() {
        return previousRead;
    }

    public void setPreviousRead(BigDecimal previousRead) {
        this.previousRead = previousRead;
    }

    public BigDecimal getCurrentRead() {
        return currentRead;
    }

    public void setCurrentRead(BigDecimal currentRead) {
        this.currentRead = currentRead;
    }

    public BigDecimal getConsumption() {
        return consumption;
    }

    public void setConsumption(BigDecimal consumption) {
        this.consumption = consumption;
    }

    public BigDecimal getProratedCurrentBill() {
        return proratedCurrentBill;
    }

    public void setProratedCurrentBill(BigDecimal proratedCurrentBill) {
        this.proratedCurrentBill = proratedCurrentBill;
    }

    @Override
    public String toString() {
        return "MeterReplacementDetail{" +
                "index=" + index +
                ", meterDetail='" + meterDetail + '\'' +
                ", replacementDate=" + replacementDate +
                ", previousRead=" + previousRead +
                ", currentRead=" + currentRead +
                ", consumption=" + consumption +
                ", proratedCurrentBill=" + proratedCurrentBill +
                '}';
    }
}
