package com.mppkvvcl.jteone.service.daos.mis;

import com.mppkvvcl.misdao.daos.ReportTemplateDAO;
import com.mppkvvcl.misdao.interfaces.ReportTemplateInterface;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportTemplateService {

    private static final Logger log = LoggerFactory.getLogger(ReportTemplateService.class);

    @Autowired
    private ReportTemplateDAO reportTemplateDAO;

    public ReportTemplateInterface getByReportNameAndBillMonthAndDeleted(String reportName, String billMonth, boolean deleted) {
        if (StringUtils.isEmpty(reportName) || StringUtils.isEmpty(billMonth)) return null;

        return reportTemplateDAO.getByReportNameAndBillMonthAndDeleted(reportName, billMonth, deleted);
    }
}
