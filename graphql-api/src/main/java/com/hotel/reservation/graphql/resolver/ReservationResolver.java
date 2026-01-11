package com.hotel.reservation.graphql.resolver;

import com.hotel.reservation.graphql.model.Client;
import com.hotel.reservation.graphql.model.Chambre;
import com.hotel.reservation.graphql.model.Reservation;
import com.hotel.reservation.graphql.repository.ClientRepository;
import com.hotel.reservation.graphql.repository.ChambreRepository;
import com.hotel.reservation.graphql.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Resolver GraphQL pour les réservations
 * 
 * Ce resolver expose les queries et mutations GraphQL
 * pour les opérations CRUD sur les réservations.
 */
@Component
public class ReservationResolver {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ChambreRepository chambreRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    /**
     * Query : Récupère une réservation par son ID
     */
    public Reservation getReservation(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    /**
     * Query : Récupère toutes les réservations
     */
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    /**
     * Mutation : Crée une nouvelle réservation
     */
    public Reservation createReservation(Long clientId, Long chambreId, 
                                        String dateDebut, String dateFin, 
                                        String preferences) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        
        Chambre chambre = chambreRepository.findById(chambreId)
                .orElseThrow(() -> new RuntimeException("Chambre non trouvée"));
        
        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setChambre(chambre);
        reservation.setDateDebut(LocalDate.parse(dateDebut));
        reservation.setDateFin(LocalDate.parse(dateFin));
        reservation.setPreferences(preferences);
        
        return reservationRepository.save(reservation);
    }

    /**
     * Mutation : Met à jour une réservation
     */
    public Reservation updateReservation(Long id, Long clientId, Long chambreId,
                                        String dateDebut, String dateFin,
                                        String preferences) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));
        
        if (clientId != null) {
            Client client = clientRepository.findById(clientId)
                    .orElseThrow(() -> new RuntimeException("Client non trouvé"));
            reservation.setClient(client);
        }
        
        if (chambreId != null) {
            Chambre chambre = chambreRepository.findById(chambreId)
                    .orElseThrow(() -> new RuntimeException("Chambre non trouvée"));
            reservation.setChambre(chambre);
        }
        
        if (dateDebut != null) {
            reservation.setDateDebut(LocalDate.parse(dateDebut));
        }
        
        if (dateFin != null) {
            reservation.setDateFin(LocalDate.parse(dateFin));
        }
        
        if (preferences != null) {
            reservation.setPreferences(preferences);
        }
        
        return reservationRepository.save(reservation);
    }

    /**
     * Mutation : Supprime une réservation
     */
    public Boolean deleteReservation(Long id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
