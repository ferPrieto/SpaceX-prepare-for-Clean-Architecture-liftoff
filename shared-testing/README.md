# shared-testing

Shared testing utilities and helpers for all modules in the SpaceX project.

## Overview

This module provides common testing utilities, test rules, and helper functions that are used across all test suites in the project.

## Dependencies

- **JUnit**: Unit testing framework
- **MockK**: Mocking library for Kotlin
- **Kotlin Coroutines Test**: Testing utilities for coroutines
- **Joda Time**: Date/time manipulation for tests

## Key Components

### Test Rules

#### `TestCoroutineRule`
A JUnit rule that provides coroutine testing capabilities:
- Sets up a test dispatcher for coroutines
- Provides `runBlockingTest` for testing suspend functions
- Includes `runCurrent()` and `advanceUntilIdle()` for controlling test execution

**Usage:**
```kotlin
@ExperimentalCoroutinesApi
class MyViewModelTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Test
    fun `test suspend function`() = testCoroutineRule.runBlockingTest {
        // Your test code here
        viewModel.doSomething()
        testCoroutineRule.advanceUntilIdle()
        // Assert results
    }
}
```

### Test Extensions

#### `runTestWithTimeout`
Runs a test with coroutines and a configurable timeout:
```kotlin
@Test
fun myTest() = runTestWithTimeout(timeout = 5000L) {
    // Test code
}
```

#### `advanceTimeAndRun`
Advances virtual time and runs pending coroutines:
```kotlin
testScope.advanceTimeAndRun(1000L)
```

### MockK Test Base

#### `MockKTest`
Abstract base class that automatically initializes and cleans up MockK:
```kotlin
class MyTest : MockKTest() {
    @MockK
    lateinit var mockDependency: MyDependency

    override fun setUp() {
        // Custom setup
    }

    @Test
    fun myTest() {
        // mockDependency is already initialized
    }
}
```

### Utilities

#### `buildDate`
Builds `DateTime` objects from string representations:
```kotlin
val date = buildDate("2020-01-01T12:00:00.000Z")
```

## Usage in Other Modules

Add to your module's `build.gradle.kts`:

```kotlin
dependencies {
    testImplementation(project(":shared-testing"))
}
```

## Module Type

This is a **pure Kotlin/JVM module** (not an Android module) to ensure it can be used across all module types without introducing Android dependencies.

