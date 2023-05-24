/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

/**
 *
 * @author fiwie
 */
import ui.Main;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Flight {

    String destination;
    UUID flightId;
    ArrayList<Passenger> passengers;

    /**
     * Konstruktor třídy Flight.
     *
     * @param destination Destinace letu
     */
    public Flight(String destination) {
        this.destination = destination;
        this.passengers = new ArrayList<>();
        this.flightId = UUID.randomUUID();
    }

    /**
     * Získá destinaci letu.
     *
     * @return Destinace letu
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Nastaví destinaci letu.
     *
     * @param destination Destinace letu
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Získá ID letu.
     *
     * @return ID letu
     */
    public UUID getFlightId() {
        return flightId;
    }

    /**
     * Získá seznam cestujících na letu.
     *
     * @return Seznam cestujících
     */
    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    /**
     * Přidá cestujícího na let.
     *
     * @param passenger Cestující
     */
    public void addPassenger(Passenger passenger) {
        this.passengers.add(passenger);
    }

    /**
     * Odebere cestujícího z letu.
     *
     * @param passenger Cestující
     */
    public void removePassenger(Passenger passenger) {
        this.passengers.remove(passenger);
    }

    /**
     * Přepsaná metoda toString pro třídu Flight.
     *
     * @return Textová reprezentace objektu Flight
     */
    @Override
    public String toString() {
        return "Flight {" +
                "destination= '" + destination + '\'' +
                ", flightId= " + flightId +
                ", passengers= " + passengers +
                '}';
    }

    /**
     * Metoda pro zrušení letu.
     */
    public static void cancelFlight() {
        System.out.println("Zrušení existující destinace");
        System.out.println("Vyberte jakou destinaci chcete zrušit");
        Main.airport.displayAllAvailableFlights();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Zadejte destinaci: ");
        String flightDestination = scanner.nextLine();

        Flight chosenFlight = Main.airport.getFlight(flightDestination);

        if (chosenFlight != null) {
            Main.airport.cancelFlight(chosenFlight);
            System.out.println("Let do " + chosenFlight + " úspěšně zrušen. \n");
        } else {
            System.out.println("Let nelze zrušit, protože do této destinace aktuálně nejsou dostupné žádné lety\n");
        }
        System.out.println("Co chcete vybrat dál? Vyberte číslo od 0-6: \n");
    }

    /**
     * Metoda pro vytvoření letů.
     */
    public static void makeFlights() {
        List<String> destinations = readDestinationsFromFile("src/flights.txt");
        for (String destination : destinations) {
            Flight flight = new Flight(destination);
            Main.airport.addFlight(flight);
        }
    }

    /**
     * Metoda pro načtení destinací letů ze souboru.
     *
     * @param fileName Název souboru
     * @return Seznam destinací letů
     */
    public static List<String> readDestinationsFromFile(String fileName) {
        List<String> destinations = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                destinations.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Soubor nebyl nalezen: " + fileName);
        }
        return destinations;
    }

    /**
     * Metoda pro vyhledání letů.
     */
    public static void searchFlights() {
        System.out.println("Chcete-li vyhledat lety, zadejte prosím destinaci");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Zadejte destinaci: ");
        String destination = scanner.nextLine();
        Main.airport.searchFlights(destination);
        System.out.println("\n Co chcete vybrat dál? Vyberte číslo od 0-6: \n");
    }

    /**
     * Metoda pro zobrazení letů.
     */
    public static void displayFlights() {
        System.out.println("Vybrali jste první moznost: ");
        Main.airport.displayAllAvailableFlights();
        System.out.println("\n Co chcete vybrat dál? Vyberte číslo od 0-6: \n");
    }

    /**
     * Metoda pro přidání nového letu.
     */
    public static void addNewFlight() {
        System.out.println("Přidání nových letů do systému");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Zadejte destinaci");
        String flightDestination = scanner.nextLine();

        Flight addedFlight = new Flight(flightDestination);
        Main.airport.addFlight(addedFlight);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("flights.txt", true))) {
            writer.write(flightDestination);
            writer.newLine(); // Přidá nový řádek po každém zapsaném letu
            System.out.println("\nLet do " + flightDestination + " úspěšně přidán do systému.");
        } catch (IOException e) {
            System.out.println("Nastala chyba při zápisu do souboru: " + e.getMessage());
        }

        System.out.println("\nCo chcete vybrat dál? Vyberte číslo od 0-6: \n");
    }
}