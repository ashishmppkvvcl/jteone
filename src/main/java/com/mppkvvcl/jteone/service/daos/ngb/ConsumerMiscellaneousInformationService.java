package com.mppkvvcl.jteone.service.daos.ngb;

import com.mppkvvcl.ngbdao.daos.ConsumerMiscellaneousInformationDAO;
import com.mppkvvcl.ngbdao.interfaces.ConsumerMiscellaneousInformationInterface;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * For PDF Bill fetching. as we are fetching miscellaneous information from NGB; the properties may get updated after last bill generation. So when we fetch the active property value,
 * that is not necessarily the same as it was when bill was prepared. So instead of fetching property with status ACTIVE, we should fetch with billDate between Start and End date
 * irrespective of status
 */

@Service
public class ConsumerMiscellaneousInformationService {

    @Autowired
    private ConsumerMiscellaneousInformationDAO consumerMiscellaneousInformationDAO;

    public List<ConsumerMiscellaneousInformationInterface> getActivePropertyByConsumerNo(String consumerNo) {
        if (StringUtils.isEmpty(consumerNo)) return null;

        return consumerMiscellaneousInformationDAO.getActivePropertyByConsumerNo(consumerNo);
    }

    public ConsumerMiscellaneousInformationInterface getActivePropertyByConsumerNoAndPropertyName(String consumerNo, String propertyName) {
        if (StringUtils.isEmpty(consumerNo) || StringUtils.isEmpty(propertyName)) return null;

        return consumerMiscellaneousInformationDAO.getActivePropertyByConsumerNoAndPropertyName(consumerNo, propertyName);
    }

    public String getActivePropertyValueByConsumerNoAndPropertyName(String consumerNo, String propertyName) {
        if (StringUtils.isEmpty(consumerNo) || StringUtils.isEmpty(propertyName)) return null;

        return consumerMiscellaneousInformationDAO.getActivePropertyValueByConsumerNoAndPropertyName(consumerNo, propertyName);
    }
}
