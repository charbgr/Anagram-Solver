package com.bmpak.anagramsolver.ui.search.adapter

import com.bmpak.anagramsolver.ui.search.adapter.AnagramItem.ViewTypes.ACTUAL_ANAGRAM

data class ActualAnagramItem(val anagram: CharSequence) : AnagramItem {
  override fun getItemViewType(): Int = ACTUAL_ANAGRAM
}
