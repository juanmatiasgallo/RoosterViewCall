# Branching Convention

## Base Branch

`main` is the only long-lived branch. All feature branches are created from `main` and merged back into `main` through a pull request.

## Branch Naming

```
<type>/<TICKET-ID>-<short-description>
```

- **type**: the kind of change (see below)
- **TICKET-ID**: the issue/ticket identifier (e.g. `RV-006`); the prefix is project-specific and set once per repository
- **short-description**: lowercase, hyphen-separated summary of the work

### Types

| Type       | Use for                                  |
|------------|-------------------------------------------|
| `feature`  | new functionality                         |
| `fix`      | bug fixes                                 |
| `hotfix`   | urgent production fixes                   |
| `chore`    | tooling, dependencies, non-functional work |
| `docs`     | documentation-only changes                |

### Examples

```
feature/RV-006-authentication
fix/RV-012-ticket-numbering
hotfix/RV-020-payment-crash
chore/RV-004-dependency-update
docs/RV-030-api-reference
```

## Rules

- One branch per issue.
- Branch names use lowercase and hyphens only.
- Branches are deleted after merge (see [WORKFLOW.md](WORKFLOW.md)).
- Never commit directly to `main`.
