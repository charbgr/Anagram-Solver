package com.bmpak.anagramsolver.framework.usecase

import com.bmpak.anagramsolver.framework.repository.dictionary.FetchDictionaryRepository
import com.bmpak.anagramsolver.model.Dictionary
import com.bmpak.anagramsolver.model.DownloadStatus
import com.bmpak.anagramsolver.utils.Either
import kotlinx.coroutines.experimental.channels.ReceiveChannel

class FetchDictionaryUseCase(
    private val repository: FetchDictionaryRepository
) : UseCase<ReceiveChannel<DownloadStatus>, Dictionary>() {

  @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
  override suspend fun run(
      dictionary: Dictionary
  ): Either<ReceiveChannel<DownloadStatus>, Throwable> {
    val receiveChannel = repository.fetch(dictionary)
    return Either.Left(receiveChannel)
  }
}
