# Todo Task Manager

A simple and efficient Task Management application built with modern Android development tools.

## Features

- **Add Tasks**: Easily create new tasks.
- **Edit Tasks**: Update existing task titles.
- **Delete Tasks**: Remove tasks you no longer need.
- **Mark as Done**: Toggle the completion status of tasks with a strike-through visual.
- **Persistent Storage**: Uses Room Database to ensure your tasks are saved locally.
- **Modern UI**: Built with Jetpack Compose for a reactive and smooth user interface.
- **Empty State**: Visual feedback with Lottie animations when there are no tasks.

## Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Local Database**: Room
- **Annotation Processing**: KSP (Kotlin Symbol Processing)
- **Dependency Management**: Gradle Version Catalog
- **Animation**: Lottie for Compose

## Architecture

The project follows a simple architecture using:
- **Room Database**: For local data persistence.
- **DAO (Data Access Object)**: To define database operations.
- **Compose State Management**: To handle UI updates based on data changes.

## Getting Started

1. Clone the repository.
2. Open the project in Android Studio (Ladybug or newer recommended).
3. Build and run the app on an emulator or physical device.

## Project Structure

- `MainActivity.kt`: The main entry point containing the UI logic and state.
- `Task.kt`: The Room Entity representing a task.
- `TaskDao.kt`: The Data Access Object for Room.
- `TaskDatabase.kt`: The Room Database configuration.
