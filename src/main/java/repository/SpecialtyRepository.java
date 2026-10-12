package repository;

import jakarta.persistence.EntityManager;
import model.Specialty;
import util.JPAUtil;

import java.util.List;
import java.util.Optional;

public class SpecialtyRepository {

    public Specialty save(Specialty s) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (s.getId() == null) em.persist(s);
            else s = em.merge(s);
            em.getTransaction().commit();
            return s;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    public Optional<Specialty> findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try { return Optional.ofNullable(em.find(Specialty.class, id)); }
        finally { em.close(); }
    }

    public List<Specialty> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT s FROM Specialty s " +
                                    "LEFT JOIN FETCH s.department " +
                                    "ORDER BY s.nom", Specialty.class)
                    .getResultList();
        } finally { em.close(); }
    }

    public List<Specialty> findByDepartment(Long deptId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT s FROM Specialty s " +
                                    "LEFT JOIN FETCH s.department " +
                                    "WHERE s.department.id = :d", Specialty.class)
                    .setParameter("d", deptId).getResultList();
        } finally { em.close(); }
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Specialty s = em.find(Specialty.class, id);
            if (s != null) em.remove(s);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }
}