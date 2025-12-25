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
│   └── src/main/java/com/core/app/
│       ├── App.kt               # Application class (DI setup)
│       └── presentation/
│           └── MainActivity.kt  # Entry point
│
├── core/                         # Core modules (shared across features)
│   ├── data/                     # Data layer implementation
│   │   ├── client/               # HTTP client, local storage
│   │   ├── service/              # Service implementations
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
│   └── auth/                     # Authentication feature
│       ├── data/                  # Auth data layer
│       ├── domain/                # Auth domain layer
│       └── presentation/          # Auth UI
│
└── navigation_root/               # Navigation module
    └── NavigationRoot.kt          # Root navigation composable
```

## 📦 Module Overview

### App Module (`app/`)
- **Purpose**: Application entry point
- **Responsibilities**:
  - Initialize dependency injection (Koin)
  - Setup Firebase
  - Create notification channels
  - Configure application-level settings

### Core Modules

#### `core:domain`
- **Type**: Pure Kotlin (JVM) module
- **Purpose**: Business logic and domain models
- **Contains**:
  - Domain models (User, ApiResponse, etc.)
  - Service interfaces (SessionService, AuthService, etc.)
  - Client interfaces (LocalStorageClient, etc.)
  - Utilities (Result type, Error types, Logger)
- **Dependencies**: None (pure Kotlin)

#### `core:data`
- **Type**: Android library module
- **Purpose**: Data layer implementation
- **Contains**:
  - HTTP client (KtorHttpClient)
  - Local storage (DataStoreLocalStorageClient)
  - Service implementations
  - DTOs and mappers
- **Dependencies**: `core:domain`

#### `core:presentation`
- **Type**: Android library module
- **Purpose**: Shared UI components and utilities
- **Contains**:
  - Design system components (CoreButton, CoreTextField, etc.)
  - Theme configuration
  - UI utilities (error handling, event observation)
- **Dependencies**: `core:domain`

### Feature Modules

#### `feature:auth`
Feature modules follow the same three-layer structure:

- **`feature:auth:domain`**
  - Use cases (LoginUseCase, CheckCanLoginUseCase)
  - Domain models (LoginResponse)
  - Service interfaces (AuthService)
  - **Dependencies**: `core:domain`

- **`feature:auth:data`**
  - Service implementations (KtorAuthService)
  - DTOs and mappers
  - **Dependencies**: `core:data`, `core:domain`, `feature:auth:domain`

- **`feature:auth:presentation`**
  - ViewModels (LoginViewModel, EasyLoginViewModel)
  - Compose screens (LoginScreen, EasyLoginScreen)
  - Navigation (AuthNavigation)
  - **Dependencies**: `core:presentation`, `core:domain`, `feature:auth:domain`

### Navigation Module (`navigation_root/`)
- **Purpose**: Root navigation setup
- **Contains**: Navigation graph and route definitions

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
│  (State/Events) │
└────────┬────────┘
         │ invoke()
         ▼
┌─────────────────┐
│    UseCase      │
│ (Business Logic)│
└────────┬────────┘
         │ call()
         ▼
┌─────────────────┐
│    Service      │
│  (Repository)   │
└────────┬────────┘
         │
         ├──► HTTP Client (Remote)
         │
         └──► Local Storage (Cache)
```

### Example: Login Flow

1. **User enters credentials** → `LoginScreen` collects input
2. **User taps login** → `LoginScreen` calls `viewModel.onAction(LoginAction.OnLogin)`
3. **ViewModel** → Calls `loginUseCase.invoke(email, password)`
4. **UseCase** → Validates input, calls `authService.login(email, password)`
5. **Service** → Makes HTTP request via `KtorHttpClient`
6. **Response** → Returns `Result<LoginResponse, DataError>`
7. **UseCase** → Processes response, saves session if successful
8. **ViewModel** → Updates state and emits events
9. **UI** → Observes state/events and updates accordingly

## 💉 Dependency Injection

The project uses **Koin** for dependency injection. Modules are organized by layer:

### Core Modules

**`coreDataModule`** (`core:data/di/CoreDataModule.kt`)
- HTTP client setup
- Local storage client
- Session service

**`authDataModule`** (`feature:auth:data/di/AuthDataModule.kt`)
- Auth service implementation
- Email validator

**`authPresentationModule`** (`feature:auth:presentation/di/authPresentationModule.kt`)
- Use cases
- ViewModels

### Initialization

Dependency injection is initialized in `App.kt`:

```kotlin
startKoin {
    androidContext(this@App)
    modules(
        coreDataModule,
        authDataModule,
        authPresentationModule,
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
Service → Result<Data, DataError>
    │
    ▼
UseCase → Result<DomainModel, Error>
    │
    ▼
ViewModel → Converts to UiText
    │
    ▼
UI → Displays error via Toast/Alert
```

### Error Mapping

Errors are mapped to user-friendly messages in `DataErrorToUiText.kt`:

```kotlin
fun DataError.toUiText(): UiText {
    return when(this) {
        DataError.Remote.NO_INTERNET -> UiText.Resource(R.string.error_no_internet)
        DataError.Remote.UNAUTHORIZED -> UiText.Resource(R.string.error_unauthorized)
        // ...
    }
}
```

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
            errorToast(event.error.asString(context), context)
        }
        LoginEvent.OnSuccess -> {
            onLoggedIn()
        }
    }
}
```

## 🚀 Adding New Features

To add a new feature (e.g., `profile`):

### 1. Create Feature Modules

```
feature/
└── profile/
    ├── data/
    │   ├── build.gradle.kts
    │   └── src/main/java/com/profile/data/
    ├── domain/
    │   ├── build.gradle.kts
    │   └── src/main/java/com/profile/domain/
    └── presentation/
        ├── build.gradle.kts
        └── src/main/java/com/profile/presentation/
```

### 2. Update `settings.gradle.kts`

```kotlin
include("feature:profile:data")
include("feature:profile:domain")
include("feature:profile:presentation")
```

### 3. Create Domain Layer

**`feature:profile:domain`**

```kotlin
// Service interface
interface ProfileService {
    suspend fun getProfile(): Result<Profile, DataError.Remote>
}

// Use case
class GetProfileUseCase(
    private val profileService: ProfileService
) {
    suspend operator fun invoke(): Result<Profile, Error> {
        return profileService.getProfile()
    }
}

// Domain model
data class Profile(
    val id: Int,
    val name: String,
    val email: String
)
```

### 4. Create Data Layer

**`feature:profile:data`**

```kotlin
// DTO
@Serializable
data class ProfileDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("email") val email: String
)

// Mapper
fun ProfileDto.toDomain(): Profile {
    return Profile(
        id = id,
        name = name,
        email = email
    )
}

// Service implementation
class KtorProfileService(
    private val httpClient: KtorHttpClient
) : ProfileService {
    override suspend fun getProfile(): Result<Profile, DataError.Remote> {
        return httpClient.get<ProfileDto>(HttpRoutes.PROFILE)
            .map { it.toDomain() }
    }
}

// DI Module
val profileDataModule = module {
    factoryOf(::KtorProfileService).bind<ProfileService>()
}
```

### 5. Create Presentation Layer

**`feature:profile:presentation`**

```kotlin
// State
data class ProfileState(
    val profile: Profile? = null,
    val isLoading: Boolean = false
)

// Events
sealed interface ProfileEvent {
    data class OnError(val error: UiText) : ProfileEvent
}

// ViewModel
class ProfileViewModel(
    private val getProfileUseCase: GetProfileUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state = _state.stateIn(...)
    
    private val eventChannel = Channel<ProfileEvent>()
    val event = eventChannel.receiveAsFlow()
    
    fun loadProfile() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            getProfileUseCase()
                .onSuccess { profile ->
                    _state.update { it.copy(profile = profile, isLoading = false) }
                }
                .onError { error ->
                    eventChannel.send(ProfileEvent.OnError(error.toUiText()))
                    _state.update { it.copy(isLoading = false) }
                }
        }
    }
}

// Screen
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    // UI implementation
}

// DI Module
val profilePresentationModule = module {
    factoryOf(::GetProfileUseCase)
    viewModelOf(::ProfileViewModel)
}
```

### 6. Register Modules in `App.kt`

```kotlin
startKoin {
    androidContext(this@App)
    modules(
        coreDataModule,
        authDataModule,
        authPresentationModule,
        profileDataModule,  // Add this
        profilePresentationModule,  // Add this
    )
}
```

### 7. Add Navigation Route

**`navigation_root/Route.kt`**

```kotlin
@Serializable
sealed interface Route : NavKey {
    // ... existing routes
    @Serializable
    data object Profile : Route
}
```

## 🔧 Build & Setup

### Prerequisites

- Android Studio Hedgehog or later
- JDK 11 or later
- Android SDK (minSdk: 26, targetSdk: 36)

### Setup

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle files
4. Configure `local.properties` if needed
5. Build and run

### Build Variants

The project supports multiple build variants (configured in `app/build.gradle.kts`):
- `localDebug`
- `stageDebug`

### Version Management

Version information is managed in `gradle/libs.versions.toml`:
- `versionMajor = "1"`
- `versionMinor = "6"`
- `versionPatch = "59"`

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
- **Android Keystore**: Secure storage

### Navigation
- **Navigation 3**: Compose navigation

### Other Libraries
- **Firebase**: Analytics, Crashlytics, Messaging
- **Coil**: Image loading
- **Lottie**: Animations
- **Google Maps**: Maps integration
- **MQTT**: Messaging protocol

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
