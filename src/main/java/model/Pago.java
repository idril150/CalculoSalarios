package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pago implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    int idemp;
    @Basic
    double bonp;
    double bong;
    double ret;
    double sal;
    double pagtot;
    int fech;

    public Pago() {
    }

    public Pago(int idemp, double bonp, double bong, double ret, double sal, double pagtot, int fech) {
    this.idemp = idemp;
    this.bonp = bonp;
    this.bong = bong;
    this.ret = ret;
    this.sal = sal;
    this.pagtot = pagtot;
    this.fech = fech;
    }

    
    public int getIdemp() {
        return idemp;
    }

    public void setIdemp(int idemp) {
        this.idemp = idemp;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "Pago{" + "id=" + id + ", idemp=" + idemp + ", bonp=" + bonp + ", bong=" + bong + ", ret=" + ret + ", sal=" + sal + ", pagtot=" + pagtot + ", fech=" + fech + '}';
    }
 
}
