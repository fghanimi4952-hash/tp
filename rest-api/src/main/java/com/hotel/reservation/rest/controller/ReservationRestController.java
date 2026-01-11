package com.hotel.reservation.rest.controller;

import com.hotel.reservation.rest.model.Client;
import com.hotel.reservation.rest.model.Chambre;
import com.hotel.reservation.rest.model.Reservation;
import com.hotel.reservation.rest.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Contrôleur REST pour la gestion des réservations d'hôtel
 * 
 * Ce contrôleur expose des endpoints REST pour les opérations CRUD
 * sur les clients, chambres et réservations.
 * 
 * Base URL: http://localhost:8081/api
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Permet les requêtes CORS depuis n'importe quelle origine
public class ReservationRestController {

    @Autowired
    private ReservationService reservationService;

    // ========== Endpoints pour les Clients ==========

    /**
     * POST /api/clients
     * Crée un nouveau client
     * 
     * @param client Le client à créer (dans le body de la requête)
     * @return Le client créé avec son ID généré
     */
    @PostMapping("/clients")
    public ResponseEntity<Client> createClient(@Valid @RequestBody Client client) {
        try {
            Client createdClient = reservationService.createClient(client);
            return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GET /api/clients/{id}
     * Récupère un client par son ID
     * 
     * @param id L'ID du client
     * @return Le client trouvé ou 404 si non trouvé
     */
    @GetMapping("/clients/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Optional<Client> client = reservationService.getClientById(id);
        return client.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/clients
     * Récupère tous les clients
     * 
     * @return Liste de tous les clients
     */
    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = reservationService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    /**
     * PUT /api/clients/{id}
     * Met à jour un client existant
     * 
     * @param id L'ID du client à mettre à jour
     * @param client Les nouvelles données du client
     * @return Le client mis à jour
     */
    @PutMapping("/clients/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @Valid @RequestBody Client client) {
        try {
            Client updatedClient = reservationService.updateClient(id, client);
            return ResponseEntity.ok(updatedClient);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /api/clients/{id}
     * Supprime un client
     * 
     * @param id L'ID du client à supprimer
     * @return 204 No Content si succès, 404 si non trouvé
     */
    @DeleteMapping("/clients/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        try {
            reservationService.deleteClient(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ========== Endpoints pour les Chambres ==========

    /**
     * POST /api/chambres
     * Crée une nouvelle chambre
     * 
     * @param chambre La chambre à créer
     * @return La chambre créée avec son ID généré
     */
    @PostMapping("/chambres")
    public ResponseEntity<Chambre> createChambre(@Valid @RequestBody Chambre chambre) {
        try {
            Chambre createdChambre = reservationService.createChambre(chambre);
            return new ResponseEntity<>(createdChambre, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GET /api/chambres/{id}
     * Récupère une chambre par son ID
     * 
     * @param id L'ID de la chambre
     * @return La chambre trouvée ou 404 si non trouvée
     */
    @GetMapping("/chambres/{id}")
    public ResponseEntity<Chambre> getChambreById(@PathVariable Long id) {
        Optional<Chambre> chambre = reservationService.getChambreById(id);
        return chambre.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/chambres
     * Récupère toutes les chambres
     * 
     * @return Liste de toutes les chambres
     */
    @GetMapping("/chambres")
    public ResponseEntity<List<Chambre>> getAllChambres() {
        List<Chambre> chambres = reservationService.getAllChambres();
        return ResponseEntity.ok(chambres);
    }

    /**
     * PUT /api/chambres/{id}
     * Met à jour une chambre existante
     * 
     * @param id L'ID de la chambre à mettre à jour
     * @param chambre Les nouvelles données de la chambre
     * @return La chambre mise à jour
     */
    @PutMapping("/chambres/{id}")
    public ResponseEntity<Chambre> updateChambre(@PathVariable Long id, @Valid @RequestBody Chambre chambre) {
        try {
            Chambre updatedChambre = reservationService.updateChambre(id, chambre);
            return ResponseEntity.ok(updatedChambre);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /api/chambres/{id}
     * Supprime une chambre
     * 
     * @param id L'ID de la chambre à supprimer
     * @return 204 No Content si succès, 404 si non trouvé
     */
    @DeleteMapping("/chambres/{id}")
    public ResponseEntity<Void> deleteChambre(@PathVariable Long id) {
        try {
            reservationService.deleteChambre(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ========== Endpoints pour les Réservations ==========

    /**
     * POST /api/reservations
     * Crée une nouvelle réservation
     * 
     * @param reservation La réservation à créer
     * @return La réservation créée avec son ID généré
     */
    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody Reservation reservation) {
        try {
            Reservation createdReservation = reservationService.createReservation(reservation);
            return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * GET /api/reservations/{id}
     * Récupère une réservation par son ID
     * 
     * @param id L'ID de la réservation
     * @return La réservation trouvée ou 404 si non trouvée
     */
    @GetMapping("/reservations/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Optional<Reservation> reservation = reservationService.getReservationById(id);
        return reservation.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/reservations
     * Récupère toutes les réservations
     * 
     * @return Liste de toutes les réservations
     */
    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    /**
     * PUT /api/reservations/{id}
     * Met à jour une réservation existante
     * 
     * @param id L'ID de la réservation à mettre à jour
     * @param reservation Les nouvelles données de la réservation
     * @return La réservation mise à jour
     */
    @PutMapping("/reservations/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @Valid @RequestBody Reservation reservation) {
        try {
            Reservation updatedReservation = reservationService.updateReservation(id, reservation);
            return ResponseEntity.ok(updatedReservation);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /api/reservations/{id}
     * Supprime une réservation
     * 
     * @param id L'ID de la réservation à supprimer
     * @return 204 No Content si succès, 404 si non trouvé
     */
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        try {
            reservationService.deleteReservation(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
