package com.mppkvvcl.jteone.service.daos.ngb;

import com.mppkvvcl.ngbdao.daos.EmployeeConnectionMappingDAO;
import com.mppkvvcl.ngbdao.interfaces.EmployeeConnectionMappingInterface;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeConnectionMappingService {

    @Autowired
    private EmployeeConnectionMappingDAO employeeConnectionMappingDAO;

    public EmployeeConnectionMappingInterface getActiveEmployeeByConsumerNo(String consumerNo) {
        if (StringUtils.isEmpty(consumerNo)) return null;

        return employeeConnectionMappingDAO.getActiveEmployeeByConsumerNo(consumerNo);
    }
}
