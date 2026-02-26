package com.mppkvvcl.jteone.service.daos.ngb;

import com.mppkvvcl.ngbdao.daos.SecurityDepositDAO;
import com.mppkvvcl.ngbdao.interfaces.SecurityDepositInterface;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SecurityDepositService {

    @Autowired
    private SecurityDepositDAO securityDepositDAO;

    public List<SecurityDepositInterface> getByConsumerNoOrderByIdDesc(String consumerNo) {
        if (StringUtils.isEmpty(consumerNo)) return null;

        return securityDepositDAO.getByConsumerNoOrderByIdDesc(consumerNo);
    }

    public SecurityDepositInterface getConsumerNoAndEffectiveStartDateAndEffectiveEndDateOrderByIdAsc(String consumerNo, LocalDate referenceDate) {
        if (StringUtils.isEmpty(consumerNo) || referenceDate == null) return null;

        return securityDepositDAO.getConsumerNoAndEffectiveStartDateAndEffectiveEndDateOrderByIdAsc(consumerNo, referenceDate);
    }
}
