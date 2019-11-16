package com.bmpak.anagramsolver.framework.usecase

import com.bmpak.anagramsolver.framework.arch.FlowableUseCase
import com.bmpak.anagramsolver.framework.arch.SchedulerProvider
import com.bmpak.anagramsolver.framework.data.anagram.AnagramRepository
import io.reactivex.Flowable
import java.io.File

class InstallDictionaryUseCase(
    private val repository: AnagramRepository,
    schedulerProvider: SchedulerProvider = SchedulerProvider.Real)
  : FlowableUseCase<Unit, File>(schedulerProvider) {


  override fun build(params: File): Flowable<Unit> = Flowable.empty()



}
