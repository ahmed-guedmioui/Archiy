# Archiy

Archiy is an Android application built with Clean Architecture principles, following a multi-module structure for scalability and maintainability.

## 📋 Table of Contents

- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Module Overview](#module-overview)
- [Data Flow](#data-flow)
- [Dependency Injection](#dependency-injection)
- [Error Handling](#error-handling)
- [Adding New Features](#adding-new-features)
- [Build & Setup](#build--setup)
- [Technologies](#technologies)

## 🏗️ Architecture

This project follows **Clean Architecture** principles with clear separation of concerns across three main layers:

```
┌─────────────────────────────────────────────────────────┐
│                    Presentation Layer                   │
│  (UI Components, ViewModels, Compose Screens)           │
└────────────────────┬────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────┐
│                     Domain Layer                        │
│  (Use Cases, Business Logic, Domain Models, Interfaces) │
└────────────────────┬────────────────────────────────────┘
                     │
                     ▼
┌────────────────────────────────────────────────────────────┐
│                      Data Layer                            │
│  (Repositories, Services, Data Sources, DTOs, API Clients) │
└────────────────────────────────────────────────────────────┘
```

### Architecture Principles

1. **Dependency Rule**: Dependencies point inward
   - Presentation depends on Domain
   - Domain has no dependencies (pure Kotlin)
   - Data depends on Domain

2. **Separation of Concerns**: Each layer has a single responsibility
   - **Presentation**: UI and user interaction
   - **Domain**: Business logic and rules
   - **Data**: Data retrieval and persistence

3. **Testability**: Domain layer is platform-independent and easily testable

## 📁 Project Structure

The project is organized into feature-based and layer-based modules:

```
Archiy/
├── app/                          # Main application module
│   └── src/main/java/com/app/
│       ├── App.kt               # Application class (DI setup)
│       ├── di/
│       │   └── AppModule.kt    # App-level DI module
│       └── presentation/
│           ├── MainActivity.kt  # Entry point
│           ├── MainViewModel.kt
│           ├── MainState.kt
│           ├── MainAction.kt
│           └── MainEvent.kt
│
├── core/                         # Core modules (shared across features)
│   ├── data/                     # Data layer implementation
│   │   ├── client/               # HTTP client, local storage
│   │   │   ├── http/             # Ktor HTTP client
│   │   │   └── local_storage/    # DataStore implementation
│   │   ├── service/              # Service implementations
│   │   ├── dto/                  # Data Transfer Objects
│   │   └── di/                   # Dependency injection modules
│   │
│   ├── domain/                   # Domain layer (pure Kotlin)
│   │   ├── model/                # Domain models
│   │   ├── service/              # Service interfaces
│   │   ├── client/               # Client interfaces
│   │   └── util/                 # Utilities (Result, Logger, etc.)
│   │
│   └── presentation/             # Presentation layer (shared UI)
│       ├── design_system/        # Reusable UI components
│       ├── theme/                # Material theme
│       └── util/                 # UI utilities
│
├── feature/                       # Feature modules
│   ├── auth/                     # Authentication feature
│   │   ├── data/                 # Auth data layer
│   │   ├── domain/               # Auth domain layer
│   │   └── presentation/         # Auth UI
│   │
│   ├── home/                     # Home feature
│   │   ├── data/                 # Home data layer
│   │   ├── domain/               # Home domain layer
│   │   └── presentation/         # Home UI
│   │
│   └── profile/                  # Profile feature
│       ├── data/                 # Profile data layer
│       ├── domain/               # Profile domain layer
│       └── presentation/         # Profile UI
│
└── nav-root/                      # Navigation module
    ├── api/                       # Navigation API
    └── impl/                      # Navigation implementation
        └── NavRoot.kt             # Root navigation composable
```

## 📦 Module Overview

### App Module (`app/`)
- **Purpose**: Application entry point
- **Responsibilities**:
  - Initialize dependency injection (Koin)
  - Setup Firebase (Analytics, Crashlytics, Config, Messaging)
  - Create notification channels (messages for driver, location)
  - Configure application-level settings
  - Handle splash screen logic

### Core Modules

#### `core:domain`
- **Type**: Pure Kotlin (JVM) module
- **Purpose**: Business logic and domain models
- **Contains**:
  - Domain models (User, Response, ArchiyFile, etc.)
  - Service interfaces (SessionService, etc.)
  - Client interfaces (LocalStorageClient, etc.)
  - Utilities (Result type, Error types, Logger)
- **Dependencies**: None (pure Kotlin)

#### `core:data`
- **Type**: Android library module
- **Purpose**: Data layer implementation
- **Contains**:
  - HTTP client (KtorHttpClient)
  - Local storage (DataStoreLocalStorageClient)
  - Service implementations (LocalStorageSessionService)
  - DTOs and mappers
- **Dependencies**: `core:domain`

#### `core:presentation`
- **Type**: Android library module
- **Purpose**: Shared UI components and utilities
- **Contains**:
  - Design system components (CoreButton, CoreTextField, CorePasswordTextField, etc.)
  - Theme configuration
  - UI utilities (error handling, event observation)
- **Dependencies**: `core:domain`

### Feature Modules

#### `feature:auth`
Feature modules follow the same three-layer structure:

- **`feature:auth:domain`**
  - Use cases (LoginUseCase, RegisterUseCase, CheckCanLoginUseCase, CheckCanRegisterUseCase)
  - Domain models (LoginData)
  - Service interfaces (EmailValidatorService, AuthRepository)
  - **Dependencies**: `core:domain`

- **`feature:auth:data`**
  - Service implementations (KtorAuthService, AndroidEmailValidatorService)
  - DTOs and mappers
  - **Dependencies**: `core:data`, `core:domain`, `feature:auth:domain`

- **`feature:auth:presentation`**
  - ViewModels (LoginViewModel, RegisterViewModel)
  - Compose screens (LoginScreen, RegisterScreen)
  - Navigation (AuthNavigation)
  - **Dependencies**: `core:presentation`, `core:domain`, `feature:auth:domain`

#### `feature:home`
- **`feature:home:domain`**
  - Use cases and domain models for home feature
  - **Dependencies**: `core:domain`

- **`feature:home:data`**
  - Service implementations and DTOs
  - **Dependencies**: `core:data`, `core:domain`, `feature:home:domain`

- **`feature:home:presentation`**
  - ViewModels (HomeViewModel)
  - Compose screens (HomeScreen)
  - **Dependencies**: `core:presentation`, `core:domain`, `feature:home:domain`

#### `feature:profile`
- **`feature:profile:domain`**
  - Use cases (GetUserUseCase, LogoutUseCase)
  - **Dependencies**: `core:domain`

- **`feature:profile:data`**
  - Service implementations and DTOs
  - **Dependencies**: `core:data`, `core:domain`, `feature:profile:domain`

- **`feature:profile:presentation`**
  - ViewModels (ProfileViewModel)
  - Compose screens (ProfileScreen)
  - **Dependencies**: `core:presentation`, `core:domain`, `feature:profile:domain`

### Navigation Module (`nav-root/`)
- **Purpose**: Root navigation setup
- **Contains**: 
  - Navigation graph and route definitions
  - Main bottom navigation bar (Home, Profile)
  - Route definitions (Auth, Main, Home)

## 🔄 Data Flow

The data flow follows a unidirectional pattern:

```
User Action
    │
    ▼
┌─────────────────┐
│   Composable    │
│   (UI Screen)   │
└────────┬────────┘
         │ onAction()
         ▼
┌─────────────────┐
│   ViewModel     │
│ (State/Actions) │
└────────┬────────┘
         │ invoke()
         ▼
┌─────────────────┐
│    UseCase      │
│ (Business Logic)│
└────────┬────────┘
         │ call()
         ▼
┌────────────────────────┐
│   Service/Repository   │
└────────┬───────────────┘
         │
         ├──► HTTP Client (Remote)
         │
         └──► Local Storage (Cache)
```

### Example: Login Flow

1. **User enters credentials** → `LoginScreen` collects input
2. **User taps login** → `LoginScreen` calls `viewModel.onAction(LoginAction.OnLogin)`
3. **ViewModel** → Calls `loginUseCase.invoke(email, password)`
4. **UseCase** → Validates input, calls `authRepository.login(email, password)`
5. **Service** → Makes HTTP request via `KtorHttpClient`
6. **Response** → Returns `Result<Response<LoginData>, DataError>`
7. **UseCase** → Processes response, saves session if successful
8. **ViewModel** → Updates state and emits events
9. **UI** → Observes state/events and updates accordingly

## 💉 Dependency Injection

The project uses **Koin** for dependency injection. Modules are organized by layer:

### Core Modules

**`coreDataModule`** (`core:data/di/CoreDataModule.kt`)
- HTTP client setup (KtorHttpClient)
- Local storage client (DataStoreLocalStorageClient)
- Session service (LocalStorageSessionService)

**`corePresentationModule`** (`core:presentation/di/`)
- Shared presentation components

**`appModule`** (`app/di/AppModule.kt`)
- App-level dependencies

### Feature Modules

**`authDataModule`** (`feature:auth:data/di/`)
- Auth service implementation
- Email validator

**`authPresentationModule`** (`feature:auth:presentation/di/`)
- Use cases
- ViewModels

**`homeDataModule`** (`feature:home:data/di/`)
- Home service implementations

**`homePresentationModule`** (`feature:home:presentation/di/`)
- Home use cases
- ViewModels

**`profilePresentationModule`** (`feature:profile:presentation/di/`)
- Profile use cases (GetUserUseCase, LogoutUseCase)
- ViewModels (ProfileViewModel)

### Initialization

Dependency injection is initialized in `App.kt`:

```kotlin
startKoin {
    androidContext(this@App)
    modules(
        appModule,
        coreDataModule,
        corePresentationModule,
        authDataModule,
        authPresentationModule,
        homeDataModule,
        homePresentationModule,
        profilePresentationModule,
    )
}
```

### Usage in Compose

```kotlin
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel()
) {
    // Use viewModel
}
```

## ⚠️ Error Handling

The project uses a centralized error handling system:

### Result Type

All operations return `Result<T, E>`:

```kotlin
sealed interface Result<out D, out E : Error> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : Error>(val error: E) : Result<Nothing, E>
}
```

### Error Types

- **`DataError.Remote`**: Network errors (UNAUTHORIZED, NO_INTERNET, etc.)
- **`DataError.Local`**: Local errors (DISK_FULL, NOT_FOUND, etc.)
- **`DataError.Connection`**: Connection errors
- **UseCase-specific errors**: Domain-specific errors (e.g., `LoginUseCase.LoginError`)

### Error Flow

```
Clinet → Result<Data, DataError>

Repository/Service → Result<DomainModel, Error>
    │
    ▼
UseCase → Result<DomainModel, Error>
    │
    ▼
ViewModel → Converts to UiText
    │
    ▼
UI → Displays error via Dialog/Toast
```

### Error Mapping

Errors are mapped to user-friendly messages in ViewModels and displayed through error dialogs or toasts.

### Event-Based Error Display

ViewModels emit events through a `Channel`:

```kotlin
sealed interface LoginEvent {
    data class OnError(val error: UiText) : LoginEvent
    data object OnSuccess : LoginEvent
}
```

UI observes events:

```kotlin
ObserveAsEvent(viewModel.event) { event ->
    when (event) {
        is LoginEvent.OnError -> {
            errorDialog(event.error.asString(context))
        }
        LoginEvent.OnSuccess -> {
            onLoggedIn()
        }
    }
}
```

## 🚀 Adding New Features

To add a new feature (e.g., `settings`):

### 1. Create Feature Modules

```
feature/
└── settings/
    ├── data/
    │   ├── build.gradle.kts
    │   └── src/main/java/com/settings/data/
    ├── domain/
    │   ├── build.gradle.kts
    │   └── src/main/java/com/settings/domain/
    └── presentation/
        ├── build.gradle.kts
        └── src/main/java/com/settings/presentation/
```

### 2. Update `settings.gradle.kts`

```kotlin
include("feature:settings:data")
include("feature:settings:domain")
include("feature:settings:presentation")
```

### 3. Create Domain Layer

**`feature:settings:domain`**

```kotlin
// Service interface
interface SettingsService {
    suspend fun getSettings(): Result<Settings, DataError.Remote>
}

// Use case
class GetSettingsUseCase(
    private val settingsService: SettingsService
) {
    suspend operator fun invoke(): Result<Settings, Error> {
        return settingsService.getSettings()
    }
}

// Domain model
data class Settings(
    val theme: String,
    val notifications: Boolean
)
```

### 4. Create Data Layer

**`feature:settings:data`**

```kotlin
// DTO
@Serializable
data class SettingsDto(
    @SerialName("theme") val theme: String,
    @SerialName("notifications") val notifications: Boolean
)

// Mapper
fun SettingsDto.toDomain(): Settings {
    return Settings(
        theme = theme,
        notifications = notifications
    )
}

// Service implementation
class KtorSettingsService(
    private val httpClient: KtorHttpClient
) : SettingsService {
    override suspend fun getSettings(): Result<Settings, DataError.Remote> {
        return httpClient.get<SettingsDto>(HttpRoutes.SETTINGS)
            .map { it.toDomain() }
    }
}

// DI Module
val settingsDataModule = module {
    factoryOf(::KtorSettingsService).bind<SettingsService>()
}
```

### 5. Create Presentation Layer

**`feature:settings:presentation`**

```kotlin
// State
data class SettingsState(
    val settings: Settings? = null,
    val isLoading: Boolean = false
)

// Events
sealed interface SettingsEvent {
    data class OnError(val error: UiText) : SettingsEvent
}

// ViewModel
class SettingsViewModel(
    private val getSettingsUseCase: GetSettingsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(SettingsState())
    val state = _state.stateIn(...)
    
    private val eventChannel = Channel<SettingsEvent>()
    val event = eventChannel.receiveAsFlow()
    
    fun loadSettings() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            getSettingsUseCase()
                .onSuccess { settings ->
                    _state.update { it.copy(settings = settings, isLoading = false) }
                }
                .onError { error ->
                    eventChannel.send(SettingsEvent.OnError(error.toUiText()))
                    _state.update { it.copy(isLoading = false) }
                }
        }
    }
}

// Screen
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    // UI implementation
}

// DI Module
val settingsPresentationModule = module {
    factoryOf(::GetSettingsUseCase)
    viewModelOf(::SettingsViewModel)
}
```

### 6. Register Modules in `App.kt`

```kotlin
startKoin {
    androidContext(this@App)
    modules(
        appModule,
        coreDataModule,
        corePresentationModule,
        authDataModule,
        authPresentationModule,
        homeDataModule,
        homePresentationModule,
        profilePresentationModule,
        settingsDataModule,  // Add this
        settingsPresentationModule,  // Add this
    )
}
```

### 7. Add Navigation Route

**`nav-root/impl/Route.kt`**

```kotlin
@Serializable
sealed interface Route : NavKey {
    // ... existing routes
    @Serializable
    data object Settings : Route
}
```

## 🔧 Build & Setup

### Setup

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle files
4. Build and run

### Build Variants

The project supports multiple build variants:
- `stageDebug`
- `prodDebug`

### Version Management

Version information is managed in `gradle/libs.versions.toml`:
- `versionMajor = "1"`
- `versionMinor = "0"`
- `versionPatch = "0"`
- `applicationId = "com.agapps.archiy"`

## 🛠️ Technologies

### Core Technologies
- **Kotlin**: Primary language
- **Jetpack Compose**: UI framework
- **Material 3**: Design system
- **Kotlin Coroutines**: Asynchronous programming
- **Kotlin Flow**: Reactive streams

### Architecture & DI
- **Clean Architecture**: Architecture pattern
- **Koin**: Dependency injection
- **MVVM**: Presentation pattern

### Networking
- **Ktor**: HTTP client
- **Kotlinx Serialization**: JSON serialization
- **OkHttp**: HTTP engine

### Data Persistence
- **DataStore Preferences**: Local storage
- **Android Keystore**: Secure storage (if used)

### Navigation
- **Navigation 3**: Compose navigation

### Other Libraries
- **Firebase**: Analytics, Crashlytics, Config, Messaging
- **Coil**: Image loading
- **Splash Screen**: Core splash screen API

## 📝 Code Style Guidelines

### Package Naming
- Use lowercase with underscores: `com.feature.module.submodule`
- Follow feature-based organization

### Naming Conventions
- **ViewModels**: `FeatureViewModel`
- **Use Cases**: `ActionUseCase` (e.g., `LoginUseCase`, `GetProfileUseCase`)
- **Services**: `KtorFeatureService` (implementation), `FeatureService` (interface)
- **DTOs**: `FeatureDto`
- **Mappers**: `FeatureDto.toDomain()`

### State Management
- Use `StateFlow` for state
- Use `Channel` for one-time events
- Keep state immutable (use `copy()`)

### Error Handling
- Always return `Result<T, E>`
- Map errors to `UiText` in ViewModels
- Emit errors via events, not state

## 🔐 Security

- Session management through `SessionService`
- Secure token storage
- User data persistence via DataStore
