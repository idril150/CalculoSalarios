package vista;

import controller.ExportarController;
import controller.PagoDestinoJpaController;
import controller.PagoJpaController;
import controller.PagoTotalJpaController;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;
import model.Pago;
import model.PagoDestino;
import model.PagoTotal;

public class PagosView extends JFrame {
    PagoTotalJpaController ptotal = new PagoTotalJpaController();
    PagoDestinoJpaController pdctl = new PagoDestinoJpaController();
    private final JTable table;
    private final JTable secondTable;
    private final AbstractTableModel tableModel;
    private final AbstractTableModel tableModel2;
    private List<Pago> pagos;
    private List<PagoDestino> pagosDestinos;
    private PagoTotal pagoTotal;

    public PagosView(List<Pago> pagos, List<PagoDestino> pagosDestinos, PagoTotal pagoTotal, PagoJpaController pagoJpaController) {
        this.pagos = pagos;
        this.pagosDestinos = pagosDestinos;
        this.pagoTotal = pagoTotal;

        setTitle("Pagos");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Modelo de la primera tabla
        tableModel = new AbstractTableModel() {
            private final String[] columnNames = {
                "Nombre", "Zona", "Puesto", "Horario", "Salario", "Días Trabajados", "Días de Descanso",
                "Medios Días", "Bono personal", "Bono de meta", "MOD", "Retenciones", "Pago total", "Fecha de pago"
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
                    case 1: return pago.getEmpleado().getDestino().getNombre();
                    case 2: return pago.getEmpleado().getPuesto().getName();
                    case 3: return pago.getEmpleado().getHorario().getName();
                    case 4: return pago.getEmpleado().getSalario();
                    case 5: return pago.getDiast();
                    case 6: return pago.getDiasd();
                    case 7: return pago.getMediosd();
                    case 8: return pago.getBonp();
                    case 9: return pago.getBong();
                    case 10: return pago.getModValue();
                    case 11: return pago.getRet();
                    case 12: return pago.getPagtot();
                    case 13: return pago.getFech();
                    default: return null;
                }
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return columnIndex >= 5 && columnIndex <= 11; // Permitir edición en las columnas especificadas
            }

            @Override
            public void setValueAt(Object value, int rowIndex, int columnIndex) {
                Pago pago = pagos.get(rowIndex);
                try {
                    switch (columnIndex) {
                        case 5: pago.setDiast((Integer) value); break;
                        case 6: pago.setDiasd((Integer) value); break;
                        case 7: pago.setMediosd((Integer) value); break;
                        case 8: pago.setBonp((Double) value); break;
                        case 9: pago.setBong((Double) value); break;
                        case 10: pago.setModValue((Double) value); break;
                        case 11: pago.setRet((Double) value); break;
                    }

                    // Recalcular el pago total y actualizar la vista
                    pago.setPagtot(calcularPagoTotal(pago));
                    fireTableCellUpdated(rowIndex, columnIndex);
                    fireTableCellUpdated(rowIndex, 12); // Actualizar la columna de Pago Total

                    // Actualizar el modelo de la segunda tabla
                    actualizarTotales();
                } catch (ClassCastException e) {
                    JOptionPane.showMessageDialog(PagosView.this, "Error al actualizar el valor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public String getColumnName(int column) {
                return columnNames[column];
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0 || columnIndex == 1 || columnIndex == 2 || columnIndex == 3) {
                    return String.class;
                } else if (columnIndex == 4 || columnIndex == 8 || columnIndex == 9 || columnIndex == 10 || columnIndex == 11) {
                    return Double.class;
                } else {
                    return Integer.class;
                }
            }
        };

        // Configuración de la primera tabla
        table = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                if (row % 2 == 0) {
                    comp.setBackground(Color.decode("#D3E0AD"));
                } else {
                    comp.setBackground(Color.decode("#C8EDD3"));
                }
                if (column == 0) {
                    comp.setBackground(Color.decode("#ddbd85")); // Color de fondo para la primera columna
                    ((JLabel) comp).setFont(((JLabel) comp).getFont().deriveFont(Font.BOLD)); // Texto en negrita
                }
                return comp;
            }
        };

        // Renderizadores de la primera tabla
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 4; i <= 13; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Ajuste de diseño de la primera tabla
        table.setRowHeight(20);
        table.getColumnModel().getColumn(0).setPreferredWidth(220); // Nombre
        // (ajustar más columnas según se necesite)

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(tableHeader.getFont().deriveFont(Font.BOLD));
        tableHeader.setBackground(Color.decode("#DBAA86"));

        // Modelo de la segunda tabla
        tableModel2 = new AbstractTableModel() {
            private final String[] tableTitles = {"Zona", "Total a pagar"};

            @Override
            public int getRowCount() {
                return pagosDestinos.size() + 1;
            }

            @Override
            public int getColumnCount() {
                return tableTitles.length;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                if (rowIndex == pagosDestinos.size()) {
                    switch (columnIndex) {
                        case 0:
                            return "Pago total";
                        case 1:
                            return pagoTotal.getTotal();
                        default:
                            return null;
                    }
                }
                PagoDestino pagoDestino = pagosDestinos.get(rowIndex);
                switch (columnIndex) {
                    case 0:
                        return pagoDestino.getDestino().getNombre();
                    case 1:
                        return pagoDestino.sumaPagos();
                    default:
                        return null;
                }
            }

            @Override
            public String getColumnName(int column) {
                return tableTitles[column];
            }
        };

        // Configuración de la segunda tabla
        secondTable = new JTable(tableModel2);
        secondTable.setRowHeight(20);
        JScrollPane secondScrollPane = new JScrollPane(secondTable);

        // Crear JSplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                new JScrollPane(table), // Primera tabla en la parte superior
                secondScrollPane); // Segunda tabla en la parte inferior

        // Ajustar la proporción
        splitPane.setResizeWeight(0.7); // Ajustar este valor para cambiar el tamaño relativo (70% / 30%)

        // Añadir el JSplitPane al JFrame
        add(splitPane, BorderLayout.CENTER);

        // Botón Aceptar
        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(e -> {
            try {
                pagoJpaController.create(pagos);
                for (PagoDestino pagoDestino : pagosDestinos) {
                    pdctl.create(pagoDestino);
                }

                // Guardar el PagoTotal
                ptotal.create(pagoTotal);
                pagoTotal.getPagodestinos().get(1).getPagos().get(0).getEmpleado().getNombre();
                ExportarController export = new ExportarController(pagos);
                export.exportarAExcel();
                JOptionPane.showMessageDialog(PagosView.this, "Pagos guardados exitosamente.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(PagosView.this, "Error al guardar los pagos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Panel inferior con el botón
        JPanel panelSur = new JPanel();
        panelSur.add(btnAceptar);
        add(panelSur, BorderLayout.PAGE_END);
    }

    // Método para calcular el pago total
    private double calcularPagoTotal(Pago pago) {
        double salarioDiario = pago.getEmpleado().getSalario() / 30;
        return salarioDiario * (pago.getDiast() + pago.getDiasd() + 0.5 * pago.getMediosd()) + pago.getBonp() + pago.getBong() - pago.getRet();
    }

    private void actualizarTotales() {
        // Recalcular los totales de cada PagoDestino
        for (PagoDestino pagoDestino : pagosDestinos) {
            pagoDestino.setTotal(pagoDestino.sumaPagos());
        }

        // Calcular el total general sumando todos los totales de PagoDestino
        double totalGeneral = pagosDestinos.stream().mapToDouble(PagoDestino::getTotal).sum();
        pagoTotal.setTotal(totalGeneral);

        // Actualizar el modelo de la segunda tabla
        ((AbstractTableModel) secondTable.getModel()).fireTableDataChanged();
    }
}