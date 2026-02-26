package com.mppkvvcl.jteone.service.daos.ngb;

import com.mppkvvcl.ngbdao.daos.AdjustmentDAO;
import com.mppkvvcl.ngbdao.interfaces.AdjustmentInterface;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service("ngbAdjustmentService")
public class AdjustmentService {

    private static final Logger log = LoggerFactory.getLogger(AdjustmentService.class);

    @Autowired
    @Qualifier("ngbAdjustmentDAO")
    private AdjustmentDAO adjustmentDAO;

    public List<AdjustmentInterface> getByConsumerNoAndCodeAndApprovalStatusAndDeletedOrderByPostingBillMonthASC(String consumerNo, int code, String approvalStatus, boolean deleted) {
        if (StringUtils.isEmpty(consumerNo) || StringUtils.isEmpty(approvalStatus)) return null;

        return adjustmentDAO.getByConsumerNoAndCodeAndApprovalStatusAndDeletedOrderByPostingBillMonthASC(consumerNo, code, approvalStatus, deleted);
    }

    public List<AdjustmentInterface> getByConsumerNoAndCodeAndApprovalStatusAndPostedAndDeletedOrderByPostingBillMonthASC(String consumerNo, int code, String approvalStatus, boolean posted, boolean deleted) {
        if (StringUtils.isEmpty(consumerNo) || StringUtils.isEmpty(approvalStatus)) return null;

        return adjustmentDAO.getByConsumerNoAndCodeAndApprovalStatusAndPostedAndDeletedOrderByPostingBillMonthASC(consumerNo, code, approvalStatus, posted, deleted);
    }

    public List<AdjustmentInterface> getByConsumerNoAndPostingBillMonthAndApprovalStatusAndPostedAndDeletedOrderByCodeAscIdAsc(String consumerNo, String postingBillMonth, String approvalStatus, boolean posted, boolean deleted) {
        if (StringUtils.isEmpty(consumerNo) || StringUtils.isEmpty(postingBillMonth) || StringUtils.isEmpty(approvalStatus))
            return null;

        return adjustmentDAO.getByConsumerNoAndPostingBillMonthAndApprovalStatusAndPostedAndDeletedOrderByCodeAscIdAsc(consumerNo, postingBillMonth, approvalStatus, posted, deleted);
    }

    public BigDecimal getAmountByConsumerNoAndCodeAndApprovalStatusAndPostedAndDeleted(String consumerNo, int code, String approvalStatus, boolean posted, boolean deleted) {
        if (StringUtils.isEmpty(consumerNo) || StringUtils.isEmpty(approvalStatus))
            return null;

        return adjustmentDAO.getAmountByConsumerNoAndCodeAndApprovalStatusAndPostedAndDeleted(consumerNo, code, approvalStatus, posted, deleted);
    }

    public BigDecimal getAmountByConsumerNoAndCodeAndPostingBillMonthAndApprovalStatusAndPostedAndDeleted(String consumerNo, int code, String postingBillMonth, String approvalStatus, boolean posted, boolean deleted) {
        if (StringUtils.isEmpty(consumerNo) || StringUtils.isEmpty(postingBillMonth) || StringUtils.isEmpty(approvalStatus))
            return null;

        return adjustmentDAO.getAmountByConsumerNoAndCodeAndPostingBillMonthAndApprovalStatusAndPostedAndDeleted(consumerNo, code, postingBillMonth, approvalStatus, posted, deleted);
    }
}
