package repository;

import jakarta.persistence.EntityManager;
import model.Availability;
import util.JPAUtil;

import java.util.List;
import java.util.Optional;

public class AvailabilityRepository {

    public Availability save(Availability a) {
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

    public Optional<Availability> findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try { return Optional.ofNullable(em.find(Availability.class, id)); }
        finally { em.close(); }
    }

    public List<Availability> findByDoctor(Long doctorId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT a FROM Availability a WHERE a.doctor.id = :d " +
                                    "ORDER BY a.jour, a.heureDebut", Availability.class)
                    .setParameter("d", doctorId).getResultList();
        } finally { em.close(); }
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Availability a = em.find(Availability.class, id);
            if (a != null) em.remove(a);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }
}