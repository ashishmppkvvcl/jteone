package com.mppkvvcl.jteone.dtos.templates.pdfbill;

import java.math.BigDecimal;

public class BillCalculationInformation {

    private int index;

    private String head;
    private String slab;
    private BigDecimal rate;
    private BigDecimal unit;
    private BigDecimal amount;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getSlab() {
        return slab;
    }

    public void setSlab(String slab) {
        this.slab = slab;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
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

    @Override
    public String toString() {
        return "BillCalculationInformation{" +
                "index=" + index +
                ", head='" + head + '\'' +
                ", slab='" + slab + '\'' +
                ", rate=" + rate +
                ", unit=" + unit +
                ", amount=" + amount +
                '}';
    }
}
