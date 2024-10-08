package controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
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
    
    public void create(List<Pago> pagos) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            for (Pago pago : pagos) {
                if (!em.contains(pago)) {
                    em.persist(pago);
                } else {
                    // Optional: Handle cases where the entity is already managed
                }
            }
            tx.commit();
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error al crear los pagos", ex);
        } finally {
            if (em != null) {
                em.close();
            }
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

    
    public List<Pago> findPagosFech(int fecha) {
    EntityManager em = null;
        try {
            em = getEntityManager();
            // Usa el nombre de parámetro correcto en la consulta
            Query query = em.createQuery("SELECT p FROM Pago p WHERE p.fech = :fech");
            query.setParameter("fech", fecha); // Asegúrate de que el nombre aquí es "fech"
            return query.getResultList();
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    
     public List<Integer> findDistinctFechas() {
        EntityManager em = null;
        try {
            em = getEntityManager();
            // Usamos SELECT DISTINCT para obtener las fechas únicas
            Query qr = em.createQuery("SELECT DISTINCT p.fech FROM Pago p ORDER BY p.fech");
            List<Integer> fechas = qr.getResultList();
            return fechas;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
}
