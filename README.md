<div align="center">
  <img src="app/src/main/res/mipmap-xxxhdpi/ic_launcher.webp" width="192" alt="F1Stats icon"/>

# F1Stats

A native Android application for Formula 1 enthusiasts — browse race calendars, driver and team
standings, circuit information, and save your favourite races for quick access.
</div>

Built with modern Android development practices: Clean Architecture, MVVM, Kotlin Coroutines, Hilt,
Room, and Jetpack Navigation.

---

## Screenshots

| Races         | Rankings      | Circuits      | Favourites    |
|---------------|---------------|---------------|---------------|
| _coming soon_ | _coming soon_ | _coming soon_ | _coming soon_ |

---

## Features

- **Race Calendar** — Full season schedule with race details and results
- **Driver & Team Rankings** — Live standings updated from the API-Sports F1 API
- **Circuit Browser** — Explore every circuit on the calendar
- **Favourites** — Save races locally with Room; list updates reactively in real time
- **Season Selector** — Switch between seasons from anywhere in the app

---

## Tech Stack

| Category             | Technology                              |
|----------------------|-----------------------------------------|
| Language             | Kotlin                                  |
| Architecture         | Clean Architecture + MVVM               |
| UI                   | Fragments, ViewBinding, RecyclerView    |
| Navigation           | Jetpack Navigation Component + SafeArgs |
| Dependency Injection | Hilt                                    |
| Networking           | Retrofit + OkHttp                       |
| Local Storage        | Room (reactive `Flow`-backed DAO)       |
| Async                | Kotlin Coroutines                       |
| Image Loading        | Coil                                    |

---

## Architecture

The project follows Clean Architecture with three distinct layers:

```
app/src/main/java/com/david/f1stats/
├── ui/          # Fragments, ViewModels, Adapters (MVVM + LiveData)
├── domain/      # Use cases and pure domain models
└── data/        # Repositories, Retrofit services, Room DAOs, DTO mappers
```

Data flows in one direction: `Fragment → ViewModel → UseCase → Repository → API / Room`. Domain
models are kept free of framework dependencies; mappers in `data/mapper/` handle the translation
from DTOs and Room entities.

---

## Getting Started

### Prerequisites

- Android Studio Hedgehog or later
- JDK 17+
- An API key from [API-Sports](https://api-sports.io/) (free tier available)

### Setup

1. Clone the repository:
   ```sh
   git clone https://github.com/davmm96/F1Stats.git
   cd F1Stats
   ```

2. Add your API key to `local.properties`:
   ```properties
   API_KEY=your_api_key_here
   ```

3. Build and run:
   ```sh
   ./gradlew assembleDebug
   ```
   Or open the project in Android Studio and run on a device or emulator.

---

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE.md) file for details.
