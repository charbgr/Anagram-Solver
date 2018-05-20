package com.bmpak.anagramsolver

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bmpak.anagramsolver.adapter.ActualAnagramItem
import com.bmpak.anagramsolver.adapter.AnagramAdapter
import com.bmpak.anagramsolver.utils.disableNumberAndSpaceInput


class MainActivity : AppCompatActivity() {

  private lateinit var searchBar: EditText
  private lateinit var resultsTitle: TextView
  private lateinit var resultsList: RecyclerView
  private val resultsAdapter: AnagramAdapter = AnagramAdapter()


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    findViews()
    setUpSearchBar()
    setUpSearchList()

    resultsTitle.setOnLongClickListener {
      resultsTitle.text = "No words found"
      resultsTitle.setTextColor(ContextCompat.getColor(this, R.color.red))
      true
    }
  }

  private fun findViews() {
    searchBar = findViewById(R.id.searchbar)
    resultsTitle = findViewById(R.id.results_found_words_title)
    resultsList = findViewById(R.id.results_found_words_list)
  }

  private fun setUpSearchBar() {
    searchBar.disableNumberAndSpaceInput()
  }

  private fun setUpSearchList() {
    val layoutManager = LinearLayoutManager(this)
    resultsList.layoutManager = layoutManager
    resultsList.adapter = resultsAdapter

    for (i in 0..50) {
      resultsAdapter.addItems(listOf(ActualAnagramItem("Anagram #$i")))
    }

    resultsTitle.text = "Found 50 words"
  }
}
