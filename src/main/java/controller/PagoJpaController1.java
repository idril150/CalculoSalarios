/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import model.Pago;
import model.PagoDestino;

/**
 *
 * @author Soporte
 */
public class PagoJpaController1 implements Serializable {

    public PagoJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public PagoJpaController1() {
        emf = Persistence.createEntityManagerFactory("db1");
    }    

    public void create(Pago pago) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PagoDestino pagoDestino = pago.getPagoDestino();
            if (pagoDestino != null) {
                pagoDestino = em.getReference(pagoDestino.getClass(), pagoDestino.getId());
                pago.setPagoDestino(pagoDestino);
            }
            em.persist(pago);
            if (pagoDestino != null) {
                pagoDestino.getPagos().add(pago);
                pagoDestino = em.merge(pagoDestino);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pago pago) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pago persistentPago = em.find(Pago.class, pago.getId());
            PagoDestino pagoDestinoOld = persistentPago.getPagoDestino();
            PagoDestino pagoDestinoNew = pago.getPagoDestino();
            if (pagoDestinoNew != null) {
                pagoDestinoNew = em.getReference(pagoDestinoNew.getClass(), pagoDestinoNew.getId());
                pago.setPagoDestino(pagoDestinoNew);
            }
            pago = em.merge(pago);
            if (pagoDestinoOld != null && !pagoDestinoOld.equals(pagoDestinoNew)) {
                pagoDestinoOld.getPagos().remove(pago);
                pagoDestinoOld = em.merge(pagoDestinoOld);
            }
            if (pagoDestinoNew != null && !pagoDestinoNew.equals(pagoDestinoOld)) {
                pagoDestinoNew.getPagos().add(pago);
                pagoDestinoNew = em.merge(pagoDestinoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = pago.getId();
                if (findPago(id) == null) {
                    throw new NonexistentEntityException("The pago with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pago pago;
            try {
                pago = em.getReference(Pago.class, id);
                pago.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pago with id " + id + " no longer exists.", enfe);
            }
            PagoDestino pagoDestino = pago.getPagoDestino();
            if (pagoDestino != null) {
                pagoDestino.getPagos().remove(pago);
                pagoDestino = em.merge(pagoDestino);
            }
            em.remove(pago);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pago> findPagoEntities() {
        return findPagoEntities(true, -1, -1);
    }

    public List<Pago> findPagoEntities(int maxResults, int firstResult) {
        return findPagoEntities(false, maxResults, firstResult);
    }

    private List<Pago> findPagoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pago.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Pago findPago(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pago.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pago> rt = cq.from(Pago.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
