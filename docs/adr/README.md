# Architecture Decision Records

This directory contains Architecture Decision Records (ADRs) for the F1Stats project.

ADRs document significant architectural decisions made during the development of the project,
including the context, the decision itself, and the consequences. They serve as a living record
of the project's technical evolution.

## Format

Each ADR follows
the [Michael Nygard template](https://cognitect.com/blog/2011/11/15/documenting-architecture-decisions):

- **Title** — A short noun phrase describing the decision
- **Status** — `Accepted`, `Superseded`, or `Deprecated`
- **Context** — The forces at play, including technical, political, and project-specific constraints
- **Decision** — The change being proposed or adopted
- **Consequences** — The resulting context after applying the decision

## Index

| ADR                                                 | Title                                 | Status   |
|-----------------------------------------------------|---------------------------------------|----------|
| [ADR-001](001-multi-module-clean-architecture.md)   | Multi-Module Clean Architecture       | Accepted |
| [ADR-002](002-koin-dependency-injection.md)         | Koin for Dependency Injection         | Accepted |
| [ADR-003](003-jetpack-compose-ui.md)                | Jetpack Compose for UI                | Accepted |
| [ADR-004](004-compose-navigation.md)                | Compose Navigation                    | Accepted |
| [ADR-005](005-kotlin-stateflow-state-management.md) | Kotlin StateFlow for State Management | Accepted |
| [ADR-006](006-sealed-result-error-handling.md)      | Sealed Result Type for Error Handling | Accepted |
| [ADR-007](007-room-local-persistence.md)            | Room for Local Persistence            | Accepted |
| [ADR-008](008-retrofit-okhttp-networking.md)        | Retrofit + OkHttp for Networking      | Accepted |
| [ADR-009](009-coil-image-loading.md)                | Coil 3 for Image Loading              | Accepted |
| [ADR-010](010-dto-domain-mapper-pattern.md)         | DTO-to-Domain Mapper Pattern          | Accepted |

## Creating a New ADR

1. Copy the template below into a new file named `NNN-short-title.md`
2. Fill in each section
3. Add the entry to the index table above

### Template

```markdown
# ADR-NNN: Title

## Status

Accepted | Superseded by [ADR-NNN](NNN-title.md) | Deprecated

## Context

[Describe the forces at play]

## Decision

[Describe the decision]

## Consequences

### Positive
- ...

### Negative
- ...

### Neutral
- ...
```
