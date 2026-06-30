# Domain Documentation

This folder describes the business domain of RoosterViewCall.

It is the authoritative reference for understanding what the system does, what its core concepts are, and what rules govern its behavior — independent of any technical implementation.

Engineers, architects, and AI agents must read this documentation before designing or implementing any feature.

---

## Contents

| File | Description |
|---|---|
| [entities.md](entities.md) | The core business concepts in the domain |
| [relationships.md](relationships.md) | How domain entities relate to each other |
| [business-rules.md](business-rules.md) | The rules and invariants that govern domain behavior |
| [order-lifecycle.md](order-lifecycle.md) | The complete lifecycle of an Order |
| [glossary.md](glossary.md) | Definitions of terms used across the project |

---

## Purpose

RoosterViewCall manages the complete customer service flow of a food business:

1. A customer places an order at the point of sale.
2. A ticket is generated and given to the customer as a reference.
3. The kitchen receives the order and prepares it.
4. The public display notifies the customer when the order is ready.
5. The customer picks up their food.

This domain documentation captures the business concepts, rules, and vocabulary that make that flow possible.

---

## Scope

This documentation covers the business domain only.

It does not describe database schemas, REST APIs, or implementation details.

For architectural and technical documentation, see [docs/architecture/](../architecture/README.md).

For product scope and roadmap, see [docs/product/](../product/00-product-vision.md).
