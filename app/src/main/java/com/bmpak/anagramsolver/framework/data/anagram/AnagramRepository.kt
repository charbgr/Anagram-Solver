package com.bmpak.anagramsolver.framework.data.anagram

import com.bmpak.anagramsolver.model.Anagram
import com.bmpak.anagramsolver.model.Dictionary
import com.bmpak.anagramsolver.utils.deAccent
import com.bmpak.anagramsolver.utils.quarable
import io.reactivex.Single
import timber.log.Timber

class AnagramRepository(
    private val dataSource: AnagramDataSource,
    private val mapper: AnagramEntityMapper
) {

  fun fetchAnagrams(query: CharSequence, dictionary: Dictionary): Single<List<Anagram>> {
    Timber.d("Fetching anagram for : $query")
    return dataSource.fetch(
        query = query.toString().deAccent(dictionary).quarable(),
        dictionary = dictionary
    ).map { mapper.transform(it) }
  }


  fun put(wordOrigin: String, dictionary: Dictionary) {
    // investigate if I need to do this transformation here or in InstallDictionaryUseCase.install
    val entity = AnagramEntity(
        quarable = wordOrigin.deAccent(dictionary).quarable(),
        wordOrigin = wordOrigin,
        dictionary = dictionary.toString()
    )
    dataSource.put(entity)
  }
}
