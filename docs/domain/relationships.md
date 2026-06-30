# Entity Relationships

This document describes how the domain entities of RoosterViewCall relate to each other.

Relationships are described in business terms. For technical implementation, see the architecture documentation.

---

## Overview

```
Business
├── has many → Users
├── has many → Orders
├── has many → Printers
├── has many → Displays
└── has one  → Ticket Numbering Configuration

Order
├── belongs to → Business
├── created by → User (Cashier)
├── generates  → one Ticket
├── has many   → Order Items
└── has one    → current Order Status (with history)

Ticket
└── belongs to → one Order

Display
├── belongs to → Business
├── shows      → Orders (filtered by status)
└── shows      → Promotional Content
```

---

## Business and Users

A Business has many Users.

Every User belongs to exactly one Business.

A User's role determines the actions they are allowed to perform within that Business.

---

## Business and Orders

A Business has many Orders.

Every Order belongs to exactly one Business.

Orders are never shared across Businesses.

---

## Business and Printers

A Business has one or more Printers.

Printers are used to print Tickets at the point of sale.

---

## Business and Displays

A Business has one or more Displays.

Each Display operates independently and shows real-time order status for its Business.

---

## Order and Ticket

Every Order generates exactly one Ticket at the moment of creation.

A Ticket cannot exist without an Order.

An Order cannot exist without a Ticket.

They are created together as a single operation.

---

## Order and Order Items

An Order contains one or more Order Items.

Each Order Item belongs to exactly one Order.

An Order with no Items is not a valid Order and cannot be created.

---

## Order and Order Status

An Order has one current Order Status at any point in time.

The system maintains a full history of all status transitions for an Order, including timestamps.

Only authorized Users can trigger status transitions. The allowed transitions are defined in [business-rules.md](business-rules.md).

---

## User and Order

A User with the Cashier role creates Orders.

A User with the Kitchen role updates the Order status (from Received to In Preparation, and from In Preparation to Ready).

A Supervisor does not modify Orders — they only observe.

---

## Display and Orders

A Display shows all Orders in the current Business that are in status In Preparation or Ready.

Orders with status Received, Delivered, or Cancelled are not shown on the Display.

When an Order transitions to Ready, the Display highlights it and triggers an audible notification.

---

## Display and Promotional Content

A Display shows Promotional Content in its dedicated area alongside the order status area.

Promotional Content is configured by the Administrator and is not linked to any individual Order.
