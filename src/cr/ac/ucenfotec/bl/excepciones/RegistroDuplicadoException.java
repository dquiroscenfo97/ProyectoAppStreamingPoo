package cr.ac.ucenfotec.bl.excepciones;

public class RegistroDuplicadoException extends Exception {

    public RegistroDuplicadoException() {
        super("Ya existe un registro con los mismos datos.");
    }

    public RegistroDuplicadoException(String message) {
        super(message);
    }

    public RegistroDuplicadoException(String message, Throwable cause) {
        super(message, cause);
    }
}