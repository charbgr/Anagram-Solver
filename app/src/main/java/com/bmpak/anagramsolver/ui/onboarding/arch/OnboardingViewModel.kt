package com.bmpak.anagramsolver.ui.onboarding.arch

import com.bmpak.anagramsolver.model.Dictionary
import com.bmpak.anagramsolver.model.Dictionary.ENGLISH
import com.bmpak.anagramsolver.model.Dictionary.FRANCE
import com.bmpak.anagramsolver.model.Dictionary.GERMAN
import com.bmpak.anagramsolver.model.Dictionary.GREEK

data class OnboardingViewModel(
    val pickedDictionaries: Map<Dictionary, Boolean>
) {

  val shouldEnableInstallButton: Boolean
    get() = pickedDictionaries.any { it.value }

  companion object {
    val INITIAL = OnboardingViewModel(mapOf(
        ENGLISH to false,
        GREEK to false,
        FRANCE to false,
        GERMAN to false
    ))
  }
}
