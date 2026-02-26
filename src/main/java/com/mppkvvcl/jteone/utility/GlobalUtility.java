package com.mppkvvcl.jteone.utility;

import com.mppkvvcl.jteone.enums.SortOrder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class GlobalUtility {
    private static final Logger log = LoggerFactory.getLogger(GlobalUtility.class);

    public static final String EXPORT_DATE_FORMAT = "dd/MM/yyyy";
    public static final String BILL_MONTH_FORMAT = "MMM-yyyy";
    public static final int GROUP_NO_PREFIX_LENGTH = 3;
    public static final String MASKING_CHARACTER = "x";
    public static final BigDecimal HP_TO_KW_CONVERSION_MULTIPLIER = new BigDecimal("0.746");
    public static final BigDecimal KW_TO_HP_CONVERSION_MULTIPLIER = new BigDecimal("1.341");

    public static boolean isEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static List<String> sortMonths(final List<String> months, final SortOrder sortOrder) {
        if (GlobalUtility.isEmpty(months) || sortOrder == null) return months;

        final List<LocalDate> dates = new ArrayList<>();
        //months.parallelStream().forEach(month -> {
        months.forEach(month -> {
            if (StringUtils.isNotEmpty(month)) {
                dates.add(getDateFromStringFormat(month, BILL_MONTH_FORMAT));
            }
        });
        List<LocalDate> sortedDates = dates;
        if (SortOrder.ASC.equals(sortOrder)) {
            sortedDates = dates.stream().sorted().collect(Collectors.toList());
        } else {
            sortedDates = dates.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        }
        return sortedDates.stream().map(billDate -> getDateInStringFromDate(billDate, BILL_MONTH_FORMAT).toUpperCase()).collect(Collectors.toList());
    }

    public static BigDecimal add(final BigDecimal... values) {
        if (values == null) return BigDecimal.ZERO;

        return Arrays.stream(values).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static BigDecimal subtract(final BigDecimal... values) {
        if (values == null) return BigDecimal.ZERO;

        return Arrays.stream(values).filter(Objects::nonNull).reduce(BigDecimal::subtract).orElse(BigDecimal.ZERO);
    }

    public static BigDecimal convertHPToKW(final BigDecimal valueInHP) {
        if (valueInHP == null) return null;

        return valueInHP.multiply(HP_TO_KW_CONVERSION_MULTIPLIER);
    }

    public static String getPreviousMonth(final String billMonth) {
        if (StringUtils.isEmpty(billMonth)) return null;

        String previousMonth = null;
        try {
            final LocalDate monthDate = getDateFromStringFormat(billMonth, BILL_MONTH_FORMAT);
            previousMonth = getDateInStringFromDate(monthDate.minusMonths(1L), BILL_MONTH_FORMAT);
            previousMonth = previousMonth.toUpperCase();

        } catch (Exception e) {
        }
        return previousMonth;
    }

    public static Sort getSortObject(final SortOrder sortOrder, final String... sortBy) {
        String[] sortParameter = {"id"};
        if (sortBy != null && sortBy.length > 0) {
            sortParameter = sortBy;
        }
        final Sort.Direction sortDirection = SortOrder.DESC.equals(sortOrder) ? Sort.Direction.DESC : Sort.Direction.ASC;
        return Sort.by(sortDirection, sortParameter);
    }

    public static String getDateInStringFromDate(final LocalDate date, final String dateFormat) {
        if (date == null || dateFormat == null) return null;

        String dateInStringFormat = null;
        try {
            dateInStringFormat = date.format(DateTimeFormatter.ofPattern(dateFormat));
        } catch (Exception exception) {
            log.error("Exception occurred while converting date to string", exception);
        }
        return dateInStringFormat;
    }

    public static LocalDate getDateFromStringFormat(String dateInStringFormat, String dateFormat) {
        if (dateInStringFormat == null || dateFormat == null) return null;

        LocalDate dateInDateFormat = null;
        try {
            dateInDateFormat = LocalDate.parse(dateInStringFormat, DateTimeFormatter.ofPattern(dateFormat));
        } catch (Exception exception) {
            log.error("Exception occurred while converting string to date", exception);
        }
        return dateInDateFormat;
    }
}
