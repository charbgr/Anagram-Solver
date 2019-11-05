package com.bmpak.anagramsolver.ui.onboarding.arch

import com.bmpak.anagramsolver.UnitTest
import com.bmpak.anagramsolver.framework.repository.dictionary.FetchDictionaryRepository
import com.bmpak.anagramsolver.framework.repository.dictionary.MockFetchDictionaryRepository
import com.bmpak.anagramsolver.framework.usecase.FetchDictionaryUseCase
import com.bmpak.anagramsolver.model.Dictionary
import com.bmpak.anagramsolver.model.Dictionary.GREEK
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
    val repository = MockFetchDictionaryRepository(DownloadStatus.Pause)
    val presenter = presenter(repository = repository)
    presenter.init(robot.view)

    presenter.initialOnboardingAnimationEnd()

    robot.assertBindViewModel(viewModel(OnboardingStep.PICKING_LANGUAGE))
  }

  @Test
  fun test_binds_new_viewmodel_for_download_step() {
    val repository = MockFetchDictionaryRepository(DownloadStatus.Downloading(0, 0))
    val presenter = presenter(repository = repository).also {
      it.init(robot.view)

      it.dictionaryClicked(GREEK)
      it.installStepClicked()
    }

    robot.assertBindViewModel(viewModel(OnboardingStep.DOWNLOAD_LANGUAGES, GREEK))
    robot.assertBindDownloadStatus(GREEK, DownloadStatus.Downloading(0, 0))
  }

  @Test
  fun test_binds_new_viewmodel_for_install_step() {
    val file = File.createTempFile("foo", "txt")
    val repository = MockFetchDictionaryRepository(DownloadStatus.Success(file))
    val presenter = presenter(repository = repository)
    presenter.init(robot.view)

    presenter.dictionaryClicked(GREEK)
    presenter.installStepClicked()

    robot.assertBindViewModel(viewModel(OnboardingStep.INSTALL_LANGUAGE, GREEK))
    file.delete()
  }

  @Test
  fun test_navigate_to_main_screen_when_downloads_are_finished() {
    val file = File.createTempFile("foo", "txt")
    val repository = MockFetchDictionaryRepository(DownloadStatus.Success(file))
    val presenter = presenter(repository = repository)
    presenter.init(robot.view)

    presenter.dictionaryClicked(GREEK)
    presenter.installStepClicked()

    robot.assertNavigateToMainScreen()
    file.delete()
  }

  private fun presenter(
      repository: FetchDictionaryRepository = MockFetchDictionaryRepository(DownloadStatus.Pause)
  ) = OnboardingPresenter(
      navigator = robot.navigator,
      fetchDictionaryUseCase = FetchDictionaryUseCase(repository, NOW_SCHEDULER_PROVIDER)
  ).apply { robot.presenter = this }

  private fun viewModel(
      currentStep: OnboardingStep = OnboardingStep.ONBOARDING,
      vararg pickedDictionaries: Dictionary
  ) = OnboardingViewModel.INITIAL.copy(
      currentStep = currentStep,
      pickedDictionaries = mapOf(
          *Dictionary.values.map { it to false }.toTypedArray(),
          *pickedDictionaries.map { it to true }.toTypedArray())
  )
}
