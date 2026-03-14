# ADR-008: Retrofit + OkHttp for Networking

## Status

Accepted

## Context

F1Stats consumes the [API-Sports Formula 1 API](https://api-sports.io/) for all remote data:
race schedules, driver/team standings, circuit information, race results, and season lists. The
networking solution needed to support:

- **Type-safe endpoint definitions** — 7+ endpoints with varying query parameters and response
  shapes.
- **Authentication** — Every request must include an `x-apisports-key` header.
- **JSON deserialization** — API responses are nested JSON objects with nullable fields.
- **Coroutine integration** — Network calls should be `suspend` functions for seamless coroutine
  usage.
- **Logging** — Debug builds should log HTTP request/response details for troubleshooting.
- **Error handling** — HTTP errors and network failures should be caught and wrapped in the
  `Result` type (see [ADR-006](006-sealed-result-error-handling.md)).

## Decision

Use Retrofit as the HTTP client abstraction with OkHttp as the underlying transport layer and
Gson for JSON deserialization.

**API service interface:**

```kotlin
interface APIClient {
    @GET("races")
    suspend fun getRaces(
        @Query("season") season: String,
        @Query("type") type: String
    ): Response<RaceResponse>

    @GET("rankings/drivers")
    suspend fun getRankingDrivers(
        @Query("season") season: String
    ): Response<RankingDriverResponse>

    // ... 7 endpoints total
}
```

**OkHttp configuration (NetworkModule):**

```kotlin
val headerInterceptor = Interceptor { chain ->
    val request = chain.request().newBuilder()
        .addHeader("x-apisports-key", BuildConfig.API_KEY)
        .build()
    chain.proceed(request)
}

val client = OkHttpClient.Builder()
    .addInterceptor(headerInterceptor)
    .addInterceptor(loggingInterceptor)
    .build()
```

**API key management:**

The API key is injected at build time via `BuildConfig` fields in `app/build.gradle`. It reads
from environment variables (for CI) or `local.properties` (for local development), ensuring the
key is never hardcoded in source control.

## Consequences

### Positive

- **Type-safe endpoints** — Retrofit interfaces define endpoints as Kotlin functions with
  annotated parameters. Compile-time validation of query names and return types.
- **Suspend functions** — Retrofit 2.6+ natively supports `suspend`, eliminating callback
  adapters and integrating cleanly with the coroutine-based architecture.
- **Interceptor pattern** — OkHttp interceptors handle cross-cutting concerns (auth headers,
  logging) without polluting individual endpoint definitions.
- **Shared OkHttp instance** — A single `OkHttpClient` is shared between Retrofit and Coil
  (image loading), reusing connection pools and interceptors.
- **Logging** — `HttpLoggingInterceptor` provides request/response logging in debug builds only.

### Negative

- **Gson limitations** — Gson lacks Kotlin-aware null safety; nullable fields in DTOs must be
  explicitly annotated or handled by mappers. Alternatives like Moshi or kotlinx.serialization
  offer better Kotlin support but would require migration.
- **Response wrapper** — Retrofit's `Response<T>` wrapper adds a layer of unwrapping in service
  classes. Each service must check `response.isSuccessful` and extract the body.

### Neutral

- All 7 API endpoints follow the same pattern: `@GET` with `@Query` parameters returning
  `Response<T>`.
- Service classes (e.g., `RaceService`, `CircuitService`) wrap Retrofit calls and convert
  responses to `Result<T>`, centralizing error handling at the service layer.
- The base URL (`https://v1.formula-1.api-sports.io/`) is configured as a `BuildConfig` field
  alongside the API key.
