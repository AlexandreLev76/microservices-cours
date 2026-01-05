# Microservices - Système de Gestion de Factures et Événements

## 🚀 Fonctionnalités

Envoi de mails pour chaque action CRUD sur un évenement (ATTENTION, veuillez à bien changer le applicaiton.properties avec vos identifiants si vous voulez accéder à la boite mail)
Gestion CRUD des factures avec BDD

## 🏗️ Architecture

### Structure du projet

```
src/main/java/com/groupeCinq/groupeCinq/
├── controller/
│   ├── FactureController.java      # Endpoints REST pour les factures
│   ├── EventController.java        # Endpoints REST pour les événements
│   └── NotificationController.java # Endpoint pour notifications directes
├── service/
│   ├── FactureService.java         # Logique métier des factures
│   ├── EventService.java           # Logique métier des événements
│   └── NotificationService.java    # Service d'envoi d'emails
├── repository/
│   ├── FactureRepository.java      # Repository JPA pour factures
│   └── EventRepository.java        # Repository en mémoire pour événements
└── model/
    ├── Facture.java                # Modèle de facture
    ├── Event.java                  # Modèle d'événement
    └── Notification.java           # Modèle de notification
```

## ⚙️ Configuration

### Fichier `application.properties`

```properties
# Application
spring.application.name=groupeCinq

# Configuration Resend (envoi d'emails)
resend.api.key=votre_cle_api_resend
resend.from.email=onboarding@resend.dev

# Configuration Notifications
notification.email=<mail utilisé avec Resend>

# Configuration Base de données PostgreSQL
spring.datasource.url=jdbc:postgresql://host:port/database?sslmode=require
spring.datasource.username=votre_username
spring.datasource.password=votre_password
spring.datasource.driver-class-name=org.postgresql.Driver

# Configuration HikariCP (pool de connexions)
spring.datasource.hikari.connection-timeout=30000
spring.datasource.hikari.maximum-pool-size=5
spring.datasource.hikari.minimum-idle=1

# Configuration JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

## 📦 Installation

### Prérequis

- Java 25
- Maven 3.6+
- PostgreSQL (ou compte Neon)
- Compte Resend

### Étapes

1. **Cloner le projet**
```bash
git clone <url-du-repo>
cd microservices-cours
```

4. **Compiler et lancer**
```bash
mvn clean install
mvn spring-boot:run
```

L'application sera accessible sur `http://localhost:8080`

## 📖 Utilisation

### API Factures

#### 1. Créer une facture
```http
POST http://localhost:8080/factures
Content-Type: application/json

{
  "titre": "Facture #001",
  "destinataire": "client@example.com",
  "emetteur": "entreprise@example.com",
  "corps": "Facture pour services rendus",
  "montantHT": 1000.0,
  "tva": 20.0
}
```

**Réponse :** `200 OK` avec la facture créée (montantTTC calculé automatiquement)

#### 2. Récupérer toutes les factures
```http
GET http://localhost:8080/factures
```

#### 3. Récupérer une facture par ID
```http
GET http://localhost:8080/factures/1
```

#### 4. Modifier une facture
```http
PUT http://localhost:8080/factures/1
Content-Type: application/json

{
  "titre": "Facture #001 - Modifiée",
  "destinataire": "client@example.com",
  "emetteur": "entreprise@example.com",
  "corps": "Facture modifiée",
  "montantHT": 1500.0,
  "tva": 20.0
}
```

#### 5. Supprimer une facture
```http
DELETE http://localhost:8080/factures/1
```

### API Événements

#### 1. Créer un événement
```http
POST http://localhost:8080/events
Content-Type: application/json

{
  "name": "Concert de rock",
  "description": "Grand concert au stade"
}
```

#### 2. Récupérer tous les événements
```http
GET http://localhost:8080/events
```

#### 3. Récupérer un événement par ID
```http
GET http://localhost:8080/events/1
```

#### 4. Modifier un événement
```http
PUT http://localhost:8080/events/1
Content-Type: application/json

{
  "name": "Concert de rock modifié",
  "description": "Nouvelle description"
}
```

#### 5. Supprimer un événement
```http
DELETE http://localhost:8080/events/1
```

### API Notifications

#### Envoyer une notification directe
```http
POST http://localhost:8080/notification/email?email=destinataire@example.com&subject=Test&body=Message de test
```


## 📝 Modèles de données

### Facture
```java
{
  "idFacture": Long,           // Généré automatiquement
  "titre": String,             // Obligatoire
  "destinataire": String,      // Obligatoire (email)
  "emetteur": String,          // Obligatoire (email)
  "corps": String,             // Obligatoire
  "montantHT": Double,         // Obligatoire (> 0)
  "tva": Double,               // Obligatoire (>= 0)
  "montantTTC": Double         // Calculé automatiquement
}
```