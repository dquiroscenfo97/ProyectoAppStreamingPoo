import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        //Instancias de objetos
        Cancion pinkMoon = new Cancion("Pink Moon", "Folk Psicodelico", "Nick Drake", "Nick Drake", LocalDate.of(1972,2,25),"Pink Moon", "Ruta de imagen", 5.0, 1000, 0);
        Cancion dig  = new Cancion("Dig", "Rock Alternativo", "Incubus", "Brandon Boyd, Mike Einziger, Ben Kenney, Jose Pasillas y Chris Kilmore", LocalDate.of(2007,3,27),"Light Grenades", "Ruta de imagen", 5.0, 2000, 0);
        Cancion nocheDePerros  = new Cancion("Noche de Perros", "Rock Progresivo", "Serú Girán", "Charly García, David Lebón", LocalDate.of(1979,8,19),"La grasa de las capitales", "Ruta de imagen", 5.0, 1000, 0 );

        Usuario primerUsuario = new Usuario("pepe@gmail.com", "pepe22", "pepe22!*", "Pepe Gonzalez", LocalDate.of(1998, 10, 22), "Costarricense", "126600754", "Ruta de imagen", 5000.65);

        Administrador admin = new Administrador("Admin@gmail.com", "admin", "admin2026*$!");

        Aplicacion app = new Aplicacion();


        //Registro en listas
        app.registrarCancion(pinkMoon);
        app.registrarCancion(dig);
        app.registrarCancion(nocheDePerros);

        app.registrarUsuario(primerUsuario);

        app.registrarAdministrador(admin);

        ListaReproduccion listaFavorita = primerUsuario.crearLista("Mis favoritas");

        //Mostrar Contenido de la App
        System.out.println("--- MOSTRANDO CONTENIDOS ---");
        app.mostrarCatalogoCanciones();
        System.out.println("\n");
        app.mostrarUsuarios();
        System.out.println("\n");
        app.mostrarAdministradores();
        System.out.println("\n");

        //Mostrar Contenido del usuario
        System.out.println("\n--- MOSTRANDO CONTENIDOS DEL USUARIO ---");
        primerUsuario.mostrarListasReproduccion();
        System.out.println("\n");
        primerUsuario.mostrarColeccionCanciones();



    }
}