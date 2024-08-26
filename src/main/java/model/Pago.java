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
    @JoinColumn(name = "empleado_id", nullable = false)  // Clave foránea que conecta a Empleado
    private Empleado empleado;

    @Basic
    private int diast;
    private int diasd;
    private double bong;
    private double bonp;
    private double ret;
    private int mediosd;
    private double modValue; // Cambiado de 'mod' a 'modValue'
    private double pagtot;
    private int fech;

    public Pago() {
    }

    public Pago(Empleado empleado, int diast, int diasd, double bong, double bonp, double ret, int mediosd, double modValue, double pagtot, int fech) {
        this.empleado = empleado;
        this.diast = diast;
        this.diasd = diasd;
        this.bong = bong;
        this.bonp = bonp;
        this.ret = ret;
        this.mediosd = mediosd;
        this.modValue = modValue;
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

    public int getDiast() {
        return diast;
    }

    public void setDiast(int diast) {
        this.diast = diast;
    }

    public int getDiasd() {
        return diasd;
    }

    public void setDiasd(int diasd) {
        this.diasd = diasd;
    }

    public int getMediosd() {
        return mediosd;
    }

    public void setMediosd(int mediosd) {
        this.mediosd = mediosd;
    }

    public double getModValue() {
        return modValue;
    }

    public void setModValue(double modValue) {
        this.modValue = modValue;
    }

    public double getBonp() {
        return bonp;
    }

    public void setBonp(double bonp) {
        this.bonp = bonp;
    }

    @Override
    public String toString() {
        return "Pago{" + "id=" + id + ", empleado=" + empleado + ", diast=" + diast + ", diasd=" + diasd + ", bong=" + bong + ", bonp=" + bonp + ", ret=" + ret + ", mediosd=" + mediosd + ", modValue=" + modValue + ", pagtot=" + pagtot + ", fech=" + fech + '}';
    }

}
