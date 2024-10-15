package calculodesalarios;
import controller.VistasController;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


public class CalculoDeSalarios {

    
    public static void main(String[] args) {
        // Mostrar ventana de "Iniciando..."
       // Mostrar mensaje "Iniciando..." en un hilo separado para que la interfaz no se congele
        JOptionPane pane = new JOptionPane("Iniciando...", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        final JDialog dialog = pane.createDialog("Cargando");
        dialog.setModal(false); // Permitir que el hilo principal continúe trabajando mientras se muestra el mensaje

        // Iniciar un hilo para mostrar el diálogo
        Thread loadingThread = new Thread(() -> dialog.setVisible(true));
        loadingThread.start();

        // Iniciar otro hilo para la inicialización de la vista
        new Thread(() -> {
            try {
                // Simular un pequeño retraso para asegurar que el mensaje se muestre (opcional)
                Thread.sleep(500);
                
                // Instanciar y iniciar VistasController
                VistasController view = new VistasController();
                view.iniciar();
            } catch (InterruptedException e) {
            } finally {
                // Cerrar el diálogo después de iniciar la vista
                SwingUtilities.invokeLater(dialog::dispose);
            }
        }).start();
    }
}
