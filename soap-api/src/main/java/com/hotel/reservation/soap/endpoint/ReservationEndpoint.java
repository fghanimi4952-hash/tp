package com.hotel.reservation.soap.endpoint;

import com.hotel.reservation.soap.model.Client;
import com.hotel.reservation.soap.model.Chambre;
import com.hotel.reservation.soap.model.Reservation;
import com.hotel.reservation.soap.repository.ClientRepository;
import com.hotel.reservation.soap.repository.ChambreRepository;
import com.hotel.reservation.soap.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Endpoint SOAP pour les opérations de réservation
 * 
 * Ce endpoint expose les opérations CRUD via SOAP
 * Namespace : http://hotel.reservation.com/soap
 */
@Endpoint
public class ReservationEndpoint {

    private static final String NAMESPACE_URI = "http://hotel.reservation.com/soap";

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ChambreRepository chambreRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    /**
     * Récupère une réservation par son ID
     * SOAP Action: getReservation
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getReservationRequest")
    @ResponsePayload
    public GetReservationResponse getReservation(@RequestPayload GetReservationRequest request) {
        GetReservationResponse response = new GetReservationResponse();
        
        Optional<Reservation> reservation = reservationRepository.findById(request.getId());
        if (reservation.isPresent()) {
            Reservation res = reservation.get();
            ReservationType resType = new ReservationType();
            resType.setId(res.getId());
            resType.setClientId(res.getClient().getId());
            resType.setChambreId(res.getChambre().getId());
            resType.setDateDebut(res.getDateDebut().toString());
            resType.setDateFin(res.getDateFin().toString());
            resType.setPreferences(res.getPreferences());
            response.setReservation(resType);
        }
        
        return response;
    }

    /**
     * Crée une nouvelle réservation
     * SOAP Action: createReservation
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createReservationRequest")
    @ResponsePayload
    public CreateReservationResponse createReservation(@RequestPayload CreateReservationRequest request) {
        CreateReservationResponse response = new CreateReservationResponse();
        
        ReservationType resType = request.getReservation();
        Reservation reservation = new Reservation();
        
        Client client = clientRepository.findById(resType.getClientId())
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        
        Chambre chambre = chambreRepository.findById(resType.getChambreId())
                .orElseThrow(() -> new RuntimeException("Chambre non trouvée"));
        
        reservation.setClient(client);
        reservation.setChambre(chambre);
        reservation.setDateDebut(LocalDate.parse(resType.getDateDebut()));
        reservation.setDateFin(LocalDate.parse(resType.getDateFin()));
        reservation.setPreferences(resType.getPreferences());
        
        Reservation saved = reservationRepository.save(reservation);
        
        ReservationType savedType = new ReservationType();
        savedType.setId(saved.getId());
        savedType.setClientId(saved.getClient().getId());
        savedType.setChambreId(saved.getChambre().getId());
        savedType.setDateDebut(saved.getDateDebut().toString());
        savedType.setDateFin(saved.getDateFin().toString());
        savedType.setPreferences(saved.getPreferences());
        
        response.setReservation(savedType);
        return response;
    }

    /**
     * Supprime une réservation
     * SOAP Action: deleteReservation
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteReservationRequest")
    @ResponsePayload
    public DeleteReservationResponse deleteReservation(@RequestPayload DeleteReservationRequest request) {
        DeleteReservationResponse response = new DeleteReservationResponse();
        
        reservationRepository.deleteById(request.getId());
        response.setStatus("SUCCESS");
        
        return response;
    }

    // Classes internes simplifiées pour les requêtes/réponses SOAP
    // Dans une vraie implémentation, ces classes seraient générées depuis le XSD
    
    public static class GetReservationRequest {
        private Long id;
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
    }

    public static class GetReservationResponse {
        private ReservationType reservation;
        public ReservationType getReservation() { return reservation; }
        public void setReservation(ReservationType reservation) { this.reservation = reservation; }
    }

    public static class CreateReservationRequest {
        private ReservationType reservation;
        public ReservationType getReservation() { return reservation; }
        public void setReservation(ReservationType reservation) { this.reservation = reservation; }
    }

    public static class CreateReservationResponse {
        private ReservationType reservation;
        public ReservationType getReservation() { return reservation; }
        public void setReservation(ReservationType reservation) { this.reservation = reservation; }
    }

    public static class DeleteReservationRequest {
        private Long id;
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
    }

    public static class DeleteReservationResponse {
        private String status;
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    public static class ReservationType {
        private Long id;
        private Long clientId;
        private Long chambreId;
        private String dateDebut;
        private String dateFin;
        private String preferences;

        // Getters et Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public Long getClientId() { return clientId; }
        public void setClientId(Long clientId) { this.clientId = clientId; }
        public Long getChambreId() { return chambreId; }
        public void setChambreId(Long chambreId) { this.chambreId = chambreId; }
        public String getDateDebut() { return dateDebut; }
        public void setDateDebut(String dateDebut) { this.dateDebut = dateDebut; }
        public String getDateFin() { return dateFin; }
        public void setDateFin(String dateFin) { this.dateFin = dateFin; }
        public String getPreferences() { return preferences; }
        public void setPreferences(String preferences) { this.preferences = preferences; }
    }
}
