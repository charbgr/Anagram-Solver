package com.bmpak.anagramsolver.framework.repository.anagram

import io.reactivex.Single

interface AnagramRepository {
  fun fetch(query: CharSequence): Single<List<String>>
}
