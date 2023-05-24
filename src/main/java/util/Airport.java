/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import app.Flight;
import app.Passenger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fiwie
 */
public class Airport {

   private ArrayList<Flight> flights;
   private ArrayList<Passenger> passengers;

   /**
    * Konstruktor třídy Airport.
    * Inicializuje seznam letů a seznam cestujících na letišti.
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
    * @param flight    Let
    */
   public void bookPassengerOnFlight(Passenger passenger, Flight flight) {
      flight.addPassenger(passenger);
   }

   /**
    * Zruší rezervaci cestujícího na letu.
    *
    * @param passenger Cestující
    * @param flight    Let
    */
   public void cancelPassengerBooking(Passenger passenger, Flight flight) {
      flight.removePassenger(passenger);
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
    * Vyhledá lety podle destinace.
    *
    * @param destination Destinace letu
    */
   public void searchFlights(String destination) {
      System.out.println("Let do " + destination + ": ");
      for (Flight flight : flights) {
         if (flight.getDestination().equals(destination)) {
            System.out.println(flight.getDestination());
         } else {
            System.out.println("Omlouváme se, momentálně nejsou žádné dostupné lety do " + destination);
            break;
         }
      }
   }

   /**
    * Vyhledá lety podle destinace a vrátí seznam letů.
    *
    * @param destination Destinace letu
    * @return Seznam letů
    */
   public List<Flight> searchFlightsByDestination(String destination) {
      List<Flight> flights = new ArrayList<>();
      for (Flight flight : this.flights) {
         if (flight.getDestination().equalsIgnoreCase(destination)) {
            flights.add(flight);
         }
      }
      return flights;
   }

   /**
    * Zobrazí všechny dostupné lety na letišti.
    */
   public void displayAllAvailableFlights() {
      System.out.println("Dostupné lety: ");
      for (int i = 0; i < flights.size(); i++) {
         Flight flight = flights.get(i);
         System.out.println(i + 1 + ":" + flight.getDestination());
      }
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
}
