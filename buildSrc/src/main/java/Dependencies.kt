/**
 * Dependency coordinates.
 */
object Dependencies {

    object Gradle {
        const val buildTools = "com.android.tools.build:gradle:${Versions.gradle}"
        const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val androidXNavSafeArgs =
            "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigationComponent}"
    }

    object Kotlin {
        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"

        // Coroutines
        const val coroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
        const val coroutinesTest =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    }

    object AndroidX {
        // Android Extensions
        const val core = "androidx.core:core-ktx:${Versions.androidXCore}"
        const val annotations = "androidx.annotation:annotation:${Versions.androidXAnnotations}"
        const val legacySupport =
            "androidx.legacy:legacy-support-v4:${Versions.androidXLegacySupport}"

        // Architecture Components
        const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
        const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
        const val cardView = "androidx.cardview:cardview:${Versions.cardView}"

        // Activity and Fragment
        const val activity = "androidx.activity:activity-ktx:${Versions.activity}"
        const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"

        // Architecture Lifecycle
        const val lifecycleViewModel =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.archLifecycle}"
        const val lifecycleLiveData =
            "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.archLifecycle}"
        const val lifecycleCompiler =
            "androidx.lifecycle:lifecycle-compiler:${Versions.archLifecycle}"
        const val lifecycleExt = "androidx.lifecycle:lifecycle-extensions:${Versions.archLifecycle}"

        // Navigation Component
        const val navigationFragment =
            "androidx.navigation:navigation-fragment-ktx:${Versions.navigationComponent}"
        const val navigationUi =
            "androidx.navigation:navigation-ui-ktx:${Versions.navigationComponent}"
        const val navigationTesting =
            "androidx.navigation:navigation-testing:${Versions.navigationComponent}"

        // Room
        const val room = "androidx.room:room-ktx:${Versions.room}"
        const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
        const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
        const val roomTesting = "androidx.room:room-testing:${Versions.room}"

        // Paging
        const val paging = "androidx.paging:paging-runtime-ktx:${Versions.paging}"
        const val pagingTesting = "androidx.paging:paging-common-ktx:${Versions.paging}"

        // Instrumented Testing
        const val testJUnit = "androidx.test.ext:junit-ktx:${Versions.androidXTestJUnitExt}"
        const val testCore = "androidx.test:core-ktx:${Versions.androidXTest}"
        const val testRules = "androidx.test:rules:${Versions.androidXTest}"
        const val archCoreTesting = "androidx.arch.core:core-testing:${Versions.archTesting}"
        const val fragmentTesting = "androidx.fragment:fragment-testing:${Versions.fragment}"

        // Espresso
        const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
        const val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
        const val espressoIdlingConcurrent =
            "androidx.test.espresso.idling:idling-concurrent:${Versions.espresso}"
        const val espressoIdlingResource =
            "androidx.test.espresso:espresso-idling-resource:${Versions.espresso}"
        const val espressoIntents = "androidx.test.espresso:espresso-intents:${Versions.espresso}"
    }

    object Google {
        const val material = "com.google.android.material:material:${Versions.material}"
        const val truth = "com.google.truth:truth:${Versions.truth}"
    }

    object Koin {
        const val android = "org.koin:koin-android:${Versions.koin}"
        const val test = "org.koin:koin-test:${Versions.koin}"
        const val viewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
    }

    object Square {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val converterScalars = "com.squareup.retrofit2:converter-scalars:${Versions.retrofit}"
        const val converterMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"

        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"

        const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    }

    object Glide {
        const val library = "com.github.bumptech.glide:glide:${Versions.glide}"
        const val compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    }

    object Robolectric {
        const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
        const val annotations = "org.robolectric:annotations:${Versions.robolectric}"
    }

    const val jUnit = "junit:junit:${Versions.jUnit}"

    const val hamcrest = "org.hamcrest:hamcrest-all:${Versions.hamcrest}"

    const val mockitoCore = "org.mockito:mockito-core:${Versions.mockito}"
    const val linkedInDexMakerMockito =
        "com.linkedin.dexmaker:dexmaker-mockito:${Versions.dexMakerMockito}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    const val spinKit = "com.github.ybq:Android-SpinKit:${Versions.spinKit}"
}
