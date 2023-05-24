/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;
import ui.Main;

/**
 *
 * @author fiwie
 */
public class Passenger {
    static String emails = "";
    private String name;
    private String email;
    private UUID id;
    private ArrayList<Flight> flights;


    /**
     * Konstruktor třídy Passenger.
     *
     * @param name  Jméno cestujícího
     * @param email Email cestujícího
     * @param uuid  UUID cestujícího
     */
    public Passenger(String name, String email, UUID uuid) {
        this.name = name;
        this.email = email;
        this.id = UUID.randomUUID();
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
     * Získá UUID cestujícího.
     *
     * @return UUID cestujícího
     */
    public UUID getId() {
        return id;
    }

    /**
     * Nastaví UUID cestujícího.
     *
     * @param id UUID cestujícího
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Přepsaná metoda toString pro třídu Passenger.
     *
     * @return Textová reprezentace objektu Passenger
     */
    @Override
    public String toString() {
        return "Passenger [name=" + name + ", email=" + email + ", id=" + id + "]";
    }

    /**
     * Enumerace pro třídy letu (BUSINESS, ECONOMY).
     */
    public enum FlightClass {
        BUSINESS,
        ECONOMY
    }

    /**
     * Metoda pro přidání nového cestujícího do systému letiště.
     */
    public static void addNewPassenger() {
        System.out.println("Přidejte nového cestujícího do systému letiště");
        System.out.println("Zadejte informace o cestujících");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Zadejte jméno: ");
        String name = scanner.nextLine();

        while (!emails.contains("@")) {
            System.out.println("Zadejte email: ");
            emails = scanner.nextLine();

            if (!emails.contains("@")) {
                System.out.println("Nebyl zadán email!");
            }
        }

        UUID uuid = UUID.randomUUID();
        Passenger addedPassenger = new Passenger(name, emails, UUID.randomUUID());

        System.out.println("\n" + name + " zadáno do systému.\n");
        System.out.println("Co chcete vybrat dál? Vyberte číslo od 0-6: \n");
    }

    /**
     * Metoda pro rezervaci letu cestujícího.
     */
    public static void bookFlight() {
        System.out.println("Rezervace letu");

        System.out.println("Zadejte údaje cestujícího");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Zadejte jméno: ");
        String name = scanner.nextLine();

        while (!emails.contains("@")) {
            System.out.println("Zadejte email: ");
            emails = scanner.nextLine();

            if (!emails.contains("@")) {
                System.out.println("Nebyl zadán email!");
            }
        }

        if (!emails.contains("@")) {
            throw new IllegalArgumentException("Nebyl zadán email!");
        }

        System.out.println("\n Vyberte si z následujících letů: ");
        Main.airport.displayAllAvailableFlights();
        System.out.println("Zadejte : ");
        String flightDestination = scanner.nextLine();

        UUID uuid = UUID.randomUUID();
        Passenger addedPassenger = new Passenger(name, emails, UUID.randomUUID());
        Flight chosenFlight = Main.airport.getFlight(flightDestination);

        System.out.println("Zadejte třídu letu (BUSINESS nebo ECONOMY): \n");
        String flightClassInput = scanner.nextLine();
        FlightClass flightClass;
        try {
            flightClass = FlightClass.valueOf(flightClassInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Zadaná třída letu není validní. Defaultně nastavena na ECONOMY.");
            flightClass = FlightClass.ECONOMY;
        }

        if (chosenFlight != null) {
            Main.airport.bookPassengerOnFlight(addedPassenger, chosenFlight);
            System.out.println(addedPassenger.getName() + " zadal let do " + flightDestination + " s třídou " + flightClassInput + "\n");
        } else {
            System.out.println("Nenalezeny lety do " + flightDestination + ".\n");
        }
        System.out.println("Co chcete vybrat dál? Vyberte číslo od 0-6: \n");
    }

}