package com.bmpak.anagramsolver.framework.arch

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

object RealSchedulerProvider: SchedulerProvider {
  override val io: Scheduler = Schedulers.io()
  override val ui: Scheduler = AndroidSchedulers.mainThread()
  override val computation: Scheduler = Schedulers.computation()
}
