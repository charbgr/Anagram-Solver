package com.bmpak.anagramsolver.framework.usecase

import com.bmpak.anagramsolver.framework.arch.CoroutineContextProvider
import com.bmpak.anagramsolver.framework.repository.anagram.AnagramRepository
import com.bmpak.anagramsolver.utils.Either

class SearchUseCase(
    private val repository: AnagramRepository,
    coContextProvider: CoroutineContextProvider = CoroutineContextProvider.Real
) : UseCase<List<String>, CharSequence>(coContextProvider) {

  @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
  override suspend fun run(query: CharSequence): Either<List<String>, Throwable> {
    val queryChars = query.toString().toCharArray()
    val safeQuery = queryChars.sortedArray().joinToString("")
    return repository.fetch(safeQuery)
  }

}
