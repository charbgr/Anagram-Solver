package com.bmpak.anagramsolver.ui.onboarding.arch

import com.bmpak.anagramsolver.framework.navigator.Navigator
import com.bmpak.anagramsolver.model.Dictionary
import com.bmpak.anagramsolver.model.DownloadStatus
import com.google.common.truth.Truth.assertThat
import io.mockk.spyk
import io.mockk.verify

class OnboardingRobot {

  val view = spyk<OnboardingView>()
  val navigator = spyk<Navigator>()
  var presenter: OnboardingPresenter? = null

  fun assertBindViewModel(viewModel: OnboardingViewModel) {
    verify { view.bind(viewModel) }
    assertThat(presenter?.viewModel).isEqualTo(viewModel)
  }

  fun assertBindDownloadStatus(dictionary: Dictionary, downloadStatus: DownloadStatus) {
    verify { view.bindDownloadStatus(dictionary, downloadStatus) }
  }

  fun assertNavigateToMainScreen() {
    verify { navigator.toMainScreen() }
  }
}
