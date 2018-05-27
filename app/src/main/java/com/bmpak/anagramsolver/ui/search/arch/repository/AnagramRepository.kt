package com.bmpak.anagramsolver.ui.search.arch.repository

import com.bmpak.anagramsolver.utils.Either

interface AnagramRepository {
  suspend fun fetch(query: CharSequence): Either<List<String>, Throwable>
}
