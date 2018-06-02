package com.bmpak.anagramsolver.model

sealed class Dictionary {
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
