package com.mppkvvcl.jteone.service.impl;

import com.mppkvvcl.jteone.dtos.response.MessageDTO;
import com.mppkvvcl.jteone.dtos.templates.BillFRT6MonthlyTemplate;
import com.mppkvvcl.jteone.dtos.templates.BillTemplate;
import com.mppkvvcl.jteone.interfaces.JteTemplateInterface;
import com.mppkvvcl.jteone.service.daos.mis.BillMasterAgricultureService;
import com.mppkvvcl.jteone.service.daos.mis.ConfiguratorService;
import com.mppkvvcl.jteone.service.daos.mis.ReportTemplateService;
import com.mppkvvcl.jteone.service.daos.ngb.BillService;
import com.mppkvvcl.jteone.service.daos.ngb.ConsumerConnectionInformationService;
import com.mppkvvcl.jteone.service.daos.ngb.ConsumerMiscellaneousInformationService;
import com.mppkvvcl.jteone.service.process.BillTemplateService;
import com.mppkvvcl.jteone.service.process.HtmlToPdfService;
import com.mppkvvcl.jteone.service.process.JteTemplateService;
import com.mppkvvcl.misdao.interfaces.ConfiguratorInterface;
import com.mppkvvcl.misdao.interfaces.ReportTemplateInterface;
import com.mppkvvcl.ngbdao.dtos.BillIdentifierDTO;
import com.mppkvvcl.ngbdao.interfaces.ConsumerMiscellaneousInformationInterface;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class BillServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(BillServiceImpl.class);

    @Autowired
    @Qualifier("misConfiguratorService")
    private ConfiguratorService configuratorService;

    @Autowired
    private BillMasterAgricultureService billMasterAgricultureService;

    @Autowired
    private BillService billService;

    @Autowired
    private ConsumerConnectionInformationService consumerConnectionInformationService;

    @Autowired
    private ConsumerMiscellaneousInformationService consumerMiscellaneousInformationService;

    @Autowired
    private ReportTemplateService reportTemplateService;

    @Autowired
    private BillTemplateService billTemplateService;

    @Autowired
    private JteTemplateService jteTemplateService;

    @Autowired
    private HtmlToPdfService htmlToPdfService;

    public byte[] downloadBill(final long billId, final String fileFormat, MessageDTO messageDTO) {
        if (messageDTO == null) return null;
        if (StringUtils.isEmpty(fileFormat)) {
            messageDTO.setMessage("Input param(s) is null");
            log.info(messageDTO.getMessage());
            return null;
        }

        final BillIdentifierDTO billIdentifierDTO = billService.getForBillIdentifierById(billId);
        if (billIdentifierDTO != null && billIdentifierDTO.isFRT6MonthlyConsumer()) {
            final String consumerNo = billIdentifierDTO.getConsumerNo();
            final String billMonth = billIdentifierDTO.getBillMonth();
            final String propertyValue = configuratorService.getValueByPropertyName(ConfiguratorInterface.PROPERTY_NAME_AGRICULTURE_BILLING_CYCLE_MONTHS);
            if (!StringUtils.isEmpty(propertyValue)) {
                final String[] months = propertyValue.split(", ");
                if (Arrays.asList(months).contains(billIdentifierDTO.getBillMonth().substring(0, 3))) {
                    return downloadFRT6MonthlyBill(billIdentifierDTO, fileFormat, messageDTO);
                }
            }
            messageDTO.setMessage("No FRT bill found for " + consumerNo + " & " + billMonth);
            log.info(messageDTO.getMessage());
            return null;
        } else {
            return downloadBill(billIdentifierDTO, fileFormat, messageDTO);
        }
    }

    public byte[] downloadLatestBill(final String consumerNo, final String fileFormat, MessageDTO messageDTO) {
        if (messageDTO == null) return null;
        if (StringUtils.isEmpty(consumerNo) || StringUtils.isEmpty(fileFormat)) {
            messageDTO.setMessage("Input param(s) is null");
            log.info(messageDTO.getMessage());
            return null;
        }

        final BillIdentifierDTO billIdentifierDTO = billService.getForLatestBillIdentifierByConsumerNoAndDeleted(consumerNo, false);
        if (billIdentifierDTO != null && billIdentifierDTO.isFRT6MonthlyConsumer()) {
            final String billMonth = billMasterAgricultureService.getMaxBillMonthByConsumerNo(consumerNo);
            if (StringUtils.isEmpty(billMonth)) {
                messageDTO.setMessage("No FRT bill found for " + consumerNo);
                log.info(messageDTO.getMessage());
                return null;
            }
            billIdentifierDTO.setBillMonth(billMonth);

            return downloadFRT6MonthlyBill(billIdentifierDTO, fileFormat, messageDTO);
        } else {
            return downloadBill(billIdentifierDTO, fileFormat, messageDTO);
        }
    }

    public byte[] downloadBill(final String consumerNo, String billMonth, final String fileFormat, MessageDTO messageDTO) {
        if (messageDTO == null) return null;
        if (StringUtils.isEmpty(consumerNo) || StringUtils.isEmpty(billMonth) || StringUtils.isEmpty(fileFormat)) {
            messageDTO.setMessage("Input param(s) is null");
            log.info(messageDTO.getMessage());
            return null;
        }

        final BillIdentifierDTO billIdentifierDTO = consumerConnectionInformationService.getForBillIdentifierByConsumerNo(consumerNo);
        if (billIdentifierDTO != null && billIdentifierDTO.isFRT6MonthlyConsumer()) {
            billIdentifierDTO.setBillMonth(billMonth);
            final String propertyValue = configuratorService.getValueByPropertyName(ConfiguratorInterface.PROPERTY_NAME_AGRICULTURE_BILLING_CYCLE_MONTHS);
            if (!StringUtils.isEmpty(propertyValue)) {
                final String[] months = propertyValue.split(", ");
                if (Arrays.asList(months).contains(billIdentifierDTO.getBillMonth().substring(0, 3))) {
                    return downloadFRT6MonthlyBill(billIdentifierDTO, fileFormat, messageDTO);
                }
            }
            messageDTO.setMessage("No FRT bill found for " + consumerNo + " & " + billMonth);
            log.info(messageDTO.getMessage());
            return null;
        } else {
            return downloadBill(billIdentifierDTO, fileFormat, messageDTO);
        }
    }

    public byte[] downloadBill(final BillIdentifierDTO billIdentifierDTO, final String fileFormat, MessageDTO messageDTO) {
        if (messageDTO == null) return null;
        if (billIdentifierDTO == null || StringUtils.isEmpty(fileFormat)) {
            messageDTO.setMessage("Input param(s) is null");
            log.info(messageDTO.getMessage());
            return null;
        }
        final ReportTemplateInterface reportTemplate = reportTemplateService.getByReportNameAndBillMonthAndDeleted(ReportTemplateInterface.REPORT_NAME_BILL, billIdentifierDTO.getBillMonth(), false);
        if (reportTemplate == null) {
            messageDTO.setMessage("No template configuration found for " + ReportTemplateInterface.REPORT_NAME_BILL);
            log.info(messageDTO.getMessage());
            return null;
        }

        String languageMiscellaneous = consumerMiscellaneousInformationService.getActivePropertyValueByConsumerNoAndPropertyName(billIdentifierDTO.getConsumerNo(), ConsumerMiscellaneousInformationInterface.PROPERTY_NAME_CONSUMER_PREFERRED_LANGUAGE);
        if (languageMiscellaneous == null) languageMiscellaneous = "ENGLISH";
        billIdentifierDTO.setConsumerPreferredLanguage(languageMiscellaneous);

        //Preparing data for Bill template
        final BillTemplate templateData = billTemplateService.prepareBillTemplateObject(billIdentifierDTO, reportTemplate.getVersion(), messageDTO);
        if (templateData == null) {
            if (StringUtils.isEmpty(messageDTO.getMessage())) messageDTO.setMessage("Unable to prepare data");
            log.info(messageDTO.getMessage());
            return null;
        }

        return downloadBill(templateData, billIdentifierDTO.getConsumerPreferredLanguage(), fileFormat, reportTemplate, messageDTO);
    }

    public byte[] downloadFRT6MonthlyBill(final BillIdentifierDTO billIdentifierDTO, final String fileFormat, MessageDTO messageDTO) {
        if (messageDTO == null) return null;
        if (billIdentifierDTO == null || StringUtils.isEmpty(fileFormat)) {
            messageDTO.setMessage("Input param(s) is null");
            log.info(messageDTO.getMessage());
            return null;
        }
        final ReportTemplateInterface reportTemplate = reportTemplateService.getByReportNameAndBillMonthAndDeleted(ReportTemplateInterface.REPORT_NAME_AGRICULTURE_BILL, billIdentifierDTO.getBillMonth(), false);
        if (reportTemplate == null) {
            messageDTO.setMessage("No template configuration found for " + ReportTemplateInterface.REPORT_NAME_AGRICULTURE_BILL);
            log.info(messageDTO.getMessage());
            return null;
        }

        String languageMiscellaneous = consumerMiscellaneousInformationService.getActivePropertyValueByConsumerNoAndPropertyName(billIdentifierDTO.getConsumerNo(), ConsumerMiscellaneousInformationInterface.PROPERTY_NAME_CONSUMER_PREFERRED_LANGUAGE);
        if (languageMiscellaneous == null) languageMiscellaneous = "ENGLISH";
        billIdentifierDTO.setConsumerPreferredLanguage(languageMiscellaneous);

        //Preparing data for Bill template
        final BillFRT6MonthlyTemplate templateData = billTemplateService.prepareAgricultureBillTemplateObject(billIdentifierDTO, reportTemplate.getVersion(), messageDTO);
        if (templateData == null) {
            if (StringUtils.isEmpty(messageDTO.getMessage())) messageDTO.setMessage("Unable to prepare data");
            log.info(messageDTO.getMessage());
            return null;
        }

        return downloadBill(templateData, billIdentifierDTO.getConsumerPreferredLanguage(), fileFormat, reportTemplate, messageDTO);
    }

    private byte[] downloadBill(final JteTemplateInterface templateData, final String consumerPreferredLanguage, final String fileFormat, final ReportTemplateInterface reportTemplate, MessageDTO messageDTO) {
        if (messageDTO == null) return null;
        if (templateData == null || StringUtils.isEmpty(consumerPreferredLanguage) || StringUtils.isEmpty(fileFormat) || reportTemplate == null) {
            messageDTO.setMessage("Input param(s) is null");
            log.info(messageDTO.getMessage());
            return null;
        }

        //Fill data to JTE Template and get HTML
        log.info("Generating HTML for {}", templateData.getConsumerNo());
        byte[] out = jteTemplateService.processTemplate(reportTemplate.getVersion(), reportTemplate.getTemplateName(), consumerPreferredLanguage, templateData);
        if ("PDF".equalsIgnoreCase(fileFormat)) {
            //Convert HTML to PDF
            log.info("Generating PDF for {}", templateData.getConsumerNo());
            out = htmlToPdfService.getPdfFromHtml(out);
        }
        log.info("Generated Output for {}", templateData.getConsumerNo());
        return out;
    }
}
