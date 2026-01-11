package com.hotel.reservation.grpc.repository;

import com.hotel.reservation.grpc.model.Chambre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository JPA pour l'entité Chambre
 */
@Repository
public interface ChambreRepository extends JpaRepository<Chambre, Long> {
}
