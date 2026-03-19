package com.mppkvvcl.jteone.service.daos.ngb;

import com.mppkvvcl.jteone.utility.GlobalUtil;
import com.mppkvvcl.ngbdao.daos.PaymentDAO;
import com.mppkvvcl.ngbdao.interfaces.PaymentInterface;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentDAO paymentDAO;

    public List<PaymentInterface> getByConsumerNoAndDeletedAndPostingBillMonthLessThanEqualOrderByPayDateDESC(String consumerNo, boolean deleted, String postingBillMonth, int limit) {
        if (StringUtils.isEmpty(consumerNo) || StringUtils.isEmpty(postingBillMonth))
            return null;

        final Pageable pageable = PageRequest.of(0, limit);
        return paymentDAO.getByConsumerNoAndDeletedAndPostingBillMonthLessThanEqualOrderByPayDateDESC(consumerNo, deleted, postingBillMonth, pageable);
    }

    public Long getAmountByConsumerNoAndPostingBillMonthAndPostedDeleted(String consumerNo, String postingBillMonth, boolean posted, boolean deleted) {
        if (StringUtils.isEmpty(consumerNo) || StringUtils.isEmpty(postingBillMonth))
            return null;

        return paymentDAO.getAmountByConsumerNoAndPostingBillMonthAndPostedDeleted(consumerNo, postingBillMonth, posted, deleted);
    }

    public Long getAmountByConsumerNoAndPostingBillMonthInAndDeleted(String consumerNo, List<String> postingBillMonths, boolean deleted) {
        if (StringUtils.isEmpty(consumerNo) || GlobalUtil.isEmpty(postingBillMonths)) return null;

        return paymentDAO.getAmountByConsumerNoAndPostingBillMonthInAndDeleted(consumerNo, postingBillMonths, deleted);
    }
}
