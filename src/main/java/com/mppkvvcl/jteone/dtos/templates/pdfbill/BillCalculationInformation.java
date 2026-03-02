package com.mppkvvcl.jteone.dtos.templates.pdfbill;

public class BillCalculationInformation {

    private int index;

    private String head;
    private String slab;
    private String rate;
    private String unit;
    private String amount;

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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public BillCalculationInformation() {
    }

    public BillCalculationInformation(int index, String head, String slab, String rate, String unit, String amount) {
        this.index = index;
        this.head = head;
        this.slab = slab;
        this.rate = rate;
        this.unit = unit;
        this.amount = amount;
    }
}
