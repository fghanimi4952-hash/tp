package com.hotel.reservation.graphql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application principale pour le module GraphQL API
 * 
 * Cette application Spring Boot expose une API GraphQL pour la gestion
 * des réservations d'hôtel. Elle permet d'effectuer des opérations CRUD
 * (Create, Read, Update, Delete) sur les clients, chambres et réservations.
 * 
 * Port par défaut : 8083
 * GraphQL Endpoint: http://localhost:8083/graphql
 * GraphiQL UI: http://localhost:8083/graphiql
 */
@SpringBootApplication
public class GraphqlApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphqlApiApplication.class, args);
        System.out.println("========================================");
        System.out.println("GraphQL API démarrée sur le port 8083");
        System.out.println("GraphQL Endpoint: http://localhost:8083/graphql");
        System.out.println("GraphiQL UI: http://localhost:8083/graphiql");
        System.out.println("========================================");
    }
}
