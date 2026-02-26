package com.mppkvvcl.jteone.service.daos.ngb;

import com.mppkvvcl.ngbdao.daos.BillTypeCodeDAO;
import com.mppkvvcl.ngbdao.interfaces.BillTypeCodeInterface;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BillTypeCodeService {

    final Map<String, String> descriptionMap = new HashMap<>();

    @Autowired
    private BillTypeCodeDAO billTypeCodeDAO;

    public BillTypeCodeInterface getByCode(final String code) {
        if (code == null) return null;

        return billTypeCodeDAO.getByCode(code);
    }

    public String getDescriptionByCode(final String code) {
        if (code == null) return null;

        String description = descriptionMap.get(code);
        if (StringUtils.isNotEmpty(description)) return description;


        final BillTypeCodeInterface billTypeCode = billTypeCodeDAO.getByCode(code);
        if (billTypeCode != null) descriptionMap.put(code, billTypeCode.getDescription());
        return descriptionMap.get(code);
    }
}
