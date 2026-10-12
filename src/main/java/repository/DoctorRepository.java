package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.Doctor;
import util.JPAUtil;

import java.util.List;
import java.util.Optional;

public class DoctorRepository {

    public Doctor save(Doctor d) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (d.getId() == null) em.persist(d);
            else d = em.merge(d);
            em.getTransaction().commit();
            return d;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    public Optional<Doctor> findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try { return Optional.ofNullable(em.find(Doctor.class, id)); }
        finally { em.close(); }
    }

    public Optional<Doctor> findByMatricule(String matricule) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Doctor d = em.createQuery(
                            "SELECT d FROM Doctor d WHERE d.matricule = :m", Doctor.class)
                    .setParameter("m", matricule).getSingleResult();
            return Optional.of(d);
        } catch (NoResultException e) { return Optional.empty(); }
        finally { em.close(); }
    }

    public List<Doctor> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT d FROM Doctor d " +
                                    "LEFT JOIN FETCH d.specialty " +
                                    "LEFT JOIN FETCH d.department " +
                                    "ORDER BY d.nom", Doctor.class)
                    .getResultList();
        } finally { em.close(); }
    }
    public List<Doctor> findBySpecialty(Long specialtyId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT d FROM Doctor d " +
                                    "LEFT JOIN FETCH d.specialty " +
                                    "LEFT JOIN FETCH d.department " +
                                    "WHERE d.specialty.id = :sid", Doctor.class)
                    .setParameter("sid", specialtyId).getResultList();
        } finally { em.close(); }
    }

    public Doctor update(Doctor d) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Doctor merged = em.merge(d);
            em.getTransaction().commit();
            return merged;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Doctor d = em.find(Doctor.class, id);
            if (d != null) em.remove(d);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }
}