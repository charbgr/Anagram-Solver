package com.github.anagramsolver

import org.gradle.api.provider.Property

internal fun <T> Property<T>.setAndDisallowChanges(value: T?) {
    set(value)
    disallowChanges()
}
