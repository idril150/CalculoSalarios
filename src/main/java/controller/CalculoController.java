package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import model.Empleado;
import model.Pago;
import model.PagoDestino;
import model.PagoTotal;

public class CalculoController {
    ChecadorJpaController chctrl = new ChecadorJpaController();
    VistasController vctrl = new VistasController();
    public CalculoController() {
    }
    
    
    public List<Pago> calculasSalario(List<Empleado> empleados, int fechaInicio, int fechaFinal) {
        int dt;
        List<Pago> pagos = new ArrayList<>();
        for (Empleado empleado : empleados) {
            dt = chctrl.obtenerDiasTrabajados(empleado.getId(), fechaInicio, fechaFinal);
            Pago pago = new Pago(empleado, dt, 2, 0, empleado.getBonop(), 0,0, 0, 0, obtenerFechaHoy());
            pago.setPagtot(((pago.getEmpleado().getSalario() / 30) * pago.getDiast()) + ((pago.getEmpleado().getSalario() / 30) * pago.getDiasd())
                + pago.getBong() - (((pago.getEmpleado().getSalario() / 30)) / 2 * pago.getMediosd()) - pago.getRet()+pago.getEmpleado().getBonop());
            pagos.add(pago);
            //System.out.println("el empleado "+pago.getEmpleado().getNombre()+" gano "+pago.getPagtot());
        }
        // Persistir la lista completa de pagos después de completar el bucle
        
        return pagos;
        
    }
    
    public int obtenerFechaHoy() {
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String fechaFormateada = fechaActual.format(formatter);
        return Integer.parseInt(fechaFormateada);
    }
    
    
    public double recalCulo (Pago pago){
        double salD = pago.getEmpleado().getSalario()/30;
        pago.setPagtot((salD*pago.getDiast())+(salD*pago.getDiasd())+pago.getBong()+pago.getBonp()+pago.getModValue()-
                       (pago.getMediosd()*salD)-pago.getRet());
        return pago.getPagtot();
    }
    
    public void agregarBonos(List<Pago> pagos, double bonTuzoo[], double bonZoomat[], double bonTamux[], double bonN[], 
                             double bonTama[], double bonY[], double bonTla[], double bonV[], double bonD[]){
        //System.out.println(Arrays.toString(bonTuzoo));
        pagos.sort(Comparator.comparing(pago -> pago.getEmpleado().getDestino().getId()));
        for(Pago pago : pagos){
            switch (pago.getEmpleado().getDestino().getId()) {
                case 1:
                    switch (pago.getEmpleado().getPuesto().getId()) {
                        case 1://vendedor
                            switch (pago.getEmpleado().getHorario().getId()) {
                                case 7://tiempo completo
                                    pago.setBong(bonZoomat[2]);
                                    pago.setPagtot(recalCulo(pago));
                                    break;
                                case 2:// fin de semana
                                    pago.setBong(bonZoomat[3]);
                                    pago.setPagtot(recalCulo(pago));
                                    break;
                                default:
                                    System.out.println("no se encontro el horario: "+ pago.getEmpleado().getHorario().getName());
                                    break;
                            }
                            break;
                        case 8://lider
                            pago.setBong(bonZoomat[1]);
                            pago.setPagtot(recalCulo(pago));
                            break;
                        case 11://Coordinador
                            pago.setBong(bonZoomat[0]);
                            pago.setPagtot(recalCulo(pago));
                            break;
                        default:
                            System.out.println("no se encontro el puesto: "+ pago.getEmpleado().getPuesto().getName());
                            break;
                    }
                    break;
                case 2:
                    switch (pago.getEmpleado().getPuesto().getId()) {
                        case 1://vendedor
                            switch (pago.getEmpleado().getHorario().getId()) {
                                case 7://tiempo completo
                                    pago.setBong(bonTama[2]);
                                    pago.setPagtot(recalCulo(pago));
                                    break;
                                case 2:// fin de semana
                                    pago.setBong(bonTama[3]);
                                    pago.setPagtot(recalCulo(pago));
                                    break;
                                default:
                                    System.out.println("no se encontro el horario: "+ pago.getEmpleado().getHorario().getName());
                                    break;
                            }
                            break;
                        case 8://lider
                            pago.setBong(bonTama[1]);
                            pago.setPagtot(recalCulo(pago));
                            break;
                        case 11://Coordinador
                            pago.setBong(bonTama[0]);
                            pago.setPagtot(recalCulo(pago));
                            break;
                        default:
                            System.out.println("no se encontro el puesto: "+ pago.getEmpleado().getPuesto().getName());
                            break;
                    }
                    break;
                case 4:
                    switch (pago.getEmpleado().getPuesto().getId()) {
                        case 1://vendedor
                            switch (pago.getEmpleado().getHorario().getId()) {
                                case 7://tiempo completo
                                    pago.setBong(bonTuzoo[2]);
                                    pago.setPagtot(recalCulo(pago));
                                    break;
                                case 2:// fin de semana
                                    pago.setBong(bonTuzoo[3]);
                                    pago.setPagtot(recalCulo(pago));
                                    break;
                                default:
                                    System.out.println("no se encontro el horario: "+ pago.getEmpleado().getHorario().getName());
                                    break;
                            }
                            break;
                        case 8://lider
                            pago.setBong(bonTuzoo[1]);
                            pago.setPagtot(recalCulo(pago));
                            break;
                        case 11://Coordinador
                            pago.setBong(bonTuzoo[0]);
                            pago.setPagtot(recalCulo(pago));
                            break;
                        default:
                            System.out.println("no se encontro el puesto: "+ pago.getEmpleado().getPuesto().getName());
                            break;
                    }
                    break;
                case 5:
                    switch (pago.getEmpleado().getPuesto().getId()) {
                        case 1://vendedor
                            switch (pago.getEmpleado().getHorario().getId()) {
                                case 7://tiempo completo
                                    pago.setBong(bonTamux[2]);
                                    pago.setPagtot(recalCulo(pago));
                                    break;
                                case 2:// fin de semana
                                    pago.setBong(bonTamux[3]);
                                    pago.setPagtot(recalCulo(pago));
                                    break;
                                default:
                                    System.out.println("no se encontro el horario: "+ pago.getEmpleado().getHorario().getName());
                                    break;
                            }
                            break;
                        case 8://lider
                            pago.setBong(bonTamux[1]);
                            pago.setPagtot(recalCulo(pago));
                            break;
                        case 11://Coordinador
                            pago.setBong(bonTamux[0]);
                            pago.setPagtot(recalCulo(pago));
                            break;
                        default:
                            System.out.println("no se encontro el puesto: "+ pago.getEmpleado().getPuesto().getName());
                            break;
                    }
                    break;
                case 6:
                    switch (pago.getEmpleado().getPuesto().getId()) {
                        case 1://vendedor
                            switch (pago.getEmpleado().getHorario().getId()) {
                                case 7://tiempo completo
                                    pago.setBong(bonTla[2]);
                                    pago.setPagtot(recalCulo(pago));
                                    break;
                                case 2:// fin de semana
                                    pago.setBong(bonTla[3]);
                                    pago.setPagtot(recalCulo(pago));
                                    break;
                                default:
                                    System.out.println("no se encontro el horario: "+ pago.getEmpleado().getHorario().getName());
                                    break;
                            }
                            break;
                        case 8://lider
                            pago.setBong(bonTla[1]);
                            pago.setPagtot(recalCulo(pago));
                            break;
                        case 11://Coordinador
                            pago.setBong(bonTla[0]);
                            pago.setPagtot(recalCulo(pago));
                            break;
                        default:
                            System.out.println("no se encontro el puesto: "+ pago.getEmpleado().getPuesto().getName());
                            break;
                    }
                    break;
                case 7:
                    switch (pago.getEmpleado().getPuesto().getId()) {
                        case 1://vendedor
                            switch (pago.getEmpleado().getHorario().getId()) {
                                case 7://tiempo completo
                                    pago.setBong(bonD[2]);
                                    pago.setPagtot(recalCulo(pago));
                                    break;
                                case 2:// fin de semana
                                    pago.setBong(bonD[3]);
                                    pago.setPagtot(recalCulo(pago));
                                    break;
                                default:
                                    System.out.println("no se encontro el horario: "+ pago.getEmpleado().getHorario());
                                    break;
                            }
                            break;
                        case 8://lider
                            pago.setBong(bonD[1]);
                            pago.setPagtot(recalCulo(pago));
                            break;
                        case 11://Coordinador
                            pago.setBong(bonD[0]);
                            pago.setPagtot(recalCulo(pago));
                            break;
                        default:
                            System.out.println("no se encontro el puesto: "+ pago.getEmpleado().getPuesto().getName());
                            break;
                    }
                    break;
                case 8:
                    switch (pago.getEmpleado().getPuesto().getId()) {
                        case 1://vendedor
                            switch (pago.getEmpleado().getHorario().getId()) {
                                case 7://tiempo completo
                                    pago.setBong(bonN[2]);
                                    pago.setPagtot(recalCulo(pago));
                                    break;
                                case 2:// fin de semana
                                    pago.setBong(bonN[3]);
                                    pago.setPagtot(recalCulo(pago));
                                    break;
                                default:
                                    System.out.println("no se encontro el horario: "+ pago.getEmpleado().getHorario().getName());
                                    break;
                            }
                            break;
                        case 8://lider
                            pago.setBong(bonN[1]);
                            pago.setPagtot(recalCulo(pago));
                            break;
                        case 11://Coordinador
                            pago.setBong(bonN[0]);
                            pago.setPagtot(recalCulo(pago));
                            break;
                        default:
                            System.out.println("no se encontro el puesto: "+ pago.getEmpleado().getPuesto().getName());
                            break;
                    }
                    break;
                case 9:
                    switch (pago.getEmpleado().getPuesto().getId()) {
                        case 1://vendedor
                            switch (pago.getEmpleado().getHorario().getId()) {
                                case 7://tiempo completo
                                    pago.setBong(bonV[2]);
                                    pago.setPagtot(recalCulo(pago));
                                    break;
                                case 2:// fin de semana
                                    pago.setBong(bonV[3]);
                                    pago.setPagtot(recalCulo(pago));
                                    break;
                                default:
                                    System.out.println("no se encontro el horario: "+ pago.getEmpleado().getHorario().getName());
                                    break;
                            }
                            break;
                        case 8://lider
                            pago.setBong(bonV[1]);
                            pago.setPagtot(recalCulo(pago));
                            break;
                        case 11://Coordinador
                            pago.setBong(bonV[0]);
                            pago.setPagtot(recalCulo(pago));
                            break;
                        default:
                            System.out.println("no se encontro el puesto: "+ pago.getEmpleado().getPuesto().getName());
                            break;
                    }
                    break;
                case 10:
                    switch (pago.getEmpleado().getPuesto().getId()) {
                        case 1://vendedor
                            switch (pago.getEmpleado().getHorario().getId()) {
                                case 7://tiempo completo
                                    pago.setBong(bonY[2]);
                                    pago.setPagtot(recalCulo(pago));
                                    break;
                                case 2:// fin de semana
                                    pago.setBong(bonY[3]);
                                    pago.setPagtot(recalCulo(pago));
                                    break;
                                default:
                                    System.out.println("no se encontro el horario: "+ pago.getEmpleado().getHorario().getName());
                                    break;
                            }
                            break;
                        case 8://lider
                            pago.setBong(bonY[1]);
                            pago.setPagtot(recalCulo(pago));
                            break;
                        case 11://Coordinador
                            pago.setBong(bonY[0]);
                            pago.setPagtot(recalCulo(pago));
                            break;
                        default:
                            System.out.println("no se encontro puesto: "+ pago.getEmpleado().getPuesto().getName());
                            break;
                    }
                    break;
                default:
                    System.out.println("no se encontro la zona: "+ pago.getEmpleado().getDestino().getNombre());
                    break;
            }
        }
        List<PagoDestino> pagosDestinos = pagosTotales(pagos);
        PagoTotal pagoTotal = pagoTotalSuma(pagosDestinos);
        vctrl.vistaPagos(pagos, pagosDestinos, pagoTotal);
    }
    
    public List<PagoDestino> pagosTotales(List<Pago> pagos){
        List<Pago> pagosZoo = pagos.stream().filter(pago -> pago.getEmpleado().getDestino().getId()==1).collect(Collectors.toList());
        int fecha = pagosZoo.get(0).getFech();
        List<Pago> pagosTamat = pagos.stream().filter(pago -> pago.getEmpleado().getDestino().getId()==2).collect(Collectors.toList());
        List<Pago> pagosTuz = pagos.stream().filter(pago -> pago.getEmpleado().getDestino().getId()==4).collect(Collectors.toList());
        List<Pago> pagosTamu = pagos.stream().filter(pago -> pago.getEmpleado().getDestino().getId()==5).collect(Collectors.toList());
        List<Pago> pagosTlac = pagos.stream().filter(pago -> pago.getEmpleado().getDestino().getId()==6).collect(Collectors.toList());
        List<Pago> pagosDi = pagos.stream().filter(pago -> pago.getEmpleado().getDestino().getId()==7).collect(Collectors.toList());
        List<Pago> pagosNin = pagos.stream().filter(pago -> pago.getEmpleado().getDestino().getId()==8).collect(Collectors.toList());
        List<Pago> pagosVal = pagos.stream().filter(pago -> pago.getEmpleado().getDestino().getId()==9).collect(Collectors.toList());
        List<Pago> pagosYum = pagos.stream().filter(pago -> pago.getEmpleado().getDestino().getId()==10).collect(Collectors.toList());
        List<Pago> pagosOf = pagos.stream().filter(pago -> pago.getEmpleado().getDestino().getId()==11).collect(Collectors.toList());
         
        PagoDestino pagosZoomat = new PagoDestino(pagosZoo.get(0).getEmpleado().getDestino(), fecha, 0);      
        pagosZoomat.setPagos(pagosZoo);
        PagoDestino pagosTamatan = new PagoDestino(pagosTamat.get(0).getEmpleado().getDestino(), fecha, 0);
        pagosTamatan.setPagos(pagosTamat);
        PagoDestino pagosTuzoo = new PagoDestino(pagosTuz.get(0).getEmpleado().getDestino(), fecha, 0);
        pagosTuzoo.setPagos(pagosTuz);
        PagoDestino pagosTamux = new PagoDestino(pagosTamu.get(0).getEmpleado().getDestino(), fecha, 0);
        pagosTamux.setPagos(pagosTamu);
        PagoDestino pagosTlaco = new PagoDestino(pagosTlac.get(0).getEmpleado().getDestino(), fecha, 0);
        pagosTlaco.setPagos(pagosTlac);
        PagoDestino pagosDios= new PagoDestino(pagosDi.get(0).getEmpleado().getDestino(), fecha, 0);
        pagosDios.setPagos(pagosDi);
        PagoDestino pagosNinez = new PagoDestino(pagosNin.get(0).getEmpleado().getDestino(), fecha, 0);
        pagosNinez.setPagos(pagosNin);
        PagoDestino pagosValle = new PagoDestino(pagosVal.get(0).getEmpleado().getDestino(), fecha, 0);
        pagosValle.setPagos(pagosVal);
        PagoDestino pagosYumka = new PagoDestino(pagosYum.get(0).getEmpleado().getDestino(), fecha, 0);
        pagosYumka.setPagos(pagosYum);
        PagoDestino pagosOficina = new PagoDestino(pagosOf.get(0).getEmpleado().getDestino(), fecha, 0);
        pagosOficina.setPagos(pagosOf);
        System.out.println(pagosTamatan);
        List<PagoDestino> pagosDestinos = new ArrayList<>();
        pagosDestinos.add(pagosZoomat);
        pagosDestinos.add(pagosTamatan);
        pagosDestinos.add(pagosTuzoo);
        pagosDestinos.add(pagosTamux);
        pagosDestinos.add(pagosTlaco);
        pagosDestinos.add(pagosDios);
        pagosDestinos.add(pagosNinez);
        pagosDestinos.add(pagosValle);
        pagosDestinos.add(pagosYumka);
        pagosDestinos.add(pagosOficina);
        return pagosDestinos;
    }
    
    public Double sumasSalarios(List<Pago> pagos){
        double a = 0;
        for(Pago pago : pagos){
            a+=pago.getPagtot();
        }        
        return a;
    }
    
    public PagoTotal pagoTotalSuma(List<PagoDestino> pagosDestinos){
        PagoTotal pagoTotal = new PagoTotal();
        pagoTotal.setFecha(pagosDestinos.get(0).getFecha());
        double a=0;
        for(PagoDestino pagos : pagosDestinos){
            a+=pagos.sumaPagos();
        }
        pagoTotal.setTotal(a);
        pagoTotal.setPagodestinos(pagosDestinos);
        pagoTotal.setContador(0);
        pagoTotal.setContador(0);
        System.out.println(a);
        return pagoTotal;
    }


}
