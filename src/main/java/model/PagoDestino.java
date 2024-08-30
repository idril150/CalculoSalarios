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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "Pagodestino")
public class PagoDestino implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @ManyToOne
    @JoinColumn(name="destino_id", nullable = false)
    private Destino destino;

    @OneToMany(mappedBy = "pagoDestino", cascade = CascadeType.ALL)
    private List<Pago> pagos = new ArrayList<>();
    
    @ManyToOne
    @JoinColumn(name = "pago_total_id")
    private PagoTotal pagototal;
    
    @Basic
    private double total;
    private int fecha;
    private int contador;



    public PagoDestino() {
    }

    public PagoDestino(Destino destino, int fecha, int contador) {
        this.destino = destino;
        this.fecha = fecha;
        this.contador = contador;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Destino getDestino() {
        return destino;
    }

    public void setDestino(Destino destino) {
        this.destino = destino;
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }

    public PagoTotal getPagototal() {
        return pagototal;
    }

    public void setPagototal(PagoTotal pagototal) {
        this.pagototal = pagototal;
    }

    @Override
    public String toString() {
        return "PagoDestino{" + "id=" + id + ", destino=" + destino + ", pagos=" + pagos + ", pagototal=" + pagototal + ", total=" + total + ", fecha=" + fecha + ", contador=" + contador + '}';
    }
    
    public Double sumaPagos(){
        double a=0;
        for(Pago pago:pagos){
            a+=pago.getPagtot();
        }
        return a;
    }
    
}
