package com.github.anagramsolver

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.the

val Project.libs: LibrariesForLibs
    get() = the()

internal inline fun <reified T : Any> ObjectFactory.newInstance(vararg parameters: Any): T =
    newInstance(T::class.java, *parameters)

internal inline fun <reified T : Any> ObjectFactory.property(): Property<T> =
    property(T::class.java)
