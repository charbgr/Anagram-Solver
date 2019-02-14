package com.bmpak.anagramsolver.framework.arch

import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

abstract class Presenter<T : View> {

  protected lateinit var viewWRef: WeakReference<T>
  protected val disposables = CompositeDisposable()

  fun init(view: T) {
    viewWRef = WeakReference(view)
  }

  fun destroy() {
    viewWRef.clear()
    disposables.clear()
  }

}
