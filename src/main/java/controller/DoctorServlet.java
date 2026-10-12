package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(urlPatterns = {"/doctor/dashboard", "/doctor/agenda",
        "/doctor/availabilities", "/doctor/appointments",
        "/doctor/medical-notes"})
public class DoctorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getServletPath();
        switch (path) {
            case "/doctor/dashboard"      -> req.getRequestDispatcher("/doctor/dashboard.jsp").forward(req, resp);
            case "/doctor/agenda"         -> req.getRequestDispatcher("/doctor/agenda.jsp").forward(req, resp);
            case "/doctor/availabilities" -> req.getRequestDispatcher("/doctor/availabilities.jsp").forward(req, resp);
            case "/doctor/appointments"   -> req.getRequestDispatcher("/doctor/appointments.jsp").forward(req, resp);
            case "/doctor/medical-notes"  -> req.getRequestDispatcher("/doctor/medical-notes.jsp").forward(req, resp);
            default -> resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}