import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class ListaReproduccion {

    //Atributos

    private String nombre;
    private LocalDate fechaCreacion;
    private double calificacion;
    private ArrayList<Cancion> canciones;

    //Métodos

    //Contructores

    //Constructor completo

    public ListaReproduccion(String nombre, LocalDate fechaCreacion, double calificacion) {
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.calificacion = calificacion;
        canciones = new ArrayList<>();
    }

    //Setters y getters


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public double getCalificacion() {
        return calificacion;
    }

    private void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public ArrayList<Cancion> getCanciones() {
        return canciones;
    }

    private void setCanciones(ArrayList<Cancion> canciones) {
        this.canciones = canciones;
    }

    //Utilidades
    @Override
    public String toString() {
        return "\nNombre: " + nombre +
                "\nFecha de Creación: " + fechaCreacion +
                "\nCalificación: " + calificacion +
                "\nCanciones: " + canciones;
    }

    public boolean equals(ListaReproduccion lista){
        return nombre.equals(lista.nombre);
    }
}
