# Roadmap

The roadmap is organized into incremental releases. Each phase builds on the previous one without breaking the existing architecture.

---

## MVP

- Authentication
- User management
- Ticket generation
- Order management
- Kitchen board
- Public display
- Dashboard and statistics
- Docker deployment

See [05-mvp.md](05-mvp.md) for the detailed MVP scope.

---

## v1.1

- Multi-printer support per business location
- Configurable order priority rules
- Improved dashboard filters (by date range, by status)
- Public display promotional content scheduling

---

## v1.2

- Multi-location support for businesses with more than one site
- Role-based reporting exports
- Customer-facing order lookup (e.g. by ticket number)
- Performance and load optimizations for high-volume businesses

---

## v2

- Expansion beyond food businesses into other queue-driven sectors (hospitals, pharmacies, government offices, banks, public institutions)
- Advanced analytics and historical trend forecasting
- Integrations with external POS and payment systems
- AI-assisted operational recommendations (e.g. predicted wait times, staffing suggestions)

---

## Guiding Principle

Each release must preserve the architecture established during the MVP. New verticals and features are additive extensions of the same domain model, not parallel rewrites.
