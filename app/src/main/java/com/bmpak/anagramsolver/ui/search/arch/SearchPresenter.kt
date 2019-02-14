package com.bmpak.anagramsolver.ui.search.arch

import com.bmpak.anagramsolver.framework.arch.Presenter
import com.bmpak.anagramsolver.framework.usecase.SearchUseCase
import com.bmpak.anagramsolver.ui.search.adapter.ActualAnagramItem
import com.bmpak.anagramsolver.ui.search.adapter.AnagramItem
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.rxkotlin.plusAssign
import timber.log.Timber

class SearchPresenter(
  private val searchUseCase: SearchUseCase
) : Presenter<SearchView>() {

  fun search(query: CharSequence) {
    disposables += searchUseCase
      .build(query)
      .subscribeWith(object : DisposableSingleObserver<List<String>>() {
        override fun onSuccess(anagrams: List<String>) {
          val anagramsItems: List<AnagramItem> = anagrams.map { ActualAnagramItem(it) }
          viewWRef.get()?.bind(SearchViewModel(anagramsItems, SearchResult.of(anagramsItems)))
        }

        override fun onError(e: Throwable) {
          Timber.e(e)
          viewWRef.get()?.bind(SearchViewModel(emptyList(), SearchResult.NOT_FOUND))
        }
      })

  }
}
