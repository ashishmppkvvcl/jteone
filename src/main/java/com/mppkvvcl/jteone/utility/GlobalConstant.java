package com.mppkvvcl.jteone.utility;

import com.mppkvvcl.ngbdao.interfaces.BillCalculationLineInterface;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GlobalConstant {

    public static final String DISCOM_MPCZ = "mpcz";
    public static final String DISCOM_MPEZ = "mpez";
    public static final String DISCOM_MPWZ = "mpwz";

    public static final String MIS_JTE_RESOURCES = "MIS_JTE_RESOURCES";
    public static final String YES_ABBREVIATION = "Y";
    public static final String NO_ABBREVIATION = "N";
    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_INACTIVE = "INACTIVE";

    public static final String OTS_OPTION_LUMPSUM = "LUMPSUM";
    public static final String OTS_OPTION_INSTALLMENT = "INSTALLMENT";
    public static final String DEFAULTER = "DEFAULTER";

    public static final String APPROVED = "APPROVED";
    public static final String PENDING = "PENDING";
    public static final String REJECTED = "REJECTED";

    public static final Map<String, String> BILL_CALCULATION_LINE_HEAD_MAPPING = Map.of(BillCalculationLineInterface.HEAD_ENERGY_CHARGE, "ENERGY CHARGE", BillCalculationLineInterface.HEAD_FIXED_CHARGE, "FIXED CHARGE");
}
