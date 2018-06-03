package com.bmpak.anagramsolver.framework.repository.dictionary

import com.bmpak.anagramsolver.model.Dictionary
import com.bmpak.anagramsolver.model.DownloadStatus
import kotlinx.coroutines.experimental.channels.ReceiveChannel

interface FetchDictionaryRepository {
  fun fetch(dictionary: Dictionary): ReceiveChannel<DownloadStatus>
}
