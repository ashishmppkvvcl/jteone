package com.mppkvvcl.jteone.service.daos.ngb;

import com.mppkvvcl.ngbdao.daos.EEDivisionMappingDAO;
import com.mppkvvcl.ngbdao.interfaces.EEDivisionMappingInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EEDivisionMappingService {

    @Autowired
    private EEDivisionMappingDAO eeDivisionMappingDAO;

    public EEDivisionMappingInterface getByDivisionId(final long divisionId) {

        return eeDivisionMappingDAO.getByDivisionId(divisionId);
    }
}
