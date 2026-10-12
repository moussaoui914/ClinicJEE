package controller;

import dto.AppointmentDTO;
import exception.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import mapper.AppointmentMapper;
import model.Appointment;
import service.AppointmentService;
import util.SessionUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(urlPatterns = {"/appointment/create", "/appointment/cancel",
        "/appointment/reschedule", "/appointment/list"})
public class AppointmentServlet extends HttpServlet {

    private final AppointmentService appointmentService = new AppointmentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Long userId = SessionUtil.getUserId(req);
        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login.jsp");
            return;
        }

        List<Appointment> list = appointmentService.findByPatient(userId);
        List<AppointmentDTO> dtos = AppointmentMapper.toDTOList(list);

        req.setAttribute("appointments", dtos);
        req.getRequestDispatcher("/patient/my-appointments.jsp").forward(req, resp);
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
            case "/appointment/create"     -> handleCreate(req, resp, userId);
            case "/appointment/cancel"     -> handleCancel(req, resp, userId);
            case "/appointment/reschedule" -> handleReschedule(req, resp, userId);
            default -> resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleCreate(HttpServletRequest req, HttpServletResponse resp, Long userId)
            throws IOException {
        try {
            Long doctorId = Long.parseLong(req.getParameter("doctorId"));
            LocalDateTime date = LocalDateTime.parse(req.getParameter("dateRdv"));
            String motif = req.getParameter("motif");
            String type = req.getParameter("type");

            appointmentService.create(userId, doctorId, date, motif, type);
            req.getSession().setAttribute("success", "Rendez-vous créé !");
        } catch (AppointmentConflictException | UnavailableSlotException
                 | PatientNotFoundException | DoctorNotFoundException e) {
            req.getSession().setAttribute("error", e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/appointment/list");
    }

    private void handleCancel(HttpServletRequest req, HttpServletResponse resp, Long userId)
            throws IOException {
        try {
            Long rdvId = Long.parseLong(req.getParameter("id"));
            appointmentService.cancel(rdvId, userId);
            req.getSession().setAttribute("success", "RDV annulé.");
        } catch (UnauthorizedActionException | AppointmentNotFoundException e) {
            req.getSession().setAttribute("error", e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/appointment/list");
    }

    private void handleReschedule(HttpServletRequest req, HttpServletResponse resp, Long userId)
            throws IOException {
        try {
            Long rdvId = Long.parseLong(req.getParameter("id"));
            LocalDateTime newDate = LocalDateTime.parse(req.getParameter("newDate"));
            appointmentService.reschedule(rdvId, newDate);
            req.getSession().setAttribute("success", "RDV replanifié.");
        } catch (Exception e) {
            req.getSession().setAttribute("error", e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/appointment/list");
    }
}