/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package calculodesalarios;

import controller.EmpleadoJpaController;
import controller.PagoJpaController;
import java.util.List;
import model.Empleado;
import model.Pago;

/**
 *
 * @author Soporte
 */
public class CalculoDeSalarios {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        EmpleadoJpaController ectrl = new EmpleadoJpaController();
        PagoJpaController pctrl = new PagoJpaController();
        /*Empleado empleado = new Empleado(2, "pedro", "oficina", 8600, "tuxtla");
        
        ctrl.create(empleado);*/
        Pago pag = new Pago(1 ,100.0 , 200.0, 100.0, 2600.0, 2700.0, 20240815);
        
        pctrl.create(pag);
        /*List<Pago> pagos = ctrl.findPagos(1);
        for(Pago pago : pagos){
            System.out.println(pago);
        }*/
        //Proc*esosController pCtrl = new ProcesosController();v
        //pCtrl.copiarEmpleados();
        List<Empleado> lista = ectrl.findEmpleados();
        for(Empleado empleado : lista){
            System.out.print("al empleado "+empleado.getNombre()+"");
            List<Pago> pagos = pctrl.findPagos(empleado.getId());
            for(Pago pago : pagos){
                System.out.print("se le pago "+pago.getPagtot()+" la fecha de "+pago.getFech()+"\n");
            }
            System.out.println("\n\n");
        }
    }
}
