package com.bmpak.anagramsolver.ui.search.arch.repository

import com.bmpak.anagramsolver.utils.Either
import java.util.Random
import java.util.UUID

object MockAnagramRepository : AnagramRepository {
  override suspend fun fetch(query: CharSequence): Either<List<String>, Throwable> {
    if (query.isEmpty()) {
      return Either.Left(emptyList())
    }
    val anagrams = mutableListOf<String>()
    for (i in 0..((0..10).random())) {
      val randomText = UUID.randomUUID().toString()
      anagrams.add(randomText.substring(0, query.length))
    }

    return Either.Left(anagrams)
  }
}

private fun ClosedRange<Int>.random() =
    Random().nextInt(endInclusive - start) + start
