package com.bmpak.anagramsolver.framework.repository.anagram

import io.reactivex.Single
import java.util.*

object MockAnagramRepository : AnagramRepository {

  override fun fetch(query: CharSequence): Single<List<String>> {
    if (query.isEmpty()) {
      return Single.just(emptyList())
    }
    val anagrams = mutableListOf<String>()
    for (i in 0..((0..10).random())) {
      val randomText = UUID.randomUUID().toString()
      anagrams.add(randomText.substring(0, query.length))
    }

    return Single.just(anagrams)
  }
}

private fun ClosedRange<Int>.random() =
    Random().nextInt(endInclusive - start) + start
