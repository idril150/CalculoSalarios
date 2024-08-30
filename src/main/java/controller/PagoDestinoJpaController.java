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
import model.PagoTotal;
import model.Pago;
import java.util.ArrayList;
import java.util.List;
import model.PagoDestino;

/**
 *
 * @author Soporte
 */
public class PagoDestinoJpaController implements Serializable {

    public PagoDestinoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public PagoDestinoJpaController(){
        emf = Persistence.createEntityManagerFactory("db1");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PagoDestino pagoDestino) {
        if (pagoDestino.getPagos() == null) {
            pagoDestino.setPagos(new ArrayList<Pago>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PagoTotal pagototal = pagoDestino.getPagototal();
            if (pagototal != null) {
                pagototal = em.getReference(pagototal.getClass(), pagototal.getId());
                pagoDestino.setPagototal(pagototal);
            }
            List<Pago> attachedPagos = new ArrayList<Pago>();
            for (Pago pagosPagoToAttach : pagoDestino.getPagos()) {
                pagosPagoToAttach = em.getReference(pagosPagoToAttach.getClass(), pagosPagoToAttach.getId());
                attachedPagos.add(pagosPagoToAttach);
            }
            pagoDestino.setPagos(attachedPagos);
            em.persist(pagoDestino);
            if (pagototal != null) {
                pagototal.getPagodestinos().add(pagoDestino);
                pagototal = em.merge(pagototal);
            }
            for (Pago pagosPago : pagoDestino.getPagos()) {
                PagoDestino oldPagoDestinoOfPagosPago = pagosPago.getPagoDestino();
                pagosPago.setPagoDestino(pagoDestino);
                pagosPago = em.merge(pagosPago);
                if (oldPagoDestinoOfPagosPago != null) {
                    oldPagoDestinoOfPagosPago.getPagos().remove(pagosPago);
                    oldPagoDestinoOfPagosPago = em.merge(oldPagoDestinoOfPagosPago);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PagoDestino pagoDestino) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PagoDestino persistentPagoDestino = em.find(PagoDestino.class, pagoDestino.getId());
            PagoTotal pagototalOld = persistentPagoDestino.getPagototal();
            PagoTotal pagototalNew = pagoDestino.getPagototal();
            List<Pago> pagosOld = persistentPagoDestino.getPagos();
            List<Pago> pagosNew = pagoDestino.getPagos();
            if (pagototalNew != null) {
                pagototalNew = em.getReference(pagototalNew.getClass(), pagototalNew.getId());
                pagoDestino.setPagototal(pagototalNew);
            }
            List<Pago> attachedPagosNew = new ArrayList<Pago>();
            for (Pago pagosNewPagoToAttach : pagosNew) {
                pagosNewPagoToAttach = em.getReference(pagosNewPagoToAttach.getClass(), pagosNewPagoToAttach.getId());
                attachedPagosNew.add(pagosNewPagoToAttach);
            }
            pagosNew = attachedPagosNew;
            pagoDestino.setPagos(pagosNew);
            pagoDestino = em.merge(pagoDestino);
            if (pagototalOld != null && !pagototalOld.equals(pagototalNew)) {
                pagototalOld.getPagodestinos().remove(pagoDestino);
                pagototalOld = em.merge(pagototalOld);
            }
            if (pagototalNew != null && !pagototalNew.equals(pagototalOld)) {
                pagototalNew.getPagodestinos().add(pagoDestino);
                pagototalNew = em.merge(pagototalNew);
            }
            for (Pago pagosOldPago : pagosOld) {
                if (!pagosNew.contains(pagosOldPago)) {
                    pagosOldPago.setPagoDestino(null);
                    pagosOldPago = em.merge(pagosOldPago);
                }
            }
            for (Pago pagosNewPago : pagosNew) {
                if (!pagosOld.contains(pagosNewPago)) {
                    PagoDestino oldPagoDestinoOfPagosNewPago = pagosNewPago.getPagoDestino();
                    pagosNewPago.setPagoDestino(pagoDestino);
                    pagosNewPago = em.merge(pagosNewPago);
                    if (oldPagoDestinoOfPagosNewPago != null && !oldPagoDestinoOfPagosNewPago.equals(pagoDestino)) {
                        oldPagoDestinoOfPagosNewPago.getPagos().remove(pagosNewPago);
                        oldPagoDestinoOfPagosNewPago = em.merge(oldPagoDestinoOfPagosNewPago);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = pagoDestino.getId();
                if (findPagoDestino(id) == null) {
                    throw new NonexistentEntityException("The pagoDestino with id " + id + " no longer exists.");
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
            PagoDestino pagoDestino;
            try {
                pagoDestino = em.getReference(PagoDestino.class, id);
                pagoDestino.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pagoDestino with id " + id + " no longer exists.", enfe);
            }
            PagoTotal pagototal = pagoDestino.getPagototal();
            if (pagototal != null) {
                pagototal.getPagodestinos().remove(pagoDestino);
                pagototal = em.merge(pagototal);
            }
            List<Pago> pagos = pagoDestino.getPagos();
            for (Pago pagosPago : pagos) {
                pagosPago.setPagoDestino(null);
                pagosPago = em.merge(pagosPago);
            }
            em.remove(pagoDestino);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PagoDestino> findPagoDestinoEntities() {
        return findPagoDestinoEntities(true, -1, -1);
    }

    public List<PagoDestino> findPagoDestinoEntities(int maxResults, int firstResult) {
        return findPagoDestinoEntities(false, maxResults, firstResult);
    }

    private List<PagoDestino> findPagoDestinoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PagoDestino.class));
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

    public PagoDestino findPagoDestino(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PagoDestino.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagoDestinoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PagoDestino> rt = cq.from(PagoDestino.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
