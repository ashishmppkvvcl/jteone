package com.mppkvvcl.jteone.service.daos.ngb;

import com.mppkvvcl.ngbdao.daos.ReadMasterGeneratorDAO;
import com.mppkvvcl.ngbdao.interfaces.ReadMasterGeneratorInterface;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadMasterGeneratorService {

    @Autowired
    private ReadMasterGeneratorDAO readMasterGeneratorDAO;

    public List<ReadMasterGeneratorInterface> getByConsumerNoAndBillMonthAndReplacementFlagAndUsedOnBill(String consumerNo, String billMonth, String replacementFlag, boolean usedOnBill, Sort sort) {
        if (StringUtils.isEmpty(consumerNo) || StringUtils.isEmpty(billMonth) || StringUtils.isEmpty(replacementFlag) || sort == null)
            return null;

        return readMasterGeneratorDAO.getByConsumerNoAndBillMonthAndReplacementFlagAndUsedOnBill(consumerNo, billMonth, replacementFlag, usedOnBill, sort);
    }
}
