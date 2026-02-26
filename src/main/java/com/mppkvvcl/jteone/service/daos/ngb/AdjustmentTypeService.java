package com.mppkvvcl.jteone.service.daos.ngb;

import com.mppkvvcl.ngbdao.daos.AdjustmentTypeDAO;
import com.mppkvvcl.ngbdao.interfaces.AdjustmentTypeInterface;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdjustmentTypeService {

    final Map<Integer, String> descriptionMap = new HashMap<>();

    @Autowired
    private AdjustmentTypeDAO adjustmentTypeDAO;

    public AdjustmentTypeInterface getByCode(final int code) {

        return adjustmentTypeDAO.getByCode(code);
    }

    public String getDescriptionByCode(final int code) {

        String description = descriptionMap.get(code);
        if (StringUtils.isNotEmpty(description)) return description;


        final AdjustmentTypeInterface adjustmentType = adjustmentTypeDAO.getByCode(code);
        if (adjustmentType != null) descriptionMap.put(code, adjustmentType.getDetail());
        return descriptionMap.get(code);
    }
}
