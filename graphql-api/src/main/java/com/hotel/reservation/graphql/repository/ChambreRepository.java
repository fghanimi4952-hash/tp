package com.hotel.reservation.graphql.repository;

import com.hotel.reservation.graphql.model.Chambre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository JPA pour l'entité Chambre
 */
@Repository
public interface ChambreRepository extends JpaRepository<Chambre, Long> {
}
