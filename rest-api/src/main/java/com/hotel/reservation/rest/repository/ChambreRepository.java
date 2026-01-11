package com.hotel.reservation.rest.repository;

import com.hotel.reservation.rest.model.Chambre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository JPA pour l'entité Chambre
 * 
 * Cette interface fournit des méthodes CRUD automatiques grâce à Spring Data JPA.
 */
@Repository
public interface ChambreRepository extends JpaRepository<Chambre, Long> {

    /**
     * Recherche les chambres disponibles
     * @param disponible Le statut de disponibilité
     * @return Liste des chambres correspondantes
     */
    List<Chambre> findByDisponible(Boolean disponible);

    /**
     * Recherche les chambres par type
     * @param type Le type de chambre
     * @return Liste des chambres correspondantes
     */
    List<Chambre> findByType(String type);
}
