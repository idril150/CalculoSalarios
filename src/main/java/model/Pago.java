package model;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;


@Entity
@Table(name = "Pago")
public class Pago implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false)  // Indica la clave foránea que conecta a Empleado
    private Empleado empleado;  // Cambia idemp por la relación ManyToOne

    @Basic
    private double bonp;
    private double bong;
    private double ret;
    private double sal;
    private double pagtot;
    private int fech;

    public Pago() {
    }

    public Pago(Empleado empleado, double bonp, double bong, double ret, double sal, double pagtot, int fech) {
        this.empleado = empleado;
        this.bonp = bonp;
        this.bong = bong;
        this.ret = ret;
        this.sal = sal;
        this.pagtot = pagtot;
        this.fech = fech;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public double getBonp() {
        return bonp;
    }

    public void setBonp(double bonp) {
        this.bonp = bonp;
    }

    public double getBong() {
        return bong;
    }

    public void setBong(double bong) {
        this.bong = bong;
    }

    public double getRet() {
        return ret;
    }

    public void setRet(double ret) {
        this.ret = ret;
    }

    public double getSal() {
        return sal;
    }

    public void setSal(double sal) {
        this.sal = sal;
    }

    public double getPagtot() {
        return pagtot;
    }

    public void setPagtot(double pagtot) {
        this.pagtot = pagtot;
    }

    public int getFech() {
        return fech;
    }

    public void setFech(int fech) {
        this.fech = fech;
    }

    @Override
    public String toString() {
        return "Pago{" + "id=" + id + ", empleado=" + empleado + ", bonp=" + bonp + ", bong=" + bong + ", ret=" + ret + ", sal=" + sal + ", pagtot=" + pagtot + ", fech=" + fech + '}';
    }
}
