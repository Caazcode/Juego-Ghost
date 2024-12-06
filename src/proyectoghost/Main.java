/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoghost;

/**
 *
 * @author adrianaguilar
 */
// File: src/main/java/Main.java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GhostGame game = new GhostGame();
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("Menu de Inicio \n Juego Fantasmas Aguilar");
            System.out.println("1- Login");
            System.out.println("2- Crear Player");
            System.out.println("3- Salir");
            System.out.print("Seleccione una opción: ");
            option = scanner.nextInt();
            scanner.nextLine(); 
            
            switch (option) {
                case 1 -> game.login();
                case 2 -> game.createPlayer();
                case 3 -> System.out.println("Saliendo del juego...");
                default -> System.out.println("Opción no válida.");
            }
        } while (option != 3);
    }
    
    
}

