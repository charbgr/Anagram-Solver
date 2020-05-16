package com.bmpak.anagramsolver.framework.repository.dictionary

import com.bmpak.anagramsolver.model.Dictionary
import com.bmpak.anagramsolver.model.DownloadStatus
import io.reactivex.rxjava3.core.BackpressureStrategy.LATEST
import io.reactivex.rxjava3.subjects.PublishSubject

class MockMultipleFetchDictionaryRepository(
    vararg dictionaries: Dictionary
) : FetchDictionaryRepository {

  private val fetchPerDictionaryPubSub = mutableMapOf<Dictionary, PublishSubject<DownloadStatus>>()

  init {
    dictionaries.forEach { fetchPerDictionaryPubSub[it] = PublishSubject.create() }
  }

  override fun fetch(dictionary: Dictionary) =
      fetchPerDictionaryPubSub[dictionary]!!.toFlowable(LATEST)

  fun fireEvent(dictionary: Dictionary, downloadStatus: DownloadStatus) {
    fetchPerDictionaryPubSub[dictionary]!!.onNext(downloadStatus)
  }
}

