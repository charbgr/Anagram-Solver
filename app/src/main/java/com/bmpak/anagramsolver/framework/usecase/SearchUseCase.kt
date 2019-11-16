package com.bmpak.anagramsolver.framework.usecase

import com.bmpak.anagramsolver.App.Companion.TEST_DICTIONARY
import com.bmpak.anagramsolver.framework.arch.RxUseCase
import com.bmpak.anagramsolver.framework.arch.SchedulerProvider
import com.bmpak.anagramsolver.framework.data.anagram.AnagramRepository
import com.bmpak.anagramsolver.model.Anagram
import io.reactivex.Single
import timber.log.Timber

class SearchUseCase(
    private val repository: AnagramRepository,
    schedulerProvider: SchedulerProvider = SchedulerProvider.Real
) : RxUseCase(schedulerProvider) {

  fun build(query: CharSequence): Single<List<Anagram>> {
    Timber.d("User searches : $query")
    return repository
        .fetchAnagrams(query, TEST_DICTIONARY)
        .subscribeOn(schedulerProvider.io)
        .observeOn(schedulerProvider.ui)
  }
}
