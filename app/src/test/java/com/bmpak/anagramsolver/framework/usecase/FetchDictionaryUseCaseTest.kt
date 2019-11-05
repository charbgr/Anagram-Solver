package com.bmpak.anagramsolver.framework.usecase

import com.bmpak.anagramsolver.UnitTest
import com.bmpak.anagramsolver.framework.repository.dictionary.FetchDictionaryRepository
import com.bmpak.anagramsolver.framework.repository.dictionary.MockFetchDictionaryRepository
import com.bmpak.anagramsolver.framework.repository.dictionary.MockMultipleFetchDictionaryRepository
import com.bmpak.anagramsolver.model.Dictionary.ENGLISH
import com.bmpak.anagramsolver.model.Dictionary.GERMAN
import com.bmpak.anagramsolver.model.Dictionary.GREEK
import com.bmpak.anagramsolver.model.DownloadStatus.Downloading
import com.bmpak.anagramsolver.model.DownloadStatus.Pause
import com.bmpak.anagramsolver.model.DownloadStatus.Success
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.io.File

class FetchDictionaryUseCaseTest : UnitTest() {

  @Test
  fun test_fetching_single_dictionary() {
    val repository = MockFetchDictionaryRepository(Downloading(0, 10))
    val useCase = useCase(repository = repository)

    useCase
        .build(GREEK)
        .test()
        .apply {
          assertThat(values()).hasSize(1)
          assertThat(values().first()).isEqualTo(FetchResult(GREEK, Downloading(0, 10)))

          repository.fireEvent(Downloading(5, 10))

          assertThat(isCancelled).isFalse()
          assertThat(isTerminated).isFalse()
          assertThat(isDisposed).isFalse()
          assertThat(values()).hasSize(2)
          assertThat(values()[1]).isEqualTo(FetchResult(GREEK, Downloading(5, 10)))
        }
  }

  @Test
  fun test_fetching_multiple_dictionaries() {
    val repository = MockMultipleFetchDictionaryRepository(GREEK, ENGLISH, GERMAN)
    val useCase = useCase(repository = repository)

    useCase
        .build(listOf(GREEK, ENGLISH, GERMAN))
        .test()
        .apply {
          assertThat(values()).isNotEmpty()
          assertThat(values().first()).isEqualTo(MultipleFetchResult.INITIAL)

          assertThat(isCancelled).isFalse()
          assertThat(isTerminated).isFalse()
          assertThat(isDisposed).isFalse()

          val firstMultiFetchResult = MultipleFetchResult(mapOf(GREEK to Downloading(5, 10)))
          repository.fireEvent(GREEK, Downloading(5, 10))
          assertThat(values()).hasSize(2)
          assertThat(values()[1]).isEqualTo(firstMultiFetchResult)
          assertThat(firstMultiFetchResult.areAllDictionariesFinished).isFalse()

          repository.fireEvent(ENGLISH, Downloading(7, 10))
          repository.fireEvent(GERMAN, Pause)

          assertThat(values()).hasSize(4)

          val preLastMultipleFetchResult = MultipleFetchResult(mapOf(
              GREEK to Downloading(5, 10),
              ENGLISH to Downloading(7, 10),
              GERMAN to Pause
          ))
          assertThat(values()[3]).isEqualTo(preLastMultipleFetchResult)
          assertThat(preLastMultipleFetchResult.areAllDictionariesFinished).isFalse()
        }
  }

  @Test
  fun test_fetching_multiple_dictionaries_are_all_finished() {
    val repository = MockMultipleFetchDictionaryRepository(GREEK, ENGLISH, GERMAN)
    val useCase = useCase(repository = repository)

    useCase
        .build(listOf(GREEK, ENGLISH, GERMAN))
        .test()
        .apply {
          assertThat(values()).isNotEmpty()
          assertThat(values().first()).isEqualTo(MultipleFetchResult.INITIAL)
          assertThat(MultipleFetchResult.INITIAL.areAllDictionariesFinished).isFalse()

          val tempFile = File.createTempFile("foo", "bar")
          repository.fireEvent(GREEK, Success(tempFile))
          repository.fireEvent(ENGLISH, Success(tempFile))
          repository.fireEvent(GERMAN, Success(tempFile))

          val multipleFetchResult = MultipleFetchResult(mapOf(
              GREEK to Success(tempFile),
              ENGLISH to Success(tempFile),
              GERMAN to Success(tempFile)
          ))
          assertThat(values()[3]).isEqualTo(multipleFetchResult)
          assertThat(multipleFetchResult.areAllDictionariesFinished).isTrue()

          tempFile.delete()
        }
  }

  private fun useCase(
      repository: FetchDictionaryRepository = MockFetchDictionaryRepository(Downloading(0, 0))
  ) = FetchDictionaryUseCase(
      repository = repository,
      schedulerProvider = NOW_SCHEDULER_PROVIDER
  )
}
