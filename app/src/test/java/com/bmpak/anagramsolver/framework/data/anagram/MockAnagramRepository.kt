package com.bmpak.anagramsolver.framework.data.anagram

import com.bmpak.anagramsolver.Boom
import com.bmpak.anagramsolver.utils.Either
import io.reactivex.Single

class MockAnagramRepository : AnagramDataSource {

  private var fetchPayload: Either<List<String>, Throwable> = Either.Right(Boom)

  fun fetchSuccess(anagrams: List<String>) = apply {
    this.fetchPayload = Either.Left(anagrams)
  }

  fun fetchFailed(throwable: Throwable = Boom) = apply {
    this.fetchPayload = Either.Right(throwable)
  }

  override fun fetch(query: CharSequence): Single<List<String>> {
    val safePayload = fetchPayload
    return when (safePayload) {
      is Either.Left -> Single.just(safePayload.value)
      is Either.Right -> Single.error(safePayload.value)
    }
  }
}
