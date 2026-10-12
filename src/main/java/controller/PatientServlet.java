package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(urlPatterns = {"/patient/dashboard", "/patient/profile",
        "/patient/appointments", "/patient/history"})
public class PatientServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getServletPath();
        switch (path) {
            case "/patient/dashboard"    -> req.getRequestDispatcher("/patient/dashboard.jsp").forward(req, resp);
            case "/patient/profile"      -> req.getRequestDispatcher("/patient/profile.jsp").forward(req, resp);
            case "/patient/appointments" -> req.getRequestDispatcher("/patient/my-appointments.jsp").forward(req, resp);
            case "/patient/history"      -> req.getRequestDispatcher("/patient/history.jsp").forward(req, resp);
            default -> resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}