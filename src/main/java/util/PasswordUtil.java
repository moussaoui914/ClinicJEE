package util;

import org.mindrot.jbcrypt.BCrypt;

public final class PasswordUtil {

    private PasswordUtil() {}

    public static String hash(String plain) {
        if (plain == null || plain.isBlank())
            throw new IllegalArgumentException("Mot de passe vide.");
        return BCrypt.hashpw(plain, BCrypt.gensalt(12));
    }

    public static boolean check(String plain, String hashed) {
        if (plain == null || hashed == null) return false;
        try {
            return BCrypt.checkpw(plain, hashed);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}