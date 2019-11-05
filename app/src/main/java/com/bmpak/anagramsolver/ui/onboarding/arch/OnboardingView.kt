package com.bmpak.anagramsolver.ui.onboarding.arch

import com.bmpak.anagramsolver.framework.arch.View
import com.bmpak.anagramsolver.model.Dictionary
import com.bmpak.anagramsolver.model.DownloadStatus

interface OnboardingView : View {
  fun bind(viewModel: OnboardingViewModel)
  fun showDownloadingFeedback()
  fun bindDownloadStatus(dictionary: Dictionary, downloadStatus: DownloadStatus)
}
