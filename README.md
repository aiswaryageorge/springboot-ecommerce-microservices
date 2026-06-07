# Ecommerce Microservices Platform

## Overview

A Spring Boot microservices-based ecommerce backend built using Java, Spring Cloud, Docker, and Kubernetes.

The application demonstrates service discovery, centralized configuration, API Gateway routing, containerization, and Kubernetes deployment.

---

## Architecture

Client

↓

API Gateway

↓

User Service | Product Service | Order Service

↓

MySQL

---

## Services

### API Gateway

* Central entry point
* Request routing
* Service discovery integration

### Config Server

* Centralized configuration management

### Eureka Server

* Service registration and discovery

### User Service

* Create users
* Retrieve users

### Product Service

* Create products
* Retrieve products

### Order Service

* Create orders
* Retrieve orders

---

## Technology Stack

* Java 21
* Spring Boot 3
* Spring Cloud
* Eureka Service Discovery
* Spring Cloud Config
* Spring Cloud Gateway
* MySQL
* Docker
* Kubernetes
* Maven

---

## Kubernetes Components

* Deployments
* Services
* Service Discovery
* Internal Cluster Networking

---

## Running Services

- Config Server
- Eureka Server
- API Gateway
- User Service
- Product Service
- Order Service
- Payment Service
- MySQL
- Docker
- Kubernetes

---

## Verification

### Products

POST /products

GET /products

### Users

POST /users

GET /users

### Orders

POST /orders

GET /orders

---

## Future Enhancements

* Kafka Integration in progress
* Redis Caching
* Prometheus Monitoring
* Grafana Dashboards
* ELK Stack
* AWS EKS Deployment

---

## Author

Aiswarya George
