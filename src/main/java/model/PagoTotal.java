/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victor Muñoz Rodas
 */
@Entity
@Table(name = "Pagototal")
public class PagoTotal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    
    @OneToMany(mappedBy = "pagototal", cascade = CascadeType.ALL)
    private List<PagoDestino> pagodestinos = new ArrayList<>();
    
    @Basic
    double total;
    int fecha;
    int contador;

    public PagoTotal() {
    }

    public PagoTotal(double total, int fecha, int contador) {
        this.total = total;
        this.fecha = fecha;
        this.contador = contador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<PagoDestino> getPagodestinos() {
        return pagodestinos;
    }

    public void setPagodestinos(List<PagoDestino> pagodestinos) {
        this.pagodestinos = pagodestinos;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getFecha() {
        return fecha;
    }

    public void setFecha(int fecha) {
        this.fecha = fecha;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    @Override
    public String toString() {
        return "PagoTotal{" + "id=" + id + ", pagodestinos=" + pagodestinos + ", total=" + total + ", fecha=" + fecha + ", contador=" + contador + '}';
    }
    
}
