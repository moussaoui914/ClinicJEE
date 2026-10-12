package repository;

import jakarta.persistence.EntityManager;
import model.Patient;
import util.JPAUtil;

import java.util.List;
import java.util.Optional;

public class PatientRepository {

    public Patient save(Patient p) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (p.getId() == null) em.persist(p);
            else p = em.merge(p);
            em.getTransaction().commit();
            return p;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    public Optional<Patient> findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try { return Optional.ofNullable(em.find(Patient.class, id)); }
        finally { em.close(); }
    }

    public List<Patient> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Patient p ORDER BY p.nom", Patient.class)
                    .getResultList();
        } finally { em.close(); }
    }

    public Patient update(Patient p) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Patient merged = em.merge(p);
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
            Patient p = em.find(Patient.class, id);
            if (p != null) em.remove(p);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }
}