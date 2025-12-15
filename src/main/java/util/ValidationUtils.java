package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for input validation
 */
public class ValidationUtils {

    private ValidationUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Validate that string is not empty
     * @param value string to validate
     * @param fieldName field name for error message
     * @throws IllegalArgumentException if validation fails
     */
    public static void validateNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        }
    }

    /**
     * Validate that number is positive
     * @param value number to validate
     * @param fieldName field name for error message
     * @throws IllegalArgumentException if validation fails
     */
    public static void validatePositive(double value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " must be positive");
        }
    }

    /**
     * Validate that number is positive
     * @param value number to validate
     * @param fieldName field name for error message
     * @throws IllegalArgumentException if validation fails
     */
    public static void validatePositive(int value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " must be positive");
        }
    }

    /**
     * Parse date from string
     * @param dateStr date string
     * @param pattern date pattern
     * @return parsed LocalDate
     * @throws IllegalArgumentException if parsing fails
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
     * Parse date-time from string
     * @param dateTimeStr date-time string
     * @param pattern date-time pattern
     * @return parsed LocalDateTime
     * @throws IllegalArgumentException if parsing fails
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
     * Parse integer from string
     * @param str string to parse
     * @param fieldName field name for error message
     * @return parsed integer
     * @throws IllegalArgumentException if parsing fails
     */
    public static int parseInt(String str, String fieldName) {
        try {
            return Integer.parseInt(str.trim());
        } catch (NumberFormatException _) {
            throw new IllegalArgumentException(fieldName + " must be a valid integer");
        }
    }

    /**
     * Parse double from string
     * @param str string to parse
     * @param fieldName field name for error message
     * @return parsed double
     * @throws IllegalArgumentException if parsing fails
     */
    public static double parseDouble(String str, String fieldName) {
        try {
            return Double.parseDouble(str.trim());
        } catch (NumberFormatException _) {
            throw new IllegalArgumentException(fieldName + " must be a valid number");
        }
    }
}

