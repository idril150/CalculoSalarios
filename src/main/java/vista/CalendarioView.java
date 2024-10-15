package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.toedter.calendar.JCalendar;
import controller.CalculoController;
import controller.VistasController;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import model.Empleado;
import java.util.List;
import model.Pago;

public class CalendarioView extends JFrame {
    
    private VistasController vctrl = new VistasController();
    private final JCalendar calendarioInicio;
    private final JCalendar calendarioFin;
    private final SimpleDateFormat dateFormat;

    public CalendarioView(List<Empleado> empleados) {        
        setTitle("Seleccionar Fechas");
        setSize(600, 300);  // Ajustado el tamaño para dar más espacio
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Añade espacio alrededor de los componentes

        dateFormat = new SimpleDateFormat("yyyyMMdd");

        // Crear los calendarios
        calendarioInicio = new JCalendar();
        calendarioFin = new JCalendar();

        // Configurar el layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Fecha de Inicio:"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(calendarioInicio, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Fecha de Fin:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(calendarioFin, gbc);

        // Botón para aceptar
        JButton aceptarButton = new JButton("Aceptar");
        aceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date fechaInicio = calendarioInicio.getDate();
                Date fechaFin = calendarioFin.getDate();
                String fechaInicioStr = dateFormat.format(fechaInicio);
                String fechaFinStr = dateFormat.format(fechaFin);
                int fechaInicioInt = Integer.parseInt(fechaInicioStr);
                int fechaFinInt = Integer.parseInt(fechaFinStr);
                if (fechaInicio != null && fechaFin != null) {
                        if (fechaInicioInt < fechaFinInt){
                            //System.out.println("Fecha de Inicio: " + dateFormat.format(fechaInicio));
                            //System.out.println("Fecha de Fin: " + dateFormat.format(fechaFin));
                            JOptionPane pane = new JOptionPane("Obteniendo datos...", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
                            final JDialog dialog = pane.createDialog("Cargando");
                            dialog.setModal(false); 
                            Thread loadingThread = new Thread(() -> dialog.setVisible(true));
                            loadingThread.start();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(500);
                                        
                                        VistasController vctrl = new VistasController();
                                        List<Empleado> primeraMitad = empleados.subList(0, empleados.size()/2);
                                        List<Empleado> segundaMitad = empleados.subList(empleados.size()/2, empleados.size());
                                        CalculoController cal1 = new CalculoController(primeraMitad, fechaInicioInt, fechaFinInt);
                                        CalculoController cal2 = new CalculoController(segundaMitad, fechaInicioInt, fechaFinInt);
                                        Thread hilo1 = new Thread(cal1);
                                        Thread hilo2 = new Thread(cal2);
                                        hilo1.start();
                                        hilo2.start();
                                        try {
                                            hilo1.join();
                                            hilo2.join();
                                        } catch(InterruptedException ex){
                                            ex.printStackTrace();
                                        }
                                        List<Pago> pagos = new ArrayList<>();
                                        pagos.addAll(cal1.getPagos());
                                        pagos.addAll(cal2.getPagos());
                                        //System.out.println(empctrl.findEmpleados());
                                        //var pagos = calctrl.calculasSalario(empleados, fechaInicioInt, fechaFinInt);
                                        vctrl.vistaBonos(pagos,empleados);
                                    }catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } finally {
                                        // Cerrar el diálogo después de abrir la vista
                                        SwingUtilities.invokeLater(dialog::dispose);
                                    }   }
                            }).start();                                
                            //System.out.println(fechaFinStr);
                            dispose();                          
                        }else{
                            JOptionPane.showMessageDialog(null, "la fecha de inicio no puede ser posterior a la fecha final");
                        }   
                }else {
                    System.out.println("Por favor, seleccione ambas fechas.");
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(aceptarButton, gbc);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                vctrl.iniciar();  // Regresar a la vista de inicio
                dispose();  // Cerrar la vista de empleados
            }
        });

    }
    
    
    
}
