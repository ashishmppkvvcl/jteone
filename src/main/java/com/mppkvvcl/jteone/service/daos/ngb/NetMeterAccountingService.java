package com.mppkvvcl.jteone.service.daos.ngb;

import com.mppkvvcl.ngbdao.daos.NetMeterAccountingDAO;
import com.mppkvvcl.ngbdao.interfaces.NetMeterAccountingInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NetMeterAccountingService {

    @Autowired
    private NetMeterAccountingDAO netMeterAccountingDAO;

    public NetMeterAccountingInterface getByBillId(long billId) {

        return netMeterAccountingDAO.getByBillId(billId);
    }
}
