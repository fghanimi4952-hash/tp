package com.hotel.reservation.rest.repository;

import com.hotel.reservation.rest.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository JPA pour l'entité Client
 * 
 * Cette interface fournit des méthodes CRUD automatiques grâce à Spring Data JPA.
 * Des méthodes de recherche personnalisées peuvent être ajoutées ici.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    /**
     * Recherche un client par son email
     * @param email L'email du client
     * @return Un Optional contenant le client trouvé ou vide
     */
    Optional<Client> findByEmail(String email);

    /**
     * Vérifie si un client existe avec cet email
     * @param email L'email à vérifier
     * @return true si un client existe, false sinon
     */
    boolean existsByEmail(String email);
}
