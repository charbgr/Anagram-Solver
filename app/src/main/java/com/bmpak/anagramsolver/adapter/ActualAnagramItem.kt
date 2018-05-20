package com.bmpak.anagramsolver.adapter

import com.bmpak.anagramsolver.adapter.AnagramItem.ViewTypes.ACTUAL_ANAGRAM

data class ActualAnagramItem(val anagram: CharSequence) : AnagramItem {
  override fun getItemViewType(): Int = ACTUAL_ANAGRAM
}
