package com.bmpak.anagramsolver.framework.repository.dictionary

import com.bmpak.anagramsolver.model.Dictionary
import com.bmpak.anagramsolver.model.DownloadStatus
import io.reactivex.Flowable

class MockFetchDictionaryRepository(
  private val downloadStatus: DownloadStatus = DownloadStatus.Pause
) : FetchDictionaryRepository {

  override fun fetch(dictionary: Dictionary) = Flowable.just(downloadStatus)
}

