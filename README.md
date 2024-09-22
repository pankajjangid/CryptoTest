# Crypto

CryptoTest is an Android application developed in Kotlin that showcases a reusable `CurrencyListFragment`. This fragment is designed to display both cryptocurrency and fiat currency lists. The application adheres to Google’s recommended architecture and includes features such as dependency injection, unit testing, and user-friendly UI states.

## Objective

The primary objective is to create a `CurrencyListFragment` that can be reused for different purposes, such as displaying cryptocurrency and fiat currency. Additionally, a `DemoActivity` showcases the functionality of the `CurrencyListFragment`.

## Features

- Reusable `CurrencyListFragment` for displaying different currency types
- `DemoActivity` with multiple functionalities
- Local database integration using Room
- Asynchronous operations with Kotlin Coroutines
- Dependency Injection with Koin
- Search functionality with customizable criteria
- Support for normal, searching, and empty states in UI

## Requirements

### Technical

- **Language**: Kotlin
- **Architecture**: Follow Google recommended architecture
- **Dependency Injection**: Koin
- **Unit Testing**: Preferred


### Libraries Used
This project uses the following libraries:

- Android Gradle Plugin: 8.5.2
- Kotlin: 1.9.0
- Core KTX: 1.10.1
- JUnit: 4.13.2
- Espresso Core: 3.5.1
- Kotlinx Coroutines: 1.7.3
- Room: 2.6.1
- Koin: 4.0.0
- Gson: 2.11.0
- Truth: 1.1.3
