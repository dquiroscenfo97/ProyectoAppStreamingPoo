package cr.ac.ucenfotec.bl.entidades;

import java.time.LocalDate;

public class Cancion {

    //Atributos

    private String titulo;
    private String genero;
    private String artista;
    private String compositor;
    private LocalDate fechaLanzamiento;
    private String album;
    private String caratula; //Guarda la ruta de una imagen
    private double calificacion;
    private double precio;
    private int vecesComprada;
    private int cantidadCalificaciones;
    private String rutaArchivoAudio;


    //Métodos

    //Constructores

    //Constructor Completo

    public Cancion(String titulo, String genero, String artista, String compositor, LocalDate fechaLanzamiento, String album, String caratula, double calificacion, double precio, int vecesComprada, String rutaArchivoAudio) {
        this.titulo = titulo;
        this.genero = genero;
        this.artista = artista;
        this.compositor = compositor;
        this.fechaLanzamiento = fechaLanzamiento;
        this.album = album;
        this.caratula = caratula;
        this.rutaArchivoAudio = rutaArchivoAudio;
        setVecesComprada(vecesComprada);
        setCalificacion(calificacion);
        setPrecio(precio);
        this.cantidadCalificaciones = 0;
    }

    //Setters y getters


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getCompositor() {
        return compositor;
    }

    public void setCompositor(String compositor) {
        this.compositor = compositor;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;

        if (album == null || album.trim().isEmpty()) {
            this.caratula = "imagenes/caratula_predeterminada.png";
        }
    }
    public String getCaratula() {
        return caratula;
    }

    public void setCaratula(String caratula) {

        if (this.album == null || this.album.trim().isEmpty()) {
            this.caratula = "imagenes/caratula_predeterminada.png";
        }
        else if (caratula == null || caratula.trim().isEmpty()) {
            this.caratula = "imagenes/caratula_predeterminada.png";
        }
        else {
            this.caratula = caratula;
        }
    }

    public double getCalificacion() {
        return calificacion;
    }

    private void setCalificacion(double calificacion) {

        if(esCalificacionValida(calificacion)){
            redondearUnaCifra(calificacion);
        }else{
            this.calificacion = 0.0;
        }

    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio >= 0.0) {
            this.precio = Math.round(precio * 100.0) / 100.0;
        } else {
            this.precio = 0.0;
        }
    }

    public int getVecesComprada() {
        return vecesComprada;
    }

    private void setVecesComprada(int vecesComprada) {
        if(vecesComprada >= 0){
            this.vecesComprada = vecesComprada;
        }
    }

    public String getRutaArchivoAudio(){
        return rutaArchivoAudio;
    }

    

    //Utilidades
    public boolean equals(Cancion cancion) {
        if (cancion == null) {
            return false;
        }
        return this.titulo.equalsIgnoreCase(cancion.titulo) &&
                this.artista.equalsIgnoreCase(cancion.artista);
    }

    @Override
    public String toString(){
        return  "\nTítulo: " + titulo +
                "\nGénero: " + genero +
                "\nArtista: " + artista +
                "\nCompositor: " + compositor +
                "\nFecha de Lanzamiento: " + fechaLanzamiento +
                "\nAlbum: " + album +
                "\nCaratula: " + caratula +
                "\nCalificacion: " + calificacion +
                "\nPrecio: " + precio +
                "\nVeces Comprada: " + vecesComprada;
    }

    public boolean esCalificacionValida(double nota) {
        return nota >= 0.0 && nota <= 5.0;
    }

    //Operaciones

    public void incrementarVecesComprada (){
        this.vecesComprada = this.vecesComprada + 1;
    }

    public void agregarCalificacion(double nuevaNota) {
        if (esCalificacionValida(nuevaNota)) {

            this.calificacion = ((this.calificacion * this.cantidadCalificaciones) + nuevaNota) / (this.cantidadCalificaciones + 1);

            this.cantidadCalificaciones++;

            redondearUnaCifra(this.calificacion);

        }
    }

    private void redondearUnaCifra(double valor) {
        this.calificacion = Math.round(valor * 10.0) / 10.0;
    }
}
