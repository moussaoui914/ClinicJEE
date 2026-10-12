package controller;

import dto.UserDTO;
import exception.UnauthorizedActionException;
import exception.UserNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import mapper.UserMapper;
import model.User;
import service.AuthService;
import util.SessionUtil;

import java.io.IOException;

@WebServlet(urlPatterns = {"/profile", "/profile/update", "/profile/password"})
public class ProfileServlet extends HttpServlet {

    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Long userId = SessionUtil.getUserId(req);
        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login.jsp");
            return;
        }

        try {
            User user = authService.getProfile(userId);
            UserDTO dto = UserMapper.toDTO(user);
            req.setAttribute("user", dto);
            req.getRequestDispatcher("/patient/profile.jsp").forward(req, resp);
        } catch (UserNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getServletPath();
        Long userId = SessionUtil.getUserId(req);

        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login.jsp");
            return;
        }

        switch (path) {
            case "/profile/update"   -> handleUpdate(req, resp, userId);
            case "/profile/password" -> handleChangePassword(req, resp, userId);
            default -> resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleUpdate(HttpServletRequest req, HttpServletResponse resp, Long userId)
            throws ServletException, IOException {
        try {
            User updated = authService.updateProfile(userId,
                    req.getParameter("nom"),
                    req.getParameter("prenom"),
                    req.getParameter("telephone"));
            req.getSession().setAttribute("userName", updated.getNomComplet());
            req.getSession().setAttribute("success", "Profil mis à jour.");
            resp.sendRedirect(req.getContextPath() + "/profile");
        } catch (IllegalArgumentException | UserNotFoundException e) {
            req.setAttribute("error", e.getMessage());
            doGet(req, resp);
        }
    }

    private void handleChangePassword(HttpServletRequest req, HttpServletResponse resp, Long userId)
            throws IOException {
        String ancien = req.getParameter("ancienMotDePasse");
        String nouveau = req.getParameter("nouveauMotDePasse");
        String confirmation = req.getParameter("confirmationMotDePasse");

        if (!nouveau.equals(confirmation)) {
            req.getSession().setAttribute("error", "Les mots de passe ne correspondent pas.");
            resp.sendRedirect(req.getContextPath() + "/profile");
            return;
        }

        try {
            authService.changePassword(userId, ancien, nouveau);
            req.getSession().setAttribute("success", "Mot de passe changé.");
        } catch (UnauthorizedActionException | IllegalArgumentException e) {
            req.getSession().setAttribute("error", e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/profile");
    }
}