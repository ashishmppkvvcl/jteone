package com.mppkvvcl.jteone.service.daos.ngb;

import com.mppkvvcl.ngbdao.daos.ConsumerMeterMappingDAO;
import com.mppkvvcl.ngbdao.interfaces.ConsumerMeterMappingInterface;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumerMeterMappingService {

    @Autowired
    private ConsumerMeterMappingDAO consumerMeterMappingDAO;

    public List<ConsumerMeterMappingInterface> getByConsumerNoAndBillMonthOrderByInstallationDateDescIdDesc(String consumerNo, String billMonth) {
        if (StringUtils.isEmpty(consumerNo) || StringUtils.isEmpty(billMonth)) return null;

        return consumerMeterMappingDAO.getByConsumerNoAndBillMonthOrderByInstallationDateDescIdDesc(consumerNo, billMonth);
    }
}
