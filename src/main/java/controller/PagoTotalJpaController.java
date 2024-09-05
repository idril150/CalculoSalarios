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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import model.PagoDestino;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import model.PagoTotal;

/**
 *
 * @author Soportew
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
        EntityManager em = getEntityManager();
        try {
            if(findPagosTFech(pagoTotal.getFecha()) != null ){
                List<PagoTotal> pagosTotales = findPagosTFech(pagoTotal.getFecha());                
                pagoTotal.setContador(pagosTotales.size());
                em.getTransaction().begin();
                // Persistir el objeto PagoTotal, lo cual debería automáticamente persistir sus PagoDestino
                em.persist(pagoTotal);
                em.getTransaction().commit();
            }else{
                em.getTransaction().begin();
                em.persist(pagoTotal);
                em.getTransaction().commit();
            }
            
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
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
            CriteriaQuery<PagoTotal> cq = em.getCriteriaBuilder().createQuery(PagoTotal.class);
            cq.select(cq.from(PagoTotal.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            List<PagoTotal> resultList = q.getResultList();

            // Inicializa las colecciones para evitar LazyInitializationException
            for (PagoTotal pagoTotal : resultList) {
                // Accede a la colección para inicializarla
                pagoTotal.getPagodestinos().size();
            }

            return resultList;
        } finally {
            em.close();
        }
    }


    public PagoTotal findPagoTotal(int id) {
        EntityManager em = getEntityManager();

        String jpql = "SELECT p FROM PagoTotal p JOIN FETCH p.pagodestinos WHERE p.id = :id";
        return em.createQuery(jpql, PagoTotal.class)
                            .setParameter("id", id)
                            .getSingleResult();
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

    public List<PagoTotal> findPagosTFech(int fecha) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            // Consulta JPQL para obtener una lista de PagoTotal basada en la fecha
            Query query = em.createQuery("SELECT p FROM PagoTotal p WHERE p.fecha = :fecha");
            query.setParameter("fecha", fecha); // Establece el valor del parámetro "fecha"

            // Devuelve la lista de resultados
            return query.getResultList();
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public ComboBoxModel<String> fechaComboBoxModel() {
        List<PagoTotal> pagosTotales = findPagoTotalEntities();
        List<String> fechasString = new ArrayList<>();
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        for (PagoTotal pagoTotal : pagosTotales) {
            int fecha = pagoTotal.getFecha(); // Suponiendo que `getFecha` devuelve un int en formato yyyyMMdd
            int contador = pagoTotal.getContador(); // Obtener el contador

            // Convertimos el int a String manteniendo el formato yyyyMMdd
            String fechaString = String.format("%08d", fecha);
            // Convertimos el String a LocalDate
            LocalDate localDate = LocalDate.parse(fechaString, inputFormatter);
            // Convertimos el LocalDate a String en el formato deseado
            String fechaFormateada = localDate.format(outputFormatter);
            // Formateamos la cadena final
            String textoComboBox = String.format("%s (%d)", fechaFormateada, contador);
            fechasString.add(textoComboBox);
        }

        // Convertimos la lista de String a DefaultComboBoxModel
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(fechasString.toArray(new String[0]));
        return model;
    }

}
