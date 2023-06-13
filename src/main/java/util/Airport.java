/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import app.Flight;
import app.Passenger;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author fiwie
 */
public class Airport {

    private ArrayList<Flight> flights;
    private ArrayList<Passenger> passengers;

    /**
     * Konstruktor třídy Airport. Inicializuje seznam letů a seznam cestujících
     * na letišti.
     */
    public Airport() {
        this.flights = new ArrayList<>();
        this.passengers = new ArrayList<>();
    }

    /**
     * Získá seznam letů na letišti.
     *
     * @return Seznam letů
     */
    public ArrayList<Flight> getFlights() {
        return flights;
    }

    /**
     * Nastaví seznam letů na letišti.
     *
     * @param flights Seznam letů
     */
    public void setFlights(ArrayList<Flight> flights) {
        this.flights = flights;
    }

    /**
     * Získá seznam cestujících na letišti.
     *
     * @return Seznam cestujících
     */
    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    /**
     * Nastaví seznam cestujících na letišti.
     *
     * @param passengers Seznam cestujících
     */
    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
    }

    /**
     * Zarezervuje cestujícího na let.
     *
     * @param passenger Cestující
     * @param flight Let
     */
    public void bookPassengerOnFlight(Passenger passenger, Flight flight) {
        flight.addPassenger(passenger);
    }

    /**
     * Zruší celý let.
     *
     * @param flight Let
     */
    public void cancelFlight(Flight flight) {
        this.flights.remove(flight);
    }

    /**
     * Přidá let do seznamu letů na letišti.
     *
     * @param flight Let
     */
    public void addFlight(Flight flight) {
        this.flights.add(flight);
    }

    /**
     * Získá let podle destinace.
     *
     * @param flightDestination Destinace letu
     * @return Let
     */
    public Flight getFlight(String flightDestination) {
        for (Flight flight : flights) {
            if (flight.getDestination().equalsIgnoreCase(flightDestination)) {
                return flight;
            }
        }
        return null;
    }

    /**
     * Načte a seřadí lety.
     */
    public void loadAndSortFlights() {
        flights.clear();

        List<String> destinations = util.Airport.readData("./src/data/flights." + ui.Main.fileFormat);
        for (String destination : destinations) {
            if (destination == null || destination.trim().isEmpty()) {
                // Skip null or empty lines
                continue;
            }

            Flight flight = new Flight(destination.trim());  // Trim white spaces
            this.flights.add(flight);
        }
        Collections.sort(this.flights);
        for (Flight flight : this.flights) {
            System.out.println(flight.getDestination());
        }
    }

    /**
     * Uloží data do souboru.
     *
     * @param data Data ke zápisu
     * @param format Formát souboru (txt nebo bin)
     * @param filename Název souboru
     */
    public static void saveData(List<String> data, String format, String filename) {
        // Check if data is empty
        if (data.isEmpty()) {
            System.out.println("Žádná data k uložení pro " + filename);
            return;
        }

        String filepath = "./src/data/" + filename;
        switch (format.toLowerCase()) {
            case "txt":
                filepath += ".txt";
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath, true))) {
                    for (String item : data) {
                        writer.write(item);
                        writer.newLine();  // Add new line after each item
                    }
                    System.out.println("Data úspěšně uložena do souboru: " + filepath);
                } catch (IOException e) {
                    System.out.println("Nastala chyba při zápisu do souboru: " + e.getMessage());
                }
                break;
            case "bin":
                filepath += ".dat";
                try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filepath, true)))) {
                    for (String item : data) {
                        dos.writeUTF(item);
                    }
                    System.out.println("Data úspěšně uložena:" + filepath);
                } catch (IOException e) {
                    System.out.println("Nastala chyba při zápisu do souboru: " + e.getMessage());
                }
                break;
            default:
                System.out.println("Nepodporovaný formát souboru: " + format);
        }
    }

    /**
     * Načte data ze souboru.
     *
     * @param fileName Název souboru
     * @return Seznam načtených dat
     */
    public static List<String> readData(String fileName) {
        List<String> data = new ArrayList<>();
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("Soubor " + fileName + " nebyl nalezen. Program bude ukončen.");
            System.exit(0);
        }
        try {
            if (ui.Main.fileFormat.equals("txt")) {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    data.add(line);
                }
                scanner.close();
            } else if (ui.Main.fileFormat.equals("dat")) {
                DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
                try {
                    while (true) {
                        String line = dis.readUTF();
                        data.add(line);
                    }
                } catch (EOFException e) {
                    // dosáhli jsme konce souboru, nic nevypisujeme a jen pokračujeme
                }
                dis.close();
            }
        } catch (IOException e) {
            System.out.println("Chyba při čtení souboru: " + fileName);
            //e.printStackTrace();
        }
        return data;
    }

    /**
     * Zapíše data do souboru.
     *
     * @param lines Řádky k zápisu
     * @param fileName Název souboru
     */
    public static void writeData(List<String> lines, String fileName) {
        try {
            if (ui.Main.fileFormat.equals("txt")) {
                PrintWriter writer = new PrintWriter(new File(fileName));
                for (String line : lines) {
                    writer.println(line);
                }
                writer.close();
            } else if (ui.Main.fileFormat.equals("dat")) {
                DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
                for (String line : lines) {
                    dos.writeUTF(line);
                }
                dos.close();
            }
        } catch (IOException e) {
            System.out.println("Chyba při zápisu do souboru: " + fileName);
        }
    }
}
