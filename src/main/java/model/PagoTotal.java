/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name = "Pagototal")
public class PagoTotal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "pagototal", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<PagoDestino> pagodestinos = new ArrayList<>();

    @Basic
    private double total;
    private int fecha;
    private int contador;

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
        if (pagodestinos == null) {
            pagodestinos = new ArrayList<>(); // Asegura que nunca sea null
        }
        return pagodestinos;
    }


    public void addPagoDestino(PagoDestino pagodestino) {
        pagodestinos.add(pagodestino);
        pagodestino.setPagototal(this);
    }

    public void setPagodestinos(List<PagoDestino> pagodestinos) {
        this.pagodestinos = pagodestinos;
        for (PagoDestino pd : pagodestinos) {
            pd.setPagototal(this);
        }
    }

    
    public void removePagoDestino(PagoDestino pagodestino) {
        pagodestinos.remove(pagodestino);
        pagodestino.setPagototal(null);
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
        return "PagoTotal{id=" + id + ", total=" + total + ", numPagosDestinos=" + pagodestinos + "}";
    }

}
