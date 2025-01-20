package com.github.anagramsolver.kotlin

import com.github.anagramsolver.configureKotlinJvm
import org.gradle.api.Plugin
import org.gradle.api.Project

class AppKotlinPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.jvm")

            configureKotlinJvm()
        }
    }
}
