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

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Object[]> encontrarEmpleados() {
        EntityManager em = null;
        try {
            em = getEntityManager();
             String sql = "SELECT e.ID, e.Name, e.LastName, e.IDBranch, e.IDSorter1, e.IDSorter2 "
                   + "FROM employee e";
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

    public List<Object[]> encontrarDestinos() {
        EntityManager em = null;
        try {
            em = getEntityManager();
            // Corregir la consulta SQL eliminando la coma antes de FROM
            String sql = "SELECT b.ID, b.Name FROM branch b";
            // Utiliza createQuery en lugar de createNamedQuery ya que no es una consulta nombrada
            Query query = em.createNativeQuery(sql); // createNativeQuery para consultas SQL nativas
            return query.getResultList();
        } catch (Exception e) {
            // Manejar excepción
            System.err.println("Error al encontrar Destinos: " + e.getMessage());
            throw e;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Object[]> encontrarClasificaciones() {
        EntityManager em = null;
        try {
            em = getEntityManager();
            // Corregir la consulta SQL eliminando la coma antes de FROM
            String sql = "SELECT b.ID, b.Name FROM sorter b";
            // Utiliza createQuery en lugar de createNamedQuery ya que no es una consulta nombrada
            Query query = em.createNativeQuery(sql); // createNativeQuery para consultas SQL nativas
            return query.getResultList();
        } catch (Exception e) {
            // Manejar excepción
            System.err.println("Error al encontrar Destinos: " + e.getMessage());
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
            String sql = "SELECT DISTINCT(f.DateTime) "
                    + "FROM event f "
                    + "WHERE f.IDEmployee = :id "
                    + "AND f.DateTime BETWEEN :fechIni AND :fechFin";

            Query query = em.createNativeQuery(sql);
            query.setParameter("id", id);
            query.setParameter("fechIni", fechIni * 1000000L);
            query.setParameter("fechFin", fechFin * 1000000L+1000000L);            
            List<Long> dateTimeList = query.getResultList();
            //System.out.println(dateTimeList);
            Set<Long> uniqueDates = new HashSet<>();
            
            for (long date : dateTimeList) {
                uniqueDates.add(date / 1000000L);
            }
            //System.out.println(uniqueDates);
            System.out.println(uniqueDates);
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
