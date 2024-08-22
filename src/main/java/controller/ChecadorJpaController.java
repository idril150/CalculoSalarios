package controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
    
    public int obtenerDiasTrabajados(int id, int fechIni, int fechFin) {
    EntityManager em = null;
        try {
            em = getEntityManager();
            String sql = "SELECT DISTINCT(f.DateTime) " +
                         "FROM event f " +
                         "WHERE f.IDEmployee = :id " +
                         "AND f.DateTime BETWEEN :fechIni AND :fechFin";

            Query query = em.createNativeQuery(sql);
            query.setParameter("id", id);
            query.setParameter("fechIni", fechIni * 1000000L);
            query.setParameter("fechFin", fechFin * 1000000L);

            List<Long> dateTimeList = query.getResultList();
            Set<Long> uniqueDates = new HashSet<>();

            for (long date : dateTimeList) {
                uniqueDates.add(date / 1000000L);
            }

            return uniqueDates.size();
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
