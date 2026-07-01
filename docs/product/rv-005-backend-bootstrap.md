# RV-005 Backend Bootstrap

## Objective

Create the initial backend foundation of RoosterViewCall.

The goal is to have a production-ready Spring Boot application capable of running locally using Docker.

No business logic will be implemented during this issue.

---

# Scope

Included

- Spring Boot project
- Java 21
- Maven
- PostgreSQL
- Docker
- Docker Compose
- Flyway
- Spring Data JPA
- Spring Validation
- OpenAPI (Swagger)
- Actuator
- Environment Profiles
- Health Check endpoint

Not Included

- Authentication
- JWT
- Users
- Orders
- Kitchen
- Business Rules

---

# Deliverables

The repository shall contain:

backend/
Dockerfile
docker-compose.yml
pom.xml

src/main/java

src/main/resources

application.yml

application-dev.yml

application-prod.yml

db/migration

README.md

---

# Acceptance Criteria

The project builds successfully.

Docker Compose starts all services.

PostgreSQL starts correctly.

Spring Boot starts correctly.

Flyway executes migrations.

Swagger UI is available.

Health endpoint responds successfully.

No compilation errors.

No warnings that prevent startup.

---

# Definition of Done

Project builds.

Project runs.

Docker works.

Database connected.

Swagger working.

Health endpoint working.

README updated.

Issue ready for review.