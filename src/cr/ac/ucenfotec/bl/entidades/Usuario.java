package cr.ac.ucenfotec.bl.entidades;

import cr.ac.ucenfotec.bl.excepciones.SaldoInsuficienteException;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Usuario extends Cuenta {

    //Constantes

    private static final int EDAD_MINIMA = 18;
    private static final List<String> NACIONALIDADES_PERMITIDAS = Arrays.asList(
            "Costa Rica", "Nicaragua", "Panama", "Mexico", "Colombia",
            "Estados Unidos", "España", "Argentina", "Chile", "Peru");

    //Atributos

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
        super(correoElectronico, nombreUsuario, contrasenia);
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
        // Si es nulo o está vacío, asignamos la imagen predeterminada
        if (avatar == null || avatar.trim().isEmpty()) {
            this.avatar = "imagenes/avatar_predeterminado.png"; // Ruta o nombre del archivo por defecto
        } else {
            this.avatar = avatar;
        }
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

    public ArrayList<Cancion> getColeccionCanciones() {
        return coleccionCanciones;
    }

    //Utilidades

    @Override
    public String toString(){
        return  "\nCorreo Electrónico: " + this.correoElectronico +
                "\nNombre de usuario: " + this.nombreUsuario +
                "\nNombre Completo: " + nombreCompleto +
                "\nFecha de nacimiento: " + fechaNacimiento +
                "\nNacionalidad: " + nacionalidad +
                "\nCédula: " + cedula +
                "\nAvatar: " + avatar +
                "\nSaldo: $" + saldo +
                "\nListas de reproducción: " + listasReproduccion.size() + " listas" +
                "\nColección de canciones: " + coleccionCanciones.size() + " canciones";
    }


    public boolean equals(Usuario usuario){
        if (usuario == null) {
            return false;
        }
        return this.correoElectronico.equals(usuario.correoElectronico) && this.nombreUsuario.equals(usuario.nombreUsuario);
    }

    @Override
    public String getRol() {
        return "Usuario";
    }

    //Validaciones

    public static boolean esMayorDeEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null || fechaNacimiento.isAfter(LocalDate.now())) {
            return false;
        }
        return Period.between(fechaNacimiento, LocalDate.now()).getYears() >= EDAD_MINIMA;
    }

    //Operaciones

    public void agregarCancion(Cancion cancion) throws SaldoInsuficienteException {
        if (cancion == null) {
            throw new IllegalArgumentException("La canción no puede ser nula.");
        }
        if (esDuenoDe(cancion)) {
            throw new IllegalArgumentException("Ya eres dueño de esta canción.");
        }
        if (saldo < cancion.getPrecio()) {
            throw new SaldoInsuficienteException("No tienes saldo suficiente para comprar '" + cancion.getTitulo() + "'. Precio: $" + cancion.getPrecio() + ", Tu saldo: $" + saldo);
        }
        saldo = redondearDosDecimales(saldo - cancion.getPrecio());
        coleccionCanciones.add(cancion);
        cancion.incrementarVecesComprada();
    }

    /**
     * Agrega una canción a una lista de reproducción específica del usuario.
     * Valida que el usuario haya comprado la canción previamente.
     */
    public boolean agregarCancionALista(String nombreLista, Cancion cancion) {
        if (cancion == null) {
            return false;
        }

        if (!esDuenoDe(cancion)) {
            return false; // No está en su colección, no puede agregarla a la lista
        }

        ListaReproduccion lista = buscarLista(nombreLista);
        if (lista == null) {
            return false;
        }

        return lista.agregarCancion(cancion);
    }

    public boolean calificarCancion(Cancion c, double calificacion) {
        if (c == null || calificacion < 0.0 || calificacion > 5.0) {
            return false;
        }
        if (!esDuenoDe(c)) {
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



    //Auxiliares

    public boolean esDuenoDe(Cancion cancion) {
        for (Cancion propia : coleccionCanciones) {
            if (propia.equals(cancion)) {
                return true;
            }
        }
        return false;
    }

    private static double redondearDosDecimales(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }

}