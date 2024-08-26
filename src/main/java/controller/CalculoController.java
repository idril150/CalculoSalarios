package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.Empleado;
import model.Pago;
import vista.PagosView;



public class CalculoController {
    ChecadorJpaController chctrl = new ChecadorJpaController();
    PagoJpaController pctrl = new PagoJpaController();
    VistasController vctrl = new VistasController();
    public CalculoController() {
    }
    
    
    public List<Pago> calculasSalario(List<Empleado> empleados, int fechaInicio, int fechaFinal) {
        int dt;
        List<Pago> pagos = new ArrayList<>();
        for (Empleado empleado : empleados) {
            dt = chctrl.obtenerDiasTrabajados(empleado.getId(), fechaInicio, fechaFinal);
            Pago pago = new Pago(empleado, dt, 0, 0, empleado.getBonop(), 0,0, 0, 0, obtenerFechaHoy());
            pago.setPagtot(((pago.getEmpleado().getSalario() / 30) * pago.getDiast()) + ((pago.getEmpleado().getSalario() / 30) * pago.getDiasd())
                + pago.getBong() - (((pago.getEmpleado().getSalario() / 30)) / 2 * pago.getMediosd()) - pago.getRet()+pago.getEmpleado().getBonop());
            pagos.add(pago);
            System.out.println("el empleado "+pago.getEmpleado().getNombre()+" gano "+pago.getPagtot());
        }
        // Persistir la lista completa de pagos después de completar el bucle
        
        return pagos;
        
    }
    
    public static int obtenerFechaHoy() {
        // Obtiene la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Define el formato deseado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // Convierte la fecha al formato especificado
        String fechaFormateada = fechaActual.format(formatter);

        // Convierte la fecha formateada en un entero
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
        System.out.println(bonTuzoo.toString());
        for(Pago pago : pagos){
            switch (pago.getEmpleado().getZona()) {
                case "TUZOOFARI":
                        switch (pago.getEmpleado().getPuesto()) {
                            case "COORDINADOR":
                                pago.setBong(bonTuzoo[0]);
                                pago.setPagtot(recalCulo(pago));
                                System.out.println(pago);
                            break;
                            case "LIDER":
                                pago.setBong(bonTuzoo[1]);
                                pago.setPagtot(recalCulo(pago));
                                System.out.println(pago);
                            break;
                            case "VENDEDOR":
                                switch(pago.getEmpleado().getHorario()){
                                    case "TIEMPO COMPLETO":
                                        pago.setBong(bonTuzoo[2]);
                                        pago.setPagtot(recalCulo(pago));
                                        System.out.println(pago);
                                    break;
                                    case "FIN DE SEMANA":
                                        pago.setBong(bonTuzoo[3]);
                                        pago.setPagtot(recalCulo(pago));
                                        System.out.println(pago);
                                    break;
                                    default:
                                        System.out.println("no se encontro el horario "+pago.getEmpleado().getHorario());
                                    break;
                                }

                            break;
                            default:                           
                                System.out.println("puesto no reconocida: " + pago.getEmpleado().getPuesto());
                            break;
                        }                   
                break;

                
                case "ZOOMAT":
                        switch (pago.getEmpleado().getPuesto()) {
                            case "COORDINADOR":
                                pago.setBong(bonZoomat[0]);
                                pago.setPagtot(recalCulo(pago));
                                System.out.println(pago);
                            break;
                            case "LIDER":
                                pago.setBong(bonZoomat[1]);
                                pago.setPagtot(recalCulo(pago));
                                System.out.println(pago);
                            break;
                            case "VENDEDOR":
                                switch(pago.getEmpleado().getHorario()){
                                    case "TIEMPO COMPLETO":
                                        pago.setBong(bonZoomat[2]);
                                        pago.setPagtot(recalCulo(pago));
                                        System.out.println(pago);
                                    break;
                                    case "FIN DE SEMANA":
                                        pago.setBong(bonZoomat[3]);
                                        pago.setPagtot(recalCulo(pago));
                                        System.out.println(pago);
                                    break;
                                    default:
                                        System.out.println("no se encontro el horario "+pago.getEmpleado().getHorario());
                                    break;
                                }

                            break;
                            default:                           
                                System.out.println("puesto no reconocida: " + pago.getEmpleado().getPuesto());
                            break;
                        }                   
                break;

                
                case "TAMUX":
                        switch (pago.getEmpleado().getPuesto()) {
                            case "COORDINADOR":
                                pago.setBong(bonTamux[0]);
                                pago.setPagtot(recalCulo(pago));
                                System.out.println(pago);
                            break;
                            case "LIDER":
                                pago.setBong(bonTamux[1]);
                                pago.setPagtot(recalCulo(pago));
                                System.out.println(pago);
                            break;
                            case "VENDEDOR":
                                switch(pago.getEmpleado().getHorario()){
                                    case "TIEMPO COMPLETO":
                                        pago.setBong(bonTamux[2]);
                                        pago.setPagtot(recalCulo(pago));
                                        System.out.println(pago);
                                    break;
                                    case "FIN DE SEMANA":
                                        pago.setBong(bonTamux[3]);
                                        pago.setPagtot(recalCulo(pago));
                                        System.out.println(pago);
                                    break;
                                    default:
                                        System.out.println("no se encontro el horario "+pago.getEmpleado().getHorario());
                                    break;
                                }

                            break;
                            default:                           
                                System.out.println("puesto no reconocida: " + pago.getEmpleado().getPuesto());
                            break;
                        }                   
                break;

                
                case "NINEZ":
                        switch (pago.getEmpleado().getPuesto()) {
                            case "COORDINADOR":
                                pago.setBong(bonN[0]);
                                pago.setPagtot(recalCulo(pago));
                                System.out.println(pago);
                            break;
                            case "LIDER":
                                pago.setBong(bonN[1]);
                                pago.setPagtot(recalCulo(pago));
                                System.out.println(pago);
                            break;
                            case "VENDEDOR":
                                switch(pago.getEmpleado().getHorario()){
                                    case "TIEMPO COMPLETO":
                                        pago.setBong(bonN[2]);
                                        pago.setPagtot(recalCulo(pago));
                                        System.out.println(pago);
                                    break;
                                    case "FIN DE SEMANA":
                                        pago.setBong(bonN[3]);
                                        pago.setPagtot(recalCulo(pago));
                                        System.out.println(pago);
                                    break;
                                    default:
                                        System.out.println("no se encontro el horario "+pago.getEmpleado().getHorario());
                                    break;
                                }

                            break;
                            default:                           
                                System.out.println("puesto no reconocida: " + pago.getEmpleado().getPuesto());
                            break;
                        }                   
                break;

                
                case "TAMATAN":
                        switch (pago.getEmpleado().getPuesto()) {
                            case "COORDINADOR":
                                pago.setBong(bonTama[0]);
                                pago.setPagtot(recalCulo(pago));
                                System.out.println(pago);
                            break;
                            case "LIDER":
                                pago.setBong(bonTama[1]);
                                pago.setPagtot(recalCulo(pago));
                                System.out.println(pago);
                            break;
                            case "VENDEDOR":
                                switch(pago.getEmpleado().getHorario()){
                                    case "TIEMPO COMPLETO":
                                        pago.setBong(bonTama[2]);
                                        pago.setPagtot(recalCulo(pago));
                                        System.out.println(pago);
                                    break;
                                    case "FIN DE SEMANA":
                                        pago.setBong(bonTama[3]);
                                        pago.setPagtot(recalCulo(pago));
                                        System.out.println(pago);
                                    break;
                                    default:
                                        System.out.println("no se encontro el horario "+pago.getEmpleado().getHorario());
                                    break;
                                }

                            break;
                            default:                           
                                System.out.println("puesto no reconocida: " + pago.getEmpleado().getPuesto());
                            break;
                        }                   
                break;

                
                case "YUMKÁ":
                        switch (pago.getEmpleado().getPuesto()) {
                            case "COORDINADOR":
                                pago.setBong(bonY[0]);
                                pago.setPagtot(recalCulo(pago));
                                System.out.println(pago);
                            break;
                            case "LIDER":
                                pago.setBong(bonY[1]);
                                pago.setPagtot(recalCulo(pago));
                                System.out.println(pago);
                            break;
                            case "VENDEDOR":
                                switch(pago.getEmpleado().getHorario()){
                                    case "TIEMPO COMPLETO":
                                        pago.setBong(bonY[2]);
                                        pago.setPagtot(recalCulo(pago));
                                        System.out.println(pago);
                                    break;
                                    case "FIN DE SEMANA":
                                        pago.setBong(bonY[3]);
                                        pago.setPagtot(recalCulo(pago));
                                        System.out.println(pago);
                                    break;
                                    default:
                                        System.out.println("no se encontro el horario "+pago.getEmpleado().getHorario());
                                    break;
                                }

                            break;
                            default:                           
                                System.out.println("puesto no reconocida: " + pago.getEmpleado().getPuesto());
                            break;
                        }                   
                break;

                
                case "TLACO":
                        switch (pago.getEmpleado().getPuesto()) {
                            case "COORDINADOR":
                                pago.setBong(bonTla[0]);
                                pago.setPagtot(recalCulo(pago));
                                System.out.println(pago);
                            break;
                            case "LIDER":
                                pago.setBong(bonTla[1]);
                                pago.setPagtot(recalCulo(pago));
                                System.out.println(pago);
                            break;
                            case "VENDEDOR":
                                switch(pago.getEmpleado().getHorario()){
                                    case "TIEMPO COMPLETO":
                                        pago.setBong(bonTla[2]);
                                        pago.setPagtot(recalCulo(pago));
                                        System.out.println(pago);
                                    break;
                                    case "FIN DE SEMANA":
                                        pago.setBong(bonTla[3]);
                                        pago.setPagtot(recalCulo(pago));
                                        System.out.println(pago);
                                    break;
                                    default:
                                        System.out.println("no se encontro el horario "+pago.getEmpleado().getHorario());
                                    break;
                                }

                            break;
                            default:                           
                                System.out.println("puesto no reconocida: " + pago.getEmpleado().getPuesto());
                            break;
                        }                   
                break;

                
                case "VALLE PARAISO":
                        switch (pago.getEmpleado().getPuesto()) {
                            case "COORDINADOR":
                                pago.setBong(bonV[0]);
                                pago.setPagtot(recalCulo(pago));
                                System.out.println(pago);
                            break;
                            case "LIDER":
                                pago.setBong(bonV[1]);
                                pago.setPagtot(recalCulo(pago));
                                System.out.println(pago);
                            break;
                            case "VENDEDOR":
                                switch(pago.getEmpleado().getHorario()){
                                    case "TIEMPO COMPLETO":
                                        pago.setBong(bonV[2]);
                                        pago.setPagtot(recalCulo(pago));
                                        System.out.println(pago);
                                    break;
                                    case "FIN DE SEMANA":
                                        pago.setBong(bonV[3]);
                                        pago.setPagtot(recalCulo(pago));
                                        System.out.println(pago);
                                    break;
                                    default:
                                        System.out.println("no se encontro el horario "+pago.getEmpleado().getHorario());
                                    break;
                                }

                            break;
                            default:                           
                                System.out.println("puesto no reconocida: " + pago.getEmpleado().getPuesto());
                            break;
                        }                   
                break;

                
                case "DIOS PADRE":
                        switch (pago.getEmpleado().getPuesto()) {
                            case "COORDINADOR":
                                pago.setBong(bonD[0]);
                                pago.setPagtot(recalCulo(pago));
                                System.out.println(pago);
                            break;
                            case "LIDER":
                                pago.setBong(bonD[1]);
                                pago.setPagtot(recalCulo(pago));
                                System.out.println(pago);
                            break;
                            case "VENDEDOR":
                                switch(pago.getEmpleado().getHorario()){
                                    case "TIEMPO COMPLETO":
                                        pago.setBong(bonD[2]);
                                        pago.setPagtot(recalCulo(pago));
                                        System.out.println(pago);
                                    break;
                                    case "FIN DE SEMANA":
                                        pago.setBong(bonD[3]);
                                        pago.setPagtot(recalCulo(pago));
                                        System.out.println(pago);
                                    break;
                                    default:
                                        System.out.println("no se encontro el horario "+pago.getEmpleado().getHorario());
                                    break;
                                }

                            break;
                            default:                           
                                System.out.println("puesto no reconocida: " + pago.getEmpleado().getPuesto());
                            break;
                        }                   
                break;   
                default:
                    System.out.println("no se encontro la zona: "+ pago.getEmpleado().getZona());
                break;
            }
        }
        vctrl.vistaPagos(pagos);
    }

    
}
