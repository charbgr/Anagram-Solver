package com.github.anagramsolver.android

import com.android.build.gradle.LibraryExtension
import com.github.anagramsolver.TARGET_SDK
import com.github.anagramsolver.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

class AppAndroidPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = TARGET_SDK
            }

            val libraryExtension = extensions.getByType<LibraryExtension>()
            AppAndroidExtension
                .create(project = target)
                .applyToProject(project = target, extension = libraryExtension)
        }
    }
}
