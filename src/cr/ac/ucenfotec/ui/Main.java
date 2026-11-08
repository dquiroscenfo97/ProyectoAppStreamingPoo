package cr.ac.ucenfotec.ui;
import cr.ac.ucenfotec.bl.controlador.Aplicacion;

public class Main {
    public static void main(String[] args) {
        Aplicacion aplicacion = new Aplicacion();
        VistaCLI vista = new VistaCLI(aplicacion);
        vista.iniciar();
    }
}