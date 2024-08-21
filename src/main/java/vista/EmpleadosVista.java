package vista;

import controller.VistasController;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import model.Empleado;

public class EmpleadosVista extends JFrame {
    private VistasController vctrl = new VistasController();

    private JTable empleadoTable;
    

    public EmpleadosVista(List<Empleado> empleados) {
        // Configuración básica del JFrame
        setTitle("Lista de Empleados");
        setSize(800, 400);

        // Crear el modelo de la tabla
        String[] columnas = {"ID", "Nombre", "Salario", "Puesto", "Zona", "Horario","Acción"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);

        // Llenar el modelo con los datos de los empleados
        for (Empleado empleado : empleados) {
            Object[] fila = {
                empleado.getId(),
                empleado.getNombre(),
                empleado.getSalario(),
                empleado.getPuesto(),
                empleado.getZona(),
                empleado.getHorario(),
                "EDITAR"  // Texto del botón
            };
            model.addRow(fila);
        }

        // Crear la tabla y agregar el modelo
        empleadoTable = new JTable(model);
        empleadoTable.getColumnModel().getColumn(0).setPreferredWidth(20);
        empleadoTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        empleadoTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        empleadoTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        empleadoTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        empleadoTable.getColumnModel().getColumn(5).setPreferredWidth(200);
        empleadoTable.getColumnModel().getColumn(6).setPreferredWidth(90);

        // Configurar el renderizador y el editor para la columna de botones
        empleadoTable.getColumn("Acción").setCellRenderer(new ButtonRenderer());
        empleadoTable.getColumn("Acción").setCellEditor(new ButtonEditor(new JCheckBox(), empleados, this));

        JScrollPane scrollPane = new JScrollPane(empleadoTable);
        add(scrollPane);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                vctrl.iniciar();  // Regresar a la vista de inicio
                dispose();  // Cerrar la vista de empleados
            }
        });


        // Mostrar la ventana
        setVisible(true);
    }
    
    public void actualizarTabla(List<Empleado> empleados) {
        DefaultTableModel model = (DefaultTableModel) empleadoTable.getModel();
        model.setRowCount(0); // Limpiar la tabla

        // Volver a llenar la tabla con los empleados actualizados
        for (Empleado empleado : empleados) {
            Object[] fila = {
                empleado.getId(),
                empleado.getNombre(),
                empleado.getSalario(),
                empleado.getPuesto(),
                empleado.getZona(),
                empleado.getHorario(),
                "EDITAR"  // Texto del botón
            };
            model.addRow(fila);
        }
        
}

    


// Renderizador de la celda del botón
class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText((value == null) ? "" : value.toString());
        return this;
    }
}

// Editor de la celda del botón
    class ButtonEditor extends DefaultCellEditor {
        VistasController vctrl = new VistasController();
        private JButton button;
        private String label;
        private boolean isPushed;
        private List<Empleado> empleados;
        private JFrame parentFrame;
        private int row;  // Variable para guardar el índice de la fila

        public ButtonEditor(JCheckBox checkBox, List<Empleado> empleados, JFrame parentFrame) {
            super(checkBox);
            this.empleados = empleados;
            this.parentFrame = parentFrame;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            this.row = row;  // Guardar el índice de la fila actual
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                Empleado empleado = empleados.get(row);
                vctrl.vistaEmpleado(empleado, (EmpleadosVista) parentFrame);
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
}

