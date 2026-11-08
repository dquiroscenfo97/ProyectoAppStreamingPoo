package cr.ac.ucenfotec.bl.entidades;

import java.util.ArrayList;

public class ColaReproduccion {
    //Atributos
    private ArrayList<Cancion> colaDeCanciones;

    // Constructor
    public ColaReproduccion(){
        this.colaDeCanciones = new ArrayList<Cancion>();
    }


    public boolean agregarCancion(Cancion cancion){
        if(cancion == null){
            return false;
        }
        colaDeCanciones.addLast(cancion);
        return true;
    }

    public Cancion eliminarSiguiente(){
        if(estaVacia()){
            return null;
        }
        return colaDeCanciones.removeFirst();
    }

    public Cancion peek(){
        if(estaVacia()){
            return null;
        }
        return colaDeCanciones.getFirst();
    }

    public void limpiarCola(){
        colaDeCanciones.clear();
    }
    //Utilidades

    public boolean estaVacia(){
        return colaDeCanciones.isEmpty();
    }

}
