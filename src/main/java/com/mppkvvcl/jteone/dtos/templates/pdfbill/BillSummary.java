package com.mppkvvcl.jteone.dtos.templates.pdfbill;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BillSummary {

    private String billNo;
    private String billMonth;
    private BigDecimal payableAmount;
    private BigDecimal payableTillDueDate;
    private BigDecimal payableAfterDueDate;
    private BigDecimal currentBill;
    private BigDecimal arrear;

    private String paymentQuickResponseString;
    private String paymentQuickResponseBase64;
    private String consumptionTrendBarChartBase64;

    private LocalDate dueDate;
    private LocalDate chequeDueDate;
    private BigDecimal consumption;
    private String meterReaderName;
    private String meterReaderContactNo;

    private LocalDate billDate;
    private String billType;
    private String billBasis;
    private BigDecimal dailyAverageConsumption;
    private BigDecimal dailyAverageBill;
    private BigDecimal sdHeld;
    private BigDecimal sdPending;
    private LocalDate pdcDate;

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getBillMonth() {
        return billMonth;
    }

    public void setBillMonth(String billMonth) {
        this.billMonth = billMonth;
    }

    public BigDecimal getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(BigDecimal payableAmount) {
        this.payableAmount = payableAmount;
    }

    public BigDecimal getPayableTillDueDate() {
        return payableTillDueDate;
    }

    public void setPayableTillDueDate(BigDecimal payableTillDueDate) {
        this.payableTillDueDate = payableTillDueDate;
    }

    public BigDecimal getPayableAfterDueDate() {
        return payableAfterDueDate;
    }

    public void setPayableAfterDueDate(BigDecimal payableAfterDueDate) {
        this.payableAfterDueDate = payableAfterDueDate;
    }

    public BigDecimal getCurrentBill() {
        return currentBill;
    }

    public void setCurrentBill(BigDecimal currentBill) {
        this.currentBill = currentBill;
    }

    public BigDecimal getArrear() {
        return arrear;
    }

    public void setArrear(BigDecimal arrear) {
        this.arrear = arrear;
    }

    public String getPaymentQuickResponseString() {
        return paymentQuickResponseString;
    }

    public void setPaymentQuickResponseString(String paymentQuickResponseString) {
        this.paymentQuickResponseString = paymentQuickResponseString;
    }

    public String getPaymentQuickResponseBase64() {
        return paymentQuickResponseBase64;
    }

    public void setPaymentQuickResponseBase64(String paymentQuickResponseBase64) {
        this.paymentQuickResponseBase64 = paymentQuickResponseBase64;
    }

    public String getConsumptionTrendBarChartBase64() {
        return consumptionTrendBarChartBase64;
    }

    public void setConsumptionTrendBarChartBase64(String consumptionTrendBarChartBase64) {
        this.consumptionTrendBarChartBase64 = consumptionTrendBarChartBase64;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getChequeDueDate() {
        return chequeDueDate;
    }

    public void setChequeDueDate(LocalDate chequeDueDate) {
        this.chequeDueDate = chequeDueDate;
    }

    public BigDecimal getConsumption() {
        return consumption;
    }

    public void setConsumption(BigDecimal consumption) {
        this.consumption = consumption;
    }

    public String getMeterReaderName() {
        return meterReaderName;
    }

    public void setMeterReaderName(String meterReaderName) {
        this.meterReaderName = meterReaderName;
    }

    public String getMeterReaderContactNo() {
        return meterReaderContactNo;
    }

    public void setMeterReaderContactNo(String meterReaderContactNo) {
        this.meterReaderContactNo = meterReaderContactNo;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBillBasis() {
        return billBasis;
    }

    public void setBillBasis(String billBasis) {
        this.billBasis = billBasis;
    }

    public BigDecimal getDailyAverageConsumption() {
        return dailyAverageConsumption;
    }

    public void setDailyAverageConsumption(BigDecimal dailyAverageConsumption) {
        this.dailyAverageConsumption = dailyAverageConsumption;
    }

    public BigDecimal getDailyAverageBill() {
        return dailyAverageBill;
    }

    public void setDailyAverageBill(BigDecimal dailyAverageBill) {
        this.dailyAverageBill = dailyAverageBill;
    }

    public BigDecimal getSdHeld() {
        return sdHeld;
    }

    public void setSdHeld(BigDecimal sdHeld) {
        this.sdHeld = sdHeld;
    }

    public BigDecimal getSdPending() {
        return sdPending;
    }

    public void setSdPending(BigDecimal sdPending) {
        this.sdPending = sdPending;
    }

    public LocalDate getPdcDate() {
        return pdcDate;
    }

    public void setPdcDate(LocalDate pdcDate) {
        this.pdcDate = pdcDate;
    }

    public BillSummary() {
    }

    public BillSummary(String billNo, String billMonth, BigDecimal payableAmount, BigDecimal payableTillDueDate, BigDecimal payableAfterDueDate, BigDecimal currentBill, BigDecimal arrear, String paymentQuickResponseString, String paymentQuickResponseBase64, String consumptionTrendBarChartBase64, LocalDate dueDate, LocalDate chequeDueDate, BigDecimal consumption, String meterReaderName, String meterReaderContactNo, LocalDate billDate, String billType, String billBasis, BigDecimal dailyAverageConsumption, BigDecimal dailyAverageBill, BigDecimal sdHeld, BigDecimal sdPending, LocalDate pdcDate) {
        this.billNo = billNo;
        this.billMonth = billMonth;
        this.payableAmount = payableAmount;
        this.payableTillDueDate = payableTillDueDate;
        this.payableAfterDueDate = payableAfterDueDate;
        this.currentBill = currentBill;
        this.arrear = arrear;
        this.paymentQuickResponseString = paymentQuickResponseString;
        this.paymentQuickResponseBase64 = paymentQuickResponseBase64;
        this.consumptionTrendBarChartBase64 = consumptionTrendBarChartBase64;
        this.dueDate = dueDate;
        this.chequeDueDate = chequeDueDate;
        this.consumption = consumption;
        this.meterReaderName = meterReaderName;
        this.meterReaderContactNo = meterReaderContactNo;
        this.billDate = billDate;
        this.billType = billType;
        this.billBasis = billBasis;
        this.dailyAverageConsumption = dailyAverageConsumption;
        this.dailyAverageBill = dailyAverageBill;
        this.sdHeld = sdHeld;
        this.sdPending = sdPending;
        this.pdcDate = pdcDate;
    }
}
