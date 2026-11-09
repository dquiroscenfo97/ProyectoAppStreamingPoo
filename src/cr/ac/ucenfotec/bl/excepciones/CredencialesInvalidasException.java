package cr.ac.ucenfotec.bl.excepciones;

public class CredencialesInvalidasException extends Exception {

    public CredencialesInvalidasException() {
        super("El nombre de usuario o la contraseña son incorrectos.");
    }

    public CredencialesInvalidasException(String message) {
        super(message);
    }

    public CredencialesInvalidasException(String message, Throwable cause) {
        super(message, cause);
    }
}