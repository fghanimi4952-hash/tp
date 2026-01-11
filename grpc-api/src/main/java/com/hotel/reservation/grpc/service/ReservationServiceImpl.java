package com.hotel.reservation.grpc.service;

import com.hotel.reservation.grpc.model.Client;
import com.hotel.reservation.grpc.model.Chambre;
import com.hotel.reservation.grpc.model.Reservation;
import com.hotel.reservation.grpc.repository.ClientRepository;
import com.hotel.reservation.grpc.repository.ChambreRepository;
import com.hotel.reservation.grpc.repository.ReservationRepository;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Implémentation du service gRPC pour les réservations
 * 
 * Cette classe implémente les méthodes définies dans le fichier .proto
 * pour les opérations CRUD sur les réservations.
 */
@Service
public class ReservationServiceImpl extends ReservationServiceGrpc.ReservationServiceImplBase {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ChambreRepository chambreRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    /**
     * Crée une nouvelle réservation
     */
    @Override
    public void createReservation(com.hotel.reservation.grpc.CreateReservationRequest request,
                                  StreamObserver<com.hotel.reservation.grpc.ReservationResponse> responseObserver) {
        try {
            Client client = clientRepository.findById(request.getClientId())
                    .orElseThrow(() -> new RuntimeException("Client non trouvé"));
            
            Chambre chambre = chambreRepository.findById(request.getChambreId())
                    .orElseThrow(() -> new RuntimeException("Chambre non trouvée"));
            
            Reservation reservation = new Reservation();
            reservation.setClient(client);
            reservation.setChambre(chambre);
            reservation.setDateDebut(LocalDate.parse(request.getDateDebut()));
            reservation.setDateFin(LocalDate.parse(request.getDateFin()));
            reservation.setPreferences(request.getPreferences());
            
            Reservation saved = reservationRepository.save(reservation);
            
            com.hotel.reservation.grpc.ReservationResponse response = 
                com.hotel.reservation.grpc.ReservationResponse.newBuilder()
                    .setReservation(convertToProto(saved))
                    .build();
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    /**
     * Récupère une réservation par son ID
     */
    @Override
    public void getReservation(com.hotel.reservation.grpc.GetReservationRequest request,
                               StreamObserver<com.hotel.reservation.grpc.ReservationResponse> responseObserver) {
        try {
            Reservation reservation = reservationRepository.findById(request.getId())
                    .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));
            
            com.hotel.reservation.grpc.ReservationResponse response = 
                com.hotel.reservation.grpc.ReservationResponse.newBuilder()
                    .setReservation(convertToProto(reservation))
                    .build();
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    /**
     * Récupère toutes les réservations
     */
    @Override
    public void getAllReservations(com.hotel.reservation.grpc.GetAllReservationsRequest request,
                                   StreamObserver<com.hotel.reservation.grpc.ReservationListResponse> responseObserver) {
        try {
            List<Reservation> reservations = reservationRepository.findAll();
            
            com.hotel.reservation.grpc.ReservationListResponse.Builder builder = 
                com.hotel.reservation.grpc.ReservationListResponse.newBuilder();
            
            for (Reservation reservation : reservations) {
                builder.addReservations(convertToProto(reservation));
            }
            
            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    /**
     * Met à jour une réservation
     */
    @Override
    public void updateReservation(com.hotel.reservation.grpc.UpdateReservationRequest request,
                                  StreamObserver<com.hotel.reservation.grpc.ReservationResponse> responseObserver) {
        try {
            Reservation reservation = reservationRepository.findById(request.getId())
                    .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));
            
            if (request.getClientId() > 0) {
                Client client = clientRepository.findById(request.getClientId())
                        .orElseThrow(() -> new RuntimeException("Client non trouvé"));
                reservation.setClient(client);
            }
            
            if (request.getChambreId() > 0) {
                Chambre chambre = chambreRepository.findById(request.getChambreId())
                        .orElseThrow(() -> new RuntimeException("Chambre non trouvée"));
                reservation.setChambre(chambre);
            }
            
            if (!request.getDateDebut().isEmpty()) {
                reservation.setDateDebut(LocalDate.parse(request.getDateDebut()));
            }
            
            if (!request.getDateFin().isEmpty()) {
                reservation.setDateFin(LocalDate.parse(request.getDateFin()));
            }
            
            if (!request.getPreferences().isEmpty()) {
                reservation.setPreferences(request.getPreferences());
            }
            
            Reservation updated = reservationRepository.save(reservation);
            
            com.hotel.reservation.grpc.ReservationResponse response = 
                com.hotel.reservation.grpc.ReservationResponse.newBuilder()
                    .setReservation(convertToProto(updated))
                    .build();
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    /**
     * Supprime une réservation
     */
    @Override
    public void deleteReservation(com.hotel.reservation.grpc.DeleteReservationRequest request,
                                  StreamObserver<com.hotel.reservation.grpc.DeleteReservationResponse> responseObserver) {
        try {
            reservationRepository.deleteById(request.getId());
            
            com.hotel.reservation.grpc.DeleteReservationResponse response = 
                com.hotel.reservation.grpc.DeleteReservationResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Réservation supprimée avec succès")
                    .build();
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    /**
     * Convertit une entité JPA en message proto
     */
    private com.hotel.reservation.grpc.Reservation convertToProto(Reservation reservation) {
        return com.hotel.reservation.grpc.Reservation.newBuilder()
                .setId(reservation.getId())
                .setClientId(reservation.getClient().getId())
                .setChambreId(reservation.getChambre().getId())
                .setDateDebut(reservation.getDateDebut().toString())
                .setDateFin(reservation.getDateFin().toString())
                .setPreferences(reservation.getPreferences() != null ? reservation.getPreferences() : "")
                .setStatut(reservation.getStatut() != null ? reservation.getStatut() : "CONFIRMEE")
                .build();
    }
}
