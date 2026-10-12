package util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.enums.Role;

public final class SessionUtil {

    public static final String ATTR_USER_ID = "userId";
    public static final String ATTR_USER_NAME = "userName";
    public static final String ATTR_USER_EMAIL = "userEmail";
    public static final String ATTR_ROLE = "role";

    private SessionUtil() {}

    public static void setUserSession(HttpSession session, Long id,
                                      String nomComplet, String email, Role role) {
        session.setAttribute(ATTR_USER_ID, id);
        session.setAttribute(ATTR_USER_NAME, nomComplet);
        session.setAttribute(ATTR_USER_EMAIL, email);
        session.setAttribute(ATTR_ROLE, role.name());
    }

    public static Long getUserId(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        return s == null ? null : (Long) s.getAttribute(ATTR_USER_ID);
    }

    public static String getUserName(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        return s == null ? null : (String) s.getAttribute(ATTR_USER_NAME);
    }

    public static Role getRole(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        if (s == null) return null;
        String role = (String) s.getAttribute(ATTR_ROLE);
        return role == null ? null : Role.valueOf(role);
    }

    public static boolean isLoggedIn(HttpServletRequest req) {
        return getUserId(req) != null;
    }

    public static void invalidate(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        if (s != null) s.invalidate();
    }
}