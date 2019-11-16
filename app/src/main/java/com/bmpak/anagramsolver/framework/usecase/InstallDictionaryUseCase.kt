package com.bmpak.anagramsolver.framework.usecase

import com.bmpak.anagramsolver.framework.arch.Observers.RxFlowable
import com.bmpak.anagramsolver.framework.arch.RxUseCase
import com.bmpak.anagramsolver.framework.arch.SchedulerProvider
import com.bmpak.anagramsolver.framework.data.anagram.AnagramRepository
import com.bmpak.anagramsolver.model.Dictionary
import io.reactivex.BackpressureStrategy.LATEST
import io.reactivex.Flowable
import io.reactivex.rxkotlin.plusAssign
import okio.Okio
import java.io.File
import java.io.IOException


class InstallDictionaryUseCase(
    private val repository: AnagramRepository,
    schedulerProvider: SchedulerProvider = SchedulerProvider.Real)
  : RxUseCase(schedulerProvider) {

  fun install(dictionary: Dictionary, file: File): Flowable<String> {
    val rxReadFile = file.rxReadFile().share()

    disposables += rxReadFile
        .observeOn(schedulerProvider.io)
        .subscribeWith(object : RxFlowable<String>() {
          override fun onNext(wordOrigin: String) {
            repository.put(wordOrigin, dictionary)
          }
        })

    return rxReadFile
  }

  private fun File.rxReadFile(): Flowable<String> = Flowable.create({ emitter ->
    try {
      val fileSource = Okio.source(this)
      val bufferedSource = Okio.buffer(fileSource)
      while (true) {
        val originWord = bufferedSource.readUtf8Line()
        if (originWord == null) {
          emitter.onComplete()
          break
        }

        emitter.onNext(originWord)
      }
    } catch (e: IOException) {
      emitter.onError(e)
    }
  }, LATEST)
}
