package repository;

import jakarta.persistence.EntityManager;
import model.Department;
import util.JPAUtil;

import java.util.List;
import java.util.Optional;

public class DepartmentRepository {

    public Department save(Department d) {
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

    public Optional<Department> findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try { return Optional.ofNullable(em.find(Department.class, id)); }
        finally { em.close(); }
    }

    public List<Department> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT d FROM Department d ORDER BY d.nom", Department.class)
                    .getResultList();
        } finally { em.close(); }
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Department d = em.find(Department.class, id);
            if (d != null) em.remove(d);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }
}