package com.bmpak.anagramsolver.framework.usecase

import com.bmpak.anagramsolver.Boom
import com.bmpak.anagramsolver.UnitTest
import com.bmpak.anagramsolver.framework.repository.anagram.MockAnagramRepository
import org.junit.Test

class SearchUseCaseTest : UnitTest() {

  @Test
  fun test_fetching_anagram() {
    val useCase = useCase(MockAnagramRepository().fetchSuccess(listOf("foo", "ofo", "oof")))
    val testObserver = useCase.build("foo").test()

    testObserver.assertNoErrors()
    testObserver.assertValue(listOf("foo", "ofo", "oof"))
  }

  @Test
  fun test_fail_on_fetching_anagram() {
    val useCase = useCase(MockAnagramRepository().fetchFailed(Boom))
    val testObserver = useCase.build("foo").test()

    testObserver.assertNoValues()
    testObserver.assertError(Boom)
  }

  private fun useCase(
      repository: MockAnagramRepository
  ): SearchUseCase = SearchUseCase(repository, NOW_SCHEDULER_PROVIDER)
}
