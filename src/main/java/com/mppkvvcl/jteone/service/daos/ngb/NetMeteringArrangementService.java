package com.mppkvvcl.jteone.service.daos.ngb;

import com.mppkvvcl.ngbdao.daos.NetMeteringArrangementDAO;
import com.mppkvvcl.ngbdao.interfaces.NetMeteringArrangementInterface;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NetMeteringArrangementService {

    @Autowired
    private NetMeteringArrangementDAO netMeteringArrangementDAO;

    public List<NetMeteringArrangementInterface> getNonParentByParentConsumerNoAndBillMonthBetween(String consumerNo, String billMonth) {
        if (StringUtils.isEmpty(consumerNo) || StringUtils.isEmpty(billMonth)) return null;

        return netMeteringArrangementDAO.getNonParentByParentConsumerNoAndBillMonthBetween(consumerNo, billMonth);
    }
}
