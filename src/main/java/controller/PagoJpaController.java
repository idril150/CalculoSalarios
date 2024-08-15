package controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Pago;

public class PagoJpaController implements Serializable {
    
    EntityManagerFactory emf;

    public PagoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public PagoJpaController() {
        emf = Persistence.createEntityManagerFactory("db1");        
    }
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public void create(Pago pago) {
    EntityManager em = getEntityManager();
    try {
        em.getTransaction().begin();
        em.persist(pago);
        em.getTransaction().commit();
    } catch (Exception ex) {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        throw new RuntimeException("Error al crear el pago", ex);
    } finally {
        em.close();
    }
}
    
    public List<Pago> findPagos(int idemp){
        EntityManager em = null;
        try{
            em=getEntityManager();
            Query qr = em.createQuery("SELECT p FROM Pago p WHERE p.idemp = :id");
            qr.setParameter("id", idemp);
            List<Pago> pag = qr.getResultList();
            return pag;
        }catch(Exception ex){
            throw ex;
        }
    }
    
    
}
