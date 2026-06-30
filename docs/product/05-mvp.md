# MVP Scope

This document defines what is, and is not, included in the first release of RoosterViewCall.

---

## In Scope

### Authentication

- Login for Administrator, Cashier, Kitchen, and Supervisor roles
- Session management

### User Management

- Create, update, and deactivate staff accounts
- Assign roles to staff accounts

### Business Configuration

- Configure business profile
- Configure printers
- Configure displays
- Configure ticket numbering

### Ticket Generation

- Generate sequential tickets at the point of sale
- Print tickets

### Order Management

- Create orders
- Update order status across its lifecycle
- Record payment at the point of sale

### Kitchen Board

- View pending and in-progress orders in real time
- Start preparation
- Complete preparation
- Trigger completion notifications

### Public Display

- Show orders in preparation and orders ready for pickup
- Highlight newly completed orders
- Play notification sounds
- Display promotional content alongside order status

### Dashboard & Statistics

- Basic operational metrics: order volume, waiting times, kitchen performance

### Deployment

- Docker and Docker Compose based deployment

---

## Out of Scope for MVP

The following are explicitly deferred to later releases (see [04-roadmap.md](04-roadmap.md)):

- Multi-location support
- Multi-printer support per location
- Promotional content scheduling
- Customer-facing order lookup
- External POS and payment system integrations
- AI-assisted operational recommendations
- Support for non-food verticals (hospitals, pharmacies, government offices, banks)

---

## Target Businesses for MVP

- Cafés
- Coffee Shops
- Food Trucks
- Restaurants
- Burger Shops
- Pizza Shops
- Bakeries
- Small Fast Food Businesses

---

## Definition of MVP Success

The MVP is considered successful when a single-location food business can:

1. Take an order and generate a ticket.
2. Track that order through preparation in the kitchen board.
3. Display its status and completion to the customer on the public display.
4. Allow a supervisor to review basic performance metrics.

All of the above must run reliably via Docker deployment with no manual server configuration beyond initial setup.
