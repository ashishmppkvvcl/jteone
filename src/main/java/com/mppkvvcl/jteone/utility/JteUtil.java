package com.mppkvvcl.jteone.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Component
public class JteUtil {
    private static final Logger log = LoggerFactory.getLogger(JteUtil.class);

    // Check null & convert BigDecimal to 2 place String and return
    public static String get(final BigDecimal val) {
        if (val == null) return null;

        return val.setScale(2, RoundingMode.HALF_UP).toPlainString();
    }

    // Check null & convert Date to String and return
    public static String get(final LocalDate val) {
        if (val == null) return null;

        return GlobalUtil.getDateInStringFromDate(val, GlobalUtil.EXPORT_DATE_FORMAT);
    }
}
