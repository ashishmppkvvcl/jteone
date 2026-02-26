package com.mppkvvcl.jteone.service.daos.ngb;

import com.mppkvvcl.ngbdao.daos.ZoneDAO;
import com.mppkvvcl.ngbdao.interfaces.ZoneInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZoneService {

    @Autowired
    private ZoneDAO zoneDAO;

    public ZoneInterface getByCode(final String code) {
        if (code == null) return null;

        return zoneDAO.getByCode(code);
    }
}
