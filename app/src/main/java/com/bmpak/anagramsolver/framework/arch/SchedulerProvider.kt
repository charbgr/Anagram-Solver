package com.bmpak.anagramsolver.framework.arch

import io.reactivex.rxjava3.core.Scheduler

interface SchedulerProvider {
  val io: Scheduler
  val ui: Scheduler
  val computation: Scheduler
}
