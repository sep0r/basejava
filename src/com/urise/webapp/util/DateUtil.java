package com.urise.webapp.util;

import com.urise.webapp.model.Organization;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateUtil {
    public static final LocalDate LAST_DATE = LocalDate.of(2100, 1, 1);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM-yyyy", Locale.US);
    private static final String NOW = "Сейчас";

    public static String jspDataFormatter(LocalDate date) {
        if (date == null) return "";
        return date.equals(LAST_DATE) ? NOW : date.format(FORMATTER);
    }

    public static String jspFormatter(Organization.Position position) {
        return jspDataFormatter(position.getStartDate()) + " / " + jspDataFormatter(position.getFinishDate());
    }
}