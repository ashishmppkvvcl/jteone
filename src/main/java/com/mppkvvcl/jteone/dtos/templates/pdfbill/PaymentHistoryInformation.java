package com.mppkvvcl.jteone.dtos.templates.pdfbill;


public class PaymentHistoryInformation {

    private int index;

    private long amount;
    private String payDate;
    private String payMonth;
    private String cacNo;
    private String payMode;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPayMonth() {
        return payMonth;
    }

    public void setPayMonth(String payMonth) {
        this.payMonth = payMonth;
    }

    public String getCacNo() {
        return cacNo;
    }

    public void setCacNo(String cacNo) {
        this.cacNo = cacNo;
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public PaymentHistoryInformation() {
    }

    public PaymentHistoryInformation(int index, long amount, String payDate, String payMonth, String cacNo, String payMode) {
        this.index = index;
        this.amount = amount;
        this.payDate = payDate;
        this.payMonth = payMonth;
        this.cacNo = cacNo;
        this.payMode = payMode;
    }
}
