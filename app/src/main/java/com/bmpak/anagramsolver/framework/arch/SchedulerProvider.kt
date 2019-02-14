package com.bmpak.anagramsolver.framework.arch

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface SchedulerProvider {
  val io: Scheduler
  val ui: Scheduler
  val computation: Scheduler

  object Real : SchedulerProvider {
    override val io: Scheduler = Schedulers.io()
    override val ui: Scheduler = AndroidSchedulers.mainThread()
    override val computation: Scheduler = Schedulers.computation()
  }
}
