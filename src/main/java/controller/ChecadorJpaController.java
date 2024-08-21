package controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.util.List;


public class ChecadorJpaController {
    
    EntityManagerFactory emf;

    public ChecadorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public ChecadorJpaController() {
        emf = Persistence.createEntityManagerFactory("dbChecador");
    }
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public List<Object[]> encontrarEmpleados() {
    EntityManager em = null;
        try {
            em = getEntityManager();
            String sql = "SELECT e.ID, e.Name, e.LastName, b.Name AS branchName, " +
             "s2.Name AS sorter2Name, s1.Name AS sorter1Name " +
             "FROM employee e " +
             "LEFT JOIN branch b ON e.IDBranch = b.ID " +
             "LEFT JOIN sorter s2 ON e.IDSorter2 = s2.ID " +
             "LEFT JOIN sorter s1 ON e.IDSorter1 = s1.ID";
            Query query = em.createNativeQuery(sql);
            return query.getResultList();
        } catch (Exception e) {
            // Manejar excepción
            System.err.println("Error al encontrar empleados: " + e.getMessage());
            throw e;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
