import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Usuario {

    //Constantes

    private static final int EDAD_MINIMA = 18;
    private static final List<String> NACIONALIDADES_PERMITIDAS = Arrays.asList(
            "Costa Rica", "Nicaragua", "Panama", "Mexico", "Colombia",
            "Estados Unidos", "España", "Argentina", "Chile", "Peru");

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
        setCorreoElectronico(correoElectronico);
        setNombreUsuario(nombreUsuario);
        setContrasenia(contrasenia);
        setNombreCompleto(nombreCompleto);
        setFechaNacimiento(fechaNacimiento);
        setNacionalidad(nacionalidad);
        setCedula(cedula);
        setAvatar(avatar);
        setSaldo(saldo);
        this.listasReproduccion = new ArrayList<>();
        this.coleccionCanciones = new ArrayList<>();
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

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre completo no puede ser nulo ni vacio");
        }
        this.nombreCompleto = nombreCompleto;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        if (!esMayorDeEdad(fechaNacimiento)) {
            throw new IllegalArgumentException("El usuario debe ser mayor de 18 años");
        }
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        if (nacionalidad == null || !NACIONALIDADES_PERMITIDAS.contains(nacionalidad)) {
            throw new IllegalArgumentException("La nacionalidad no esta en la lista de territorios permitidos");
        }
        this.nacionalidad = nacionalidad;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) {
            throw new IllegalArgumentException("La cedula no puede ser nula ni vacia");
        }
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

    private void setSaldo(double saldo) {
        if (saldo < 0.0) {
            throw new IllegalArgumentException("El saldo no puede ser negativo");
        }
        this.saldo = redondearDosDecimales(saldo);
    }

    public ArrayList<ListaReproduccion> getListasReproduccion() {
        return listasReproduccion;
    }

    private void setListasReproduccion(ArrayList<ListaReproduccion> listasReproduccion) {
        if (listasReproduccion == null) {
            throw new IllegalArgumentException("Las listas de reproduccion no pueden ser nulas");
        }
        this.listasReproduccion = listasReproduccion;
    }

    public ArrayList<Cancion> getColeccionCanciones() {
        return coleccionCanciones;
    }

    private void setColeccionCanciones(ArrayList<Cancion> coleccionCanciones) {
        if (coleccionCanciones == null) {
            throw new IllegalArgumentException("La coleccion de canciones no puede ser nula");
        }
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
        if (usuario == null) {
            return false;
        }
        return this.correoElectronico.equals(usuario.correoElectronico) && this.nombreUsuario.equals(usuario.nombreUsuario);
    }

    //Validaciones

    public static boolean esContraseniaValida(String contrasenia) {
        if (contrasenia == null) {
            return false;
        }
        return contrasenia.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,12}$");
    }

    public static boolean esMayorDeEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null || fechaNacimiento.isAfter(LocalDate.now())) {
            return false;
        }
        return Period.between(fechaNacimiento, LocalDate.now()).getYears() >= EDAD_MINIMA;
    }

    //Operaciones

    public boolean agregarCancion(Cancion c) {
        if (c == null || poseeCancion(c)) {
            return false;
        }
        if (saldo < c.getPrecio()) {
            return false;
        }
        saldo = redondearDosDecimales(saldo - c.getPrecio());
        coleccionCanciones.add(c);
        c.incrementarVecesComprada();
        return true;
    }

    public boolean calificarCancion(Cancion c, double calificacion) {
        if (c == null || calificacion < 0.0 || calificacion > 5.0) {
            return false;
        }
        if (!poseeCancion(c)) {
            return false;
        }
        c.agregarCalificacion(calificacion);
        return true;
    }

    public boolean recargarSaldo(double monto) {
        if (monto <= 0.0) {
            return false;
        }
        saldo = redondearDosDecimales(saldo + monto);
        return true;
    }

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

    public ListaReproduccion crearLista(String nombreLista){
        if (nombreLista == null || nombreLista.trim().isEmpty() || buscarLista(nombreLista) != null) {
            return null;
        }
        ListaReproduccion nuevaLista = new ListaReproduccion(nombreLista, LocalDate.now(), 0.0);
        listasReproduccion.add(nuevaLista);
        return nuevaLista;
    }

    public boolean eliminarLista(String nombre) {
        ListaReproduccion lista = buscarLista(nombre);
        if (lista == null) {
            return false;
        }
        return listasReproduccion.remove(lista);
    }

    public ListaReproduccion buscarLista(String nombre) {
        if (nombre == null) {
            return null;
        }
        for (ListaReproduccion lista : listasReproduccion) {
            if (lista.getNombre().equalsIgnoreCase(nombre)) {
                return lista;
            }
        }
        return null;
    }

    public Cancion buscarCancionEnColeccion(String titulo) {
        if (titulo == null) {
            return null;
        }
        for (Cancion cancion : coleccionCanciones) {
            if (cancion.getTitulo().equalsIgnoreCase(titulo)) {
                return cancion;
            }
        }
        return null;
    }

    public void mostrarListasReproduccion(){
        System.out.println("--- LISTAS DE REPRODUCCION DE " + nombreUsuario.toUpperCase() + " ---");
        if(listasReproduccion.isEmpty()){
            System.out.println("No tienes listas de reproduccion creadas");
        }else{
            for(ListaReproduccion lista : listasReproduccion){
                System.out.println(lista);
            }
        }
    }

    public void mostrarColeccionCanciones(){
        System.out.println("--- COLECCION DE CANCIONES DE " + nombreUsuario.toUpperCase() + " ---");
        if(coleccionCanciones.isEmpty()){
            System.out.println("No has comprado ninguna canción aún");
        }else{
            for(Cancion cancion : coleccionCanciones){
                System.out.println(cancion);
            }
        }
    }

    //Auxiliares

    private boolean poseeCancion(Cancion c) {
        for (Cancion propia : coleccionCanciones) {
            if (propia.equals(c)) {
                return true;
            }
        }
        return false;
    }

    private static double redondearDosDecimales(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}