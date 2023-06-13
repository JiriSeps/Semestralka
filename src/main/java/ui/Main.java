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
    static Scanner scanner = new Scanner(System.in);

    /**
     * Hlavní metoda programu.
     *
     * @param args Argumenty příkazové řádky
     */
    public static void main(String[] args) {

        System.out.println("\n Vítejte v rezervačním programu. Chcete pracovat s datovými (.dat) nebo textovými (.txt) soubory?");
        fileFormat = scanner.nextLine().toLowerCase();
        
        while (!fileFormat.equals("dat") && !fileFormat.equals("txt")) {
            System.out.println("Neplatný formát souboru. Zadejte buď 'dat' nebo 'txt'.");
            fileFormat = scanner.nextLine().toLowerCase();
        }

        System.out.println("Vyberte z následujících možností\n");
        Flight.makeFlights();
        while (true) {
            System.out.println("1. Zobrazení všech dostupných destinací \n2. Přidání destinace \n3. Přidání uživatele\n4. Rezervace letu\n5. Zrušení destinací\n6. Zrušení rezervovaných letů\n7. Vypsání rezervovaných letů\n8. Nápověda\n9. Uložení dat\n0. Exit");

            System.out.println("Zadejte možnost zde: ");
            String userInput = scanner.nextLine();
            try {
                int option = Integer.parseInt(userInput);
                switch (option) {
                    case 1:
                        displayFlightList();
                        break;
                    case 2:
                        addDestination();
                        break;
                    case 3:
                        addPassenger();
                        break;
                    case 4:
                        askUserForFlightDetails();
                        break;
                    case 5:
                        deleteDestination();
                        break;
                    case 6:
                        reservedFlightCancellation();
                        break;
                    case 7:
                        Passenger.displayReservedFlightsSortedByDate();
                        break;
                    case 8:
                        help();
                        break;
                    case 9:
                        dataSave();
                        break;
                    case 0:
                        util.SoundPlayer.play("sound.wav");
                        Thread.sleep(1500);
                        System.exit(0);
                    default:
                        System.out.println("Zadaná špatná hodnota, zkus to znovu.");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Zadaná špatná hodnota, zkus to znovu.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void dataSave() {
        System.out.println("Chcete uložit data do formátu .txt nebo .dat (txt/bin)?");
        String format = scanner.nextLine();
        Airport.saveData(Main.reservationData, format, "reservedflights");
        Airport.saveData(Main.passengerData, format, "users");
        Airport.saveData(Main.addedFlightData, format, "flights");
    }

    public static void deleteDestination() {
        System.out.println("Zadejte destinaci: ");
        String cancelFlightDestination = scanner.nextLine();
        String cancelMessage = Flight.cancelFlight(cancelFlightDestination);
        System.out.println(cancelMessage);
    }

    public static void displayFlightList() {
        System.out.println("Vybrali jste první možnost:");
        Flight.displayFlights();
        System.out.println("\nCo chcete vybrat dál? Vyberte číslo od 0-9:\n");
    }

    public static void addDestination() {
        System.out.println("Zadejte destinaci");
        String flightDestination = scanner.nextLine();
        String message = Flight.addNewFlight(flightDestination);
        System.out.println(message);
    }

    public static void addPassenger() {
        System.out.println("Přidejte nového cestujícího do systému letiště");
        System.out.println("Zadejte informace o cestujících");
        System.out.println("Zadejte jméno: ");
        String name = scanner.nextLine();
        System.out.println("Zadejte email: ");
        String email = scanner.nextLine();
        String passengerInfo = Passenger.addNewPassenger(name, email);
        System.out.println(passengerInfo);
    }

    public static void reservedFlightCancellation() {
        System.out.println("Zrušení rezervovaných letů");
        System.out.println("Zadejte svůj email: ");
        String inputEmail = scanner.nextLine();
        System.out.println("Zadejte destinaci letu, kterou chcete zrušit: ");
        String inputDestination = scanner.nextLine();
        // Vyzve uživatele, aby zadali třídu letu
        System.out.println("Zadejte třídu letu, kterou chcete zrušit: ");
        String inputFlightClass = scanner.nextLine();

        System.out.println("Zadejte datum odletu ve formátu DD.MM.YYYY: ");
        String inputDate = scanner.nextLine();

        String emailAndFlightData = Passenger.cancelReservedFlights(inputEmail + "," + inputDestination + "," + inputFlightClass + "," + inputDate);

    }

    public static void help() {
        System.out.println("Nápověda k programu:");
        System.out.println("1. Zobrazení všech dostupných destinací - Tato možnost vypíše seznam všech dostupných destinací.");
        System.out.println("2. Přidání destinace - Tato možnost umožní uživateli přidat novou destinaci do systému.");
        System.out.println("3. Přidání uživatele - Tato možnost umožní uživateli přidat nového uživatele do systému.");
        System.out.println("4. Rezervace letu - Tato možnost umožní uživateli rezervovat let na zvolenou destinaci.");
        System.out.println("5. Zrušení destinací - Tato možnost umožní uživateli zrušit destinaci.");
        System.out.println("6. Zrušení rezervovaných letů - Tato možnost umožní uživateli zrušit jeho rezervované lety.");
        System.out.println("7. Vypsání rezervovaných letů - Tato možnost vypíše seznam všech rezervovaných letů uživatele.");
        System.out.println("8. Nápověda - Tato možnost vypíše nápovědu k jednotlivým možnostem.");
        System.out.println("9. Uložení dat - Tato možnost uloží aktuální data do souboru.");
    }

    public static void askUserForFlightDetails() {
        System.out.println("Zadejte údaje cestujícího");
        System.out.println("Zadejte jméno: ");
        String name = scanner.nextLine();

        System.out.println("Zadejte email: ");
        String emails = scanner.nextLine();

        String validEmail = Passenger.checkEmail(emails);

        if (validEmail == null) {
            System.out.println("Zadal jste špatný nebo neplatný email. Prosím, zkuste to znovu.");
            return;
        }
        System.out.println("\n Vyberte si z následujících letů: ");
        Main.airport.loadAndSortFlights();
        System.out.println("Zadejte destinaci : ");
        String flightDestination = scanner.nextLine();
        
        String destinationCheck = Passenger.destinationCheck(flightDestination);
        
        if (destinationCheck == null) {
            System.out.println("Zadaná destinace nebyla nalezena. Zadejte prosím destinaci znovu:");
            return;
        }
        System.out.println("Zadejte třídu letu (BUSINESS nebo ECONOMY): ");
        String flightClassInput = scanner.nextLine();
        System.out.println("Zadejte datum letu (DD.MM.YYYY): ");
        String flightDate = scanner.nextLine();
        while (!Passenger.checkDate(flightDate)) {
            System.out.println("Zadejte datum letu (DD.MM.YYYY): ");
            flightDate = scanner.nextLine();
        }

        Passenger.bookFlight(name, emails, flightDestination, flightClassInput, flightDate);
    }
}
