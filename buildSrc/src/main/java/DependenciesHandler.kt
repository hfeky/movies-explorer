import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.kotlinProject() {
    "implementation"(Dependencies.Kotlin.stdLib)
}

fun DependencyHandlerScope.coroutinesDependencies(withTesting: Boolean) {
    "implementation"(Dependencies.Kotlin.coroutines)

    if (withTesting) {
        "testImplementation"(Dependencies.Kotlin.coroutinesTest)
        "androidTestImplementation"(Dependencies.Kotlin.coroutinesTest)
    }
}

fun DependencyHandlerScope.androidExtensions() {
    "implementation"(Dependencies.AndroidX.core)
    "implementation"(Dependencies.timber)
}

fun DependencyHandlerScope.uiDependencies() {
    "implementation"(Dependencies.AndroidX.appCompat)
    "implementation"(Dependencies.Google.material)
    "implementation"(Dependencies.AndroidX.constraintLayout)
    "implementation"(Dependencies.AndroidX.recyclerView)
    "implementation"(Dependencies.AndroidX.cardView)
    "implementation"(Dependencies.spinKit)
}

fun DependencyHandlerScope.activityDependencies() {
    "implementation"(Dependencies.AndroidX.activity)
    "implementation"(Dependencies.AndroidX.fragment)
}

fun DependencyHandlerScope.architectureLifecycleDependencies() {
    "implementation"(Dependencies.AndroidX.lifecycleViewModel)
    "implementation"(Dependencies.AndroidX.lifecycleLiveData)
}

fun DependencyHandlerScope.navigationDependencies(withTesting: Boolean) {
    "implementation"(Dependencies.AndroidX.navigationFragment)
    "implementation"(Dependencies.AndroidX.navigationUi)

    if (withTesting) {
        "androidTestImplementation"(Dependencies.AndroidX.navigationTesting)
    }
}

fun DependencyHandlerScope.roomDependencies(withTesting: Boolean) {
    "implementation"(Dependencies.AndroidX.room)
    "implementation"(Dependencies.AndroidX.roomRuntime)
    "kapt"(Dependencies.AndroidX.roomCompiler)

    if (withTesting) {
        "androidTestImplementation"(Dependencies.AndroidX.roomTesting)
    }
}

fun DependencyHandlerScope.pagingDependencies(withTesting: Boolean) {
    "implementation"(Dependencies.AndroidX.paging)

    if (withTesting) {
        "androidTestImplementation"(Dependencies.AndroidX.pagingTesting)
    }
}

fun DependencyHandlerScope.koinDependencies(withTesting: Boolean) {
    "implementation"(Dependencies.Koin.android)
    "implementation"(Dependencies.Koin.viewModel)

    if (withTesting) {
        "testImplementation"(Dependencies.Koin.test)
        "androidTestImplementation"(Dependencies.Koin.test)
    }
}

fun DependencyHandlerScope.retrofitDependencies(includeMoshi: Boolean) {
    "implementation"(Dependencies.Square.retrofit)
    "implementation"(Dependencies.Square.loggingInterceptor)
    "implementation"(Dependencies.Square.converterScalars)

    if (includeMoshi) {
        "implementation"(Dependencies.Square.moshiKotlin)
        "implementation"(Dependencies.Square.converterMoshi)
    }
}

fun DependencyHandlerScope.glideDependencies() {
    "implementation"(Dependencies.Glide.library)
    "annotationProcessor"(Dependencies.Glide.compiler)
}

fun DependencyHandlerScope.unitTestDependencies() {
    "testImplementation"(Dependencies.jUnit)
    "testImplementation"(Dependencies.hamcrest)

    "testImplementation"(Dependencies.AndroidX.testJUnit)
    "testImplementation"(Dependencies.AndroidX.testCore)
    "testImplementation"(Dependencies.AndroidX.testRules)
    "testImplementation"(Dependencies.AndroidX.archCoreTesting)

    // Once https://issuetracker.google.com/127986458 is fixed, this can be testImplementation.
    "debugImplementation"(Dependencies.AndroidX.fragmentTesting)
}

fun DependencyHandlerScope.androidTestDependencies() {
    "androidTestImplementation"(Dependencies.AndroidX.testJUnit)
    "androidTestImplementation"(Dependencies.AndroidX.testCore)
    "androidTestImplementation"(Dependencies.AndroidX.testRules)
    "androidTestImplementation"(Dependencies.AndroidX.archCoreTesting)

    "androidTestImplementation"(Dependencies.AndroidX.espressoCore)
    "androidTestImplementation"(Dependencies.AndroidX.espressoContrib)
    "androidTestImplementation"(Dependencies.AndroidX.espressoIdlingConcurrent)
    "androidTestImplementation"(Dependencies.AndroidX.espressoIdlingResource)
    "androidTestImplementation"(Dependencies.AndroidX.espressoIntents)

    "androidTestImplementation"(Dependencies.linkedInDexMakerMockito)
}
