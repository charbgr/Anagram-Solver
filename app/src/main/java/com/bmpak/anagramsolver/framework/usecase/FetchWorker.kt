package com.bmpak.anagramsolver.framework.usecase

import androidx.work.Worker
import com.bmpak.anagramsolver.framework.repository.dictionary.FirebaseDictionaryRepository
import com.bmpak.anagramsolver.model.Dictionary
import com.bmpak.anagramsolver.utils.Either.Left
import com.bmpak.anagramsolver.utils.Either.Right
import kotlinx.coroutines.experimental.runBlocking
import timber.log.Timber

class FetchWorker : Worker() {
  override fun doWork(): Worker.Result {
    Timber.d("Work : Start")
    val either = runBlocking {
      FetchDictionaryUseCase(FirebaseDictionaryRepository).run(Dictionary.GREEK)
    }
    if (either is Right) {
      return Worker.Result.FAILURE
    }
    either as Left

    runBlocking {
      for (item in either.value) {
        Timber.d("Work : $item")
      }
    }

    Timber.d("Work : Return")
    return Worker.Result.SUCCESS
  }
}
