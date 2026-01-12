package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * The type Validation utils.
 */
public class ValidationUtils {

    private ValidationUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Validate not empty.
     *
     * @param value     the value
     * @param fieldName the field name
     */
    public static void validateNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        }
    }

    /**
     * Validate positive.
     *
     * @param value     the value
     * @param fieldName the field name
     */
    public static void validatePositive(double value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " must be positive");
        }
    }

    /**
     * Validate positive.
     *
     * @param value     the value
     * @param fieldName the field name
     */
    public static void validatePositive(int value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " must be positive");
        }
    }

    /**
     * Parse date local date.
     *
     * @param dateStr the date str
     * @param pattern the pattern
     * @return the local date
     */
    public static LocalDate parseDate(String dateStr, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException _) {
            throw new IllegalArgumentException("Invalid date format. Expected: " + pattern);
        }
    }

    /**
     * Parse date time local date time.
     *
     * @param dateTimeStr the date time str
     * @param pattern     the pattern
     * @return the local date time
     */
    public static LocalDateTime parseDateTime(String dateTimeStr, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return LocalDateTime.parse(dateTimeStr, formatter);
        } catch (DateTimeParseException _) {
            throw new IllegalArgumentException("Invalid date-time format. Expected: " + pattern);
        }
    }

    /**
     * Parse int int.
     *
     * @param str       the str
     * @param fieldName the field name
     * @return the int
     */
    public static int parseInt(String str, String fieldName) {
        try {
            return Integer.parseInt(str.trim());
        } catch (NumberFormatException _) {
            throw new IllegalArgumentException(fieldName + " must be a valid integer");
        }
    }

    /**
     * Parse double double.
     *
     * @param str       the str
     * @param fieldName the field name
     * @return the double
     */
    public static double parseDouble(String str, String fieldName) {
        try {
            return Double.parseDouble(str.trim());
        } catch (NumberFormatException _) {
            throw new IllegalArgumentException(fieldName + " must be a valid number");
        }
    }
}

