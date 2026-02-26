package com.mppkvvcl.jteone.service.daos.mis;

import com.mppkvvcl.misdao.daos.BillMasterAgricultureDAO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillMasterAgricultureService {

    private static final Logger log = LoggerFactory.getLogger(BillMasterAgricultureService.class);

    @Autowired
    private BillMasterAgricultureDAO billMasterAgricultureDAO;

    public String getMaxBillMonthByConsumerNo(String consumerNo) {
        if (StringUtils.isEmpty(consumerNo)) return null;

        return billMasterAgricultureDAO.getMaxBillMonthByConsumerNo(consumerNo);
    }
}
