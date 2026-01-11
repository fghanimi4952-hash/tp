package com.hotel.reservation.soap.repository;

import com.hotel.reservation.soap.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository JPA pour l'entité Client
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
