package com.bmpak.anagramsolver.ui.search.arch

import android.content.Context
import androidx.core.content.ContextCompat
import com.bmpak.anagramsolver.R
import com.bmpak.anagramsolver.ui.search.adapter.AnagramItem
import com.bmpak.anagramsolver.ui.search.arch.SearchResult.FOUND
import com.bmpak.anagramsolver.ui.search.arch.SearchResult.NOT_FOUND

data class SearchViewModel(
    val anagramItems: List<AnagramItem>,
    private val searchResult: SearchResult
) {

  fun resultTitle(context: Context): CharSequence = when (searchResult) {
    is FOUND -> {
      val size = searchResult.anagrams.size
      context.resources.getQuantityString(R.plurals.words_found, size, size)
    }
    NOT_FOUND -> context.getString(R.string.no_words_found)
  }

  fun resultTitleColor(context: Context): Int = when (searchResult) {
    is FOUND -> ContextCompat.getColor(context, R.color.chambray)
    NOT_FOUND -> ContextCompat.getColor(context, R.color.red)
  }
}
