package com.github.anagramsolver.android


import com.android.build.api.dsl.CommonExtension
import com.github.anagramsolver.newInstance
import com.github.anagramsolver.setAndDisallowChanges
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory

@DslMarker
annotation class AppAndroidExtensionMarker

@Suppress("UnnecessaryAbstractClass")
@AppAndroidExtensionMarker
abstract class AppAndroidExtension(
    objectFactory: ObjectFactory
) {

    companion object {
        fun create(project: Project): AppAndroidExtension = project.extensions.create(
            "appAndroid",
            AppAndroidExtension::class.java
        )
    }

    private val composeHandler = objectFactory.newInstance<ComposeHandler>()

    fun applyToProject(project: Project, extension: CommonExtension<*, *, *, *, *, *>) {
        composeHandler.applyToProject(project = project, extension = extension)
    }

    fun compose(
        activity: Boolean = false,
        material: Boolean = false,
        ui: Boolean = false,
        foundation: Boolean = false,
    ) {
        composeHandler.enabled.setAndDisallowChanges(true)
        composeHandler.includeActivity.setAndDisallowChanges(activity)
        composeHandler.includeMaterial.setAndDisallowChanges(material)
        composeHandler.includeUi.setAndDisallowChanges(ui)
        composeHandler.includeUiGraphics.setAndDisallowChanges(ui)
        composeHandler.includeUiTooling.setAndDisallowChanges(ui)
        composeHandler.includeFoundation.setAndDisallowChanges(foundation)

        composeHandler.configureAndroidCompose()
    }
}
