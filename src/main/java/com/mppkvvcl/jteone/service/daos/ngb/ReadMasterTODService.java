package com.mppkvvcl.jteone.service.daos.ngb;

import com.mppkvvcl.ngbdao.daos.ReadMasterTODDAO;
import com.mppkvvcl.ngbdao.interfaces.ReadMasterTODInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReadMasterTODService {

    @Autowired
    private ReadMasterTODDAO readMasterTODDAO;

    public ReadMasterTODInterface getByReadMasterId(long readMasterId) {

        return readMasterTODDAO.getByReadMasterId(readMasterId);
    }
}
