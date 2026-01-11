package com.hotel.reservation.soap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application principale pour le module SOAP API
 * 
 * Cette application Spring Boot expose un service SOAP pour la gestion
 * des réservations d'hôtel. Elle permet d'effectuer des opérations CRUD
 * (Create, Read, Update, Delete) sur les clients, chambres et réservations.
 * 
 * Port par défaut : 8082
 * WSDL : http://localhost:8082/ws/reservations.wsdl
 */
@SpringBootApplication
public class SoapApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoapApiApplication.class, args);
        System.out.println("========================================");
        System.out.println("SOAP API démarrée sur le port 8082");
        System.out.println("WSDL: http://localhost:8082/ws/reservations.wsdl");
        System.out.println("========================================");
    }
}
