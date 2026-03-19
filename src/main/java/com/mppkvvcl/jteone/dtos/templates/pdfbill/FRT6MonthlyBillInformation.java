package com.mppkvvcl.jteone.dtos.templates.pdfbill;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FRT6MonthlyBillInformation {

    private String billDuration;

    private String paymentQuickResponseString;
    private String paymentQuickResponseBase64;

    private LocalDate billDate;
    private LocalDate dueDate;
    private LocalDate chequeDueDate;

    private BigDecimal sdHeld;

    private String billNo;
    private String billMonth;
    private BigDecimal payableAmount;
    private BigDecimal payableTillDueDate;
    private BigDecimal payableAfterDueDate;

    private BigDecimal actualBill;
    private BigDecimal subsidy;
    private BigDecimal currentBill;
    private BigDecimal arrear;
    private BigDecimal surchargeDemanded;
    private BigDecimal ccbAdjustment;
    private BigDecimal subTotalOne;

    private long unpostedPayment;
    private BigDecimal netBill;

    private BigDecimal ots2025SurchargeWaiver;
    private BigDecimal ots2025UnpostedInstallmentAmount;
    private BigDecimal ots2025postedInstallmentAmount;

    public String getBillDuration() {
        return billDuration;
    }

    public void setBillDuration(String billDuration) {
        this.billDuration = billDuration;
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

    public LocalDate getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
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

    public BigDecimal getSdHeld() {
        return sdHeld;
    }

    public void setSdHeld(BigDecimal sdHeld) {
        this.sdHeld = sdHeld;
    }

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

    public BigDecimal getActualBill() {
        return actualBill;
    }

    public void setActualBill(BigDecimal actualBill) {
        this.actualBill = actualBill;
    }

    public BigDecimal getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(BigDecimal subsidy) {
        this.subsidy = subsidy;
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

    public BigDecimal getSurchargeDemanded() {
        return surchargeDemanded;
    }

    public void setSurchargeDemanded(BigDecimal surchargeDemanded) {
        this.surchargeDemanded = surchargeDemanded;
    }

    public BigDecimal getCcbAdjustment() {
        return ccbAdjustment;
    }

    public void setCcbAdjustment(BigDecimal ccbAdjustment) {
        this.ccbAdjustment = ccbAdjustment;
    }

    public BigDecimal getSubTotalOne() {
        return subTotalOne;
    }

    public void setSubTotalOne(BigDecimal subTotalOne) {
        this.subTotalOne = subTotalOne;
    }

    public long getUnpostedPayment() {
        return unpostedPayment;
    }

    public void setUnpostedPayment(long unpostedPayment) {
        this.unpostedPayment = unpostedPayment;
    }

    public BigDecimal getNetBill() {
        return netBill;
    }

    public void setNetBill(BigDecimal netBill) {
        this.netBill = netBill;
    }

    public BigDecimal getOts2025SurchargeWaiver() {
        return ots2025SurchargeWaiver;
    }

    public void setOts2025SurchargeWaiver(BigDecimal ots2025SurchargeWaiver) {
        this.ots2025SurchargeWaiver = ots2025SurchargeWaiver;
    }

    public BigDecimal getOts2025UnpostedInstallmentAmount() {
        return ots2025UnpostedInstallmentAmount;
    }

    public void setOts2025UnpostedInstallmentAmount(BigDecimal ots2025UnpostedInstallmentAmount) {
        this.ots2025UnpostedInstallmentAmount = ots2025UnpostedInstallmentAmount;
    }

    public BigDecimal getOts2025postedInstallmentAmount() {
        return ots2025postedInstallmentAmount;
    }

    public void setOts2025postedInstallmentAmount(BigDecimal ots2025postedInstallmentAmount) {
        this.ots2025postedInstallmentAmount = ots2025postedInstallmentAmount;
    }

    public FRT6MonthlyBillInformation() {
    }

    public FRT6MonthlyBillInformation(String billDuration, String paymentQuickResponseString, String paymentQuickResponseBase64, LocalDate billDate, LocalDate dueDate, LocalDate chequeDueDate, BigDecimal sdHeld, String billNo, String billMonth, BigDecimal payableAmount, BigDecimal payableTillDueDate, BigDecimal payableAfterDueDate, BigDecimal actualBill, BigDecimal subsidy, BigDecimal currentBill, BigDecimal arrear, BigDecimal surchargeDemanded, BigDecimal ccbAdjustment, BigDecimal subTotalOne, long unpostedPayment, BigDecimal netBill, BigDecimal ots2025SurchargeWaiver, BigDecimal ots2025UnpostedInstallmentAmount, BigDecimal ots2025postedInstallmentAmount) {
        this.billDuration = billDuration;
        this.paymentQuickResponseString = paymentQuickResponseString;
        this.paymentQuickResponseBase64 = paymentQuickResponseBase64;
        this.billDate = billDate;
        this.dueDate = dueDate;
        this.chequeDueDate = chequeDueDate;
        this.sdHeld = sdHeld;
        this.billNo = billNo;
        this.billMonth = billMonth;
        this.payableAmount = payableAmount;
        this.payableTillDueDate = payableTillDueDate;
        this.payableAfterDueDate = payableAfterDueDate;
        this.actualBill = actualBill;
        this.subsidy = subsidy;
        this.currentBill = currentBill;
        this.arrear = arrear;
        this.surchargeDemanded = surchargeDemanded;
        this.ccbAdjustment = ccbAdjustment;
        this.subTotalOne = subTotalOne;
        this.unpostedPayment = unpostedPayment;
        this.netBill = netBill;
        this.ots2025SurchargeWaiver = ots2025SurchargeWaiver;
        this.ots2025UnpostedInstallmentAmount = ots2025UnpostedInstallmentAmount;
        this.ots2025postedInstallmentAmount = ots2025postedInstallmentAmount;
    }
}
