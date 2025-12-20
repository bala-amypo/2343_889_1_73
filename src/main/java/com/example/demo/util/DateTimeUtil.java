package com.example.demo.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    public static String format(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DEFAULT_FORMATTER) : null;
    }
    
    public static LocalDateTime parse(String dateTimeStr) {
        return dateTimeStr != null ? LocalDateTime.parse(dateTimeStr, DEFAULT_FORMATTER) : null;
    }
    
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }
}