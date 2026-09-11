# Trade-offs and Deliberate Non-Goals

| Choice | Benefit | Trade-off / follow-up |
| --- | --- | --- |
| SQLite for the assessment | Zero infrastructure and easy local review | Use PostgreSQL plus managed backups and migrations in production. |
| Synthetic deterministic seed data | Safe to share and stable in tests/demos | It is not statistically representative of ACME's real pay distribution. |
| Current compensation plus append-only audit entries | Simple reads and an accountability trail | A full compensation-event model would support future-dated changes and approval workflows better. |
| Development actor header | Makes audit behavior demonstrable locally | Replace with SSO/RBAC identity claims before production. |
| Currency-separated reporting | Prevents invalid cross-currency totals | Add a governed FX source and date-specific conversion policy if consolidated reporting is required. |
| Server-side aggregates plus in-memory median per currency | Keeps large totals in SQL while retaining portable median logic | PostgreSQL percentile queries or materialized aggregates should be considered at much larger scale. |

Intentionally excluded: payroll/tax calculation, benefit administration, spreadsheet bulk import/export, approval workflow, employee self-service, and market-benchmark integration. Each needs country-specific legal, security, or business-policy decisions outside this MVP.
