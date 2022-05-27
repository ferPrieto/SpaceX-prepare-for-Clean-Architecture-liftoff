<h1 align="center">SpaceX prepare for Clean Architecture liftoff:rocket:</h1>

<p align="center">
  <a href="https://developer.android.com/jetpack/androidx/releases/compose"><img alt="JetpackCompose" src="https://img.shields.io/badge/Jetpack%20Compose-1.1.0--beta03-blueviolet"/></a>
  <a href="https://developer.android.com/reference"><img alt="Platform" src="https://img.shields.io/badge/platform-android-brightgreen.svg"/></a>
  <a href="https://android-developers.googleblog.com/2022/01/android-studio-bumblebee-202111-stable.html"><img alt="Android Studio Dolphin" src="https://img.shields.io/badge/AS%20Bumblebee-2021.3.1%20Patch%204-9cf.svg"/></a>
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://app.bitrise.io/app/64cd2ed600a14151"><img alt="Build Status" src="https://app.bitrise.io/app/64cd2ed600a14151/status.svg?token=9eYCKzT6HcFJeAGeZmEH6g&branch=master"/></a>
</p>

<p align="center">
The purpose of this project is to consolidate some of the learned insights throughout the years about the <b>Clean Architecture</b> principles and reflect those lessons on Android, taking advantage of the Kotlin programming language features too.
<br/><br>This project summarises some of the general use cases and demands on a typical production project using: <b>Jetpack Compose</b>, <b>Functional Programming</b>,<b>MVVM</b>, <b>Kotlin Coroutines</b> and <b>Kotlin Flows</b> (check the branches section for more information).
</p> 

<p align="center">
<img src="/art/SpaceX-Latest-Banner.jpg"/>
</p>

<p>
<p align="right">
<img src="/art/SpaceX-Demo.gif" align="right" width="32%"/>
</p>

<p align="left">

## Libraries Used :books:
* [Compose][0] Toolkit for building native UI (in a declarative way - 100% Kotlin).
* [Coroutines][1] Library support for Kotlin coroutines.
* [Flows][2] Stream processing API, built on top of Coroutines.
* [Compose Navigation][3] for tabs navigation using Jetpack Compose.
* [Dagger Hilt][4] Dependency injection library for Android.
* [Retrofit][5] Type-safe REST client for Android to consume RESTful web services.
* [Timber][6] Logger with a small API which provides utility on top of Android's normal Log class.
* [Espresso][7] Android UI Testing framework.
* [MockK][8] mocking framework for testing.
* [MockWebServer][9] A scriptable web server for testing HTTP clients, used for Instrumentation tests in this project.
* [Coil Compose][10] Image downloading and caching library supported by Jetpack Compose.
* [Lottie Compose][11] Library that provides that parses Adobe After Effects animations exported as json with Bodymovin and renders them natively on mobile.
* [JodaTime][12] Date library that lets manage more extensively and easily dates
* [Shot][13] Shot is a Gradle plugin and a core android library thought to run screenshot tests for Android.

</p>

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
* **app** - The application module with access to **all the application**
* **data** - Android module that **can only access domain module**
* **data-api** - Android module that **can only access data module**
* **domain** - Kotlin module that **cannot access any other module**

And two extra modules:
* **core** - Base classes module (factories, events, etc.) that **cannot access any other module**
* **core-android-test** - Tests classes module (rules, date builders,etc.) that **cannot access any other module**

## Branches :octocat:
There are three options depending on different tech-stack desired. The latest codebase will be updated in master.
These are the three options available (all of them maintained):

- [master][13]
  - Jetpack Compose (declarative UI)
  - Dagger Hilt
  - VM approach using UI States and Effects
  - Kotlin Flows (removed LiveData)
  - Modules simplification
  - The rest of modules remain the same (Tests)

- [SpaceX-Coroutines-Flows][14]
  - Imperative UI
  - Dagger2
  - Kotlin Coroutines and Flows
  - Granular modularisation (CleanArchitecture approach)
  - Unit Tests + UI Tests + MockWebServer + Robot Pattern

- [SpaceX-RxJava][15]
    - VMs and Fragments communication via RxJava
    - Dagger2
    - Granular modularisation (CleanArchitecture approach)
    - Unit Tests + UI Tests + MockWebServer + Robot Pattern

[13]: https://github.com/ferPrieto/SpaceX-prepare-for-Clean-Architecture-liftoff/tree/master
[14]: https://github.com/ferPrieto/SpaceX-prepare-for-Clean-Architecture-liftoff/tree/feature/SpaceX-Coroutines-Flows
[15]: https://github.com/ferPrieto/SpaceX-prepare-for-Clean-Architecture-liftoff/tree/feature/SpaceX-RxJava

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
- MockWebserver, where real connection scenarios are setup. More information in my  [MockWebServer Medium Article][14]!
- `Tests in Isolation`, where it's possible to mock and set any content, UI state, etc. Which allows to emulate very specific edge cases.
- Screenshot Testing :camera_flash:

In the two first types of tests I used Robot Pattern to improve cleanliness and ease of readability. More information in my [RobotPatter Medium Article][15]!


[14]: https://proandroiddev.com/lessons-learned-on-jetpack-compose-ui-testing-mockwebserver-848c262e799c
[15]: https://medium.com/proandroiddev/lessons-learned-on-jetpack-compose-ui-testing-robot-pattern-d5e82a9f4efc

## License :oncoming_police_car:
    Copyright 2022 Fernando Prieto Moyano

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.