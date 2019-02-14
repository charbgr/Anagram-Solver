package com.bmpak.anagramsolver.framework.usecase

import com.bmpak.anagramsolver.framework.arch.FlowableUseCase
import com.bmpak.anagramsolver.framework.arch.SchedulerProvider
import com.bmpak.anagramsolver.framework.repository.dictionary.FetchDictionaryRepository
import com.bmpak.anagramsolver.model.Dictionary
import com.bmpak.anagramsolver.model.DownloadStatus
import io.reactivex.Flowable

class FetchDictionaryUseCase(
    private val repository: FetchDictionaryRepository,
    schedulerProvider: SchedulerProvider = SchedulerProvider.Real
) : FlowableUseCase<DownloadStatus, Dictionary>(schedulerProvider) {

  override fun build(params: Dictionary): Flowable<DownloadStatus> {
    return repository.fetch(dictionary = params)
      .subscribeOn(schedulerProvider.io)
      .observeOn(schedulerProvider.ui)
      .share()
  }
}
