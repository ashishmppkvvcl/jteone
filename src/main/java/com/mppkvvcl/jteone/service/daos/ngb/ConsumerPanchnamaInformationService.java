package com.mppkvvcl.jteone.service.daos.ngb;

import com.mppkvvcl.ngbdao.daos.ConsumerPanchnamaInformationDAO;
import com.mppkvvcl.ngbdao.interfaces.ConsumerPanchnamaInformationInterface;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumerPanchnamaInformationService {

    @Autowired
    private ConsumerPanchnamaInformationDAO consumerPanchnamaInformationDAO;

    public List<ConsumerPanchnamaInformationInterface> getByConsumerNoAndPostedAndDeleted(String consumerNo, boolean posted, boolean deleted) {
        if (StringUtils.isEmpty(consumerNo)) return null;

        return consumerPanchnamaInformationDAO.getByConsumerNoAndPostedAndDeleted(consumerNo, posted, deleted);
    }
}
