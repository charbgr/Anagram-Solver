package com.bmpak.anagramsolver

import android.app.Application
import com.bmpak.anagramsolver.model.Dictionary
import timber.log.Timber
import timber.log.Timber.DebugTree

class App : Application() {

  companion object {
    val TEST_DICTIONARY = Dictionary.ENGLISH
  }

  override fun onCreate() {
    super.onCreate()
    Timber.plant(DebugTree())
  }
}
