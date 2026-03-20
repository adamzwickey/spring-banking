# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Spring Banking is a sample banking application built with Spring Boot 4.0.3. It is adapted from Spring PetClinic and remodeled around a banking domain (customers, accounts, transactions). It uses server-side rendering with Thymeleaf templates and JPA for persistence.

## Build & Run Commands

Both Maven and Gradle are supported. Java 17+ is required.

### Maven (primary, used in CI)
- **Build & test:** `./mvnw -B verify`
- **Run app:** `./mvnw spring-boot:run`
- **Build container image:** `./mvnw spring-boot:build-image`
- **Compile CSS (SCSS changes):** `./mvnw package -P css`
- **Run a single test class:** `./mvnw -Dtest=ClassName test`

### Gradle
- **Build & test:** `./gradlew build`
- **Run app:** `./gradlew bootRun`
- **Run a single test class:** `./gradlew test --tests "org.springframework.samples.banking.ClassName"`

## Code Formatting & Linting

The project enforces **Spring Java Format** (v0.0.47) and **Checkstyle** (v12.3.1). Formatting is validated during the Maven `validate` phase and in the Gradle build. The `nohttp` checkstyle rule ensures no plain `http://` URLs leak into source files.

- Format check runs automatically on build. Fix violations before committing.
- Use tabs for indentation (per `.editorconfig`).

## Architecture

### Package Structure (`org.springframework.samples.banking`)

- **`model`** - Base JPA entities: `BaseEntity` (id), `NamedEntity` (id + name), `Person` (firstName, lastName)
- **`customer`** - Core banking domain: `Customer`, `Account`, `AccountType`, `Transaction`, plus controllers, repositories, validators, and the `AccountService`
- **`system`** - Infrastructure: `CacheConfiguration`, `WelcomeController`, `WebConfiguration`

### Domain Model

- **Customer** extends Person; has email, phone, address, and a `@OneToMany` list of **Account**s
- **Account** has accountNumber, openedDate, balance (BigDecimal), type (AccountType), and a `@OneToMany` set of **Transaction**s
- **Transaction** records amount, transactionType (DEPOSIT/WITHDRAWAL), description, date, and balanceAfter
- **AccountService** handles transactional deposit/withdraw/transfer operations with balance tracking

### Key Design Patterns

- Repositories extend `JpaRepository` (Spring Data JPA) — `CustomerRepository`, `AccountTypeRepository`
- Controllers are MVC-style with `@ModelAttribute` methods for loading entities by path variable
- `AccountService` is the `@Service`/`@Transactional` layer for banking operations that modify balances
- Caching via JCache/Caffeine for `accountTypes`

## Database Configuration

- **Default:** H2 in-memory (no setup needed). Console at `/h2-console`.
- **MySQL:** `spring.profiles.active=mysql` — start via `docker compose up mysql`
- **PostgreSQL:** `spring.profiles.active=postgres` — start via `docker compose up postgres`
- Schema/data SQL scripts are in `src/main/resources/db/{h2,mysql,postgres}/`

## Testing

- **Unit tests** in `src/test/java` — standard JUnit 5 with Spring Boot Test
- **MySQL integration** uses Testcontainers (`MySqlIntegrationTests`)
- **Postgres integration** uses Docker Compose (`BankingIntegrationTests` with postgres profile)
- `BankingIntegrationTests` can be run from IDE with H2 for fast feedback

## Kubernetes / Helm

- Raw K8s manifests in `k8s/` (postgres-backed deployment with service bindings)
- Helm chart in `helm/banking/` for templated deployment with a PostgreSQL sidecar
