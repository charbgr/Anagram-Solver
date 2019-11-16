package com.bmpak.anagramsolver.utils

import com.bmpak.anagramsolver.model.Dictionary
import com.bmpak.anagramsolver.utils.deaccenter.findDeAccenter

fun String.quarable(): String = toCharArray().sortedArray().joinToString("")
fun String.deAccent(dictionary: Dictionary) = dictionary.findDeAccenter().deAccent(this)
