# ADR-003: Jetpack Compose for UI

## Status

Accepted

## Context

The application was originally built with the traditional Android View system — XML layouts,
Fragments, RecyclerView adapters, ViewBinding, and the Navigation Component with Fragment
destinations. While functional, this approach had several drawbacks:

- **Boilerplate** — Each screen required an XML layout, a Fragment class, a RecyclerView adapter
  (for lists), ViewBinding setup, and manual lifecycle management.
- **Imperative UI updates** — Synchronizing UI state with data required explicit calls to update
  individual views, leading to subtle bugs when state fell out of sync.
- **Adapter complexity** — `RecyclerView.Adapter` with `DiffUtil` added significant code for every
  list screen.
- **Theme consistency** — Applying consistent styling required careful XML theme/style inheritance
  and was error-prone across screens.
- **Testing** — UI tests required Espresso with its complex matching API; snapshot/preview testing
  was limited.

Jetpack Compose offers a declarative, Kotlin-first alternative where UI is described as functions
of state, eliminating the impedance mismatch between Kotlin business logic and XML layouts.

## Decision

Migrate the entire UI layer to Jetpack Compose with Material 3.

Key aspects of the migration:

- **100% Compose** — All screens are `@Composable` functions. No XML layouts remain in the project.
- **Material 3** — The app uses `MaterialTheme` with custom color schemes (`lightColorScheme` /
  `darkColorScheme`) and a custom `F1Typography` using the official F1 font family.
- **LazyColumn/LazyRow** — Replace all `RecyclerView` usage with Compose lazy lists, eliminating
  adapter boilerplate.
- **State hoisting** — Screens receive state and callbacks as parameters, keeping composables
  stateless and testable.
- **Dark mode** — Full dark theme support via `isSystemInDarkTheme()` with user override in
  Settings.

Custom theme definition:

```kotlin
@Composable
fun F1StatsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = F1Typography,
        content = content
    )
}
```

## Consequences

### Positive

- **Less code** — Screens are 40-60% shorter compared to Fragment + XML + Adapter equivalents.
- **Declarative state** — UI automatically recomposes when `StateFlow` values change, eliminating
  manual view updates and state sync bugs.
- **Composable previews** — `@Preview` functions enable rapid UI iteration without running the app.
- **Unified language** — Both UI and logic are Kotlin, removing the context-switch between XML and
  Kotlin.
- **Built-in accessibility** — Material 3 components provide semantic roles, content descriptions,
  and minimum touch targets by default.

### Negative

- **Migration effort** — Converting 12+ screens from XML/Fragment to Compose was a significant
  one-time investment.
- **Learning curve** — Compose's mental model (recomposition, remember, side effects) differs from
  the imperative View system.
- **Binary size** — Compose runtime adds ~2-3 MB to the APK. Mitigated by R8 shrinking in release
  builds.

### Neutral

- The migration was done incrementally using `ComposeView` wrappers in Fragments during the
  transition, then fully removing Fragments once all screens were converted.
- All ViewModels were unchanged during the migration — only the UI layer was replaced.
