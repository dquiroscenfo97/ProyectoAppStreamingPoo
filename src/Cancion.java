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

    //Métodos

    //Constructores

    //Constructor Completo

    public Cancion(String titulo, String genero, String artista, String compositor, LocalDate fechaLanzamiento, String album, String caratula, double calificacion, double precio, int vecesComprada) {
        this.titulo = titulo;
        this.genero = genero;
        this.artista = artista;
        this.compositor = compositor;
        this.fechaLanzamiento = fechaLanzamiento;
        this.album = album;
        this.caratula = caratula;
        this.calificacion = calificacion;
        this.precio = precio;
        this.vecesComprada = vecesComprada;
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
    }

    public String getCaratula() {
        return caratula;
    }

    public void setCaratula(String caratula) {
        this.caratula = caratula;
    }

    public double getCalificacion() {
        return calificacion;
    }

    private void setCalificacion(double calificacion) { //Este set debe de eliminarse por seguridad
        this.calificacion = calificacion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getVecesComprada() {
        return vecesComprada;
    }

    private void setVecesComprada(int vecesComprada) {
        this.vecesComprada = vecesComprada;
    }

    //Utilidades
    public boolean equals(Cancion cancion){
        return this.titulo.equals(cancion.titulo) && this.artista.equals(cancion.artista) && this.compositor.equals(cancion.compositor);
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
}
