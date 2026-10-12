package util;

public final class ValidationUtil {

    private ValidationUtil() {}

    public static boolean isBlank(String s) {
        return s == null || s.isBlank();
    }

    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    public static void requireNonBlank(String value, String fieldName) {
        if (isBlank(value)) throw new IllegalArgumentException(fieldName + " obligatoire.");
    }
}