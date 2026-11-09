package cr.ac.ucenfotec.bl.excepciones;

public class SaldoInsuficienteException extends Exception {

    public SaldoInsuficienteException() {
        super("El saldo es insuficiente para realizar esta operación.");
    }

    public SaldoInsuficienteException(String message) {
        super(message);
    }

    public SaldoInsuficienteException(String message, Throwable cause) {
        super(message, cause);
    }
}