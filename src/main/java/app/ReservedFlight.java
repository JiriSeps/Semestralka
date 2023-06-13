/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import java.time.LocalDate;
import util.FlightClassEnum;

/**
 *
 * @author fiwie
 */
public class ReservedFlight {

    private String email;
    private String destination;
    private FlightClassEnum flightClass;
    private LocalDate date;

    /**
     * Konstruktor třídy ReservedFlight.
     *
     * @param email email rezervujícího cestujícího
     * @param destination destinace letu
     * @param flightClass třída letu
     * @param date datum letu
     */
    public ReservedFlight(String email, String destination, FlightClassEnum flightClass, LocalDate date) {
        this.email = email;
        this.destination = destination;
        this.flightClass = flightClass;
        this.date = date;
    }

    /**
     * Získá email rezervujícího cestujícího.
     *
     * @return email rezervujícího cestujícího
     */
    public String getEmail() {
        return email;
    }

    /**
     * Získá destinaci letu.
     *
     * @return destinace letu
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Získá třídu letu.
     *
     * @return třída letu
     */
    public FlightClassEnum getFlightClass() {
        return flightClass;
    }

    /**
     * Získá datum letu.
     *
     * @return datum letu
     */
    public LocalDate getDate() {
        return date;
    }
}
