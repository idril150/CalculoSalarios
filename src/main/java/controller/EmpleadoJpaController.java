package controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import model.Empleado;

public class EmpleadoJpaController implements Serializable {
    
    EntityManagerFactory emf;

    public EmpleadoJpaController() {
        emf = Persistence.createEntityManagerFactory("db1");
    }

    public EmpleadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public void create(Empleado empleado){
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado find = em.find(Empleado.class, empleado.getId());
            if(find != null){
                throw new EntityNotFoundException("ya existe un empleado con el ID"+ empleado.getId());
            }
            em.persist(empleado);
            em.getTransaction().commit();
        }catch(Exception ex){
            throw ex;
        }finally{
            if(em!=null){
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
                throw new EntityNotFoundException("no se encontro un empleado con el ID "+empleado.getId());
            }
            empEdit.setNombre(empleado.getNombre());
            empEdit.setPuesto(empleado.getPuesto());
            empEdit.setSalario(empleado.getSalario());
            empEdit.setZona(empleado.getZona());
            em.getTransaction().commit();
        } catch (Exception e) {
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
                empleado.getId();
            }catch(EntityNotFoundException enfe){
                throw new EntityNotFoundException("el Empleado con el ID "+id+" no se encontro");
            }
            em.remove(empleado);
            em.getTransaction().commit();        
        } catch (Exception e) {
            throw e;
        } finally {
            if (em!=null) {
                em.close();
            }
        }
    }
    
    public List<Empleado> findEmpleados(){
        return findEmpleados(true, -1, -1);
    }
    
    public List<Empleado> findEmpleados(boolean all, int maxResults, int firstResult){
        EntityManager em = getEntityManager();
        try{
            javax.persistence.criteria.CriteriaQuery<Empleado> cq = em.getCriteriaBuilder().createQuery(Empleado.class);
            cq.select(cq.from(Empleado.class));
            javax.persistence.Query q = em.createQuery(cq);
            if(!all){
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        }finally{
            if (em != null) {
                em.close();                
            }
        }
    }
    
    
}
