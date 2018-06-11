package com.bmpak.anagramsolver.framework.repository.anagram

import com.bmpak.anagramsolver.Boom
import com.bmpak.anagramsolver.utils.Either

class MockAnagramRepository : AnagramRepository {

  private var fetchPayload: Either<List<String>, Throwable> = Either.Right(Boom)

  fun fetchSuccess(anagrams: List<String>) = apply {
    this.fetchPayload = Either.Left(anagrams)
  }

  fun fetchFailed(throwable: Throwable = Boom) = apply {
    this.fetchPayload = Either.Right(throwable)
  }

  override suspend fun fetch(query: CharSequence) = fetchPayload
}