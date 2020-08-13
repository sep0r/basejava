package com.urise.webapp.util;

import com.urise.webapp.model.Organization;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.urise.webapp.util.HtmlUtil.isEmpty;

public class DateUtil {
    public static final LocalDate LAST_DATE = LocalDate.of(3000, 1, 1);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM-yyyy", Locale.US);
    private static final String NOW = "Сейчас";

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static LocalDate dataParser(String date) {
        if (isEmpty(date) || NOW.equals(date)) return LAST_DATE;
        YearMonth yearMonth = YearMonth.parse(date, FORMATTER);
        return LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
    }

    public static String jspDataFormatter(LocalDate date) {
        if (date == null) return "";
        return date.equals(LAST_DATE) ? NOW : date.format(FORMATTER);
    }

    public static String jspFormatter(Organization.Position position) {
        return jspDataFormatter(position.getStartDate()) + " / " + jspDataFormatter(position.getFinishDate());
    }
}