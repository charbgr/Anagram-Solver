package com.bmpak.anagramsolver.ui.onboarding.arch

import com.bmpak.anagramsolver.framework.arch.View

interface OnboardingView : View {
  fun bind(viewModel: OnboardingViewModel)
}
