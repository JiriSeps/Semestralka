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

    public ReservedFlight(String email, String destination, FlightClassEnum flightClass, LocalDate date) {
        this.email = email;
        this.destination = destination;
        this.flightClass = flightClass;
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public String getDestination() {
        return destination;
    }

    public FlightClassEnum getFlightClass() {
        return flightClass;
    }

    public LocalDate getDate() {
        return date;
    }
}