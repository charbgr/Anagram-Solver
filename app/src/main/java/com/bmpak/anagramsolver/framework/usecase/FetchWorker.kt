package com.bmpak.anagramsolver.framework.usecase

import androidx.work.Worker
import androidx.work.Worker.WorkerResult.FAILURE
import com.bmpak.anagramsolver.framework.repository.dictionary.FirebaseDictionaryRepository
import com.bmpak.anagramsolver.model.Dictionary
import com.bmpak.anagramsolver.utils.Either.Left
import com.bmpak.anagramsolver.utils.Either.Right
import kotlinx.coroutines.experimental.runBlocking
import timber.log.Timber

class FetchWorker : Worker() {
  override fun doWork(): WorkerResult {
    Timber.d("Work : Start")
    val either = runBlocking {
      FetchDictionaryUseCase(FirebaseDictionaryRepository).run(Dictionary.GREEK)
    }
    if (either is Right) {
      return FAILURE
    }
    either as Left

    runBlocking {
      for (item in either.value) {
        Timber.d("Work : $item")
      }
    }

    Timber.d("Work : Return")
    return WorkerResult.SUCCESS
  }
}