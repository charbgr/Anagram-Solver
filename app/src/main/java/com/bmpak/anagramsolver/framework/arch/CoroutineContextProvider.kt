package com.bmpak.anagramsolver.framework.arch

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext

interface CoroutineContextProvider {
  val asyncPool: CoroutineContext
  val ui: CoroutineContext

  object Real : CoroutineContextProvider {
    override val asyncPool: CoroutineContext = CommonPool
    override val ui: CoroutineContext = UI
  }
}