package com.bmpak.anagramsolver.framework.repository.dictionary

import com.bmpak.anagramsolver.model.Dictionary
import com.bmpak.anagramsolver.model.DownloadStatus
import io.reactivex.rxjava3.core.Flowable

interface FetchDictionaryRepository {
  fun fetch(dictionary: Dictionary): Flowable<DownloadStatus>
}
