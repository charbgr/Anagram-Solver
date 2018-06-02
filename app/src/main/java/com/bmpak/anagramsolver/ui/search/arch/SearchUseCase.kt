package com.bmpak.anagramsolver.ui.search.arch

import com.bmpak.anagramsolver.framework.arch.UseCase
import com.bmpak.anagramsolver.framework.repository.anagram.AnagramRepository
import com.bmpak.anagramsolver.utils.Either

class SearchUseCase(
    private val repository: AnagramRepository
) : UseCase<List<String>, CharSequence>() {

  @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
  override suspend fun run(query: CharSequence): Either<List<String>, Throwable> {
    val queryChars = query.toString().toCharArray()
    val safeQuery = queryChars.sortedArray().joinToString("")
    return repository.fetch(safeQuery)
  }

}
