package repository;

import jakarta.persistence.EntityManager;
import model.Appointment;
import model.enums.AppointmentStatus;
import util.JPAUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class AppointmentRepository {

    public Appointment save(Appointment a) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (a.getId() == null) em.persist(a);
            else a = em.merge(a);
            em.getTransaction().commit();
            return a;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    public Optional<Appointment> findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try { return Optional.ofNullable(em.find(Appointment.class, id)); }
        finally { em.close(); }
    }

    public List<Appointment> findByPatient(Long patientId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT a FROM Appointment a WHERE a.patient.id = :p ORDER BY a.dateRdv DESC",
                            Appointment.class)
                    .setParameter("p", patientId).getResultList();
        } finally { em.close(); }
    }

    public List<Appointment> findByDoctor(Long doctorId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT a FROM Appointment a WHERE a.doctor.id = :d ORDER BY a.dateRdv",
                            Appointment.class)
                    .setParameter("d", doctorId).getResultList();
        } finally { em.close(); }
    }

    public List<Appointment> findByDoctorAndDateRange(Long doctorId,
                                                      LocalDateTime start,
                                                      LocalDateTime end) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT a FROM Appointment a WHERE a.doctor.id = :d " +
                                    "AND a.dateRdv >= :s AND a.dateRdv < :e " +
                                    "AND a.statut <> :c ORDER BY a.dateRdv", Appointment.class)
                    .setParameter("d", doctorId)
                    .setParameter("s", start)
                    .setParameter("e", end)
                    .setParameter("c", AppointmentStatus.CANCELED)
                    .getResultList();
        } finally { em.close(); }
    }

    public List<Appointment> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT a FROM Appointment a ORDER BY a.dateRdv DESC", Appointment.class)
                    .getResultList();
        } finally { em.close(); }
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Appointment a = em.find(Appointment.class, id);
            if (a != null) em.remove(a);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }
}