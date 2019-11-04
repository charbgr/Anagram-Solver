package com.bmpak.anagramsolver.ui.search.arch

import io.mockk.spyk
import io.mockk.verify

class SearchRobot {

  val view = spyk<SearchView>()

  fun assertBindViewModel(viewModel: SearchViewModel) {
    verify { view.bind(viewModel) }
  }
}
