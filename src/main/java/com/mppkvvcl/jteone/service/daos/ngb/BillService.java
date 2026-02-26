package com.mppkvvcl.jteone.service.daos.ngb;

import com.mppkvvcl.ngbdao.daos.BillDAO;
import com.mppkvvcl.ngbdao.interfaces.BillInterface;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillService {

    @Autowired
    private BillDAO billDAO;

    public String getMaxBillMonthByConsumerNoAndDeleted(final String consumerNo, final boolean deleted) {
        if (StringUtils.isEmpty(consumerNo)) return null;

        return billDAO.getMaxBillMonthByConsumerNoAndDeleted(consumerNo, deleted);
    }

    public BillInterface getByConsumerNoAndBillMonthAndDeleted(String consumerNo, String billMonth, boolean deleted) {
        if (consumerNo == null || billMonth == null) return null;

        return billDAO.getByConsumerNoAndBillMonthAndDeleted(consumerNo, billMonth, deleted);
    }
}
