public class Administrador {
    //Atributos
    private String correoElectronico;
    private String nombreUsuario;
    private String contrasenia;

    //Métodos

    //Constructores

    //Constructor completo

    public Administrador(String correoElectronico, String nombreUsuario, String contrasenia) {
        this.correoElectronico = correoElectronico;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
    }

    //Setters y getters


    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    private String getContrasenia() { //Este getter debe de eliminarse por seguridad
        return contrasenia;
    }

    private void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    //Utilidades
    @Override
    public String toString() {
        return "\nCorreo Eléctronico: " + correoElectronico +
                "\nNombre de Usuario: " + nombreUsuario;
    }

    public boolean equals(Administrador admin){
        return this.correoElectronico.equals(admin.correoElectronico) && this.nombreUsuario.equals(admin.nombreUsuario);
    }
}
