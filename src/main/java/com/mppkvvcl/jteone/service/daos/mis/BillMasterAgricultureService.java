package com.mppkvvcl.jteone.service.daos.mis;

import com.mppkvvcl.jteone.enums.SortOrder;
import com.mppkvvcl.jteone.utility.GlobalUtil;
import com.mppkvvcl.misdao.daos.BillMasterAgricultureDAO;
import com.mppkvvcl.misdao.interfaces.BillMasterAgricultureInterface;
import com.mppkvvcl.misdao.interfaces.ConfiguratorInterface;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Service
public class BillMasterAgricultureService {

    private static final Logger log = LoggerFactory.getLogger(BillMasterAgricultureService.class);
    private static final Map<String, List<String>> agricultureBillCycleMappingByStartMonth = new HashMap<>();

    @Autowired
    private BillMasterAgricultureDAO billMasterAgricultureDAO;

    @Autowired
    private ConfiguratorService configuratorService;

    public String getMaxBillMonthByConsumerNo(String consumerNo) {
        if (StringUtils.isEmpty(consumerNo)) return null;

        return billMasterAgricultureDAO.getMaxBillMonthByConsumerNo(consumerNo);
    }

    public BillMasterAgricultureInterface getByConsumerNoAndBillMonth(String consumerNo, String billMonth) {
        if (consumerNo == null || billMonth == null) return null;

        return billMasterAgricultureDAO.getByConsumerNoAndBillMonth(consumerNo, billMonth);
    }

    public List<String> getAgricultureBillMonthsForCycle(final String billMonth) {
        if (StringUtils.isEmpty(billMonth)) return null;

        if (agricultureBillCycleMappingByStartMonth.containsKey(billMonth)) {
            return new ArrayList<>(agricultureBillCycleMappingByStartMonth.get(billMonth));
        }

        final String propertyValue = configuratorService.getValueByPropertyName(ConfiguratorInterface.PROPERTY_NAME_AGRICULTURE_BILLING_CYCLE_MONTHS);
        if (StringUtils.isEmpty(propertyValue)) {
            log.info("Agriculture billing cycle property not configured");
            return null;
        }
        final String[] configuredAgricultureBillMonthCycle = propertyValue.split(",");
        if (Arrays.stream(configuredAgricultureBillMonthCycle).noneMatch(ele -> billMonth.startsWith(ele.trim()))) {
            log.info("Invalid bill month");
            return null;
        }

        final int configuredMonthCount = configuredAgricultureBillMonthCycle.length;
        final LocalDate refDate = GlobalUtil.getDateFromStringBillMonth(billMonth);
        final int year = refDate.getYear();
        final int startMonth = refDate.getMonth().getValue();
        final int totalMonthCount = Month.values().length;
        final int expectedMonthCount = totalMonthCount / configuredMonthCount;
        final int beforeThis = (startMonth + expectedMonthCount) - totalMonthCount - 1;
        List<String> billMonths = new ArrayList<>();
        for (Month month : Month.values()) {
            if (month.getValue() >= startMonth && month.getValue() < (startMonth + expectedMonthCount)) {
                billMonths.add(month.name().substring(0, 3).concat("-").concat(String.valueOf(year)));
            }
            if ((totalMonthCount - startMonth) < expectedMonthCount && month.getValue() <= beforeThis) {
                billMonths.add(month.name().substring(0, 3).concat("-").concat(String.valueOf(year + 1)));
            }
        }

        if (!GlobalUtil.isEmpty(billMonths)) {
            billMonths = GlobalUtil.sortMonths(billMonths, SortOrder.ASC);
            agricultureBillCycleMappingByStartMonth.put(billMonth, billMonths);
        }
        return billMonths;
    }

    public String getBillDuration(final String billMonth) {
        if (StringUtils.isEmpty(billMonth)) return null;

        final List<String> billMonths = getAgricultureBillMonthsForCycle(billMonth);
        if (GlobalUtil.isEmpty(billMonths)) return null;

        return billMonths.get(0) + " TO " + billMonths.get(billMonths.size() - 1);
    }
}
