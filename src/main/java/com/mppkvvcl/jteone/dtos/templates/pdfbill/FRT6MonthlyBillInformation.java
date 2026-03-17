package com.mppkvvcl.jteone.dtos.templates.pdfbill;

import java.math.BigDecimal;

public class FRT6MonthlyBillInformation {

    private String billDuration;


    private BigDecimal energyCharge;
    private BigDecimal fppas;
    private BigDecimal fixedCharge;
    private BigDecimal subTotalOne;

    private BigDecimal electricityDuty;
    private BigDecimal ccbAdjustment;
    private BigDecimal subTotalTwo;

    private BigDecimal penalCharge;
    private BigDecimal pfSurchargeOrIncentive;
    private BigDecimal asdInstallment;
    private BigDecimal todRebateOrSurcharge;
    private BigDecimal sdInterest;
    private BigDecimal locdFactorIncentive;
    private BigDecimal lockCredit;
    private BigDecimal employeeRebate;
    private BigDecimal prepaidPaymentRebate;
    private BigDecimal onlinePaymentRebate;
    private BigDecimal promptPaymentIncentive;
    private BigDecimal advancePaymentIncentive;
    private BigDecimal demandSideIncentive;
    private BigDecimal wheelingCharge;
    private BigDecimal subTotalThree;

    private BigDecimal subsidy;
    private BigDecimal subTotalFour;

    private BigDecimal principalArrear;
    private BigDecimal cumulativeSurcharge;
    private BigDecimal asdArrear;
    private BigDecimal netBill;

    private Long paymentForBillAndVigilance;
    private Long paymentForSmartMeterRCDC;

    private BigDecimal vigilanceDueAmount;
    private BigDecimal vigilanceDueInterestAmount;

    private BigDecimal totalPayableAmount;

    private BigDecimal pf;
    private BigDecimal md;
    private BigDecimal previousRead;
    private BigDecimal meteredUnit;
    private BigDecimal currentRead;
    private BigDecimal surchargeDemanded;

    public BigDecimal getEnergyCharge() {
        return energyCharge;
    }

    public void setEnergyCharge(BigDecimal energyCharge) {
        this.energyCharge = energyCharge;
    }

    public BigDecimal getFppas() {
        return fppas;
    }

    public void setFppas(BigDecimal fppas) {
        this.fppas = fppas;
    }

    public BigDecimal getFixedCharge() {
        return fixedCharge;
    }

    public void setFixedCharge(BigDecimal fixedCharge) {
        this.fixedCharge = fixedCharge;
    }

    public BigDecimal getSubTotalOne() {
        return subTotalOne;
    }

    public void setSubTotalOne(BigDecimal subTotalOne) {
        this.subTotalOne = subTotalOne;
    }

    public BigDecimal getElectricityDuty() {
        return electricityDuty;
    }

    public void setElectricityDuty(BigDecimal electricityDuty) {
        this.electricityDuty = electricityDuty;
    }

    public BigDecimal getCcbAdjustment() {
        return ccbAdjustment;
    }

    public void setCcbAdjustment(BigDecimal ccbAdjustment) {
        this.ccbAdjustment = ccbAdjustment;
    }

    public BigDecimal getSubTotalTwo() {
        return subTotalTwo;
    }

    public void setSubTotalTwo(BigDecimal subTotalTwo) {
        this.subTotalTwo = subTotalTwo;
    }

    public BigDecimal getPenalCharge() {
        return penalCharge;
    }

    public void setPenalCharge(BigDecimal penalCharge) {
        this.penalCharge = penalCharge;
    }

    public BigDecimal getPfSurchargeOrIncentive() {
        return pfSurchargeOrIncentive;
    }

    public void setPfSurchargeOrIncentive(BigDecimal pfSurchargeOrIncentive) {
        this.pfSurchargeOrIncentive = pfSurchargeOrIncentive;
    }

    public BigDecimal getAsdInstallment() {
        return asdInstallment;
    }

    public void setAsdInstallment(BigDecimal asdInstallment) {
        this.asdInstallment = asdInstallment;
    }

    public BigDecimal getTodRebateOrSurcharge() {
        return todRebateOrSurcharge;
    }

    public void setTodRebateOrSurcharge(BigDecimal todRebateOrSurcharge) {
        this.todRebateOrSurcharge = todRebateOrSurcharge;
    }

    public BigDecimal getSdInterest() {
        return sdInterest;
    }

    public void setSdInterest(BigDecimal sdInterest) {
        this.sdInterest = sdInterest;
    }

    public BigDecimal getLocdFactorIncentive() {
        return locdFactorIncentive;
    }

    public void setLocdFactorIncentive(BigDecimal locdFactorIncentive) {
        this.locdFactorIncentive = locdFactorIncentive;
    }

    public BigDecimal getLockCredit() {
        return lockCredit;
    }

    public void setLockCredit(BigDecimal lockCredit) {
        this.lockCredit = lockCredit;
    }

    public BigDecimal getEmployeeRebate() {
        return employeeRebate;
    }

    public void setEmployeeRebate(BigDecimal employeeRebate) {
        this.employeeRebate = employeeRebate;
    }

    public BigDecimal getPrepaidPaymentRebate() {
        return prepaidPaymentRebate;
    }

    public void setPrepaidPaymentRebate(BigDecimal prepaidPaymentRebate) {
        this.prepaidPaymentRebate = prepaidPaymentRebate;
    }

    public BigDecimal getOnlinePaymentRebate() {
        return onlinePaymentRebate;
    }

    public void setOnlinePaymentRebate(BigDecimal onlinePaymentRebate) {
        this.onlinePaymentRebate = onlinePaymentRebate;
    }

    public BigDecimal getPromptPaymentIncentive() {
        return promptPaymentIncentive;
    }

    public void setPromptPaymentIncentive(BigDecimal promptPaymentIncentive) {
        this.promptPaymentIncentive = promptPaymentIncentive;
    }

    public BigDecimal getAdvancePaymentIncentive() {
        return advancePaymentIncentive;
    }

    public void setAdvancePaymentIncentive(BigDecimal advancePaymentIncentive) {
        this.advancePaymentIncentive = advancePaymentIncentive;
    }

    public BigDecimal getDemandSideIncentive() {
        return demandSideIncentive;
    }

    public void setDemandSideIncentive(BigDecimal demandSideIncentive) {
        this.demandSideIncentive = demandSideIncentive;
    }

    public BigDecimal getWheelingCharge() {
        return wheelingCharge;
    }

    public void setWheelingCharge(BigDecimal wheelingCharge) {
        this.wheelingCharge = wheelingCharge;
    }

    public BigDecimal getSubTotalThree() {
        return subTotalThree;
    }

    public void setSubTotalThree(BigDecimal subTotalThree) {
        this.subTotalThree = subTotalThree;
    }

    public BigDecimal getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(BigDecimal subsidy) {
        this.subsidy = subsidy;
    }

    public BigDecimal getSubTotalFour() {
        return subTotalFour;
    }

    public void setSubTotalFour(BigDecimal subTotalFour) {
        this.subTotalFour = subTotalFour;
    }

    public BigDecimal getPrincipalArrear() {
        return principalArrear;
    }

    public void setPrincipalArrear(BigDecimal principalArrear) {
        this.principalArrear = principalArrear;
    }

    public BigDecimal getCumulativeSurcharge() {
        return cumulativeSurcharge;
    }

    public void setCumulativeSurcharge(BigDecimal cumulativeSurcharge) {
        this.cumulativeSurcharge = cumulativeSurcharge;
    }

    public BigDecimal getAsdArrear() {
        return asdArrear;
    }

    public void setAsdArrear(BigDecimal asdArrear) {
        this.asdArrear = asdArrear;
    }

    public BigDecimal getNetBill() {
        return netBill;
    }

    public void setNetBill(BigDecimal netBill) {
        this.netBill = netBill;
    }

    public Long getPaymentForBillAndVigilance() {
        return paymentForBillAndVigilance;
    }

    public void setPaymentForBillAndVigilance(Long paymentForBillAndVigilance) {
        this.paymentForBillAndVigilance = paymentForBillAndVigilance;
    }

    public Long getPaymentForSmartMeterRCDC() {
        return paymentForSmartMeterRCDC;
    }

    public void setPaymentForSmartMeterRCDC(Long paymentForSmartMeterRCDC) {
        this.paymentForSmartMeterRCDC = paymentForSmartMeterRCDC;
    }

    public BigDecimal getVigilanceDueAmount() {
        return vigilanceDueAmount;
    }

    public void setVigilanceDueAmount(BigDecimal vigilanceDueAmount) {
        this.vigilanceDueAmount = vigilanceDueAmount;
    }

    public BigDecimal getVigilanceDueInterestAmount() {
        return vigilanceDueInterestAmount;
    }

    public void setVigilanceDueInterestAmount(BigDecimal vigilanceDueInterestAmount) {
        this.vigilanceDueInterestAmount = vigilanceDueInterestAmount;
    }

    public BigDecimal getTotalPayableAmount() {
        return totalPayableAmount;
    }

    public void setTotalPayableAmount(BigDecimal totalPayableAmount) {
        this.totalPayableAmount = totalPayableAmount;
    }

    public BigDecimal getPf() {
        return pf;
    }

    public void setPf(BigDecimal pf) {
        this.pf = pf;
    }

    public BigDecimal getMd() {
        return md;
    }

    public void setMd(BigDecimal md) {
        this.md = md;
    }

    public BigDecimal getPreviousRead() {
        return previousRead;
    }

    public void setPreviousRead(BigDecimal previousRead) {
        this.previousRead = previousRead;
    }

    public BigDecimal getMeteredUnit() {
        return meteredUnit;
    }

    public void setMeteredUnit(BigDecimal meteredUnit) {
        this.meteredUnit = meteredUnit;
    }

    public BigDecimal getCurrentRead() {
        return currentRead;
    }

    public void setCurrentRead(BigDecimal currentRead) {
        this.currentRead = currentRead;
    }

    public BigDecimal getSurchargeDemanded() {
        return surchargeDemanded;
    }

    public void setSurchargeDemanded(BigDecimal surchargeDemanded) {
        this.surchargeDemanded = surchargeDemanded;
    }

    public FRT6MonthlyBillInformation() {
    }

    public FRT6MonthlyBillInformation(BigDecimal energyCharge, BigDecimal fppas, BigDecimal fixedCharge, BigDecimal subTotalOne, BigDecimal electricityDuty, BigDecimal ccbAdjustment, BigDecimal subTotalTwo, BigDecimal penalCharge, BigDecimal pfSurchargeOrIncentive, BigDecimal asdInstallment, BigDecimal todRebateOrSurcharge, BigDecimal sdInterest, BigDecimal locdFactorIncentive, BigDecimal lockCredit, BigDecimal employeeRebate, BigDecimal prepaidPaymentRebate, BigDecimal onlinePaymentRebate, BigDecimal promptPaymentIncentive, BigDecimal advancePaymentIncentive, BigDecimal demandSideIncentive, BigDecimal wheelingCharge, BigDecimal subTotalThree, BigDecimal subsidy, BigDecimal subTotalFour, BigDecimal principalArrear, BigDecimal cumulativeSurcharge, BigDecimal asdArrear, BigDecimal netBill, Long paymentForBillAndVigilance, Long paymentForSmartMeterRCDC, BigDecimal vigilanceDueAmount, BigDecimal vigilanceDueInterestAmount, BigDecimal totalPayableAmount, BigDecimal pf, BigDecimal md, BigDecimal previousRead, BigDecimal meteredUnit, BigDecimal currentRead, BigDecimal surchargeDemanded) {
        this.energyCharge = energyCharge;
        this.fppas = fppas;
        this.fixedCharge = fixedCharge;
        this.subTotalOne = subTotalOne;
        this.electricityDuty = electricityDuty;
        this.ccbAdjustment = ccbAdjustment;
        this.subTotalTwo = subTotalTwo;
        this.penalCharge = penalCharge;
        this.pfSurchargeOrIncentive = pfSurchargeOrIncentive;
        this.asdInstallment = asdInstallment;
        this.todRebateOrSurcharge = todRebateOrSurcharge;
        this.sdInterest = sdInterest;
        this.locdFactorIncentive = locdFactorIncentive;
        this.lockCredit = lockCredit;
        this.employeeRebate = employeeRebate;
        this.prepaidPaymentRebate = prepaidPaymentRebate;
        this.onlinePaymentRebate = onlinePaymentRebate;
        this.promptPaymentIncentive = promptPaymentIncentive;
        this.advancePaymentIncentive = advancePaymentIncentive;
        this.demandSideIncentive = demandSideIncentive;
        this.wheelingCharge = wheelingCharge;
        this.subTotalThree = subTotalThree;
        this.subsidy = subsidy;
        this.subTotalFour = subTotalFour;
        this.principalArrear = principalArrear;
        this.cumulativeSurcharge = cumulativeSurcharge;
        this.asdArrear = asdArrear;
        this.netBill = netBill;
        this.paymentForBillAndVigilance = paymentForBillAndVigilance;
        this.paymentForSmartMeterRCDC = paymentForSmartMeterRCDC;
        this.vigilanceDueAmount = vigilanceDueAmount;
        this.vigilanceDueInterestAmount = vigilanceDueInterestAmount;
        this.totalPayableAmount = totalPayableAmount;
        this.pf = pf;
        this.md = md;
        this.previousRead = previousRead;
        this.meteredUnit = meteredUnit;
        this.currentRead = currentRead;
        this.surchargeDemanded = surchargeDemanded;
    }
}
