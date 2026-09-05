package com.app.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

/**
 * Central place for all input validation rules.
 * Every servlet should call these methods instead of writing its own
 * regex or checks, so the rules stay consistent across the whole system.
 */
public class ValidationUtil {

    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z .'-]{2,100}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{10}$");
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{3,30}$");

    // Clinic working hours, used to reject appointments outside these hours
    private static final LocalTime CLINIC_OPEN = LocalTime.of(8, 0);
    private static final LocalTime CLINIC_CLOSE = LocalTime.of(18, 0);

    private ValidationUtil() {
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isValidName(String name) {
        return name != null && NAME_PATTERN.matcher(name.trim()).matches();
    }

    public static boolean isValidPhoneNumber(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone.trim()).matches();
    }

    public static boolean isValidUsername(String username) {
        return username != null && USERNAME_PATTERN.matcher(username.trim()).matches();
    }

    public static boolean isValidAddress(String address) {
        return address != null && address.trim().length() >= 5 && address.trim().length() <= 255;
    }

    /**
     * Checks the date string is a real calendar date and not in the past.
     */
    public static boolean isValidFutureDate(String dateStr) {
        try {
            LocalDate date = LocalDate.parse(dateStr);
            return !date.isBefore(LocalDate.now());
        } catch (DateTimeParseException | NullPointerException e) {
            return false;
        }
    }

    /**
     * Checks the time string is a real time and falls within clinic hours.
     */
    public static boolean isWithinClinicHours(String timeStr) {
        try {
            LocalTime time = LocalTime.parse(timeStr);
            return !time.isBefore(CLINIC_OPEN) && !time.isAfter(CLINIC_CLOSE);
        } catch (DateTimeParseException | NullPointerException e) {
            return false;
        }
    }

    public static boolean isNotPastDateTime(String dateStr, String timeStr) {
        try {
            LocalDate date = LocalDate.parse(dateStr);
            LocalTime time = LocalTime.parse(timeStr);
            if (date.isEqual(LocalDate.now())) {
                return !time.isBefore(LocalTime.now());
            }
            return true; // any future date, time doesn't matter
        } catch (DateTimeParseException | NullPointerException e) {
            return false;
        }
    }

    /**
     * Strips characters that have no business being in plain text fields,
     * as a defense-in-depth measure against stored XSS. This does NOT
     * replace PreparedStatement (which already prevents SQL injection),
     * it only protects what gets displayed back in JSP pages later.
     */
    public static String sanitize(String input) {
        if (input == null) {
            return null;
        }
        return input.trim()
                .replace("<", "")
                .replace(">", "")
                .replace("\"", "")
                .replace("'", "");
    }

    public static String getClinicOpenTime() {
        return CLINIC_OPEN.toString();
    }

    public static String getClinicCloseTime() {
        return CLINIC_CLOSE.toString();
    }
}
