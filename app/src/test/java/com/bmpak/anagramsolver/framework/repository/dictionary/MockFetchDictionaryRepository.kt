package com.bmpak.anagramsolver.framework.repository.dictionary

import com.bmpak.anagramsolver.UnitTest
import com.bmpak.anagramsolver.model.Dictionary
import com.bmpak.anagramsolver.model.DownloadStatus
import kotlinx.coroutines.experimental.channels.RendezvousChannel
import kotlinx.coroutines.experimental.launch

class MockFetchDictionaryRepository : FetchDictionaryRepository {

  private var fetchReceiveChannel = RendezvousChannel<DownloadStatus>()

  override fun fetch(dictionary: Dictionary) = fetchReceiveChannel

  fun onFetch(downloadStatus: DownloadStatus) {
    launch(UnitTest.NOW) { fetchReceiveChannel.send(downloadStatus) }
  }
}

