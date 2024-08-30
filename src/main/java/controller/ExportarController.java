package controller;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Cells;
import com.aspose.cells.Cell;
import com.aspose.cells.FileFormatType;
import model.Pago;
import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
public class ExportarController {
    private Workbook workbook;
    private Worksheet worksheet;
    private List<Pago> pag;
    public ExportarController() {
    }
    public ExportarController(List<Pago> pag) {
        this.pag = pag;
        this.workbook = new Workbook(); // Crea un nuevo libro de trabajo
        this.worksheet = workbook.getWorksheets().get(0); // Obtén la primera hoja del libro de trabajo
        configurarHoja();
    }
    private void configurarHoja() {
        Cells cells = worksheet.getCells();
        // Títulos de las columnas
        String[] columnNames = {
            "Nombre", "Zona", "Puesto", "Horario", "Salario", "Días Trabajados", "Días de Descanso",
            "Medios Días", "Bono personal", "Bono de meta", "MOD", "Retenciones", "Pago total", "Fecha de pago"
        };
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = cells.get(0, i);
            cell.setValue(columnNames[i]);
        }

        // Rellenar la hoja con datos
        for (int row = 0; row < pag.size(); row++) {
            Pago pago = pag.get(row);
            cells.get(row + 1, 0).setValue(pago.getEmpleado().getNombre());
            cells.get(row + 1, 1).setValue(pago.getEmpleado().getDestino().getNombre());
            cells.get(row + 1, 2).setValue(pago.getEmpleado().getPuesto());
            cells.get(row + 1, 3).setValue(pago.getEmpleado().getHorario());
            cells.get(row + 1, 4).setValue(pago.getEmpleado().getSalario());
            cells.get(row + 1, 5).setValue(pago.getDiast());
            cells.get(row + 1, 6).setValue(pago.getDiasd());
            cells.get(row + 1, 7).setValue(pago.getMediosd());
            cells.get(row + 1, 8).setValue(pago.getBonp());
            cells.get(row + 1, 9).setValue(pago.getBong());
            cells.get(row + 1, 10).setValue(pago.getModValue());
            cells.get(row + 1, 11).setValue(pago.getRet());
            cells.get(row + 1, 12).setValue(pago.getPagtot());
            cells.get(row + 1, 13).setValue(pago.getFech());
        }
    }

    public void exportarAExcel() throws Exception {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar ubicación para guardar el archivo");
        CalculoController cal = new CalculoController();
        fileChooser.setSelectedFile(new java.io.File("Nominas"+cal.obtenerFechaHoy()+".xlsx")); // Nombre predeterminado del archivo

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();
            try (FileOutputStream fileOut = new FileOutputStream(fileToSave)) {
                workbook.save(fileOut, FileFormatType.XLSX); // Guarda el libro de trabajo en un archivo nuevo
                JOptionPane.showMessageDialog(null, "Archivo guardado exitosamente en: " + fileToSave.getAbsolutePath());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al guardar el archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    
}
