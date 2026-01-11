-- ============================================
-- Schema de Base de Données pour le Système de Gestion d'Hôtel
-- ============================================
-- Base de données : MySQL ou PostgreSQL
-- Description : Structure des tables pour gérer les clients, chambres et réservations
-- ============================================

-- Création de la base de données (si elle n'existe pas)
CREATE DATABASE IF NOT EXISTS hotel_reservation_db;
USE hotel_reservation_db;

-- Table : Client
-- Stocke les informations des clients
CREATE TABLE IF NOT EXISTS client (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telephone VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_nom_prenom (nom, prenom)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Table : Chambre
-- Stocke les informations des chambres disponibles
CREATE TABLE IF NOT EXISTS chambre (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    type VARCHAR(50) NOT NULL COMMENT 'Type de chambre : simple, double, suite, etc.',
    prix DECIMAL(10, 2) NOT NULL COMMENT 'Prix par nuit en devise locale',
    disponible BOOLEAN DEFAULT TRUE COMMENT 'Statut de disponibilité',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_type (type),
    INDEX idx_disponible (disponible)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Table : Reservation
-- Stocke les réservations avec références aux clients et chambres
CREATE TABLE IF NOT EXISTS reservation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    client_id BIGINT NOT NULL,
    chambre_id BIGINT NOT NULL,
    date_debut DATE NOT NULL COMMENT 'Date de début de séjour',
    date_fin DATE NOT NULL COMMENT 'Date de fin de séjour',
    preferences TEXT COMMENT 'Préférences spécifiques pour la réservation (JSON)',
    statut VARCHAR(50) DEFAULT 'CONFIRMEE' COMMENT 'Statut : CONFIRMEE, ANNULEE, EN_ATTENTE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES client(id) ON DELETE CASCADE,
    FOREIGN KEY (chambre_id) REFERENCES chambre(id) ON DELETE RESTRICT,
    INDEX idx_client (client_id),
    INDEX idx_chambre (chambre_id),
    INDEX idx_dates (date_debut, date_fin),
    INDEX idx_statut (statut),
    CHECK (date_fin > date_debut)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Données de test initiales
-- Insertion de quelques chambres de test
INSERT INTO chambre (type, prix, disponible) VALUES
('Simple', 50.00, TRUE),
('Double', 80.00, TRUE),
('Suite', 150.00, TRUE),
('Simple', 55.00, TRUE),
('Double', 85.00, TRUE),
('Suite', 160.00, TRUE)
ON DUPLICATE KEY UPDATE id=id;
