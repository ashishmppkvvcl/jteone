package com.mppkvvcl.jteone.dtos.templates.pdfbill;

import java.math.BigDecimal;

public class ReadHistoryInformation {

    private int index;

    private String billMonth;
    private String readDate;
    private BigDecimal reading;
    private BigDecimal unit;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public BigDecimal getUnit() {
        return unit;
    }

    public void setUnit(BigDecimal unit) {
        this.unit = unit;
    }

    public BigDecimal getReading() {
        return reading;
    }

    public void setReading(BigDecimal reading) {
        this.reading = reading;
    }

    public String getReadDate() {
        return readDate;
    }

    public void setReadDate(String readDate) {
        this.readDate = readDate;
    }

    public String getBillMonth() {
        return billMonth;
    }

    public void setBillMonth(String billMonth) {
        this.billMonth = billMonth;
    }

    public ReadHistoryInformation() {
    }

    public ReadHistoryInformation(int index, String billMonth, String readDate, BigDecimal reading, BigDecimal unit) {
        this.index = index;
        this.billMonth = billMonth;
        this.readDate = readDate;
        this.reading = reading;
        this.unit = unit;
    }
}
