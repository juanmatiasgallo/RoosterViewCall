# Order Lifecycle

This document describes the complete lifecycle of an Order in RoosterViewCall, from creation to closure.

---

## Overview

```
[Cashier creates Order]
         │
         ▼
     RECEIVED
    /         \
   ▼           ▼
IN PREPARATION  CANCELLED
   /         \
  ▼           ▼
READY       CANCELLED
             (Admin only)
   │
   ▼
DELIVERED
```

---

## Status Definitions

### RECEIVED

The Order has been created by the Cashier.

A Ticket has been generated and handed to the Customer.

The Order is visible on the Kitchen Board, waiting to be picked up by kitchen staff.

The Order is not yet visible on the Public Display.

---

### IN PREPARATION

The Kitchen has accepted the Order and started preparing it.

The Order is now visible on the Public Display in the "in preparation" section.

Order Items cannot be modified from this point forward.

---

### READY

The Order has been fully prepared by the Kitchen.

The Order is highlighted on the Public Display to alert the Customer.

An audible notification is triggered on the Display.

The Order remains on the Display until it is moved to Delivered.

---

### DELIVERED

The Cashier has confirmed that the Customer collected the Order.

The Order is removed from the Kitchen Board and from the Public Display.

The Order is closed. No further status changes are possible.

---

### CANCELLED

The Order was cancelled and will not be fulfilled.

Cancelled Orders are removed from the Kitchen Board and the Public Display.

Cancellation is only permitted from the Received status by the Cashier, or from any status by the Administrator.

Cancelled Orders are preserved in the historical record for reporting purposes.

---

## Lifecycle by Role

| Transition | Triggered by |
|---|---|
| Creation (→ Received) | Cashier |
| Received → In Preparation | Kitchen |
| In Preparation → Ready | Kitchen |
| Ready → Delivered | Cashier |
| Received → Cancelled | Cashier or Administrator |
| In Preparation → Cancelled | Administrator only |

---

## Visibility by Status

| Status | Kitchen Board | Public Display |
|---|---|---|
| Received | Visible (pending) | Not visible |
| In Preparation | Visible (active) | Visible (in preparation section) |
| Ready | Visible (completed) | Visible (highlighted, with notification) |
| Delivered | Not visible | Not visible |
| Cancelled | Not visible | Not visible |

---

## Status History

Every status transition is recorded with a timestamp and the User who triggered it.

Status history is immutable and preserved for the lifetime of the Order.

The history is used for:

- Supervisor dashboards (waiting time metrics, kitchen performance)
- Operational audit trail
- Dispute resolution between staff

---

## Timing Expectations

The system must reflect status changes in real time.

From the moment a Kitchen User marks an Order as Ready, the Public Display must update and play the audible notification within a time acceptable for a live food service environment.

Delays caused by polling or network latency are not acceptable for this core flow.
