# RoosterViewCall — Backend

Backend service for RoosterViewCall, built with Spring Boot.

This is the foundational bootstrap of the backend (RV-005). It contains no authentication, business entities, or business logic — only the infrastructure required to run, connect to a database, and expose operational endpoints.

---

## Tech Stack

- Java 21
- Spring Boot 3.3
- Maven
- PostgreSQL 16
- Flyway (database migrations)
- Spring Data JPA
- Spring Validation
- Springdoc OpenAPI (Swagger UI)
- Spring Boot Actuator
- Docker / Docker Compose

---

## Running Locally with Docker

1. Copy the environment template:

   ```bash
   cp .env.example .env
   ```

2. Start the stack:

   ```bash
   docker compose up --build
   ```

This starts PostgreSQL and the Spring Boot application. On startup, Flyway applies the database migrations automatically.

- API base URL: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI spec: http://localhost:8080/v3/api-docs
- Health check: http://localhost:8080/actuator/health

Stop the stack with `docker compose down`. Add `-v` to also remove the database volume.

---

## Running Locally without Docker

Requires a local PostgreSQL 16 instance and Java 21.

```bash
mvn spring-boot:run
```

By default, the `dev` profile is active and connects to `localhost:5432` using the credentials in `application-dev.yml` (overridable via `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`, `DB_PASSWORD` environment variables).

---

## Building

```bash
mvn clean package
```

## Testing

```bash
mvn test
```

The test suite starts a real PostgreSQL instance via Testcontainers to verify the application context, and Flyway migrations, load correctly.

---

## Environment Profiles

| Profile | Purpose |
|---|---|
| `dev` | Local development. Verbose logging, full Actuator exposure, SQL logging enabled. |
| `prod` | Production. Minimal Actuator exposure, no SQL logging, all datasource values required via environment variables. |

Select the active profile with `SPRING_PROFILES_ACTIVE` (defaults to `dev`).

---

## Project Structure

```
backend/
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── src/
│   ├── main/java/com/roostercode/roosterviewcall/
│   │   ├── RoosterViewCallApplication.java   # application entry point
│   │   └── config/OpenApiConfig.java         # Swagger/OpenAPI metadata
│   ├── main/resources/
│   │   ├── application.yml                   # shared configuration
│   │   ├── application-dev.yml               # dev profile overrides
│   │   ├── application-prod.yml              # prod profile overrides
│   │   └── db/migration/                     # Flyway migrations
│   └── test/java/com/roostercode/roosterviewcall/
│       └── RoosterViewCallApplicationTests.java
```

---

## Scope

This bootstrap intentionally does **not** include authentication, users, orders, kitchen workflows, or any other business logic. Those will be introduced in subsequent issues on top of this foundation.
