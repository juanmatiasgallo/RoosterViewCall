# RoosterViewCall - Technology Stack

## Purpose

This document defines the approved technology stack for RoosterViewCall.

The objective is to maintain consistency across the project and prevent unnecessary technologies from being introduced.

Every new dependency should have a clear purpose.

---

# Backend

## Language

- Java 25 LTS

Reason:

- Modern language features
- Long-term support
- Excellent Spring Boot compatibility
- Strong enterprise ecosystem

---

## Framework

Spring Boot

Reason:

- Enterprise-ready
- Large community
- Excellent documentation
- Easy Docker deployment
- Strong security ecosystem

---

## Build Tool

Maven

Reason:

- Standard Spring ecosystem
- Easy dependency management
- Predictable builds

---

## Security

Spring Security

JWT Authentication

Reason:

- Stateless authentication
- Easy API integration
- Secure by default

---

## Database

PostgreSQL

Reason:

- Open Source
- Reliable
- Powerful indexing
- JSON support
- Enterprise ready

---

## Database Migration

Flyway

Reason:

- Versioned database
- Safe deployments
- Reproducible environments

---

## ORM

Spring Data JPA

Hibernate

Reason:

- Rapid development
- Mature ecosystem
- Easy repository pattern

---

# Frontend

## Framework

React

Reason:

- Huge ecosystem
- Component-based architecture
- Excellent TypeScript support

---

## Language

TypeScript

Reason:

- Type safety
- Better maintainability
- Better AI code generation
- Reduced runtime errors

---

## Build Tool

Vite

Reason:

- Extremely fast
- Modern tooling
- Excellent developer experience

---

## Routing

React Router

---

## State Management

TanStack Query

Reason:

Server state should not be stored manually.

Use React Query for:

- API requests
- Cache
- Background synchronization
- Optimistic updates

---

Local state should use:

- React Hooks

Global state should only be introduced when truly necessary.

---

## Forms

React Hook Form

Zod

Reason:

- High performance
- Easy validation
- Strong TypeScript integration

---

# UI

## CSS Framework

Tailwind CSS

Reason:

- Utility-first
- Fast development
- Excellent component libraries

---

## Component Library

Primary:

shadcn/ui

Reason:

- Accessible
- Modern
- Customizable
- No runtime dependency

---

Secondary:

Magic UI

Use only for:

- Landing pages
- Hero sections
- Marketing

---

Secondary:

Aceternity UI

Use only for:

- Advanced animations
- Premium effects

---

Secondary:

Origin UI

Use only when:

- There is no equivalent component
- It follows project design principles

---

## Icons

Lucide React

Reason:

- Native shadcn integration
- Lightweight
- Consistent design

---

## Animations

Framer Motion

Reason:

- Modern animations
- Smooth transitions
- High performance

Animations must improve UX.

Never animate for decoration only.

---

# Testing

Unit Tests

Vitest

---

E2E

Playwright

Reason:

- Reliable browser automation
- Cross-browser support
- Modern testing approach

---

Component Testing

Storybook

Reason:

- Component documentation
- Visual testing
- Design validation

---

# Design

Figma

Reason:

Single source of truth for UI.

---

Figma MCP

Purpose:

Allow AI agents to understand and implement Figma designs accurately.

---

# AI Development

Primary Development Agent

Claude Code

Responsibilities:

- Feature implementation
- Refactoring
- Code generation

---

Architecture

ChatGPT

Responsibilities:

- Product decisions
- Architecture
- Documentation
- AI workflow
- Roadmap

---

Code Review

Codex

Responsibilities:

- Review implementations
- Improve code quality
- Suggest optimizations
- Generate tests

---

Knowledge Base

Obsidian

Purpose:

Project documentation.

---

Long-Term Memory

LLM Wiki

Purpose:

Persistent technical knowledge.

---

# Infrastructure

Docker

Docker Compose

GitHub

GitHub Actions

VPS Deployment

---

# Development Principles

Always prefer:

- Open Source
- Long-term support
- Community adoption
- Excellent documentation
- Low maintenance

Avoid:

- Experimental frameworks
- Abandoned projects
- Duplicate libraries
- Multiple solutions for the same problem

---

# Dependency Policy

Before introducing a new library ask:

1. Does the project already solve this problem?

2. Can existing libraries do it?

3. Is it actively maintained?

4. Is it compatible with our architecture?

5. Does it increase complexity?

If the answer is uncertain,

do not introduce it.

---

# Approved Stack Summary

Backend

- Java 25
- Spring Boot
- Spring Security
- Spring Data JPA
- Flyway
- PostgreSQL
- Maven

Frontend

- React
- TypeScript
- Vite
- Tailwind CSS
- shadcn/ui
- TanStack Query
- React Hook Form
- Zod
- Framer Motion
- Lucide

Testing

- Vitest
- Playwright
- Storybook

Infrastructure

- Docker
- Docker Compose
- GitHub Actions

AI

- Claude Code
- ChatGPT
- Codex
- Obsidian
- LLM Wiki

Design

- Figma
- Figma MCP