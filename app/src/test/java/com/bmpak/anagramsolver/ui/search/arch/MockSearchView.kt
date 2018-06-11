package com.bmpak.anagramsolver.ui.search.arch

import com.bmpak.anagramsolver.Tape

class MockSearchView : SearchView {

  var bindTapes = Tape.create<SearchViewModel>()

  override fun bind(viewModel: SearchViewModel) = bindTapes.add(viewModel)
}