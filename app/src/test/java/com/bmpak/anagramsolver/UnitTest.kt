package com.bmpak.anagramsolver

import com.bmpak.anagramsolver.framework.arch.SchedulerProvider
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

open class UnitTest {
  companion object {
    val NOW_SCHEDULER = Schedulers.trampoline()
    val NOW_SCHEDULER_PROVIDER = object : SchedulerProvider {
      override val io: Scheduler = NOW_SCHEDULER
      override val ui: Scheduler = NOW_SCHEDULER
      override val computation: Scheduler = NOW_SCHEDULER
    }
  }

  init {
    Timber.plant(DebugSystemTree)
  }
}
