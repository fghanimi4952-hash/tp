package com.hotel.reservation.grpc.config;

import com.hotel.reservation.grpc.service.ReservationServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

/**
 * Configuration du serveur gRPC
 * 
 * Cette classe configure et démarre le serveur gRPC
 * pour exposer les services de réservation.
 */
@Configuration
public class GrpcServerConfig {

    @Value("${grpc.server.port:8084}")
    private int grpcPort;

    @Autowired
    private ReservationServiceImpl reservationService;

    private Server grpcServer;

    /**
     * Démarre le serveur gRPC
     */
    @PostConstruct
    public void startGrpcServer() throws IOException {
        grpcServer = ServerBuilder.forPort(grpcPort)
                .addService(reservationService)
                .build()
                .start();
        
        System.out.println("Serveur gRPC démarré sur le port " + grpcPort);
        
        // Ajoute un hook pour arrêter le serveur à l'arrêt de l'application
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Arrêt du serveur gRPC...");
            if (grpcServer != null) {
                grpcServer.shutdown();
            }
        }));
    }

    /**
     * Arrête le serveur gRPC
     */
    @PreDestroy
    public void stopGrpcServer() {
        if (grpcServer != null) {
            grpcServer.shutdown();
        }
    }
}
