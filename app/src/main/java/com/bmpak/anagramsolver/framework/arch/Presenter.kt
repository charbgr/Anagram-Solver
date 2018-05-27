package com.bmpak.anagramsolver.framework.arch

import java.lang.ref.WeakReference

abstract class Presenter<T : View> {

  protected lateinit var viewWRef: WeakReference<T>

  fun init(view: T) {
    viewWRef = WeakReference(view)
  }

  fun destroy() {
    viewWRef.clear()
  }

}
