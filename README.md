# Countries Android App

Android application that fetches and displays countries data from a JSON API.

## Features

- Fetches country data from remote JSON endpoint
- Displays countries in RecyclerView with scrolling
- Error handling for network failures
- Device rotation support
- Pull-to-refresh functionality
- Loading indicators

## Setup

1. Open project in Android Studio
2. Sync with Gradle files
3. Run on device/emulator (API 21+)

## Architecture

- MVVM pattern with ViewModel and LiveData
- Kotlin Coroutines for async operations
- OkHttp for network requests
- Gson for JSON parsing

## Requirements

**No Dependency Injection** - Pure Android, no Dagger  
**Scrollable List** - RecyclerView with all countries  
**Error Handling** - Network errors, timeouts, device rotation  
**Clean Code** - MVVM architecture with ViewModel 
**Edge Case Handling** - Handles missing JSON fields via Gson's default empty string assignment, preventing crashes

## API

Data source: Used source link given in Instructions doc.

## Dependencies

- OkHttp
- Gson
- RecyclerView
- SwipeRefreshLayout
- ViewModel & LiveData

## Screenshots

- [App Screenshot 1](https://drive.google.com/file/d/1mMkhOI1DXd-ud6qo4VZluzPZHkhev44S/view?usp=sharing)
- [App Screenshot 2](https://drive.google.com/file/d/1YjJqhSQRxsGfFYjSB8zc8PR4jfI5eGbb/view?usp=sharing)

