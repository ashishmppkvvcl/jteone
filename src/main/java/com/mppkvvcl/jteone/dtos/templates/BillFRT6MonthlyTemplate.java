package com.mppkvvcl.jteone.dtos.templates;

import com.mppkvvcl.jteone.dtos.templates.pdfbill.CompanyInformation;
import com.mppkvvcl.jteone.dtos.templates.pdfbill.FRT6MonthlyBillInformation;
import com.mppkvvcl.jteone.dtos.templates.pdfbill.FRT6MonthlyConsumerInformation;
import com.mppkvvcl.jteone.dtos.templates.pdfbill.TemplateInformation;
import com.mppkvvcl.jteone.interfaces.JteTemplateInterface;

import java.util.List;

public class BillFRT6MonthlyTemplate implements JteTemplateInterface {

    private TemplateInformation templateInformation;
    private CompanyInformation companyInformation;
    private FRT6MonthlyConsumerInformation consumerInformation;
    private FRT6MonthlyBillInformation billInformation;
    private List<String> messages;

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

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "BillFRT6MonthlyTemplate{" +
                "templateInformation=" + templateInformation +
                ", companyInformation=" + companyInformation +
                ", consumerInformation=" + consumerInformation +
                ", billInformation=" + billInformation +
                ", messages=" + messages +
                '}';
    }
}
