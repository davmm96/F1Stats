# ADR-007: Room for Local Persistence

## Status

Accepted

## Context

The application needs to persist user-selected favourite races locally so they survive app
restarts and are available offline. The requirements were:

- **Reactive updates** — The favourites list should update in real time when a race is added or
  removed, without manual refresh.
- **Structured data** — Favourite races have multiple fields (ID, name, country, season), making
  key-value stores like SharedPreferences insufficient.
- **Offline support** — Favourites must be accessible without network connectivity.
- **Type safety** — Schema changes should be caught at compile time.

Alternatives considered:

- **SharedPreferences** — Too limited for structured data; no query capabilities, no reactive
  observation.
- **DataStore** — Better than SharedPreferences but optimized for key-value pairs, not relational
  data.
- **Raw SQLite** — Full flexibility but requires manual cursor handling, schema management, and
  has no compile-time query validation.

## Decision

Use Room as the persistence layer for local data, specifically for the favourite races feature.

**Entity:**

```kotlin
@Entity(tableName = "favorite_races")
data class FavoriteRace(
    @PrimaryKey val id: Int,
    val raceName: String,
    val country: String,
    val season: String
)
```

**DAO with reactive Flow:**

```kotlin
@Dao
interface RaceDao {
    @Query("SELECT * FROM favorite_races")
    fun getAllFavoriteRaces(): Flow<List<FavoriteRace>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRace(race: FavoriteRace)

    @Query("DELETE FROM favorite_races WHERE id = :raceId")
    suspend fun deleteFavoriteRace(raceId: Int)
}
```

**Database:**

```kotlin
@Database(entities = [FavoriteRace::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun raceDao(): RaceDao
}
```

Room is provided as a singleton via Koin in `DatabaseModule`. The `FavoriteRaceRepository`
maps the Room entity to a domain `FavoriteRace` model via `toDomain()`, keeping the domain
layer free of Room annotations.

## Consequences

### Positive

- **Reactive by default** — `Flow<List<FavoriteRace>>` from the DAO automatically emits new
  values when data changes, driving UI updates without manual refresh logic.
- **Compile-time query validation** — Room's annotation processor validates SQL queries against
  the schema at compile time, catching typos and type mismatches early.
- **Type-safe schema** — Entity classes define the schema in Kotlin, with `@PrimaryKey`,
  `@ColumnInfo`, and conflict strategies expressed as annotations.
- **Testable** — `Room.inMemoryDatabaseBuilder()` enables fast, isolated integration tests
  without affecting production data.
- **Coroutine integration** — `suspend` DAO functions integrate naturally with the coroutine-based
  repository and use case layers.

### Negative

- **Annotation processing** — Room requires `ksp` (Kotlin Symbol Processing), adding to build
  time in the `:data` module.
- **Migration management** — Schema changes require explicit `Migration` objects or
  `fallbackToDestructiveMigration()`. Currently using destructive migration since the only entity
  is user-generated favourites.
- **Module boundary overhead** — Room entities live in `:data`, but the domain model lives in
  `:domain`. A mapper (`toDomain()`) bridges the two, adding a small mapping layer.

### Neutral

- Only one entity (`FavoriteRace`) currently uses Room. The database is intentionally simple,
  as the app is primarily API-driven.
- 10 instrumented tests validate DAO operations including insert, delete, conflict resolution,
  and Flow reactivity.
