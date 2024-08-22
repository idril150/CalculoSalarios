package controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Empleado;

public class EmpleadoJpaController implements Serializable {
    
    private static final Logger LOGGER = Logger.getLogger(EmpleadoJpaController.class.getName());
    private EntityManagerFactory emf;

    public EmpleadoJpaController() {
        emf = Persistence.createEntityManagerFactory("db1");
    }

    public EmpleadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public void create(Empleado empleado) throws Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado find = em.find(Empleado.class, empleado.getId());
            if(find != null){
                throw new IllegalArgumentException("Ya existe un empleado con el ID " + empleado.getId());
            }
            em.persist(empleado);
            em.getTransaction().commit();
        } catch (Exception ex){
            LOGGER.log(Level.SEVERE, "Error al crear el empleado: ", ex);
            throw ex;
        } finally {
            if(em != null){
                em.close();
            }
        }
    }
    
    public void edit(Empleado empleado){
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado empEdit = em.find(Empleado.class, empleado.getId());
            if (empEdit == null) {
                throw new EntityNotFoundException("No se encontró un empleado con el ID " + empleado.getId());
            }
            empEdit.setNombre(empleado.getNombre());
            empEdit.setPuesto(empleado.getPuesto());
            empEdit.setSalario(empleado.getSalario());
            empEdit.setZona(empleado.getZona());
            empEdit.setHorario(empleado.getHorario());
            empEdit.setBonop(empleado.getBonop());
            em.getTransaction().commit();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al editar el empleado: ", e);
            throw e;
        } finally {
            if(em != null){
                em.close();
            }
        }
    }
    
    public void destroyEmpleado(int id){
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado empleado;
            try{
                empleado = em.getReference(Empleado.class, id);
                empleado.getId(); // Verifica si el empleado realmente existe
            }catch(EntityNotFoundException enfe){
                throw new EntityNotFoundException("El empleado con el ID " + id + " no se encontró");
            }
            em.remove(empleado);
            em.getTransaction().commit();        
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar el empleado: ", e);
            throw e;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public List<Empleado> findEmpleados() {
        return findEmpleados(true, -1, -1);
    }
    
    public List<Empleado> findEmpleados(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Empleado> cq = em.getCriteriaBuilder().createQuery(Empleado.class);
            cq.select(cq.from(Empleado.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            if (em != null) {
                em.close();                
            }
        }
    }
    
    public Empleado findEmpleado(int id){
        EntityManager em = null;
        try{
            em = getEntityManager();            
            return em.find(Empleado.class, id);
        }catch(Exception ex){
            throw ex;
        }finally{
            if(em!=null){
                em.close();
            }
        }
        
    }

}
