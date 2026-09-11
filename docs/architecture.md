# Architecture and Engineering Decisions

## System shape

```text
React + TypeScript (Vite)
        |
        | JSON / HTTP
        v
Spring Boot REST API (/api/v1)
        |
        +-- Employee directory & compensation service
        +-- Analytics query service
        +-- Salary change audit service
        |
        v
SQLite (local development) -> PostgreSQL-compatible JPA design (production)
```

## Key decisions

| Decision | Why |
| --- | --- |
| Spring Boot modular package structure | Clear separation of employee, analytics, and shared API concerns without premature microservices. |
| SQLite locally | Zero setup for assessment reviewers and deterministic seed data. Repository queries and migrations remain portable to PostgreSQL. |
| Server-side pagination/filtering | Keeps directory requests bounded for 10,000 employees and scales beyond the seed set. |
| Database aggregation for analytics | Avoids transferring sensitive individual salary records solely to calculate summaries. |
| `BigDecimal` plus ISO currency code | Prevents floating-point money errors and avoids invalid cross-currency totals. |
| Immutable salary-change audit rows | Creates an accountability trail for a sensitive HR operation. |
| REST DTOs separate from entities | Keeps persistence details out of the API and makes validation/contracts explicit. |

## Domain model (planned)

- `Employee`: immutable staff identity and organizational fields.
- `Compensation`: current annual base salary, bonus target, currency, and effective date; one current record per employee.
- `SalaryChange`: append-only audit history containing before/after salary values, effective date, actor, and timestamp.

## Performance considerations

- Index employee lookup/filter fields: employee number, normalized name, department, country, status.
- Use page sizes capped by the API and response DTO projections for the directory.
- Group analytics within SQL and query only needed aggregates.
- Seed data is deterministic so tests and demos produce stable results.

## Delivery phases

1. Foundation: repository layout, requirements, architecture, and decisions.
2. Backend: schema, deterministic seeding, employee directory and compensation APIs, tests.
3. Insights: analytics and audit APIs with aggregation tests.
4. Frontend: directory, profile/editor, dashboard, UI tests.
5. Hardening: integration tests, accessibility pass, container/deployment guidance, and demo recording instructions.
