#  Étude de Cas : Analyse Scalabilité/Performance des APIs Modernes

## Contexte

Ce projet compare les performances de quatre technologies d'API modernes (REST, SOAP, GraphQL, gRPC) pour un système de gestion d'hôtel. Chaque technologie est implémentée comme un module séparé avec la même base de données pour garantir une comparaison équitable.

## Structure du Projet

<img width="465" height="681" alt="Capture d’écran 2026-01-11 à 12 17 50" src="https://github.com/user-attachments/assets/e0427fea-83e4-477a-9998-0e18d0f659b5" />


##  Technologies Utilisées

### Backend
- **Java 17** avec **Spring Boot 3.1.5**
- **Spring Data JPA** pour l'accès aux données
- **MySQL 8.0** (ou PostgreSQL) pour la base de données
- **Spring Web Services** pour SOAP
- **GraphQL Java** pour GraphQL
- **gRPC Java** pour gRPC

### Frontend
- **React.js 18** avec **Axios** pour les requêtes HTTP

##  Prérequis

1. **Java 17** ou supérieur
2. **Maven 3.8+**
3. **MySQL 8.0** (ou PostgreSQL)
4. **Node.js 16+** et **npm** (pour le client React)
5. **MySQL Workbench** ou un client MySQL (optionnel)

## Installation et Configuration

### 1. Configuration de la Base de Données

1. Créez une base de données MySQL :
```sql
CREATE DATABASE hotel_reservation_db;
```

2. Exécutez le script SQL pour créer les tables :
```bash
mysql -u root -p hotel_reservation_db < database/schema.sql
```

Ou connectez-vous à MySQL et exécutez le contenu du fichier `database/schema.sql`.

### 2. Configuration des Modules Backend

Les fichiers `application.properties` de chaque module contiennent la configuration de la base de données. Modifiez si nécessaire :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hotel_reservation_db
spring.datasource.username=root
spring.datasource.password=votre_mot_de_passe
```

### 3. Compilation du Projet

Depuis la racine du projet :

```bash
mvn clean install
```

Cette commande compile tous les modules Maven.

##  Démarrage des Services

### REST API (Port 8081)

```bash
cd rest-api
mvn spring-boot:run
```

OU

```bash
java -jar rest-api/target/rest-api-1.0.0.jar
```

- **API Base URL**: http://localhost:8081/api
- **Swagger UI**: http://localhost:8081/swagger-ui.html
- **API Docs**: http://localhost:8081/api-docs

### SOAP API (Port 8082)

```bash
cd soap-api
mvn spring-boot:run
```

- **WSDL**: http://localhost:8082/ws/reservations.wsdl
- **SOAP Endpoint**: http://localhost:8082/ws

### GraphQL API (Port 8083)

```bash
cd graphql-api
mvn spring-boot:run
```

- **GraphQL Endpoint**: http://localhost:8083/graphql
- **GraphiQL UI**: http://localhost:8083/graphiql

### gRPC API (Port 8084)

```bash
cd grpc-api
mvn spring-boot:run
```

- **gRPC Endpoint**: localhost:8084
- **Proto File**: `grpc-api/src/main/proto/reservation.proto`

### Frontend React (Port 3000)

```bash
cd frontend
npm install
npm start
```

Le client React sera accessible à : http://localhost:3000


##  Endpoints et Opérations

### REST API

Tous les endpoints sont préfixés par `/api` :

- **Clients**:
  - `GET /api/clients` - Récupère tous les clients
  - `GET /api/clients/{id}` - Récupère un client par ID
  - `POST /api/clients` - Crée un nouveau client
  - `PUT /api/clients/{id}` - Met à jour un client
  - `DELETE /api/clients/{id}` - Supprime un client

- **Chambres**:
  - `GET /api/chambres` - Récupère toutes les chambres
  - `GET /api/chambres/{id}` - Récupère une chambre par ID
  - `POST /api/chambres` - Crée une nouvelle chambre
  - `PUT /api/chambres/{id}` - Met à jour une chambre
  - `DELETE /api/chambres/{id}` - Supprime une chambre

- **Réservations**:
  - `GET /api/reservations` - Récupère toutes les réservations
  - `GET /api/reservations/{id}` - Récupère une réservation par ID
  - `POST /api/reservations` - Crée une nouvelle réservation
  - `PUT /api/reservations/{id}` - Met à jour une réservation
  - `DELETE /api/reservations/{id}` - Supprime une réservation

### SOAP API

Les opérations SOAP sont définies dans le WSDL accessible à `http://localhost:8082/ws/reservations.wsdl`.

### GraphQL API

Utilisez GraphiQL à `http://localhost:8083/graphiql` pour explorer et tester les queries et mutations.

**Queries**:
```graphql
query {
  reservations {
    id
    dateDebut
    dateFin
    client {
      nom
      prenom
      email
    }
    chambre {
      type
      prix
    }
  }
}
```

**Mutations**:
```graphql
mutation {
  createReservation(
    clientId: 1
    chambreId: 1
    dateDebut: "2024-01-15"
    dateFin: "2024-01-20"
    preferences: "Vue sur mer"
  ) {
    id
    dateDebut
    dateFin
  }
}
```

### gRPC API

Consultez le fichier `reservation.proto` pour les définitions des services et messages. Utilisez un client gRPC (comme BloomRPC ou grpcurl) pour tester.

##  Tests de Performance

Pour tester les performances, vous pouvez utiliser les outils suivants :

- **Apache JMeter** - Tests de charge
- **k6** - Tests de stress
- **Postman** - Tests manuels et collections
- **curl** - Tests en ligne de commande

### Exemple avec curl (REST API)

```bash
# Créer un client
curl -X POST http://localhost:8081/api/clients \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Dupont",
    "prenom": "Jean",
    "email": "jean.dupont@example.com",
    "telephone": "+33 6 12 34 56 78"
  }'

# Récupérer toutes les réservations
curl http://localhost:8081/api/reservations
```

##  Métriques à Mesurer

1. **Latence (Temps de réponse)**:
   - Temps moyen, médian, p95, p99
   - Pour différentes tailles de messages (1 KB, 10 KB, 100 KB)

2. **Débit (Throughput)**:
   - Nombre de requêtes par seconde (req/s)
   - Pour différentes charges (10, 100, 500, 1000 requêtes simultanées)

3. **Consommation des Ressources**:
   - Utilisation CPU (%)
   - Utilisation Mémoire (MB)
   - Pour différentes charges

4. **Simplicité d'Implémentation**:
   - Temps d'implémentation
   - Nombre de lignes de code
   - Complexité de la configuration

5. **Sécurité**:
   - Support TLS/SSL
   - Gestion de l'authentification
   - Résistance aux attaques

##  Dépannage

### Erreur de connexion à la base de données

Vérifiez que :
- MySQL est démarré
- La base de données `hotel_reservation_db` existe
- Les identifiants dans `application.properties` sont corrects

### Port déjà utilisé

Modifiez le port dans le fichier `application.properties` de chaque module :
```properties
server.port=8085  # Changez le port si nécessaire
```

### Erreurs de compilation Maven

Assurez-vous que Java 17 est installé et configuré :
```bash
java -version
mvn -version
```

##  Notes

- Chaque module est indépendant et peut être démarré séparément
- Tous les modules partagent la même base de données pour une comparaison équitable
- Le code est commenté en français pour faciliter la compréhension
- Les ports par défaut peuvent être modifiés dans les fichiers `application.properties`



