package com.bmpak.anagramsolver

import com.bmpak.anagramsolver.framework.arch.CoroutineContextProvider
import kotlinx.coroutines.experimental.Unconfined
import timber.log.Timber
import kotlin.coroutines.experimental.CoroutineContext

open class UnitTest {
  companion object {
    val NOW = Unconfined
    val NOW_CO_PROVIDER = object : CoroutineContextProvider {
      override val asyncPool: CoroutineContext = Unconfined
      override val ui: CoroutineContext = Unconfined
    }
  }

  init {
    Timber.plant(DebugSystemTree)
  }
}
