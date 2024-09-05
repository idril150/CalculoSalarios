package controller;

import java.util.List;
import model.Empleado;
import model.Pago;
import model.PagoDestino;
import model.PagoTotal;
import vista.BonosView;
import vista.CalendarioView;
import vista.EmpleadoView;
import vista.EmpleadosVista;
import vista.ExportarHistorial;
import vista.Inicio;
import vista.PagosView;



public class VistasController {

    private final PagoJpaController pagoJpaController; // Declaración de pagoJpaController

    public VistasController() {
        this.pagoJpaController = new PagoJpaController(); // Inicialización en el constructor
    }

    public void iniciar(){
        EmpleadoJpaController ectrl = new EmpleadoJpaController();
        List<Empleado> empleados = ectrl.findEmpleadoEntities();
        Inicio view = new Inicio(empleados);
        view.setVisible(true);
        view.setTitle("NOMINA");
        view.setLocationRelativeTo(null);                
    }

    public void vistaEmpleados(List<Empleado> empleados){        
        EmpleadosVista view = new EmpleadosVista(empleados);
        view.setVisible(true);
        view.setLocationRelativeTo(null);
    }

    public void vistaEmpleado(Empleado empleado, EmpleadosVista parentView) {
        EmpleadoView view = new EmpleadoView(parentView, empleado);
        view.jLId.setText(String.valueOf(empleado.getId()));
        view.jTNombre.setText(empleado.getNombre());
        view.jTPuesto.setText(empleado.getPuesto().getName());
        view.jTZona.setText(empleado.getDestino().getNombre());
        view.jTSalario.setText(String.valueOf( empleado.getSalario()));
        view.jTHorario.setText(empleado.getHorario().getName());
        view.jTBono.setText(String.valueOf(empleado.getBonop()));
        view.setVisible(true);
        view.setLocationRelativeTo(view);
    }

    public void vistaCalendario(List<Empleado> empleados){
        CalendarioView view = new CalendarioView(empleados);
        view.setVisible(true);
        view.setLocationRelativeTo(null);
    }

    public void vistaBonos(List<Pago> pagos, List<Empleado> empleados){
        BonosView view = new BonosView(pagos, empleados);
        view.setVisible(true);
        view.setLocationRelativeTo(null);
    }
    
    public void vistaPagos(List<Pago> pagos, List<PagoDestino> pagosDestinos, PagoTotal pagoTotal){
        PagosView view = new PagosView(pagoTotal, pagoJpaController); // Uso de pagoJpaController inicializado
        view.setVisible(true); // Añadir visibilidad
        view.setLocationRelativeTo(null); // Añadir localización
    }
    
    public void vistaExportar(){
        ExportarHistorial view = new ExportarHistorial();
        view.setVisible(true);
        view.setLocationRelativeTo(null);
    }

}
