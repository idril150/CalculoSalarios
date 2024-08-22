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
import vista.Inicio;

/**
 *
 * @author Soporte
 */
public class CalculoDeSalarios {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        ChecadorJpaController cctrl = new ChecadorJpaController();
        EmpleadoJpaController ectrl = new EmpleadoJpaController();
        PagoJpaController pctrl = new PagoJpaController();
        CalculoController cactrl = new CalculoController();
        
        //cactrl.calculasSalario(ectrl.findEmpleado(4));
        /*VistasController vistas = new VistasController();
        vistas.iniciar();*/
        

            //cactrl.calculasSalario(ectrl.findEmpleados());

        
        /*for(Empleado empleado : ectrl.findEmpleados()){
            System.out.println(empleado.getId()+" "+empleado.getNombre());
            System.out.println(cctrl.obtenerDiasTrabajados(empleado.getId(), 20240601, 20240615));
            System.out.println("\n\n");
        }*/
        
        
        
        
        //creacion de empleado
        /*
        Empleado empleado = new Empleado(1, "Pedro", "Oficina", 8600, "Tuxtla");

        try {
            ectrl.create(empleado);
            System.out.println("Empleado creado exitosamente.");
        } catch (Exception e) {
            System.err.println("Error al crear el empleado: " + e.getMessage());
        } */       
        
        //creacion de pago
        /*List<Empleado> lista = ectrl.findEmpleados();        
        Pago pag = new Pago(lista.get(0),100.0 , 200.0, 100.0, 2600.0, 2900.0, 20240815);
        pctrl.create(pag);*/
        
        
        //encontrar pagos de empleados
        /*List<Pago> pagos = pctrl.findPagos(1);
        for(Pago pago : pagos){
            System.out.println(pago);
        }*/
        
        
        //ProcesosController pCtrl = new ProcesosController();v
        //pCtrl.copiarEmpleados();
        
        
        //agregar empleados a la bd
        /*List<Object[]> checador = cctrl.encontrarEmpleados();
        System.out.printf("%-5s %-20s %-20s %-20s %-20s%n", "ID", "Name", "Last Name", "Branch", "Sorter2");
        for (Object[] row : checador) {
            // Asumimos que el orden es ID, Name, LastName, BranchName, Sorter2Name
            Integer id = (Integer) row[0];
            String name = (String) row[1];
            String lastName = (String) row[2];
            String branchName = (String) row[3];
            String sorter2Name = (String) row[4];

            // Verificar si el empleado ya existe
            Empleado existingEmpleado = ectrl.findEmpleado(id);
            if (existingEmpleado != null) {
                System.out.printf("Empleado con ID %d ya existe. Saltando al siguiente.%n", id);
                continue; // Saltar al siguiente empleado
            }

            // Crear el nuevo empleado
            Empleado empleado = new Empleado(id, name, sorter2Name, 0, branchName);

            try {
                ectrl.create(empleado);
                System.out.println("Empleado creado exitosamente.");
            } catch (Exception e) {
                System.err.println("Error al crear el empleado: " + e.getMessage());
            }

            System.out.printf("%-5d %-20s %-20s %-20s %-20s%n", id, name, lastName, branchName, sorter2Name);
        }*/
        

        //Encontrar empleados
        /*List<Empleado> lista = ectrl.findEmpleados();
        for(Empleado emp : lista){
            System.out.println(emp);
        }*/
        
        
    }
}
