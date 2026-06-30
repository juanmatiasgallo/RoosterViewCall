# Glossary

This document defines the terms used across the RoosterViewCall domain.

All engineers and AI agents must use these terms consistently. Using different words to mean the same thing creates ambiguity in documentation, code, and communication.

---

## Administrator

A staff role with full access to the system configuration.

Responsibilities include managing Users, configuring Printers, Displays, and Business settings.

---

## Business

The food business organization that operates RoosterViewCall.

All operational data (Orders, Users, Tickets, Displays, Printers) belongs to a Business.

In the MVP, each deployment serves exactly one Business.

---

## Cancelled

An Order Status indicating that the Order was voided and will not be fulfilled.

A Cancelled Order is preserved in the historical record but is no longer active.

---

## Cashier

A staff role responsible for taking orders and serving customers at the point of sale.

The Cashier creates Orders, prints Tickets, receives payment, and marks Orders as Delivered.

---

## Customer

The person who places an order.

Customers do not have accounts. They interact with the system only through the Ticket they receive and the Public Display where they track their order.

---

## Delivered

An Order Status indicating that the Customer has collected their order.

A Delivered Order is the final closed state. No further transitions are possible.

---

## Display

A screen registered to the Business for customer-facing communication.

Divided into two areas: the Promotional area and the Order Status area.

Also referred to as Public Display.

---

## In Preparation

An Order Status indicating that the Kitchen has started preparing the Order.

---

## Kitchen

A staff role responsible for food preparation.

The Kitchen interacts with the system through the Kitchen Board.

---

## Kitchen Board

The interface used by the Kitchen role to view and manage Orders during preparation.

Shows Orders in status Received (pending) and In Preparation (active).

---

## Order

A service request from a Customer, containing one or more Order Items, progressing through a defined lifecycle from creation to delivery.

The Order is the central entity in the domain.

---

## Order Item

A single product or preparation unit within an Order.

Describes what the kitchen needs to prepare for a specific Order.

---

## Order Status

The current state of an Order in its lifecycle.

Possible values: Received, In Preparation, Ready, Delivered, Cancelled.

---

## Point of Sale

The counter or workstation where the Cashier interacts with Customers to take orders and receive payment.

Also referred to as POS.

---

## Printer

A physical printing device registered to the Business.

Used to generate physical Tickets at the point of sale.

---

## Promotional Content

Media material (images, videos, text banners) displayed in the promotional area of the Display.

Configured by the Administrator. Not linked to any specific Order.

---

## Public Display

See Display.

The term "Public Display" emphasizes the customer-facing nature of the screen.

---

## Ready

An Order Status indicating that the Order has been fully prepared by the Kitchen and is waiting for the Customer to pick it up.

When an Order becomes Ready, the Public Display highlights it and plays an audible notification.

---

## Received

An Order Status indicating that the Order has been created and is waiting to be picked up by the Kitchen.

This is the initial status of every Order.

---

## RoosterCode

The organization that builds and maintains RoosterViewCall.

RoosterViewCall is the flagship software product of RoosterCode and serves as the reference architecture for future products.

---

## RoosterViewCall

The Queue & Kitchen Management System described in this documentation.

---

## Supervisor

A staff role with read-only access to dashboards and operational statistics.

Cannot create, modify, or cancel Orders.

---

## Ticket

A reference document generated when an Order is created, given to the Customer at the point of sale.

Contains a sequential ticket number that the Customer uses to identify their order on the Public Display.

One Ticket corresponds to exactly one Order.

---

## Ticket Number

A sequential identifier assigned to a Ticket at the time of creation.

Configured by the Administrator (prefix, starting number, daily reset rules).

Ticket numbers are unique within a numbering sequence and cannot be reused.

---

## User

A staff member of the Business with authenticated access to the management interface.

Every User has exactly one role: Administrator, Cashier, Kitchen, or Supervisor.
