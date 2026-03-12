package com.mppkvvcl.jteone.utility;

import com.mppkvvcl.jteone.enums.SortOrder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class GlobalUtility {
    private static final Logger log = LoggerFactory.getLogger(GlobalUtility.class);

    public static final String EXPORT_DATE_FORMAT = "dd/MM/yyyy";
    public static final String BILL_MONTH_FORMAT = "MMM-yyyy";
    public static final int GROUP_NO_PREFIX_LENGTH = 3;
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
                dates.add(getDateFromStringBillMonth(month));
            }
        });
        List<LocalDate> sortedDates = dates;
        if (SortOrder.ASC.equals(sortOrder)) {
            sortedDates = dates.stream().sorted().collect(Collectors.toList());
        } else {
            sortedDates = dates.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        }
        return sortedDates.stream().map(billDate -> getBillMonthInStringFromDate(billDate).toUpperCase()).collect(Collectors.toList());
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
            final LocalDate monthDate = getDateFromStringBillMonth(billMonth);
            previousMonth = getBillMonthInStringFromDate(monthDate.minusMonths(1L));
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
            dateInStringFormat = date.format(DateTimeFormatter.ofPattern(dateFormat, Locale.ENGLISH));
        } catch (Exception exception) {
            log.error("Exception occurred while converting date to string", exception);
        }
        return dateInStringFormat;
    }

    public static LocalDate getDateFromStringFormat(String dateInStringFormat, String dateFormat) {
        if (dateInStringFormat == null || dateFormat == null) return null;

        LocalDate dateInDateFormat = null;
        try {
            dateInDateFormat = LocalDate.parse(dateInStringFormat, DateTimeFormatter.ofPattern(dateFormat, Locale.ENGLISH));
        } catch (Exception exception) {
            log.error("Exception occurred while converting string to date", exception);
        }
        return dateInDateFormat;
    }

    public static LocalDate getDateFromStringBillMonth(String dateInStringFormat) {
        if (dateInStringFormat == null) return null;

        LocalDate date = null;
        try {
            final Date dateInDateFormat = new SimpleDateFormat(BILL_MONTH_FORMAT).parse(dateInStringFormat);
            Instant instant = dateInDateFormat.toInstant();
            date = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (Exception exception) {
            log.error("Exception occurred while converting bill month string to date", exception);
        }
        return date;
    }

    public static String getBillMonthInStringFromDate(final LocalDate date) {
        if (date == null) return null;

        String dateInStringFormat = null;
        try {
            final Date utilDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
            dateInStringFormat = new SimpleDateFormat(BILL_MONTH_FORMAT).format(utilDate);
        } catch (Exception exception) {
            log.error("Exception occurred while converting date to Bill Month string", exception);
        }
        return dateInStringFormat;
    }

    public static String genericMask(final String inputData) {
        if (org.springframework.util.StringUtils.isEmpty(inputData)) return null;
        int lastIndex = inputData.length() - 1;
        int lMinusTwo = inputData.length() - 3;
        if (lastIndex <= 2 || lMinusTwo <= 0) return inputData;

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(inputData.charAt(0));
        for (int index = 1; index < lMinusTwo; index++) {
            stringBuilder.append("x");
        }
        stringBuilder.append(inputData.charAt(lMinusTwo));
        stringBuilder.append(inputData.charAt(inputData.length() - 2));
        stringBuilder.append(inputData.charAt(lastIndex));
        return stringBuilder.toString();
    }

    public static String maskMobileNO(final String inputMobileNo) {
        if (inputMobileNo == null || inputMobileNo.length() != 10) {
            return inputMobileNo;
        }
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(inputMobileNo.charAt(0));
        stringBuilder.append(inputMobileNo.charAt(1));
        stringBuilder.append("x");
        stringBuilder.append("x");
        stringBuilder.append("x");
        stringBuilder.append("x");
        stringBuilder.append("x");
        stringBuilder.append(inputMobileNo.charAt(7));
        stringBuilder.append(inputMobileNo.charAt(8));
        stringBuilder.append(inputMobileNo.charAt(9));
        return stringBuilder.toString();
    }

    public static String maskEmailId(final String inputEmailId) {
        if (StringUtils.isEmpty(inputEmailId)) return null;

        int indexOfAt = inputEmailId.indexOf("@");
        if (indexOfAt <= 0) return null;
        if (indexOfAt < 3) return inputEmailId;

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(inputEmailId.charAt(0));
        stringBuilder.append(inputEmailId.charAt(1));
        for (int index = 2; index < indexOfAt; index++) {
            stringBuilder.append("*");
        }
        stringBuilder.append(inputEmailId.substring(indexOfAt));
        return stringBuilder.toString();
    }
}
