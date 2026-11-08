package cr.ac.ucenfotec.bl.entidades;

public class Administrador extends Cuenta {
    //Métodos

    //Constructores

    //Constructor completo

    public Administrador(String correoElectronico, String nombreUsuario, String contrasenia) {
        super(correoElectronico, nombreUsuario, contrasenia);
    }

    //Utilidades
    @Override
    public String toString() {
        return "\nCorreo Eléctronico: " + correoElectronico +
                "\nNombre de cr.ac.ucenfotec.bl.entidades.Usuario: " + nombreUsuario;
    }

    public boolean equals(Administrador admin){
        if (admin == null) {
            return false;
        }
        return this.correoElectronico.equals(admin.correoElectronico) && this.nombreUsuario.equals(admin.nombreUsuario);
    }

    @Override
    public String getRol() {
        return "cr.ac.ucenfotec.bl.entidades.Administrador";
    }


}
