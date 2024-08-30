package model;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Clasificador")
public class Clasificacion implements Serializable {
    
    @Id
    private int id;
    @Basic
    String name;

    public Clasificacion() {
    }

    public Clasificacion(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Clasificador{" + "id=" + id + ", name=" + name + '}';
    }
}
