package com.hotel.reservation.rest.repository;

import com.hotel.reservation.rest.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository JPA pour l'entité Reservation
 * 
 * Cette interface fournit des méthodes CRUD automatiques grâce à Spring Data JPA.
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    /**
     * Recherche les réservations d'un client
     * @param clientId L'ID du client
     * @return Liste des réservations du client
     */
    List<Reservation> findByClientId(Long clientId);

    /**
     * Recherche les réservations d'une chambre
     * @param chambreId L'ID de la chambre
     * @return Liste des réservations de la chambre
     */
    List<Reservation> findByChambreId(Long chambreId);
}
