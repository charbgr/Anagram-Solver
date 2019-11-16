package com.bmpak.anagramsolver.framework.usecase

import com.bmpak.anagramsolver.framework.arch.SchedulerProvider
import com.bmpak.anagramsolver.framework.arch.SingleUseCase
import com.bmpak.anagramsolver.framework.data.anagram.AnagramRepository
import com.bmpak.anagramsolver.model.Anagram
import com.bmpak.anagramsolver.utils.quarable
import io.reactivex.Single

class SearchUseCase(
    private val repository: AnagramRepository,
    schedulerProvider: SchedulerProvider = SchedulerProvider.Real
) : SingleUseCase<List<Anagram>, CharSequence>(schedulerProvider) {

  @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
  override fun build(query: CharSequence): Single<List<Anagram>> {
    return repository
        .fetchAnagrams(query.toString().quarable())
        .subscribeOn(schedulerProvider.io)
        .observeOn(schedulerProvider.ui)
  }
}
