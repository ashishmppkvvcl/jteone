package com.mppkvvcl.jteone.dtos.templates.pdfbill;

public class FRT6MonthlyConsumerInformation {

    private String consumerNo;
    private String consumerName;
    private String address;
    private String consumerNameHindi;
    private String addressHindi;
    private String mobileNo;
    private String emailId;
    private String employeeNo;
    private String status;

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

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FRT6MonthlyConsumerInformation() {
    }

    public FRT6MonthlyConsumerInformation(String consumerNo, String consumerName, String address, String consumerNameHindi, String addressHindi, String mobileNo, String emailId, String employeeNo, String status) {
        this.consumerNo = consumerNo;
        this.consumerName = consumerName;
        this.address = address;
        this.consumerNameHindi = consumerNameHindi;
        this.addressHindi = addressHindi;
        this.mobileNo = mobileNo;
        this.emailId = emailId;
        this.employeeNo = employeeNo;
        this.status = status;
    }
}
