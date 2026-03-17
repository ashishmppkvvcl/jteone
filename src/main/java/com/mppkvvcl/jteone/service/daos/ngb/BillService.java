package com.mppkvvcl.jteone.service.daos.ngb;

import com.mppkvvcl.ngbdao.daos.BillDAO;
import com.mppkvvcl.ngbdao.interfaces.BillInterface;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BillService {

    @Autowired
    private BillDAO billDAO;

    public String getMaxBillMonthByConsumerNoAndDeleted(final String consumerNo, final boolean deleted) {
        if (StringUtils.isEmpty(consumerNo)) return null;

        return billDAO.getMaxBillMonthByConsumerNoAndDeleted(consumerNo, deleted);
    }

    public BillInterface getActiveByConsumerNoAndBillMonth(String consumerNo, String billMonth) {
        if (consumerNo == null || billMonth == null) return null;

        return billDAO.getActiveByConsumerNoAndBillMonth(consumerNo, billMonth);
    }

    public BigDecimal[] getActiveBilledMdAndPfByConsumerNoAndBillMonth(String consumerNo, String billMonth) {
        if (consumerNo == null || billMonth == null) return null;

        return billDAO.getActiveBilledMdAndPfByConsumerNoAndBillMonth(consumerNo, billMonth);
    }
}
