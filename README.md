# Spring Banking Application

A sample banking application built with Spring Boot 3.2.12, demonstrating modern Java web development practices. This project is adapted from Spring PetClinic and remodeled around a banking domain with customers, accounts, and transactions.

## Overview

Spring Banking is a full-stack web application that showcases:
- **Server-side rendering** with Thymeleaf templates
- **JPA/Hibernate** for database persistence
- **Spring Data JPA** repositories
- **Transactional service layer** for banking operations
- **Multiple database support** (H2, MySQL, PostgreSQL)
- **Containerization** with Docker and Kubernetes deployment options

## Quick Start

**Requirements:** Java 17+, Maven

```sh
# Build and test
./mvnw -B verify

# Run the application
./mvnw spring-boot:run
```

The application will be available at `http://localhost:8080`

## Build Commands

- **Build & test:** `./mvnw -B verify`
- **Run app:** `./mvnw spring-boot:run`
- **Build container image:** `./mvnw spring-boot:build-image`
- **Compile CSS (SCSS changes):** `./mvnw package -P css`
- **Run a single test class:** `./mvnw -Dtest=ClassName test`

## Architecture

### Domain Model

The application models a simplified banking system:

- **Customer** - Extends Person with email, phone, and address. Has multiple accounts.
- **Account** - Contains account number, balance, type, and opening date. Has multiple transactions.
- **Transaction** - Records deposits and withdrawals with amount, type, description, and balance after transaction.
- **AccountType** - Defines different account types (e.g., Checking, Savings).

### Package Structure

Located under `org.springframework.samples.banking`:

- **`model`** - Base JPA entities: `BaseEntity`, `NamedEntity`, `Person`
- **`customer`** - Core banking domain: entities, controllers, repositories, validators, and `AccountService`
- **`system`** - Infrastructure: caching, web configuration, welcome controller

### Key Design Patterns

- **Repository Pattern** - Spring Data JPA repositories for data access
- **Service Layer** - `AccountService` handles transactional banking operations
- **MVC Controllers** - `@ModelAttribute` methods for entity loading
- **Caching** - JCache/Caffeine for account types

## Database Configuration

### H2 (Default)
No configuration needed. In-memory database with console at `/h2-console`.

### MySQL
```sh
# Start MySQL
docker compose up mysql

# Run application
./mvnw spring-boot:run -Dspring-boot.run.profiles=mysql
```

### PostgreSQL
```sh
# Start PostgreSQL
docker compose up postgres

# Run application
./mvnw spring-boot:run -Dspring-boot.run.profiles=postgres
```

Database scripts are located in `src/main/resources/db/{h2,mysql,postgres}/`

## Testing

- **Unit tests** - Standard JUnit 5 with Spring Boot Test
- **MySQL integration** - Uses Testcontainers (`MySqlIntegrationTests`)
- **PostgreSQL integration** - Uses Docker Compose (`BankingIntegrationTests`)

Run tests with H2 for fast feedback during development.

## Code Quality

The project enforces:
- **Spring Java Format** (v0.0.47) - Validated during Maven `validate` phase
- **Checkstyle** (v12.3.1) - Including `nohttp` rule to prevent plain HTTP URLs
- **Tab indentation** - Per `.editorconfig` configuration

Format checks run automatically on build. Fix violations before committing.

## Deployment

### Docker
```sh
./mvnw spring-boot:build-image
```

### Kubernetes
- Raw manifests in `k8s/` directory (PostgreSQL-backed deployment)
- Helm chart in `helm/banking/` for templated deployment

## Project Structure

```
src/
├── main/
│   ├── java/org/springframework/samples/banking/
│   │   ├── customer/          # Banking domain
│   │   ├── model/             # Base entities
│   │   └── system/            # Infrastructure
│   └── resources/
│       ├── db/                # Database scripts
│       ├── templates/         # Thymeleaf templates
│       └── static/            # CSS, JS, images
└── test/
    └── java/                  # Unit and integration tests
```

## Contributing

This is a sample application for demonstration purposes. When contributing:
1. Follow Spring Java Format conventions
2. Ensure all tests pass
3. Use tabs for indentation
4. Avoid plain HTTP URLs in source files
