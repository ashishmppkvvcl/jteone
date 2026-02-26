package com.mppkvvcl.jteone.service.daos.ngb;

import com.mppkvvcl.ngbdao.daos.ConsumerConnectionInformationDAO;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsumerConnectionInformationService {

    @Autowired
    private ConsumerConnectionInformationDAO consumerConnectionInformationDAO;

    public Object[] getForBillTemplateByConsumerNo(String consumerNo) {
        if (StringUtils.isEmpty(consumerNo)) return null;

        return consumerConnectionInformationDAO.getForBillTemplateByConsumerNo(consumerNo);
    }
}
