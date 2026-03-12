package com.mppkvvcl.jteone.service.impl;

import com.mppkvvcl.jteone.dtos.response.MessageDTO;
import com.mppkvvcl.jteone.dtos.templates.BillTemplate;
import com.mppkvvcl.jteone.service.daos.mis.BillMasterAgricultureService;
import com.mppkvvcl.jteone.service.daos.mis.BillMasterService;
import com.mppkvvcl.jteone.service.daos.mis.ReportTemplateService;
import com.mppkvvcl.jteone.service.daos.ngb.BillService;
import com.mppkvvcl.jteone.service.daos.ngb.ConsumerMiscellaneousInformationService;
import com.mppkvvcl.jteone.service.process.BillTemplateService;
import com.mppkvvcl.jteone.service.process.HtmlToPdfService;
import com.mppkvvcl.jteone.service.process.JteTemplateService;
import com.mppkvvcl.misdao.dtos.BillIdentifierDTO;
import com.mppkvvcl.misdao.interfaces.ReportTemplateInterface;
import com.mppkvvcl.ngbdao.interfaces.ConsumerMiscellaneousInformationInterface;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(BillServiceImpl.class);

    @Autowired
    private BillMasterService billMasterService;

    @Autowired
    private BillMasterAgricultureService billMasterAgricultureService;

    @Autowired
    private BillService billService;

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

    public byte[] downloadLatestBill(final String consumerNo, final String fileFormat, MessageDTO messageDTO) {
        if (messageDTO == null) return null;
        if (StringUtils.isEmpty(consumerNo) || StringUtils.isEmpty(fileFormat)) {
            messageDTO.setMessage("Input param(s) is null");
            log.info(messageDTO.getMessage());
            return null;
        }

        //MIS Checking
        final BillIdentifierDTO billIdentifierDTO = billMasterService.getForLatestBillIdentifierByConsumerNoAndDeleted(consumerNo, false);
        if (billIdentifierDTO == null) {
            messageDTO.setMessage("No bill found in MIS for " + consumerNo);
            log.info(messageDTO.getMessage());
            return null;
        }
        String database = "MIS";
        if (billIdentifierDTO.isFRT6MonthlyConsumer()) {
            database = "MIS";

            String billMonth = billMasterAgricultureService.getMaxBillMonthByConsumerNo(consumerNo);
            if (StringUtils.isEmpty(billMonth)) {
                messageDTO.setMessage("No FRT bill found for " + consumerNo);
                log.info(messageDTO.getMessage());
                return null;
            }
            billIdentifierDTO.setBillMonth(billMonth);
        } else {
            database = (billIdentifierDTO.isCC4() || billIdentifierDTO.isPDC()) ? "NGB" : "MIS";
        }


        //************************************
        //For now fetching everything from NGB
        database = "NGB"; //Hardcoding, remove this
        //TODO:MIS fetching Implementation
        //************************************

        if ("NGB".equals(database)) {
            String billMonth = billService.getMaxBillMonthByConsumerNoAndDeleted(consumerNo, false);
            if (StringUtils.isEmpty(billMonth)) {
                messageDTO.setMessage("No bill found for " + consumerNo);
                log.info(messageDTO.getMessage());
                return null;
            }
            billIdentifierDTO.setBillMonth(billMonth);
        }

        return downloadBill(billIdentifierDTO, database, fileFormat, messageDTO);
    }

    public byte[] downloadBill(final String consumerNo, String billMonth, final String fileFormat, MessageDTO messageDTO) {
        if (messageDTO == null) return null;
        if (StringUtils.isEmpty(consumerNo) || StringUtils.isEmpty(billMonth) || StringUtils.isEmpty(fileFormat)) {
            messageDTO.setMessage("Input param(s) is null");
            log.info(messageDTO.getMessage());
            return null;
        }

        //MIS Checking
        final BillIdentifierDTO billIdentifierDTO = billMasterService.getForBillIdentifierByConsumerNoAndBillMonthAndDeleted(consumerNo, billMonth, false);
        if (billIdentifierDTO == null) {
            messageDTO.setMessage("No bill found in MIS for " + consumerNo);
            log.info(messageDTO.getMessage());
            return null;
        }
        String database = "MIS";
        if (billIdentifierDTO.isFRT6MonthlyConsumer()) {
            database = "MIS";

            billMonth = billMasterAgricultureService.getMaxBillMonthByConsumerNo(consumerNo);
            if (StringUtils.isEmpty(billMonth)) {
                messageDTO.setMessage("No FRT bill found for " + consumerNo);
                log.info(messageDTO.getMessage());
                return null;
            }
            billIdentifierDTO.setBillMonth(billMonth);
        } else {
            database = (billIdentifierDTO.isCC4() || billIdentifierDTO.isPDC()) ? "NGB" : "MIS";
        }


        //************************************
        //For now fetching everything from NGB
        database = "NGB"; //Hardcoding, remove this
        //TODO:MIS fetching Implementation
        //************************************

        return downloadBill(billIdentifierDTO, database, fileFormat, messageDTO);
    }

    public byte[] downloadBill(final BillIdentifierDTO billIdentifierDTO, final String database, final String fileFormat, MessageDTO messageDTO) {
        if (messageDTO == null) return null;
        if (billIdentifierDTO == null || StringUtils.isEmpty(database) || StringUtils.isEmpty(fileFormat)) {
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
        final BillTemplate templateData = billTemplateService.prepareBillTemplateObject(billIdentifierDTO, database, reportTemplate.getVersion(), messageDTO);
        if (templateData == null) {
            if (StringUtils.isEmpty(messageDTO.getMessage())) messageDTO.setMessage("Unable to prepare data");
            log.info(messageDTO.getMessage());
            return null;
        }

        return downloadBill(templateData, billIdentifierDTO.getConsumerPreferredLanguage(), fileFormat, reportTemplate, messageDTO);
    }

    private byte[] downloadBill(final BillTemplate templateData, final String consumerPreferredLanguage, final String fileFormat, final ReportTemplateInterface reportTemplate, MessageDTO messageDTO) {
        if (messageDTO == null) return null;
        if (templateData == null || StringUtils.isEmpty(consumerPreferredLanguage) || StringUtils.isEmpty(fileFormat) || reportTemplate == null) {
            messageDTO.setMessage("Input param(s) is null");
            log.info(messageDTO.getMessage());
            return null;
        }

        //Fill data to JTE Template and get HTML
        log.info("Generating HTML for {}", templateData.getConsumerInformation().getConsumerNo());
        byte[] out = jteTemplateService.processTemplate(reportTemplate.getVersion(), reportTemplate.getTemplateName(), consumerPreferredLanguage, templateData);
        if ("PDF".equalsIgnoreCase(fileFormat)) {
            //Convert HTML to PDF
            log.info("Generating PDF for {}", templateData.getConsumerInformation().getConsumerNo());
            out = htmlToPdfService.getPdfFromHtml(out);
        }
        log.info("Generated Output for {}", templateData.getConsumerInformation().getConsumerNo());
        return out;
    }
}
