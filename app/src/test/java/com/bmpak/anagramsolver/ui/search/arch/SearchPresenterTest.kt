package com.bmpak.anagramsolver.ui.search.arch

import com.bmpak.anagramsolver.UnitTest
import com.bmpak.anagramsolver.framework.data.anagram.AnagramDataSource
import com.bmpak.anagramsolver.framework.data.anagram.MockAnagramRepository
import com.bmpak.anagramsolver.framework.usecase.SearchUseCase
import org.junit.Test

class SearchPresenterTest : UnitTest() {

  private val robot = SearchRobot()

  @Test
  fun test_anagrams_found() {
    val repo = MockAnagramRepository().fetchSuccess(listOf("foo"))
//    val presenter = presenter(repo)
//    presenter.init(view)
//
//    presenter.search("ofo")
//
//    val anagrams = listOf(ActualAnagramItem("foo"))
//    view.bindTapes.assertRenders(
//        SearchViewModel(anagrams, SearchResult.FOUND(anagrams))
//    )
  }

  @Test
  fun test_anagrams_not_found_() {
    val presenter = presenter(MockAnagramRepository().fetchSuccess(emptyList()))
    presenter.init(robot.view)

    presenter.search("ofo")

    robot.assertBindViewModel(SearchViewModel(emptyList(), SearchResult.NOT_FOUND))
  }

  @Test
  fun test_failed_to_find_anagrams() {
    val presenter = presenter(MockAnagramRepository().fetchFailed())
    presenter.init(robot.view)

    presenter.search("ofo")

    robot.assertBindViewModel(SearchViewModel(emptyList(), SearchResult.NOT_FOUND))
  }

  private fun presenter(
      repository: AnagramDataSource
  ): SearchPresenter = SearchPresenter(SearchUseCase(repository, NOW_SCHEDULER_PROVIDER))
}
