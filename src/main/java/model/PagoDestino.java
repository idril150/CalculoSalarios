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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Pagodestino")
public class PagoDestino implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "pagototal_id")
    private PagoTotal pagototal;

    @OneToMany(mappedBy = "pagodestino", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Pago> pagos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "destino_id", nullable = false)
    private Destino destino;

    @Basic
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

    public Destino getDestino() {
        return destino;
    }

    public void setDestino(Destino destino) {
        this.destino = destino;
    }

    public List<Pago> getPagos() {
        if (pagos == null) {
            pagos = new ArrayList<>(); // Asegura que nunca sea null
        }
        return pagos;
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
        for(Pago pag : pagos){
            pag.setPagoDestino(this);
        }
    }

    public void addPago(Pago pago) {
        pagos.add(pago);
        pago.setPagoDestino(this);
    }
    
    public void removePago(Pago pago) {
        pagos.remove(pago);
        pago.setPagoDestino(null);
    }
    

    public PagoTotal getPagototal() {
        return pagototal;
    }

    public void setPagototal(PagoTotal pagototal) {
        this.pagototal = pagototal;
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
        return "PagoDestino{id=" + id + ", destino=" + destino.getNombre() + ", numPagos=" + pagos + "}";
    }


    public Double sumaPagos() {
        double suma = 0;
        for (Pago pago : pagos) {
            suma += pago.getPagtot();
        }
        return suma;
    }
}
