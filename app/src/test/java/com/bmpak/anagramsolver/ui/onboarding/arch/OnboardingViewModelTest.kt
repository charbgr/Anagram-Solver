package com.bmpak.anagramsolver.ui.onboarding.arch

import com.bmpak.anagramsolver.UnitTest
import com.bmpak.anagramsolver.model.Dictionary
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class OnboardingViewModelTest : UnitTest() {

  @Test
  fun test_viewmodel_has_correct_initial_values() {
    val viewModel = OnboardingViewModel.INITIAL

    assertThat(viewModel).isEqualTo(OnboardingViewModel.INITIAL)

    assertThat(viewModel.currentStep).isEqualTo(OnboardingStep.ONBOARDING)

    assertThat(viewModel.pickedDictionaries).containsKey(Dictionary.ENGLISH)
    assertThat(viewModel.pickedDictionaries).containsKey(Dictionary.GREEK)
    assertThat(viewModel.pickedDictionaries).containsKey(Dictionary.FRANCE)
    assertThat(viewModel.pickedDictionaries).containsKey(Dictionary.GERMAN)

    assertThat(viewModel.shouldShowInstallStep).isFalse()
    assertThat(viewModel.shouldEnableInstallButton).isFalse()
    assertThat(viewModel.shouldLanguagesBeClickable).isFalse()
    assertThat(viewModel.shouldShowTitle).isFalse()
    assertThat(viewModel.shouldShowSubtitle).isFalse()
  }
}
