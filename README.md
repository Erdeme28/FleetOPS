# FleetOPS – Cloud-Native Fleet Management System

FleetOPS is a cloud-native application built using a microservices architecture, designed for managing a fleet of vehicles.

The application allows users to create transport orders, calculate routes through a separate microservice, persist data in a PostgreSQL database, and interact through a user-friendly frontend.

The entire system is fully containerized and can be started using a single Docker Compose command.

---

## Project Objective

The goal of this project is to build a cloud-native application that fulfills the following requirements:

- Spring Boot Gateway
- Separate microservice (C++)
- PostgreSQL database
- Authentication
- Secret management (no secrets in source code)
- Observability (health, metrics, logs)
- Basic CI/CD
- Cloud storage
- Single-command startup using Docker Compose

---

## Architecture Overview

The application consists of multiple containerized components orchestrated using Docker Compose.

### Gateway Service (Spring Boot)
- Exposes the public REST API
- Handles authentication and authorization
- Orchestrates application logic
- Communicates with the C++ microservice via HTTP
- Persists data using Spring Data JPA
- Exposes health and metrics endpoints via Spring Actuator
- Provides WebSocket support for real-time updates

### Route Service (C++)
- Independent microservice
- Exposes an HTTP endpoint for route calculation
- Computes a simplified route between two locations
- Returns distance, duration, and route data

### PostgreSQL Database
- Relational database used for persistence
- Stores users, vehicles, orders, and routes
- Data is persisted using Docker volumes

### Frontend (Python – Streamlit)
- User-friendly web interface
- Supports user authentication
- Displays vehicles and orders
- Allows creation of new orders
- Communicates with the Gateway Service via REST API

### Cloud Storage (MinIO)
- S3-compatible object storage
- Runs locally in a container
- Included to satisfy cloud storage requirements

---

## Technologies Used

- Java 21
- Spring Boot 3
- Spring Data JPA
- Spring Security
- Spring WebSocket
- Spring Actuator
- PostgreSQL 15
- C++ (httplib)
- Python 3 (Streamlit)
- Docker
- Docker Compose
- GitHub Actions
- MinIO

---

## Secret Management

Sensitive configuration values are managed using environment variables:

- DB_NAME
- DB_USER
- DB_PASSWORD

Secrets are:
- Not stored in the source code
- Provided via `.env` files for local execution
- Managed via GitHub Secrets in the CI/CD pipeline

---

## CI/CD Pipeline

A basic CI/CD pipeline is implemented using GitHub Actions.

The pipeline is triggered on:
- Push to `main` or `master`
- Pull requests targeting `main` or `master`

Pipeline steps:
- Build the Spring Boot Gateway using Maven
- Build Docker image for the Gateway Service
- Build Docker image for the Route Service

---

## Observability

The Gateway Service exposes observability features using Spring Actuator:
- Health checks
- Application metrics
- Structured logs

Endpoints are available under `/actuator`.

---

## Running the Application

### Prerequisites
- Docker
- Docker Compose

### Start the application

From the project root directory:

```bash
docker compose up --build
```

## Started Components

This single command starts all components:

- Gateway Service
- Route Service
- PostgreSQL
- Frontend
- MinIO

---

## Access URLs

- Frontend: http://localhost:3000  
- Gateway API: http://localhost:8080  
- Swagger UI: http://localhost:8080/swagger-ui.html  
- MinIO Console: http://localhost:9001  

---

## Functional Flow

Using the frontend, a user can:

- Authenticate
- View available vehicles
- Create a transport order (start location to destination)
- View created orders
- Trigger route calculation via the C++ microservice

---

## Conclusion

FleetOPS is a complete cloud-native application that strictly follows the project requirements.

All components are containerized, securely configured, observable, and orchestrated using Docker Compose.  
The system demonstrates microservices architecture, inter-service communication, persistence, DevOps practices, and a functional user interface.

The entire application can be started and demonstrated using a single command.