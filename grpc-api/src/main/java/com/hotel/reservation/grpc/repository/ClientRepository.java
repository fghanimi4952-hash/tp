package com.hotel.reservation.grpc.repository;

import com.hotel.reservation.grpc.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository JPA pour l'entité Client
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
