package com.github.anagramsolver

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal const val COMPILE_SDK = 35
internal const val TARGET_SDK = 35
internal const val MIN_SDK = 29

// Java version running on the apps
// Up to Java 11 APIs are available through desugaring
// https://developer.android.com/studio/write/java11-minimal-support-table
private val ANDROID_RUN_JAVA_VERSION = JavaVersion.VERSION_17

/**
 * Configure base Kotlin with Android options
 */
internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = COMPILE_SDK

        defaultConfig.apply {
            minSdk = MIN_SDK
        }

        compileOptions {
            sourceCompatibility = ANDROID_RUN_JAVA_VERSION
            targetCompatibility = ANDROID_RUN_JAVA_VERSION
        }
    }

    configureKotlin()
}

/**
 * Configure base Kotlin options for JVM (non-Android)
 */
internal fun Project.configureKotlinJvm() {
    configureKotlin()
}

/**
 * Configure base Kotlin options
 */
private fun Project.configureKotlin() {
    // Use withType to workaround https://youtrack.jetbrains.com/issue/KT-55947
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = ANDROID_RUN_JAVA_VERSION.toString()
        }
    }
}
