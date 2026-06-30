# Modules

This document lists the functional modules that compose RoosterViewCall.

---

## Authentication

Login and session management for staff roles (Administrator, Cashier, Kitchen, Supervisor).

The Public Display and Customer flows do not require authentication.

---

## User Management

Create, update, and deactivate staff accounts.

Assign roles and permissions.

---

## Business Configuration

Configure business identity, branding, and operating parameters:

- Business profile
- Printers
- Displays
- Ticket numbering rules

---

## Ticket Generation

Generate sequential order tickets at the point of sale.

Print physical tickets for the customer and/or the kitchen.

---

## Order Management

Create and update orders.

Track order status across its lifecycle: received → in preparation → ready → delivered.

Process payments at the point of sale.

---

## Kitchen Board

Real-time view of pending and in-progress orders for kitchen staff.

Allow kitchen staff to start preparation, mark items complete, and notify when an order is finished.

---

## Public Display

Customer-facing screen, divided into two sections:

- **Promotional area** — food promotions, videos, images, seasonal offers, brand identity.
- **Order status area** — orders in preparation, orders ready for pickup, highlighted newly completed orders, notification sounds, smooth animations.

---

## Dashboard & Statistics

Operational visibility for supervisors and administrators:

- Waiting time metrics
- Kitchen performance metrics
- Order volume and historical trends

---

## Notifications

Audible and visual notifications for newly completed orders, both in the kitchen board and on the public display.

---

## Deployment & Infrastructure

Docker and Docker Compose based deployment, designed to run on a single VPS or on-site server with minimal setup.
