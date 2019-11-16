package com.bmpak.anagramsolver.framework.repository.dictionary

import com.bmpak.anagramsolver.model.Dictionary
import com.bmpak.anagramsolver.model.DownloadStatus
import io.reactivex.BackpressureStrategy.LATEST
import io.reactivex.subjects.BehaviorSubject

class MockFetchDictionaryRepository(initialDownloadStatus: DownloadStatus) : FetchDictionaryRepository {

  private val fetchDictionaryPubSub = BehaviorSubject.createDefault(initialDownloadStatus)

  override fun fetch(dictionary: Dictionary) = fetchDictionaryPubSub.toFlowable(LATEST)

  fun fireEvent(downloadStatus: DownloadStatus) {
    fetchDictionaryPubSub.onNext(downloadStatus)
  }
}

