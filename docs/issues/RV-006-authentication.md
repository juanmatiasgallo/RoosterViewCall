# RV-006 Authentication

> Status: Specification only. No code has been implemented under this issue. This document is the functional and technical specification to be reviewed before implementation begins.

---

## 1. Objective

Establish the authentication and role-based authorization foundation for RoosterViewCall.

This issue introduces stateless, JWT-based login for staff roles on top of the backend bootstrapped in [RV-005](rv-005-backend-bootstrap.md), so that every future module (Orders, Kitchen Board, Business Configuration, Dashboard) can rely on a single, consistent security mechanism instead of inventing its own.

No business entities (Order, Ticket, Printer, Display) and no business logic are part of this issue.

---

## 2. Scope

Included:

- Spring Security integration (stateless, filter-based)
- Login endpoint (email + password → access token + refresh token)
- Access token issuance and validation (JWT)
- Refresh token issuance, rotation, and revocation
- Logout (refresh token revocation)
- Password hashing (BCrypt)
- Password policy validation (Spring Validation)
- Role-based authorization model (RBAC) for Administrator, Cashier, Kitchen, Supervisor
- Minimal `Business` and `User` persistence — only the fields required to support login and business-scoped authorization
- A seeding mechanism for the first Administrator account per deployment
- Environment-profile-aware security configuration (dev/prod), consistent with RV-005
- Keeping Actuator health and Swagger UI publicly accessible, as established in RV-005

Not included (see [Out of Scope](#3-out-of-scope)):

- Full User Management (create/update/deactivate accounts by an Administrator)
- Business Configuration (profile, printers, displays, ticket numbering)
- Orders, Kitchen, Public Display, Dashboard
- Frontend implementation

---

## 3. Out of Scope

- **User Management CRUD** — creating, updating, and deactivating staff accounts through an API is a distinct module (see [01-modules.md](../product/01-modules.md)) and belongs to a dedicated follow-up issue. RV-006 only provides the `User` persistence needed for login and a seeded Administrator, not management endpoints.
- **Business Configuration** — business profile, printers, displays, and ticket numbering are out of scope. Only the minimal `Business` record needed as a foreign key owner for `User` is included.
- **Self-service password reset / "forgot password"** — no user story requires it for MVP; deferred.
- **Multi-factor authentication (MFA)** — not required for MVP-scale small food businesses.
- **OAuth / social login / SSO** — not part of the approved stack ([02-tech-stack.md](../../.claude/02-tech-stack.md) specifies Spring Security + JWT only).
- **Rate limiting / brute-force protection** beyond what is noted as a risk — infrastructure-level mitigation (reverse proxy) is assumed, not application code, in this issue.
- **Public Display or Customer authentication** — per BR-02, the Public Display is explicitly accessible without authentication, and Customers never have accounts.
- **Multi-business / multi-location support** — the MVP serves exactly one Business per deployment ([glossary.md](../domain/glossary.md)). Business scoping is included structurally (see §17) to avoid rework in roadmap v1.2, but multi-tenant switching flows are not implemented now.
- **Frontend implementation** — this issue, like RV-005, is backend-only.

---

## 4. Functional Requirements

- **FR-1** — A User authenticates with email and password via a login endpoint.
- **FR-2** — On successful login, the system issues a short-lived JWT access token and a longer-lived refresh token.
- **FR-3** — The access token carries the User's id, Business id, and role as claims.
- **FR-4** — Requests to protected endpoints without a valid access token are rejected with `401 Unauthorized`.
- **FR-5** — Requests from an authenticated User whose role does not permit the action are rejected with `403 Forbidden`.
- **FR-6** — A User can exchange a valid, non-revoked refresh token for a new access token without re-entering credentials.
- **FR-7** — A User can log out; logout revokes the refresh token so it can no longer be exchanged.
- **FR-8** — Passwords are stored only as a salted one-way hash (BCrypt); plaintext passwords are never persisted or logged.
- **FR-9** — A deactivated User cannot authenticate, even with correct credentials.
- **FR-10** — Every User belongs to exactly one Business (BR-25); the access token's `businessId` claim scopes authorization for all future business-data checks.
- **FR-11** — The system provides a way to seed one initial Administrator per Business so the system is usable before a User Management UI exists.
- **FR-12** — Login, refresh, health, and API documentation endpoints remain accessible without a token; all other endpoints require authentication by default.
- **FR-13** — An authenticated User can retrieve their own profile (id, email, role, businessId) to let the frontend bootstrap its session state.

---

## 5. Non-Functional Requirements

- **Stateless** — no server-side HTTP session; authorization state travels in the access token, enabling horizontal scaling without sticky sessions.
- **Externalized secrets** — the JWT signing secret and token TTLs are read from environment variables, never hard-coded, following the `application-dev.yml` / `application-prod.yml` pattern from RV-005.
- **Fast validation** — access token validation is local signature verification; it must not require a database round trip.
- **No information leakage** — login failures return a generic "invalid credentials" message; the API never reveals whether the email exists.
- **Auditable** — authentication failures and successes are logged, without ever logging credentials or token values.
- **Non-regression** — Actuator health and Swagger UI must remain reachable exactly as in RV-005; security configuration must whitelist them explicitly rather than accidentally lock them behind auth.
- **Transport security** — TLS termination is assumed at the reverse proxy / VPS level (per [04-roadmap.md](../product/04-roadmap.md) deployment model); the application itself does not implement TLS.
- **Consistency** — password hashing and JWT issuance are each centralized in a single reusable component, so later modules (e.g., User Management) reuse them rather than reimplementing.

---

## 6. User Stories

- As a staff member (Administrator, Cashier, Kitchen, or Supervisor), I want to log in with my email and password, so that I can access only the features permitted by my role.
- As a staff member, I want my session to stay active without re-entering my password constantly during a shift, so that I can work efficiently (refresh token).
- As a staff member, I want to log out, so that my session cannot be reused by someone else on a shared device.
- As the system, I want to reject unauthenticated or unauthorized requests, so that Business data stays protected (BR-01, BR-04).
- As an Administrator, I want the first account in a new deployment to already exist, so that I can start configuring the system immediately without a chicken-and-egg problem.

These extend the role-based stories already defined in [03-user-stories.md](../product/03-user-stories.md), which assume authenticated access but do not describe the mechanism itself.

---

## 7. Business Rules

Existing rules from [business-rules.md](../domain/business-rules.md) that this module must enforce:

- **BR-01** — Only authenticated Users can access the management interface.
- **BR-02** — The Public Display is accessible without authentication.
- **BR-03** — Each User has exactly one role. That role cannot be empty.
- **BR-04** — A User can only perform the actions permitted by their assigned role.
- **BR-25** — Every User belongs to exactly one Business.

Proposed new rules (pending addition to `business-rules.md` once this issue is implemented):

- **BR-30** — A deactivated User cannot authenticate under any circumstances, regardless of credential validity.
- **BR-31** — A User's email is unique within their Business.
- **BR-32** — A refresh token is single-use: exchanging it for a new access token immediately invalidates it and issues a new one (rotation).
- **BR-33** — Revoking a refresh token (logout) invalidates only that token; other active sessions for the same User are unaffected.

---

## 8. Roles Involved

| Role | Authenticates? | Notes |
|---|---|---|
| Administrator | Yes | Full access; will manage Users and Business configuration in future issues. |
| Cashier | Yes | Order/ticket actions in future issues; here, just a login subject. |
| Kitchen | Yes | Kitchen Board actions in future issues; here, just a login subject. |
| Supervisor | Yes | Read-only dashboards in future issues; here, just a login subject. |
| Public Display | No | Per BR-02, explicitly unauthenticated. |
| Customer | No | Never has an account (per [entities.md](../domain/entities.md)). |

RV-006 does not implement role-specific business permissions (e.g., "only Cashier can create Orders") because those business actions don't exist yet. It implements the **mechanism** — role is known and verifiable on every request — that those future modules will depend on.

---

## 9. Security Model

- **Framework**: Spring Security, configured for stateless API authentication (no form login, no server-side session, no CSRF tokens — justified below).
- **Password storage**: BCrypt (`PasswordEncoder` bean), one central bean reused anywhere a password is set.
- **Transport**: Bearer token in the `Authorization` header (`Authorization: Bearer <access-token>`).
- **CSRF**: disabled. CSRF protects cookie-based sessions; this API uses no cookies for authentication, only bearer tokens explicitly attached by the client, so CSRF does not apply.
- **CORS**: configured per environment (allowed origins driven by profile/env var), since the frontend will be served from a different origin during local development.
- **Endpoint exposure matrix**:

  | Endpoint group | Access |
  |---|---|
  | `/actuator/health` | Public |
  | `/swagger-ui.html`, `/v3/api-docs/**` | Public |
  | `/api/v1/auth/login` | Public |
  | `/api/v1/auth/refresh` | Public |
  | `/api/v1/auth/logout` | Authenticated |
  | `/api/v1/auth/me` | Authenticated |
  | Everything else | Authenticated by default (deny-by-default posture) |

- **Multi-tenancy hook**: the `businessId` claim in the access token is the seam later modules will use to scope all queries to the caller's Business (BR-24). Establishing this now avoids retrofitting tenant isolation into every future entity.

---

## 10. Authentication Flow

1. Client sends `POST /api/v1/auth/login` with email and password.
2. Server loads the `User` by email (scoped to the deployment's Business), checks the `active` flag, and verifies the password against the stored BCrypt hash.
3. On success, the server issues:
   - an **access token** (short-lived JWT), and
   - a **refresh token** (opaque, persisted server-side, see §13).
4. The client attaches the access token as a Bearer header on every subsequent request.
5. A Spring Security filter validates the token's signature and expiration on each request, then populates the security context with the User's id, Business id, and role.
6. When the access token expires, the client calls `POST /api/v1/auth/refresh` with the refresh token to receive a new access token and a rotated refresh token.
7. On logout, the client calls `POST /api/v1/auth/logout`; the server revokes the refresh token so it can no longer be exchanged.

```
Client                      Backend                     Database
  │  POST /auth/login          │                            │
  │ ───────────────────────────▶                            │
  │                            │  load User by email         │
  │                            │ ────────────────────────────▶
  │                            │ ◀────────────────────────────
  │                            │  verify password + active   │
  │  access + refresh tokens   │  persist refresh token       │
  │ ◀───────────────────────────│ ────────────────────────────▶
  │                            │                            │
  │  Authorization: Bearer ... │                            │
  │ ───────────────────────────▶  validate JWT signature      │
  │                            │  (no DB round trip)         │
```

---

## 11. Authorization Model (RBAC)

- Roles are a fixed, closed set matching BR-03: `ADMINISTRATOR`, `CASHIER`, `KITCHEN`, `SUPERVISOR`. No custom or dynamic roles/permissions in the MVP.
- Each User has exactly one role (BR-03) — no multi-role assignment.
- No implied role hierarchy. Even though some business rules give the Administrator broader powers (e.g., BR-10, BR-20), those are modeled as **explicit** permissions per rule rather than inheritance, so future authorization checks read literally from the documented business rules instead of an implicit "Admin can do everything" assumption that could silently grant unintended access.
- Enforcement happens at two levels once controllers exist in future issues:
  - **URL-level** — coarse grouping via the security filter chain (public vs. authenticated), as in §9.
  - **Method-level** — fine-grained, per-action checks (e.g., `@PreAuthorize`) tied directly to specific business rules (BR-05, BR-07, BR-18, etc.) as those actions are implemented.
- RV-006 itself introduces no method-level business checks, since no business actions exist yet — it delivers the authenticated principal (id, role, businessId) that those checks will consume.

---

## 12. JWT Strategy

- **Algorithm**: HS256 (symmetric). RS256 would only be justified by multiple independent services validating tokens without sharing a secret; RoosterViewCall is a single monolithic backend (per architecture), so a shared HMAC secret is simpler and equally secure (KISS).
- **Signing secret**: sourced from an environment variable (e.g., `JWT_SECRET`), minimum 256-bit, never committed to source control — consistent with the env-var-only convention already established for `application-prod.yml` in RV-005.
- **Claims**: `sub` (user id), `businessId`, `role`, `iat`, `exp`, `jti` (unique token id, reserved for future blacklisting needs).
- **What is never included**: password hash, or any other sensitive data — JWT payloads are base64-encoded, not encrypted, and must be treated as visible to the client.
- **Access token TTL**: short (proposed default 15 minutes), configurable per profile via environment variable — dev may use a longer TTL for convenience, prod stays strict.
- **Validation**: signature and expiration checked in-process on every request; no database lookup required for access tokens (only refresh tokens touch the database).

---

## 13. Refresh Token Strategy

- Refresh tokens are **opaque, high-entropy random strings** — not JWTs — because their defining requirement is server-side revocability, which a stateless JWT cannot provide without an additional denylist.
- Refresh tokens are persisted **hashed** (never in plaintext) in a `refresh_tokens` table, linked to the issuing User.
- **Rotation**: each use of a refresh token immediately invalidates it and issues a new one (BR-32). This limits the value of a leaked refresh token to a single use.
- **Revocation**: logout marks the specific refresh token as revoked; it can never be exchanged again, while other active sessions for the same User are unaffected (BR-33).
- **TTL**: longer than the access token (proposed default 7 days), fixed expiration rather than sliding, to keep the MVP implementation simple.
- **Reuse detection** (presenting an already-rotated/revoked token) is a well-known hardening technique — noted as a future improvement (see [Risks](#18-risks)) rather than mandatory for this MVP-scale issue, to avoid over-engineering ahead of demonstrated need.

---

## 14. Password Policy

- Minimum 8 characters, containing at least one letter and one digit.
- Deliberately avoids heavier "enterprise" complexity rules (mandatory special characters, no dictionary words, etc.) — the product vision calls for minimal training and friction for small-business staff who are not necessarily technical; 8-char alphanumeric is a reasonable balance for an internal staff tool, not a public consumer product.
- Enforced via Spring Validation on the request DTO (reusing the `spring-boot-starter-validation` dependency already added in RV-005), not via ad-hoc manual checks.
- Passwords are hashed with BCrypt before persistence; the encoder's work factor is a configuration value, not hard-coded, so it can be tuned per environment.
- No forced periodic password rotation in the MVP — rotation policies are a known source of user friction with limited modern security benefit, and no business rule or user story requires it.
- Administrator-driven password resets ("set a temporary password for a User") are part of the future User Management issue, not RV-006.

---

## 15. API Endpoints to be Implemented

| Method | Path | Auth required | Purpose |
|---|---|---|---|
| POST | `/api/v1/auth/login` | No | Authenticate with email + password; returns access + refresh tokens. |
| POST | `/api/v1/auth/refresh` | No (valid refresh token required) | Exchange a refresh token for a new access token and a rotated refresh token. |
| POST | `/api/v1/auth/logout` | Yes | Revoke the current refresh token. |
| GET | `/api/v1/auth/me` | Yes | Return the authenticated User's id, email, role, and businessId. |

No `/register` endpoint exists — this is an internal staff tool where accounts are provisioned by an Administrator (future issue) or seeded (§4, FR-11), not self-service sign-up.

---

## 16. Database Tables Required

Described conceptually; no migrations are created in this issue.

**`businesses`** (minimal shape — only what `users` needs as a foreign key owner; full configuration is a future issue)
- `id`
- `name`
- `created_at`, `updated_at`

**`users`**
- `id`
- `business_id` (FK → `businesses.id`)
- `email` (unique within `business_id`, per BR-31)
- `password_hash`
- `role` (enum: `ADMINISTRATOR`, `CASHIER`, `KITCHEN`, `SUPERVISOR`, per BR-03)
- `active` (boolean, per BR-30)
- `created_at`, `updated_at`

**`refresh_tokens`**
- `id`
- `user_id` (FK → `users.id`)
- `token_hash`
- `issued_at`
- `expires_at`
- `revoked_at` (nullable)
- `replaced_by_token_id` (nullable, self-referencing FK — supports rotation chain and future reuse detection)

An optional `auth_events` (login attempt audit log) table is noted as a possible future addition, not required for this issue — see [Risks](#18-risks).

---

## 17. Architecture Considerations

- Follows the layering convention already defined for the project (Controller → Service → Repository, with DTOs at the boundary and Mappers between DTO and Entity — per [04-coding-standards.md](../../.claude/04-coding-standards.md)).
- Anticipated new packages (to be created when implementation starts, not in this issue): `security` (JWT filter, token service, Spring Security configuration), `auth` (auth controller, auth service, request/response DTOs), `user` (minimal User entity and repository) — and a shared `Business` entity, kept intentionally thin.
- Builds directly on the RV-005 foundation: Spring Data JPA + Flyway for schema evolution, Spring Validation for request DTOs, Actuator/Swagger left publicly reachable as already configured.
- Password hashing and JWT issuance are each implemented once and reused by every future module that touches credentials or identity — avoiding duplicated logic (DRY).
- JWT secret and token TTLs follow the same environment-profile pattern (`application-dev.yml` / `application-prod.yml`, environment variables in prod) introduced in RV-005, rather than a new configuration mechanism.
- Embedding `business_id` into the token now — even though the MVP runs one Business per deployment — is a deliberate, low-cost decision to avoid an expensive retrofit when multi-business/multi-location support arrives in roadmap v1.2.
- This issue is backend-only; no frontend login screen or token-storage strategy is designed here.

---

## 18. Risks

- **JWT secret mismanagement** — if the signing secret were hard-coded or committed, all tokens would be forgeable. Mitigated by mandating environment-variable-only secrets, consistent with the RV-005 prod-profile convention.
- **No brute-force protection at the application layer** — `/auth/login` has no built-in rate limiting in this issue. Accepted as an MVP risk; mitigation is expected at the infrastructure layer (reverse proxy / VPS firewall), and should be revisited before a public-facing production rollout.
- **No refresh-token reuse detection** — rotation alone (§13) limits but does not fully eliminate replay risk from a stolen refresh token before its first use. Full reuse detection (revoking an entire token family) is deferred to avoid over-engineering the MVP; revisit if incidents warrant it.
- **No self-service password reset** — an Administrator must intervene manually if a User forgets their password (until the future User Management issue exists). Acceptable for a small-business, low-headcount MVP.
- **Seeding the first Administrator** must not rely on a hardcoded default password (a well-known vulnerability class). Implementation must source seed credentials from environment variables and should force a password change on first login.
- **Token/role coupling** — a role or Business change for a User only takes effect once their current access token expires (up to the access-token TTL). Mitigated by keeping that TTL short (§12).

---

## 19. Acceptance Criteria

- A seeded Administrator can log in and receive a valid access token and refresh token.
- Requests to a protected endpoint without a token return `401 Unauthorized`.
- Requests with a valid token but an insufficient role return `403 Forbidden`.
- An expired access token is rejected, and a new one can be obtained via `/api/v1/auth/refresh` using a valid refresh token.
- Logging out revokes the refresh token; a subsequent refresh attempt with that same token fails.
- A deactivated User cannot log in even with correct credentials.
- Passwords are never stored, returned, or logged in plaintext.
- Actuator health and Swagger UI remain publicly reachable, unchanged from RV-005.
- All new configuration (JWT secret, TTLs, seed credentials) is environment-profile aware (dev/prod).

---

## 20. Definition of Done

For this document:

- Specification covers all 20 required sections and is grounded in existing domain/product/engineering documentation — no invented scope.
- Ready for Software Architect / Product Owner review.

For the eventual implementation (tracked as a follow-up to this issue):

- Code compiles and starts successfully alongside the RV-005 bootstrap.
- All Acceptance Criteria (§19) pass.
- Tests cover login, refresh, logout, and role-rejection scenarios.
- Documentation (`business-rules.md`, `entities.md`, `relationships.md`) updated to incorporate the proposed rules and the `User`/`Business` entities once implemented.
- No unrelated files changed; architecture and layering conventions respected.
- Ready for Pull Request.
