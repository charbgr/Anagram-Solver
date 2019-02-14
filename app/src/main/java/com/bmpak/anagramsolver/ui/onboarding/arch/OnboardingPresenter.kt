package com.bmpak.anagramsolver.ui.onboarding.arch

import android.content.Context
import com.bmpak.anagramsolver.framework.arch.Presenter
import com.bmpak.anagramsolver.framework.navigator.Navigator
import com.bmpak.anagramsolver.framework.navigator.RealNavigator
import com.bmpak.anagramsolver.framework.repository.dictionary.FirebaseDictionaryRepository
import com.bmpak.anagramsolver.framework.usecase.FetchDictionaryUseCase
import com.bmpak.anagramsolver.model.Dictionary
import com.bmpak.anagramsolver.model.DownloadStatus
import com.bmpak.anagramsolver.ui.onboarding.arch.OnboardingStep.*
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subscribers.ResourceSubscriber

class OnboardingPresenter(
  private val navigator: Navigator,
  val fetchDictionaryUseCase: FetchDictionaryUseCase
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

    disposables += fetchDictionaryUseCase
      .build(Dictionary.GREEK)
      .subscribeWith(object : ResourceSubscriber<DownloadStatus>() {
        override fun onComplete() {
        }

        override fun onNext(downloadStatus: DownloadStatus) {
          viewWRef.get()?.bindDownloadStatus(downloadStatus)
          if (downloadStatus is DownloadStatus.Success) {
            navigator.toMainScreen()
            downloadFinished()
          }
        }

        override fun onError(e: Throwable) {
        }
      })
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
