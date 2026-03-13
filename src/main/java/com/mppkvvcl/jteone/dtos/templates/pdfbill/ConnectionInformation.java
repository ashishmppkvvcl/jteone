package com.mppkvvcl.jteone.dtos.templates.pdfbill;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ConnectionInformation {

    private String divisionName;
    private String zoneName;
    private String locationCode;
    private String groupNo;
    private String readingDiaryNo;
    private String feederCode;
    private String dtrCode;
    private String poleNo;
    private String purpose;
    private String areaType;
    private String tariffCategory;
    private String tariffCode;
    private LocalDate connectionDate;
    private String phase;
    private BigDecimal sanctionedLoad;
    private String sanctionedLoadUnit;
    private BigDecimal contractDemand;
    private String contractDemandUnit;
    private String connectionType;
    private String meteringStatus;
    private boolean isNetMeter;
    private boolean isNetMeterParent;
    private boolean isNetMeterChild;
    private String netMeterType;
    private boolean isMemberOfHTGroupHousingSociety;
    private boolean isPrepaid;
    private LocalDate prepaidDate;
    private String meterAttribute;

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    public String getReadingDiaryNo() {
        return readingDiaryNo;
    }

    public void setReadingDiaryNo(String readingDiaryNo) {
        this.readingDiaryNo = readingDiaryNo;
    }

    public String getFeederCode() {
        return feederCode;
    }

    public void setFeederCode(String feederCode) {
        this.feederCode = feederCode;
    }

    public String getDtrCode() {
        return dtrCode;
    }

    public void setDtrCode(String dtrCode) {
        this.dtrCode = dtrCode;
    }

    public String getPoleNo() {
        return poleNo;
    }

    public void setPoleNo(String poleNo) {
        this.poleNo = poleNo;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    public String getTariffCategory() {
        return tariffCategory;
    }

    public void setTariffCategory(String tariffCategory) {
        this.tariffCategory = tariffCategory;
    }

    public String getTariffCode() {
        return tariffCode;
    }

    public void setTariffCode(String tariffCode) {
        this.tariffCode = tariffCode;
    }

    public LocalDate getConnectionDate() {
        return connectionDate;
    }

    public void setConnectionDate(LocalDate connectionDate) {
        this.connectionDate = connectionDate;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public BigDecimal getSanctionedLoad() {
        return sanctionedLoad;
    }

    public void setSanctionedLoad(BigDecimal sanctionedLoad) {
        this.sanctionedLoad = sanctionedLoad;
    }

    public String getSanctionedLoadUnit() {
        return sanctionedLoadUnit;
    }

    public void setSanctionedLoadUnit(String sanctionedLoadUnit) {
        this.sanctionedLoadUnit = sanctionedLoadUnit;
    }

    public BigDecimal getContractDemand() {
        return contractDemand;
    }

    public void setContractDemand(BigDecimal contractDemand) {
        this.contractDemand = contractDemand;
    }

    public String getContractDemandUnit() {
        return contractDemandUnit;
    }

    public void setContractDemandUnit(String contractDemandUnit) {
        this.contractDemandUnit = contractDemandUnit;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public String getMeteringStatus() {
        return meteringStatus;
    }

    public void setMeteringStatus(String meteringStatus) {
        this.meteringStatus = meteringStatus;
    }

    public boolean isNetMeter() {
        return isNetMeter;
    }

    public void setNetMeter(boolean netMeter) {
        isNetMeter = netMeter;
    }

    public boolean isNetMeterParent() {
        return isNetMeterParent;
    }

    public void setNetMeterParent(boolean netMeterParent) {
        isNetMeterParent = netMeterParent;
    }

    public boolean isNetMeterChild() {
        return isNetMeterChild;
    }

    public void setNetMeterChild(boolean netMeterChild) {
        isNetMeterChild = netMeterChild;
    }

    public String getNetMeterType() {
        return netMeterType;
    }

    public void setNetMeterType(String netMeterType) {
        this.netMeterType = netMeterType;
    }

    public boolean isMemberOfHTGroupHousingSociety() {
        return isMemberOfHTGroupHousingSociety;
    }

    public void setMemberOfHTGroupHousingSociety(boolean memberOfHTGroupHousingSociety) {
        isMemberOfHTGroupHousingSociety = memberOfHTGroupHousingSociety;
    }

    public boolean isPrepaid() {
        return isPrepaid;
    }

    public void setPrepaid(boolean prepaid) {
        isPrepaid = prepaid;
    }

    public LocalDate getPrepaidDate() {
        return prepaidDate;
    }

    public void setPrepaidDate(LocalDate prepaidDate) {
        this.prepaidDate = prepaidDate;
    }

    public String getMeterAttribute() {
        return meterAttribute;
    }

    public void setMeterAttribute(String meterAttribute) {
        this.meterAttribute = meterAttribute;
    }

    public ConnectionInformation() {
    }

    public ConnectionInformation(String divisionName, String zoneName, String locationCode, String groupNo, String readingDiaryNo, String feederCode, String dtrCode, String poleNo, String purpose, String areaType, String tariffCategory, String tariffCode, LocalDate connectionDate, String phase, BigDecimal sanctionedLoad, String sanctionedLoadUnit, BigDecimal contractDemand, String contractDemandUnit, String connectionType, String meteringStatus, boolean isNetMeter, boolean isNetMeterParent, boolean isNetMeterChild, String netMeterType, boolean isMemberOfHTGroupHousingSociety, boolean isPrepaid, LocalDate prepaidDate, String meterAttribute) {
        this.divisionName = divisionName;
        this.zoneName = zoneName;
        this.locationCode = locationCode;
        this.groupNo = groupNo;
        this.readingDiaryNo = readingDiaryNo;
        this.feederCode = feederCode;
        this.dtrCode = dtrCode;
        this.poleNo = poleNo;
        this.purpose = purpose;
        this.areaType = areaType;
        this.tariffCategory = tariffCategory;
        this.tariffCode = tariffCode;
        this.connectionDate = connectionDate;
        this.phase = phase;
        this.sanctionedLoad = sanctionedLoad;
        this.sanctionedLoadUnit = sanctionedLoadUnit;
        this.contractDemand = contractDemand;
        this.contractDemandUnit = contractDemandUnit;
        this.connectionType = connectionType;
        this.meteringStatus = meteringStatus;
        this.isNetMeter = isNetMeter;
        this.isNetMeterParent = isNetMeterParent;
        this.isNetMeterChild = isNetMeterChild;
        this.netMeterType = netMeterType;
        this.isMemberOfHTGroupHousingSociety = isMemberOfHTGroupHousingSociety;
        this.isPrepaid = isPrepaid;
        this.prepaidDate = prepaidDate;
        this.meterAttribute = meterAttribute;
    }
}
