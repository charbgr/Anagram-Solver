package com.bmpak.anagramsolver.ui.onboarding.arch

import android.content.Context
import com.bmpak.anagramsolver.framework.arch.Observers.RxFlowable
import com.bmpak.anagramsolver.framework.arch.Presenter
import com.bmpak.anagramsolver.framework.data.anagram.AnagramRepository
import com.bmpak.anagramsolver.framework.data.anagram.RandomTextAnagramDataSource
import com.bmpak.anagramsolver.framework.data.anagram.RealAnagramEntityMapper
import com.bmpak.anagramsolver.framework.navigator.Navigator
import com.bmpak.anagramsolver.framework.navigator.RealNavigator
import com.bmpak.anagramsolver.framework.repository.dictionary.FirebaseDictionaryRepository
import com.bmpak.anagramsolver.framework.usecase.FetchDictionaryUseCase
import com.bmpak.anagramsolver.framework.usecase.InstallDictionaryUseCase
import com.bmpak.anagramsolver.framework.usecase.MultipleFetchResult
import com.bmpak.anagramsolver.model.Dictionary
import com.bmpak.anagramsolver.model.Dictionary.GREEK
import com.bmpak.anagramsolver.model.DownloadStatus
import com.bmpak.anagramsolver.ui.onboarding.arch.OnboardingStep.DOWNLOAD_LANGUAGES
import com.bmpak.anagramsolver.ui.onboarding.arch.OnboardingStep.INSTALL_LANGUAGE
import com.bmpak.anagramsolver.ui.onboarding.arch.OnboardingStep.PICKING_LANGUAGE
import io.reactivex.rxkotlin.plusAssign

class OnboardingPresenter(
    private val navigator: Navigator,
    private val fetchDictionaryUseCase: FetchDictionaryUseCase,
    private val installDictionaryUseCase: InstallDictionaryUseCase
) : Presenter<OnboardingView>() {

  companion object {
    fun create(context: Context): OnboardingPresenter =
        OnboardingPresenter(
            navigator = RealNavigator(context),
            fetchDictionaryUseCase = FetchDictionaryUseCase(FirebaseDictionaryRepository),
            installDictionaryUseCase = InstallDictionaryUseCase(AnagramRepository(
                RandomTextAnagramDataSource, RealAnagramEntityMapper
            ))
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

    val pickedDictionaries = viewModel.pickedDictionaries.filter { it.value }.keys

    disposables += fetchDictionaryUseCase.build(pickedDictionaries)
        .subscribeWith(object : RxFlowable<MultipleFetchResult>() {
          override fun onNext(result: MultipleFetchResult) {
            result.dictionaries.forEach {
              viewWRef.get()?.bindDownloadStatus(it.key, it.value)
            }

            if (result.areAllDictionariesFinished) {
              downloadFinished(result)
            }
          }
        })
  }

  private fun downloadFinished(result: MultipleFetchResult) {
    this.viewModel = viewModel.copy(currentStep = INSTALL_LANGUAGE)
    viewWRef.get()?.bind(viewModel)

    val downloadStatus = result.dictionaries[GREEK] as DownloadStatus.Success
    installDictionaryUseCase.install(GREEK, downloadStatus.file)
  }

  fun initialOnboardingAnimationEnd() {
    this.viewModel = viewModel.copy(currentStep = PICKING_LANGUAGE)
    viewWRef.get()?.bind(viewModel)
  }

}
