package com.mppkvvcl.jteone.dtos.templates.pdfbill;

public class ECGRFInformation {

    private int index;

    private String memberType;
    private String name;
    private String contactNo;
    private String caseHandling;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getCaseHandling() {
        return caseHandling;
    }

    public void setCaseHandling(String caseHandling) {
        this.caseHandling = caseHandling;
    }

    public ECGRFInformation() {
    }

    public ECGRFInformation(int index, String memberType, String name, String contactNo, String caseHandling) {
        this.index = index;
        this.memberType = memberType;
        this.name = name;
        this.contactNo = contactNo;
        this.caseHandling = caseHandling;
    }
}
