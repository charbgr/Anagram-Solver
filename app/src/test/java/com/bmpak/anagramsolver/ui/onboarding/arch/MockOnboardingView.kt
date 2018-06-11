package com.bmpak.anagramsolver.ui.onboarding.arch

import com.bmpak.anagramsolver.Tape
import com.bmpak.anagramsolver.model.DownloadStatus

class MockOnboardingView : OnboardingView {

  var bindTapes = Tape.create<OnboardingViewModel>()
  var showDownloadingFeedbackTapes = Tape.create<Unit>()
  var bindDownloadStatusTapes = Tape.create<DownloadStatus>()

  override fun bind(viewModel: OnboardingViewModel) = bindTapes.add(viewModel)
  override fun showDownloadingFeedback() = showDownloadingFeedbackTapes.add(Unit)
  override fun bindDownloadStatus(downloadStatus: DownloadStatus) =
      bindDownloadStatusTapes.add(downloadStatus)
}

