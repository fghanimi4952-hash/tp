package com.hotel.reservation.graphql.repository;

import com.hotel.reservation.graphql.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository JPA pour l'entité Reservation
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
