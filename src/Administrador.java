public class Administrador {
    //Atributos
    private String correoElectronico;
    private String nombreUsuario;
    private String contrasenia;

    //Métodos

    //Constructores

    //Constructor completo

    public Administrador(String correoElectronico, String nombreUsuario, String contrasenia) {
        setCorreoElectronico(correoElectronico);
        setNombreUsuario(nombreUsuario);
        setContrasenia(contrasenia);
    }

    //Setters y getters


    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        if (correoElectronico == null || correoElectronico.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo electronico no puede ser nulo ni vacio");
        }
        this.correoElectronico = correoElectronico;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario no puede ser nulo ni vacio");
        }
        this.nombreUsuario = nombreUsuario;
    }

    private String getContrasenia() {
        return contrasenia;
    }

    private void setContrasenia(String contrasenia) {
        if (!esContraseniaValida(contrasenia)) {
            throw new IllegalArgumentException("La contrasenia debe tener de 8 a 12 caracteres, con mayuscula, minuscula, numero y caracter especial");
        }
        this.contrasenia = contrasenia;
    }

    //Utilidades
    @Override
    public String toString() {
        return "\nCorreo Eléctronico: " + correoElectronico +
                "\nNombre de Usuario: " + nombreUsuario;
    }

    public boolean equals(Administrador admin){
        if (admin == null) {
            return false;
        }
        return this.correoElectronico.equals(admin.correoElectronico) && this.nombreUsuario.equals(admin.nombreUsuario);
    }

    //Validaciones

    public static boolean esContraseniaValida(String contrasenia) {
        if (contrasenia == null) {
            return false;
        }
        return contrasenia.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,12}$");
    }

    //Operaciones

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
        this.contrasenia = nueva;
        return true;
    }
}
