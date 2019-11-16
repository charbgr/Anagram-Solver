package com.bmpak.anagramsolver.utils.deaccenter

import com.bmpak.anagramsolver.model.Dictionary
import com.bmpak.anagramsolver.model.Dictionary.ENGLISH
import com.bmpak.anagramsolver.model.Dictionary.FRANCE
import com.bmpak.anagramsolver.model.Dictionary.GERMAN
import com.bmpak.anagramsolver.model.Dictionary.GREEK

/**
 * Removes all diacritical marks from a word.
 */
interface WordDeAccenter {
  fun deAccent(anagram: String): String
}

object EnglishDeAccent : WordDeAccenter {
  override fun deAccent(anagram: String): String = anagram.toUpperCase()
}

object GreekDeAccent : WordDeAccenter {
  override fun deAccent(anagram: String): String {
    var normalizedAnagram = anagram
    normalizedAnagram = normalizedAnagram.replace('ΐ', 'Ι')
    normalizedAnagram = normalizedAnagram.replace('ΰ', 'Υ')
    normalizedAnagram = normalizedAnagram.toUpperCase()
    normalizedAnagram = normalizedAnagram.replace('Ά', 'Α')
    normalizedAnagram = normalizedAnagram.replace('Έ', 'Ε')
    normalizedAnagram = normalizedAnagram.replace('Ή', 'Η')
    normalizedAnagram = normalizedAnagram.replace('Ί', 'Ι')
    normalizedAnagram = normalizedAnagram.replace('Ό', 'Ο')
    normalizedAnagram = normalizedAnagram.replace('Ύ', 'Υ')
    normalizedAnagram = normalizedAnagram.replace('Ώ', 'Ω')
    return normalizedAnagram
  }
}

object FranceDeAccent : WordDeAccenter {
  override fun deAccent(anagram: String): String {
    var normalizedAnagram = anagram
    normalizedAnagram = normalizedAnagram.toLowerCase()
    normalizedAnagram = normalizedAnagram.replace('ī', 'I')
    normalizedAnagram = normalizedAnagram.replace('ï', 'I')
    normalizedAnagram = normalizedAnagram.replace('î', 'I')
    normalizedAnagram = normalizedAnagram.replace('ā', 'A')
    normalizedAnagram = normalizedAnagram.replace('â', 'A')
    normalizedAnagram = normalizedAnagram.replace('à', 'A')
    normalizedAnagram = normalizedAnagram.replace('č', 'C')
    normalizedAnagram = normalizedAnagram.replace('ç', 'C')
    normalizedAnagram = normalizedAnagram.replace('ź', 'Z')
    normalizedAnagram = normalizedAnagram.replace('ē', 'E')
    normalizedAnagram = normalizedAnagram.replace('è', 'E')
    normalizedAnagram = normalizedAnagram.replace('é', 'E')
    normalizedAnagram = normalizedAnagram.replace('ê', 'E')
    normalizedAnagram = normalizedAnagram.replace('ü', 'U')
    normalizedAnagram = normalizedAnagram.replace('ū', 'U')
    normalizedAnagram = normalizedAnagram.toUpperCase()
    normalizedAnagram = normalizedAnagram.replace('Ļ', 'L')
    normalizedAnagram = normalizedAnagram.replace('Ç', 'C')
    normalizedAnagram = normalizedAnagram.replace('Ó', 'O')
    normalizedAnagram = normalizedAnagram.replace('Ô', 'O')
    return normalizedAnagram
  }
}

object GermanDeAccent : WordDeAccenter {
  override fun deAccent(anagram: String): String {
    var normalizedAnagram = anagram
    normalizedAnagram = normalizedAnagram.toUpperCase()
    normalizedAnagram = normalizedAnagram.replace('Ä', 'A')
    normalizedAnagram = normalizedAnagram.replace('Ö', 'O')
    normalizedAnagram = normalizedAnagram.replace('Ü', 'U')
    return normalizedAnagram
  }
}

fun Dictionary.findDeAccenter(): WordDeAccenter = when (this) {
  GREEK -> GreekDeAccent
  FRANCE -> FranceDeAccent
  GERMAN -> GermanDeAccent
  ENGLISH -> EnglishDeAccent
}
