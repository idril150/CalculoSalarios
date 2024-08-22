package model;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "Empleado")
public class Empleado implements Serializable {
    
    @Id   
    private int id;
    
    @Basic
    private String nombre;
    private String puesto;
    private double salario;
    private String zona;
    private String horario;
    private double bonop;

    public Empleado() {
    }

    public Empleado(int id, String nombre, String puesto, double salario, String zona, String horario, double bonop) {
        this.id = id;
        this.nombre = nombre;
        this.puesto = puesto;
        this.salario = salario;
        this.zona = zona;
        this.horario = horario;
        this.bonop = bonop;
    }

    // Getters y Setters
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

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public double getBonop() {
        return bonop;
    }

    public void setBonop(double bonop) {
        this.bonop = bonop;
    }

    @Override
    public String toString() {
        return "Empleado{" + "id=" + id + ", nombre=" + nombre + ", puesto=" + puesto + ", salario=" + salario + ", zona=" + zona + ", horario=" + horario + ", bonop=" + bonop + '}';
    }
        
}
