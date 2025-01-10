package campus.u2.burrosu2.Vista;

import campus.u2.burrosu2.Controlador.BurroControlador;
import campus.u2.burrosu2.Controlador.DueñoControlador;
import campus.u2.burrosu2.Controlador.RazaControlador;
import campus.u2.burrosu2.modelo.clases.Dueño;
import campus.u2.burrosu2.modelo.clases.Raza;
import campus.u2.burrosu2.modelo.clases.Burro;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class BurroMenu {

    public static void Menu() throws SQLException {

        Scanner scanner = new Scanner(System.in);
        int option = -1;

        do {
            System.out.println("\n--- Menu Burros ---");
            System.out.println("1. Registrar Burro");
            System.out.println("2. Actualiza Burro");
            System.out.println("3. Eliminar Burro");
            System.out.println("4. Listar los burros");
            System.out.println("0. Salir");
            System.out.print("Selecciona una opcion ");

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 ->
                    crearBurro();
                case 2 ->
                    actualizarBurro();
                case 3 ->
                    eliminarBurro();
                case 4 ->
                    listarBurros();
                case 0 ->
                    System.out.println("Volviendo al menu Anterior");
                default ->
                    System.out.println("Opcion Invalida");
            }
        } while (option != 0);
    }

    private static void buscarBurro() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static void crearBurro() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n--- REGISTRANDO UN BURRO ---");

        System.out.print("Ingrese la cédula del dueño: ");
        String cedula = obtenerEntradaTextoValida(scanner);

        Dueño d = DueñoControlador.obtenerDueñoCedula(cedula);
        if (d == null) {
            d = registrarDueño(scanner, cedula);
        }

        System.out.print("Ingrese el nombre del burro: ");
        String name = obtenerEntradaTextoValida(scanner);

        System.out.print("Ingrese la edad del burro: ");
        int edad = obtenerNumeroValido(scanner);

        int raza = seleccionarRaza(scanner);

        boolean resultado = BurroControlador.creaBurro(name, edad, RazaControlador.obtenerRaza(raza), d);

        if (resultado) {
            System.out.println("Burro registrado exitosamente.");
        } else {
            System.out.println("Error al registrar el burro.");
        }
    }

    private static int seleccionarRaza(Scanner scanner) throws SQLException {
        List<Raza> tiposrazas = RazaControlador.listarRazas();

        System.out.println("\n--- Selecciona la raza ---");
        for (int i = 0; i < tiposrazas.size(); i++) {
            System.out.println((i + 1) + ". " + tiposrazas.get(i).getNombre());
        }

        int opcion = 0;
        boolean validChoice = false;
        while (!validChoice) {
            System.out.print("Ingresa el número de la raza: ");
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                if (opcion > 0 && opcion <= tiposrazas.size()) {
                    validChoice = true;
                } else {
                    System.out.println("Opción inválida.");
                }
            } else {
                System.out.println("Ingresa un número válido.");
                scanner.nextLine();
            }
        }

        return tiposrazas.get(opcion - 1).getIdRaza();
    }

    private static Dueño registrarDueño(Scanner scanner, String cedula) throws SQLException {
        System.out.println("\n--- REGISTRANDO UN NUEVO DUEÑO ---");

        System.out.print("Ingrese el nombre del dueño: ");
        String nombre = obtenerEntradaTextoValida(scanner);

        boolean creado = DueñoControlador.crearDueño(nombre, cedula);
        if (creado) {
            return DueñoControlador.obtenerDueñoCedula(cedula);
        } else {
            System.out.println("Error al registrar el dueño.");
            return null;
        }
    }

    private static int obtenerNumeroValido(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Entrada no válida. Ingrese un número.");
            scanner.next();
        }
        int numero = scanner.nextInt();
        scanner.nextLine();
        return numero;
    }

    private static String obtenerEntradaTextoValida(Scanner scanner) {
        String entrada = scanner.nextLine().trim();
        while (entrada.isEmpty()) {
            System.out.println("La entrada no puede estar vacía. Ingrese texto válido.");
            entrada = scanner.nextLine().trim();
        }
        return entrada;
    }

    private static void actualizarBurro() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese la cédula del dueño: ");
        String cedula = obtenerEntradaTextoValida(scanner);

        Dueño dueño = DueñoControlador.obtenerDueñoCedula(cedula);
        if (dueño == null) {
            System.out.println("Dueño no encontrado.");
            return;
        }

        List<Burro> burrosDelDueño = BurroControlador.mostrarBurrosDeDueño(cedula);
        if (burrosDelDueño.isEmpty()) {
            System.out.println("Este dueño no tiene burros registrados.");
            return;
        }

        System.out.println("\n--- Seleccione el burro a actualizar ---");
        for (int i = 0; i < burrosDelDueño.size(); i++) {
            System.out.println((i + 1) + ". " + burrosDelDueño.get(i).getNombre() + " (ID: " + burrosDelDueño.get(i).getId() + ")");
        }
        int seleccion = obtenerSeleccionValida(scanner, 1, burrosDelDueño.size());

        Burro burroAActualizar = burrosDelDueño.get(seleccion - 1);

        System.out.print("Ingrese nuevo nombre del burro: ");
        String nombre = obtenerEntradaTextoValida(scanner);

        System.out.print("Ingrese nueva edad del burro: ");
        int edad = obtenerNumeroValido(scanner);

        int idRaza = seleccionarRaza(scanner);
        Raza newRaza = RazaControlador.obtenerRaza(idRaza);

        boolean actualizado = BurroControlador.actualizarBurro(burroAActualizar.getId(), nombre, edad, newRaza);

        if (actualizado) {
            System.out.println("Burro actualizado exitosamente.");
        } else {
            System.out.println("Error al actualizar burro.");
        }
    }

    private static int obtenerSeleccionValida(Scanner scanner, int min, int max) {
        int seleccion = -1;
        while (seleccion < min || seleccion > max) {
            if (scanner.hasNextInt()) {
                seleccion = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Entrada no válida. Por favor, ingrese un número entre " + min + " y " + max);
                scanner.next();
            }
        }
        return seleccion;
    }

    private static void eliminarBurro() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese la cédula del dueño: ");
        String cedula = obtenerEntradaTextoValida(scanner);

        Dueño dueño = DueñoControlador.obtenerDueñoCedula(cedula);
        if (dueño == null) {
            System.out.println("Dueño no encontrado.");
            return;
        }

        List<Burro> burrosDelDueño = BurroControlador.mostrarBurrosDeDueño(cedula);
        if (burrosDelDueño.isEmpty()) {
            System.out.println("Este dueño no tiene burros registrados.");
            return;
        }

        System.out.println("\n--- Seleccione el burro a eliminar ---");
        for (int i = 0; i < burrosDelDueño.size(); i++) {
            System.out.println((i + 1) + ". " + burrosDelDueño.get(i).getNombre() + " (ID: " + burrosDelDueño.get(i).getId() + ")");
        }
        int seleccion = obtenerSeleccionValida(scanner, 1, burrosDelDueño.size());

        Burro burroAEliminar = burrosDelDueño.get(seleccion - 1);

        System.out.print("¿Está seguro de que desea eliminar el burro " + burroAEliminar.getNombre() + "? (S/N): ");
        String confirmacion = scanner.nextLine().trim().toUpperCase();
        if (!confirmacion.equals("S")) {
            System.out.println("Eliminación cancelada.");
            return;
        }

        boolean borrado = BurroControlador.borrarBurro(burroAEliminar.getId());
        if (borrado) {
            System.out.println("Burro eliminado exitosamente.");
        } else {
            System.out.println("Error al eliminar burro.");
        }
    }

    private static void listarBurros() throws SQLException {
        List<Burro> burros = BurroControlador.listarBurros();
        if (burros.isEmpty()) {
            System.out.println("No hay burros registrados");
        } else {
            System.out.println("---- Lista de Burros ----");
            burros.stream()
                    .map(burro -> "ID: " + burro.getId() + ", Nombre: "
                    + burro.getNombre() + ", Edad: " + burro.getEdad()
                    + ", Raza: " + burro.getRaza().getNombre() )
                    .forEach(System.out::println);

        }
    }

}
