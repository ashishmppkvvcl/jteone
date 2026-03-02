package com.mppkvvcl.jteone.service.daos.ngb;

import com.mppkvvcl.ngbdao.daos.ReadMasterExportTODDAO;
import com.mppkvvcl.ngbdao.interfaces.ReadMasterExportTODInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReadMasterExportTODService {

    @Autowired
    private ReadMasterExportTODDAO readMasterExportTODDAO;

    public ReadMasterExportTODInterface getByReadMasterExportId(long readMasterExportId) {

        return readMasterExportTODDAO.getByReadMasterExportId(readMasterExportId);
    }
}
