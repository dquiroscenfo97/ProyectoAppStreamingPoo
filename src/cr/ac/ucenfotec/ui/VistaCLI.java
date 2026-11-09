package cr.ac.ucenfotec.ui;

import cr.ac.ucenfotec.bl.controlador.Aplicacion;
import cr.ac.ucenfotec.bl.entidades.Administrador;
import cr.ac.ucenfotec.bl.entidades.Cancion;
import cr.ac.ucenfotec.bl.entidades.Cuenta;
import cr.ac.ucenfotec.bl.entidades.ListaReproduccion;
import cr.ac.ucenfotec.bl.entidades.Usuario;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import cr.ac.ucenfotec.bl.excepciones.SaldoInsuficienteException;
import cr.ac.ucenfotec.bl.excepciones.RegistroDuplicadoException;
import cr.ac.ucenfotec.bl.excepciones.CredencialesInvalidasException;
import java.time.format.DateTimeParseException;

public class VistaCLI {

    private static final Scanner scanner = new Scanner(System.in);

    private static final String[] MENU_PRINCIPAL = {
            "1. Registrarse como Usuario",
            "2. Iniciar Sesión",
            "3. Salir"
    };

    private static final String[] MENU_ADMINISTRADOR = {
            "1. Subir nueva canción al catálogo",
            "2. Ver catálogo completo",
            "3. Buscar canción por nombre",
            "4. Reproducir canción",
            "5. Gestionar cola de reproducción",
            "6. Modificar contraseña",
            "0. Cerrar sesión"
    };

    private static final String[] MENU_USUARIO = {
            "1. Ver mi perfil y saldo",
            "2. Recargar saldo",
            "3. Explorar catálogo y comprar canciones",
            "4. Mi colección (calificar canciones)",
            "5. Mis listas de reproducción",
            "6. Ver Top 3",
            "7. Reproducir y gestionar cola",
            "8. Modificar contraseña",
            "0. Cerrar sesión"
    };

    private final Aplicacion aplicacion;
    private Cuenta cuentaAutenticada;

    public VistaCLI(Aplicacion aplicacion) {
        this.aplicacion = aplicacion;
        this.cuentaAutenticada = null;
    }

    public void iniciar() {
        if (!aplicacion.hayAdministradorRegistrado()) {
            System.out.println("\n========================================");
            System.out.println("      SISTEMA DE MÚSICA (INICIO)        ");
            System.out.println("========================================");
            System.out.println("No hay administradores registrados en el sistema.");
            System.out.println("Es obligatorio registrar uno para continuar.\n");
            registrarAdministrador();
        }
        ejecutarMenu(0);
    }

    private void ejecutarMenu(int estadoActual) {
        byte opcion;
        do {
            filtrarPorEstado(estadoActual);
            opcion = solicitarOpcion();
            estadoActual = procesarOpcion(estadoActual, opcion);
        } while (estadoActual != -1);

        System.out.println("\nSaliendo del sistema. ¡Hasta pronto!");
    }

    private void filtrarPorEstado(int estadoActual) {
        System.out.println("\n========================================");
        if (estadoActual == 0) {
            System.out.println("          MENÚ PRINCIPAL              ");
            imprimirMenu(MENU_PRINCIPAL);
        } else if (estadoActual == 1) {
            System.out.println("          PANEL DE ADMINISTRADOR      ");
            imprimirMenu(MENU_ADMINISTRADOR);
        } else if (estadoActual == 2) {
            System.out.println("          PANEL DE USUARIO            ");
            imprimirMenu(MENU_USUARIO);
        }
        System.out.println("========================================");
    }

    private void imprimirMenu(String[] arregloOpciones) {
        for (String opcion : arregloOpciones) {
            System.out.println(opcion);
        }
    }

    private int procesarOpcion(int estadoActual, byte opcion) {
        switch (estadoActual) {
            case 0:
                return procesarMenuPrincipal(opcion);
            case 1:
                return procesarMenuAdministrador(opcion);
            case 2:
                return procesarMenuUsuario(opcion);
            default:
                System.out.println("\nError de estado inesperado.");
                return 0;
        }
    }

    private int procesarMenuPrincipal(byte opcion) {
        if (opcion == 1) {
            registrarUsuario();
            return 0;
        } else if (opcion == 2) {
            return iniciarSesion();
        } else if (opcion == 3) {
            return -1;
        } else {
            System.out.println("\nError, seleccione una opción válida.");
            return 0;
        }
    }

    private int procesarMenuAdministrador(byte opcion) {
        switch (opcion) {
            case 0:
                this.cuentaAutenticada = null;
                return 0;
            case 1:
                subirCancion();
                return 1;
            case 2:
                mostrarCatalogoCanciones();
                return 1;
            case 3:
                buscarCancion();
                return 1;
            case 4:
                reproducirCancion();
                return 1;
            case 5:
                gestionarCola();
                return 1;
            case 6:
                modificarContrasenia();
                return 1;
            default:
                System.out.println("\nError, seleccione una opción válida.");
                return 1;
        }
    }

    private void mostrarCatalogoCanciones() {
        System.out.println("\n--- CATÁLOGO DE CANCIONES ---");
        List<Cancion> catalogo = aplicacion.getCatalogoCanciones();
        if (catalogo.isEmpty()) {
            System.out.println("El catálogo está vacío actualmente.");
        } else {
            for (Cancion cancion : catalogo) {
                System.out.println(cancion.toString());
            }
        }
    }

    private int procesarMenuUsuario(byte opcion) {
        Usuario usuarioActual = (Usuario) this.cuentaAutenticada;

        switch (opcion) {
            case 0:
                this.cuentaAutenticada = null;
                return 0;
            case 1:
                System.out.println(usuarioActual.toString());
                return 2;
            case 2:
                double monto = solicitarDecimal("Monto a recargar: ");
                if (usuarioActual.recargarSaldo(monto)) {
                    System.out.println("✓ Saldo recargado exitosamente. Nuevo saldo: $" + usuarioActual.getSaldo());
                } else {
                    System.out.println("✗ Monto inválido. Debe ser mayor a 0.");
                }
                return 2;
            case 3:
                explorarYComprar(usuarioActual);
                return 2;
            case 4:
                calificarColeccion(usuarioActual);
                return 2;
            case 5:
                gestionarListas(usuarioActual);
                return 2;
            case 6:
                verTop3();
                return 2;
            case 7:
                gestionarCola();
                return 2;
            case 8:
                modificarContrasenia();
                return 2;
            default:
                System.out.println("\nError, seleccione una opción válida.");
                return 2;
        }
    }

    private void registrarAdministrador() {
        System.out.println("--- REGISTRO DE ADMINISTRADOR ---");
        String correo = solicitarTexto("Correo electrónico: ");
        String nombreUsuario = solicitarTexto("Nombre de usuario: ");
        String contrasenia = solicitarContraseniaSegura();

        Administrador admin = new Administrador(correo, nombreUsuario, contrasenia);
        try {
            aplicacion.registrarAdministrador(admin);
            System.out.println("✓ Administrador registrado exitosamente.");
        } catch (RegistroDuplicadoException e) {
            System.out.println("✗ " + e.getMessage());
        }
    }

    private void registrarUsuario() {
        System.out.println("\n--- REGISTRO DE USUARIO ---");
        String nombreCompleto = solicitarTexto("Nombre completo: ");
        LocalDate fechaNacimiento = solicitarFecha("Fecha de nacimiento (AAAA-MM-DD): ", "yyyy-MM-dd");
        String nacionalidad = solicitarTexto("Nacionalidad: ");
        String cedula = solicitarTexto("Cédula: ");
        String correo = solicitarTexto("Correo electrónico: ");
        String nombreUsuario = solicitarTexto("Nombre de usuario: ");
        String contrasenia = solicitarContraseniaSegura();

        String avatar = solicitarTexto("Ruta del avatar (dejar vacío para usar el predeterminado): ");

        Usuario usuario = new Usuario(correo, nombreUsuario, contrasenia, nombreCompleto,
                fechaNacimiento, nacionalidad, cedula, avatar, 0.0);

        try {
            aplicacion.registrarUsuario(usuario);
            System.out.println("✓ Usuario registrado exitosamente. Bono de $4.99 aplicado.");
        } catch (RegistroDuplicadoException e) {
            System.out.println("✗ " + e.getMessage());
        }
    }

    private int iniciarSesion() {
        System.out.println("\n--- INICIAR SESIÓN ---");
        String nombreUsuario = solicitarTexto("Nombre de usuario: ");
        String contrasenia = solicitarTexto("Contraseña: ");

        try {
            // Delegamos la validación a Aplicacion
            this.cuentaAutenticada = aplicacion.iniciarSesion(nombreUsuario, contrasenia);

            if (cuentaAutenticada instanceof Administrador) {
                System.out.println("✓ Bienvenido, Administrador: " + cuentaAutenticada.getNombreUsuario());
                return 1;
            } else {
                System.out.println("✓ Bienvenido, Usuario: " + cuentaAutenticada.getNombreUsuario());
                mostrarListasDeUsuario((Usuario) cuentaAutenticada); // Requisito de consigna
                return 2;
            }
        } catch (CredencialesInvalidasException e) {
            System.out.println("✗ " + e.getMessage());
            return 0;
        }
    }



    private void subirCancion() {
        System.out.println("\n--- SUBIR NUEVA CANCIÓN ---");
        String titulo = solicitarTexto("Título: ");
        String genero = solicitarTexto("Género: ");
        String artista = solicitarTexto("Artista: ");
        String compositor = solicitarTexto("Compositor: ");
        LocalDate fecha = solicitarFecha("Fecha de lanzamiento (AAAA-MM-DD): ", "yyyy-MM-dd");
        String album = solicitarTexto("Álbum: ");
        String caratula = solicitarTexto("Ruta de carátula (dejar vacío para predeterminada): ");
        double precio = solicitarDecimal("Precio: ");
        String rutaAudio = solicitarTexto("Ruta del archivo de audio: ");

        try {
            Cancion nuevaCancion = new Cancion(titulo, genero, artista, compositor, fecha, album, caratula, 0.0, precio, 0, rutaAudio);
            aplicacion.registrarCancion(nuevaCancion);
            System.out.println("✓ Canción registrada exitosamente en el catálogo.");
        } catch (IllegalArgumentException e) {
            System.out.println("✗ Error en los datos de la canción: " + e.getMessage());
        }
    }

    private void buscarCancion() {
        System.out.println("\n--- BUSCAR CANCIÓN ---");
        System.out.println("1. Por nombre");
        System.out.println("2. Por género");
        System.out.println("3. Por artista");
        byte tipo = solicitarOpcion();
        String termino = solicitarTexto("Ingrese el término de búsqueda: ");

        List<Cancion> resultados = null;
        if (tipo == 1) {
            Cancion c = aplicacion.buscarCancionPorNombre(termino);
            if (c != null) System.out.println(c.toString());
            else System.out.println("No se encontró la canción.");
        } else if (tipo == 2) {
            resultados = aplicacion.buscarCancionPorGenero(termino);
            imprimirResultados(resultados);
        } else if (tipo == 3) {
            resultados = aplicacion.buscarCancionPorArtista(termino);
            imprimirResultados(resultados);
        } else {
            System.out.println("Opción de búsqueda inválida.");
        }
    }

    private void imprimirResultados(List<Cancion> resultados) {
        if (resultados == null || resultados.isEmpty()) {
            System.out.println("No se encontraron resultados.");
        } else {
            for (Cancion c : resultados) {
                System.out.println(c.toString());
            }
        }
    }

    private void reproducirCancion() {
        String titulo = solicitarTexto("\nIngrese el título de la canción a reproducir: ");
        Cancion c = aplicacion.buscarCancionPorNombre(titulo);

        if (c != null) {
            System.out.println("Reproduciendo: " + c.getTitulo() + "...");
            aplicacion.reproducirCancion(c, this.cuentaAutenticada);
        } else {
            System.out.println("✗ Canción no encontrada en el catálogo.");
        }
    }

    private void explorarYComprar(Usuario usuarioActual) {
        mostrarCatalogoCanciones();
        String titulo = solicitarTexto("\nIngrese el título de la canción que desea comprar: ");
        Cancion c = aplicacion.buscarCancionPorNombre(titulo);

        if (c != null) {
            try {
                usuarioActual.agregarCancion(c);
                System.out.println("✓ Compra exitosa. Canción agregada a su colección.");
            } catch (SaldoInsuficienteException e) {
                System.out.println("✗ " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("✗ " + e.getMessage());
            }
        } else {
            System.out.println("✗ Canción no encontrada.");
        }
    }

    private void calificarColeccion(Usuario usuarioActual) {
        System.out.println("--- COLECCION DE CANCIONES DE " + usuarioActual.getNombreUsuario().toUpperCase() + " ---");
        if (usuarioActual.getColeccionCanciones().isEmpty()) {
            System.out.println("No has comprado ninguna canción aún");
        } else {
            for (Cancion cancion : usuarioActual.getColeccionCanciones()) {
                System.out.println(cancion.toString());
            }
        }

        String titulo = solicitarTexto("\nIngrese el título de la canción a calificar: ");
        Cancion c = usuarioActual.buscarCancionEnColeccion(titulo);

        if (c != null) {
            double nota = solicitarDecimal("Ingrese su calificación (0.0 a 5.0): ");
            try {
                if (usuarioActual.calificarCancion(c, nota)) {
                    System.out.println("✓ Calificación registrada exitosamente.");
                } else {
                    System.out.println("✗ Error al calificar. Verifique que la nota esté entre 0.0 y 5.0.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("✗ " + e.getMessage());
            }
        } else {
            System.out.println("✗ La canción no se encuentra en su colección.");
        }
    }

    private void gestionarListas(Usuario usuarioActual) {
        System.out.println("\n--- GESTIÓN DE LISTAS ---");
        System.out.println("1. Crear nueva lista");
        System.out.println("2. Agregar canción a una lista");
        System.out.println("3. Ver mis listas");
        System.out.println("4. Buscar lista por nombre");
        byte subOpcion = solicitarOpcion();

        if (subOpcion == 1) {
            String nombreLista = solicitarTexto("Nombre de la nueva lista: ");
            ListaReproduccion lista = usuarioActual.crearLista(nombreLista);
            if (lista != null) {
                System.out.println("✓ Lista '" + nombreLista + "' creada exitosamente.");
            } else {
                System.out.println("✗ No se pudo crear la lista (nombre vacío o ya existe).");
            }
        } else if (subOpcion == 2) {
            System.out.println("--- COLECCION DE CANCIONES DE " + usuarioActual.getNombreUsuario().toUpperCase() + " ---");
            if (usuarioActual.getColeccionCanciones().isEmpty()) {
                System.out.println("No has comprado ninguna canción aún");
            } else {
                for (Cancion cancion : usuarioActual.getColeccionCanciones()) {
                    System.out.println(cancion.toString());
                }
            }

            String nombreLista = solicitarTexto("Nombre de la lista de destino: ");
            String tituloCancion = solicitarTexto("Título de la canción a agregar: ");
            Cancion c = usuarioActual.buscarCancionEnColeccion(tituloCancion);

            if (c != null) {
                try {
                    if (usuarioActual.agregarCancionALista(nombreLista, c)) {
                        System.out.println("✓ Canción agregada a la lista.");
                    } else {
                        System.out.println("✗ No se pudo agregar. Verifique el nombre de la lista.");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("✗ " + e.getMessage());
                }
            } else {
                System.out.println("✗ La canción no se encuentra en su colección.");
            }
        } else if (subOpcion == 3) {
            mostrarListasDeUsuario(usuarioActual);
        } else if (subOpcion == 4) {
            String nombreLista = solicitarTexto("Ingrese el nombre de la lista a buscar: ");
            ListaReproduccion lista = usuarioActual.buscarLista(nombreLista);
            if (lista != null) {
                System.out.println("✓ Lista encontrada:");
                System.out.println(lista.toString());
            } else {
                System.out.println("✗ No se encontró ninguna lista con ese nombre.");
            }
        } else {
            System.out.println("Opción inválida.");
        }
    }

    private void gestionarCola() {
        System.out.println("\n--- COLA DE REPRODUCCIÓN ---");
        System.out.println("1. Agregar canción a la cola");
        System.out.println("2. Agregar lista completa a la cola");
        System.out.println("3. Reproducir siguiente en la cola");
        byte subOpcion = solicitarOpcion();

        if (subOpcion == 1) {
            String titulo = solicitarTexto("Título de la canción: ");
            Cancion c = aplicacion.buscarCancionPorNombre(titulo);
            if (c != null) {
                try {
                    if (aplicacion.agregarCancionACola(c)) {
                        System.out.println("✓ Canción agregada a la cola.");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("✗ " + e.getMessage());
                }
            } else {
                System.out.println("✗ Canción no encontrada.");
            }
        } else if (subOpcion == 2) {
            if (this.cuentaAutenticada instanceof Usuario) {
                Usuario u = (Usuario) this.cuentaAutenticada;
                mostrarListasDeUsuario(u);

                String nombreLista = solicitarTexto("Nombre de la lista a agregar: ");
                ListaReproduccion lista = u.buscarLista(nombreLista);
                if (lista != null) {
                    try {
                        if (aplicacion.agregarListaACola(lista)) {
                            System.out.println("✓ Lista agregada a la cola de reproducción.");
                        } else {
                            System.out.println("✗ La lista está vacía o no se pudo agregar.");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("✗ " + e.getMessage());
                    }
                } else {
                    System.out.println("✗ Lista no encontrada.");
                }
            } else {
                System.out.println("✗ Solo los usuarios pueden agregar sus listas personales a la cola.");
            }
        } else if (subOpcion == 3) {
            System.out.println("Reproduciendo siguiente elemento de la cola...");
            try {
                aplicacion.reproducirSiguienteEnCola(this.cuentaAutenticada);
            } catch (IllegalArgumentException e) {
                System.out.println("✗ " + e.getMessage());
            }
        } else {
            System.out.println("Opción inválida.");
        }
    }

    private void modificarContrasenia() {
        System.out.println("\n--- MODIFICAR CONTRASEÑA ---");
        String actual = solicitarTexto("Ingrese su contraseña actual: ");
        String nueva = solicitarContraseniaSegura();

        if (cuentaAutenticada.modificarContrasenia(actual, nueva, nueva)) {
            System.out.println("✓ Contraseña modificada exitosamente.");
        } else {
            System.out.println("✗ Error al modificar la contraseña. Verifique que la actual sea correcta y que la nueva sea distinta.");
        }
    }

    private void mostrarListasDeUsuario(Usuario usuario) {
        System.out.println("\n--- SUS LISTAS DE REPRODUCCIÓN ---");
        if (usuario.getListasReproduccion().isEmpty()) {
            System.out.println("Aún no tienes listas de reproducción creadas.");
        } else {
            for (ListaReproduccion lista : usuario.getListasReproduccion()) {
                System.out.println(lista.toString());
            }
        }
    }

    private byte solicitarOpcion() {
        while (true) {
            System.out.print("\nPor favor ingrese una opción: ");
            try {
                return Byte.parseByte(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido.");

            }
        }
    }

    private String solicitarTexto(String mensaje) {
        System.out.print(mensaje + " ");
        return scanner.nextLine().trim();
    }

    private double solicitarDecimal(String mensaje) {
        while (true) {
            System.out.print(mensaje + " ");
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número decimal válido.");
            }
        }
    }

    private String solicitarContraseniaSegura() {
        String contrasenia, confirmacion;
        boolean esValida = false;
        do {
            contrasenia = solicitarTexto("Contraseña (8-12 caracteres, mayús, minús, número y carácter especial): ");
            confirmacion = solicitarTexto("Confirme su contraseña: ");

            if (contrasenia.equals(confirmacion) && Cuenta.esContraseniaValida(contrasenia)) {
                esValida = true;
            } else {
                System.out.println("Error: Las contraseñas no coinciden o no cumplen con los requisitos de seguridad.");
            }
        } while (!esValida);
        return contrasenia;
    }

    private LocalDate solicitarFecha(String mensaje, String patron) {
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern(patron);
        while (true) {
            String fechaTexto = solicitarTexto(mensaje);
            try {

                return LocalDate.parse(fechaTexto, formateador);
            } catch (DateTimeParseException e) {
                System.out.println("Error: Fecha inválida o formato incorrecto. Use el formato: " + patron);

            }
        }
    }

    private void verTop3() {
        System.out.println("\n=== TOP 3 MEJOR CALIFICADAS ===");
        imprimirResultados(aplicacion.obtenerTop3MejorCalificadas());

        System.out.println("\n=== TOP 3 MÁS COMPRADAS ===");
        imprimirResultados(aplicacion.obtenerTop3MasCompradas());

        System.out.println("\n=== TOP 3 MÁS INCLUIDAS EN LISTAS ===");
        imprimirResultados(aplicacion.obtenerTop3MasIncluidasEnListas());
    }
}