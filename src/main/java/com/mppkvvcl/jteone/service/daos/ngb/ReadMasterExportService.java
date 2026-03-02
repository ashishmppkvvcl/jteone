package com.mppkvvcl.jteone.service.daos.ngb;

import com.mppkvvcl.ngbdao.daos.ReadMasterExportDAO;
import com.mppkvvcl.ngbdao.interfaces.ReadMasterExportInterface;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadMasterExportService {

    @Autowired
    private ReadMasterExportDAO readMasterExportDAO;

    public List<ReadMasterExportInterface> getByConsumerNoAndReplacementFlagAndUsedOnBill(String consumerNo, String replacementFlag, boolean usedOnBill, Sort sort) {
        if (StringUtils.isEmpty(consumerNo) || StringUtils.isEmpty(replacementFlag) || sort == null)
            return null;

        return readMasterExportDAO.getByConsumerNoAndReplacementFlagAndUsedOnBill(consumerNo, replacementFlag, usedOnBill, sort);
    }

    public List<ReadMasterExportInterface> getByConsumerNoAndBillMonthAndReplacementFlagAndUsedOnBill(String consumerNo, String billMonth, String replacementFlag, boolean usedOnBill, Sort sort) {
        if (StringUtils.isEmpty(consumerNo) || StringUtils.isEmpty(billMonth) || StringUtils.isEmpty(replacementFlag) || sort == null)
            return null;

        return readMasterExportDAO.getByConsumerNoAndBillMonthAndReplacementFlagAndUsedOnBill(consumerNo, billMonth, replacementFlag, usedOnBill, sort);
    }

    public List<ReadMasterExportInterface> getByConsumerNoAndBillMonthAndMeterIdentifier(String consumerNo, String billMonth, String meterIdentifier, Sort sort) {
        if (StringUtils.isEmpty(consumerNo) || StringUtils.isEmpty(billMonth) || StringUtils.isEmpty(meterIdentifier) || sort == null)
            return null;

        return readMasterExportDAO.getByConsumerNoAndBillMonthAndMeterIdentifier(consumerNo, billMonth, meterIdentifier, sort);
    }
}
