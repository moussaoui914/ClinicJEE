package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.enums.Role;
import util.SessionUtil;

import java.io.IOException;

@WebFilter(urlPatterns = {"/admin/*", "/doctor/*", "/patient/*", "/staff/*"})
public class RoleFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String path = request.getRequestURI().substring(request.getContextPath().length());
        String ctx = request.getContextPath();
        Role role = SessionUtil.getRole(request);

        if (role == null) {
            response.sendRedirect(ctx + "/auth/login.jsp");
            return;
        }

        boolean authorized = false;
        if (path.startsWith("/admin/"))   authorized = (role == Role.ADMIN);
        if (path.startsWith("/doctor/"))  authorized = (role == Role.DOCTOR);
        if (path.startsWith("/patient/")) authorized = (role == Role.PATIENT);
        if (path.startsWith("/staff/"))   authorized = (role == Role.STAFF);

        if (!authorized) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Accès refusé pour le rôle : " + role);
            return;
        }

        chain.doFilter(req, res);
    }
}