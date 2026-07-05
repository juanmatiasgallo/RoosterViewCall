# Development Workflow

## Overview

Every change in a RoosterCode project follows the same lifecycle, from the roadmap down to a merged pull request. This ensures traceability between planning, code, and documentation.

## Lifecycle

1. **Roadmap** — the feature or fix originates from the product roadmap.
2. **GitHub Issue** — an issue is created describing the work and its acceptance criteria.
3. **docs/issues/** — a specification document is added, linking back to the issue.
4. **Feature branch** — a branch is created from `main` following [BRANCHING.md](BRANCHING.md).
5. **Specification** — requirements and design are clarified before coding starts.
6. **Implementation** — the code is written according to the specification.
7. **Review** — the implementation is reviewed for correctness and standards compliance.
8. **Compile** — the project builds and existing tests pass.
9. **Commit** — changes are committed using the project's commit convention.
10. **Push** — the branch is pushed to the remote repository.
11. **Pull Request** — a PR is opened against `main`, describing the change and linking the issue.
12. **Merge** — the PR is approved and merged.
13. **Delete branch** — the feature branch is deleted after merge.
14. **Return to main** — development continues from an up-to-date `main`.

## Diagram

```
Roadmap
   |
GitHub Issue
   |
docs/issues/
   |
Feature Branch
   |
Specification
   |
Implementation
   |
Review
   |
Compile
   |
Commit
   |
Push
   |
Pull Request
   |
Merge
   |
Delete Branch
   |
Return to main
```

## Principles

- No implementation starts without an issue and a specification.
- No branch is merged without review.
- No branch outlives its pull request.
