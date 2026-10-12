package repository;

import jakarta.persistence.EntityManager;
import model.MedicalNote;
import util.JPAUtil;

import java.util.List;
import java.util.Optional;

public class MedicalNoteRepository {

    public MedicalNote save(MedicalNote n) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (n.getId() == null) em.persist(n);
            else n = em.merge(n);
            em.getTransaction().commit();
            return n;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    public Optional<MedicalNote> findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try { return Optional.ofNullable(em.find(MedicalNote.class, id)); }
        finally { em.close(); }
    }

    public Optional<MedicalNote> findByAppointment(Long appointmentId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            List<MedicalNote> list = em.createQuery(
                            "SELECT n FROM MedicalNote n WHERE n.appointment.id = :a",
                            MedicalNote.class)
                    .setParameter("a", appointmentId).getResultList();
            return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
        } finally { em.close(); }
    }

    public List<MedicalNote> findByDoctor(Long doctorId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT n FROM MedicalNote n WHERE n.doctor.id = :d " +
                                    "ORDER BY n.dateNote DESC", MedicalNote.class)
                    .setParameter("d", doctorId).getResultList();
        } finally { em.close(); }
    }

    public List<MedicalNote> findByPatient(Long patientId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT n FROM MedicalNote n " +
                                    "WHERE n.appointment.patient.id = :p " +
                                    "ORDER BY n.dateNote DESC", MedicalNote.class)
                    .setParameter("p", patientId).getResultList();
        } finally { em.close(); }
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            MedicalNote n = em.find(MedicalNote.class, id);
            if (n != null) em.remove(n);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }
}