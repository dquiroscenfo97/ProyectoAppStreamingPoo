import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.time.Period;
import java.util.List;
import java.util.Map;

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

    /**
     * Valida que una contraseña cumpla con los requisitos de seguridad.
     * Debe tener entre 8 y 12 caracteres, al menos una mayúscula,
     * una minúscula, un número y un carácter especial.
     */
    public static boolean validarFormatoContrasenia(String contrasenia) {
        if (contrasenia == null || contrasenia.length() < 8 || contrasenia.length() > 12) {
            return false;
        }

        boolean tieneMinuscula = false;
        boolean tieneMayuscula = false;
        boolean tieneNumero = false;
        boolean tieneEspecial = false;

        for (char c : contrasenia.toCharArray()) {
            if (Character.isLowerCase(c)) {
                tieneMinuscula = true;
            } else if (Character.isUpperCase(c)) {
                tieneMayuscula = true;
            } else if (Character.isDigit(c)) {
                tieneNumero = true;
            } else {
                tieneEspecial = true;
            }
        }

        return tieneMinuscula && tieneMayuscula && tieneNumero && tieneEspecial;
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
        return listaAdministradores.isEmpty();
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

        if(!administradorPorRegistrar.esContraseniaValida()){
            return false;
        }

        listaAdministradores.add(administradorPorRegistrar);
        return true;
    }

    public boolean registrarUsuario(Usuario usuarioPorRegistrar){
        if(usuarioPorRegistrar == null){
            return false;
        }

        int edad = calcularEdad(usuarioPorRegistrar.getFechaNacimiento())
        if(edad < 18){
            return false;
        }

        for(Usuario usuario : listaUsuarios){
            if(usuario.equals(usuarioPorRegistrar)){
                return false;
            }
        }

        if(!usuarioPorRegistrar.esContraseniaValida){
            return false;
        }

        usuarioPorRegistrar.recargarSaldo();

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

    public ArrayList<Cancion> buscarCancionPorGenero(String genero){
        ArrayList<Cancion> resultadosBusquedaGenero = new ArrayList<Cancion>();

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

    public ArrayList<Cancion> buscarCancionPorArtista(String artista){
        ArrayList<Cancion> resultadosBusquedaArtista = new ArrayList<Cancion>();

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

    public ArrayList<Cancion> obtenerTop3MejorCalificadas(){
        ArrayList<Cancion> copiaCatalogoCanciones = new ArrayList<Cancion>(catalogoCanciones);

        ordenarListaPorCriterio(copiaCatalogoCanciones, "calificacion");

        return extraerTop3(copiaCatalogoCanciones);

    }

    public ArrayList<Cancion> obtenerTop3MasCompradas(){
        ArrayList<Cancion> copiaCatalogoCanciones = new ArrayList<Cancion>(catalogoCanciones);

        ordenarListaPorCriterio(copiaCatalogoCanciones, "vecesComprada");

        return extraerTop3(copiaCatalogoCanciones);

    }

    private void ordenarListaPorCriterio(ArrayList<Cancion> lista, String criterio){
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

    private ArrayList<Cancion> extraerTop3 (ArrayList<Cancion> listaOrdenada){
        ArrayList<Cancion> top3 = new ArrayList<Cancion>();

        for(int i = 0; i < 3 && i < listaOrdenada.size(); i++){
            top3.add(listaOrdenada.get(i));
        }

        return top3;
    }


    public ArrayList<Cancion> obtenerTop3MasIncluidasEnListas() {
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
                    Collections.swap((List<Cancion>) cancionesContadas, j, j + 1);
                }
            }
        }

        return extraerTop3((ArrayList<Cancion>) cancionesContadas);
    }


}
