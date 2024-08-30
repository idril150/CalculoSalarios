package model;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import org.hibernate.annotations.ManyToAny;


@Entity
@Table(name = "Empleado")
public class Empleado implements Serializable {
    
    @Id   
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "destino_id", nullable = false)
    private Destino destino;
    @ManyToOne
    @JoinColumn(name = "clasificacion_id1", nullable = false)
    private Clasificacion horario;
    @ManyToOne
    @JoinColumn(name = "clasificasion_id2", nullable = false)
    private Clasificacion puesto;
    
    @Basic
    private String nombre;
    private double salario;
    private double bonop;
    private boolean estado;

    public Empleado() {
    }

    public Empleado(int id, Destino destino, Clasificacion horario, Clasificacion puesto, String nombre, double salario, double bonop, boolean estado) {
        this.id = id;
        this.destino = destino;
        this.horario = horario;
        this.puesto = puesto;
        this.nombre = nombre;
        this.salario = salario;
        this.bonop = bonop;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Destino getDestino() {
        return destino;
    }

    public void setDestino(Destino destino) {
        this.destino = destino;
    }

    public Clasificacion getHorario() {
        return horario;
    }

    public void setHorario(Clasificacion horario) {
        this.horario = horario;
    }

    public Clasificacion getPuesto() {
        return puesto;
    }

    public void setPuesto(Clasificacion puesto) {
        this.puesto = puesto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public double getBonop() {
        return bonop;
    }

    public void setBonop(double bonop) {
        this.bonop = bonop;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Empleado{" + "id=" + id + ", destino=" + destino + ", horario=" + horario + ", puesto=" + puesto + ", nombre=" + nombre + ", salario=" + salario + ", bonop=" + bonop + ", estado=" + estado + '}';
    }

}
