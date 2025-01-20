package com.github.anagramsolver.android

import com.android.build.api.dsl.ApplicationExtension
import com.github.anagramsolver.TARGET_SDK
import com.github.anagramsolver.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

class ΑppAndroidApplicationPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = TARGET_SDK
            }

            val applicationExtension = extensions.getByType<ApplicationExtension>()
            AppAndroidExtension
                .create(project = target)
                .applyToProject(project = target, extension = applicationExtension)
        }
    }
}
