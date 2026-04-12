# ChâTop API - Backend Spring Boot

Ce projet constitue l'API backend pour l'application "ChâTop", un portail de mise en relation entre locataires et propriétaires. Il a été développé dans le cadre du projet 3 du parcours Développeur Full-Stack - Java et Angular d'OpenClassrooms.

## Table des matières

* [Contexte](#contexte)
* [Technologies utilisées](#technologies-utilis%C3%A9es)
* [Architecture](#architecture)
* [Installation & Configuration](#installation--configuration)
* [Lancement de l'application](#lancement-de-lapplication)
* [Outils de test](#outils-de-test)
* [Documentation API (Swagger)](#documentation-api-swagger)
* [Limites](#limites)
* [Améliorations possibles](#am%C3%A9liorations-possibles)
* [Auteur](#auteur)

---

## Contexte

Développement d'une API RESTful sécurisée permettant au front-end (Angular) de gérer des utilisateurs (authentification), des locations immobilières (création, modification, consultation avec upload d'images) et une messagerie interne. 

---

## Technologies utilisées

* **Java 17**
* **Spring Boot 3.2.5**
  * Spring Web (API REST)
  * Spring Security & JJWT 0.11.5 (Authentification par Token JWT)
  * Spring Data JPA (ORM)
  * Spring Validation (Validation des requêtes)
* **MySQL** (Base de données relationnelle)
* **MapStruct 1.5.5** (Mapping automatique entre Entités et DTOs)
* **Lombok** (Génération automatique de code : Getters, Setters, Builders)
* **Springdoc OpenAPI / Swagger UI 2.5.0** (Documentation interactive de l'API)
* **Maven** (Gestionnaire de dépendances)

---

## Architecture

Le projet suit une architecture en couches (MVC) classique de Spring Boot (Controller - Service - Repository) :

```text
src/main/java/com/chatop/api/
├── configuration/
│   ├── JwtAuthFilter.java          # Intercepteur pour la validation des tokens JWT
│   ├── OpenApiConfig.java          # Configuration de Swagger UI
│   ├── ResourceConfig.java         # Configuration pour servir les images statiques
│   └── SecurityConfig.java         # Configuration globale de Spring Security
├── controllers/
│   ├── AuthController.java         # Endpoints Login, Register et Me
│   ├── MessageController.java      # Endpoint pour l'envoi de messages
│   ├── RentalController.java       # Endpoints pour la gestion des locations
│   └── UserController.java         # Endpoints pour les infos utilisateurs
├── dto/                            # Objets de transfert (Requests & Responses)
│   ├── AuthResponse.java, LoginRequest.java, RegisterRequest.java
│   ├── MessageRequest.java, MessageResponse.java
│   ├── RentalCreateRequest.java, RentalUpdateRequest.java, RentalDto.java, RentalsResponse.java
│   ├── UserResponse.java
│   └── ErrorResponse.java          # Format standard des erreurs API
├── exceptions/
│   ├── GlobalExceptionHandler.java # Centralisation de la gestion des erreurs
│   ├── BadRequestException.java, ResourceNotFoundException.java, UnauthorizedException.java
├── mappers/                        # Mapping Entité <-> DTO (MapStruct)
│   ├── MessageMapper.java
│   ├── RentalMapper.java
│   └── UserMapper.java
├── models/                         # Entités JPA (Table MySQL)
│   ├── Message.java
│   ├── Rental.java
│   └── User.java
├── repositories/                   # Accès à la base de données
│   ├── MessageRepository.java
│   ├── RentalRepository.java
│   └── UserRepository.java
├── services/                       # Logique métier
│   ├── AuthService.java, JwtService.java, UserService.java
│   ├── RentalService.java          # Inclut la gestion de l'upload d'images
│   └── MessageService.java
└── ApiApplication.java             # Point d'entrée de l'application

src/main/resources/
├── static/images/                  # Stockage physique des images uploadées
├── templates/                      # (Optionnel)
└── application.properties          # Configuration de l'application et de la BDD
```
---
## Installation & Configuration

### Prérequis et Backend

Assurez-vous d'avoir installé les outils suivants :

- **Git** (pour cloner le repository)  
   https://git-scm.com/downloads  

- **Java 17 (JDK)**  
    https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html

- **IntelliJ IDEA** (ou autre IDE Java recommandé)  
   https://www.jetbrains.com/idea/download/  

- **MySQL** (serveur de base de données)  
   https://dev.mysql.com/downloads/mysql/  

- **MySQL Workbench** (interface graphique, optionnel mais recommandé)  
   https://dev.mysql.com/downloads/workbench/  

- **Maven** 
   https://maven.apache.org/download.cgi  

- **Postman (optionnel)**
   https://www.postman.com/downloads/

- **Mockoon (optionnel)**
   https://mockoon.com/download/

### Configuration de la base de données

Se connecter à son MySQL pour créer la base de données :

```bash
CREATE DATABASE chatop;
```
Via mySQL Workbench :

- Ouvrir MySQL Workbench
- Se connecter à votre serveur
- Sélectionner la base chatop
- Ouvrir le fichier script.sql
- Exécuter le script

Via ligne de commandes :

```bash
mysql -u root -p chatop < front-end/ressources/sql/script.sql
```

### Configuration des variables d'environnement

Pour des raisons de sécurité, les identifiants de base de données ne sont pas stockés en clair dans le projet.

Le fichier `application.properties` utilise des variables d’environnement :

```properties
spring.datasource.username=${DB_CHATOP_USER}
spring.datasource.password=${DB_CHATOP_PASSWORD}
```
Pour Windows soit par recherche dans les paramètres (Modifier les variables d'environnement système), soit dans un terminal powershell :

```PowerShell
setx DB_CHATOP_USER "votre_user_mysql"
setx DB_CHATOP_PASSWORD "votre_mot_de_passe_mysql"
```

Pour Mac / Linux :
```bash
export DB_CHATOP_USER=votre_user_mysql
export DB_CHATOP_PASSWORD=votre_mot_de_passe_mysql
```

### Frontend Angular

Si vous souhaitez lancer le frontend associé :

- **Angular CLI**
  https://angular.io/cli

- **Node.js (version 18+)**  
   https://nodejs.org/  

- npm (installé automatiquement avec Node.js)

### Vérifier l'installation

```bash
java -version
mvn -v
node -v
npm -v
ng version
```
Si les commandes ne fonctionnent pas car votre OS ne détecte pas le préfixe de la commande, ajouter le chemin de votre technologie dans la variable d'environnement de votre système (PATH pour Windows)



---

## Lancement de l'application

### Backend (Spring Boot)

####  Via IntelliJ (recommandé)

- Ouvrir le projet dans IntelliJ IDEA
- Lancer la classe principale (bouton Run) :

```text
ApiApplication.java
```

OU

```bash
mvn spring-boot:run
```
L'API sera accessible à l'adresse :
```text
http://localhost:8080
```
La documentation Swagger sera accessible à l'adresse :
```text
http://localhost:8080/swagger-ui.html
```
### Frontend (Angular)

- Ouvrir le projet
- Lancer ces commandes
```bash
cd front-end
npm install
npm run start
```
Le frontend sera accessible à l'adresse :
```text
http://localhost:4200
```
---


## Outils de test

Le projet fournit des outils pour tester facilement l’API sans frontend.

### 📦 Collection Postman

Une collection Postman est disponible :

```text
front-end/ressources/postman/rental.postman_collection.json
```
- Ouvrir Postman
- Cliquer sur Import
- Sélectionner le fichier .json
- La collection apparaît automatiquement
- Lancer le backend
- Tester les endpoints
- Récupérer le token JWT via register et login et l'ajouter dans les headers Authorization (Bearer token)


### 📦 Mockoon

- Cliquer sur Import
- Sélectionner le fichier .json
- L’environnement apparaît dans la liste
- Dans l'environnement importé, cliquer sur Start
- Lancer le Frontend et tester

---

## Documentation API (Swagger)

L'API est entièrement documentée grâce à **Swagger UI** (via Springdoc OpenAPI).

Une fois l'application backend lancée, la documentation interactive est accessible à l'adresse suivante :

http://localhost:8080/swagger-ui.html

### 🔐 Authentification

L’API utilise une authentification par **JWT (JSON Web Token)**.

Pour accéder aux endpoints sécurisés :

1. Utilisez l’endpoint `/api/auth/register` pour obtenir un token JWT (création d'un compte)
2. Cliquez sur le bouton **"Authorize"** dans Swagger
3. Entrez le token fournit pour pouvoir utiliser les autres endpoints sécurisés

---

## Limites

- Pas de gestion avancée des rôles utilisateurs (admin / user)
- Gestion des images simplifiée (stockage local uniquement)
- Gestion des erreurs encore basique (messages simples)
- Aucun test unitaire ou test d'intégration implémenté

---

## Améliorations possibles

- Ajouter des tests unitaires et d'intégration (JUnit, Mockito)
- Implémenter un système de rôles et permissions
- Externaliser le stockage des images (AWS, etc...)
- Mettre en place des technologies pour faciliter le déploiement (Docker)
- Ajouter une CI/CD (GitHub Actions)
- Sécuriser davantage l'API (limitations de requètes au serveur, etc...)

---

## Auteur

Clément Cirou  
Étudiant OpenClassrooms - Parcours Développeur Full-Stack Java & Angular

GitHub : https://github.com/clems2

