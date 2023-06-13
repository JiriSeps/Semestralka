/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import java.util.ArrayList;
import ui.Main;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import util.FlightClassEnum;

/**
 *
 * @author fiwie
 */
public class Passenger {

    static String emails = "";
    private String name;
    private String email;
    private ArrayList<Flight> flights;

    /**
     * Konstruktor třídy Passenger.
     *
     * @param name Jméno cestujícího
     * @param email Email cestujícího
     */
    public Passenger(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * Získá jméno cestujícího.
     *
     * @return Jméno cestujícího
     */
    public String getName() {
        return name;
    }

    /**
     * Přepisuje metodu toString pro třídu Passenger.
     *
     * @return Řetězcová reprezentace objektu Passenger
     */
    @Override
    public String toString() {
        return "Passenger [name=" + name + ", email=" + email + ", id=" + "]";
    }

    /**
     * Metoda pro přidání nového cestujícího do systému letiště. Uživatel ručně
     * zadává informace o cestujícím.
     */
    public static String addNewPassenger(String name, String email) {

        if (!email.contains("@")) {
            return "Nebyl zadán email!";
        }

        String passengerInfo = email;
        Main.passengerData.add(passengerInfo);
        return "\n" + name + " zadáno do systému.\n";
    }

    public static void bookFlight(String name, String emails, String flightDestination, String flightClassInput, String flightDate) {
        boolean validDestination = false;
        while (!validDestination) {
            // Kontrola dostupných destinací
            List<String> flightLines = util.Airport.readData("./src/data/flights." + ui.Main.fileFormat);
            for (String line : flightLines) {
                String[] parts = line.split(",");
                String destination = parts[0];
                if (destination.equals(flightDestination)) {
                    validDestination = true;
                    break;
                }
            }
            if (!validDestination) {
                System.out.println("Zadal jste špatnou nebo neexistující destinaci, zkuste to znovu.");
                return;
            }
        }

        FlightClassEnum flightClass;
        try {
            flightClass = FlightClassEnum.valueOf(flightClassInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Zadaná třída letu není platná. Defaultně nastavena na ECONOMY.");
            flightClass = FlightClassEnum.ECONOMY;
            flightClassInput = "ECONOMY"; // aktualizuje input uživatele na "ECONOMY"
        }

        Passenger addedPassenger = new Passenger(name, emails);
        Flight chosenFlight = Main.airport.getFlight(flightDestination);

        if (chosenFlight != null) {
            Main.airport.bookPassengerOnFlight(addedPassenger, chosenFlight);
            System.out.println(addedPassenger.getName() + " zadal let do " + flightDestination + " s třídou " + flightClassInput + " a datem " + flightDate + "\n");

            String flightData = emails + "," + flightDestination + "," + flightClassInput + "," + flightDate;
            Main.reservationData.add(flightData);

        } else {
            System.out.println("Nenalezeny lety do " + flightDestination + ".\n");
        }
        System.out.println("Co chcete vybrat dál? Vyberte číslo od 0-6: \n");
    }

    /**
     * Metoda pro zrušení rezervovaných letů cestujícího. Uživatel ručně zadává
     * email cestujícího, destinaci letu, třídu letu a datum.
     */
    public static String cancelReservedFlights(String emailAndFlightData) {

        List<String> lines = util.Airport.readData("./src/data/reservedflights." + ui.Main.fileFormat);
        int flightIndex = lines.indexOf(emailAndFlightData);
        if (flightIndex != -1) {
            lines.remove(flightIndex);
            util.Airport.writeData(lines, "./src/data/reservedflights." + ui.Main.fileFormat);
            System.out.println("Let byl úspěšně zrušen");
        } else {
            System.out.println("Tento let nebyl nalezen v našem systému");
        }
        return null;
    }

    /**
     * Metoda pro získání všech rezervovaných letů a seřazení podle data.
     *
     * @return Seznam rezervovaných letů seřazených podle data
     */
    public static List<ReservedFlight> getSortedFlights() {
        String path = "./src/data/reservedflights." + ui.Main.fileFormat;
        List<ReservedFlight> flights = new ArrayList<>();

        List<String> lines = util.Airport.readData(path);
        for (String line : lines) {
            String[] parts = line.split(",");
            String email = parts[0];
            String destination = parts[1];
            FlightClassEnum flightClass = FlightClassEnum.valueOf(parts[2].toUpperCase());
            LocalDate date = LocalDate.parse(parts[3].trim(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            flights.add(new ReservedFlight(email, destination, flightClass, date));
        }

        flights.sort(Comparator.comparing(ReservedFlight::getDate));
        return flights;
    }

    /**
     * Metoda pro zobrazení všech rezervovaných letů seřazených podle data.
     * Vypíše data rezervovaných letů do konzole.
     */
    public static void displayReservedFlightsSortedByDate() {
        List<String> lines = util.Airport.readData("./src/data/reservedflights." + Main.fileFormat);

        List<ReservedFlight> flights = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            LocalDate date = LocalDate.parse(parts[3].trim(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            flights.add(new ReservedFlight(parts[0], parts[1], FlightClassEnum.valueOf(parts[2]), date));
        }

        flights.sort(Comparator.comparing(ReservedFlight::getDate));

        System.out.println("Rezervované lety seřazené podle data:");
        for (ReservedFlight flight : flights) {
            System.out.println(flight.getEmail() + ", " + flight.getDestination() + ", " + flight.getFlightClass() + ", " + flight.getDate().format(DateTimeFormatter.ofPattern("d.M.yyyy")));
        }
    }

    public static String checkEmail(String emails) {
        List<String> lines = util.Airport.readData("./src/data/users." + ui.Main.fileFormat);
        for (String line : lines) {
            String[] parts = line.split(",");
            String email = parts[0];
            if (email.equals(emails)) {
                return email;
            }
        }
        return null;
    }

    public static boolean checkDate(String flightDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
        LocalDate parsedDate;
        try {
            parsedDate = LocalDate.parse(flightDate, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Zadané datum není ve správném formátu (DD.MM.YYYY).");
            return false;
        }

        // Kontrola platnosti dnů v měsíci a přestupných let
        if (parsedDate.getMonth() == Month.FEBRUARY && parsedDate.getDayOfMonth() > 29) {
            System.out.println("Únor nemůže mít více než 29 dní, aniž by to byl přestupný rok.");
            return false;
        } else if (parsedDate.getMonth() == Month.FEBRUARY && parsedDate.getDayOfMonth() > 28 && !Year.isLeap(parsedDate.getYear())) {
            System.out.println("Únor nemůže mít 29 dní, pokud to není přestupný rok.");
            return false;
        } else if (parsedDate.getDayOfMonth() == 31 && (parsedDate.getMonth() == Month.APRIL || parsedDate.getMonth() == Month.JUNE || parsedDate.getMonth() == Month.SEPTEMBER || parsedDate.getMonth() == Month.NOVEMBER)) {
            System.out.println(parsedDate.getMonth().getDisplayName(TextStyle.FULL, new Locale("cs", "CZ")) + " nemůže mít 31 dní.");
            return false;
        }

        LocalDate currentDate = LocalDate.now();
        if (parsedDate.isBefore(currentDate.plusDays(3))) {
            System.out.println("Zadané datum je méně než 3 dny od dnešního data. Vyberte jiné datum.");
            return false;
        }
        return true;
    }
}
