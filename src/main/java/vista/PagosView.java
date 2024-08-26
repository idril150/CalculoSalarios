package vista;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.table.TableCellRenderer;
import model.Pago;
import controller.PagoJpaController; // Asegúrate de importar tu clase PagoJpaController

public class PagosView extends JFrame {
    private final JTable table;
    private final List<Pago> pagos;
    private final PagoJpaController pagoJpaController; // Añadido para gestionar los pagos

    public PagosView(List<Pago> pagos, PagoJpaController pagoJpaController) {
        this.pagos = pagos;
        this.pagoJpaController = pagoJpaController; // Inicializar el controlador de pagos

        setTitle("Pagos");
        setSize(1000, 400); // Aumenta el tamaño para acomodar más columnas
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear el modelo de tabla
        AbstractTableModel tableModel = new AbstractTableModel() {
            private final String[] columnNames = {
                "Nombre", "Salario", "Zona", "Horario", "Bonop", "Días Trabajados", "Días de Descanso", "Medios Días", "Bonificación",
                "Retenciones", "Pago Total", "Fecha de Pago", "Actualizar"
            };

            @Override
            public int getRowCount() {
                return pagos.size();
            }

            @Override
            public int getColumnCount() {
                return columnNames.length;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                Pago pago = pagos.get(rowIndex);
                switch (columnIndex) {
                    case 0: return pago.getEmpleado().getNombre();
                    case 1: return pago.getEmpleado().getSalario();
                    case 2: return pago.getEmpleado().getZona();
                    case 3: return pago.getEmpleado().getHorario();
                    case 4: return pago.getBonp();
                    case 5: return pago.getDiast();
                    case 6: return pago.getDiasd();
                    case 7: return pago.getMediosd();
                    case 8: return pago.getBong();
                    case 9: return pago.getRet();
                    case 10: return pago.getPagtot();
                    case 11: return pago.getFech();
                    case 12: return "Actualizar";
                    default: return null;
                }
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                // Hacer que solo las columnas 4 a 11 sean editables (no editables las de Empleado ni Pago Total)
                return columnIndex >= 4 && columnIndex <= 9;
            }

            @Override
            public void setValueAt(Object value, int rowIndex, int columnIndex) {
                Pago pago = pagos.get(rowIndex);
                switch (columnIndex) {
                    case 4: pago.setBonp((Double) value); break;
                    case 5: pago.setDiast((Integer) value); break;
                    case 6: pago.setDiasd((Integer) value); break;
                    case 7: pago.setMediosd((Integer) value); break;
                    case 8: pago.setBong((Double) value); break;
                    case 9: pago.setRet((Double) value); break;
                    case 11: pago.setFech((Integer) value); break;
                }
                
                // Recalcular el pago total
                pago.setPagtot(calcularPagoTotal(pago));
                
                // Notificar a la tabla que el valor ha cambiado
                fireTableCellUpdated(rowIndex, columnIndex);
                fireTableCellUpdated(rowIndex, 10); // Actualizar la columna de Pago Total
            }

            @Override
            public String getColumnName(int column) {
                return columnNames[column];
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 12) {
                    return JButton.class;
                } else if (columnIndex == 0 || columnIndex == 1 || columnIndex == 2 || columnIndex == 3) {
                    return String.class; // nombre, salario, zona, horario
                } else if (columnIndex == 4 || columnIndex == 8 || columnIndex == 9 || columnIndex == 10) {
                    return Double.class; // bonop, bonificación, retenciones, pago total
                } else {
                    return Integer.class; // días trabajados, días de descanso, medios días, fecha de pago
                }
            }
        };

        // Crear la tabla con el modelo
        table = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                if (column == 12) {
                    JButton button = new JButton("Actualizar");
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int rowIndex = getSelectedRow();
                            Pago pago = pagos.get(rowIndex);
                            pago.setPagtot(calcularPagoTotal(pago));
                            tableModel.fireTableRowsUpdated(rowIndex, rowIndex);
                        }
                    });
                    return button;
                }
                return comp;
            }
        };

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Crear el botón "Aceptar"
        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Llamar al método create con la lista de pagos
                try {
                    pagoJpaController.create(pagos);
                    JOptionPane.showMessageDialog(PagosView.this, "Pagos guardados exitosamente.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(PagosView.this, "Error al guardar los pagos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Añadir el botón en la parte inferior
        JPanel panelSur = new JPanel();
        panelSur.add(btnAceptar);
        add(panelSur, BorderLayout.SOUTH);
    }

    private double calcularPagoTotal(Pago pago) {
        double salarioDiario = pago.getEmpleado().getSalario() / 30;
        return (salarioDiario * pago.getDiast()) + (salarioDiario * pago.getDiasd())
             - ((salarioDiario / 2) * pago.getMediosd())
             + pago.getBonp()
             + pago.getBong()
             - pago.getRet();
    }
}
