package com.bmpak.anagramsolver.framework.data.anagram

import com.bmpak.anagramsolver.model.Anagram
import io.reactivex.Single

class AnagramRepository(
    private val dataSource: AnagramDataSource,
    private val mapper: AnagramEntityMapper
) {

  fun fetchAnagrams(query: CharSequence): Single<List<Anagram>> =
      dataSource.fetch(query).map { mapper.transform(it) }
}
