package com.mppkvvcl.jteone.service.daos.ngb;

import com.mppkvvcl.ngbdao.daos.CircleECGRFInformationDAO;
import com.mppkvvcl.ngbdao.interfaces.CircleECGRFInformationInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CircleECGRFInformationService {

    @Autowired
    private CircleECGRFInformationDAO circleECGRFInformationDAO;

    public List<CircleECGRFInformationInterface> getByCircleIdOrderByIndexAsc(long circleId) {

        return circleECGRFInformationDAO.getByCircleIdOrderByIndexAsc(circleId);
    }
}
