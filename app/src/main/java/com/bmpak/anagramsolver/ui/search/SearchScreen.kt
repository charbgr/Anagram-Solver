package com.bmpak.anagramsolver.ui.search

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.bmpak.anagramsolver.R.id
import com.bmpak.anagramsolver.R.layout
import com.bmpak.anagramsolver.framework.data.anagram.AnagramDatabase
import com.bmpak.anagramsolver.framework.data.anagram.AnagramRepository
import com.bmpak.anagramsolver.framework.data.anagram.RealAnagramEntityMapper
import com.bmpak.anagramsolver.framework.data.anagram.RoomAnagramDataSource
import com.bmpak.anagramsolver.framework.usecase.SearchUseCase
import com.bmpak.anagramsolver.ui.search.adapter.AnagramAdapter
import com.bmpak.anagramsolver.ui.search.arch.SearchPresenter
import com.bmpak.anagramsolver.ui.search.arch.SearchView
import com.bmpak.anagramsolver.ui.search.arch.SearchViewModel
import com.bmpak.anagramsolver.utils.disableNumberAndSpaceInput
import com.bmpak.anagramsolver.utils.onTextChanged
import kotlin.properties.Delegates


class SearchScreen : AppCompatActivity(), SearchView {

  private lateinit var searchBar: EditText
  private lateinit var resultsTitle: TextView
  private lateinit var resultsList: RecyclerView
  private val resultsAdapter: AnagramAdapter = AnagramAdapter()

  private var presenter: SearchPresenter by Delegates.notNull()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.screen_main)

    findViews()
    setUpSearchBar()
    setUpSearchList()
    setUpPresenter()
  }

  override fun onResume() {
    presenter.init(this)
    super.onResume()
  }

  override fun onStop() {
    presenter.destroy()
    super.onStop()
  }

  private fun findViews() {
    searchBar = findViewById(id.searchbar)
    resultsTitle = findViewById(id.results_found_words_title)
    resultsList = findViewById(id.results_found_words_list)
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
    val db = Room.databaseBuilder(this, AnagramDatabase::class.java, "anagram-db").build()
    val repository = AnagramRepository(RoomAnagramDataSource(db), RealAnagramEntityMapper)
    presenter = SearchPresenter(SearchUseCase(repository))
  }

  override fun bind(viewModel: SearchViewModel) {
    resultsTitle.text = viewModel.resultTitle(this)
    resultsTitle.setTextColor(viewModel.resultTitleColor(this))
    resultsAdapter.setItems(viewModel.anagramItems)
  }
}
