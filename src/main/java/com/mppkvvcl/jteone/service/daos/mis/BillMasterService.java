package com.mppkvvcl.jteone.service.daos.mis;

import com.mppkvvcl.misdao.daos.BillMasterDAO;
import com.mppkvvcl.misdao.dtos.BillIdentifierDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillMasterService {

    private static final Logger log = LoggerFactory.getLogger(BillMasterService.class);

    @Autowired
    private BillMasterDAO billMasterDAO;

    public BillIdentifierDTO getForLatestBillIdentifierByConsumerNoAndDeleted(String consumerNo, boolean deleted) {
        if (StringUtils.isEmpty(consumerNo)) return null;

        return billMasterDAO.getForLatestBillIdentifierByConsumerNoAndDeleted(consumerNo, deleted);
    }


    public BillIdentifierDTO getForBillIdentifierByConsumerNoAndBillMonthAndDeleted(String consumerNo, String billMonth, boolean deleted) {
        if (StringUtils.isEmpty(consumerNo) || StringUtils.isEmpty(billMonth)) return null;

        return billMasterDAO.getForBillIdentifierByConsumerNoAndBillMonthAndDeleted(consumerNo, billMonth, deleted);
    }
}
