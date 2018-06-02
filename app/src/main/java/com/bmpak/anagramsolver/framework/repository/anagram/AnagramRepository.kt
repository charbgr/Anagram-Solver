package com.bmpak.anagramsolver.framework.repository.anagram

import com.bmpak.anagramsolver.utils.Either

interface AnagramRepository {
  suspend fun fetch(query: CharSequence): Either<List<String>, Throwable>
}
