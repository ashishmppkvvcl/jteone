package com.mppkvvcl.jteone.service.daos.ngb;

import com.mppkvvcl.ngbdao.daos.MeterReaderInformationDAO;
import com.mppkvvcl.ngbdao.interfaces.MeterReaderInformationInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeterReaderInformationService {

    @Autowired
    private MeterReaderInformationDAO meterReaderInformationDAO;

    public MeterReaderInformationInterface getByReadingDiaryNoId(long readingDiaryNoId) {

        return meterReaderInformationDAO.getByReadingDiaryNoId(readingDiaryNoId);
    }
}
