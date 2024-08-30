package model;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author Victor Muñoz Rodas
 */
@Entity
@Table(name = "Destino")
public class Destino implements Serializable {
    @Id
    int id;
    @Basic
    String nombre;
    Boolean estado;

    public Destino() {
    }

    public Destino(int id, String nombre, Boolean estado) {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Destino{" + "id=" + id + ", nombre=" + nombre + ", estado=" + estado + '}';
    }
    
}
