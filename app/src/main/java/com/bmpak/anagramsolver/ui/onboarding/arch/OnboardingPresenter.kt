package com.bmpak.anagramsolver.ui.onboarding.arch

import com.bmpak.anagramsolver.framework.arch.Presenter
import com.bmpak.anagramsolver.framework.navigator.Navigator
import com.bmpak.anagramsolver.model.Dictionary

class OnboardingPresenter(
    private val navigator: Navigator
) : Presenter<OnboardingView>() {

  var viewModel: OnboardingViewModel = OnboardingViewModel.INITIAL
    private set

  fun dictionaryClicked(dictionary: Dictionary) {
    val pickedDictionaries = viewModel.pickedDictionaries.toMutableMap()
    pickedDictionaries[dictionary] = pickedDictionaries[dictionary]?.not() ?: false
    this.viewModel = viewModel.copy(pickedDictionaries = pickedDictionaries.toMap())
    viewWRef.get()?.bind(viewModel)
  }

  fun installDictionaries() {
  }

  fun initialOnboardingAnimationEnd() {
    viewWRef.get()?.bind(viewModel)
  }

  fun shuffle(word: String): String {
    val chars = word.toMutableList()
    chars.shuffle()
    return chars.joinToString("")
  }
}
