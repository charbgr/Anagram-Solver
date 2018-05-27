package com.bmpak.anagramsolver.ui.search.arch

import com.bmpak.anagramsolver.ui.search.adapter.ActualAnagramItem
import com.bmpak.anagramsolver.ui.search.adapter.AnagramItem

sealed class SearchResult {
  object NOT_FOUND : SearchResult()
  class FOUND(val anagrams: List<ActualAnagramItem>) : SearchResult()

  companion object {
    fun of(anagramItems: List<AnagramItem>): SearchResult {
      val actualAnagrams = anagramItems.filterIsInstance(ActualAnagramItem::class.java)
      if (actualAnagrams.isEmpty()) {
        return NOT_FOUND
      }

      return FOUND(actualAnagrams)
    }
  }
}
