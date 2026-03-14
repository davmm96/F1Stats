# ADR-005: Kotlin StateFlow for State Management

## Status

Accepted

## Context

The project originally used `LiveData` for observable state in ViewModels. While LiveData is
lifecycle-aware and works well with the View system, it presented issues after the migration to
Compose and Coroutines:

- **Platform dependency** — `LiveData` is an Android framework class, making `:domain` and `:data`
  modules depend on Android lifecycle libraries for no reason.
- **Coroutine mismatch** — Converting between `LiveData` and coroutine `Flow` required
  `asLiveData()` / `asFlow()` bridge extensions, adding noise.
- **Compose integration** — Compose's `observeAsState()` for LiveData is a compatibility bridge;
  `collectAsStateWithLifecycle()` for Flow is the idiomatic approach.
- **Testing** — LiveData required `InstantTaskExecutorRule` and Android-specific test
  infrastructure; `StateFlow` works with pure coroutine test utilities.
- **Backpressure** — LiveData has no backpressure handling, while Flow provides operators like
  `conflate()`, `buffer()`, and `debounce()`.

## Decision

Replace all `LiveData` with Kotlin `StateFlow` in ViewModels, and use `Flow` for reactive data
streams from Room.

**ViewModel pattern:**

```kotlin
class RacesViewModel(private val getRacesUseCase: GetRacesUseCase) : ViewModel() {

    private val _races = MutableStateFlow<List<Race>>(emptyList())
    val races: StateFlow<List<Race>> = _races.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun fetchRaces(season: String) {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = getRacesUseCase(season)) {
                is Result.Success -> _races.value = result.data
                is Result.Error -> _errorMessage.value = result.exception.message
            }
            _isLoading.value = false
        }
    }
}
```

**UI consumption:**

```kotlin
val races by viewModel.races.collectAsStateWithLifecycle()
val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
```

**Room DAOs** return `Flow<List<T>>` directly, which ViewModels collect and expose as `StateFlow`.

## Consequences

### Positive

- **Pure Kotlin** — `StateFlow` and `Flow` are part of `kotlinx.coroutines`, with no Android
  dependency. ViewModels can be tested with `runTest` and `Turbine` on the JVM.
- **Compose-native** — `collectAsStateWithLifecycle()` is the recommended approach for Compose,
  providing lifecycle-aware collection without bridges.
- **Consistent async model** — The entire stack uses coroutines: `suspend` functions in
  repositories, `Flow` from Room, `StateFlow` in ViewModels. No mixing of reactive paradigms.
- **Backpressure support** — Flow operators handle rapid emissions gracefully, which is important
  for Room's reactive DAO queries.
- **Testability** — Turbine's `test {}` block provides clean, sequential assertions on Flow
  emissions without Android test rules.

### Negative

- **No automatic lifecycle handling** — Unlike LiveData, raw `StateFlow.collect()` does not
  stop on lifecycle events. Mitigated by always using `collectAsStateWithLifecycle()` in Compose.
- **Mutable state exposure risk** — `MutableStateFlow` could be leaked if not wrapped with
  `asStateFlow()`. Enforced by code convention: private `_state`, public `state`.

### Neutral

- All 12 ViewModels were migrated from `MutableLiveData` to `MutableStateFlow`.
- `InstantTaskExecutorRule` was removed from all ViewModel tests.
- `Turbine` was added as a test dependency for Flow assertion.
