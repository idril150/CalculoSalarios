package controller;

import java.util.List;
import model.Empleado;
import model.Pago;
import vista.BonosView;
import vista.CalendarioView;
import vista.EmpleadoView;
import vista.EmpleadosVista;
import vista.Inicio;
import vista.PagosView;



public class VistasController {

    private final PagoJpaController pagoJpaController; // Declaración de pagoJpaController

    public VistasController() {
        this.pagoJpaController = new PagoJpaController(); // Inicialización en el constructor
    }

    public void iniciar(){
        Inicio view = new Inicio();
        view.setVisible(true);
        view.setTitle("NOMINA");
        view.setLocationRelativeTo(null);
    }

    public void vistaEmpleados(){
        EmpleadoJpaController ectrl = new EmpleadoJpaController();
        List<Empleado> empleados = ectrl.findEmpleados();
        EmpleadosVista view = new EmpleadosVista(empleados);
        view.setVisible(true);
        view.setLocationRelativeTo(null);
    }

    public void vistaEmpleado(Empleado empleado, EmpleadosVista parentView) {
        EmpleadoView view = new EmpleadoView(parentView);
        view.jLId.setText(String.valueOf(empleado.getId()));
        view.jTNombre.setText(empleado.getNombre());
        view.jTPuesto.setText(empleado.getPuesto());
        view.jTZona.setText(empleado.getZona());
        view.jTSalario.setText(String.valueOf(empleado.getSalario()));
        view.jTHorario.setText(empleado.getHorario());
        view.jTBono.setText(String.valueOf(empleado.getBonop()));
        view.setVisible(true);
        view.setLocationRelativeTo(view);
    }

    public void vistaCalendario(){
        CalendarioView view = new CalendarioView();
        view.setVisible(true);
        view.setLocationRelativeTo(null);
    }

    public void vistaBonos(List<Pago> pagos){
        BonosView view = new BonosView(pagos);
        view.setVisible(true);
        view.setLocationRelativeTo(null);
    }

    public void vistaPagos(List<Pago> pagos){
        PagosView view = new PagosView(pagos, pagoJpaController); // Uso de pagoJpaController inicializado
        view.setVisible(true); // Añadir visibilidad
        view.setLocationRelativeTo(null); // Añadir localización
    }
}
