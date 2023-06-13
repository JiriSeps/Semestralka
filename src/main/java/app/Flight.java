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
import java.util.ArrayList;
import java.util.List;

/**
 * Třída Flight představuje let s danou destinací. Implementuje rozhraní
 * Comparable pro možnost porovnání letů podle destinace.
 */
public class Flight implements Comparable<Flight> {

    String destination;
    ArrayList<Passenger> passengers;

    /**
     * Konstruktor pro vytvoření nového letu se zadanou destinací. Seznam
     * pasažérů je inicializován jako prázdný a generuje se jedinečné ID letu.
     *
     * @param destination destinace nového letu
     */
    public Flight(String destination) {
        this.destination = destination;
        this.passengers = new ArrayList<>();
    }

    /**
     * Metoda pro získání destinace letu.
     *
     * @return destinace letu
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Metoda pro nastavení destinace letu.
     *
     * @param destination destinace letu
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Metoda pro získání seznamu pasažérů letu.
     *
     * @return seznam pasažérů
     */
    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    /**
     * Metoda pro přidání pasažéra na let.
     *
     * @param passenger pasažér
     */
    public void addPassenger(Passenger passenger) {
        this.passengers.add(passenger);
    }

    /**
     * Metoda pro odstranění pasažéra z letu.
     *
     * @param passenger pasažér
     */
    public void removePassenger(Passenger passenger) {
        this.passengers.remove(passenger);
    }

    /**
     * Přepisuje metodu toString pro třídu Flight.
     *
     * @return Textová reprezentace objektu Flight
     */
    @Override
    public String toString() {
        return "Flight {"
                + "destination='" + destination + '\''
                + ", passengers=" + passengers
                + '}';
    }

    /**
     * Metoda pro zrušení daného letu ze systému a aktualizaci datového souboru
     * letů. Pokud daný let neexistuje, zobrazí se chybové hlášení.
     */
    public static String cancelFlight(String flightDestination) {
        Flight chosenFlight = Main.airport.getFlight(flightDestination);

        if (chosenFlight != null) {
            Main.airport.cancelFlight(chosenFlight);

            List<String> lines = util.Airport.readData("./src/data/flights." + ui.Main.fileFormat);
            lines.removeIf(line -> line.contains(flightDestination));
            util.Airport.writeData(lines, "./src/data/flights." + ui.Main.fileFormat);

            return "Destinace " + chosenFlight + " úspěšně zrušena. Destinace " + flightDestination + " byla úspěšně odstraněna ze souboru.";
        } else {
            return "Destinaci nelze zrušit, protože neexistuje v souboru";
        }
    }

    /**
     * Metoda pro vytvoření nových letů pro každou destinaci ze souboru s daty
     * letů a jejich přidání do systému.
     */
    public static void makeFlights() {
        List<String> destinations = util.Airport.readData("./src/data/flights." + Main.fileFormat);
        for (String destination : destinations) {
            Flight flight = new Flight(destination);
            Main.airport.addFlight(flight);
        }
    }


    /**
     * Metoda pro zobrazení všech letů v systému seřazených podle destinace.
     */
    public static void displayFlights() {
        Main.airport.loadAndSortFlights();
    }

    /**
     * Metoda pro přidání nového letu do systému. Destinace je zadaná uživatelem
     * a nový let je přidán do systému a datového souboru letů.
     */
    public static String addNewFlight(String flightDestination) {
        Flight addedFlight = new Flight(flightDestination);
        Main.airport.addFlight(addedFlight);

        String flightInfo = flightDestination;
        Main.addedFlightData.add(flightInfo);

        return "\nLet do " + flightDestination + " úspěšně přidán do systému.";
    }

    /**
     * Porovnává tento let s jiným letem pro účely řazení. Vrací záporné číslo,
     * nulu nebo kladné číslo v závislosti na tom, zda je destinace tohoto letu
     * menší než, rovna nebo větší než destinace druhého letu.
     *
     * @param other druhý let k porovnání
     * @return záporné číslo, nula nebo kladné číslo, pokud je destinace tohoto
     * letu menší, rovna nebo větší než destinace druhého letu
     */
    @Override
    public int compareTo(Flight other) {
        return this.destination.compareTo(other.destination);
    }
}
