# ADR-001: Multi-Module Clean Architecture

## Status

Accepted

## Context

F1Stats started as a single-module Android application with all layers colocated under
`app/src/main/`.
As the codebase grew — adding screens for races, standings, circuits, favourites, settings, and
detail
views — several problems emerged:

- **Build times** increased because any change recompiled the entire codebase.
- **Layer violations** were easy to introduce (e.g., a use case importing a Retrofit DTO).
- **Testability** suffered because domain logic carried transitive Android framework dependencies.
- **Team scalability** was limited; parallel work on different layers caused frequent merge
  conflicts.

Clean Architecture prescribes separating concerns into concentric layers with an inward-pointing
dependency rule. Gradle modules enforce this rule at compile time — a module simply cannot import
symbols from a module it does not depend on.

## Decision

Split the project into four Gradle modules with strict, unidirectional dependencies:

```
:app          Thin application shell — MainActivity, Koin DI wiring
  └─ :ui      Compose screens, ViewModels, theme, navigation graph, resources
       └─ :data   Repositories, Retrofit services, Room database, DTO mappers
            └─ :domain   Pure Kotlin library — domain models, repository interfaces, use cases
```

**Dependency flow:** `:app` → `:ui` → `:data` → `:domain`

| Module    | Type            | Responsibility                                                                  |
|-----------|-----------------|---------------------------------------------------------------------------------|
| `:domain` | Pure Kotlin lib | Domain models, repository interfaces, use cases. Zero Android dependencies.     |
| `:data`   | Android lib     | Concrete repository implementations, Retrofit services, Room DB, DTOs, mappers. |
| `:ui`     | Android lib     | Compose screens, ViewModels, theme, navigation, drawable/string resources.      |
| `:app`    | Application     | `F1StatsApp`, `MainActivity`, all Koin module definitions.                      |

Repository interfaces live in `:domain`; concrete implementations live in `:data`. Koin binds them
in `:app`, satisfying the Dependency Inversion Principle.

## Consequences

### Positive

- **Compile-time layer enforcement** — Gradle prevents `:domain` from seeing Retrofit, Room, or
  Android SDK types, guaranteeing a clean domain layer.
- **Incremental builds** — Changes to a Compose screen only recompile `:ui` and `:app`, not `:data`
  or `:domain`.
- **Testability** — `:domain` tests run on the JVM in milliseconds with no Android mocks. Each
  module can be tested in isolation.
- **Clear ownership** — Contributors know exactly where new code belongs based on its
  responsibility.

### Negative

- **Initial migration effort** — Extracting modules from a monolith required moving files, updating
  imports, and resolving circular dependencies.
- **Gradle configuration overhead** — Four `build.gradle` files to maintain, with careful use of
  `api` vs `implementation` for transitive exposure.
- **Navigation between modules** — IDE "Find Usages" and refactoring tools require indexing all
  modules, which can be slower.

### Neutral

- The `:app` module acts purely as a composition root. It contains no business logic, only DI wiring
  and the application entry point.
- Consumer ProGuard rules in `:data` ensure R8 preserves Gson models and Retrofit interfaces across
  module boundaries.
