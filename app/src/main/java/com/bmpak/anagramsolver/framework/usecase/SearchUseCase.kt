package com.bmpak.anagramsolver.framework.usecase

import com.bmpak.anagramsolver.framework.arch.RealSchedulerProvider
import com.bmpak.anagramsolver.framework.arch.SchedulerProvider
import com.bmpak.anagramsolver.framework.arch.SingleUseCase
import com.bmpak.anagramsolver.framework.repository.anagram.AnagramRepository
import io.reactivex.rxjava3.core.Single

class SearchUseCase(
  private val repository: AnagramRepository,
  schedulerProvider: SchedulerProvider = RealSchedulerProvider
) : SingleUseCase<List<String>, CharSequence>(schedulerProvider) {

  @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
  override fun build(query: CharSequence): Single<List<String>> {
    val queryChars = query.toString().toCharArray()
    val safeQuery = queryChars.sortedArray().joinToString("")
    return repository.fetch(safeQuery)
      .subscribeOn(schedulerProvider.io)
      .observeOn(schedulerProvider.ui)
  }
}
