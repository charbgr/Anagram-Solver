package com.bmpak.anagramsolver.model

sealed class Dictionary {
  object ENGLISH : Dictionary()
  object GREEK : Dictionary()
  object FRANCE: Dictionary()
  object GERMAN: Dictionary()
}
