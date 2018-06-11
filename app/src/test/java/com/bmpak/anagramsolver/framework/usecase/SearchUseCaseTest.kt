package com.bmpak.anagramsolver.framework.usecase

import com.bmpak.anagramsolver.Boom
import com.bmpak.anagramsolver.UnitTest
import com.bmpak.anagramsolver.framework.repository.anagram.MockAnagramRepository
import com.bmpak.anagramsolver.utils.Either.Left
import com.bmpak.anagramsolver.utils.Either.Right
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class SearchUseCaseTest : UnitTest() {

  @Test
  fun test_fetching_anagram() {
    val useCase = useCase(MockAnagramRepository().fetchSuccess(listOf("foo", "ofo", "oof")))

    var isExecuted = false

    useCase.execute("foo") { res ->
      isExecuted = true
      assertThat(res.isLeft).isTrue()
      assertThat(res.isRight).isFalse()

      res as Left<List<String>>
      assertThat(res.value).containsAllOf("foo", "ofo", "oof")
    }

    assertThat(isExecuted).isTrue()
  }

  @Test
  fun test_fail_on_fetching_anagram() {
    val useCase = useCase(MockAnagramRepository().fetchFailed(Boom))

    var isExecuted = false

    useCase.execute("foo") { res ->
      isExecuted = true
      assertThat(res.isLeft).isFalse()
      assertThat(res.isRight).isTrue()

      res as Right<Throwable>
      assertThat(res.value).isEqualTo(Boom)
    }

    assertThat(isExecuted).isTrue()
  }


  private fun useCase(
      repository: MockAnagramRepository
  ): SearchUseCase = SearchUseCase(repository, NOW_CO_PROVIDER)
}