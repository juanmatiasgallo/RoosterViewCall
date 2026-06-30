# Domain Entities

This document describes the core business concepts of RoosterViewCall.

Each entity represents a meaningful concept in the food business domain. Entities are described in business terms only, without reference to database structure or code.

---

## Business

A Business is the organization that uses RoosterViewCall to manage its operations.

Each deployment of the system serves one Business.

Key attributes:

- Name and brand identity
- Ticket numbering configuration (starting number, prefix, daily reset)
- Registered printers
- Registered displays

A Business is the root of all operational data. Every order, every ticket, and every staff member belongs to a Business.

---

## User

A User is a staff member of the Business who has authenticated access to the management interface.

Every User has one of the following roles:

- Administrator
- Cashier
- Kitchen
- Supervisor

A User's role determines which areas of the system they can access and which actions they can perform.

See [docs/product/02-user-roles.md](../product/02-user-roles.md) for the full role descriptions.

---

## Customer

A Customer is the person who places an order at the point of sale.

Customers do not have accounts. They interact with the system only through:

- The Ticket they receive
- The Public Display where they track their order status

---

## Ticket

A Ticket is a reference document generated when an Order is created.

The Ticket is handed to the Customer so they can track their order.

Key attributes:

- Ticket number (sequential, scoped to the Business)
- Reference to the associated Order
- Timestamp of generation

Ticket numbers follow the Business's configured numbering rules. The format typically includes a numeric sequence and optionally a prefix or daily reset.

One Ticket corresponds to exactly one Order.

---

## Order

An Order is the central entity in the domain.

An Order represents a service request from a Customer, progressing through the kitchen until it is ready for pickup.

Key attributes:

- Reference number (linked to the Ticket)
- List of Order Items
- Current status
- Status history with timestamps
- The Cashier who created it
- Timestamp of creation

An Order moves through a defined sequence of statuses. See [order-lifecycle.md](order-lifecycle.md) for the complete lifecycle.

---

## Order Item

An Order Item is a single product or preparation unit within an Order.

Key attributes:

- Description of the item
- Quantity
- Any relevant notes (e.g. customizations, allergen requests)

An Order Item is meaningful to the kitchen for preparation purposes.

---

## Order Status

Order Status is the current state of an Order in its lifecycle.

Possible statuses:

- **Received** — the Order has been created and is waiting to be picked up by the kitchen.
- **In Preparation** — the kitchen has started working on the Order.
- **Ready** — the Order is prepared and waiting for the Customer to pick it up.
- **Delivered** — the Customer has collected the Order. The Order is closed.
- **Cancelled** — the Order was cancelled and will not be fulfilled.

Status transitions follow strict rules defined in [business-rules.md](business-rules.md) and [order-lifecycle.md](order-lifecycle.md).

---

## Printer

A Printer is a physical printing device registered to a Business.

Printers are used to generate physical Tickets at the point of sale.

Key attributes:

- Name or identifier
- Connection configuration

A Business may have one or more registered Printers.

---

## Display

A Display is a screen registered to a Business for public-facing order status communication.

Displays are passive — they show information without requiring user interaction.

Key attributes:

- Name or identifier
- Configuration settings

A Display is divided into two areas:

- **Promotional area** — shows promotional media (images, videos, seasonal offers, brand content).
- **Order status area** — shows orders in preparation, orders ready for pickup, and highlights newly completed orders.

---

## Promotional Content

Promotional Content is media material (images, videos, text banners) configured to appear on the Display alongside the order status area.

Promotional Content is managed by the Administrator and serves brand identity and marketing purposes.
