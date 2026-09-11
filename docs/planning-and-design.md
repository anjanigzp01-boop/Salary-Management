# Planning and Design Notes

## Product flow

1. The HR Manager opens the dashboard to understand headcount and pay grouped by original currency.
2. They narrow the directory by country, department, status, or a name/employee-number search.
3. They open an employee record, review current compensation and prior changes, then submit a validated update.
4. The API records an immutable salary-change entry and returns the updated view.

## Delivery plan followed

| Phase | Outcome | Evidence |
| --- | --- | --- |
| 1 | Product scope and architecture agreed | `docs` and initial Git commits |
| 2 | Persistent model and deterministic 10,000-person dataset | seed factory and tests |
| 3 | Bounded directory API | pagination, search, filters, tests |
| 4 | Compensation workflow, audit, and analytics | service tests and local smoke test |
| 5 | React HR workspace | production Vite build |

## API contract summary

- `GET /api/v1/employees`: paginated directory; `search`, `country`, `department`, `status`, `page`, and `size` are supported.
- `GET /api/v1/employees/{employeeNumber}`: employee, current compensation, and salary-change history.
- `PUT /api/v1/employees/{employeeNumber}/compensation`: validated compensation update. The client supplies an expected version to prevent lost updates.
- `GET /api/v1/analytics`: currency-safe summary and country/department breakdowns; filters match the directory.

## UX choices

- Directory actions are intentionally compact; the sensitive salary editor is isolated in a scrollable, responsive detail panel.
- Filters apply server-side. A page is capped at 100 items, with the UI using 20.
- Analytics display each original currency independently. This is clearer and safer than silently converting money.
