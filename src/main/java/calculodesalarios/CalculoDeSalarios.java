/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package calculodesalarios;

import controller.CalculoController;
import controller.ChecadorJpaController;
import controller.EmpleadoJpaController;
import controller.PagoJpaController;
import controller.VistasController;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import model.Empleado;
import model.Pago;
import vista.CalendarioView;
import vista.Inicio;

/**
 *
 * @author Soporte
 */
public class CalculoDeSalarios {

    public static void main(String[] args) {
        VistasController view = new VistasController();
        view.iniciar();
        
    }
}
