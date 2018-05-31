package com.bmpak.anagramsolver

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bmpak.anagramsolver.framework.navigator.RealNavigator
import com.bmpak.anagramsolver.ui.search.adapter.AnagramAdapter
import com.bmpak.anagramsolver.ui.search.arch.SearchPresenter
import com.bmpak.anagramsolver.ui.search.arch.SearchUseCase
import com.bmpak.anagramsolver.ui.search.arch.SearchView
import com.bmpak.anagramsolver.ui.search.arch.SearchViewModel
import com.bmpak.anagramsolver.ui.search.arch.repository.FirebaseRepository
import com.bmpak.anagramsolver.utils.disableNumberAndSpaceInput
import com.bmpak.anagramsolver.utils.onTextChanged
import kotlin.properties.Delegates


class MainActivity : AppCompatActivity(), SearchView {

  private lateinit var searchBar: EditText
  private lateinit var resultsTitle: TextView
  private lateinit var resultsList: RecyclerView
  private val resultsAdapter: AnagramAdapter = AnagramAdapter()

  private var presenter: SearchPresenter by Delegates.notNull()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    findViews()
    setUpSearchBar()
    setUpSearchList()
    setUpPresenter()

    RealNavigator(this).toOnboarding()
  }

  override fun onStop() {
    presenter.destroy()
    super.onStop()
  }

  private fun findViews() {
    searchBar = findViewById(R.id.searchbar)
    resultsTitle = findViewById(R.id.results_found_words_title)
    resultsList = findViewById(R.id.results_found_words_list)
  }

  private fun setUpSearchBar() {
    searchBar.disableNumberAndSpaceInput()
    searchBar.onTextChanged {
      presenter.search(it)
    }
  }

  private fun setUpSearchList() {
    val layoutManager = LinearLayoutManager(this)
    resultsList.layoutManager = layoutManager
    resultsList.adapter = resultsAdapter
  }

  private fun setUpPresenter() {
    presenter = SearchPresenter(SearchUseCase(FirebaseRepository))
    presenter.init(this)
  }

  override fun bind(viewModel: SearchViewModel) {
    resultsTitle.text = viewModel.resultTitle(this)
    resultsTitle.setTextColor(viewModel.resultTitleColor(this))
    resultsAdapter.setItems(viewModel.anagramItems)
  }
}
