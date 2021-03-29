## Movies Explorer

The past decade held a lot of movies, some left a mark and some were just a set of 24-60 pictures per second. This app is a Master-Detail Application to showcase those movies and the signature they left behind.

## Project Description

- Load movies list from a local JSON file.
- Search movies by name.
- Categorize the movies search results by year and each year category has the top 5 rated movies within.
- Once a movie is selected from the search results, the user will be switched to a detailed view to unveil the
  following:
    - Movie Title
    - Movie Year
    - Movie Genres (if any)
    - Movie Cast (if any)
    - A list of pictures fetched from Flickr that matches the movie title as the search query

## Screenshots

<img src="https://github.com/HusseinElFeky/movies-explorer/blob/master/screenshots/screenshot_1.png"></a>
<img src="https://github.com/HusseinElFeky/movies-explorer/blob/master/screenshots/screenshot_2.png"></a>
<img src="https://github.com/HusseinElFeky/movies-explorer/blob/master/screenshots/screenshot_3.png"></a>
<img src="https://github.com/HusseinElFeky/movies-explorer/blob/master/screenshots/screenshot_4.png"></a>
<img src="https://github.com/HusseinElFeky/movies-explorer/blob/master/screenshots/screenshot_5.png"></a>
<img src="https://github.com/HusseinElFeky/movies-explorer/blob/master/screenshots/screenshot_6.png"></a>

## Specifications

- Parse the movies that are inside the local JSON file.
- Cache the movies in a local database to optimize loading time, reducing parsing overhead.
- Implement movies search by name functionality.
- Group the movies search results by year, and show the top 5 rated movies only for each year.
- Optimize movies search and sorting performance.
- Load movie images using Flickr Photos API.
- Create a nice transition between Master and Detail views.
- Create MotionLayout scenes.
- Implement unit tests.
- Implement integration tests.
- Implement end-to-end tests (UI tests).
- Document the unfamiliar code.

## Architecture

MVVM (Model-View-ViewModel) Architecture is used in this project. It removes the tight coupling between each component. Most importantly, in this architecture, the children don't have direct reference to the parent; they only have reference by observables.

<img src="https://github.com/HusseinElFeky/movies-explorer/blob/master/screenshots/mvvm_architecture.png"></a>

## DBMS ER Diagram

Although one could just serialize/deserialize the movies genres and cast as a list, having one table only, creating separate tables for each is preferable to ensure scalability in the future, so that one could easily implement filter options by genre or cast for instance if needed later on.

<img src="https://github.com/HusseinElFeky/movies-explorer/blob/master/screenshots/dbms_er_diagram.png"></a>

## Languages, Libraries and Tools Used

* [Kotlin](https://kotlinlang.org/), the programming language used
* [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines) for asynchronous APIs
* [Kotlin Flow](https://developer.android.com/kotlin/flow) for handling states and having operators
* [Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) for writing Kotlin instead of Groovy in Gradle scripts
* [AndroidX Libraries](https://developer.android.com/jetpack/androidx) that are part of Android Jetpack
* [Data Binding](https://developer.android.com/topic/libraries/data-binding) for binding views and safer view reference
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) for communicating between the view and the model layers
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) for implementing the Observer design pattern
* [Android Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) for handling the lifecycle of architecture components
* [Navigation Component](https://developer.android.com/guide/navigation) for easier and safer navigation between fragments
* [Room](https://developer.android.com/training/data-storage/room) for caching data in a SQLite database
* [Koin](https://github.com/InsertKoinIO/koin) for implementing Service Locator design pattern
* [Retrofit](https://github.com/square/retrofit) for making network requests
* [Moshi](https://github.com/square/moshi) for parsing network responses and JSON files
* [Glide](https://github.com/bumptech/glide) for loading images with URL
* [SpinKit](https://github.com/ybq/Android-SpinKit) for showing a unique progress bar
* [Auto Image Slider](https://github.com/smarteist/Android-Image-Slider) for implementing a slide show of images
* [Timber](https://github.com/JakeWharton/timber) for easier logging without defining tags
* [JUnit 4](https://junit.org/junit4/) for writing unit tests
* [AndroidX Test Libraries](https://developer.android.com/training/testing/set-up-project) for testing Android code with JUnit
* [Hamcrest](http://hamcrest.org/JavaHamcrest/tutorial) for more idiomatic tests
* [Mockito](https://github.com/mockito/mockito) for mocking APIs
* [Espresso](https://developer.android.com/training/testing/espresso) for writing UI tests

## Requirements

- Android Device with API 21 minimum
- Create Flickr Photos API Key

## Installation

- Clone the app using Git or import it to Android Studio.

- Create a `credentials.properties` file, and add it to the project root folder. Inside it add the Flickr Photos API key named as `flickrApiKey`.

## Usage

- To test the app, there is an APK build [here](https://github.com/HusseinElFeky/movies-explorer/raw/movies_explorer.apk) that you can directly download and install.
