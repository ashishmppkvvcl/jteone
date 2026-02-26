package com.mppkvvcl.jteone.service.daos.ngb;

import com.mppkvvcl.ngbdao.daos.BillTODDAO;
import com.mppkvvcl.ngbdao.interfaces.BillTODInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillTODService {

    @Autowired
    private BillTODDAO billTODDAO;

    public BillTODInterface getByBillId(long billId) {

        return billTODDAO.getByBillId(billId);
    }
}
