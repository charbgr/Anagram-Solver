package com.bmpak.anagramsolver.ui.onboarding.arch

import androidx.annotation.StringRes
import com.bmpak.anagramsolver.R
import com.bmpak.anagramsolver.model.Dictionary
import com.bmpak.anagramsolver.model.Dictionary.ENGLISH
import com.bmpak.anagramsolver.model.Dictionary.FRANCE
import com.bmpak.anagramsolver.model.Dictionary.GERMAN
import com.bmpak.anagramsolver.model.Dictionary.GREEK
import com.bmpak.anagramsolver.ui.onboarding.arch.OnboardingStep.DOWNLOAD_LANGUAGES
import com.bmpak.anagramsolver.ui.onboarding.arch.OnboardingStep.INSTALL_LANGUAGE
import com.bmpak.anagramsolver.ui.onboarding.arch.OnboardingStep.ONBOARDING
import com.bmpak.anagramsolver.ui.onboarding.arch.OnboardingStep.PICKING_LANGUAGE

data class OnboardingViewModel(
    val pickedDictionaries: Map<Dictionary, Boolean>,
    val currentStep: OnboardingStep
) {

  val shouldEnableInstallButton: Boolean
    get() = pickedDictionaries.any { it.value }

  val shouldShowInstallStep: Boolean
    get() = (currentStep == PICKING_LANGUAGE)

  val shouldLanguagesBeClickable: Boolean
    get() = (currentStep == PICKING_LANGUAGE)

  val shouldShowTitle: Boolean
    get() = (currentStep != ONBOARDING)

  val shouldShowSubtitle: Boolean
    get() = (currentStep == DOWNLOAD_LANGUAGES || currentStep == INSTALL_LANGUAGE)

  val titleResId: Int
    @StringRes get() = when (currentStep) {
      PICKING_LANGUAGE -> R.string.onboarding_pick_languages_title
      DOWNLOAD_LANGUAGES -> R.string.onboarding_downloading_title
      INSTALL_LANGUAGE -> R.string.onboarding_installing_title
      else -> 0
    }

  val subtitleResId: Int
    @StringRes get() = when (currentStep) {
      DOWNLOAD_LANGUAGES -> R.string.onboarding_downloading_subtitle
      INSTALL_LANGUAGE -> R.string.onboarding_installing_subtitle
      else -> 0
    }

  companion object {
    val INITIAL = OnboardingViewModel(
        pickedDictionaries = mapOf(
            ENGLISH to false,
            GREEK to false,
            FRANCE to false,
            GERMAN to false
        ),
        currentStep = OnboardingStep.ONBOARDING
    )
  }
}
