package com.bmpak.anagramsolver.ui.onboarding.arch

import com.bmpak.anagramsolver.UnitTest
import com.bmpak.anagramsolver.framework.navigator.MockNavigator
import com.bmpak.anagramsolver.framework.navigator.Navigator
import com.bmpak.anagramsolver.framework.repository.dictionary.FetchDictionaryRepository
import com.bmpak.anagramsolver.framework.repository.dictionary.MockFetchDictionaryRepository
import com.bmpak.anagramsolver.framework.usecase.FetchDictionaryUseCase
import com.bmpak.anagramsolver.model.DownloadStatus
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.io.File

class OnboardingPresenterTest : UnitTest() {

  @Test
  fun test_creates_initial_viewmodel() {
    assertThat(presenter().viewModel).isEqualTo(OnboardingViewModel.INITIAL)
  }

  @Test
  fun test_binds_new_viewmodel_for_pick_step() {
    val view = MockOnboardingView()
    val repository = MockFetchDictionaryRepository()
    val presenter = presenter(repository = repository)
    presenter.init(view)

    presenter.initialOnboardingAnimationEnd()

    assertThat(presenter.viewModel.currentStep).isEqualTo(OnboardingStep.PICKING_LANGUAGE)
    view.bindTapes.assertRenderedOnce()
  }

  @Test
  fun test_binds_new_viewmodel_for_download_step() {
    val view = MockOnboardingView()
    val repository = MockFetchDictionaryRepository()
    val presenter = presenter(repository = repository)
    presenter.init(view)

    presenter.installStepClicked()

    assertThat(presenter.viewModel.currentStep).isEqualTo(OnboardingStep.DOWNLOAD_LANGUAGES)
    view.bindTapes.assertRenderedOnce()
    view.showDownloadingFeedbackTapes.assertRenderedOnce()
  }

  @Test
  fun test_binds_new_viewmodel_for_install_step() {
    val file = File.createTempFile("foo", "txt")
    val view = MockOnboardingView()
    val repository = MockFetchDictionaryRepository()
    val presenter = presenter(repository = repository)
    presenter.init(view)

    presenter.installStepClicked()
    view.bindTapes.reset()
    repository.onFetch(DownloadStatus.Success(file))

    assertThat(presenter.viewModel.currentStep).isEqualTo(OnboardingStep.INSTALL_LANGUAGE)
    view.bindTapes.assertRenderedOnce()
    file.delete()
  }

  private fun presenter(
      navigator: Navigator = MockNavigator(),
      repository: FetchDictionaryRepository = MockFetchDictionaryRepository()
  ) = OnboardingPresenter(navigator, FetchDictionaryUseCase(repository, NOW_CO_PROVIDER))
}
