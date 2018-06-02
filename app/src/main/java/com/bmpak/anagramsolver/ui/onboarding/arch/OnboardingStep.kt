package com.bmpak.anagramsolver.ui.onboarding.arch

sealed class OnboardingStep {
  object ONBOARDING : OnboardingStep()
  object PICKING_LANGUAGE: OnboardingStep()
  object DOWNLOAD_LANGUAGES: OnboardingStep()
  object INSTALL_LANGUAGE: OnboardingStep()
}
