package com.bmpak.anagramsolver.framework.data.anagram

import com.bmpak.anagramsolver.model.Dictionary
import io.reactivex.Single

interface AnagramDataSource {
  fun fetch(query: CharSequence, dictionary: Dictionary): Single<List<AnagramEntity>>
  fun put(anagramEntity: AnagramEntity)
}
