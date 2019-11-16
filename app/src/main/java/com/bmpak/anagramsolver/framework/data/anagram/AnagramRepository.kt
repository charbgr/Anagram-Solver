package com.bmpak.anagramsolver.framework.data.anagram

import com.bmpak.anagramsolver.model.Anagram
import com.bmpak.anagramsolver.model.Dictionary
import com.bmpak.anagramsolver.utils.deAccent
import com.bmpak.anagramsolver.utils.quarable
import io.reactivex.Single

class AnagramRepository(
    private val dataSource: AnagramDataSource,
    private val mapper: AnagramEntityMapper
) {

  fun fetchAnagrams(query: CharSequence): Single<List<Anagram>> =
      dataSource.fetch(query).map { mapper.transform(it) }

  fun put(wordOrigin: String, dictionary: Dictionary) {
    // investigate if I need to do this transformation here or in InstallDictionaryUseCase.install
    val entity = AnagramEntity(
        quarable = wordOrigin.deAccent(dictionary).quarable(),
        wordOrigin = wordOrigin,
        dictionary = dictionary
    )
    dataSource.put(entity)
  }
}
