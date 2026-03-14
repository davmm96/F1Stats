# ADR-004: Compose Navigation

## Status

Accepted

## Context

The project originally used the Jetpack Navigation Component with Fragment destinations and a
`nav_graph.xml` file defining the navigation graph. After migrating the UI to Jetpack Compose
(see [ADR-003](003-jetpack-compose-ui.md)), Fragment-based navigation became an impedance mismatch:

- **Fragment shells** — Each screen still required a Fragment class solely to host a `ComposeView`,
  adding boilerplate with no value.
- **SafeArgs code generation** — The SafeArgs Gradle plugin generated Directions and Args classes
  from XML, requiring annotation processing.
- **Two navigation systems** — Mixing Fragment navigation with Compose UI created confusion about
  where navigation logic belonged.
- **Deep linking** — Fragment-based deep links required `<deepLink>` elements in XML, which were
  harder to maintain alongside Compose routes.

Compose Navigation provides a purely Kotlin-based navigation system that eliminates Fragments
entirely and integrates natively with the Compose lifecycle.

## Decision

Replace Fragment-based Jetpack Navigation with Compose Navigation.

The navigation graph is defined in code using `NavHost` and `composable()` route declarations:

```kotlin
NavHost(navController = navController, startDestination = Routes.RACES) {
    composable(Routes.RACES) { RacesScreen(...) }
    composable(
        route = Routes.RACE_DETAIL,
        arguments = listOf(
            navArgument("id") { type = NavType.IntType },
            navArgument("country") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        RaceDetailScreen(
            id = backStackEntry.arguments!!.getInt("id"),
            country = backStackEntry.arguments!!.getString("country")!!
        )
    }
    // ...
}
```

Route constants are centralized in a `Routes` object. Bottom navigation tabs use `saveState` and
`restoreState` to preserve tab state across switches.

## Consequences

### Positive

- **No more Fragment shells** — Screens are direct `@Composable` functions, eliminating ~12
  Fragment classes that existed only to host `ComposeView`.
- **Type-safe arguments** — `navArgument` with explicit `NavType` catches type mismatches at
  definition time.
- **Single navigation system** — All navigation is Kotlin code, colocated with the UI it controls.
- **Tab state preservation** — `saveState`/`restoreState` on bottom nav items preserves scroll
  position and back stack per tab.
- **Simpler deep linking** — Routes are plain strings; deep link URIs can be constructed
  programmatically.

### Negative

- **String-based routes** — Routes are strings, which are less type-safe than SafeArgs-generated
  classes. Mitigated by centralizing route patterns in a `Routes` object.
- **Manual argument extraction** — Arguments must be manually extracted from `backStackEntry`,
  whereas SafeArgs generated typed accessor classes.
- **No visual graph editor** — The XML navigation graph had a visual editor in Android Studio;
  Compose Navigation is code-only.

### Neutral

- The `nav_graph.xml` file and all SafeArgs-generated code were removed entirely.
- All navigation actions are triggered via `navController.navigate(route)` from composable
  callbacks.
