package com.bmpak.anagramsolver.model

sealed class Dictionary {

  companion object {
    val values by lazy {
      listOf(ENGLISH, GREEK, FRANCE, GERMAN)
    }
  }

  object ENGLISH : Dictionary() {
    override fun toString(): String = "English"
  }

  object GREEK : Dictionary() {
    override fun toString(): String = "Greek"
  }

  object FRANCE : Dictionary() {
    override fun toString(): String = "France"
  }

  object GERMAN : Dictionary() {
    override fun toString(): String = "German"
  }
}
