package repository;

import jakarta.persistence.EntityManager;
import model.enums.AppointmentStatus;
import util.JPAUtil;

import java.time.LocalDateTime;

public class StatisticRepository {

    public long countPatients() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(p) FROM Patient p", Long.class)
                    .getSingleResult();
        } finally { em.close(); }
    }

    public long countDoctors() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(d) FROM Doctor d", Long.class)
                    .getSingleResult();
        } finally { em.close(); }
    }

    public long countAppointments() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(a) FROM Appointment a", Long.class)
                    .getSingleResult();
        } finally { em.close(); }
    }

    public long countAppointmentsByStatus(AppointmentStatus status) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT COUNT(a) FROM Appointment a WHERE a.statut = :s", Long.class)
                    .setParameter("s", status).getSingleResult();
        } finally { em.close(); }
    }

    public long countDepartments() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(d) FROM Department d", Long.class)
                    .getSingleResult();
        } finally { em.close(); }
    }

    public long countSpecialties() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(s) FROM Specialty s", Long.class)
                    .getSingleResult();
        } finally { em.close(); }
    }

    public long countTodayAppointments() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            LocalDateTime debut = LocalDateTime.now().toLocalDate().atStartOfDay();
            LocalDateTime fin = debut.plusDays(1);
            return em.createQuery(
                            "SELECT COUNT(a) FROM Appointment a " +
                                    "WHERE a.dateRdv >= :s AND a.dateRdv < :e", Long.class)
                    .setParameter("s", debut)
                    .setParameter("e", fin)
                    .getSingleResult();
        } finally { em.close(); }
    }
}