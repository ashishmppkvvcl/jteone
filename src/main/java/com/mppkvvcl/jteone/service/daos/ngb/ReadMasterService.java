package com.mppkvvcl.jteone.service.daos.ngb;

import com.mppkvvcl.ngbdao.daos.ReadMasterDAO;
import com.mppkvvcl.ngbdao.interfaces.ReadMasterInterface;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadMasterService {

    @Autowired
    private ReadMasterDAO readMasterDAO;

    public List<ReadMasterInterface> getByConsumerNoAndBillMonthAndReplacementFlagAndUsedOnBill(String consumerNo, String billMonth, String replacementFlag, Boolean usedOnBill, Sort sort) {
        if (StringUtils.isEmpty(consumerNo) || StringUtils.isEmpty(billMonth) || StringUtils.isEmpty(replacementFlag) || sort == null)
            return null;

        return readMasterDAO.getByConsumerNoAndBillMonthAndReplacementFlagAndUsedOnBill(consumerNo, billMonth, replacementFlag, usedOnBill, sort);
    }

    public List<ReadMasterInterface> getByConsumerNoAndReplacementFlagAndUsedOnBillAndBillMonthLessThanOrderByBillMonthDESC(String consumerNo, String replacementFlag, Boolean usedOnBill, String billMonth, int limit) {
        if (StringUtils.isEmpty(consumerNo) || StringUtils.isEmpty(billMonth) || StringUtils.isEmpty(replacementFlag))
            return null;

        final Pageable pageable = PageRequest.of(0, limit);
        return readMasterDAO.getByConsumerNoAndReplacementFlagAndUsedOnBillAndBillMonthLessThanOrderByBillMonthDESC(consumerNo, replacementFlag, usedOnBill, billMonth, pageable);
    }
}
