package com.bmpak.anagramsolver.ui.search.arch

import com.bmpak.anagramsolver.framework.arch.Presenter
import com.bmpak.anagramsolver.framework.usecase.SearchUseCase
import com.bmpak.anagramsolver.ui.search.adapter.ActualAnagramItem
import com.bmpak.anagramsolver.ui.search.adapter.AnagramItem
import timber.log.Timber

class SearchPresenter(
    private val searchUseCase: SearchUseCase
) : Presenter<SearchView>() {

  fun search(query: CharSequence) {
    searchUseCase.execute(query, {
      it.either({
        val anagrams: List<AnagramItem> = it.map { ActualAnagramItem(it) }
        viewWRef.get()?.bind(SearchViewModel(anagrams, SearchResult.of(anagrams)))
      }, {
        Timber.e(it)
        viewWRef.get()?.bind(SearchViewModel(emptyList(), SearchResult.NOT_FOUND))
      })
    })
  }
}
