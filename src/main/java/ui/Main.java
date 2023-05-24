/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

/**
 *
 * @author fiwie
 */
import util.Airport;
import app.Flight;
import app.Passenger;
import java.util.Scanner;

public class Main {
    public static Airport airport = new Airport();
    static String email = "";

    /**
     * Hlavní metoda programu.
     *
     * @param args Argumenty příkazové řádky
     */
    public static void main(String[] args) {

        Flight.makeFlights();
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n Vítejte v rezervačním programu. Vyberte z následujících možností\n");
        while (true) {
            System.out.println("1. Zobrazení všech dostupných destinací \n2. Přidání destinace \n3. Přidání uživatele\n4. Rezervace letu\n5. Zrušení letů\n6. Vyhledat lety\n0. Exit");

            System.out.println("Zadejte možnost zde: ");
            String userInput = scanner.nextLine();
            try {
                int option = Integer.parseInt(userInput);
                switch (option) {
                    case 1:
                        Flight.displayFlights();
                        break;
                    case 2:
                        Flight.addNewFlight();
                        break;
                    case 3:
                        Passenger.addNewPassenger();
                        break;
                    case 4:
                        Passenger.bookFlight();
                        break;
                    case 5:
                        Flight.cancelFlight();
                        break;
                    case 6:
                        Flight.searchFlights();
                        break;
                    case 0:
                        System.exit(0);
                    default:
                        throw new Exception("Nesprávná možnost zadána, zkuste znovu.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }

    }

}
