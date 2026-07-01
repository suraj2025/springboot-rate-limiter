# Spring Boot Rate Limiter

A complete production-ready Rate Limiting project built using **Spring Boot**, **Redis**, **Bucket4j**, and **Docker**.

This project starts with a basic in-memory implementation and gradually evolves into a scalable distributed rate limiter similar to what is used in real-world systems.

---

## Project Goals

- Learn rate limiting from scratch
- Understand different rate limiting algorithms
- Implement multiple approaches
- Build a production-ready distributed solution
- Gain hands-on experience with Redis
- Prepare for backend and system design interviews

---

## Tech Stack

- Java
- Spring Boot
- Maven
- Redis
- Docker
- Bucket4j
- Postman

---

## Learning Roadmap

### Phase 1
- Basic Spring Boot setup
- In-Memory Rate Limiter
- ConcurrentHashMap
- HTTP Filter
- HTTP 429 (Too Many Requests)

### Phase 2
- Fixed Window Algorithm
- Sliding Window Algorithm
- Token Bucket Algorithm
- Leaky Bucket (Concept)

### Phase 3
- Redis Integration
- RedisTemplate
- Atomic Operations
- Key Expiration (TTL)

### Phase 4
- Dockerize Redis
- Dockerize Spring Boot
- Multi-instance Simulation

### Phase 5
- Bucket4j Integration
- Distributed Rate Limiting
- Production Best Practices

---

## Features

- Per-IP Rate Limiting
- Configurable Request Limits
- Configurable Time Window
- Redis-backed Distributed Rate Limiting
- Custom Error Response
- Production Ready Structure

---

## Project Structure

src
├── controller
├── filter
├── service
├── config
├── model
├── exception
└── util

---

## Future Improvements

- User-based Rate Limiting
- API Key Rate Limiting
- JWT-based Rate Limiting
- Role-based Limits
- Dynamic Limits from Database
- Monitoring using Prometheus
- Grafana Dashboard
- Spring Cloud Gateway Rate Limiting

---

## Status

🚧 Project Under Development

This repository is being built step-by-step while learning Spring Boot, Redis, and Distributed System concepts.

---

## License

MIT License
