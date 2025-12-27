<h1 align="center">SpaceX Prepare for Clean Architecture Liftoff :rocket:</h1>

<p align="center">
  <a href="https://developer.android.com/jetpack/androidx/releases/compose"><img alt="JetpackCompose" src="https://img.shields.io/badge/Jetpack%20Compose-1.7.6-blueviolet"/></a>
  <a href="https://developer.android.com/reference"><img alt="Platform" src="https://img.shields.io/badge/platform-android-brightgreen.svg"/></a>
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://app.bitrise.io/app/64cd2ed600a14151"><img alt="Build Status" src="https://app.bitrise.io/app/64cd2ed600a14151/status.svg?token=9eYCKzT6HcFJeAGeZmEH6g&branch=master"/></a>
</p>

<p align="center">
A modern Android application demonstrating <b>Feature-First Clean Architecture</b> principles with <b>Kotlin 2.1.0</b> and <b>Jetpack Compose</b>.
<br/><br>
Unlike traditional centralized-module approaches (separate data, domain, presentation layers), this project uses a <b>feature-first modular architecture</b> where each feature is self-contained with its own domain, data, and presentation layers, promoting true modularity and independence.
<br/><br>
Built with: <b>Kotlin Coroutines</b>, <b>Kotlin Flow</b>, <b>Hilt</b>, <b>MVVM</b>, <b>Compose Navigation 3</b>, and <b>Multi-Module Architecture</b>.
</p> 

<p align="center">
<img src="/art/SpaceX-Latest-Banner.jpg"/>
</p>

[0]:  https://developer.android.com/jetpack
[1]:  https://github.com/Kotlin/kotlinx.coroutines
[2]:  https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/
[3]:  https://developer.android.com/jetpack/compose/navigation
[4]:  https://dagger.dev/hilt/
[5]:  https://github.com/square/retrofit
[6]:  https://github.com/JakeWharton/timber
[7]:  https://developer.android.com/training/testing/espresso/
[8]:  https://mockk.io
[9]:  https://github.com/square/okhttp/tree/master/mockwebserver
[10]: https://github.com/coil-kt/coil
[11]: https://airbnb.io/lottie/#/android-compose
[12]: https://github.com/JodaOrg/joda-time
[13]: https://github.com/pedrovgs/Shot

## Modules :bookmark_tabs:

<img src="/art/SpaceX-Demo.gif" align="right" width="32%"/>

This project follows a **feature-first modular architecture** where each feature is self-contained:

### Feature Modules
* **feature-dashboard** - Company information feature with its own domain, data, and presentation layers. Displays SpaceX company details.
* **feature-launches** - Rocket launches feature. Includes launch listing, filtering, and details with full domain/data/presentation separation.
* **feature-navigation** - Navigation orchestration using custom Navigation 3 implementation. Manages app-wide navigation and bottom navigation UI.

### Core/Shared Modules
* **core-network** - Network infrastructure module. Provides Retrofit setup, API service definitions, and response models. Used by all feature data layers.
* **shared-ui** - Shared UI components and theme. Contains Material Design theme, Lottie animations, common composables, and design system.
* **shared-testing** - Test utilities and helpers. Provides coroutine test rules, MockK extensions, and test builders for all modules.

### App Module
* **app** - Application entry point. Minimal module that wires together feature modules with Hilt and initializes the application.

Each feature module contains:
- **domain/** - Use cases, domain models, and repository interfaces
- **data/** - Repository implementations, data models, and mappers
- **presentation/vm/** - ViewModels, contracts, and presentation logic
- **presentation/ui/** - Composable screens and UI models
- **di/** - Hilt dependency injection modules

<p align="center">
<img src="/art/clean_architecture_dark.jpg"/>
</p>

## Testing :mag:

### Unit Testing

There are some highlights:
* Every layer in the architecture has been tested.
* MockK has been used for mocking | stubbing.
* `Given | When | Then` code presentation order, in order to give a more structured style.
* Code Coverage (WORK IN PROGRESS).

### UI Testing (Compose)

I opted to use three types of approaches:
- MockWebserver, where real connection scenarios are setup. More information in my  [MockWebServer Medium Article][16]!
- `Tests in Isolation`, where it's possible to mock and set any content, UI state, etc. Which allows to emulate very specific edge cases.
- Screenshot Testing :camera_flash:

In the two first types of tests I used Robot Pattern to improve cleanliness and ease of readability. More information in my [RobotPattern Medium Article][17]!


[16]: https://proandroiddev.com/lessons-learned-on-jetpack-compose-ui-testing-mockwebserver-848c262e799c
[17]: https://medium.com/proandroiddev/lessons-learned-on-jetpack-compose-ui-testing-robot-pattern-d5e82a9f4efc

## License :oncoming_police_car:
    Copyright 2026 Fernando Prieto Moyano

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
