/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package campus.u2.burrosu2.Vista;

import campus.u2.burrosu2.Controlador.CompetenciaControlador;
import campus.u2.burrosu2.modelo.clases.Competencia;
import campus.u2.burrosu2.modelo.clases.Burro;
import java.sql.SQLException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CompetenciasMenu {

    public static void Menu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n========== MENÚ DE COMPETENCIAS ==========");
            System.out.println("1. Registrar Competencia");
            System.out.println("2. Actualizar Competencia");
            System.out.println("3. Finalizar Competencia");
            System.out.println("4. Listar Competencias");
            System.out.println("5. Registrar Burros a Competencias");
            System.out.println("6. Listar Burros Participando");
            System.out.println("7. Asignar Puestos a los Burros");
            System.out.println("8. Listar Ganadores");
            System.out.println("9. Buscar Competencias");
            System.out.println("10. Salir");
            System.out.print("Seleccione una opción: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Opción inválida, por favor ingrese un número: ");
                scanner.next();
            }
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir línea restante

            switch (opcion) {
                case 1:
                    registrarCompetencia(scanner);
                    break;
                case 2:
                    actualizarCompetencia(scanner);
                    break;
                case 3:
                    finalizarCompetencia(scanner);
                    break;
                case 4:
                    listarCompetencias();
                    break;
                case 5:
                    registrarBurros(scanner);
                    break;
                case 6:
                    listarBurrosParticipando(scanner);
                    break;
                case 7:
                    asignarPuestos(scanner);
                    break;
                case 8:
                    listarGanadores(scanner);
                    break;
                case 9:
                    buscarCompetencias(scanner);
                    break;
                case 10:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 10);

        scanner.close();
    }

    private static void registrarCompetencia(Scanner scanner) {
        try {
            System.out.print("Ingrese el nombre de la competencia: ");
            String nombre = obtenerEntradaTextoValida(scanner);
            System.out.print("Ingrese la fecha (YYYY-MM-DD): ");
            LocalDate fecha = LocalDate.parse(scanner.nextLine().trim());
            System.out.print("Ingrese el lugar: ");
            String lugar = obtenerEntradaTextoValida(scanner);

            boolean creada = CompetenciaControlador.crearCompetencia(nombre, fecha, lugar);
            System.out.println(creada ? "Competencia registrada exitosamente." : "Error al registrar la competencia.");
        } catch (DateTimeParseException e) {
            System.out.println("Fecha inválida. Por favor, ingrese una fecha en el formato YYYY-MM-DD.");
        } catch (Exception e) {
            System.out.println("Error al registrar la competencia: " + e.getMessage());
        }
    }

    private static void actualizarCompetencia(Scanner scanner) {
        try {
            System.out.print("Ingrese el ID de la competencia: ");
            int id = obtenerNumeroValido(scanner);
            System.out.print("Ingrese el nuevo nombre: ");
            String nombre = obtenerEntradaTextoValida(scanner);
            System.out.print("Ingrese la nueva fecha (YYYY-MM-DD): ");
            LocalDate fecha = LocalDate.parse(scanner.nextLine().trim());
            System.out.print("Ingrese el nuevo lugar: ");
            String lugar = obtenerEntradaTextoValida(scanner);

            boolean actualizada = CompetenciaControlador.editarCompetencia(nombre, fecha, lugar, id);
            System.out.println(actualizada ? "Competencia actualizada correctamente." : "Error al actualizar la competencia.");
        } catch (DateTimeParseException e) {
            System.out.println("Fecha inválida. Por favor, ingrese una fecha en el formato YYYY-MM-DD.");
        } catch (Exception e) {
            System.out.println("Error al actualizar la competencia: " + e.getMessage());
        }
    }

    private static void finalizarCompetencia(Scanner scanner) {
        try {
            System.out.print("Ingrese el ID de la competencia: ");
            int id = obtenerNumeroValido(scanner);

            boolean finalizada = CompetenciaControlador.terminarCompetencia(id);
            System.out.println(finalizada ? "Competencia finalizada exitosamente." : "Error al finalizar la competencia.");
        } catch (Exception e) {
            System.out.println("Error al finalizar la competencia: " + e.getMessage());
        }
    }

    private static void listarCompetencias() {
        try {
            List<Competencia> competencias = CompetenciaControlador.listarCompetencias();
            if (competencias.isEmpty()) {
                System.out.println("No hay competencias registradas.");
            } else {
                System.out.println("===== Competencias Registradas =====");
                competencias.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error al listar competencias: " + e.getMessage());
        }
    }

    private static void registrarBurros(Scanner scanner) {
        try {
            System.out.print("Ingrese el ID de la competencia: ");
            int idCompetencia = obtenerNumeroValido(scanner);
            System.out.print("Ingrese el ID del burro: ");
            int idBurro = obtenerNumeroValido(scanner);

            boolean registrado = CompetenciaControlador.registrarBurroEnCompetencia(idCompetencia, idBurro);
            System.out.println(registrado ? "Burro registrado exitosamente." : "Error al registrar el burro.");
        } catch (Exception e) {
            System.out.println("Error al registrar burro: " + e.getMessage());
        }
    }

    private static void listarBurrosParticipando(Scanner scanner) {
        try {
            System.out.print("Ingrese el ID de la competencia: ");
            int idCompetencia = obtenerNumeroValido(scanner);

            List<Burro> burros = CompetenciaControlador.listarBurrosCompetencia(idCompetencia);
            if (burros.isEmpty()) {
                System.out.println("No hay burros registrados en esta competencia.");
            } else {
                System.out.println("===== Burros Participando =====");
                burros.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error al listar burros: " + e.getMessage());
        }
    }

    private static void asignarPuestos(Scanner scanner) {
        try {
            System.out.print("Ingrese el ID de la competencia: ");
            int idCompetencia = obtenerNumeroValido(scanner);

            List<Burro> burros = CompetenciaControlador.listarBurrosCompetencia(idCompetencia);
            if (burros.isEmpty()) {
                System.out.println("No hay burros registrados en esta competencia.");
                return;
            }

            System.out.println("===== Burros Participantes =====");
            for (int i = 0; i < burros.size(); i++) {
                Burro burro = burros.get(i);
                System.out.println((i + 1) + ". ID: " + burro.getId() + ", Nombre: " + burro.getNombre());
            }

            for (Burro burro : burros) {
                System.out.print("Ingrese el puesto para el burro " + burro.getNombre() + " (ID: " + burro.getId() + "): ");
                int puesto = obtenerNumeroValido(scanner);

                boolean asignado = CompetenciaControlador.asignarPuesto(idCompetencia, burro.getId(), puesto);
                if (asignado) {
                    System.out.println("Puesto asignado correctamente al burro " + burro.getNombre() + ".");
                } else {
                    System.out.println("Error al asignar puesto al burro " + burro.getNombre() + ".");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al asignar puestos: " + e.getMessage());
        }
    }

    private static void listarGanadores(Scanner scanner) {
        try {
            System.out.print("Ingrese el ID de la competencia: ");
            int idCompetencia = obtenerNumeroValido(scanner);

            try {
                List<Map.Entry<Burro, Integer>> ganadores = CompetenciaControlador.obtenerTresPrimerosPuestos(idCompetencia);

                if (ganadores.isEmpty()) {
                    System.out.println("No hay ganadores registrados o la competencia no tiene puestos asignados.");
                } else {
                    System.out.println("===== Ganadores =====");
                    for (Map.Entry<Burro, Integer> entry : ganadores) {
                        Burro burro = entry.getKey();
                        Integer puesto = entry.getValue();
                        System.out.println("Puesto: " + puesto + " - Burro: " + burro.getNombre());
                    }
                }
            } catch (IllegalStateException e) {
                System.out.println("La competencia aún no está finalizada, por lo que no se pueden listar los ganadores.");
            }
        } catch (SQLException e) {
            System.out.println("Error al listar ganadores: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    private static void buscarCompetencias(Scanner scanner) {
        try {
            System.out.println("1. Buscar por ID");
            System.out.println("2. Buscar por rango de fechas");
            System.out.println("3. Listar competencias activas");
            System.out.print("Seleccione una opción: ");

            int opcion = obtenerNumeroValido(scanner);
            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el ID de la competencia: ");
                    int id = obtenerNumeroValido(scanner);
                    Competencia competencia = CompetenciaControlador.buscarCompetenciaPorId(id);
                    System.out.println(competencia != null ? competencia : "Competencia no encontrada.");
                    break;
                case 2:
                    System.out.print("Ingrese la fecha de inicio (YYYY-MM-DD): ");
                    LocalDate inicio = LocalDate.parse(scanner.nextLine().trim());
                    System.out.print("Ingrese la fecha de fin (YYYY-MM-DD): ");
                    LocalDate fin = LocalDate.parse(scanner.nextLine().trim());

                    List<Competencia> competencias = CompetenciaControlador.buscarCompetenciasPorRangoDeFechas(inicio, fin);
                    if (competencias.isEmpty()) {
                        System.out.println("No hay competencias registradas en el rango de fechas proporcionado.");
                    } else {
                        System.out.println("===== Competencias en Rango de Fechas =====");
                        competencias.forEach(System.out::println);
                    }
                    break;

                case 3:
                    List<Competencia> activas = CompetenciaControlador.obtenerCompetenciasActivas();
                    if (activas.isEmpty()) {
                        System.out.println("No hay competencias activas.");
                    } else {
                        System.out.println("===== Competencias Activas =====");
                        activas.forEach(System.out::println);
                    }
                    break;

                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        } catch (DateTimeParseException e) {
            System.out.println("Fecha inválida. Por favor, ingrese una fecha en el formato YYYY-MM-DD.");
        } catch (Exception e) {
            System.out.println("Error al buscar competencias: " + e.getMessage());
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
}
