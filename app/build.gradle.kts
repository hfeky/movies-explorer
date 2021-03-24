plugins {
    id(Plugins.androidApp)
    `kotlin-android`
    `kotlin-parcelize`
    `kotlin-kapt`
    id(Plugins.androidXNavSafeArgs)
}

android {
    compileSdkVersion(BuildConfig.compileSdk)

    defaultConfig {
        applicationId = "com.husseinelfeky.moviesexplorer"
        minSdkVersion(BuildConfig.minSdk)
        targetSdkVersion(BuildConfig.targetSdk)
        versionCode = BuildConfig.versionCode
        versionName = BuildConfig.versionName
        testInstrumentationRunner = BuildConfig.testInstrumentationRunner
        multiDexEnabled = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    @Suppress("UnstableApiUsage")
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    @Suppress("UnstableApiUsage")
    packagingOptions {
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
    }

    testOptions.unitTests.apply {
        isIncludeAndroidResources = true
        isReturnDefaultValues = true
    }

    @Suppress("UnstableApiUsage")
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    kotlinProject()
    coroutinesDependencies(true)

    androidExtensions()
    uiDependencies()
    activityDependencies()

    architectureLifecycleDependencies()
    navigationDependencies(true)
    roomDependencies(true)
    pagingDependencies(true)
    koinDependencies(true)
    retrofitDependencies(true)
    glideDependencies()

    unitTestDependencies()
    androidTestDependencies()
}
