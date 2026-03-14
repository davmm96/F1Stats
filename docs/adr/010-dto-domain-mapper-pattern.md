# ADR-010: DTO-to-Domain Mapper Pattern

## Status

Accepted

## Context

The API-Sports F1 API returns deeply nested JSON responses with nullable fields, abbreviated
values (e.g., team names, country codes), and structures optimized for the API's schema rather
than the app's needs. Directly using these network DTOs in the UI and domain layers would create
several problems:

- **Coupling** — API contract changes would ripple through ViewModels and Compose screens.
- **Null unsafety** — DTO fields are nullable to match the JSON schema, but the domain layer
  expects non-null values with sensible defaults.
- **Data transformation** — Some fields need post-processing: sorting, abbreviation resolution,
  points calculation, date formatting. Performing these in ViewModels would violate separation of
  concerns.
- **Testability** — Testing domain logic against raw DTOs requires constructing deeply nested
  JSON-like objects, making tests verbose and brittle.

## Decision

Introduce dedicated mapper classes in `:data` that transform network DTOs into clean domain
models defined in `:domain`.

**Mapper interface:**

```kotlin
interface IMapper<in From, out To> {
    fun fromMap(from: From): To
}
```

**Concrete mapper example:**

```kotlin
class CircuitMapper : IMapper<List<CircuitData>?, List<Circuit>?> {
    override fun fromMap(from: List<CircuitData>?): List<Circuit>? {
        return from?.map { it.toCircuit() }?.sortedBy { it.name }
    }

    private fun CircuitData.toCircuit() = Circuit(
        id = id ?: 0,
        name = name ?: "Circuit name not found",
        country = competition?.location?.country ?: "Country not found",
        city = competition?.location?.city ?: "City not found",
        image = image ?: Constants.IMAGE_NOT_FOUND,
        // ...
    )
}
```

**Rules:**

1. Each API response type has a corresponding mapper class.
2. Mappers live in `:data/mapper/` and are injected into repositories via Koin.
3. Mappers handle null coalescing, default values, sorting, and abbreviation resolution.
4. Domain models in `:domain` use non-null types with sensible defaults.
5. Mappers are stateless and have no side effects.

**Mappers in the project:**

| Mapper                | Transforms                            | Notable Logic                          |
|-----------------------|---------------------------------------|----------------------------------------|
| `RaceMapper`          | `RaceData` → `Race`                   | Date formatting, timezone conversion   |
| `CircuitMapper`       | `CircuitData` → `Circuit`             | Alphabetical sorting, null coalescing  |
| `RankingDriverMapper` | `RankingDriverData` → `RankingDriver` | Points parsing, position sorting       |
| `RankingTeamMapper`   | `RankingTeamData` → `RankingTeam`     | Points calculation, team ID extraction |
| `RankingRaceMapper`   | `RankingRaceData` → `RankingRace`     | Status abbreviation resolution         |
| `DriverMapper`        | `DriverData` → `Driver`               | Nationality, image URL handling        |
| `TeamMapper`          | `TeamData` → `Team`                   | Logo URL, team ID mapping              |
| `SeasonMapper`        | `SeasonData` → `Season`               | Year extraction, sorting               |
| `RaceResultMapper`    | `RaceResultData` → `RaceResult`       | Lap times, fastest lap flags           |

## Consequences

### Positive

- **API isolation** — Domain models are completely decoupled from the API schema. API changes
  only affect the mapper and DTO classes in `:data`.
- **Null safety** — Mappers coalesce nulls into defaults at the boundary, so all downstream code
  works with non-null values.
- **Testable transformations** — Mappers are pure functions that are trivial to unit test with
  known inputs and expected outputs. 62 tests in `:data` cover mapper edge cases.
- **Single Responsibility** — Data transformation logic lives in one place (mappers), not
  scattered across repositories and ViewModels.
- **Sorting and enrichment** — Mappers apply domain-relevant transformations (sorting circuits
  alphabetically, resolving abbreviations) before the data reaches the domain layer.

### Negative

- **Boilerplate** — Each API response requires a mapper class, DTO classes, and domain model
  classes. For simple APIs this can feel like over-engineering.
- **Maintenance surface** — API contract changes require updating both the DTO and the mapper.
  However, this is preferable to updating UI code.

### Neutral

- Mappers implement the `IMapper<From, To>` interface, providing a consistent API across all
  transformers.
- Mappers are injected via Koin constructor injection into repositories, making them easily
  replaceable in tests.
- The `FavoriteRaceRepository` uses a simpler `toDomain()` extension function instead of a
  full mapper class, since Room entity → domain model mapping is straightforward.
