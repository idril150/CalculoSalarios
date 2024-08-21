
package controller;

import java.util.List;
import model.Empleado;
import vista.EmpleadoView;
import vista.EmpleadosVista;
import vista.Inicio;

public class VistasController {
    
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
        view.setVisible(true);
        view.setLocationRelativeTo(view);
    }
}
