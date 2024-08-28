package vista;

import controller.ExportarController;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;
import javax.swing.table.TableCellRenderer;
import model.Pago;
import controller.PagoJpaController;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class PagosView extends JFrame {
    private final JTable table;
    private final List<Pago> pagos;
    private final PagoJpaController pagoJpaController;

    public PagosView(List<Pago> pagos, PagoJpaController pagoJpaController) {
        this.pagos = pagos;
        this.pagoJpaController = pagoJpaController;

        setTitle("Pagos");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        AbstractTableModel tableModel = new AbstractTableModel() {
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
                    case 1: return pago.getEmpleado().getZona();
                    case 2: return pago.getEmpleado().getPuesto();
                    case 3: return pago.getEmpleado().getHorario();
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
                return columnIndex >= 5 && columnIndex <= 11;
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

                    pago.setPagtot(calcularPagoTotal(pago));

                    fireTableCellUpdated(rowIndex, columnIndex);
                    fireTableCellUpdated(rowIndex, 12); // Actualizar la columna de Pago Total
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
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 4; i <= 13; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        DefaultTableCellRenderer firstColumnRenderer = new DefaultTableCellRenderer();        
        firstColumnRenderer.setBackground(Color.decode("#ddbd85")); // Color de fondo para la primera columna
        firstColumnRenderer.setFont(firstColumnRenderer.getFont().deriveFont(Font.BOLD)); // Texto en negrita
        table.getColumnModel().getColumn(0).setCellRenderer(firstColumnRenderer);


        // Ajustar el tamaño de las filas y columnas
        table.setRowHeight(20); // Altura de las filas
        table.getColumnModel().getColumn(0).setPreferredWidth(220); // Ancho de la columna "Nombre"
        table.getColumnModel().getColumn(1).setPreferredWidth(50); // Ancho de la columna "Zona"
        table.getColumnModel().getColumn(2).setPreferredWidth(75); // Ancho de la columna "Puesto"
        table.getColumnModel().getColumn(3).setPreferredWidth(75); // Ancho de la columna "Horario"
        table.getColumnModel().getColumn(4).setPreferredWidth(25); // Ancho de la columna "Salario"
        table.getColumnModel().getColumn(5).setPreferredWidth(45); // Ancho de la columna "Salario"
        table.getColumnModel().getColumn(6).setPreferredWidth(45); // Ancho de la columna "Salario"
        table.getColumnModel().getColumn(7).setPreferredWidth(30); // Ancho de la columna "Salario"
        table.getColumnModel().getColumn(8).setPreferredWidth(30); // Ancho de la columna "Salario"
        table.getColumnModel().getColumn(9).setPreferredWidth(30); // Ancho de la columna "Salario"
        table.getColumnModel().getColumn(10).setPreferredWidth(30); // Ancho de la columna "Salario"
        table.getColumnModel().getColumn(11).setPreferredWidth(3); // Ancho de la columna "Salario"
        table.getColumnModel().getColumn(12).setPreferredWidth(30); // Ancho de la columna "Salario"
        table.getColumnModel().getColumn(13).setPreferredWidth(30); // Ancho de la columna "Salario"
        
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(tableHeader.getFont().deriveFont(Font.BOLD));
        //tableHeader.setForeground(Color.decode("#DBAA86")); // Color del texto del encabezado
        tableHeader.setBackground(Color.decode("#DBAA86")); // Color de fondo del encabezado


        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(e -> {
            try {
                pagoJpaController.create(pagos);
                ExportarController export = new ExportarController(pagos);
                export.exportarAExcel();
                JOptionPane.showMessageDialog(PagosView.this, "Pagos guardados exitosamente.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(PagosView.this, "Error al guardar los pagos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

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
             - pago.getRet() 
             + pago.getModValue();
    }
}
