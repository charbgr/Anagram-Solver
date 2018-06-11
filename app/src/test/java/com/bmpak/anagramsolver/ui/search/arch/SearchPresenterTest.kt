package com.bmpak.anagramsolver.ui.search.arch

import com.bmpak.anagramsolver.UnitTest
import com.bmpak.anagramsolver.framework.repository.anagram.AnagramRepository
import com.bmpak.anagramsolver.framework.repository.anagram.MockAnagramRepository
import com.bmpak.anagramsolver.framework.usecase.SearchUseCase
import com.bmpak.anagramsolver.ui.search.adapter.ActualAnagramItem
import org.junit.Test

class SearchPresenterTest : UnitTest() {

  @Test
  fun test_anagrams_found() {
    val view = MockSearchView()
    val presenter = presenter(MockAnagramRepository().fetchSuccess(listOf("foo")))
    presenter.init(view)

    presenter.search("ofo")

    val anagrams = listOf(ActualAnagramItem("foo"))
    view.bindTapes.assertRenders(
        SearchViewModel(anagrams, SearchResult.FOUND(anagrams))
    )
  }

  @Test
  fun test_anagrams_not_found_() {
    val view = MockSearchView()
    val presenter = presenter(MockAnagramRepository().fetchSuccess(emptyList()))
    presenter.init(view)

    presenter.search("ofo")

    view.bindTapes.assertRenders(
        SearchViewModel(emptyList(), SearchResult.NOT_FOUND)
    )
  }

  @Test
  fun test_failed_to_find_anagrams() {
    val view = MockSearchView()
    val presenter = presenter(MockAnagramRepository().fetchFailed())
    presenter.init(view)

    presenter.search("ofo")

    view.bindTapes.assertRenders(
        SearchViewModel(emptyList(), SearchResult.NOT_FOUND)
    )
  }

  private fun presenter(
      repository: AnagramRepository
  ): SearchPresenter = SearchPresenter(SearchUseCase(repository, NOW_CO_PROVIDER))
}