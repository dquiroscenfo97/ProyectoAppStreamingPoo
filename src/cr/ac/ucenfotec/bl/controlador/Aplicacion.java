package cr.ac.ucenfotec.bl.controlador;

import cr.ac.ucenfotec.bl.entidades.Cuenta;
import cr.ac.ucenfotec.bl.entidades.Usuario;
import cr.ac.ucenfotec.bl.entidades.Administrador;
import cr.ac.ucenfotec.bl.entidades.Cancion;
import cr.ac.ucenfotec.bl.entidades.ListaReproduccion;
import cr.ac.ucenfotec.bl.entidades.ColaReproduccion;

import cr.ac.ucenfotec.bl.entidades.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.time.Period;
import java.util.List;

import javax.sound.sampled.*;
import java.io.File;

import cr.ac.ucenfotec.bl.excepciones.SaldoInsuficienteException;
import cr.ac.ucenfotec.bl.excepciones.RegistroDuplicadoException;
import cr.ac.ucenfotec.bl.excepciones.CredencialesInvalidasException;

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

    public boolean registrarCancion(Cancion cancionPorRegistrar) {
        return catalogoCanciones.add(cancionPorRegistrar);
    }

    public static int calcularEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            return 0; //Proteccion contra null pointer exception
        }

        LocalDate fechaActual = LocalDate.now();

        Period periodo = Period.between(fechaNacimiento, fechaActual);

        return periodo.getYears();
    }

    public boolean hayAdministradorRegistrado() {
        return !listaAdministradores.isEmpty();
    }

    public void registrarAdministrador(Administrador administradorPorRegistrar) throws RegistroDuplicadoException {
        if (administradorPorRegistrar == null) {
            throw new IllegalArgumentException("El administrador no puede ser nulo.");
        }

        for (Administrador admin : listaAdministradores) {
            if (admin.equals(administradorPorRegistrar)) {
                throw new RegistroDuplicadoException("Ya existe un administrador con ese correo o nombre de usuario.");
            }
        }

        listaAdministradores.add(administradorPorRegistrar);
    }

    public void registrarUsuario(Usuario usuarioPorRegistrar) throws RegistroDuplicadoException {
        if (usuarioPorRegistrar == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }

        int edad = calcularEdad(usuarioPorRegistrar.getFechaNacimiento());
        if (edad < 18) {
            throw new IllegalArgumentException("El usuario debe ser mayor de 18 años.");
        }

        for (Usuario usuario : listaUsuarios) {
            if (usuario.equals(usuarioPorRegistrar)) {
                throw new RegistroDuplicadoException("Ya existe un usuario con ese correo o nombre de usuario.");
            }
        }

        usuarioPorRegistrar.recargarSaldo(4.99);
        listaUsuarios.add(usuarioPorRegistrar);
    }

    public Cancion buscarCancionPorNombre(String nombre) {
        if (nombre == null) {
            return null;
        }

        for (Cancion cancion : catalogoCanciones) {
            if (cancion.getTitulo().equalsIgnoreCase(nombre)) {
                return cancion;
            }
        }

        return null;
    }

    public List<Cancion> buscarCancionPorGenero(String genero) {
        List<Cancion> resultadosBusquedaGenero = new ArrayList<Cancion>();

        if (genero == null) {
            return resultadosBusquedaGenero;
        }

        for (Cancion cancion : catalogoCanciones) {
            if (cancion.getGenero().equalsIgnoreCase(genero)) {
                resultadosBusquedaGenero.add(cancion);
            }
        }

        return resultadosBusquedaGenero;
    }

    public List<Cancion> buscarCancionPorArtista(String artista) {
        List<Cancion> resultadosBusquedaArtista = new ArrayList<Cancion>();

        if (artista == null) {
            return resultadosBusquedaArtista;
        }

        for (Cancion cancion : catalogoCanciones) {
            if (cancion.getArtista().equalsIgnoreCase(artista)) {
                resultadosBusquedaArtista.add(cancion);
            }
        }

        return resultadosBusquedaArtista;
    }

    public List<Cancion> obtenerTop3MejorCalificadas() {
        List<Cancion> copiaCatalogoCanciones = new ArrayList<Cancion>(catalogoCanciones);

        ordenarListaPorCriterio(copiaCatalogoCanciones, "calificacion");

        return extraerTop3(copiaCatalogoCanciones);

    }

    public List<Cancion> obtenerTop3MasCompradas() {
        List<Cancion> copiaCatalogoCanciones = new ArrayList<Cancion>(catalogoCanciones);

        ordenarListaPorCriterio(copiaCatalogoCanciones, "vecesComprada");

        return extraerTop3(copiaCatalogoCanciones);

    }

    private void ordenarListaPorCriterio(List<Cancion> lista, String criterio) {
        for (int i = 0; i < lista.size() - 1; i++) {
            for (int j = 0; j < lista.size() - 1 - i; j++) {
                double valorActual = 0;
                double valorSiguiente = 0;

                if (criterio.equals("calificacion")) {
                    valorActual = lista.get(j).getCalificacion();
                    valorSiguiente = lista.get(j + 1).getCalificacion();
                } else if (criterio.equals("vecesComprada")) {
                    valorActual = lista.get(j).getVecesComprada();
                    valorSiguiente = lista.get(j + 1).getVecesComprada();
                }

                if (valorActual < valorSiguiente) {
                    Collections.swap(lista, j, j + 1);
                }
            }
        }
    }

    private List<Cancion> extraerTop3(List<Cancion> listaOrdenada) {
        List<Cancion> top3 = new ArrayList<Cancion>();

        for (int i = 0; i < 3 && i < listaOrdenada.size(); i++) {
            top3.add(listaOrdenada.get(i));
        }

        return top3;
    }


    public List<Cancion> obtenerTop3MasIncluidasEnListas() {
        HashMap<Cancion, Integer> contador = new HashMap<>();
        for (Usuario usuario : listaUsuarios) {
            for (ListaReproduccion lista : usuario.getListasReproduccion()) {
                for (Cancion cancion : lista.getCanciones()) {
                    if (contador.containsKey(cancion)) {
                        contador.put(cancion, contador.get(cancion) + 1);
                    } else {
                        contador.put(cancion, 1);
                    }
                }
            }
        }

        List<Cancion> cancionesContadas = new ArrayList<>(contador.keySet());

        for (int i = 0; i < cancionesContadas.size() - 1; i++) {
            for (int j = 0; j < cancionesContadas.size() - 1 - i; j++) {

                Cancion cancionActual = cancionesContadas.get(j);
                Cancion cancionSiguiente = cancionesContadas.get(j + 1);

                int conteoActual = contador.get(cancionActual);
                int conteoSiguiente = contador.get(cancionSiguiente);

                if (conteoActual < conteoSiguiente) {
                    Collections.swap(cancionesContadas, j, j + 1);
                }
            }
        }

        return extraerTop3(cancionesContadas);
    }

    public ArrayList<ListaReproduccion> obtenerListasDeUsuario(String nombreUsuario) {
        if (nombreUsuario == null) {
            return null;
        }

        for (Usuario usuario : listaUsuarios) {
            if (usuario.getNombreUsuario().equalsIgnoreCase(nombreUsuario)) {
                return usuario.getListasReproduccion();
            }
        }
        return null;
    }

    public boolean agregarCancionACola(Cancion cancion) {
        return colaReproduccion.agregarCancion(cancion);
    }

    public void reproducirSiguienteEnCola(Cuenta cuenta) {
        if (colaReproduccion.estaVacia()) {
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

    public Cuenta iniciarSesion(String nombreUsuario, String contrasenia) throws CredencialesInvalidasException {

        for (Administrador a : listaAdministradores) {
            if (a.getNombreUsuario().equals(nombreUsuario) && a.autenticar(contrasenia)) {
                return a;
            }
        }

        for (Usuario u : listaUsuarios) {
            if (u.getNombreUsuario().equals(nombreUsuario) && u.autenticar(contrasenia)) {
                return u;
            }
        }

        throw new CredencialesInvalidasException("El nombre de usuario o la contraseña son incorrectos.");
    }

    //Métodos para reproducción de audio

    public void reproducirCancion(Cancion cancion, Cuenta cuentaAutenticada) {
        if (cancion == null || cuentaAutenticada == null) {
            return;
        }

        if (cuentaAutenticada.getRol().equals("Administrador")) {
            reproducirAudioLocal(cancion.getRutaArchivoAudio(), 0);
            return;
        }

        Usuario usuario = (Usuario) cuentaAutenticada;

        if (usuario.esDuenoDe(cancion)) {
            reproducirAudioLocal(cancion.getRutaArchivoAudio(), 0);
        } else {
            reproducirAudioLocal(cancion.getRutaArchivoAudio(), 30);
        }
    }

    private void reproducirAudioLocal(String rutaArchivo, int limiteSegundos) {
        try {
            File archivo = new File(rutaArchivo);

            if (!archivo.exists()) {
                System.out.println("Error: El archivo de audio no existe en la ruta especificada.");
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(archivo);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            if (limiteSegundos > 0) {
                long maxInicio = Math.max(0, clip.getMicrosecondLength() - 30_000_000L);
                long inicioAleatorio = (long) (Math.random() * maxInicio);
                clip.setMicrosecondPosition(inicioAleatorio);
                clip.start();
                Thread.sleep(limiteSegundos * 1000);
                if (clip.isRunning()) {
                    clip.stop();
                }
            } else {
                clip.setFramePosition(0);
                clip.start();
                Thread.sleep(100);
                while (clip.isRunning()) {
                    Thread.sleep(1000);
                }
            }

            clip.close();

        } catch (UnsupportedAudioFileException e) {
            System.out.println("Error: El formato de audio no es soportado. Use archivos .WAV");
        } catch (LineUnavailableException e) {
            System.out.println("Error: El sistema de audio no está disponible.");
        } catch (IOException e) {
            System.out.println("Error de lectura al acceder al archivo de audio.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.out.println("Error inesperado al reproducir: " + e.getMessage());
        }
    }
}