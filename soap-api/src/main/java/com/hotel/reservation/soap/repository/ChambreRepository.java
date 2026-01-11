package com.hotel.reservation.soap.repository;

import com.hotel.reservation.soap.model.Chambre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository JPA pour l'entité Chambre
 */
@Repository
public interface ChambreRepository extends JpaRepository<Chambre, Long> {
}
