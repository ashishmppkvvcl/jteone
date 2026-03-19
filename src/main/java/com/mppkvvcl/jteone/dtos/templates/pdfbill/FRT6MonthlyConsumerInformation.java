package com.mppkvvcl.jteone.dtos.templates.pdfbill;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FRT6MonthlyConsumerInformation {

    private String consumerNo;
    private String consumerName;
    private String address;
    private String consumerNameHindi;
    private String addressHindi;
    private String mobileNo;
    private String status;

    private LocalDate connectionDate;
    private String phase;
    private BigDecimal sanctionedLoad;
    private String sanctionedLoadUnit;

    private String regionName;
    private String circleName;
    private String divisionName;
    private String zoneName;
    private String locationCode;
    private String groupNo;
    private String readingDiaryNo;

    public String getConsumerNo() {
        return consumerNo;
    }

    public void setConsumerNo(String consumerNo) {
        this.consumerNo = consumerNo;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getConsumerNameHindi() {
        return consumerNameHindi;
    }

    public void setConsumerNameHindi(String consumerNameHindi) {
        this.consumerNameHindi = consumerNameHindi;
    }

    public String getAddressHindi() {
        return addressHindi;
    }

    public void setAddressHindi(String addressHindi) {
        this.addressHindi = addressHindi;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

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

    public FRT6MonthlyConsumerInformation() {
    }

    public FRT6MonthlyConsumerInformation(String consumerNo, String consumerName, String address, String consumerNameHindi, String addressHindi, String mobileNo, String status, LocalDate connectionDate, String phase, BigDecimal sanctionedLoad, String sanctionedLoadUnit, String regionName, String circleName, String divisionName, String zoneName, String locationCode, String groupNo, String readingDiaryNo) {
        this.consumerNo = consumerNo;
        this.consumerName = consumerName;
        this.address = address;
        this.consumerNameHindi = consumerNameHindi;
        this.addressHindi = addressHindi;
        this.mobileNo = mobileNo;
        this.status = status;
        this.connectionDate = connectionDate;
        this.phase = phase;
        this.sanctionedLoad = sanctionedLoad;
        this.sanctionedLoadUnit = sanctionedLoadUnit;
        this.regionName = regionName;
        this.circleName = circleName;
        this.divisionName = divisionName;
        this.zoneName = zoneName;
        this.locationCode = locationCode;
        this.groupNo = groupNo;
        this.readingDiaryNo = readingDiaryNo;
    }
}
