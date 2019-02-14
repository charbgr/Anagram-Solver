package com.bmpak.anagramsolver.framework.usecase

import androidx.work.Worker
import com.bmpak.anagramsolver.framework.repository.dictionary.FirebaseDictionaryRepository
import com.bmpak.anagramsolver.model.Dictionary
import timber.log.Timber

class FetchWorker : Worker() {
  override fun doWork(): Worker.Result {
    Timber.d("FetchWorker : Start")
    val flowable = FetchDictionaryUseCase(FirebaseDictionaryRepository).build(Dictionary.GREEK)
    flowable.blockingForEach { downloadStatus ->
      Timber.d("DownloadStatus : $downloadStatus")
    }
    Timber.d("FetchWorker : Return")
    return Worker.Result.SUCCESS
  }
}
