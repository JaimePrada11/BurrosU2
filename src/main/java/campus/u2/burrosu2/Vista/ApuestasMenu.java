/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package campus.u2.burrosu2.Vista;

import campus.u2.burrosu2.Controlador.ApuestasControlador;
import campus.u2.burrosu2.Controlador.BurroControlador;
import campus.u2.burrosu2.Controlador.CompetenciaControlador;
import campus.u2.burrosu2.Controlador.ParticipanteControlador;
import java.sql.SQLException;
import java.util.Scanner;
import campus.u2.burrosu2.modelo.clases.Competencia;
import campus.u2.burrosu2.modelo.clases.Burro;
import campus.u2.burrosu2.modelo.clases.Apuesta;
import campus.u2.burrosu2.modelo.clases.Participante;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class ApuestasMenu {

    public static void Menu() {

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n========== MENÚ DE APUESTAS ==========");
            System.out.println("1. Realizar Apuesta");
            System.out.println("2. Listar Todas las Apuestas");
            System.out.println("3. Listar Apuestas por Competencia");
            System.out.println("4. Listar Apuestas por Participante");
            System.out.println("5. Obtener Ganancias");
            System.out.println("6. Calcular Ganancias por Competencia y Participante");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Opción inválida, por favor ingrese un número: ");
                scanner.next();
            }
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir línea restante

            switch (opcion) {
                case 1:
                    realizarApuesta(scanner);
                    break;
                case 2:
                    listarApuestas();
                    break;
                case 3:
                    listarApuestasPorCompetencia(scanner);
                    break;
                case 4:
                    listarApuestasPorParticipante(scanner);
                    break;
                case 5:
                    calcularGananciasPorCompetencia(scanner);
                    break;
                case 6:
                    calcularGanancias(scanner);
                    break;
                case 7:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 7);

        scanner.close();
    }

    private static void realizarApuesta(Scanner scanner) {
        System.out.print("Ingrese el ID del participante: ");
        int idParticipante = obtenerNumeroValido(scanner);
        System.out.print("Ingrese el ID del burro: ");
        int idBurro = obtenerNumeroValido(scanner);
        System.out.print("Ingrese el ID de la competencia: ");
        int idCompetencia = obtenerNumeroValido(scanner);
        System.out.print("Ingrese el monto apostado: ");
        double montoApostado = obtenerDoubleValido(scanner);

        Participante participante = ParticipanteControlador.obtenerParticipante(idParticipante);
        Burro burro = BurroControlador.obtenerBurro(idBurro);
        Competencia competencia = CompetenciaControlador.buscarCompetenciaPorId(idCompetencia);
        boolean realizada = ApuestasControlador.realizarApuesta(participante, burro, competencia, montoApostado);
        System.out.println(realizada ? "Apuesta realizada con éxito." : "Error al realizar la apuesta.");
    }

    private static void listarApuestas() {
        try {
            List<Apuesta> apuestas = ApuestasControlador.listarApuestas();
            if (apuestas.isEmpty()) {
                System.out.println("No hay apuestas registradas.");
            } else {
                System.out.println("===== Apuestas Registradas =====");
                apuestas.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar apuestas: " + e.getMessage());
        }
    }

    private static void listarApuestasPorCompetencia(Scanner scanner) {
        System.out.print("Ingrese el ID de la competencia: ");
        int idCompetencia = obtenerNumeroValido(scanner);

        try {
            List<Apuesta> apuestas = ApuestasControlador.listarApuestasPorCompetencia(idCompetencia);
            if (apuestas.isEmpty()) {
                System.out.println("No hay apuestas registradas para esta competencia.");
            } else {
                System.out.println("===== Apuestas por Competencia =====");
                apuestas.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar apuestas por competencia: " + e.getMessage());
        }
    }

    private static void listarApuestasPorParticipante(Scanner scanner) {
        System.out.print("Ingrese la cédula del participante: ");
        String cedula = scanner.nextLine();

        try {
            List<Apuesta> apuestas = ApuestasControlador.listarApuestasPorParticipante(cedula);
            if (apuestas.isEmpty()) {
                System.out.println("No hay apuestas registradas para este participante.");
            } else {
                System.out.println("===== Apuestas por Participante =====");
                apuestas.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar apuestas por participante: " + e.getMessage());
        }
    }

    private static void calcularGananciasPorCompetencia(Scanner scanner) {
        try {
            System.out.print("Ingrese el ID de la competencia: ");
            int idCompetencia = obtenerNumeroValido(scanner);

            double ganancias = ApuestasControlador.calcularGananciasPorCompetencia(idCompetencia);
            System.out.println("Las ganancias de competencia son: " + ganancias);
        } catch (SQLException e) {
            System.out.println("Error al calcular las ganancias: " + e.getMessage());
        }
    }

    private static void calcularGanancias(Scanner scanner) {
        System.out.print("Ingrese el ID de la competencia: ");
        int idCompetencia = obtenerNumeroValido(scanner);
        System.out.print("Ingrese la cédula del participante: ");
        String cedula = scanner.nextLine();

        try {
            double ganancia = ApuestasControlador.calcularGananciasApuestaPorCompetenciaYParticipante(idCompetencia, cedula);
            System.out.println("Ganancias calculadas: " + ganancia);
        } catch (SQLException e) {
            System.out.println("Error al calcular ganancias: " + e.getMessage());
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

    private static double obtenerDoubleValido(Scanner scanner) {
        while (!scanner.hasNextDouble()) {
            System.out.println("Entrada no válida. Ingrese un número decimal.");
            scanner.next();
        }
        double numero = scanner.nextDouble();
        scanner.nextLine();
        return numero;
    }
}
