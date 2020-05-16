package com.bmpak.anagramsolver.framework.arch

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class RxUseCase(protected var schedulerProvider: SchedulerProvider) {

  protected val disposables: CompositeDisposable by lazy {
    CompositeDisposable()
  }
}

abstract class ObservableUseCase<Type, in Params>(
  schedulerProvider: SchedulerProvider
) : RxUseCase(schedulerProvider) {

  abstract fun build(params: Params): Observable<Type>
}

abstract class SingleUseCase<Type, in Params>(
  schedulerProvider: SchedulerProvider
) : RxUseCase(schedulerProvider) {

  abstract fun build(params: Params): Single<Type>
}

abstract class CompletableUseCase<in Params>(
  schedulerProvider: SchedulerProvider
) : RxUseCase(schedulerProvider) {

  abstract fun build(params: Params): Completable
}

abstract class FlowableUseCase<Type, in Params>(
  schedulerProvider: SchedulerProvider
) : RxUseCase(schedulerProvider) {

  abstract fun build(params: Params): Flowable<Type>
}
