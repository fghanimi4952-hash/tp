import React, { useState } from 'react';
import axios from 'axios';
import './index.css';

/**
 * Composant principal de l'application React
 * 
 * Ce client React permet de tester les opérations CRUD
 * sur les réservations d'hôtel via l'API REST.
 */
function App() {
  // État pour stocker les résultats des requêtes
  const [result, setResult] = useState(null);
  const [error, setError] = useState(null);

  // État pour les formulaires
  const [clientForm, setClientForm] = useState({
    nom: '',
    prenom: '',
    email: '',
    telephone: ''
  });

  const [reservationForm, setReservationForm] = useState({
    clientId: '',
    chambreId: '',
    dateDebut: '',
    dateFin: '',
    preferences: ''
  });

  const [reservationId, setReservationId] = useState('');

  // URL de base de l'API REST
  const API_BASE_URL = 'http://localhost:8081/api';

  /**
   * Gère les erreurs et affiche les messages
   */
  const handleError = (err) => {
    if (err.response) {
      setError(`Erreur ${err.response.status}: ${JSON.stringify(err.response.data, null, 2)}`);
    } else if (err.request) {
      setError('Erreur de connexion au serveur. Assurez-vous que le serveur REST est démarré.');
    } else {
      setError(`Erreur: ${err.message}`);
    }
    setResult(null);
  };

  /**
   * Affiche le résultat d'une opération réussie
   */
  const handleSuccess = (data, message) => {
    setResult({ data, message });
    setError(null);
  };

  /**
   * Crée un nouveau client
   */
  const createClient = async () => {
    try {
      const response = await axios.post(`${API_BASE_URL}/clients`, clientForm);
      handleSuccess(response.data, 'Client créé avec succès');
    } catch (err) {
      handleError(err);
    }
  };

  /**
   * Récupère tous les clients
   */
  const getAllClients = async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/clients`);
      handleSuccess(response.data, 'Liste des clients récupérée');
    } catch (err) {
      handleError(err);
    }
  };

  /**
   * Crée une nouvelle réservation
   */
  const createReservation = async () => {
    try {
      // Récupère d'abord le client et la chambre pour construire l'objet réservation
      const clientResponse = await axios.get(`${API_BASE_URL}/clients/${reservationForm.clientId}`);
      const chambreResponse = await axios.get(`${API_BASE_URL}/chambres/${reservationForm.chambreId}`);
      
      const reservationData = {
        client: { id: parseInt(reservationForm.clientId) },
        chambre: { id: parseInt(reservationForm.chambreId) },
        dateDebut: reservationForm.dateDebut,
        dateFin: reservationForm.dateFin,
        preferences: reservationForm.preferences
      };

      const response = await axios.post(`${API_BASE_URL}/reservations`, reservationData);
      handleSuccess(response.data, 'Réservation créée avec succès');
    } catch (err) {
      handleError(err);
    }
  };

  /**
   * Récupère une réservation par son ID
   */
  const getReservation = async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/reservations/${reservationId}`);
      handleSuccess(response.data, 'Réservation récupérée');
    } catch (err) {
      handleError(err);
    }
  };

  /**
   * Récupère toutes les réservations
   */
  const getAllReservations = async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/reservations`);
      handleSuccess(response.data, 'Liste des réservations récupérée');
    } catch (err) {
      handleError(err);
    }
  };

  /**
   * Supprime une réservation
   */
  const deleteReservation = async () => {
    try {
      await axios.delete(`${API_BASE_URL}/reservations/${reservationId}`);
      handleSuccess(null, `Réservation ${reservationId} supprimée avec succès`);
      setReservationId('');
    } catch (err) {
      handleError(err);
    }
  };

  return (
    <div className="app">
      <h1>🏨 Client de Test - API de Réservation d'Hôtel</h1>
      <p style={{ textAlign: 'center', color: '#666' }}>
        Ce client React permet de tester les opérations CRUD sur les réservations
      </p>

      {/* Section Gestion des Clients */}
      <div className="api-section">
        <h2>Gestion des Clients</h2>
        
        <div className="form-group">
          <label>Nom:</label>
          <input
            type="text"
            value={clientForm.nom}
            onChange={(e) => setClientForm({ ...clientForm, nom: e.target.value })}
            placeholder="Nom du client"
          />
        </div>

        <div className="form-group">
          <label>Prénom:</label>
          <input
            type="text"
            value={clientForm.prenom}
            onChange={(e) => setClientForm({ ...clientForm, prenom: e.target.value })}
            placeholder="Prénom du client"
          />
        </div>

        <div className="form-group">
          <label>Email:</label>
          <input
            type="email"
            value={clientForm.email}
            onChange={(e) => setClientForm({ ...clientForm, email: e.target.value })}
            placeholder="email@example.com"
          />
        </div>

        <div className="form-group">
          <label>Téléphone:</label>
          <input
            type="text"
            value={clientForm.telephone}
            onChange={(e) => setClientForm({ ...clientForm, telephone: e.target.value })}
            placeholder="+33 6 12 34 56 78"
          />
        </div>

        <button onClick={createClient}>Créer un Client</button>
        <button onClick={getAllClients}>Récupérer Tous les Clients</button>
      </div>

      {/* Section Gestion des Réservations */}
      <div className="api-section">
        <h2>Gestion des Réservations</h2>

        <div className="form-group">
          <label>ID Client:</label>
          <input
            type="number"
            value={reservationForm.clientId}
            onChange={(e) => setReservationForm({ ...reservationForm, clientId: e.target.value })}
            placeholder="1"
          />
        </div>

        <div className="form-group">
          <label>ID Chambre:</label>
          <input
            type="number"
            value={reservationForm.chambreId}
            onChange={(e) => setReservationForm({ ...reservationForm, chambreId: e.target.value })}
            placeholder="1"
          />
        </div>

        <div className="form-group">
          <label>Date de Début:</label>
          <input
            type="date"
            value={reservationForm.dateDebut}
            onChange={(e) => setReservationForm({ ...reservationForm, dateDebut: e.target.value })}
          />
        </div>

        <div className="form-group">
          <label>Date de Fin:</label>
          <input
            type="date"
            value={reservationForm.dateFin}
            onChange={(e) => setReservationForm({ ...reservationForm, dateFin: e.target.value })}
          />
        </div>

        <div className="form-group">
          <label>Préférences:</label>
          <input
            type="text"
            value={reservationForm.preferences}
            onChange={(e) => setReservationForm({ ...reservationForm, preferences: e.target.value })}
            placeholder="Vue sur mer, lit king-size, etc."
          />
        </div>

        <button onClick={createReservation}>Créer une Réservation</button>
        <button onClick={getAllReservations}>Récupérer Toutes les Réservations</button>

        <div style={{ marginTop: '20px' }}>
          <div className="form-group">
            <label>ID Réservation (pour consulter/supprimer):</label>
            <input
              type="number"
              value={reservationId}
              onChange={(e) => setReservationId(e.target.value)}
              placeholder="1"
            />
          </div>
          <button onClick={getReservation}>Consulter Réservation</button>
          <button className="delete" onClick={deleteReservation}>Supprimer Réservation</button>
        </div>
      </div>

      {/* Section Affichage des Résultats */}
      {error && (
        <div className="result error">
          <h3>Erreur</h3>
          <pre>{error}</pre>
        </div>
      )}

      {result && (
        <div className="result success">
          <h3>{result.message}</h3>
          {result.data && (
            <pre>{JSON.stringify(result.data, null, 2)}</pre>
          )}
        </div>
      )}
    </div>
  );
}

export default App;
