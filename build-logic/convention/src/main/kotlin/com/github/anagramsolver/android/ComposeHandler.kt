package com.github.anagramsolver.android

import com.android.build.api.dsl.CommonExtension
import com.github.anagramsolver.libs
import com.github.anagramsolver.property
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.dependencies
import javax.inject.Inject

@Suppress("UnnecessaryAbstractClass")
@AppAndroidExtensionMarker
internal abstract class ComposeHandler @Inject constructor(objectFactory: ObjectFactory) {

    private lateinit var project: Project
    private lateinit var androidExtension: CommonExtension<*, *, *, *, *, *>

    val enabled: Property<Boolean> = objectFactory.property<Boolean>().convention(false)
    val includeActivity: Property<Boolean> = objectFactory.property<Boolean>().convention(false)
    val includeMaterial: Property<Boolean> = objectFactory.property<Boolean>().convention(false)
    val includeUi: Property<Boolean> = objectFactory.property<Boolean>().convention(false)
    val includeUiGraphics: Property<Boolean> = objectFactory.property<Boolean>().convention(false)
    val includeUiTooling: Property<Boolean> = objectFactory.property<Boolean>().convention(false)
    val includeFoundation: Property<Boolean> = objectFactory.property<Boolean>().convention(false)

    internal fun applyToProject(project: Project, extension: CommonExtension<*, *, *, *, *, *>) {
        this.project = project
        this.androidExtension = extension

        with(project) {
            configureDependencies()
        }
    }

    internal fun configureAndroidCompose() {
        if (!enabled.get()) return

        with(project) {
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")
            androidExtension.apply {
                buildFeatures.compose = true
                dependencies {
                    add("implementation", platform(libs.androidx.compose.bom))
                }
            }
        }
    }

    private fun Project.configureDependencies() {
        afterEvaluate {
            dependencies {
                if (includeActivity.get()) {
                    add("implementation", libs.androidx.activity.compose)
                }

                if (includeMaterial.get()) {
                    add("implementation", libs.androidx.material3)
                }

                if (includeUi.get()) {
                    add("implementation", libs.androidx.ui)
                }

                if (includeUiGraphics.get()) {
                    add("implementation", libs.androidx.ui.graphics)
                }

                if (includeUiTooling.get()) {
                    add("implementation", libs.androidx.ui.tooling)
                    add("implementation", libs.androidx.ui.tooling.preview)
                }

                if (includeFoundation.get()) {
                    add("implementation", libs.androidx.foundation.android)
                }
            }
        }
    }
}
