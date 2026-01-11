package com.hotel.reservation.grpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application principale pour le module gRPC API
 * 
 * Cette application Spring Boot expose un service gRPC pour la gestion
 * des réservations d'hôtel. Elle permet d'effectuer des opérations CRUD
 * (Create, Read, Update, Delete) sur les clients, chambres et réservations.
 * 
 * Port par défaut : 8084
 * gRPC Endpoint: localhost:8084
 */
@SpringBootApplication
public class GrpcApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrpcApiApplication.class, args);
        System.out.println("========================================");
        System.out.println("gRPC API démarrée sur le port 8084");
        System.out.println("gRPC Endpoint: localhost:8084");
        System.out.println("========================================");
    }
}
