package com.hotel.reservation.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application principale pour le module REST API
 * 
 * Cette application Spring Boot expose une API REST pour la gestion
 * des réservations d'hôtel. Elle permet d'effectuer des opérations CRUD
 * (Create, Read, Update, Delete) sur les clients, chambres et réservations.
 * 
 * Port par défaut : 8081
 * Documentation API : http://localhost:8081/swagger-ui.html
 */
@SpringBootApplication
public class RestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApiApplication.class, args);
        System.out.println("========================================");
        System.out.println("REST API démarrée sur le port 8081");
        System.out.println("Swagger UI: http://localhost:8081/swagger-ui.html");
        System.out.println("API Docs: http://localhost:8081/api-docs");
        System.out.println("========================================");
    }
}
