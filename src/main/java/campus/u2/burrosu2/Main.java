/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package campus.u2.burrosu2;

import campus.u2.burrosu2.Vista.ApuestasMenu;
import campus.u2.burrosu2.Vista.BurroMenu;
import campus.u2.burrosu2.Vista.CompetenciasMenu;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class Main {

    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n---- Menu Principal ----");
            System.out.println("1. Burros");
            System.out.println("2. Competencias");
            System.out.println("3. Apuestas");
            System.out.println("4. Salir");
            System.out.print("Selecciona una opción: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.next();  
            }
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 ->
                    BurroMenu.Menu();
                case 2 ->
                    CompetenciasMenu.Menu();
                case 3 ->
                    ApuestasMenu.Menu();
                case 4 -> {
                    System.out.println("Saliendo...");
                    scanner.close();
                    return;
                }
                default ->
                    System.out.println("Opcion Invalida, seleccione una opción del 1 al 4.");
            }
        }
    }
}
