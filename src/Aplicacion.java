import java.util.ArrayList;
import java.util.Objects;

public class Aplicacion {

    //Atributos

    private ArrayList<Cancion> catalogoCanciones;
    private ArrayList<Usuario> listaUsuarios;
    private ArrayList<Administrador> listaAdministradores;

    //Métodos

    //Constructores

    //Constructor completo


    public Aplicacion() {
        catalogoCanciones = new ArrayList<>();
        listaUsuarios = new ArrayList<>();
        listaAdministradores = new ArrayList<>();
    }

    //Setters y getters

    public ArrayList<Cancion> getCatalogoCanciones() {
        return catalogoCanciones;
    }

    private void setCatalogoCanciones(ArrayList<Cancion> catalogoCanciones) {
        this.catalogoCanciones = catalogoCanciones;
    }

    public ArrayList<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    private void setListaUsuarios(ArrayList<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public ArrayList<Administrador> getListaAdministradores() {
        return listaAdministradores;
    }

    private void setListaAdministradores(ArrayList<Administrador> listaAdministradores) {
        this.listaAdministradores = listaAdministradores;
    }
    //Utilidades

    @Override
    public String toString() {
        return "\nCatálogo de Canciones: " + catalogoCanciones +
                "\nLista de Usuarios: " + listaUsuarios +
                "\nLista de Administradores: " + listaAdministradores;
    }

    //Operaciones

    public boolean registrarCancion(Cancion cancionPorRegistrar){
        return catalogoCanciones.add(cancionPorRegistrar);
    }

    public boolean registrarAdministrador(Administrador administradorPorRegistrar){
        return listaAdministradores.add(administradorPorRegistrar);
    }

    public boolean registrarUsuario (Usuario usuarioPorRegistrar){
        return listaUsuarios.add(usuarioPorRegistrar);
    }

    public void mostrarCatalogoCanciones(){
        System.out.println("--- CATÁLOGO DE CANCIONES ---");
        for(Cancion cancion : catalogoCanciones){
            System.out.println(cancion);
        }
    }

    public void mostrarUsuarios(){
        System.out.println("--- LISTA DE USUARIOS REGISTRADOS ---");
        for(Usuario usuario : listaUsuarios){
            System.out.println(usuario);
        }
    }

    public void mostrarAdministradores(){
        System.out.println("--- LISTA DE ADMINISTRADORES ---");
        for(Administrador administrador : listaAdministradores){
            System.out.println(administrador);
        }
    }

}
