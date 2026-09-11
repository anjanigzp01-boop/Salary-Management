# ACME Salary Management

A web application for ACME HR managers to maintain employee compensation data for a global workforce of 10,000 employees and answer practical questions about pay.

## Technology

- **Backend:** Java 17, Spring Boot, Spring Data JPA, SQLite
- **Frontend:** React, TypeScript, Vite
- **Testing:** JUnit 5 / Mockito and Vitest / React Testing Library

## Repository layout

```
backend/                 Spring Boot API
frontend/                React single-page application
docs/                    Product, architecture, and delivery decisions
```

## Planned local workflow

1. Seed a local SQLite database with 10,000 deterministic employee records.
2. Run the Spring Boot API on `http://localhost:8080`.
3. Run the Vite application on `http://localhost:5173`.

## Run locally

In one terminal, run `cd backend && mvn spring-boot:run`. The first startup creates a local SQLite file and seeds 10,000 synthetic employees. In a second terminal, run `cd frontend && npm install && npm run dev`. Open `http://localhost:5173`.

Detailed product scope and the delivery plan are in [docs](docs/).
