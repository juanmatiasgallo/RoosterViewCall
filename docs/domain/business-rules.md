# Business Rules

This document defines the rules and invariants that govern business behavior in RoosterViewCall.

Every rule documented here must be enforced by the system, regardless of the interface through which it is triggered.

---

## Authentication and Access

**BR-01** — Only authenticated Users can access the management interface (Cashier view, Kitchen view, Dashboard, Administration).

**BR-02** — The Public Display is accessible without authentication.

**BR-03** — Each User has exactly one role. That role cannot be empty.

**BR-04** — A User can only perform the actions permitted by their assigned role.

---

## User Roles and Permissions

**BR-05** — Only the Administrator can create, update, or deactivate User accounts.

**BR-06** — Only the Administrator can configure the Business profile, Printers, Displays, and ticket numbering.

**BR-07** — Only the Cashier can create new Orders and record payment.

**BR-08** — Only the Kitchen role can transition an Order from Received to In Preparation, and from In Preparation to Ready.

**BR-09** — The Supervisor role has read-only access to dashboards and statistics. A Supervisor cannot create, modify, or cancel Orders.

**BR-10** — The Administrator can cancel any Order, regardless of its current status.

---

## Order Creation

**BR-11** — An Order must contain at least one Order Item to be created.

**BR-12** — Every new Order generates exactly one Ticket at the moment of creation. Order and Ticket are created together as an atomic operation.

**BR-13** — Ticket numbers are sequential within a Business and follow the configured numbering rules (prefix, starting number, daily reset).

**BR-14** — Once assigned, a Ticket number cannot be reused within the same numbering sequence.

---

## Order Status Transitions

**BR-15** — An Order must follow the defined status sequence. The only valid transitions are:

- Received → In Preparation
- In Preparation → Ready
- Ready → Delivered
- Received → Cancelled
- In Preparation → Cancelled (Administrator only)

**BR-16** — A status transition cannot be reversed. An Order cannot move backward in its lifecycle (e.g. from Ready back to In Preparation).

**BR-17** — An Order in status Ready or Delivered cannot be cancelled.

**BR-18** — Only the Kitchen role can move an Order to In Preparation or Ready.

**BR-19** — Only the Cashier role can move an Order to Delivered.

**BR-20** — Only the Administrator can cancel an Order that is already In Preparation.

---

## Public Display

**BR-21** — The Public Display shows only Orders in status In Preparation or Ready.

**BR-22** — When an Order transitions to Ready, the Display highlights it and triggers an audible notification.

**BR-23** — An Order disappears from the Display after it transitions to Delivered or Cancelled.

---

## Data Integrity

**BR-24** — Every Order belongs to exactly one Business. Cross-business data access is not permitted.

**BR-25** — Every User belongs to exactly one Business.

**BR-26** — Deleting a User account does not delete the Orders they created. Historical operational data must be preserved.

**BR-27** — Order Items cannot be modified after the Order has been moved to In Preparation.

---

## Notifications

**BR-28** — Every status transition that results in an Order becoming Ready must trigger a visual and audible notification on the Public Display.

**BR-29** — Notifications are displayed in real time. There is no polling delay acceptable to the business.
