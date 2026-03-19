package com.mppkvvcl.jteone.dtos.templates;

import com.mppkvvcl.jteone.dtos.templates.pdfbill.*;
import com.mppkvvcl.jteone.interfaces.JteTemplateInterface;

import java.util.List;

public class BillFRT6MonthlyTemplate implements JteTemplateInterface {

    private TemplateInformation templateInformation;
    private CompanyInformation companyInformation;
    private FRT6MonthlyConsumerInformation consumerInformation;
    private FRT6MonthlyBillInformation billInformation;
    private ContactInformation contactInformation;
    private List<String> messageList;

    @Override
    public String getConsumerNo() {
        return consumerInformation == null ? null : consumerInformation.getConsumerNo();
    }

    public TemplateInformation getTemplateInformation() {
        return templateInformation;
    }

    public void setTemplateInformation(TemplateInformation templateInformation) {
        this.templateInformation = templateInformation;
    }

    public CompanyInformation getCompanyInformation() {
        return companyInformation;
    }

    public void setCompanyInformation(CompanyInformation companyInformation) {
        this.companyInformation = companyInformation;
    }

    public FRT6MonthlyConsumerInformation getConsumerInformation() {
        return consumerInformation;
    }

    public void setConsumerInformation(FRT6MonthlyConsumerInformation consumerInformation) {
        this.consumerInformation = consumerInformation;
    }

    public FRT6MonthlyBillInformation getBillInformation() {
        return billInformation;
    }

    public void setBillInformation(FRT6MonthlyBillInformation billInformation) {
        this.billInformation = billInformation;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(ContactInformation contactInformation) {
        this.contactInformation = contactInformation;
    }

    public List<String> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<String> messageList) {
        this.messageList = messageList;
    }

    @Override
    public String toString() {
        return "BillFRT6MonthlyTemplate{" +
                "templateInformation=" + templateInformation +
                ", companyInformation=" + companyInformation +
                ", consumerInformation=" + consumerInformation +
                ", billInformation=" + billInformation +
                ", contactInformation=" + contactInformation +
                ", messageList=" + messageList +
                '}';
    }
}
