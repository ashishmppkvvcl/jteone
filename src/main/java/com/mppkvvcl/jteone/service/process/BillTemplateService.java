package com.mppkvvcl.jteone.service.process;

import com.mppkvvcl.jteone.dtos.response.MessageDTO;
import com.mppkvvcl.jteone.dtos.templates.BillFRT6MonthlyTemplate;
import com.mppkvvcl.jteone.dtos.templates.BillTemplate;
import com.mppkvvcl.ngbdao.dtos.BillIdentifierDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillTemplateService {
    private static final Logger log = LoggerFactory.getLogger(BillTemplateService.class);

    @Autowired
    private BillNgbTemplateService billNgbTemplateService;

    @Autowired
    private Agriculture6MonthlyFRTNgbTemplateService agriculture6MonthlyFRTNgbTemplateService;

    public BillTemplate prepareBillTemplateObject(final BillIdentifierDTO billIdentifierDTO, final String templateVersion, final MessageDTO messageDTO) {
        if (billIdentifierDTO == null || StringUtils.isEmpty(templateVersion))
            return null;
        return billNgbTemplateService.prepareBillTemplateObject(billIdentifierDTO, templateVersion, messageDTO);
    }

    public BillFRT6MonthlyTemplate prepareAgricultureBillTemplateObject(final BillIdentifierDTO billIdentifierDTO, final String templateVersion, final MessageDTO messageDTO) {
        if (billIdentifierDTO == null || StringUtils.isEmpty(templateVersion))
            return null;
        return agriculture6MonthlyFRTNgbTemplateService.prepareBillTemplateObject(billIdentifierDTO, templateVersion, messageDTO);
    }
}
