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
    
    public void calculasSalario(List<Empleado> empleados) {
        int dt;
        List<Pago> pagos = new ArrayList<>();
        for (Empleado empleado : empleados) {
            dt = chctrl.obtenerDiasTrabajados(empleado.getId(), 20240601, 20240615);
            Pago pago = new Pago(empleado, dt, 0, 0, 0, 0, 0, 0, 20240822);
            pago.setPagtot(((pago.getEmpleado().getSalario() / 30) * pago.getDiast()) + ((pago.getEmpleado().getSalario() / 30) * pago.getDiasd())
                + pago.getBong() - (((pago.getEmpleado().getSalario() / 30)) / 2 * pago.getMediosd()) - pago.getRet()+pago.getEmpleado().getBonop());
            pagos.add(pago);
            System.out.println("el empleado "+pago.getEmpleado().getNombre()+" gano "+pago.getPagtot());
        }
        // Persistir la lista completa de pagos después de completar el bucle
        PagosView pview = new PagosView(pagos, pctrl);
        pview.setVisible(true);
        
    }

    
}
