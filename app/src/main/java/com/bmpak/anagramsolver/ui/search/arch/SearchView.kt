package com.bmpak.anagramsolver.ui.search.arch

import com.bmpak.anagramsolver.framework.arch.View

interface SearchView: View {
  fun bind(viewModel: SearchViewModel)
}
