/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;
import ui.Main;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
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
     * @param uuid UUID cestujícího
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
     * Přepsaná metoda toString pro třídu Passenger.
     *
     * @return Textová reprezentace objektu Passenger
     */
    @Override
    public String toString() {
        return "Passenger [name=" + name + ", email=" + email + ", id=" +"]";
    }

    /**
     * Enumerace pro třídy letu (BUSINESS, ECONOMY).
     */
    /**
     * Metoda pro přidání nového cestujícího do systému letiště.
     */
    public static void addNewPassenger() {
        System.out.println("Přidejte nového cestujícího do systému letiště");
        System.out.println("Zadejte informace o cestujících");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Zadejte jméno: ");
        String name = scanner.nextLine();
        String emails = "";
        while (!emails.contains("@")) {
            System.out.println("Zadejte email: ");
            emails = scanner.nextLine();

            if (!emails.contains("@")) {
                System.out.println("Nebyl zadán email!");
            }
        }

        String passengerInfo = emails;
        Main.passengerData.add(passengerInfo);

        System.out.println("\n" + name + " zadáno do systému.\n");
        System.out.println("Co chcete vybrat dál? Vyberte číslo od 0-6: \n");
    }

    /**
     * Metoda pro rezervaci letu cestujícího.
     */
    public static void bookFlight() {
        System.out.println("Rezervace letu");
        System.out.println("Jste již uživatelem této aerolinky? (Y/N)");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine().toUpperCase();

        if (answer.equals("N")) {
            addNewPassenger();
            return;
        }

        System.out.println("Zadejte údaje cestujícího");
        System.out.println("Zadejte jméno: ");
        String name = scanner.nextLine();
        String emails = "";

        boolean emailExists = false;
        while (!emailExists) {
            System.out.println("Zadejte email: ");
            emails = scanner.nextLine();

            List<String> lines = util.Airport.readData("./src/data/users." + ui.Main.fileFormat);
            for (String line : lines) {
                String[] parts = line.split(",");
                String email = parts[0];
                if (email.equals(emails)) {
                    emailExists = true;
                    break;
                }
            }
            if (!emailExists) {
                System.out.println("Zadal jste špatný nebo neplatný email, (1) zkuste to znovu, nebo (2) založte účet (1/2)");
                String option = scanner.nextLine();
                
                if ("2".equals(option)) {
                    addNewPassenger();
                    return;
                }
            }

            System.out.println("\n Vyberte si z následujících letů: ");
            Main.airport.loadAndSortFlights();
            System.out.println("Zadejte destinaci : ");
            String flightDestination = scanner.nextLine();

            System.out.println("Zadejte třídu letu (BUSINESS nebo ECONOMY): \n");
            String flightClassInput = scanner.nextLine();
            FlightClassEnum flightClass;
            try {
                flightClass = FlightClassEnum.valueOf(flightClassInput.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Zadaná třída letu není validní. Defaultně nastavena na ECONOMY.");
                flightClass = FlightClassEnum.ECONOMY;
                flightClassInput = "ECONOMY"; // aktualizuje input uživatele na "ECONOMY"
            }

            // Vyberte si měsíc odletu
            System.out.println("Zadejte měsíc odletu (srpen, zari, rijen) (prosim nepouzivej diakritiku, konzole jí nerozumí): ");
            String inputMonth = scanner.nextLine();
            String monthNumber;
            switch (inputMonth.trim().toLowerCase()) {
                case "srpen":
                    monthNumber = "08";
                    break;
                case "zari":
                    monthNumber = "09";
                    break;
                case "rijen":
                    monthNumber = "10";
                    break;
                default:
                    System.out.println("Zadal jste špatný měsíc, nebo jste použili diakritiku ve vaší odpovědi.");
                    return;
            }

            String flightDate = "";
            try {
                Path path = Paths.get("./src/data/dates.txt");
                List<String> allDates = Files.readAllLines(path);
                List<String> possibleDates = allDates.stream().filter(d -> d.substring(3, 5).equals(monthNumber)).collect(Collectors.toList());

                // Print available dates
                System.out.println("Možné datumy v " + inputMonth + ":");
                possibleDates.forEach(System.out::println);

                System.out.println("Zadejte datum letu (DD.MM.YYYY): ");
                flightDate = scanner.nextLine();
                String datePattern = "\\b\\d{1,2}\\.\\d{1,2}\\.\\d{4}\\b"; //regulární výraz
                if (!flightDate.matches(datePattern)) {
                    System.out.println("Zadané datum není ve správném formátu (DD.MM.YYYY).");
                    return;
                }

            } catch (IOException e) {
                e.printStackTrace();
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

    }

    public static void cancelReservedFlights() {
        System.out.println("Zrušení rezervovaných letů");
        System.out.println("Zadejte svůj email: ");
        Scanner scanner = new Scanner(System.in);
        String inputEmail = scanner.nextLine();

        // Vyzve uživatele, aby zadali destinaci
        System.out.println("Zadejte destinaci letu, kterou chcete zrušit: ");
        String inputDestination = scanner.nextLine();

        // Vyzve uživatele, aby zadali třídu letu
        System.out.println("Zadejte třídu letu, kterou chcete zrušit: ");
        String inputFlightClass = scanner.nextLine();

        System.out.println("Zadejte datum odletu ve formátu DD.MM.YYYY: ");
        String inputDate = scanner.nextLine();

        String emailAndFlightData = inputEmail + "," + inputDestination + "," + inputFlightClass + "," + inputDate;

        List<String> lines = util.Airport.readData("./src/data/reservedflights." + ui.Main.fileFormat);
        int flightIndex = lines.indexOf(emailAndFlightData);
        if (flightIndex != -1) {
            lines.remove(flightIndex);
            util.Airport.writeData(lines, "./src/data/reservedflights." + ui.Main.fileFormat);
            System.out.println("Let byl úspěšně zrušen");
        } else {
            System.out.println("Tento let nebyl nalezen v našem systému");
        }
    }

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
}
