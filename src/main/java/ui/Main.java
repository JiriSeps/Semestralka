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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static Airport airport = new Airport();
    public static List<String> passengerData = new ArrayList<>();
    public static List<String> addedFlightData = new ArrayList<>();
    public static List<String> reservationData = new ArrayList<>();
    public static String fileFormat;

    /**
     * Hlavní metoda programu.
     *
     * @param args Argumenty příkazové řádky
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("\n Vítejte v rezervačním programu. Chcete pracovat s datovými (.dat) nebo textovými (.txt) soubory?");
        fileFormat = scanner.nextLine().toLowerCase();

        System.out.println("Vyberte z následujících možností\n");
        Flight.makeFlights();
        while (true) {
            System.out.println("1. Zobrazení všech dostupných destinací \n2. Přidání destinace \n3. Přidání uživatele\n4. Rezervace letu\n5. Zrušení letů\n6. Zrušení rezervovaných letů\n7. Vypsání rezervovaných letů\n8. Uložení dat\n0. Exit");

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
                        Passenger.cancelReservedFlights();
                        break;
                    case 7:
                        Passenger.displayReservedFlightsSortedByDate();
                        break;
                    case 8:
                        System.out.println("Chcete uložit data do formátu .txt nebo .dat (txt/bin)?");
                        String format = scanner.nextLine();
                        Airport.saveData(Main.reservationData, format, "reservedflights");
                        Airport.saveData(Main.passengerData, format, "users");
                        Airport.saveData(Main.addedFlightData, format, "flights");
                        break;
                    case 0:
                        util.SoundPlayer.play("sound.wav");
                        Thread.sleep(1500);
                        System.exit(0);
                    default:
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }
    }
}
