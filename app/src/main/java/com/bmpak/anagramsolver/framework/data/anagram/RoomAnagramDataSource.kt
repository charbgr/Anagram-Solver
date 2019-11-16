package com.bmpak.anagramsolver.framework.data.anagram

import com.bmpak.anagramsolver.model.Dictionary
import io.reactivex.Single

class RoomAnagramDataSource(private val roomDb: AnagramDatabase) : AnagramDataSource {

  override fun fetch(query: CharSequence, dictionary: Dictionary): Single<List<AnagramEntity>> {
    return roomDb.anagramEntityDao().fetch(query.toString(), dictionary.toString())
  }

  override fun put(anagramEntity: AnagramEntity) {
    roomDb.anagramEntityDao().put(anagramEntity)
  }
}
