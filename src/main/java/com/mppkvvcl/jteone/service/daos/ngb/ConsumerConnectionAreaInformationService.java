package com.mppkvvcl.jteone.service.daos.ngb;

import com.mppkvvcl.ngbdao.daos.ConsumerInformationDAO;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsumerConnectionAreaInformationService {

    @Autowired
    private ConsumerInformationDAO consumerInformationDAO;

    public Object[] getForBillTemplateByConsumerNo(String consumerNo) {
        if (StringUtils.isEmpty(consumerNo)) return null;

        return consumerInformationDAO.getForBillTemplateByConsumerNo(consumerNo);
    }
}
