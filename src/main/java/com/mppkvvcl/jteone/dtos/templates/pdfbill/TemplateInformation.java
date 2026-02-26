package com.mppkvvcl.jteone.dtos.templates.pdfbill;

public class TemplateInformation {

    private String templateVersion;
    private String reportVersion;
    private String misVersion;

    public String getTemplateVersion() {
        return templateVersion;
    }

    public void setTemplateVersion(String templateVersion) {
        this.templateVersion = templateVersion;
    }

    public String getReportVersion() {
        return reportVersion;
    }

    public void setReportVersion(String reportVersion) {
        this.reportVersion = reportVersion;
    }

    public String getMisVersion() {
        return misVersion;
    }

    public void setMisVersion(String misVersion) {
        this.misVersion = misVersion;
    }

    public TemplateInformation() {
    }

    public TemplateInformation(String templateVersion, String reportVersion, String misVersion) {
        this.templateVersion = templateVersion;
        this.reportVersion = reportVersion;
        this.misVersion = misVersion;
    }

    @Override
    public String toString() {
        return "TemplateInformation{" +
                "templateVersion='" + templateVersion + '\'' +
                ", reportVersion='" + reportVersion + '\'' +
                ", misVersion='" + misVersion + '\'' +
                '}';
    }
}
