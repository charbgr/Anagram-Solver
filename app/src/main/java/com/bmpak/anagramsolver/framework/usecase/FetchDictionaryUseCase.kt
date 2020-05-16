package com.bmpak.anagramsolver.framework.usecase

import com.bmpak.anagramsolver.framework.arch.FlowableUseCase
import com.bmpak.anagramsolver.framework.arch.RealSchedulerProvider
import com.bmpak.anagramsolver.framework.arch.SchedulerProvider
import com.bmpak.anagramsolver.framework.arch.StateReducer
import com.bmpak.anagramsolver.framework.arch.withStateReducer
import com.bmpak.anagramsolver.framework.repository.dictionary.FetchDictionaryRepository
import com.bmpak.anagramsolver.model.Dictionary
import com.bmpak.anagramsolver.model.DownloadStatus
import com.bmpak.anagramsolver.model.DownloadStatus.Success
import io.reactivex.rxjava3.core.Flowable

class FetchDictionaryUseCase(
    private val repository: FetchDictionaryRepository,
    schedulerProvider: SchedulerProvider = RealSchedulerProvider
) : FlowableUseCase<FetchResult, Dictionary>(schedulerProvider) {

  fun build(dictionaries: Iterable<Dictionary>): Flowable<MultipleFetchResult> {
    val stateReducer = object : StateReducer<MultipleFetchResult, FetchResult>() {

      override val initialState: MultipleFetchResult = MultipleFetchResult.INITIAL

      override fun reduce(
          currentState: MultipleFetchResult,
          reactToEvent: FetchResult
      ): MultipleFetchResult {
        val previousDictionaries = currentState.dictionaries.toMutableMap()
        previousDictionaries[reactToEvent.dictionary] = reactToEvent.status
        return currentState.copy(dictionaries = previousDictionaries.toMap())
      }
    }

    val dictionariesAsFlowables = dictionaries.map { build(it) }

    return Flowable
        .merge(dictionariesAsFlowables)
        .withStateReducer(stateReducer)
        .subscribeOn(schedulerProvider.io)
        .observeOn(schedulerProvider.ui)
        .share()
  }

  override fun build(params: Dictionary): Flowable<FetchResult> {
    return repository.fetch(dictionary = params)
        .map { FetchResult(status = it, dictionary = params) }
        .subscribeOn(schedulerProvider.io)
        .observeOn(schedulerProvider.ui)
        .share()
  }
}

data class MultipleFetchResult(
    val dictionaries: Map<Dictionary, DownloadStatus>
) {

  companion object {
    val INITIAL = MultipleFetchResult(emptyMap())
  }

  val areAllDictionariesFinished: Boolean
    get() = dictionaries.values.isNotEmpty() && dictionaries.values.none { it !is Success }
}

data class FetchResult(
    val dictionary: Dictionary,
    val status: DownloadStatus
)

