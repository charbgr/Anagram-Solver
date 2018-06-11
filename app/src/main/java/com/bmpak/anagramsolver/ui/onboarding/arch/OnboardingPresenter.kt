package com.bmpak.anagramsolver.ui.onboarding.arch

import android.content.Context
import com.bmpak.anagramsolver.framework.arch.Presenter
import com.bmpak.anagramsolver.framework.navigator.Navigator
import com.bmpak.anagramsolver.framework.navigator.RealNavigator
import com.bmpak.anagramsolver.framework.repository.dictionary.FirebaseDictionaryRepository
import com.bmpak.anagramsolver.framework.usecase.FetchDictionaryUseCase
import com.bmpak.anagramsolver.model.Dictionary
import com.bmpak.anagramsolver.model.DownloadStatus
import com.bmpak.anagramsolver.ui.onboarding.arch.OnboardingStep.DOWNLOAD_LANGUAGES
import com.bmpak.anagramsolver.ui.onboarding.arch.OnboardingStep.INSTALL_LANGUAGE
import com.bmpak.anagramsolver.ui.onboarding.arch.OnboardingStep.PICKING_LANGUAGE
import com.bmpak.anagramsolver.utils.Either.Left
import kotlinx.coroutines.experimental.channels.consumeEach

class OnboardingPresenter(
    private val navigator: Navigator,
    private val fetchDictionaryUseCase: FetchDictionaryUseCase
) : Presenter<OnboardingView>() {

  companion object {
    fun create(context: Context): OnboardingPresenter =
        OnboardingPresenter(
            navigator = RealNavigator(context),
            fetchDictionaryUseCase = FetchDictionaryUseCase(FirebaseDictionaryRepository)
        )
  }

  var viewModel: OnboardingViewModel = OnboardingViewModel.INITIAL
    private set

  fun dictionaryClicked(dictionary: Dictionary) {
    val pickedDictionaries = viewModel.pickedDictionaries.toMutableMap()
    pickedDictionaries[dictionary] = pickedDictionaries[dictionary]?.not() ?: false
    this.viewModel = viewModel.copy(pickedDictionaries = pickedDictionaries.toMap())
    viewWRef.get()?.bind(viewModel)
  }

  fun installStepClicked() {
    this.viewModel = viewModel.copy(currentStep = DOWNLOAD_LANGUAGES)
    viewWRef.get()?.showDownloadingFeedback()
    viewWRef.get()?.bind(viewModel)

    fetchDictionaryUseCase.execute(Dictionary.GREEK) { result ->
      when (result) {
        is Left -> result.value.consumeEach { s ->
          viewWRef.get()?.bindDownloadStatus(s)
          if (s is DownloadStatus.Success) {
            navigator.toMainScreen()
            downloadFinished()
          }
        }
        else -> {
          //TODO
        }
      }
    }
  }

  fun downloadFinished() {
    this.viewModel = viewModel.copy(currentStep = INSTALL_LANGUAGE)
    viewWRef.get()?.bind(viewModel)
  }

  fun initialOnboardingAnimationEnd() {
    this.viewModel = viewModel.copy(currentStep = PICKING_LANGUAGE)
    viewWRef.get()?.bind(viewModel)
  }

}
