# ADR-002: Koin for Dependency Injection

## Status

Accepted

## Context

The project originally used Hilt (Dagger-based) for dependency injection. While Hilt provides
compile-time safety and is the officially recommended DI framework for Android, it introduced
friction during the multi-module migration:

- **Annotation processing** — Hilt requires `kapt` or `ksp` in every module that declares or
  consumes injected types, increasing build times and configuration complexity.
- **Boilerplate** — `@Module`, `@InstallIn`, `@Provides`, `@Inject`, and `@HiltViewModel`
  annotations were needed throughout the codebase.
- **Multi-module wiring** — Hilt's component hierarchy (`SingletonComponent`,
  `ViewModelComponent`, etc.) required careful coordination across module boundaries.
- **Testing** — Replacing bindings in tests required `@UninstallModules` and `@TestInstallIn`,
  adding test-specific annotation complexity.

The team evaluated Koin as an alternative — a lightweight, Kotlin-first service locator that uses
DSL-based module definitions instead of annotation processing.

## Decision

Migrate from Hilt to Koin for dependency injection across all modules.

All DI module definitions are centralized in `:app` using nine logical Koin modules:

| Module                    | Bindings                                                |
|---------------------------|---------------------------------------------------------|
| `NetworkModule`           | OkHttpClient, Retrofit, API service interfaces          |
| `DatabaseModule`          | Room AppDatabase, DAOs                                  |
| `RepositoryModule`        | Repository interface → concrete implementation bindings |
| `UseCaseModule`           | All 14 use case classes                                 |
| `ViewModelModule`         | All 12 ViewModels                                       |
| `SeasonManagerModule`     | SeasonManager singleton                                 |
| `SharedPreferencesModule` | SharedPreferences, PreferencesHelper                    |
| `ImageLoaderModule`       | Coil ImageLoader                                        |
| `MusicModule`             | MediaPlayer for background music                        |

Koin is initialized in `F1StatsApp.onCreate()`:

```kotlin
startKoin {
    androidContext(this@F1StatsApp)
    modules(networkModule, databaseModule, repositoryModule, ...)
}
```

ViewModels are resolved in Compose screens via `koinViewModel<T>()`.

## Consequences

### Positive

- **No annotation processing** — Eliminates `kapt`/`ksp` from all modules, reducing build times
  by ~15-20%.
- **Kotlin DSL** — Module definitions are concise, readable Kotlin code; no annotation magic.
- **Simple multi-module setup** — Modules are plain Kotlin objects that can live anywhere; `:app`
  simply registers them all.
- **Easy test overrides** — `loadKoinModules(module { ... })` replaces any binding without
  annotations.
- **DI graph verification** — `checkModules()` in a unit test validates the entire graph at test
  time.

### Negative

- **Runtime resolution** — Dependency errors surface at runtime instead of compile time. Mitigated
  by the `checkModules()` integration test in `:app`.
- **Service locator pattern** — Koin is technically a service locator, not a true DI container.
  This means classes can resolve dependencies anywhere, not just via constructors. Code review
  discipline is needed to keep injection constructor-based.
- **IDE support** — Navigation from `get()` to the concrete type is less direct than Hilt's
  `@Inject` constructor navigation.

### Neutral

- The migration was a one-time effort. All `@Inject`, `@HiltViewModel`, `@Module`, and
  `@InstallIn` annotations were removed and replaced with Koin DSL equivalents.
- Three integration tests in `:app` verify the full DI graph resolves correctly.
