package com.bmpak.anagramsolver.ui.search.adapter

import com.bmpak.anagramsolver.model.Anagram
import com.bmpak.anagramsolver.ui.search.adapter.AnagramItem.ViewTypes.ACTUAL_ANAGRAM

data class ActualAnagramItem(val anagram: Anagram) : AnagramItem {
  override fun getItemViewType(): Int = ACTUAL_ANAGRAM
}
