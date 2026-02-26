package com.mppkvvcl.jteone.service.daos.ngb;

import com.mppkvvcl.ngbdao.daos.PanchnamaPaymentInformationDAO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class PanchnamaPaymentInformationService {

    @Autowired
    private PanchnamaPaymentInformationDAO panchnamaPaymentInformationDAO;

    public BigDecimal getByConsumerNoAndPanchnamaNoAndPanchnamaDateAndDeleted(String consumerNo, String panchnamaNo, LocalDate panchnamaDate, boolean deleted) {
        if (StringUtils.isEmpty(consumerNo) || StringUtils.isEmpty(panchnamaNo) || panchnamaDate == null) return null;

        return panchnamaPaymentInformationDAO.getByConsumerNoAndPanchnamaNoAndPanchnamaDateAndDeleted(consumerNo, panchnamaNo, panchnamaDate, deleted);
    }
}
