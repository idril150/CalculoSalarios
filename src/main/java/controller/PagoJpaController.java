package controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.io.Serializable;
import java.util.List;
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
    
    public List<Pago> findPagos(int idemp) {
    EntityManager em = null;
    try {
        em = getEntityManager();
        // Cambia 'p.idemp' por 'p.empleado.id' para hacer referencia a la relación ManyToOne
        Query qr = em.createQuery("SELECT p FROM Pago p WHERE p.empleado.id = :id");
        qr.setParameter("id", idemp);
        List<Pago> pagos = qr.getResultList();
        return pagos;
    } catch (Exception ex) {
        throw ex;
    } finally {
        if (em != null) {
            em.close();
        }
    }
}
    
    
}
