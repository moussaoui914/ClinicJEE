package controller;

import mapper.SpecialtyMapper;
import mapper.DepartmentMapper;
import exception.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import mapper.PatientMapper;
import mapper.DoctorMapper;
import model.*;
import model.enums.BloodGroup;
import model.enums.Gender;
import model.enums.Role;
import repository.UserRepository;
import service.*;
import util.SessionUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(urlPatterns = {
        "/admin/dashboard",
        "/admin/patients", "/admin/patients/create", "/admin/patients/update", "/admin/patients/delete",
        "/admin/doctors",  "/admin/doctors/create",  "/admin/doctors/update",  "/admin/doctors/delete",
        "/admin/departments", "/admin/departments/create", "/admin/departments/update", "/admin/departments/delete",
        "/admin/specialties", "/admin/specialties/create", "/admin/specialties/update", "/admin/specialties/delete",
        "/admin/users", "/admin/users/delete"
})
public class AdminServlet extends HttpServlet {

    private final StatisticService statisticService = new StatisticService();
    private final PatientService patientService = new PatientService();
    private final DoctorService doctorService = new DoctorService();
    private final DepartmentService departmentService = new DepartmentService();
    private final SpecialtyService specialtyService = new SpecialtyService();
    private final AuthService authService = new AuthService();
    private final UserRepository userRepo = new UserRepository();

    // ============================================================
    // GET
    // ============================================================
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getServletPath();

        switch (path) {

            case "/admin/dashboard" -> {
                req.setAttribute("stats", statisticService.getAdminDashboardStats());
                forward(req, resp, "/admin/dashboard.jsp");
            }

            case "/admin/patients" -> {
                List<Patient> patients = patientService.findAll();
                req.setAttribute("patients", PatientMapper.toDTOList(patients));
                forward(req, resp, "/admin/patients.jsp");
            }

            case "/admin/doctors" -> {
                List<Doctor> doctors = doctorService.findAll();
                req.setAttribute("doctors", DoctorMapper.toDTOList(doctors));
                req.setAttribute("specialties", SpecialtyMapper.toDTOList(specialtyService.findAll()));
                req.setAttribute("departments", DepartmentMapper.toDTOList(departmentService.findAll()));
                forward(req, resp, "/admin/doctors.jsp");
            }

            case "/admin/departments" -> {
                req.setAttribute("departments", DepartmentMapper.toDTOList(departmentService.findAll()));
                forward(req, resp, "/admin/departments.jsp");
            }

            case "/admin/specialties" -> {
                req.setAttribute("specialties", SpecialtyMapper.toDTOList(specialtyService.findAll()));
                req.setAttribute("departments", DepartmentMapper.toDTOList(departmentService.findAll()));
                forward(req, resp, "/admin/specialties.jsp");
            }

            case "/admin/users" -> {
                req.setAttribute("users", userRepo.findAll());
                forward(req, resp, "/admin/users.jsp");
            }

            default -> resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // ============================================================
    // POST
    // ============================================================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getServletPath();

        try {
            switch (path) {

                // ---------- PATIENTS ----------
                case "/admin/patients/delete" -> {
                    Long id = Long.parseLong(req.getParameter("id"));
                    patientService.delete(id);
                    req.getSession().setAttribute("success", "Patient supprimé.");
                    resp.sendRedirect(req.getContextPath() + "/admin/patients");
                }
                case "/admin/patients/update" -> {
                    Long id = Long.parseLong(req.getParameter("id"));
                    Patient p = patientService.findById(id);
                    p.setNom(req.getParameter("nom"));
                    p.setPrenom(req.getParameter("prenom"));
                    p.setTelephone(req.getParameter("telephone"));
                    p.setAdresse(req.getParameter("adresse"));
                    String cin = req.getParameter("cin");
                    if (cin != null) p.setCin(cin);
                    String genreStr = req.getParameter("genre");
                    if (genreStr != null && !genreStr.isBlank())
                        p.setGenre(Gender.valueOf(genreStr));
                    String bgStr = req.getParameter("groupeSanguin");
                    if (bgStr != null && !bgStr.isBlank())
                        p.setGroupeSanguin(BloodGroup.valueOf(bgStr));
                    String dnStr = req.getParameter("dateNaissance");
                    if (dnStr != null && !dnStr.isBlank())
                        p.setDateNaissance(LocalDate.parse(dnStr));
                    patientService.update(p);
                    req.getSession().setAttribute("success", "Patient mis à jour.");
                    resp.sendRedirect(req.getContextPath() + "/admin/patients");
                }

                // ---------- DOCTORS ----------
                case "/admin/doctors/create" -> {
                    String nom = req.getParameter("nom");
                    String prenom = req.getParameter("prenom");
                    String email = req.getParameter("email");
                    String telephone = req.getParameter("telephone");
                    String motDePasse = req.getParameter("motDePasse");
                    String matricule = req.getParameter("matricule");
                    String titre = req.getParameter("titre");
                    Long specialtyId = Long.parseLong(req.getParameter("specialtyId"));
                    Long departmentId = Long.parseLong(req.getParameter("departmentId"));

                    User user = authService.registerUser(nom, prenom, email, telephone, motDePasse, Role.DOCTOR);

                    Doctor doctor = new Doctor();
                    doctor.setId(user.getId());
                    doctor.setNom(nom);
                    doctor.setPrenom(prenom);
                    doctor.setEmail(email);
                    doctor.setTelephone(telephone);
                    doctor.setMotDePasse(user.getMotDePasse());
                    doctor.setRole(Role.DOCTOR);
                    doctor.setActif(true);
                    doctor.setMatricule(matricule);
                    doctor.setTitre(titre);
                    doctor.setSpecialty(specialtyService.findById(specialtyId));
                    doctor.setDepartment(departmentService.findById(departmentId));
                    doctorService.create(doctor);

                    req.getSession().setAttribute("success", "Médecin créé avec succès.");
                    resp.sendRedirect(req.getContextPath() + "/admin/doctors");
                }
                case "/admin/doctors/delete" -> {
                    Long id = Long.parseLong(req.getParameter("id"));
                    doctorService.delete(id);
                    req.getSession().setAttribute("success", "Médecin supprimé.");
                    resp.sendRedirect(req.getContextPath() + "/admin/doctors");
                }
                case "/admin/doctors/update" -> {
                    Long id = Long.parseLong(req.getParameter("id"));
                    Doctor d = doctorService.findById(id);
                    d.setNom(req.getParameter("nom"));
                    d.setPrenom(req.getParameter("prenom"));
                    d.setTelephone(req.getParameter("telephone"));
                    d.setTitre(req.getParameter("titre"));
                    String specId = req.getParameter("specialtyId");
                    if (specId != null && !specId.isBlank())
                        d.setSpecialty(specialtyService.findById(Long.parseLong(specId)));
                    String deptId = req.getParameter("departmentId");
                    if (deptId != null && !deptId.isBlank())
                        d.setDepartment(departmentService.findById(Long.parseLong(deptId)));
                    doctorService.update(d);
                    req.getSession().setAttribute("success", "Médecin mis à jour.");
                    resp.sendRedirect(req.getContextPath() + "/admin/doctors");
                }

                // ---------- DEPARTMENTS ----------
                case "/admin/departments/create" -> {
                    Department d = new Department();
                    d.setNom(req.getParameter("nom"));
                    d.setDescription(req.getParameter("description"));
                    departmentService.save(d);
                    req.getSession().setAttribute("success", "Département créé.");
                    resp.sendRedirect(req.getContextPath() + "/admin/departments");
                }
                case "/admin/departments/update" -> {
                    Long id = Long.parseLong(req.getParameter("id"));
                    Department d = departmentService.findById(id);
                    d.setNom(req.getParameter("nom"));
                    d.setDescription(req.getParameter("description"));
                    departmentService.save(d);
                    req.getSession().setAttribute("success", "Département mis à jour.");
                    resp.sendRedirect(req.getContextPath() + "/admin/departments");
                }
                case "/admin/departments/delete" -> {
                    Long id = Long.parseLong(req.getParameter("id"));
                    departmentService.delete(id);
                    req.getSession().setAttribute("success", "Département supprimé.");
                    resp.sendRedirect(req.getContextPath() + "/admin/departments");
                }

                // ---------- SPECIALTIES ----------
                case "/admin/specialties/create" -> {
                    Specialty s = new Specialty();
                    s.setNom(req.getParameter("nom"));
                    s.setDescription(req.getParameter("description"));
                    Long deptId = Long.parseLong(req.getParameter("departmentId"));
                    s.setDepartment(departmentService.findById(deptId));
                    specialtyService.save(s);
                    req.getSession().setAttribute("success", "Spécialité créée.");
                    resp.sendRedirect(req.getContextPath() + "/admin/specialties");
                }
                case "/admin/specialties/update" -> {
                    Long id = Long.parseLong(req.getParameter("id"));
                    Specialty s = specialtyService.findById(id);
                    s.setNom(req.getParameter("nom"));
                    s.setDescription(req.getParameter("description"));
                    String deptId = req.getParameter("departmentId");
                    if (deptId != null && !deptId.isBlank())
                        s.setDepartment(departmentService.findById(Long.parseLong(deptId)));
                    specialtyService.save(s);
                    req.getSession().setAttribute("success", "Spécialité mise à jour.");
                    resp.sendRedirect(req.getContextPath() + "/admin/specialties");
                }
                case "/admin/specialties/delete" -> {
                    Long id = Long.parseLong(req.getParameter("id"));
                    specialtyService.delete(id);
                    req.getSession().setAttribute("success", "Spécialité supprimée.");
                    resp.sendRedirect(req.getContextPath() + "/admin/specialties");
                }

                // ---------- USERS ----------
                case "/admin/users/delete" -> {
                    Long id = Long.parseLong(req.getParameter("id"));
                    authService.deactivate(id);
                    req.getSession().setAttribute("success", "Utilisateur désactivé.");
                    resp.sendRedirect(req.getContextPath() + "/admin/users");
                }

                default -> resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.getSession().setAttribute("error", e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
        }
    }

    private void forward(HttpServletRequest req, HttpServletResponse resp, String jsp)
            throws ServletException, IOException {
        req.getRequestDispatcher(jsp).forward(req, resp);
    }
}