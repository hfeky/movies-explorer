plugins {
    /**
     * [Kotlin DSL Plugin]
     * (https://docs.gradle.org/current/userguide/kotlin_dsl.html#sec:kotlin-dsl_plugin)
     */
    `kotlin-dsl`
}

repositories {
    google()
    jcenter()
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

dependencies {
    implementation("com.android.tools.build:gradle:4.1.3")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.31")
}
