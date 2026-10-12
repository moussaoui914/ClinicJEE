package controller;

import dto.LoginDTO;
import dto.RegisterDTO;
import exception.DuplicateEmailException;
import exception.UnauthorizedActionException;
import exception.UserNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.User;
import service.AuthService;
import util.SessionUtil;

import java.io.IOException;

@WebServlet(urlPatterns = {"/auth/login", "/auth/register", "/auth/logout"})
public class AuthServlet extends HttpServlet {

    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/auth/login"    -> req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
            case "/auth/register" -> req.getRequestDispatcher("/auth/register.jsp").forward(req, resp);
            case "/auth/logout"   -> handleLogout(req, resp);
            default -> resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/auth/login"    -> handleLogin(req, resp);
            case "/auth/register" -> handleRegister(req, resp);
            default -> resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        LoginDTO dto = new LoginDTO();
        dto.setEmail(req.getParameter("email"));
        dto.setMotDePasse(req.getParameter("motDePasse"));

        if (!dto.isValid()) {
            req.setAttribute("error", "Email et mot de passe obligatoires.");
            req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
            return;
        }

        try {
            User user = authService.login(dto.getEmail(), dto.getMotDePasse());
            HttpSession session = req.getSession(true);
            SessionUtil.setUserSession(session, user.getId(),
                    user.getNomComplet(), user.getEmail(), user.getRole());
            redirectByRole(req, resp, user);
        } catch (UserNotFoundException | UnauthorizedActionException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("email", dto.getEmail());
            req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
        }
    }

    private void handleRegister(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        RegisterDTO dto = new RegisterDTO();
        dto.setNom(req.getParameter("nom"));
        dto.setPrenom(req.getParameter("prenom"));
        dto.setEmail(req.getParameter("email"));
        dto.setTelephone(req.getParameter("telephone"));
        dto.setMotDePasse(req.getParameter("motDePasse"));
        dto.setConfirmationMotDePasse(req.getParameter("confirmationMotDePasse"));

        if (!dto.passwordsMatch()) {
            req.setAttribute("error", "Les mots de passe ne correspondent pas.");
            preserveFormData(req, dto);
            req.getRequestDispatcher("/auth/register.jsp").forward(req, resp);
            return;
        }

        try {
            authService.register(dto.getNom(), dto.getPrenom(), dto.getEmail(),
                    dto.getTelephone(), dto.getMotDePasse());
            req.getSession().setAttribute("success",
                    "Inscription réussie ! Vous pouvez vous connecter.");
            resp.sendRedirect(req.getContextPath() + "/auth/login.jsp");
        } catch (DuplicateEmailException | IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            preserveFormData(req, dto);
            req.getRequestDispatcher("/auth/register.jsp").forward(req, resp);
        }
    }

    private void handleLogout(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        SessionUtil.invalidate(req);
        resp.sendRedirect(req.getContextPath() + "/auth/login.jsp?logout=true");
    }

    private void redirectByRole(HttpServletRequest req, HttpServletResponse resp,
                                User user) throws IOException {
        String ctx = req.getContextPath();
        switch (user.getRole()) {
            case ADMIN   -> resp.sendRedirect(ctx + "/admin/dashboard");
            case DOCTOR  -> resp.sendRedirect(ctx + "/doctor/dashboard");
            case STAFF   -> resp.sendRedirect(ctx + "/staff/dashboard");
            case PATIENT -> resp.sendRedirect(ctx + "/patient/dashboard");
        }
    }

    private void preserveFormData(HttpServletRequest req, RegisterDTO dto) {
        req.setAttribute("nom", dto.getNom());
        req.setAttribute("prenom", dto.getPrenom());
        req.setAttribute("email", dto.getEmail());
        req.setAttribute("telephone", dto.getTelephone());
    }
}