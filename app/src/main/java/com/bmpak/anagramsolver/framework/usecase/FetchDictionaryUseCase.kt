package com.bmpak.anagramsolver.framework.usecase

import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.bmpak.anagramsolver.framework.arch.CoroutineContextProvider
import com.bmpak.anagramsolver.framework.repository.dictionary.FetchDictionaryRepository
import com.bmpak.anagramsolver.model.Dictionary
import com.bmpak.anagramsolver.model.DownloadStatus
import com.bmpak.anagramsolver.utils.Either
import kotlinx.coroutines.experimental.channels.ReceiveChannel

class FetchDictionaryUseCase(
    private val repository: FetchDictionaryRepository,
    coContextProvider: CoroutineContextProvider = CoroutineContextProvider.Real
) : UseCase<ReceiveChannel<DownloadStatus>, Dictionary>(coContextProvider) {


  fun testScheduleFetch() {
    val workRequest = OneTimeWorkRequest.from(FetchWorker::class.java)
    WorkManager.getInstance()?.enqueue(workRequest)
  }

  @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
  override suspend fun run(
      dictionary: Dictionary
  ): Either<ReceiveChannel<DownloadStatus>, Throwable> {
    return try {
      val receiveChannel = repository.fetch(dictionary)
      Either.Left(receiveChannel)
    } catch (e: Exception) {
      Either.Right(e)
    }
  }
}
