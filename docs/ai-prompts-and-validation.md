# AI Prompts, Instructions, and Validation Record

AI was used as an accelerator for design alternatives, boilerplate, test cases, and UI iteration. The developer reviewed generated code, ran tests/builds, and corrected issues found during runtime checks.

## Representative instructions used

- “Build employee salary management software for an organization with 10,000 employees using Java Spring Boot and React; work in incremental commits.”
- “Use a relational local database, seed 10,000 deterministic synthetic employees, and keep salary analytics currency-safe.”
- “Implement server-side pagination, employee compensation updates, optimistic concurrency, and immutable audit history.”
- “Make the UI responsive and prevent the salary editor from overflowing.”

## Human/agent validation performed

- `mvn test` for deterministic seeding, pagination constraints, directory mapping, salary-update auditing, stale-version rejection, and median calculation.
- `npm run build` for a production frontend compilation.
- Local API smoke tests against a fresh SQLite database: 10,000 records seeded; directory, analytics, detail, and audited salary update returned expected JSON.

## Guardrails

- No real employee data or credentials are present.
- Money uses decimal values and ISO currency codes.
- Generated build artifacts and local databases are ignored by Git.
- AI output is treated as draft implementation, not as a source of truth.
