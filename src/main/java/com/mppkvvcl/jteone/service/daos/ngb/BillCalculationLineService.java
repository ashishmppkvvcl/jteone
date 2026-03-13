package com.mppkvvcl.jteone.service.daos.ngb;

import com.mppkvvcl.jteone.utility.GlobalUtil;
import com.mppkvvcl.ngbdao.daos.BillCalculationLineDAO;
import com.mppkvvcl.ngbdao.interfaces.BillCalculationLineInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillCalculationLineService {

    @Autowired
    private BillCalculationLineDAO billCalculationLineDAO;

    public List<BillCalculationLineInterface> getByBillId(long billId) {

        return billCalculationLineDAO.getByBillId(billId);
    }

    public List<BillCalculationLineInterface> getByBillIdAndHead(long billId, String head) {
        if (head == null) return null;

        return billCalculationLineDAO.getByBillIdAndHead(billId, head);
    }

    public List<BillCalculationLineInterface> getByBillIdAndHeadIn(long billId, List<String> heads) {
        if (GlobalUtil.isEmpty(heads)) return null;

        return billCalculationLineDAO.getByBillIdAndHeadIn(billId, heads);
    }
}
