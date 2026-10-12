package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.MedicalNote;
import service.MedicalNoteService;
import util.SessionUtil;

import java.io.IOException;

@WebServlet(urlPatterns = {"/medical-note/create", "/medical-note/validate",
        "/medical-note/update", "/medical-note/list"})
public class MedicalNoteServlet extends HttpServlet {

    private final MedicalNoteService service = new MedicalNoteService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long doctorId = SessionUtil.getUserId(req);
        req.setAttribute("notes", service.findByDoctor(doctorId));
        req.getRequestDispatcher("/doctor/medical-notes.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getServletPath();
        Long doctorId = SessionUtil.getUserId(req);

        try {
            switch (path) {
                case "/medical-note/create" -> {
                    Long apptId = Long.parseLong(req.getParameter("appointmentId"));
                    service.create(apptId, doctorId,
                            req.getParameter("diagnostic"),
                            req.getParameter("contenu"));
                    req.getSession().setAttribute("success", "Note créée.");
                }
                case "/medical-note/validate" -> {
                    service.validate(Long.parseLong(req.getParameter("id")));
                    req.getSession().setAttribute("success", "Note validée (READ-ONLY).");
                }
                case "/medical-note/update" -> {
                    service.update(Long.parseLong(req.getParameter("id")),
                            req.getParameter("diagnostic"),
                            req.getParameter("contenu"));
                    req.getSession().setAttribute("success", "Note mise à jour.");
                }
            }
        } catch (Exception e) {
            req.getSession().setAttribute("error", e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/medical-note/list");
    }
}