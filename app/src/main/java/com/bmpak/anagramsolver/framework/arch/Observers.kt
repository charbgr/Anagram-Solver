package com.bmpak.anagramsolver.framework.arch

import io.reactivex.rxjava3.observers.DisposableCompletableObserver
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.subscribers.DisposableSubscriber
import timber.log.Timber

/**
 * Simple DisposableObservers in order to avoid boilerplate.
 */
object Observers {

  abstract class RxSingle<T> : DisposableSingleObserver<T>() {
    override fun onError(e: Throwable) {
      Timber.e(e)
    }
  }

  abstract class RxFlowable<T> : DisposableSubscriber<T>() {
    override fun onComplete() {}
    override fun onError(e: Throwable) {
      Timber.e(e)
    }
  }

  abstract class RxObservable<T> : DisposableObserver<T>() {
    override fun onComplete() {}
    override fun onError(e: Throwable) {
      Timber.e(e)
    }
  }

  open class RxCompletable : DisposableCompletableObserver() {
    override fun onComplete() {}
    override fun onError(e: Throwable) {
      Timber.e(e)
    }
  }
}
