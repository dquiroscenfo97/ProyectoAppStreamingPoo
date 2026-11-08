package cr.ac.ucenfotec.bl.entidades;

import java.time.LocalDate;
import java.util.ArrayList;

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
        setCalificacion(calificacion);
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
        if (calificacion >= 0.0 && calificacion <= 5.0) {
            this.calificacion = Math.round(calificacion * 10.0) / 10.0;
        } else {
            this.calificacion = 0.0;
        }
    }

    public ArrayList<Cancion> getCanciones() {
        return canciones;
    }

    public int getCantidadCanciones(){
        return canciones.size();
    }

    //Utilidades
    @Override
    public String toString() {
        return "\nNombre: " + nombre +
                "\nFecha de Creación: " + fechaCreacion +
                "\nCalificación: " + calificacion + "/5.0" +
                "\nCantidad de canciones: " + getCantidadCanciones();
    }

    public boolean equals(ListaReproduccion lista) {
        if (lista == null) {
            return false;
        }
        return this.nombre.equalsIgnoreCase(lista.nombre);
    }

    public boolean agregarCancion (Cancion cancionPorAgregar){
        if(cancionPorAgregar == null){
            return false;
        }

        if(canciones.contains(cancionPorAgregar)){
            return false;
        }

        canciones.add(cancionPorAgregar);

        calcularCalificacion();

        return true;
    }

    private void calcularCalificacion(){
        if(canciones.isEmpty()){
            setCalificacion(0.0);
            return;
        }

        double sumaTotal = 0.0;

        for(Cancion cancion : canciones){
            sumaTotal = sumaTotal + cancion.getCalificacion();
        }

        double promedio = sumaTotal / canciones.size();

        setCalificacion(promedio);
    }

    public boolean eliminarCancion(Cancion cancion){
        if(cancion == null){
            return false;
        }

        boolean fueEliminada = canciones.remove(cancion);

        if(fueEliminada){
            calcularCalificacion();
        }

        return fueEliminada;
    }

    public Cancion buscarCancionEnLista(String titulo){
        if(titulo == null){
            return null;
        }

        for(Cancion cancion : canciones){
            if(cancion.getTitulo().equalsIgnoreCase(titulo)){
                return cancion;
            }
        }
        return null;
    }
}
