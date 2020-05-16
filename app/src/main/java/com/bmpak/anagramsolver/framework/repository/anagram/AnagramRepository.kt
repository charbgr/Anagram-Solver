package com.bmpak.anagramsolver.framework.repository.anagram

import io.reactivex.rxjava3.core.Single

interface AnagramRepository {
  fun fetch(query: CharSequence): Single<List<String>>
}
