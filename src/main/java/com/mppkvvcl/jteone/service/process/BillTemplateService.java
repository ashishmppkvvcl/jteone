package com.mppkvvcl.jteone.service.process;

import com.mppkvvcl.jteone.dtos.response.MessageDTO;
import com.mppkvvcl.jteone.dtos.templates.BillTemplate;
import com.mppkvvcl.misdao.dtos.BillIdentifierDTO;
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

    public BillTemplate prepareBillTemplateObject(final BillIdentifierDTO billIdentifierDTO, final String database, final String templateVersion, final MessageDTO messageDTO) {
        if (billIdentifierDTO == null || StringUtils.isEmpty(database) || StringUtils.isEmpty(templateVersion))
            return null;
        BillTemplate templateData = null;
        if ("MIS".equalsIgnoreCase(database)) {
            //TODO:MIS fetching Implementation
            if (billIdentifierDTO.isFRT6MonthlyConsumer()) {
                //FRT 6 Monthly Implementation
            } else {
                //Monthly Bill Implementation
            }
        } else if ("NGB".equalsIgnoreCase(database)) {
            templateData = billNgbTemplateService.prepareBillTemplateObject(billIdentifierDTO, templateVersion, messageDTO);
        }

        return templateData;
    }
}
