package com.bmpak.anagramsolver.framework.data.anagram

import com.bmpak.anagramsolver.model.Dictionary

data class AnagramEntity(
    val quarable: String,
    val wordOrigin: String,
    val dictionary: Dictionary
)
