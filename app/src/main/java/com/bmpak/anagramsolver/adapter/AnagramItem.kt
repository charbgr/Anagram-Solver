package com.bmpak.anagramsolver.adapter

import com.bmpak.anagramsolver.R

interface AnagramItem {
  companion object ViewTypes{
    val ACTUAL_ANAGRAM = R.layout.item_anagram_result
  }

  fun getItemViewType(): Int
}
