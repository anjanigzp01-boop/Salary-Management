# AI-Assisted Development Notes

AI is being used as an implementation accelerator and thought partner, not as an authority.

## Working method

1. Define scope and explicit non-goals before producing code.
2. Build in small, reviewable phases with an incremental Git commit after each coherent milestone.
3. Use AI to propose structures, boilerplate, test cases, and edge cases; inspect every generated change against the requirements and domain constraints.
4. Run deterministic automated tests at each phase and fix failures before committing.
5. Record material trade-offs here or in architecture notes so reviewers can understand the decisions.

## Guardrails applied

- No real employee data is used; the seed dataset is synthetic and deterministic.
- Financial amounts use decimal arithmetic and currencies are never silently combined.
- Generated dependencies and API designs are reviewed for maintainability, security, and local reproducibility.
- Commits remain small and narrate the system's evolution.
