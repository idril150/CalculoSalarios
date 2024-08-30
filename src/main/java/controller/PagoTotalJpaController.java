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
import model.PagoDestino;
import java.util.ArrayList;
import java.util.List;
import model.PagoTotal;

/**
 *
 * @author Soporte
 */
public class PagoTotalJpaController implements Serializable {

    public PagoTotalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public PagoTotalJpaController() {
        emf = Persistence.createEntityManagerFactory("db1");
    }

    public void create(PagoTotal pagoTotal) {
        if (pagoTotal.getPagodestinos() == null) {
            pagoTotal.setPagodestinos(new ArrayList<PagoDestino>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PagoDestino> attachedPagodestinos = new ArrayList<PagoDestino>();
            for (PagoDestino pagodestinosPagoDestinoToAttach : pagoTotal.getPagodestinos()) {
                pagodestinosPagoDestinoToAttach = em.getReference(pagodestinosPagoDestinoToAttach.getClass(), pagodestinosPagoDestinoToAttach.getId());
                attachedPagodestinos.add(pagodestinosPagoDestinoToAttach);
            }
            pagoTotal.setPagodestinos(attachedPagodestinos);
            em.persist(pagoTotal);
            for (PagoDestino pagodestinosPagoDestino : pagoTotal.getPagodestinos()) {
                PagoTotal oldPagototalOfPagodestinosPagoDestino = pagodestinosPagoDestino.getPagototal();
                pagodestinosPagoDestino.setPagototal(pagoTotal);
                pagodestinosPagoDestino = em.merge(pagodestinosPagoDestino);
                if (oldPagototalOfPagodestinosPagoDestino != null) {
                    oldPagototalOfPagodestinosPagoDestino.getPagodestinos().remove(pagodestinosPagoDestino);
                    oldPagototalOfPagodestinosPagoDestino = em.merge(oldPagototalOfPagodestinosPagoDestino);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PagoTotal pagoTotal) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PagoTotal persistentPagoTotal = em.find(PagoTotal.class, pagoTotal.getId());
            List<PagoDestino> pagodestinosOld = persistentPagoTotal.getPagodestinos();
            List<PagoDestino> pagodestinosNew = pagoTotal.getPagodestinos();
            List<PagoDestino> attachedPagodestinosNew = new ArrayList<PagoDestino>();
            for (PagoDestino pagodestinosNewPagoDestinoToAttach : pagodestinosNew) {
                pagodestinosNewPagoDestinoToAttach = em.getReference(pagodestinosNewPagoDestinoToAttach.getClass(), pagodestinosNewPagoDestinoToAttach.getId());
                attachedPagodestinosNew.add(pagodestinosNewPagoDestinoToAttach);
            }
            pagodestinosNew = attachedPagodestinosNew;
            pagoTotal.setPagodestinos(pagodestinosNew);
            pagoTotal = em.merge(pagoTotal);
            for (PagoDestino pagodestinosOldPagoDestino : pagodestinosOld) {
                if (!pagodestinosNew.contains(pagodestinosOldPagoDestino)) {
                    pagodestinosOldPagoDestino.setPagototal(null);
                    pagodestinosOldPagoDestino = em.merge(pagodestinosOldPagoDestino);
                }
            }
            for (PagoDestino pagodestinosNewPagoDestino : pagodestinosNew) {
                if (!pagodestinosOld.contains(pagodestinosNewPagoDestino)) {
                    PagoTotal oldPagototalOfPagodestinosNewPagoDestino = pagodestinosNewPagoDestino.getPagototal();
                    pagodestinosNewPagoDestino.setPagototal(pagoTotal);
                    pagodestinosNewPagoDestino = em.merge(pagodestinosNewPagoDestino);
                    if (oldPagototalOfPagodestinosNewPagoDestino != null && !oldPagototalOfPagodestinosNewPagoDestino.equals(pagoTotal)) {
                        oldPagototalOfPagodestinosNewPagoDestino.getPagodestinos().remove(pagodestinosNewPagoDestino);
                        oldPagototalOfPagodestinosNewPagoDestino = em.merge(oldPagototalOfPagodestinosNewPagoDestino);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = pagoTotal.getId();
                if (findPagoTotal(id) == null) {
                    throw new NonexistentEntityException("The pagoTotal with id " + id + " no longer exists.");
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
            PagoTotal pagoTotal;
            try {
                pagoTotal = em.getReference(PagoTotal.class, id);
                pagoTotal.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pagoTotal with id " + id + " no longer exists.", enfe);
            }
            List<PagoDestino> pagodestinos = pagoTotal.getPagodestinos();
            for (PagoDestino pagodestinosPagoDestino : pagodestinos) {
                pagodestinosPagoDestino.setPagototal(null);
                pagodestinosPagoDestino = em.merge(pagodestinosPagoDestino);
            }
            em.remove(pagoTotal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PagoTotal> findPagoTotalEntities() {
        return findPagoTotalEntities(true, -1, -1);
    }

    public List<PagoTotal> findPagoTotalEntities(int maxResults, int firstResult) {
        return findPagoTotalEntities(false, maxResults, firstResult);
    }

    private List<PagoTotal> findPagoTotalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PagoTotal.class));
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

    public PagoTotal findPagoTotal(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PagoTotal.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagoTotalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PagoTotal> rt = cq.from(PagoTotal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
