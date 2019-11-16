package com.bmpak.anagramsolver.model

sealed class Dictionary {

  companion object {
    val values by lazy {
      listOf(ENGLISH, GREEK, FRANCE, GERMAN)
    }

    fun from(value: String): Dictionary = when(value) {
      "English" -> ENGLISH
      "Greek" -> GREEK
      "France" -> FRANCE
      "German" -> GERMAN
      else -> throw IllegalArgumentException("Maybe you missed the case --> $value on when expression")
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
