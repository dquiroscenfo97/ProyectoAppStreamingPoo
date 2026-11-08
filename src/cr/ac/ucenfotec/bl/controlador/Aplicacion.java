package cr.ac.ucenfotec.bl.controlador;

import cr.ac.ucenfotec.bl.entidades.Cuenta;
import cr.ac.ucenfotec.bl.entidades.Usuario;
import cr.ac.ucenfotec.bl.entidades.Administrador;
import cr.ac.ucenfotec.bl.entidades.Cancion;
import cr.ac.ucenfotec.bl.entidades.ListaReproduccion;
import cr.ac.ucenfotec.bl.entidades.ColaReproduccion;

import cr.ac.ucenfotec.bl.entidades.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.time.Period;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Aplicacion {

    //Atributos

    private List<Cancion> catalogoCanciones;
    private List<Usuario> listaUsuarios;
    private List<Administrador> listaAdministradores;
    private ColaReproduccion colaReproduccion;

    //Métodos

    //Constructores

    //Constructor completo


    public Aplicacion() {
        catalogoCanciones = new ArrayList<>();
        listaUsuarios = new ArrayList<>();
        listaAdministradores = new ArrayList<>();
        colaReproduccion = new ColaReproduccion();
    }

    //Setters y getters

    public List<Cancion> getCatalogoCanciones() {
        return catalogoCanciones;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public List<Administrador> getListaAdministradores() {
        return listaAdministradores;
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



    public static int calcularEdad(LocalDate fechaNacimiento){
        if(fechaNacimiento == null){
            return 0; //Proteccion contra null pointer exception
        }

        LocalDate fechaActual = LocalDate.now();

        Period periodo = Period.between(fechaNacimiento, fechaActual);

        return periodo.getYears();
    }

    public boolean hayAdministradorRegistrado () {
        return !listaAdministradores.isEmpty();
    }

    public boolean registrarAdministrador(Administrador administradorPorRegistrar){
        if(administradorPorRegistrar == null){
            return false;
        }

        for(Administrador admin : listaAdministradores){
            if(admin.equals(administradorPorRegistrar)){
                return false;
            }
        }

        // La validación de contraseña ya ocurrió en el constructor del cr.ac.ucenfotec.bl.entidades.Administrador
        listaAdministradores.add(administradorPorRegistrar);
        return true;
    }

    public boolean registrarUsuario(Usuario usuarioPorRegistrar){
        if(usuarioPorRegistrar == null){
            return false;
        }

        int edad = calcularEdad(usuarioPorRegistrar.getFechaNacimiento());
        if(edad < 18){
            return false;
        }

        for(Usuario usuario : listaUsuarios){
            if(usuario.equals(usuarioPorRegistrar)){
                return false;
            }
        }

        // La validación de contraseña ya ocurrió en el constructor del cr.ac.ucenfotec.bl.entidades.Usuario
        usuarioPorRegistrar.recargarSaldo(4.99); // Aplica el bono de bienvenida

        listaUsuarios.add(usuarioPorRegistrar);
        return true;
    }

    public Cancion buscarCancionPorNombre(String nombre){
        if(nombre == null){
            return null;
        }

        for(Cancion cancion : catalogoCanciones){
            if(cancion.getTitulo().equalsIgnoreCase(nombre)){
                return cancion;
            }
        }

        return null;
    }

    public List<Cancion> buscarCancionPorGenero(String genero){
        List<Cancion> resultadosBusquedaGenero = new ArrayList<Cancion>();

        if(genero == null){
            return resultadosBusquedaGenero;
        }

        for(Cancion cancion : catalogoCanciones){
            if(cancion.getGenero().equalsIgnoreCase(genero)){
                resultadosBusquedaGenero.add(cancion);
            }
        }

        return resultadosBusquedaGenero;
    }

    public List<Cancion> buscarCancionPorArtista(String artista){
        List<Cancion> resultadosBusquedaArtista = new ArrayList<Cancion>();

        if(artista == null){
            return resultadosBusquedaArtista;
        }

        for(Cancion cancion : catalogoCanciones){
            if(cancion.getArtista().equalsIgnoreCase(artista)){
                resultadosBusquedaArtista.add(cancion);
            }
        }

        return resultadosBusquedaArtista;
    }

    public List<Cancion> obtenerTop3MejorCalificadas(){
        List<Cancion> copiaCatalogoCanciones = new ArrayList<Cancion>(catalogoCanciones);

        ordenarListaPorCriterio(copiaCatalogoCanciones, "calificacion");

        return extraerTop3(copiaCatalogoCanciones);

    }

    public List<Cancion> obtenerTop3MasCompradas(){
        List<Cancion> copiaCatalogoCanciones = new ArrayList<Cancion>(catalogoCanciones);

        ordenarListaPorCriterio(copiaCatalogoCanciones, "vecesComprada");

        return extraerTop3(copiaCatalogoCanciones);

    }

    private void ordenarListaPorCriterio(List<Cancion> lista, String criterio){
        for(int i = 0; i < lista.size() - 1; i++){
            for(int j = 0; j < lista.size() - 1 - i; j++) {
               double valorActual = 0;
               double valorSiguiente = 0;

               if(criterio.equals("calificacion")){
                    valorActual = lista.get(j).getCalificacion();
                    valorSiguiente = lista.get(j + 1).getCalificacion();
               }else if(criterio.equals("vecesComprada")){
                   valorActual = lista.get(j).getVecesComprada();
                   valorSiguiente = lista.get(j + 1).getVecesComprada();
               }

               if(valorActual < valorSiguiente){
                   Collections.swap(lista, j, j+1);
               }
            }
        }
    }

    private List<Cancion> extraerTop3 (List<Cancion> listaOrdenada){
        List<Cancion> top3 = new ArrayList<Cancion>();

        for(int i = 0; i < 3 && i < listaOrdenada.size(); i++){
            top3.add(listaOrdenada.get(i));
        }

        return top3;
    }


    public List<Cancion> obtenerTop3MasIncluidasEnListas() {
        HashMap<Cancion, Integer> contador = new HashMap<>();
        for(Usuario usuario : listaUsuarios) {
            for(ListaReproduccion lista : usuario.getListasReproduccion()){
                for(Cancion cancion : lista.getCanciones()){
                    if(contador.containsKey(cancion)){
                        contador.put(cancion, contador.get(cancion) + 1);
                    } else {
                        contador.put(cancion, 1);
                    }
                }
            }
        }

        List<Cancion> cancionesContadas = new ArrayList<>(contador.keySet());

        for(int i = 0; i < cancionesContadas.size() - 1; i++){
            for(int j = 0; j < cancionesContadas.size() - 1 - i; j++) {

                Cancion cancionActual = cancionesContadas.get(j);
                Cancion cancionSiguiente = cancionesContadas.get(j + 1);

                int conteoActual = contador.get(cancionActual);
                int conteoSiguiente = contador.get(cancionSiguiente);

                if(conteoActual < conteoSiguiente){
                    Collections.swap(cancionesContadas, j, j + 1);
                }
            }
        }

        return extraerTop3(cancionesContadas);
    }

    public ArrayList<ListaReproduccion> obtenerListasDeUsuario(String nombreUsuario){
        if(nombreUsuario == null){
            return null;
        }

        for(Usuario usuario : listaUsuarios){
            if(usuario.getNombreUsuario().equalsIgnoreCase(nombreUsuario)){
                return usuario.getListasReproduccion();
            }
        }
        return null;
    }

    public boolean agregarCancionACola(Cancion cancion){
        return colaReproduccion.agregarCancion(cancion);
    }

    public void reproducirSiguienteEnCola(Cuenta cuenta){
        if(colaReproduccion.estaVacia()){
            return;
        }
        Cancion siguiente = colaReproduccion.eliminarSiguiente();
        reproducirCancion(siguiente, cuenta);
    }

    public boolean agregarListaACola(ListaReproduccion lista) {
        if (lista == null || lista.getCanciones() == null || lista.getCanciones().isEmpty()) {
            return false;
        }

        boolean todasAgregadas = true;
        for (Cancion cancion : lista.getCanciones()) {

            if (!agregarCancionACola(cancion)) {
                todasAgregadas = false;
            }

        }
        return todasAgregadas;
    }

    //Métodos para reproducción de audio

    public void reproducirCancion(Cancion cancion, Cuenta cuentaAutenticada) {
        if (cancion == null || cuentaAutenticada == null) {
            return;
        }

        if (cuentaAutenticada.getRol().equals("cr.ac.ucenfotec.bl.entidades.Administrador")) {
            reproducirAudioLocal(cancion.getRutaArchivoAudio(), 0);
            return;
        }

        Usuario usuario = (Usuario) cuentaAutenticada;

        if (usuario.esDuenoDe(cancion)) {
            reproducirAudioLocal(cancion.getRutaArchivoAudio(), 0); // Completa
        } else {
            reproducirAudioLocal(cancion.getRutaArchivoAudio(), 30); // Preview
        }
    }

    /**
     * Instancia y reproduce el audio localmente sin necesidad de una clase externa.
     * @param rutaArchivo La ruta del archivo .wav
     * @param limiteSegundos 0 para reproducción completa, >0 para limitar el tiempo (preview)
     */
    private void reproducirAudioLocal(String rutaArchivo, int limiteSegundos) {
        try {
            File archivo = new File(rutaArchivo);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(archivo);

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            if (limiteSegundos > 0) {

                new Thread(() -> {
                    try {
                        Thread.sleep(limiteSegundos * 1000);
                        if (clip.isRunning()) {
                            clip.stop();
                            clip.close();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }).start();
            }

        } catch (Exception e) {
            // En una GUI, aquí actualizarías un label de error en la vista.

        }
    }

}
