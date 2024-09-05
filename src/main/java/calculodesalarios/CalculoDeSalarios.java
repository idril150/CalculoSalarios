package calculodesalarios;
import controller.ChecadorJpaController;
import controller.VistasController;
import java.util.List;


public class CalculoDeSalarios {

    public static void main(String[] args) {
        VistasController view = new VistasController();
        view.iniciar(); 
       // ChecadorJpaController chctrl = new ChecadorJpaController();
       // int dias = chctrl.obtenerDiasTrabajados(101, 20240816, 20240828);
       // System.out.println(dias);
    }
}
