# ADR-006: Sealed Result Type for Error Handling

## Status

Accepted

## Context

Error handling across the application was inconsistent. Some repositories threw exceptions, others
returned nullable types, and ViewModels used `try/catch` blocks with varying granularity. This
created several problems:

- **Silent failures** — Nullable return types (`List<Race>?`) provided no information about what
  went wrong.
- **Uncaught exceptions** — Forgetting a `try/catch` in a coroutine could crash the app or
  silently cancel the coroutine scope.
- **Inconsistent UX** — Some screens showed error messages, others showed empty states, and
  others did nothing — all depending on how the individual ViewModel handled failures.
- **Testability** — Testing error paths required mocking exceptions, which is fragile and
  doesn't compose well with coroutine test utilities.

A sealed type that explicitly models success and failure makes error handling a first-class
concern that the compiler can enforce.

## Decision

Define a sealed `Result<T>` class in `:domain` as the return type for all use cases and
repositories:

```kotlin
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}
```

**Rules:**

1. All repository interface methods return `Result<T>`.
2. All use case `invoke()` operators return `Result<T>`.
3. Concrete repositories catch exceptions at the boundary and wrap them in `Result.Error`.
4. ViewModels use exhaustive `when` expressions to handle both cases.

**Repository usage:**

```kotlin
override suspend fun getRaces(season: String): Result<List<Race>> {
    return when (val response = raceService.getRaces(season)) {
        is Result.Success -> Result.Success(raceMapper.fromMap(response.data) ?: emptyList())
        is Result.Error -> response
    }
}
```

**ViewModel usage:**

```kotlin
when (val result = getRacesUseCase(season)) {
    is Result.Success -> _races.value = result.data
    is Result.Error -> _errorMessage.value = result.exception.localizedMessage
}
```

## Consequences

### Positive

- **Compiler-enforced handling** — `when` expressions on sealed classes require exhaustive
  branches. Forgetting to handle `Error` is a compile-time error.
- **Explicit failure paths** — Every method signature communicates that it can fail, making error
  handling visible and intentional.
- **Consistent UX** — All ViewModels follow the same pattern: set loading state, call use case,
  handle success or error. Users always see feedback on failure.
- **Testable** — Tests assert on `Result.Success` or `Result.Error` values without mocking
  exception throwing.
- **Domain purity** — `Result` lives in `:domain` with zero dependencies, keeping the domain
  layer clean.

### Negative

- **Wrapping overhead** — Every return site must wrap values in `Result.Success(...)`. This is
  minor syntactic overhead.
- **Exception information loss** — Wrapping all exceptions as `Result.Error(exception)` flattens
  the error type. If more granular error handling is needed in the future (e.g., distinguishing
  network errors from parse errors), the sealed class would need additional variants.

### Neutral

- The custom `Result` class intentionally does not extend `kotlin.Result` to avoid confusion
  with the standard library's inline class, which has different semantics and limitations.
- Services wrap HTTP error responses and network exceptions into `Result.Error` at the lowest
  layer, so repositories and use cases never see raw exceptions.
