package com.bmpak.anagramsolver.ui.onboarding.arch

import com.bmpak.anagramsolver.UnitTest
import com.bmpak.anagramsolver.framework.repository.dictionary.FetchDictionaryRepository
import com.bmpak.anagramsolver.framework.repository.dictionary.MockFetchDictionaryRepository
import com.bmpak.anagramsolver.framework.usecase.FetchDictionaryUseCase
import com.bmpak.anagramsolver.model.DownloadStatus
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.io.File

class OnboardingPresenterTest : UnitTest() {

  private val robot = OnboardingRobot()

  @Test
  fun test_creates_initial_viewmodel() {
    assertThat(presenter().viewModel).isEqualTo(OnboardingViewModel.INITIAL)
  }

  @Test
  fun test_binds_new_viewmodel_for_pick_step() {
    val repository = MockFetchDictionaryRepository()
    val presenter = presenter(repository = repository)
    presenter.init(robot.view)

    presenter.initialOnboardingAnimationEnd()

    robot.assertBindViewModel(viewModel(OnboardingStep.PICKING_LANGUAGE))
  }

  @Test
  fun test_binds_new_viewmodel_for_download_step() {
    val repository = MockFetchDictionaryRepository()
    val presenter = presenter(repository = repository)
    presenter.init(robot.view)

    presenter.installStepClicked()

    robot.assertBindViewModel(viewModel(OnboardingStep.DOWNLOAD_LANGUAGES))
    robot.assertBindDownloadStatus(DownloadStatus.Pause)
  }

  @Test
  fun test_binds_new_viewmodel_for_install_step() {
    val file = File.createTempFile("foo", "txt")
    val repository = MockFetchDictionaryRepository(DownloadStatus.Success(file))
    val presenter = presenter(repository = repository)
    presenter.init(robot.view)

    presenter.installStepClicked()

    robot.assertBindViewModel(viewModel(OnboardingStep.INSTALL_LANGUAGE))
    file.delete()
  }

  private fun presenter(
      repository: FetchDictionaryRepository = MockFetchDictionaryRepository()
  ) = OnboardingPresenter(
      navigator = robot.navigator,
      fetchDictionaryUseCase = FetchDictionaryUseCase(repository, NOW_SCHEDULER_PROVIDER)
  ).apply { robot.presenter = this }

  private fun viewModel(currentStep: OnboardingStep = OnboardingStep.ONBOARDING) =
      OnboardingViewModel.INITIAL.copy(currentStep = currentStep)
}
