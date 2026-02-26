package com.mppkvvcl.jteone.service.daos.ngb;

import com.mppkvvcl.ngbdao.daos.MeterMasterDAO;
import com.mppkvvcl.ngbdao.interfaces.MeterMasterInterface;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeterMasterService {

    @Autowired
    private MeterMasterDAO meterMasterDAO;

    public MeterMasterInterface getByIdentifier(final String identifier) {
        if (StringUtils.isEmpty(identifier)) return null;

        return meterMasterDAO.getByIdentifier(identifier);
    }
}
