package com.bmpak.anagramsolver.framework.data.anagram

import io.reactivex.Single

interface AnagramDataSource {
  fun fetch(query: CharSequence): Single<List<AnagramEntity>>
}
