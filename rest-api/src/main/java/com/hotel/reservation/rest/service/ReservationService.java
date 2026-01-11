package com.hotel.reservation.rest.service;

import com.hotel.reservation.rest.model.Client;
import com.hotel.reservation.rest.model.Chambre;
import com.hotel.reservation.rest.model.Reservation;
import com.hotel.reservation.rest.repository.ClientRepository;
import com.hotel.reservation.rest.repository.ChambreRepository;
import com.hotel.reservation.rest.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service métier pour la gestion des réservations
 * 
 * Cette classe contient la logique métier pour les opérations CRUD
 * sur les clients, chambres et réservations.
 */
@Service
@Transactional
public class ReservationService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ChambreRepository chambreRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    // ========== Opérations sur les Clients ==========

    /**
     * Crée un nouveau client
     * @param client Le client à créer
     * @return Le client créé avec son ID généré
     */
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    /**
     * Récupère un client par son ID
     * @param id L'ID du client
     * @return Un Optional contenant le client ou vide
     */
    @Transactional(readOnly = true)
    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    /**
     * Récupère tous les clients
     * @return Liste de tous les clients
     */
    @Transactional(readOnly = true)
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    /**
     * Met à jour un client existant
     * @param id L'ID du client à mettre à jour
     * @param client Les nouvelles données du client
     * @return Le client mis à jour
     */
    public Client updateClient(Long id, Client client) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client non trouvé avec l'ID: " + id));
        
        existingClient.setNom(client.getNom());
        existingClient.setPrenom(client.getPrenom());
        existingClient.setEmail(client.getEmail());
        existingClient.setTelephone(client.getTelephone());
        
        return clientRepository.save(existingClient);
    }

    /**
     * Supprime un client
     * @param id L'ID du client à supprimer
     */
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    // ========== Opérations sur les Chambres ==========

    /**
     * Crée une nouvelle chambre
     * @param chambre La chambre à créer
     * @return La chambre créée avec son ID généré
     */
    public Chambre createChambre(Chambre chambre) {
        return chambreRepository.save(chambre);
    }

    /**
     * Récupère une chambre par son ID
     * @param id L'ID de la chambre
     * @return Un Optional contenant la chambre ou vide
     */
    @Transactional(readOnly = true)
    public Optional<Chambre> getChambreById(Long id) {
        return chambreRepository.findById(id);
    }

    /**
     * Récupère toutes les chambres
     * @return Liste de toutes les chambres
     */
    @Transactional(readOnly = true)
    public List<Chambre> getAllChambres() {
        return chambreRepository.findAll();
    }

    /**
     * Met à jour une chambre existante
     * @param id L'ID de la chambre à mettre à jour
     * @param chambre Les nouvelles données de la chambre
     * @return La chambre mise à jour
     */
    public Chambre updateChambre(Long id, Chambre chambre) {
        Chambre existingChambre = chambreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chambre non trouvée avec l'ID: " + id));
        
        existingChambre.setType(chambre.getType());
        existingChambre.setPrix(chambre.getPrix());
        existingChambre.setDisponible(chambre.getDisponible());
        
        return chambreRepository.save(existingChambre);
    }

    /**
     * Supprime une chambre
     * @param id L'ID de la chambre à supprimer
     */
    public void deleteChambre(Long id) {
        chambreRepository.deleteById(id);
    }

    // ========== Opérations sur les Réservations ==========

    /**
     * Crée une nouvelle réservation
     * @param reservation La réservation à créer
     * @return La réservation créée avec son ID généré
     */
    public Reservation createReservation(Reservation reservation) {
        // Vérification que le client existe
        Client client = clientRepository.findById(reservation.getClient().getId())
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        
        // Vérification que la chambre existe et est disponible
        Chambre chambre = chambreRepository.findById(reservation.getChambre().getId())
                .orElseThrow(() -> new RuntimeException("Chambre non trouvée"));
        
        if (!chambre.getDisponible()) {
            throw new RuntimeException("La chambre n'est pas disponible");
        }

        // Vérification des dates
        if (reservation.getDateFin().isBefore(reservation.getDateDebut()) || 
            reservation.getDateFin().isEqual(reservation.getDateDebut())) {
            throw new RuntimeException("La date de fin doit être postérieure à la date de début");
        }

        reservation.setClient(client);
        reservation.setChambre(chambre);
        
        return reservationRepository.save(reservation);
    }

    /**
     * Récupère une réservation par son ID
     * @param id L'ID de la réservation
     * @return Un Optional contenant la réservation ou vide
     */
    @Transactional(readOnly = true)
    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    /**
     * Récupère toutes les réservations
     * @return Liste de toutes les réservations
     */
    @Transactional(readOnly = true)
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    /**
     * Met à jour une réservation existante
     * @param id L'ID de la réservation à mettre à jour
     * @param reservation Les nouvelles données de la réservation
     * @return La réservation mise à jour
     */
    public Reservation updateReservation(Long id, Reservation reservation) {
        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée avec l'ID: " + id));
        
        // Mise à jour des champs
        if (reservation.getClient() != null) {
            Client client = clientRepository.findById(reservation.getClient().getId())
                    .orElseThrow(() -> new RuntimeException("Client non trouvé"));
            existingReservation.setClient(client);
        }
        
        if (reservation.getChambre() != null) {
            Chambre chambre = chambreRepository.findById(reservation.getChambre().getId())
                    .orElseThrow(() -> new RuntimeException("Chambre non trouvée"));
            existingReservation.setChambre(chambre);
        }
        
        if (reservation.getDateDebut() != null) {
            existingReservation.setDateDebut(reservation.getDateDebut());
        }
        
        if (reservation.getDateFin() != null) {
            existingReservation.setDateFin(reservation.getDateFin());
        }
        
        if (reservation.getPreferences() != null) {
            existingReservation.setPreferences(reservation.getPreferences());
        }
        
        if (reservation.getStatut() != null) {
            existingReservation.setStatut(reservation.getStatut());
        }
        
        return reservationRepository.save(existingReservation);
    }

    /**
     * Supprime une réservation
     * @param id L'ID de la réservation à supprimer
     */
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
