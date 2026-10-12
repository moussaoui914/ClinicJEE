package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Availability;
import model.Doctor;
import model.enums.AvailabilityStatus;
import model.enums.DayOfWeek;
import service.AvailabilityService;
import service.DoctorService;
import util.SessionUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

@WebServlet(urlPatterns = {"/availability/add", "/availability/delete", "/availability/list"})
public class AvailabilityServlet extends HttpServlet {

    private final AvailabilityService availabilityService = new AvailabilityService();
    private final DoctorService doctorService = new DoctorService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Long doctorId = SessionUtil.getUserId(req);
        req.setAttribute("availabilities",
                availabilityService.findByDoctor(doctorId));
        req.getRequestDispatcher("/doctor/availabilities.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getServletPath();
        Long doctorId = SessionUtil.getUserId(req);

        if ("/availability/add".equals(path)) {
            try {
                Doctor doctor = doctorService.findById(doctorId);
                Availability a = new Availability();
                a.setDoctor(doctor);
                a.setJour(DayOfWeek.valueOf(req.getParameter("jour")));
                a.setHeureDebut(LocalTime.parse(req.getParameter("heureDebut")));
                a.setHeureFin(LocalTime.parse(req.getParameter("heureFin")));
                a.setStatut(AvailabilityStatus.ACTIVE);
                String dd = req.getParameter("dateDebut");
                String df = req.getParameter("dateFin");
                if (dd != null && !dd.isBlank()) a.setDateDebut(LocalDate.parse(dd));
                if (df != null && !df.isBlank()) a.setDateFin(LocalDate.parse(df));

                availabilityService.save(a);
                req.getSession().setAttribute("success", "Disponibilité ajoutée.");
            } catch (Exception e) {
                req.getSession().setAttribute("error", e.getMessage());
            }
        } else if ("/availability/delete".equals(path)) {
            try {
                availabilityService.delete(Long.parseLong(req.getParameter("id")));
                req.getSession().setAttribute("success", "Disponibilité supprimée.");
            } catch (Exception e) {
                req.getSession().setAttribute("error", e.getMessage());
            }
        }
        resp.sendRedirect(req.getContextPath() + "/availability/list");
    }
}