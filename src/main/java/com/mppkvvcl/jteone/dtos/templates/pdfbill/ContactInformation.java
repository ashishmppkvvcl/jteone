package com.mppkvvcl.jteone.dtos.templates.pdfbill;

public class ContactInformation {

    private int index;
    private String designation;
    private String name;
    private String contactNo;
    private String officeName;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
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

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public ContactInformation() {
    }

    public ContactInformation(int index, String designation, String name, String contactNo, String officeName) {
        this.index = index;
        this.designation = designation;
        this.name = name;
        this.contactNo = contactNo;
        this.officeName = officeName;
    }
}
