# ADR-009: Coil 3 for Image Loading

## Status

Accepted

## Context

The application displays numerous images: driver portraits, team logos, circuit maps, country
flags, and race thumbnails. An image loading library is essential for:

- **Async loading** — Images must be fetched from URLs without blocking the UI thread.
- **Caching** — Images should be cached in memory and on disk to avoid redundant network
  requests.
- **Compose integration** — The library must work natively with Jetpack Compose without
  interop layers.
- **Error/placeholder handling** — Missing or slow-loading images need graceful fallbacks.

Alternatives considered:

- **Glide** — Mature and battle-tested but View-system-oriented. Compose support exists but
  feels like an afterthought. Java-first API.
- **Picasso** — Lightweight but lacks first-class Compose support and is less actively
  maintained.
- **Coil** — Kotlin-first, built on coroutines, with dedicated Compose artifacts and OkHttp
  integration.

## Decision

Use Coil 3 with the `coil-compose` and `coil-network-okhttp` artifacts for all image loading.

**ImageLoader configuration (via Koin):**

```kotlin
single {
    ImageLoader.Builder(androidContext())
        .components {
            add(OkHttpNetworkFetcherFactory(callFactory = { get<OkHttpClient>() }))
        }
        .build()
}
```

**Compose usage:**

```kotlin
AsyncImage(
    model = imageUrl,
    contentDescription = "Driver portrait",
    imageLoader = imageLoader,
    contentScale = ContentScale.Crop,
    modifier = Modifier.size(64.dp)
)
```

The `ImageLoader` is provided as a Koin singleton and injected into composables via
`koinInject<ImageLoader>()`. It shares the same `OkHttpClient` as Retrofit, reusing the
connection pool and any configured interceptors.

## Consequences

### Positive

- **Compose-native** — `AsyncImage` is a first-class composable that handles loading,
  error, and success states declaratively.
- **OkHttp reuse** — Sharing the OkHttp client between Retrofit and Coil means one connection
  pool, consistent timeout settings, and shared interceptors.
- **Kotlin coroutines** — Coil is built on coroutines internally, aligning with the app's
  async model.
- **Efficient caching** — Two-level cache (memory + disk) minimizes network usage and provides
  instant display for previously loaded images.
- **Lightweight** — Coil's APK footprint is smaller than Glide's, which matters for a
  size-conscious app using R8.

### Negative

- **Smaller ecosystem** — Coil has fewer community transformations and integrations compared
  to Glide's extensive ecosystem.
- **API churn** — Coil 3 introduced breaking changes from Coil 2 (e.g., new network artifact
  structure). Future major versions may require migration effort.

### Neutral

- Coil's `ImageLoader` is a singleton managed by Koin, consistent with how other dependencies
  (OkHttp, Room, Retrofit) are managed.
- The `ImageLoader` is passed to `DialogHelper` for showing full-screen image previews, ensuring
  caching is shared across all image display contexts.
