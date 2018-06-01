package com.bmpak.anagramsolver.ui.onboarding.arch

import com.bmpak.anagramsolver.framework.arch.Presenter

class OnboardingPresenter : Presenter<OnboardingView>() {


  fun shuffle(word: String): String {
    val chars = word.toMutableList()
    chars.shuffle()
    return chars.joinToString("")
  }
}
