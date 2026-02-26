package com.mppkvvcl.jteone.service.daos.ngb;

import com.mppkvvcl.ngbdao.daos.DivisionDAO;
import com.mppkvvcl.ngbdao.interfaces.DivisionInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DivisionService {

    @Autowired
    private DivisionDAO divisionDAO;

    public DivisionInterface getById(long id) {

        return divisionDAO.getById(id);
    }
}
