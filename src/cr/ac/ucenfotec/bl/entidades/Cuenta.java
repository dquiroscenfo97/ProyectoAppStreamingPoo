package cr.ac.ucenfotec.bl.entidades;

public  abstract class Cuenta {
    protected String correoElectronico;
    protected String nombreUsuario;
    protected String contrasenia;

    //Constructor

    protected Cuenta(String correoElectronico, String nombreUsuario, String contrasenia){
        setNombreUsuario(nombreUsuario);
        setCorreoElectronico(correoElectronico);
        setContrasenia(contrasenia);
    }

    //Métodos de consulta y mutadores

    public String getCorreoElectronico(){
        return correoElectronico;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public boolean autenticar(String contraseniaIngresada) {
        if (contraseniaIngresada == null) {
            return false;
        }
        return this.contrasenia.equals(contraseniaIngresada);
    }

    protected void setContrasenia(String contrasenia) {
        if (!esContraseniaValida(contrasenia)) {
            throw new IllegalArgumentException("La contrasenia debe tener de 8 a 12 caracteres, con mayuscula, minuscula, numero y caracter especial");
        }
        this.contrasenia = contrasenia;
    }

    public void setCorreoElectronico(String correoElectronico) {
        if (correoElectronico == null || correoElectronico.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo electronico no puede ser nulo ni vacio");
        }
        this.correoElectronico = correoElectronico;
    }

    public void setNombreUsuario(String nombreUsuario) {
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario no puede ser nulo ni vacio");
        }
        this.nombreUsuario = nombreUsuario;
    }
    //Utilidades

    public static boolean esContraseniaValida(String contrasenia) {
        if (contrasenia == null) {
            return false;
        }
        return contrasenia.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,12}$");
    }

    //Procesos

    public boolean modificarContrasenia(String actual, String nueva, String confirmacion) {
        if (actual == null || nueva == null || confirmacion == null) {
            return false;
        }
        if (!actual.equals(this.contrasenia)) {
            return false;
        }
        if (!nueva.equals(confirmacion) || nueva.equals(this.contrasenia)) {
            return false;
        }
        if (!esContraseniaValida(nueva)) {
            return false;
        }
        this.setContrasenia(nueva);
        return true;
    }

    //Abstractos

    public abstract String getRol();
}
