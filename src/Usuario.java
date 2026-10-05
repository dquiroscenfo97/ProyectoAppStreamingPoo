import java.time.LocalDate;
import java.util.ArrayList;

public class Usuario {

    //Atributos

    private String correoElectronico;
    private String nombreUsuario;
    private String contrasenia;
    private String nombreCompleto;
    private LocalDate fechaNacimiento;
    private String nacionalidad;
    private String cedula;
    private String avatar;
    private double saldo;
    private ArrayList<ListaReproduccion> listasReproduccion;
    private ArrayList<Cancion> coleccionCanciones;

    //Métodos

    //Constructores

    //Constructor completo


    public Usuario(String correoElectronico, String nombreUsuario, String contrasenia, String nombreCompleto, LocalDate fechaNacimiento, String nacionalidad, String cedula, String avatar, double saldo) {
        this.correoElectronico = correoElectronico;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.cedula = cedula;
        this.avatar = avatar;
        this.saldo = saldo;
        this.listasReproduccion = new ArrayList<>();
        this.coleccionCanciones = new ArrayList<>();
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

    private String getContrasenia() {
        return contrasenia;
    }

    private void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public double getSaldo() {
        return saldo;
    }

    private void setSaldo(double saldo) { //Este metodo debe de cambiarse a privado
        this.saldo = saldo;
    }

    public ArrayList<ListaReproduccion> getListasReproduccion() {
        return listasReproduccion;
    }

    private void setListasReproduccion(ArrayList<ListaReproduccion> listasReproduccion) {
        this.listasReproduccion = listasReproduccion;
    }

    public ArrayList<Cancion> getColeccionCanciones() { //Este metodo debe cambiarse a privado
        return coleccionCanciones;
    }

    private void setColeccionCanciones(ArrayList<Cancion> coleccionCanciones) {
        this.coleccionCanciones = coleccionCanciones;
    }

    //Utilidades
    @Override
    public String toString(){
       return  "\nCorreo Electronico: " + correoElectronico +
               "\nNombre de usuario: " + nombreUsuario +
               "\nNombre Completo: " + nombreCompleto +
               "\nFecha de nacimiento: " + fechaNacimiento +
               "\nNacionalidad: " + nacionalidad +
               "\nCédula: " + cedula +
               "\nAvatar: " + avatar +
               "\nSaldo: " + saldo +
               "\nListas de reproduccion: " + listasReproduccion +
               "\nColecciones: " + coleccionCanciones;
    }

    public boolean equals(Usuario usuario){
        return this.correoElectronico.equals(usuario.correoElectronico) && this.nombreUsuario.equals(usuario.nombreUsuario);
    }

    //Operaciones
    public ListaReproduccion crearLista(String nombreLista){
        ListaReproduccion nuevaLista = new ListaReproduccion(nombreLista, LocalDate.now(), 0.0);
        listasReproduccion.add(nuevaLista);
        return nuevaLista;
    }

    public void mostrarListasReproduccion(){
        System.out.println("--- LISTAS DE REPRODUCCION DE " + nombreUsuario.toUpperCase() + "---");
        if(listasReproduccion.isEmpty()){
            System.out.println("No tienes listas de reproduccion creadas");
        }else{
            for(ListaReproduccion lista : listasReproduccion){
                System.out.println(lista);
            }
        }
    }

    public void mostrarColeccionCanciones(){
        System.out.println("--- COLECCION DE CANCIONES DE " + nombreUsuario.toUpperCase() + "---");
        if(coleccionCanciones.isEmpty()){
            System.out.println("No has comprado ninguna canción aún");
        }else{
            for(Cancion cancion : coleccionCanciones){
                System.out.println(cancion);
            }
        }
    }
}
