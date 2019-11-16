package com.bmpak.anagramsolver.framework.data.anagram

import com.bmpak.anagramsolver.model.Dictionary
import io.reactivex.Single
import java.util.Random
import java.util.UUID

object RandomTextAnagramDataSource : AnagramDataSource {

  override fun fetch(query: CharSequence, dictionary: Dictionary): Single<List<AnagramEntity>> {
    if (query.isEmpty()) {
      return Single.just(emptyList())
    }
    val anagrams = mutableListOf<AnagramEntity>()
    for (i in 0..((0..10).random())) {
      val randomText = UUID.randomUUID().toString()
      anagrams.add(AnagramEntity("", randomText.substring(0, query.length), "Greek"))
    }

    return Single.just(anagrams)
  }

  override fun put(anagramEntity: AnagramEntity) {
  }
}

private fun ClosedRange<Int>.random() =
    Random().nextInt(endInclusive - start) + start
